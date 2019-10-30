package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.common.block.*;
import com.github.mnesikos.samhain.common.block.trees.CrimsonKingMapleTree;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks extends ModRegistry<Block> {
    public static final List<Item> ITEM_BLOCKS = new ArrayList<>();

    public static final ModLogBlock CRIMSON_KING_MAPLE_LOG = new ModLogBlock("crimson_king_maple_log", MaterialColor.WOOD, Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    public static final ModWoodBlock CRIMSON_KING_MAPLE_WOOD = new ModWoodBlock("crimson_king_maple_wood", Block.Properties.from(CRIMSON_KING_MAPLE_LOG));
    public static final ModLogBlock STRIPPED_CRIMSON_KING_MAPLE_LOG = new ModLogBlock("stripped_crimson_king_maple_log", MaterialColor.WOOD, Block.Properties.from(CRIMSON_KING_MAPLE_LOG));
    public static final ModWoodBlock STRIPPED_CRIMSON_KING_MAPLE_WOOD = new ModWoodBlock("stripped_crimson_king_maple_wood", Block.Properties.from(CRIMSON_KING_MAPLE_LOG));
    public static final ModLeavesBlock CRIMSON_KING_MAPLE_LEAVES = new ModLeavesBlock("crimson_king_maple_leaves", Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT));
    public static final ModSaplingBlock CRIMSON_KING_MAPLE_SAPLING = new ModSaplingBlock("crimson_king_maple_sapling", new CrimsonKingMapleTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
    public static final ModBlock CRIMSON_KING_MAPLE_PLANKS = new ModBlock("crimson_king_maple_planks", Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final ModStairsBlock CRIMSON_KING_MAPLE_STAIRS = new ModStairsBlock("crimson_king_maple_stairs", CRIMSON_KING_MAPLE_PLANKS::getDefaultState, Block.Properties.from(CRIMSON_KING_MAPLE_PLANKS));
    public static final ModSlabBlock CRIMSON_KING_MAPLE_SLAB = new ModSlabBlock("crimson_king_maple_slab", Block.Properties.from(CRIMSON_KING_MAPLE_PLANKS));
    //public static final ModDoorBlock CRIMSON_KING_MAPLE_DOOR_1 = new ModDoorBlock("crimson_king_maple_door_1", Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD));
    //public static final ModDoorBlock CRIMSON_KING_MAPLE_DOOR_2 = new ModDoorBlock("crimson_king_maple_door_2", Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD));
    //public static final ModBlock CRIMSON_KING_MAPLE_SIGN = new ModBlock("", );
    //public static final ModBlock CRIMSON_KING_MAPLE_WALL_SIGN = new ModBlock("", );
    //public static final ModBlock CRIMSON_KING_MAPLE_PRESSURE_PLATE = new ModBlock("", );
    //public static final ModBlock CRIMSON_KING_MAPLE_FENCE = new ModBlock("", );
    //public static final ModBlock CRIMSON_KING_MAPLE_TRAPDOOR = new ModBlock("", );
    //public static final ModBlock CRIMSON_KING_MAPLE_FENCE_GATE = new ModBlock("", );
    //public static final ModBlock CRIMSON_KING_MAPLE_BUTTON = new ModBlock("", );
    public static final ModBlock OTHERWORLD_PORTAL = new OtherworldPortalBlock();
    //public static final GravestoneBlock GRAVESTONE_1 = new GravestoneBlock("gravestone_1");
    //public static final GravestoneBlock GRAVESTONE_2 = new GravestoneBlock("gravestone_2");

    public static void registerItemBlocks(List<Item> registry) {
        registry.addAll(ITEM_BLOCKS);
    }
}
