package com.vequinox.colacraft.blocks.machines.carbonizer;

import com.vequinox.colacraft.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCarbonizer extends TileEntity implements ITickable{
	private ItemStackHandler handler = new ItemStackHandler(8);
	private String customName;
	private ItemStack smelting = ItemStack.EMPTY;
	
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime;
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) this.handler;
		}
		
		return super.getCapability(capability, facing);
	}
	
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.carbonizer");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = getItemBurnTime((ItemStack)this.handler.getStackInSlot(6));
		
		if(compound.hasKey("CustomName", 8)) {
			this.setCustomName(compound.getString("CustomName"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short)this.burnTime);
		compound.setInteger("CookTime", (short)this.cookTime);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
		compound.setTag("Inventory", this.handler.serializeNBT());
		
		if(this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}
		
		return compound;
	}
	
	public boolean isBurning() {
		return this.burnTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isBurning(TileEntityCarbonizer tileEntity) {
		return tileEntity.getField(0) > 0;
	}
	
	public void update() {
		if(this.isBurning()) {
			--this.burnTime;
			BlockCarbonizer.setState(true, world, pos);
		}
		
		ItemStack[] inputs = new ItemStack[] {handler.getStackInSlot(0), handler.getStackInSlot(1), handler.getStackInSlot(2), handler.getStackInSlot(3), handler.getStackInSlot(4), handler.getStackInSlot(5)};
		ItemStack fuel = this.handler.getStackInSlot(6);
		
		if(this.isBurning() || !fuel.isEmpty() && !this.handler.getStackInSlot(0).isEmpty() && !this.handler.getStackInSlot(1).isEmpty()
				&& !this.handler.getStackInSlot(2).isEmpty() && !this.handler.getStackInSlot(3).isEmpty() && !this.handler.getStackInSlot(4).isEmpty()
				&& !this.handler.getStackInSlot(5).isEmpty()) {
			
			if(!this.isBurning() && this.canSmelt()) {
				this.burnTime = getItemBurnTime(fuel);
				this.currentBurnTime = this.burnTime;
				
				if(this.isBurning() && !fuel.isEmpty()) {
					Item fuelItem = fuel.getItem();
					fuel.shrink(1);
					
					if(fuel.isEmpty()) {
						ItemStack item1 = fuelItem.getContainerItem(fuel);
						this.handler.setStackInSlot(6, item1);
					}
				}
			}
		}
		
		if(this.isBurning() && this.canSmelt() && cookTime > 0) {
			cookTime++;
			if(cookTime == totalCookTime) {
				if(handler.getStackInSlot(7).getCount() > 0) {
					handler.getStackInSlot(7).grow(1);
				}else {
					handler.insertItem(7, smelting, false);
				}
				
				smelting = ItemStack.EMPTY;
				cookTime = 0;
				return;
			}
		}else {
			if(this.canSmelt() && this.isBurning()) {
				ItemStack output = CarbonizerRecipes.getInstance().getCarbonizingResult(inputs[0], inputs[1], inputs[2], inputs[3]);
				if(!output.isEmpty()) {
					smelting = output;
					cookTime++;
					inputs[0].shrink(1);
					inputs[1].shrink(1);
					inputs[2].shrink(1);
					inputs[3].shrink(1);
					inputs[4].shrink(1);
					inputs[5].shrink(1);
				}
			}
		}
				
	}
	
	public int getCookTime(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5, ItemStack input6, ItemStack input7) {
		return 200;
	}
	
	private boolean canSmelt() {
		if(((ItemStack)this.handler.getStackInSlot(0)).isEmpty() || ((ItemStack)this.handler.getStackInSlot(1)).isEmpty() ||
				((ItemStack)this.handler.getStackInSlot(2)).isEmpty() || ((ItemStack)this.handler.getStackInSlot(3)).isEmpty() ||
				((ItemStack)this.handler.getStackInSlot(4)).isEmpty() || ((ItemStack)this.handler.getStackInSlot(5)).isEmpty()) {
			return false;
		}else {
			ItemStack result = CarbonizerRecipes.getInstance().getCarbonizingResult(
					(ItemStack)this.handler.getStackInSlot(0), 
					(ItemStack)this.handler.getStackInSlot(1),
					(ItemStack)this.handler.getStackInSlot(2),
					(ItemStack)this.handler.getStackInSlot(3));
			
			if(result.isEmpty()) {
				return false;
			}else {
				ItemStack output = (ItemStack)this.handler.getStackInSlot(7);
				if(output.isEmpty()) {
					return true;
				}
				
				if(!output.isItemEqual(result)) {
					return false;
				}
				
				int res = output.getCount() + result.getCount();
				return res <= 64 && res <= output.getMaxStackSize();
			}
		}
	}
	
	public static int getItemBurnTime(ItemStack fuel) {
		if(fuel.isEmpty()) {
			return 0;
		}else {
			Item item = fuel.getItem();
			
			if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
				Block block = Block.getBlockFromItem(item);
				
				if(block == Blocks.WOODEN_SLAB) {
					return 150;
				}
				
				if(block.getDefaultState().getMaterial() == Material.WOOD) {
					return 300;
				}
				
				if(block == Blocks.COAL_BLOCK) {
					return 16000;
				}
			}
			
			if(item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) {
				return 200;
			}
			
			if(item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) {
				return 200;
			}
			
			if(item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) {
				return 200;
			}
			
			if(item == Items.STICK) {
				return 100;
			}
			
			if(item == Items.COAL) {
				return 1600;
			}
			
			if(item == Items.LAVA_BUCKET) {
				return 20000;
			}
			
			if(item == Item.getItemFromBlock(Blocks.SAPLING)) {
				return 100;
			}
			
			if(item == Items.BLAZE_ROD) {
				return 2400;
			}
			
			return GameRegistry.getFuelValue(fuel);
		}
	}
	
	public static boolean isItemFuel(ItemStack fuel) {
		return getItemBurnTime(fuel) > 0;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX()+0.5D, (double)this.pos.getY()+0.5D, (double)this.pos.getZ()+0.5D) <= 64.0D;
	}
	
	public String getGuiID() {
		return "cm:carbonizer";
	}
	
	public int getField(int id) {
		switch(id) {
		case 0:
			return this.burnTime;
		case 1:
			return this.currentBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
				return 0;
		}
	}
	
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}
	
	@Override
	public void tick() {}
	
	
	
	
}