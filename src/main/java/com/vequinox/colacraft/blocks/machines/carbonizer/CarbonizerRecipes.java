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
		if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.SPECKLED_MELON) {
			return ModItems.RED_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.POISONOUS_POTATO) {
			return ModItems.YELLOW_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.ROTTEN_FLESH) {
			return ModItems.GREEN_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.FISH) {
			return ModItems.BLUE_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.COOKED_RABBIT) {
			return ModItems.WHITE_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.SPIDER_EYE) {
			return ModItems.BLACK_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.BEETROOT) {
			return ModItems.BROWN_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.LAVA_BUCKET) {
			return ModItems.ORANGE_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.FEATHER) {
			return ModItems.LIGHT_BLUE_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.SLIME_BALL) {
			return ModItems.MAGENTA_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.RABBIT_STEW) {
			return ModItems.PINK_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.GRAY.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.FERMENTED_SPIDER_EYE) {
			return ModItems.GREY_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.LIME.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.GOLDEN_CARROT) {
			return ModItems.LIME_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.CHICKEN) {
			return ModItems.CYAN_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.PURPLE.getDyeDamage()).getItem() && fillerIngredient.getItem() == Items.COOKIE) {
			return ModItems.PURPLE_SODA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.SILVER.getDyeDamage()).getItem() && fillerIngredient.getItem() == new ItemStack(Blocks.TNT).getItem()){
			return ModItems.LIGHT_GREY_SODA;
		}else {
			return null;
		}
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
	
	public ItemStack getCarbonizingResult(ItemStack baseIngredient, ItemStack fillerIngredient, ItemStack augmentIngredient, ItemStack modifierIngredient) {
		List<Potion> extraPotionEffects = new ArrayList<Potion>();
		
		ItemSoda baseSoda = getBaseSodaFromIngredients(baseIngredient, fillerIngredient);
		
		if(!augmentIngredient.isEmpty()){
			double percentage = augmentIngredient.getCount() / 64;
			
			if(percentage >= Math.random()) {
				extraPotionEffects.add(getPotionEffectFromAugment(augmentIngredient));
			}
		}
		
		int duration = 1200;
		int amplifier = 0;
		if(!modifierIngredient.isEmpty()) {
			Item modifier = modifierIngredient.getItem();
			
			if(modifier == Items.SUGAR) {
				amplifier = randInt(modifierIngredient.getCount());
			}else if(modifier == Items.REDSTONE) {
				duration = (randInt(modifierIngredient.getCount())+1)*1200;
			}else if(modifier == Items.GUNPOWDER) {
				//make pressurized
			}
		}
		
		if(duration > 1200 || amplifier > 0) {
			baseSoda.addPotionEffect(baseSoda.getBasePotionEffect(), duration, amplifier);
		}
		
		if(!extraPotionEffects.isEmpty()) {
			for(Potion effect : extraPotionEffects) {
				baseSoda.addPotionEffect(effect, 1200, 0);
			}
		}
		
		ItemStack soda = new ItemStack(baseSoda);
		return soda;
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
