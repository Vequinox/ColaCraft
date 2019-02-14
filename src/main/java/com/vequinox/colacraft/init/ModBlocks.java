package com.vequinox.colacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.blocks.BlockBase;
import com.vequinox.colacraft.blocks.BlockOre;
import com.vequinox.colacraft.blocks.machines.carbonizer.BlockCarbonizer;
import com.vequinox.colacraft.blocks.machines.mixer.BlockMixer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static BlockOre ORE_ALUMINUM = new BlockOre("ore_aluminum", 3f, 5f, 2);
	
	public static Block CARBONIZER = new BlockCarbonizer("carbonizer");
	public static Block MIXER = new BlockMixer("mixer", 1);
}
