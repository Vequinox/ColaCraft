package com.vequinox.colacraft.blocks;

import com.vequinox.colacraft.util.Reference;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockOre extends BlockBase{

	public BlockOre(String name, Float hardness, Float resistance) {
		super(name, Material.ROCK);
		setHardness(hardness);
		setResistance(resistance);
	}

}
