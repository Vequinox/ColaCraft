package com.vequinox.colacraft.items;

import com.vequinox.colacraft.init.ModItems;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class ItemFlavorPacket extends ItemBase{
	private String name;

	public ItemFlavorPacket(String name) {
		super(name);
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

}
