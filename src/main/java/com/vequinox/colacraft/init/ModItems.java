package com.vequinox.colacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.items.ItemFlavorPacket;
import com.vequinox.colacraft.items.ItemBase;
import com.vequinox.colacraft.items.ItemSoda;

import com.vequinox.colacraft.items.ItemSolution;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final Item CAN = new ItemBase("can");
	public static final Item BASE_SOLUTION = new ItemSolution("base_solution", 3, 70);
	public static final Item SODA = new ItemSoda("soda", 2, 0.6F, false);
	
	public static final Item ALUMINUM_INGOT = new ItemBase("aluminum_ingot");
	
	public static final ItemFlavorPacket BLACK_FLAVOR_PACKET = new ItemFlavorPacket("black_flavor_packet", MobEffects.SLOWNESS);
	public static final ItemFlavorPacket WHITE_FLAVOR_PACKET = new ItemFlavorPacket("white_flavor_packet", MobEffects.SPEED);
	public static final ItemFlavorPacket RED_FLAVOR_PACKET = new ItemFlavorPacket("red_flavor_packet", MobEffects.INSTANT_HEALTH);
	public static final ItemFlavorPacket BLUE_FLAVOR_PACKET = new ItemFlavorPacket("blue_flavor_packet", MobEffects.WATER_BREATHING);
	public static final ItemFlavorPacket GREEN_FLAVOR_PACKET = new ItemFlavorPacket("green_flavor_packet", MobEffects.HUNGER);
	public static final ItemFlavorPacket YELLOW_FLAVOR_PACKET = new ItemFlavorPacket("yellow_flavor_packet", MobEffects.POISON);
	public static final ItemFlavorPacket BROWN_FLAVOR_PACKET = new ItemFlavorPacket("brown_flavor_packet", MobEffects.STRENGTH);
	public static final ItemFlavorPacket ORANGE_FLAVOR_PACKET = new ItemFlavorPacket("orange_flavor_packet", MobEffects.FIRE_RESISTANCE);
	public static final ItemFlavorPacket CYAN_FLAVOR_PACKET = new ItemFlavorPacket("cyan_flavor_packet", MobEffects.NAUSEA);
	public static final ItemFlavorPacket PURPLE_FLAVOR_PACKET = new ItemFlavorPacket("purple_flavor_packet", MobEffects.WEAKNESS);
	public static final ItemFlavorPacket GRAY_FLAVOR_PACKET = new ItemFlavorPacket("gray_flavor_packet", MobEffects.BLINDNESS);
	public static final ItemFlavorPacket LIGHT_BLUE_FLAVOR_PACKET = new ItemFlavorPacket("light_blue_flavor_packet", MobEffects.HASTE);
	public static final ItemFlavorPacket PINK_FLAVOR_PACKET = new ItemFlavorPacket("pink_flavor_packet", MobEffects.REGENERATION);
	public static final ItemFlavorPacket LIME_FLAVOR_PACKET = new ItemFlavorPacket("lime_flavor_packet", MobEffects.NIGHT_VISION);
	public static final ItemFlavorPacket MAGENTA_FLAVOR_PACKET = new ItemFlavorPacket("magenta_flavor_packet", MobEffects.MINING_FATIGUE);
	public static final ItemFlavorPacket LIGHT_GRAY_FLAVOR_PACKET = new ItemFlavorPacket("light_gray_flavor_packet", MobEffects.WITHER);
}
