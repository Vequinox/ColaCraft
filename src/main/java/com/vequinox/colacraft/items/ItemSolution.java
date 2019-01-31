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
	
	private List<PotionEffectWrapper> potionEffects = new ArrayList<PotionEffectWrapper>();
	private Potion basePotionEffect;
	private int waterParts;
	private int maxPowderAmount;

	public ItemSolution(String name, int waterParts, int maxPowderAmount) {
		super(name);
		ModItems.ITEMS.add(this);
		this.waterParts = waterParts;
		this.maxPowderAmount = maxPowderAmount;
	}
	
	public ItemBase addPotionEffect(Potion effect, int duration, int amplifier) {
		if(potionEffects.isEmpty()) {
			this.basePotionEffect = effect;
		}
		this.potionEffects.add(new PotionEffectWrapper(new PotionEffect(effect, duration, amplifier)));
        return this;
    }
	
	public ItemBase addPotionEffect(Potion effect) {
		return addPotionEffect(effect, 200, 0);
	}
	
	public int numberOfPotionEffects() {
		return potionEffects.size();
	}
	
	public Potion getBasePotionEffect() {
		return this.basePotionEffect;
	}
	
	public int getStartingWaterParts() {
		return this.waterParts;
	}
	
	public int getMaxPowderParts() {
		return this.maxPowderAmount;
	}

}
