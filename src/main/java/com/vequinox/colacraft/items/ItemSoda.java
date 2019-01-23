package com.vequinox.colacraft.items;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.util.IHasModel;
import com.vequinox.colacraft.util.PotionEffectWrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSoda extends ItemFood implements IHasModel{
	
	private List<PotionEffectWrapper> potionEffects = new ArrayList<PotionEffectWrapper>();
	private String name;
	private int hunger;
	private float saturation;
	private boolean isWolfFood;
	
	private Potion basePotionEffect;

	public ItemSoda(String name, int hunger, float saturation, boolean isWolfFood) {
		super(hunger, saturation, isWolfFood);
		setAlwaysEdible();
		setTranslationKey(name);
		setRegistryName(name);
		ModItems.ITEMS.add(this);
	}
	
	public ItemFood addPotionEffect(Potion effect, int duration, int amplifier) {
		if(potionEffects.isEmpty()) {
			this.basePotionEffect = effect;
		}
		this.potionEffects.add(new PotionEffectWrapper(new PotionEffect(effect, duration, amplifier)));
        return this;
    }
	
	public int numberOfPotionEffects() {
		return potionEffects.size();
	}
	
	public Potion getBasePotionEffect() {
		return this.basePotionEffect;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		System.out.println("POTION EFFECTS: " + this.potionEffects.toString());
		
		if (!worldIn.isRemote && !this.potionEffects.isEmpty()){
			for(PotionEffectWrapper effectWrapper : potionEffects) {
				player.addPotionEffect(new PotionEffect(effectWrapper.getPotionEffect()));
			}
        }
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
