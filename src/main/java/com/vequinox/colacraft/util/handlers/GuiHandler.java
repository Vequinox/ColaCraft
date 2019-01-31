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
		
		
		switch(ID) {
			case 0:
				return new ContainerCarbonizer(player.inventory, (TileEntityCarbonizer)tileEntity);
			case 1:
				return new ContainerMixer(player.inventory, (TileEntityMixer)tileEntity);
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
		
		if(tileEntity == null) {
			return null;
		}
		
		
		switch(ID) {
			case 0:
				return new GuiCarbonizer(player.inventory, (TileEntityCarbonizer)tileEntity);
			case 1:
				return new GuiMixer(player.inventory, (TileEntityMixer)tileEntity);
		}
		
		return null;
	}
}
