package com.vequinox.colacraft.recipe;

import com.vequinox.colacraft.init.ModBlocks;
import com.vequinox.colacraft.init.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	public static void init() {
		GameRegistry.addSmelting(ModBlocks.ORE_ALUMINUM, new ItemStack(ModItems.ALUMINUM_INGOT), 0.7f);
		GameRegistry.addSmelting(ModItems.CONDENSED_REDSTONE, new ItemStack(ModItems.CRYSTALLIZED_REDSTONE), 0.7f);
		GameRegistry.addSmelting(ModItems.CONDENSED_SUGAR, new ItemStack(ModItems.CRYSTALLIZED_SUGAR), 0.7f);
	}
}
