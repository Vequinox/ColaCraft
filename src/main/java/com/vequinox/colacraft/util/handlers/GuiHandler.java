package com.vequinox.colacraft.util.handlers;

import com.vequinox.colacraft.blocks.machines.carbonizer.ContainerCarbonizer;
import com.vequinox.colacraft.blocks.machines.carbonizer.GuiCarbonizer;
import com.vequinox.colacraft.blocks.machines.carbonizer.TileEntityCarbonizer;
import com.vequinox.colacraft.blocks.machines.mixer.ContainerMixer;
import com.vequinox.colacraft.blocks.machines.mixer.GuiMixer;
import com.vequinox.colacraft.blocks.machines.mixer.TileEntityMixer;
import com.vequinox.colacraft.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
		
		if(tileEntity == null) {
			return null;
		}
		
		if(ID == ConfigHandler.GUI_CARBONIZER) {
			return new ContainerCarbonizer(player.inventory, (TileEntityCarbonizer)tileEntity);
		}else if(ID == ConfigHandler.GUI_MIXER) {
			return new ContainerMixer(player.inventory, (TileEntityMixer) tileEntity);
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
		
		if(tileEntity == null) {
			return null;
		}
		
		if(ID == ConfigHandler.GUI_CARBONIZER) {
			return new GuiCarbonizer(player.inventory, (TileEntityCarbonizer) tileEntity);
		}else if(ID == ConfigHandler.GUI_MIXER) {
			return new GuiMixer(player.inventory, (TileEntityMixer) tileEntity);
		}
		
		return null;
	}
}
