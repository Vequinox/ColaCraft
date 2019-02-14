package com.vequinox.colacraft;

import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.proxy.CommonProxy;
import com.vequinox.colacraft.recipe.ModRecipes;
import com.vequinox.colacraft.util.Reference;
import com.vequinox.colacraft.util.handlers.RegistryHandler;
import com.vequinox.colacraft.world.ModWorldGen;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		ModRecipes.init();
		RegistryHandler.initRegistries(event);
	}

	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		
	}
}
