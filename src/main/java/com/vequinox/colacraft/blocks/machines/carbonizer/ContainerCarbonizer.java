package com.vequinox.colacraft.blocks.machines.carbonizer;

import com.vequinox.colacraft.blocks.machines.carbonizer.slots.SlotCarbonizerFuel;
import com.vequinox.colacraft.blocks.machines.carbonizer.slots.SlotCarbonizerOutput;
import com.vequinox.colacraft.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCarbonizer extends Container{
	private final TileEntityCarbonizer tileEntity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public ContainerCarbonizer(InventoryPlayer player, TileEntityCarbonizer tileEntity) {
		this.tileEntity = tileEntity;
		
		this.addSlotToContainer(new Slot(tileEntity, 0, 50, 30));
		this.addSlotToContainer(new Slot(tileEntity, 1, 68, 30));
		this.addSlotToContainer(new Slot(tileEntity, 2, 50, 48));
		this.addSlotToContainer(new Slot(tileEntity, 3, 68, 48));
		this.addSlotToContainer(new Slot(tileEntity, 4, 92, 30));
		this.addSlotToContainer(new Slot(tileEntity, 5, 92, 48));
		
		this.addSlotToContainer(new SlotCarbonizerFuel(tileEntity, 6, 19, 48));
		this.addSlotToContainer(new SlotCarbonizerOutput(player.player, tileEntity, 7, 145, 39));
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x*18, 142));
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(this.cookTime != this.tileEntity.getField(2)) {
				listener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
			}
			
			if(this.burnTime != this.tileEntity.getField(0)) {
				listener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
			}
			
			if(this.currentBurnTime != this.tileEntity.getField(1)) {
				listener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
			}
			
			if(this.totalCookTime != this.tileEntity.getField(3)) {
				listener.sendWindowProperty(this, 3, this.tileEntity.getField(3));
			}
		}
		
		this.cookTime = this.tileEntity.getField(2);
		this.burnTime = this.tileEntity.getField(0);
		this.currentBurnTime = this.tileEntity.getField(1);
		this.totalCookTime = this.tileEntity.getField(3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileEntity.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUsableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot currentSlot = (Slot)this.inventorySlots.get(index);
		
		if(currentSlot != null && currentSlot.getHasStack()) {
			ItemStack currentSlotStack = currentSlot.getStack();
			stack = currentSlotStack.copy();
			
			if(index == 7) {//if the index is the output slot
				if(!this.mergeItemStack(currentSlotStack, 8, 44, true)) {
					return ItemStack.EMPTY;
				}
				
				currentSlot.onSlotChange(currentSlotStack, stack);
			}else if(index != 6 && index != 5 && index != 4 && index != 3 && index != 2 && index != 1 && index != 0) {// if the index is in player's inventory, not carbonizer
				if(currentSlotStack.getItem() == ModItems.BASE_SOLUTION) {
					if(!this.mergeItemStack(currentSlotStack, 4, 5, false)) return ItemStack.EMPTY;
				}else if(currentSlotStack.getItem() == ModItems.CAN) {
					if(!this.mergeItemStack(currentSlotStack, 5, 6, false)) return ItemStack.EMPTY;
				}else if(TileEntityCarbonizer.isItemFuel(currentSlotStack)){
					if(!this.mergeItemStack(currentSlotStack, 6, 7, false)) return ItemStack.EMPTY;
				}else if(index >= 8 && index < 35){
					if(!this.mergeItemStack(currentSlotStack, 35, 44, false)) return ItemStack.EMPTY;
				}else if(index >= 35 && index < 44 && !this.mergeItemStack(currentSlotStack, 8, 35, false)){
					return ItemStack.EMPTY;
				}
			}else if(!this.mergeItemStack(currentSlotStack, 8, 44, false)) {
				return ItemStack.EMPTY;
			}
			
			if(currentSlotStack.isEmpty()) {
				currentSlot.putStack(ItemStack.EMPTY);
			}else {
				currentSlot.onSlotChanged();
			}
			
			if(currentSlotStack.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			currentSlot.onTake(player, currentSlotStack);
				
		}
		
		return stack;
	}
}
