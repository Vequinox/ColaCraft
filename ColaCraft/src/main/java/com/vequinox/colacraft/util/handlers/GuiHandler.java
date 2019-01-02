package com.vequinox.colacraft.util.handlers;

import com.vequinox.colacraft.blocks.machines.carbonizer.ContainerCarbonizer;
import com.vequinox.colacraft.blocks.machines.carbonizer.GuiCarbonizer;
import com.vequinox.colacraft.blocks.machines.carbonizer.TileEntityCarbonizer;
import com.vequinox.colacraft.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.GUI_CARBONIZER) {
			return new ContainerCarbonizer(player.inventory, (TileEntityCarbonizer)world.getTileEntity(new BlockPos(x,y,z)));
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.GUI_CARBONIZER) {
			return new GuiCarbonizer(player.inventory, (TileEntityCarbonizer)world.getTileEntity(new BlockPos(x,y,z)));
		}
		
		return null;
	}
}
