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
	
	public static BlockOre ORE_ALUMINUM = new BlockOre("ore_aluminum", 3f, 5f, 2);
	
	public static Block CARBONIZER = new BlockCarbonizer("carbonizer", 200);
	public static Block CARBONIZER_TIER_2 = new BlockCarbonizer("carbonizer_tier_2", 100);
	public static Block CARBONIZER_TIER_3 = new BlockCarbonizer("carbonizer_tier_3", 50);
}
