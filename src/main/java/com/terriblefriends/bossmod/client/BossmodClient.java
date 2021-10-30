package com.terriblefriends.bossmod.client;

import com.terriblefriends.bossmod.Bossmod;
import com.terriblefriends.bossmod.client.gui.screen.ingame.UnnaturalChestScreen;
import com.terriblefriends.bossmod.client.gui.screen.ingame.UnnaturalDoubleChestScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class BossmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Bossmod.UNNATURAL_LARGE_CHEST_SCREEN_HANDLER, UnnaturalChestScreen::new);
        ScreenRegistry.register(Bossmod.UNNATURAL_DOUBLE_CHEST_SCREEN_HANDLER, UnnaturalDoubleChestScreen::new);
    }
}
