package com.terriblefriends.bossmod.blocks.entities;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractUnnaturalChestEntity<E extends BlockEntity> extends BlockWithEntity {

    protected AbstractUnnaturalChestEntity(AbstractBlock.Settings settings) {
        super(settings);
    }

    public abstract DoubleBlockProperties.PropertySource<? extends UnnaturalChestEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked);
}
