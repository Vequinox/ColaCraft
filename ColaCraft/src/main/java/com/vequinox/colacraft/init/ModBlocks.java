package com.vequinox.colacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.blocks.BlockBase;
import com.vequinox.colacraft.blocks.BlockOre;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static BlockOre oreCinerium = new BlockOre("ore_cinerium", 3f, 5f, 0);
	public static BlockOre oreLuxium = new BlockOre("ore_luxium", 3f, 5f, 1);
	public static BlockOre oreAlbium = new BlockOre("ore_albium", 3f, 5f, 2);
	public static BlockOre oreCerasium = new BlockOre("ore_cerasium", 3f, 5f, 2);
	public static BlockOre oreTenebrium = new BlockOre("ore_tenebrium", 3f, 5f, 3);
}
