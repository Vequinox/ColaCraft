package com.vequinox.colacraft.util.handlers;

import com.vequinox.colacraft.blocks.machines.carbonizer.TileEntityCarbonizer;
import com.vequinox.colacraft.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityCarbonizer.class, new ResourceLocation(Reference.MOD_ID + ":carbonizer"));
	}
}
