package com.vequinox.colacraft.blocks.machines.carbonizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.items.ItemSoda;

import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class CarbonizerRecipes {
	private static final CarbonizerRecipes INSTANCE = new CarbonizerRecipes();
	private final Map<List<ItemStack>, ItemStack> smeltingList = Maps.<List<ItemStack>, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static CarbonizerRecipes getInstance() {
		return INSTANCE;
	}
	
	private CarbonizerRecipes() {}
	
	private int randInt(int itemCount) {
		int min = 0;
		int max = 0;
		
		if(itemCount < 11) {
			max = 1;
		}else if(itemCount < 21) {
			max = 2;
		}else if(itemCount < 31) {
			min = 1;
			max = 3;
		}else if(itemCount < 41){
			min = 2;
			max = 4;
		}else if(itemCount < 51) {
			min = 4;
			max = 6;
		}else if(itemCount < 61) {
			min = 6;
			max = 8;
		}else {
			min = 8;
			max = 10;
		}
		
		return min + (int)(Math.random()*((max-min) + 1));
	}
	
	private ItemSoda getBaseSodaFromIngredients(ItemStack baseIngredient, ItemStack fillerIngredient) {
		return null;
	}
	
	private Potion getPotionEffectFromAugment(ItemStack augmentIngredient) {
//		if(augmentIngredient.getItem() == ModItems.BLACK_AUGMENT) {
//			return MobEffects.SLOWNESS;
//		}else if(augmentIngredient.getItem() == ModItems.BLUE_AUGMENT) {
//			return MobEffects.WATER_BREATHING;
//		}else if(augmentIngredient.getItem() == ModItems.BROWN_AUGMENT) {
//			return MobEffects.STRENGTH;
//		}else if(augmentIngredient.getItem() == ModItems.CYAN_AUGMENT) {
//			return MobEffects.NAUSEA;
//		}else if(augmentIngredient.getItem() == ModItems.GREEN_AUGMENT) {
//			return MobEffects.HUNGER;
//		}else if(augmentIngredient.getItem() == ModItems.GREY_AUGMENT) {
//			return MobEffects.BLINDNESS;
//		}else if(augmentIngredient.getItem() == ModItems.LIGHT_BLUE_AUGMENT) {
//			return MobEffects.HASTE;
//		}else if(augmentIngredient.getItem() == ModItems.LIGHT_GREY_AUGMENT) {
//			return MobEffects.WITHER;
//		}else if(augmentIngredient.getItem() == ModItems.LIME_AUGMENT) {
//			return MobEffects.NIGHT_VISION;
//		}else if(augmentIngredient.getItem() == ModItems.MAGENTA_AUGMENT) {
//			return MobEffects.MINING_FATIGUE;
//		}else if(augmentIngredient.getItem() == ModItems.ORANGE_AUGMENT) {
//			return MobEffects.FIRE_RESISTANCE;
//		}else if(augmentIngredient.getItem() == ModItems.PINK_AUGMENT) {
//			return MobEffects.REGENERATION;
//		}else if(augmentIngredient.getItem() == ModItems.PURPLE_AUGMENT) {
//			return MobEffects.WEAKNESS;
//		}else if(augmentIngredient.getItem() == ModItems.RED_AUGMENT) {
//			return MobEffects.INSTANT_HEALTH;
//		}else if(augmentIngredient.getItem() == ModItems.WHITE_AUGMENT) {
//			return MobEffects.SPEED;
//		}else if(augmentIngredient.getItem() == ModItems.YELLOW_AUGMENT) {
//			return MobEffects.POISON;
//		}else {
//			return null;
//		}
		
		return null;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Map<List<ItemStack>, ItemStack> getSmeltingList(){
		return this.smeltingList;
	}
	
	public float getCarbonizingExperience(ItemStack stack) {
		for(Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())){
				return ((Float)entry.getValue()).floatValue();
			}
		}
		
		return 0.0F;
	}
}
