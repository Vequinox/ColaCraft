package com.vequinox.colacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.blocks.BlockBase;
import com.vequinox.colacraft.blocks.BlockOre;
import com.vequinox.colacraft.blocks.machines.carbonizer.BlockCarbonizer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static BlockOre ORE_CINERIUM = new BlockOre("ore_cinerium", 3f, 5f, 0);
	public static BlockOre ORE_LUXIUM = new BlockOre("ore_luxium", 3f, 5f, 1);
	public static BlockOre ORE_ALBIUM = new BlockOre("ore_albium", 3f, 5f, 2);
	public static BlockOre ORE_CERASIUM = new BlockOre("ore_cerasium", 3f, 5f, 2);
	public static BlockOre ORE_TENEBRIUM = new BlockOre("ore_tenebrium", 3f, 5f, 3);
	
	public static Block CARBONIZER = new BlockCarbonizer("carbonizer", 200);
	public static Block CARBONIZER_TIER_2 = new BlockCarbonizer("carbonizer_tier_2", 100);
	public static Block CARBONIZER_TIER_3 = new BlockCarbonizer("carbonizer_tier_3", 50);
}
