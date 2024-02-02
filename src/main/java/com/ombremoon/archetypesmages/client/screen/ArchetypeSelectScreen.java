package com.ombremoon.archetypesmages.client.screen;

import com.ombremoon.archetypesmages.Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ArchetypeSelectScreen extends Screen {
    private static final Component SHAMAN = Component.translatable(Constants.MOD_ID + ".menu.shaman");
    private static final Component DRUID = Component.translatable(Constants.MOD_ID + ".menu.druid");
    private static final Component WARLOCK = Component.translatable(Constants.MOD_ID + ".menu.warlock");
    private static final Component SHADOW_MAGE = Component.translatable(Constants.MOD_ID + ".menu.shadow_mage");
    private static final Component DIVINE_CLERIC = Component.translatable(Constants.MOD_ID + ".menu.divine_cleric");

    protected ArchetypeSelectScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    protected void init() {
        this.createArchetypeSelectScreen();
        this.addRenderableWidget(new StringWidget(0, 40, this.width, 9, this.title, this.font));
    }

    private void createArchetypeSelectScreen() {

    }

    private void initButtons() {
        this.addRenderableWidget(Button.builder(SHAMAN, pButton -> {

        }))
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
