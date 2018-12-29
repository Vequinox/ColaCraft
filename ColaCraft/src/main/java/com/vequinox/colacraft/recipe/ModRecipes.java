package com.vequinox.colacraft.recipe;

import com.vequinox.colacraft.init.ModBlocks;
import com.vequinox.colacraft.init.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	public static void init() {
		GameRegistry.addSmelting(ModBlocks.oreCinerium, new ItemStack(ModItems.CINERIUM_INGOT), 0.7f);
		GameRegistry.addSmelting(ModBlocks.oreLuxium, new ItemStack(ModItems.LUXIUM_INGOT), 0.7f);
		GameRegistry.addSmelting(ModBlocks.oreAlbium, new ItemStack(ModItems.ALBIUM_INGOT), 0.7f);
		GameRegistry.addSmelting(ModBlocks.oreCerasium, new ItemStack(ModItems.CERASIUM_INGOT), 0.7f);
		GameRegistry.addSmelting(ModBlocks.oreTenebrium, new ItemStack(ModItems.TENEBRIUM_INGOT), 0.7f);
	}
}
