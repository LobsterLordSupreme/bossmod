package com.terriblefriends.bossmod.biomes;

import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BossmodBiomes {
    public static RegistryKey<Biome> PETRIFIED_OAK_FOREST_KEY;

    public static RegistryKey<Biome> registerInternal(String name, Biome biome) {
        Identifier identifier = new Identifier("bossmod",name);
        BuiltinRegistries.add(BuiltinRegistries.BIOME,identifier, biome);
        return RegistryKey.of(Registry.BIOME_KEY,identifier);
    }

    public static void register() {
        PetrifiedOakForestBiome.register();
        OverworldBiomes.addContinentalBiome(PETRIFIED_OAK_FOREST_KEY, OverworldClimate.DRY, 2D);
    }
}
