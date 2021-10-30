package com.terriblefriends.bossmod.access;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;

public interface GenericContainerScreenHandlerAccess {
    public GenericContainerScreenHandler createGeneric9x4(int syncId, PlayerInventory playerInventory, Inventory inventory);
}
