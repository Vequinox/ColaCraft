package com.vequinox.colacraft.items;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.util.PotionEffectWrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSolution extends ItemBase{

	private int waterParts;
	private int maxPowderAmount;

	public ItemSolution(String name, int waterParts, int maxPowderAmount) {
		super(name);
		ModItems.ITEMS.add(this);
		this.waterParts = waterParts;
		this.maxPowderAmount = maxPowderAmount;
	}
	
	public int getStartingWaterParts() {
		return this.waterParts;
	}
	
	public int getMaxPowderParts() {
		return this.maxPowderAmount;
	}

}
