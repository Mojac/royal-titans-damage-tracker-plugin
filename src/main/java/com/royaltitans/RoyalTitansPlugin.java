package com.royaltitans;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Hitsplat;
import net.runelite.api.NPC;
import net.runelite.api.WorldView;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.gameval.NpcID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@PluginDescriptor(
	name = "Royal Titans Damage Tracker", 
	description = "Tracks damage contribution for Royal Titans (Branda and Eldric)", 
	tags = {"royal", "titans", "damage", "contribution", "branda", "eldric", "giants", 
	        "duo", "boss"}
)
public class RoyalTitansPlugin extends Plugin {

	private static final int BRANDA_ID = NpcID.RT_FIRE_QUEEN;
	private static final int ELDRIC_ID = NpcID.RT_ICE_KING;

	private static final int COMBINED_HP = 1200;
	private static final double BASE_DROP_RATE = 75.0;
	
	@Inject
	private Client client;

	@Inject
	private RoyalTitansConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private RoyalTitansOverlay overlay;

	@Inject
	private ScheduledExecutorService executorService;

	private int brandaDamage = 0;
	private int eldricDamage = 0;
	private boolean encounterActive = false;
	private NPC branda = null;
	private NPC eldric = null;
	private ScheduledFuture<?> resetCounterDelay = null;
	private boolean resetScheduled = false;
	private boolean titansDefeated = false;
	private int ticksSinceLastTitanSeen = 0;

	@Override
	protected void startUp() throws Exception {
		overlayManager.add(overlay);
		resetDamageCounters();
	}

	@Override
	protected void shutDown() throws Exception {
		overlayManager.remove(overlay);
		cancelResetCounterDelay();
		resetDamageCounters();
	}

	@Subscribe
	public void onChatMessage(ChatMessage event) {
		String message = event.getMessage();

		// Reset damage tracker when titans respawn (this is the looting message)
		if (message.contains("The Royal Titans will reinvigorate themselves in 18 seconds.")) {
			scheduleReset();
		}
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event) {
		NPC npc = event.getNpc();

		if (npc.getId() == BRANDA_ID) {
			branda = npc;
			// If we have damage tracked and titans were defeated, but now fresh titans spawned,
			// this means player left and returned to new spawns - reset the tracker
			if (getTotalDamage() > 0 && titansDefeated && !resetScheduled) {
				resetDamageCounters();
			}
			titansDefeated = false;
			ticksSinceLastTitanSeen = 0;
			checkEncounterStart();
		} else if (npc.getId() == ELDRIC_ID) {
			eldric = npc;
			// Same check for Eldric
			if (getTotalDamage() > 0 && titansDefeated && !resetScheduled) {
				resetDamageCounters();
			}
			titansDefeated = false;
			ticksSinceLastTitanSeen = 0;
			checkEncounterStart();
		}
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event) {
		NPC npc = event.getNpc();

		if (npc.getId() == BRANDA_ID) {
			branda = null;
		} else if (npc.getId() == ELDRIC_ID) {
			eldric = null;
		}

		// If both titans are gone and player did damage, they were likely defeated
		if (branda == null && eldric == null && getTotalDamage() > 0) {
			titansDefeated = true;
		}
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied event) {
		if (!encounterActive) {
			return;
		}

		Actor target = event.getActor();
		Hitsplat hitsplat = event.getHitsplat();

		// Only count damage from the local player
		if (!hitsplat.isMine()) {
			return;
		}

		// Only count damage to the Royal Titans
		if (target instanceof NPC) {
			NPC npc = (NPC) target;
			int damage = hitsplat.getAmount();

			if (npc.getId() == BRANDA_ID) {
				brandaDamage += damage;
			} else if (npc.getId() == ELDRIC_ID) {
				eldricDamage += damage;
			}
		}
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		// Validate NPC references and clean up if needed
		validateNpcReferences();

		// Check if player has left the encounter area
		checkAreaExit();
	}

	private void validateNpcReferences() {
		// Clean up invalid NPC references
		if (branda != null && !isNpcValid(branda)) {
			branda = null;
		}

		if (eldric != null && !isNpcValid(eldric)) {
			eldric = null;
		}
	}

	private void checkAreaExit() {
		// Check if there are any Royal Titan NPCs in the current world view
		boolean anyTitansPresent = false;
		WorldView worldView = client.getTopLevelWorldView();

		if (worldView != null) {
			for (NPC npc : worldView.npcs()) {
				if (npc.getId() == BRANDA_ID || npc.getId() == ELDRIC_ID) {
					anyTitansPresent = true;
					break;
				}
			}
		}

		if (anyTitansPresent) {
			// Titans are present, reset the exit counter
			ticksSinceLastTitanSeen = 0;
		} else if (encounterActive || getTotalDamage() > 0) {
			// No titans present but we have an active encounter or damage tracked
			ticksSinceLastTitanSeen++;
		}

		// Only reset if:
		// 1. No royal titans have been seen for 10 ticks (6 seconds) - player left the area
		// 2. No reset is already scheduled
		// 3. Titans were not defeated (player left during active fight)
		if (ticksSinceLastTitanSeen >= 10 && !resetScheduled && !titansDefeated) {
			resetDamageCounters();
		}
		
	}

	private boolean isNpcValid(NPC npc) {
		if (npc == null || npc.isDead()) {
			return false;
		}
		
		// Check if NPC exists in the current world view
		WorldView worldView = client.getTopLevelWorldView();
		if (worldView == null) {
			return false;
		}

		// Iterate through NPCs to find our reference
		for (NPC worldNpc : worldView.npcs()) {
			if (worldNpc == npc) {
				return true;
			}
		}

		return false;
	}

	private void scheduleReset() {
		// Cancel any existing reset delay
		cancelResetCounterDelay();

		// Set the flag to indicate a reset is scheduled
		resetScheduled = true;

		// Get delay from config and clamp between 0-18 seconds
		int delaySeconds = Math.max(0, Math.min(18, config.resetDelay()));

		if (delaySeconds == 0) {
			// Reset immediately if delay is 0
			resetDamageCounters();
			return;
		}

		// Schedule new reset task for specified delay
		resetCounterDelay = executorService.schedule(() -> {
			resetDamageCounters();
			resetCounterDelay = null;
		}, delaySeconds, TimeUnit.SECONDS);
	}

	private void cancelResetCounterDelay() {
		if (resetCounterDelay != null && !resetCounterDelay.isDone()) {
			resetCounterDelay.cancel(false);
		}
		resetCounterDelay = null;
		resetScheduled = false;
	}

	private void checkEncounterStart() {
		if (!encounterActive && (branda != null || eldric != null)) {
			// Cancel any existing reset delay since a new encounter is starting
			cancelResetCounterDelay();
			encounterActive = true;
			titansDefeated = false;
			ticksSinceLastTitanSeen = 0;
		}
	}

	private void resetDamageCounters() {
		brandaDamage = 0;
		eldricDamage = 0;
		encounterActive = false;
		branda = null;
		eldric = null;
		titansDefeated = false;
		ticksSinceLastTitanSeen = 0;
		cancelResetCounterDelay();
	}

	// Getters for overlay
	public int getBrandaDamage() {
		return brandaDamage;
	}

	public int getEldricDamage() {
		return eldricDamage;
	}

	public int getTotalDamage() {
		return brandaDamage + eldricDamage;
	}

	public double getContributionPercentage() {
		int totalDamage = getTotalDamage();
		if (totalDamage == 0) {
			return 0.0;
		}
		return Math.min(100.0, (totalDamage * 100.0) / COMBINED_HP);
	}

	public String getDropRate() {
		int totalDamage = getTotalDamage();
		if (totalDamage == 0) {
			return "N/A";
		}

		double contribution = Math.min(1.0, totalDamage / (double) COMBINED_HP);
		int adjustedRate = (int) Math.round(BASE_DROP_RATE / contribution);

		return "1/" + adjustedRate;
	}

	public boolean isEncounterActive() {
		return encounterActive;
	}

	@Provides
	RoyalTitansConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(RoyalTitansConfig.class);
	}
}
