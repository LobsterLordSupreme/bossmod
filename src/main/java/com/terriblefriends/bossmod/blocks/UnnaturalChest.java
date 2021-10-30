package com.terriblefriends.bossmod.blocks;

import com.terriblefriends.bossmod.blocks.entities.AbstractUnnaturalChestEntity;
import com.terriblefriends.bossmod.blocks.entities.BossmodBlockEntities;
import com.terriblefriends.bossmod.blocks.entities.UnnaturalChestEntity;
import com.terriblefriends.bossmod.screen.UnnaturalDoubleChestScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class UnnaturalChest extends AbstractUnnaturalChestEntity<UnnaturalChestEntity> {
    public static final DirectionProperty FACING;
    public static final EnumProperty<ChestType> CHEST_TYPE;
    private static final DoubleBlockProperties.PropertyRetriever<UnnaturalChestEntity, Optional<Inventory>> INVENTORY_RETRIEVER;
    private static final DoubleBlockProperties.PropertyRetriever<UnnaturalChestEntity, Optional<NamedScreenHandlerFactory>> NAME_RETRIEVER;

    public UnnaturalChest(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.CHEST_TYPE, ChestType.SINGLE));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HORIZONTAL_FACING, Properties.CHEST_TYPE);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            NamedScreenHandlerFactory namedScreenHandlerFactory = this.createScreenHandlerFactory(state, world, pos);
            if (namedScreenHandlerFactory != null) {
                player.openHandledScreen(namedScreenHandlerFactory);
            }

            return ActionResult.CONSUME;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof UnnaturalChestEntity) {
                ((UnnaturalChestEntity)blockEntity).setCustomName(itemStack.getName());
            }
        }

    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new UnnaturalChestEntity(pos, state);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }


    @Nullable
    private Direction getNeighborChestDirection(ItemPlacementContext ctx, Direction dir) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(dir));
        return blockState.isOf(this) && blockState.get(Properties.CHEST_TYPE) == ChestType.SINGLE ? blockState.get(Properties.HORIZONTAL_FACING) : null;
    }

    public static DoubleBlockProperties.Type getDoubleBlockType(BlockState state) {
        ChestType chestType = (ChestType)state.get(Properties.CHEST_TYPE);
        if (chestType == ChestType.SINGLE) {
            return DoubleBlockProperties.Type.SINGLE;
        } else {
            return chestType == ChestType.RIGHT ? DoubleBlockProperties.Type.FIRST : DoubleBlockProperties.Type.SECOND;
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (neighborState.isOf(this) && direction.getAxis().isHorizontal()) {
            ChestType chestType = neighborState.get(Properties.CHEST_TYPE);
            if (state.get(Properties.CHEST_TYPE) == ChestType.SINGLE && chestType != ChestType.SINGLE && state.get(Properties.HORIZONTAL_FACING) == neighborState.get(Properties.HORIZONTAL_FACING) && getFacing(neighborState) == direction.getOpposite()) {
                return state.with(Properties.CHEST_TYPE, chestType.getOpposite());
            }
        } else if (getFacing(state) == direction) {
            return state.with(Properties.CHEST_TYPE, ChestType.SINGLE);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public static Direction getFacing(BlockState state) {
        Direction direction = state.get(Properties.HORIZONTAL_FACING);
        return state.get(Properties.CHEST_TYPE) == ChestType.LEFT ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        ChestType chestType = ChestType.SINGLE;
        Direction direction = ctx.getPlayerFacing().getOpposite();
        boolean bl = ctx.shouldCancelInteraction();
        Direction direction2 = ctx.getSide();
        if (direction2.getAxis().isHorizontal() && bl) {
            Direction direction3 = this.getNeighborChestDirection(ctx, direction2.getOpposite());
            if (direction3 != null && direction3.getAxis() != direction2.getAxis()) {
                direction = direction3;
                chestType = direction3.rotateYCounterclockwise() == direction2.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
            }
        }

        if (chestType == ChestType.SINGLE && !bl) {
            if (direction == this.getNeighborChestDirection(ctx, direction.rotateYClockwise())) {
                chestType = ChestType.LEFT;
            } else if (direction == this.getNeighborChestDirection(ctx, direction.rotateYCounterclockwise())) {
                chestType = ChestType.RIGHT;
            }
        }

        return getDefaultState().with(Properties.HORIZONTAL_FACING, direction).with(Properties.CHEST_TYPE, chestType);
    }

    public DoubleBlockProperties.PropertySource<? extends UnnaturalChestEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        BiPredicate biPredicate;
        if (ignoreBlocked) {
            biPredicate = (worldx, posx) -> false;
        } else {
            biPredicate = UnnaturalChest::isChestBlocked;
        }

        return DoubleBlockProperties.toPropertySource(BossmodBlockEntities.UNNATURAL_LARGE_CHEST_ENTITY, ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world, pos, biPredicate);
    }

    public static boolean isChestBlocked(Object o, Object o1) {
        return hasBlockOnTop((WorldAccess) o, (BlockPos) o1) || hasOcelotOnTop((WorldAccess) o, (BlockPos) o1);
    }

    //public static boolean isChestBlocked(WorldAccess world, BlockPos pos) {
        //return hasBlockOnTop(world, pos) || hasOcelotOnTop(world, pos);
    //}

    private static boolean hasBlockOnTop(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return world.getBlockState(blockPos).isSolidBlock(world, blockPos);
    }

    private static boolean hasOcelotOnTop(WorldAccess world, BlockPos pos) {
        List<CatEntity> list = world.getNonSpectatingEntities(CatEntity.class, new Box((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1)));
        if (!list.isEmpty()) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                CatEntity catEntity = (CatEntity)var3.next();
                if (catEntity.isInSittingPose()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return (NamedScreenHandlerFactory)((Optional)this.getBlockEntitySource(state, world, pos, false).apply(NAME_RETRIEVER)).orElse((Object)null);
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        CHEST_TYPE = Properties.CHEST_TYPE;
        INVENTORY_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<UnnaturalChestEntity, Optional<Inventory>>() {
            public Optional<Inventory> getFromBoth(UnnaturalChestEntity chestBlockEntity, UnnaturalChestEntity chestBlockEntity2) {
                return Optional.of(new DoubleInventory(chestBlockEntity, chestBlockEntity2));
            }

            public Optional<Inventory> getFrom(UnnaturalChestEntity chestBlockEntity) {
                return Optional.of(chestBlockEntity);
            }

            public Optional<Inventory> getFallback() {
                return Optional.empty();
            }
        };
        NAME_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<UnnaturalChestEntity, Optional<NamedScreenHandlerFactory>>() {
            public Optional<NamedScreenHandlerFactory> getFromBoth(UnnaturalChestEntity chestBlockEntity, UnnaturalChestEntity chestBlockEntity2) {
                final Inventory inventory = new DoubleInventory(chestBlockEntity, chestBlockEntity2);
                return Optional.of(new NamedScreenHandlerFactory() {
                    @Nullable
                    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        if (chestBlockEntity.checkUnlocked(playerEntity) && chestBlockEntity2.checkUnlocked(playerEntity)) {
                            chestBlockEntity.checkLootInteraction(playerInventory.player);
                            chestBlockEntity2.checkLootInteraction(playerInventory.player);
                            return new UnnaturalDoubleChestScreenHandler(i, playerInventory, inventory);
                        } else {
                            return null;
                        }
                    }

                    public Text getDisplayName() {
                        if (chestBlockEntity.hasCustomName()) {
                            return chestBlockEntity.getDisplayName();
                        } else {
                            return (chestBlockEntity2.hasCustomName() ? chestBlockEntity2.getDisplayName() : new TranslatableText("container.unnatural_large_chest.double"));
                        }
                    }
                });
            }

            public Optional<NamedScreenHandlerFactory> getFrom(UnnaturalChestEntity chestBlockEntity) {
                return Optional.of(chestBlockEntity);
            }

            public Optional<NamedScreenHandlerFactory> getFallback() {
                return Optional.empty();
            }
        };
    }
}
