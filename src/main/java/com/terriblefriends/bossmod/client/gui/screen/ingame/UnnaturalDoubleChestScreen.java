package com.terriblefriends.bossmod.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import com.terriblefriends.bossmod.screen.UnnaturalDoubleChestScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class UnnaturalDoubleChestScreen extends HandledScreen<UnnaturalDoubleChestScreenHandler> implements ScreenHandlerProvider<UnnaturalDoubleChestScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("bossmod","textures/gui/container/generic_72.png");
    private int rows;

    public UnnaturalDoubleChestScreen(UnnaturalDoubleChestScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
        this.passEvents = false;
        this.rows = screenHandler.getRows();
        this.backgroundHeight = 112 + this.rows * 18;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.rows * 18 + 19);
        this.drawTexture(matrices, i, j + this.rows * 18 + 19, 0, 162, this.backgroundWidth, 95);//CHANGE V TO FIX HEIGHT
    }
}
