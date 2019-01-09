package com.vequinox.colacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.items.ItemAugment;
import com.vequinox.colacraft.items.ItemBase;
import com.vequinox.colacraft.items.ItemSoda;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final Item CAN = new ItemBase("can");
	public static final Item SUGAR_WATER = new ItemBase("sugar_water");
	
	public static final Item ALBIUM_INGOT = new ItemBase("albium_ingot");
	public static final Item CERASIUM_INGOT = new ItemBase("cerasium_ingot");
	public static final Item CINERIUM_INGOT = new ItemBase("cinerium_ingot");
	public static final Item LUXIUM_INGOT = new ItemBase("luxium_ingot");
	public static final Item TENEBRIUM_INGOT = new ItemBase("tenebrium_ingot");
	
	public static final Item BLACK_AUGMENT = new ItemBase("black_augment");
	public static final Item WHITE_AUGMENT = new ItemBase("white_augment");
	public static final Item RED_AUGMENT = new ItemBase("red_augment");
	public static final Item BLUE_AUGMENT = new ItemBase("blue_augment");
	public static final Item GREEN_AUGMENT = new ItemBase("green_augment");
	public static final Item YELLOW_AUGMENT = new ItemBase("yellow_augment");
	public static final Item BROWN_AUGMENT = new ItemBase("brown_augment");
	public static final Item ORANGE_AUGMENT = new ItemBase("orange_augment");
	public static final Item CYAN_AUGMENT = new ItemBase("cyan_augment");
	public static final Item PURPLE_AUGMENT = new ItemBase("purple_augment");
	public static final Item GREY_AUGMENT = new ItemBase("grey_augment");
	public static final Item LIGHT_BLUE_AUGMENT = new ItemBase("light_blue_augment");
	public static final Item PINK_AUGMENT = new ItemBase("pink_augment");
	public static final Item LIME_AUGMENT = new ItemBase("lime_augment");
	public static final Item MAGENTA_AUGMENT = new ItemBase("magenta_augment");
	public static final Item LIGHT_GREY_AUGMENT = new ItemBase("light_grey_augment");
	
	public static final ItemSoda BLACK_SODA = new ItemSoda("black_soda", 2, 0.6F, false);
	public static final ItemSoda WHITE_SODA = new ItemSoda("white_soda", 2, 0.6F, false);
	public static final ItemSoda RED_SODA = new ItemSoda("red_soda", 2, 0.6F, false);
	public static final ItemSoda BLUE_SODA = new ItemSoda("blue_soda", 2, 0.6F, false);
	public static final ItemSoda GREEN_SODA = new ItemSoda("green_soda", 2, 0.6F, false);
	public static final ItemSoda YELLOW_SODA = new ItemSoda("yellow_soda", 2, 0.6F, false);
	public static final ItemSoda BROWN_SODA = new ItemSoda("brown_soda", 2, 0.6F, false);
	public static final ItemSoda ORANGE_SODA = new ItemSoda("orange_soda", 2, 0.6F, false);
	public static final ItemSoda CYAN_SODA = new ItemSoda("cyan_soda", 2, 0.6F, false);
	public static final ItemSoda PURPLE_SODA = new ItemSoda("purple_soda", 2, 0.6F, false);
	public static final ItemSoda GREY_SODA = new ItemSoda("grey_soda", 2, 0.6F, false);
	public static final ItemSoda LIGHT_BLUE_SODA = new ItemSoda("light_blue_soda", 2, 0.6F, false);
	public static final ItemSoda PINK_SODA = new ItemSoda("pink_soda", 2, 0.6F, false);
	public static final ItemSoda LIME_SODA = new ItemSoda("lime_soda", 2, 0.6F, false);
	public static final ItemSoda MAGENTA_SODA = new ItemSoda("magenta_soda", 2, 0.6F, false);
	public static final ItemSoda LIGHT_GREY_SODA = new ItemSoda("light_grey_soda", 2, 0.6F, false);
	
	public static void setBasePotionEffects() {
		int duration = 1200;
		int amplifier = 0;
		
		BLACK_SODA.addPotionEffect(MobEffects.SLOWNESS, duration, amplifier);
		WHITE_SODA.addPotionEffect(MobEffects.SPEED, duration, amplifier);
		RED_SODA.addPotionEffect(MobEffects.INSTANT_HEALTH, duration, amplifier);
		BLUE_SODA.addPotionEffect(MobEffects.WATER_BREATHING, duration, amplifier);
		GREEN_SODA.addPotionEffect(MobEffects.HUNGER, duration, amplifier);
		YELLOW_SODA.addPotionEffect(MobEffects.POISON, duration, amplifier);
		BROWN_SODA.addPotionEffect(MobEffects.STRENGTH, duration, amplifier);
		ORANGE_SODA.addPotionEffect(MobEffects.FIRE_RESISTANCE, duration, amplifier);
		CYAN_SODA.addPotionEffect(MobEffects.NAUSEA, duration, amplifier);
		PURPLE_SODA.addPotionEffect(MobEffects.WEAKNESS, duration, amplifier);
		GREY_SODA.addPotionEffect(MobEffects.BLINDNESS, duration, amplifier);
		LIGHT_BLUE_SODA.addPotionEffect(MobEffects.HASTE, duration, amplifier);
		PINK_SODA.addPotionEffect(MobEffects.REGENERATION, duration, amplifier);
		LIME_SODA.addPotionEffect(MobEffects.NIGHT_VISION, duration, amplifier);
		MAGENTA_SODA.addPotionEffect(MobEffects.MINING_FATIGUE, duration, amplifier);
		LIGHT_GREY_SODA.addPotionEffect(MobEffects.WITHER, duration, amplifier);
	}
}
