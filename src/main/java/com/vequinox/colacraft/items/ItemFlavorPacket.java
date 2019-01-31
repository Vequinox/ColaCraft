package com.vequinox.colacraft.items;

import com.vequinox.colacraft.init.ModItems;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class ItemFlavorPacket extends ItemBase{
	private Potion effect;

	public ItemFlavorPacket(String name, Potion effect) {
		super(name);
		this.effect = effect;
	}
	
	public ResourceLocation getEffect() {
		return this.effect.getRegistryName();
	}

}
