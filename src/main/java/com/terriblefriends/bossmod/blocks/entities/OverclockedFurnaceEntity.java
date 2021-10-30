package com.terriblefriends.bossmod.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class OverclockedFurnaceEntity extends AbstractOverclockedFurnaceEntity {
    public OverclockedFurnaceEntity(BlockPos blockPos, BlockState blockState) {
        super(BossmodBlockEntities.OVERCLOCKED_FURNACE_ENTITY, blockPos, blockState, RecipeType.SMELTING);
    }

    protected Text getContainerName() {
        return new TranslatableText("container.overclocked_furnace");
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
