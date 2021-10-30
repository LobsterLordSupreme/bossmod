package com.terriblefriends.bossmod.items;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class BossmodItemGroups {
    public static final ItemGroup BOSSMOD_GENERAL_ITEMS = FabricItemGroupBuilder.create(
            new Identifier("bossmod", "general"))
            .icon(() -> new ItemStack(Items.BARRIER))
            .build();
    public static final ItemGroup BOSSMOD_BOSS_DROPS = FabricItemGroupBuilder.create(
            new Identifier("bossmod", "bossdrops"))
            .icon(() -> new ItemStack(Items.BARRIER))
            .build();
    public static final ItemGroup BOSSMOD_MOBDROPS = FabricItemGroupBuilder.create(
            new Identifier("bossmod", "mobdrops"))
            .icon(() -> new ItemStack(Items.BARRIER))
            .build();
    public static final ItemGroup BOSSMOD_TERRAIN = FabricItemGroupBuilder.create(
            new Identifier("bossmod", "terrain"))
            .icon(() -> new ItemStack(Items.BARRIER))
            .build();
}
