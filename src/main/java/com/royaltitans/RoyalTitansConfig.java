package com.royaltitans;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("royaltitans")
public interface RoyalTitansConfig extends Config {
	
	@ConfigSection(
		name = "Display Options",
		description = "Customize what information to show in the overlay",
		position = 0
	)
	String displaySection = "display";

	@ConfigItem(
		keyName = "showIndividualDamage",
		name = "Show Individual Titan Damage",
		description = "Display damage to each titan separately (Branda and Eldric lines)",
		section = displaySection,
		position = 0
	)
	default boolean showIndividualDamage() {
		return true;
	}

	@ConfigItem(
		keyName = "showDropRate",
		name = "Show Drop Rate",
		description = "Calculate and display drop rate based on current contribution",
		section = displaySection,
		position = 1
	)
	default boolean showDropRate() {
		return true;
	}

	@ConfigSection(
		name = "Overlay Settings",
		description = "Overlay appearance and positioning",
		position = 1
	)
	String overlaySection = "overlay";

	@ConfigItem(
		keyName = "showOnlyDuringEncounter",
		name = "Show Only During Encounter",
		description = "Only display the overlay when Royal Titans encounter is active",
		section = overlaySection,
		position = 0
	)
	default boolean showOnlyDuringEncounter() {
		return true;
	}

	@ConfigItem(
		keyName = "resetDelay",
		name = "Reset Delay (seconds)",
		description = "Delay in seconds before resetting damage counters after encounter ends. " +
			"(0-18 seconds) Set to 0 for immediate reset on looting.",
		section = overlaySection,
		position = 1
	)
	default int resetDelay() {
		return 15; // Default reset delay in seconds
	}

	@ConfigItem(
		keyName = "totalColor",
		name = "Total Damage Color",
		description = "Color of the total damage text",
		section = overlaySection,
		position = 2
	)
	default java.awt.Color totalColor() {
		return java.awt.Color.WHITE;
	}

	@ConfigItem(
		keyName = "brandaColor",
		name = "Branda Damage Color",
		description = "Color of Branda damage text",
		section = overlaySection,
		position = 3
	)
	default java.awt.Color brandaColor() {
		return java.awt.Color.PINK;
	}

	@ConfigItem(
		keyName = "eldricColor",
		name = "Eldric Damage Color",
		description = "Color of Eldric damage text",
		section = overlaySection,
		position = 4
	)
	default java.awt.Color eldricColor() {
		return java.awt.Color.CYAN;
	}

	@ConfigItem(
		keyName = "dropRateColor",
		name = "Drop Rate Color",
		description = "Color of the drop rate text",
		section = overlaySection,
		position = 5
	)
	default java.awt.Color dropRateColor() {
		return java.awt.Color.ORANGE;
	}
}
