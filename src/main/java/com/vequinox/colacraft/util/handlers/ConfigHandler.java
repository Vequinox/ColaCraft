package com.vequinox.colacraft.util.handlers;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.util.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.tools.nsc.backend.icode.TypeKinds;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;

    public static int GUI_CARBONIZER = 0;
    public static int GUI_MIXER = 1;

    public static boolean spawnAluminumOre;

    public static void init(File file){
        config = new Configuration(file);

        String category;

        category = "IDs";
        config.addCustomCategoryComment(category, "Set IDs for each GUI");
        GUI_CARBONIZER = config.getInt("Carbonizer GUI ID", category, 0, 0, 100, "ID for the Carbonizer GUI");
        GUI_MIXER = config.getInt("Mixer GUI ID", category, 1, 0, 100, "ID for the Mixer GUI");
        spawnAluminumOre = config.getBoolean("Spawn Aluminum Ore in Overworld", category, true, "Declare whether you wish Aluminum Ore from Cola Craft spawns in the Overworld");

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event){
        Main.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
        Main.config.mkdirs();
        init(new File(Main.config.getPath(), Reference.MOD_ID + ".cfg"));
    }
}
