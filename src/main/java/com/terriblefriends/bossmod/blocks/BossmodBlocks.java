package com.terriblefriends.bossmod.blocks;

import com.terriblefriends.bossmod.items.BossmodItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.registry.Registry;

import java.util.function.ToIntFunction;

public class BossmodBlocks {
    //petrified oak, birch; corrupted spruce, acacia, nylium; wither wood; metalic jungle,

    public static final Block OverclockedFurnace = new OverclockedFurnace(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.5f).luminance(createLightLevelFromLitBlockState(13)));
    public static final Block SuperBrewingStand = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(0.5F).luminance((state) -> 1).nonOpaque());
    public static final Block UnnaturalLargeChest = new UnnaturalChest(FabricBlockSettings.of(Material.WOOD).strength(2.5F).sounds(BlockSoundGroup.WOOD));
    public static final Block CorruptedSpruceButton = new Block(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON));
    //public static final Block CorruptedSpruceDoor = new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR));
    public static final Block CorruptedSpruceFence = new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE));
    public static final Block CorruptedSpruceFenceGate = new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE));
    public static final Block CorruptedSpruceLeaves = new Block(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES));
    public static final Block CorruptedSpruceLog = new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG));
    public static final Block CorruptedSprucePlanks = new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS));
    //public static final Block CorruptedSprucePressurePlate = new PressurePlateBlock(FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE));
    //public static final Block CorruptedSpruceSapling = new SaplingBlock(FabricBlockSettings.copyOf(Blocks.OAK_SAPLING));
    public static final Block CorruptedSpruceSign = new Block(FabricBlockSettings.copyOf(Blocks.OAK_SIGN));
    public static final Block CorruptedSpruceSlab = new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB));
    //public static final Block CorruptedSpruceStairs = new StairsBlock(FabricBlockSettings.copyOf(Blocks.OAK_STAIRS));
    //public static final Block CorruptedSpruceTrapdoor = new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR));
    public static final Block CorruptedSpruceWallSign = new WallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN), SignType.OAK);
    //public static final Block CorruptedSpruceWood = new createLogBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD));


    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> (Boolean)state.get(Properties.LIT) ? litLevel : 0;
    }

    public static void register() {
        registerBlocks();
        registerItems();

    }

    private static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier("bossmod","overclocked_furnace"), OverclockedFurnace);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","super_brewing_stand"), SuperBrewingStand);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","unnatural_large_chest"), UnnaturalLargeChest);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_button"), CorruptedSpruceButton);
        //Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_door"), CorruptedSpruceDoor);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_fence"), CorruptedSpruceFence);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_fence_gate"), CorruptedSpruceFenceGate);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_leaves"), CorruptedSpruceLeaves);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_log"), CorruptedSpruceLog);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_planks"), CorruptedSprucePlanks);
        //.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_pressure_plate"), CorruptedSprucePressurePlate);
        //Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_sapling"), CorruptedSpruceSapling);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_sign"), CorruptedSpruceSign);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_slab"), CorruptedSpruceSlab);
        //Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_stairs"), CorruptedSpruceStairs);
        //Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_trapdoor"), CorruptedSpruceTrapdoor);
        Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_wall_sign"), CorruptedSpruceWallSign);
        //Registry.register(Registry.BLOCK, new Identifier("bossmod","corrupted_spruce_wood"), CorruptedSpruceWood);
    }

    private static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier("bossmod","overclocked_furnace"), new BlockItem(OverclockedFurnace, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","super_brewing_stand"), new BlockItem(SuperBrewingStand, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","unnatural_large_chest"), new BlockItem(UnnaturalLargeChest, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_button"), new BlockItem(CorruptedSpruceButton, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_fence"), new BlockItem(CorruptedSpruceFence, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_fence_gate"), new BlockItem(CorruptedSpruceFenceGate, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_leaves"), new BlockItem(CorruptedSpruceLeaves, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_log"), new BlockItem(CorruptedSpruceLog, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_planks"), new BlockItem(CorruptedSprucePlanks, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        //Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_pressure_plate"), new BlockItem(CorruptedSprucePressurePlate, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        //Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_sapling"), new BlockItem(CorruptedSpruceSapling, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_sign"), new BlockItem(CorruptedSpruceSign, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_slab"), new BlockItem(CorruptedSpruceSlab, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        //Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_stairs"), new BlockItem(CorruptedSpruceStairs, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        //Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_trapdoor"), new BlockItem(CorruptedSpruceTrapdoor, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
        //Registry.register(Registry.ITEM, new Identifier("bossmod","corrupted_spruce_wood"), new BlockItem(CorruptedSpruceWood, new FabricItemSettings().group(BossmodItemGroups.BOSSMOD_GENERAL_ITEMS)));
    }
}
