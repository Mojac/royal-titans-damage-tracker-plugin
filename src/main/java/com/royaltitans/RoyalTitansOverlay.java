package com.royaltitans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

public class RoyalTitansOverlay extends Overlay {

    private final RoyalTitansPlugin plugin;
    private final RoyalTitansConfig config;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    private RoyalTitansOverlay(RoyalTitansPlugin plugin, RoyalTitansConfig config) {
        this.plugin = plugin;
        this.config = config;

        setPosition(OverlayPosition.TOP_LEFT);
        setPriority(Overlay.PRIORITY_MED);
        getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, 
            "Royal Titans overlay"));
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        // Don't render if encounter isn't active and config says to hide
        if (config.showOnlyDuringEncounter() && !plugin.isEncounterActive()) {
            return null;
        }

        // Don't render if no damage has been dealt yet
        if (plugin.getTotalDamage() == 0 && config.showOnlyDuringEncounter()) {
            return null;
        }

        panelComponent.getChildren().clear();

        Color totalColor = config.totalColor();
        Color brandaColor = config.brandaColor();
        Color eldricColor = config.eldricColor();
        Color dropRateColor = config.dropRateColor();

        // Line 1: Total damage with percentage
        int totalDamage = plugin.getTotalDamage();
        double percentage = plugin.getContributionPercentage();
        String totalLine = String.format("Total: %d  (%.1f%%)", totalDamage, percentage);

        panelComponent.getChildren().add(LineComponent.builder()
            .left(totalLine)
            .leftColor(totalColor)
            .build());

        // Line 2-3: Individual titan damage (if enabled)
        if (config.showIndividualDamage()) {
            panelComponent.getChildren().add(LineComponent.builder()
                .left("Branda: " + plugin.getBrandaDamage())
                .leftColor(brandaColor)
                .build());

            panelComponent.getChildren().add(LineComponent.builder()
                .left("Eldric: " + plugin.getEldricDamage())
                .leftColor(eldricColor)
                .build());
        }

        // Line 4: Drop rate (if enabled)
        if (config.showDropRate()) {
            String dropRate = plugin.getDropRate();
            panelComponent.getChildren().add(LineComponent.builder()
                .left("Drop Rate: " + dropRate)
                .leftColor(dropRateColor)
                .build());
        }

        return panelComponent.render(graphics);
    }
    
}
