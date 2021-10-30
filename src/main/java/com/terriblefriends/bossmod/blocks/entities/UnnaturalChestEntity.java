package com.terriblefriends.bossmod.blocks.entities;

import com.terriblefriends.bossmod.screen.UnnaturalChestScreenHandler;
import com.terriblefriends.bossmod.util.ImplementedInventory;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class UnnaturalChestEntity extends LootableContainerBlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {
    private static final int rows = 4;
    private final ViewerCountManager stateManager;
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(rows*9, ItemStack.EMPTY);

    public UnnaturalChestEntity(BlockPos blockPos, BlockState blockState) {
        super(BossmodBlockEntities.UNNATURAL_LARGE_CHEST_ENTITY, blockPos, blockState);
        this.stateManager = new ViewerCountManager() {
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                playSound(world, pos, state, SoundEvents.BLOCK_CHEST_OPEN);
            }

            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                playSound(world, pos, state, SoundEvents.BLOCK_CHEST_CLOSE);
            }

            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
                onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);
            }

            protected boolean isPlayerViewing(PlayerEntity player) {
                if (!(player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                    return false;
                } else {
                    Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                    return inventory == UnnaturalChestEntity.this || inventory instanceof DoubleInventory && ((DoubleInventory)inventory).isPart(UnnaturalChestEntity.this);
                }
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public int size() {
        return rows*9;
    }

    protected Text getContainerName() {
        return new TranslatableText("container.unnatural_large_chest");
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }

    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }

        return nbt;
    }

    static void playSound(World world, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = (state.get(Properties.HORIZONTAL_FACING)).getVector();
        double d = (double)pos.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
        double e = (double)pos.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
        double f = (double)pos.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
        world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
    }

    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    public static void copyInventory(UnnaturalChestEntity from, UnnaturalChestEntity to) {
        DefaultedList<ItemStack> defaultedList = from.getInvStackList();
        from.setInvStackList(to.getInvStackList());
        to.setInvStackList(defaultedList);
    }

    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new UnnaturalChestScreenHandler(i, playerInventory, this);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    protected void onInvOpenOrClose(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        Block block = state.getBlock();
        world.addSyncedBlockEvent(pos, block, 1, newViewerCount);
    }
}
