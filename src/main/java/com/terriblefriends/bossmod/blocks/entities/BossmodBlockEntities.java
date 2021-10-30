package com.terriblefriends.bossmod.blocks.entities;

import com.terriblefriends.bossmod.blocks.BossmodBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BossmodBlockEntities {
    public static BlockEntityType<UnnaturalChestEntity> UNNATURAL_LARGE_CHEST_ENTITY;
    public static BlockEntityType<OverclockedFurnaceEntity> OVERCLOCKED_FURNACE_ENTITY;

    public static void register() {
        UNNATURAL_LARGE_CHEST_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "bossmod:unnatural_large_chest_entity", FabricBlockEntityTypeBuilder.create(UnnaturalChestEntity::new, BossmodBlocks.UnnaturalLargeChest).build(null));
        OVERCLOCKED_FURNACE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "bossmod:overclocked_furnace_entity", FabricBlockEntityTypeBuilder.create(OverclockedFurnaceEntity::new, BossmodBlocks.OverclockedFurnace).build(null));
    }
}
