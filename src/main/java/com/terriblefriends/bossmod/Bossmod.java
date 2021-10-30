package com.terriblefriends.bossmod;

import com.terriblefriends.bossmod.biomes.BossmodBiomes;
import com.terriblefriends.bossmod.blocks.BossmodBlocks;
import com.terriblefriends.bossmod.blocks.entities.BossmodBlockEntities;
import com.terriblefriends.bossmod.items.BossmodItems;
import com.terriblefriends.bossmod.screen.UnnaturalDoubleChestScreenHandler;
import com.terriblefriends.bossmod.screen.UnnaturalChestScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class Bossmod implements ModInitializer {
    public static final ScreenHandlerType<UnnaturalChestScreenHandler> UNNATURAL_LARGE_CHEST_SCREEN_HANDLER;
    public static final ScreenHandlerType<UnnaturalDoubleChestScreenHandler> UNNATURAL_DOUBLE_CHEST_SCREEN_HANDLER;
    static {
        UNNATURAL_LARGE_CHEST_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("bossmod","unnatural_chest"), UnnaturalChestScreenHandler::new);
        UNNATURAL_DOUBLE_CHEST_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("bossmod","unnatural_double_chest"),UnnaturalDoubleChestScreenHandler::new);
    }
    @Override
    public void onInitialize() {
        BossmodItems.register();
        BossmodBlocks.register();
        BossmodBlockEntities.register();
        BossmodBiomes.register();
    }
}
