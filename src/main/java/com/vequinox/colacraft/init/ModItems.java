package com.vequinox.colacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.items.*;

import net.minecraft.item.Item;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();

	public static final Item CAN = new ItemCan("can", 200, 0);
	public static final Item GOLD_CAN = new ItemCan("gold_can", 300, 0);
	public static final Item HOLY_CAN = new ItemCan("holy_can", 400, 0);
	public static final Item DEMONIC_CAN = new ItemCan("demonic_can", 200, 1);
	public static final Item ANCIENT_CAN = new ItemCan("ancient_can", 1000, 0);

	public static final ItemSoda SODA = new ItemSoda("soda", 2, 0.6F, false);
	public static final ItemSoda GOLD_SODA = new ItemSoda("gold_soda", 4, 1.2F, false);
	public static final ItemSoda HOLY_SODA = new ItemSoda("holy_soda", 6, 1.6F, false);
	public static final ItemSoda DEMONIC_SODA = new ItemSoda("demonic_soda", 8, -0.6F, false);
	public static final ItemSoda ANCIENT_SODA = new ItemSoda("ancient_soda", 0, 0.0F, false);

	public static final Item EMPTY_SMALL_VIAL = new ItemVial("empty_small_vial");
	public static final Item EMPTY_VIAL = new ItemVial("empty_vial");
	public static final Item EMPTY_LARGE_VIAL = new ItemVial("empty_large_vial");
	public static final Item SMALL_VIAL_WATER = new ItemSolution("small_vial_water", 3, 70);
	public static final Item VIAL_WATER = new ItemSolution("vial_water", 5, 100);
	public static final Item LARGE_VIAL_WATER = new ItemSolution("large_vial_water", 7, 200);
	
	public static final Item ALUMINUM_INGOT = new ItemBase("aluminum_ingot");

	public static final Item ELECTRODUST = new ItemBase("electrodust");

	public static final Item HYDRODUST = new ItemBase("hydrodust");
	public static final Item GHOST_HYDRODUST = new ItemBase("ghost_hydrodust");

	public static final Item CONDENSED_SUGAR = new ItemBase("condensed_sugar");
	public static final Item CRYSTALLIZED_SUGAR = new ItemBase("crystallized_sugar");
	public static final Item GHOST_SUGAR = new ItemBase("ghost_sugar");

	public static final Item CONDENSED_REDSTONE = new ItemBase("condensed_redstone");
	public static final Item CRYSTALLIZED_REDSTONE = new ItemBase("crystallized_redstone");
	public static final Item GHOST_REDSTONE = new ItemBase("ghost_redstone");
	
	public static final ItemFlavorPacket BLACK_FLAVOR_PACKET = new ItemFlavorPacket("black_flavor_packet");
	public static final ItemFlavorPacket WHITE_FLAVOR_PACKET = new ItemFlavorPacket("white_flavor_packet");
	public static final ItemFlavorPacket RED_FLAVOR_PACKET = new ItemFlavorPacket("red_flavor_packet");
	public static final ItemFlavorPacket BLUE_FLAVOR_PACKET = new ItemFlavorPacket("blue_flavor_packet");
	public static final ItemFlavorPacket GREEN_FLAVOR_PACKET = new ItemFlavorPacket("green_flavor_packet");
	public static final ItemFlavorPacket YELLOW_FLAVOR_PACKET = new ItemFlavorPacket("yellow_flavor_packet");
	public static final ItemFlavorPacket BROWN_FLAVOR_PACKET = new ItemFlavorPacket("brown_flavor_packet");
	public static final ItemFlavorPacket ORANGE_FLAVOR_PACKET = new ItemFlavorPacket("orange_flavor_packet");
	public static final ItemFlavorPacket CYAN_FLAVOR_PACKET = new ItemFlavorPacket("cyan_flavor_packet");
	public static final ItemFlavorPacket PURPLE_FLAVOR_PACKET = new ItemFlavorPacket("purple_flavor_packet");
	public static final ItemFlavorPacket GRAY_FLAVOR_PACKET = new ItemFlavorPacket("gray_flavor_packet");
	public static final ItemFlavorPacket LIGHT_BLUE_FLAVOR_PACKET = new ItemFlavorPacket("light_blue_flavor_packet");
	public static final ItemFlavorPacket PINK_FLAVOR_PACKET = new ItemFlavorPacket("pink_flavor_packet");
	public static final ItemFlavorPacket LIME_FLAVOR_PACKET = new ItemFlavorPacket("lime_flavor_packet");
	public static final ItemFlavorPacket MAGENTA_FLAVOR_PACKET = new ItemFlavorPacket("magenta_flavor_packet");
	public static final ItemFlavorPacket LIGHT_GRAY_FLAVOR_PACKET = new ItemFlavorPacket("light_gray_flavor_packet");

	public static final ItemFlavorPacket INSTANT_DAMAGE_FLAVOR_PACKET = new ItemFlavorPacket("instant_damage_flavor_packet");
	public static final ItemFlavorPacket JUMP_BOOST_FLAVOR_PACKET = new ItemFlavorPacket("jump_boost_flavor_packet");
	public static final ItemFlavorPacket RESISTANCE_FLAVOR_PACKET = new ItemFlavorPacket("resistance_flavor_packet");
	public static final ItemFlavorPacket INVISIBILITY_FLAVOR_PACKET = new ItemFlavorPacket("invisibility_flavor_packet");
	public static final ItemFlavorPacket HEALTH_BOOST_FLAVOR_PACKET = new ItemFlavorPacket("health_boost_flavor_packet");
	public static final ItemFlavorPacket ABSORPTION_FLAVOR_PACKET = new ItemFlavorPacket("absorption_flavor_packet");
	public static final ItemFlavorPacket SATURATION_FLAVOR_PACKET = new ItemFlavorPacket("saturation_flavor_packet");
	public static final ItemFlavorPacket GLOWING_FLAVOR_PACKET = new ItemFlavorPacket("glowing_flavor_packet");
	public static final ItemFlavorPacket LEVITATION_FLAVOR_PACKET = new ItemFlavorPacket("levitation_flavor_packet");
	public static final ItemFlavorPacket LUCK_FLAVOR_PACKET = new ItemFlavorPacket("luck_flavor_packet");
	public static final ItemFlavorPacket UNLUCK_FLAVOR_PACKET = new ItemFlavorPacket("unluck_flavor_packet");
}
