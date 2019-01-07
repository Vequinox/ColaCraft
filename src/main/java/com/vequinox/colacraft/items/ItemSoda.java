package com.vequinox.colacraft.items;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.util.IHasModel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSoda extends ItemFood implements IHasModel{
	
	private List<PotionEffect> potionEffects = new ArrayList<PotionEffect>();

	public ItemSoda(String name, int hunger, float saturation, boolean isWolfFood) {
		super(hunger, saturation, isWolfFood);
		setAlwaysEdible();
		setTranslationKey(name);
		setRegistryName(name);
		ModItems.ITEMS.add(this);
	}
	
	public ItemFood addPotionEffects(List<Potion> effects, int duration, int amplifier) {
		for(Potion effect : effects) {
			this.potionEffects.add(new PotionEffect(effect, duration, amplifier));
		}
        return this;
    }
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		System.out.println("POTION EFFECTS: " + potionEffects.toString());
		
		if (!worldIn.isRemote && !this.potionEffects.isEmpty()){
			for(PotionEffect effect : potionEffects) {
				player.addPotionEffect(new PotionEffect(effect));
			}
        }
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
