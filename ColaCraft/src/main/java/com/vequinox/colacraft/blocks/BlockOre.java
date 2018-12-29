package com.vequinox.colacraft.blocks;

import com.vequinox.colacraft.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockOre extends BlockBase{

	public BlockOre(String name, Float hardness, Float resistance, int harvestLevel) {
		super(name, Material.ROCK);
		setHardness(hardness);
		setResistance(resistance);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", harvestLevel);
	}

}
