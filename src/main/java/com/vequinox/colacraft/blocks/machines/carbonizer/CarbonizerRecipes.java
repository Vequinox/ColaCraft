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
	
	private CarbonizerRecipes() {
//		addCarbonizingRecipe(
//				new ItemStack(Items.CARROT),
//				new ItemStack(Items.APPLE),
//				new ItemStack(ModItems.ALBIUM_INGOT),
//				new ItemStack(Items.BAKED_POTATO),
//				4.0F
//		);
		
		//Ink Sac + Spider Eye + Mushroom + Modifier
//		addCarbonizerRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()),
//				new ItemStack(Items.SPIDER_EYE),
//				new ItemStack(Blocks.BROWN_MUSHROOM),
//				new ItemStack);
	}
	
	private int randInt(int itemCount) {
		int min = 0;
		int max = 0;
		
		if(itemCount < 17) {
			max = 1;
		}else if(itemCount < 33) {
			max = 2;
		}else if(itemCount < 49) {
			min = 1;
			max = 3;
		}else {
			min = 2;
			max = 4;
		}
		
		return min + (int)(Math.random()*((max-min) + 1));
	}
	
	private Potion getPotionEffectFromIngredients(ItemStack baseIngredient, ItemStack fillerIngredient) {
		if((baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()).getItem()
				&& fillerIngredient.getItem() == Items.SPECKLED_MELON) /* TODO || baseIngredient.getItem() == augment item*/) {
			return MobEffects.INSTANT_HEALTH;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage()).getItem()
				&& fillerIngredient.getItem() == Items.POISONOUS_POTATO) {
			return MobEffects.POISON;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()).getItem()
				&& fillerIngredient.getItem() == Items.ROTTEN_FLESH) {
			return MobEffects.HUNGER;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()).getItem()
				&& fillerIngredient.getItem() == Items.FISH) {
			return MobEffects.WATER_BREATHING;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()).getItem()
				&& fillerIngredient.getItem() == Items.COOKED_RABBIT) {
			return MobEffects.SPEED;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()).getItem()
				&& fillerIngredient.getItem() == Items.SPIDER_EYE) {
			return MobEffects.SLOWNESS;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()).getItem()
				&& fillerIngredient.getItem() == Items.BEETROOT) {
			return MobEffects.STRENGTH;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage()).getItem()) {
			return MobEffects.FIRE_RESISTANCE;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()).getItem()) {
			return MobEffects.HASTE;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()).getItem()) {
			return MobEffects.MINING_FATIGUE;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage()).getItem()) {
			return MobEffects.REGENERATION;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.GRAY.getDyeDamage()).getItem()) {
			return MobEffects.BLINDNESS;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.LIME.getDyeDamage()).getItem()) {
			return MobEffects.NIGHT_VISION;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage()).getItem()) {
			return MobEffects.NAUSEA;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.PURPLE.getDyeDamage()).getItem()) {
			return MobEffects.WEAKNESS;
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.SILVER.getDyeDamage()).getItem()) {
			return MobEffects.WITHER;
		}else {
			return null;
		}
	}
	
	private String getSodaNameFromBaseIngredient(ItemStack baseIngredient) {
		if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()).getItem()) {
			return "Red Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage()).getItem()) {
			return "Yellow Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()).getItem()) {
			return "Green Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()).getItem()) {
			return "Blue Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()).getItem()) {
			return "White Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()).getItem()) {
			return "Black Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()).getItem()) {
			return "Brown Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage()).getItem()) {
			return "Orange Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()).getItem()) {
			return "Light Blue Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()).getItem()) {
			return "Magenta Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage()).getItem()) {
			return "Pink Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.GRAY.getDyeDamage()).getItem()) {
			return "Grey Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.LIME.getDyeDamage()).getItem()) {
			return "Lime Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage()).getItem()) {
			return "Cyan Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.PURPLE.getDyeDamage()).getItem()) {
			return "Purple Soda";
		}else if(baseIngredient.getItem() == new ItemStack(Items.DYE, 1, EnumDyeColor.SILVER.getDyeDamage()).getItem()) {
			return "Light Grey Soda";
		}else {
			return null;
		}
	}
	
	private Potion getPotionEffectFromAugment(ItemStack augment) {
		return getPotionEffectFromIngredients(augment, null);
	}
	
	public ItemSoda getCarbonizingResult(ItemStack baseIngredient, ItemStack fillerIngredient, ItemStack augmentIngredient, ItemStack modifierIngredient) {
		List<Potion> potionEffects = new ArrayList<Potion>();
		
		Potion effect = getPotionEffectFromIngredients(baseIngredient, fillerIngredient);
		if(effect != null) {
			ItemSoda soda = new ItemSoda(getSodaNameFromBaseIngredient(baseIngredient), 1, 0.6F, false);
			potionEffects.add(effect);
			
			if(!augmentIngredient.isEmpty()){ // TODO && is a correct augment item) {
				double percentage = augmentIngredient.getCount() / 64;
				
				if(percentage >= Math.random()) {
					potionEffects.add(getPotionEffectFromAugment(augmentIngredient));
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
			
			return (ItemSoda)soda.addPotionEffects(potionEffects, duration, amplifier);
		}else {
			return null;
		}
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
