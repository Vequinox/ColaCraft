package com.vequinox.colacraft.blocks.machines.carbonizer;

import com.vequinox.colacraft.init.ModBlocks;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.items.ItemSoda;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.ITickable;
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

public class TileEntityCarbonizer extends TileEntity implements IInventory, ITickable{
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);
	private String customName;
	private ItemStack smelting = ItemStack.EMPTY;

	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime;
	
	public TileEntityCarbonizer(int tierCookTime) {
		this.totalCookTime = tierCookTime;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.carbonizer";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack stack : this.inventory) {
			if(!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return (ItemStack)this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemStack = (ItemStack)this.inventory.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
		this.inventory.set(index, stack);

		if(stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if(index == 0 && index + 1 == 1 && index + 2 == 2 && index + 3 == 3 && index + 4 == 4 && index + 5 == 5 && !flag) {
			this.cookTime = 0;
			this.markDirty();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.inventory);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = getItemBurnTime((ItemStack)this.inventory.get(6));

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
		ItemStackHelper.saveAllItems(compound, this.inventory);

		if(this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isBurning() {
		return this.burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(TileEntityCarbonizer tileEntity) {
		return tileEntity.getField(0) > 0;
	}

	@Override
	public void update() {
		boolean flag = this.isBurning();
		boolean flag1 = false;
		
		if(this.isBurning()) {
			--this.burnTime;
		}
		
		if(!this.world.isRemote) {
			ItemStack fuel = (ItemStack)this.inventory.get(6);//fuel slot
			
			if(this.isBurning() || !fuel.isEmpty() && !((((ItemStack)this.inventory.get(0)).isEmpty()) || ((ItemStack)this.inventory.get(1)).isEmpty() || ((ItemStack)this.inventory.get(4)).isEmpty() || ((ItemStack)this.inventory.get(5)).isEmpty())){
				if(!this.isBurning() && this.canSmelt()) {
					this.burnTime = getItemBurnTime(fuel);
					this.currentBurnTime = this.burnTime;

					if(this.isBurning()) {
						flag1 = true;
						
						if(!fuel.isEmpty()) {
							Item fuelItem = fuel.getItem();
							fuel.shrink(1);
	
							if(fuel.isEmpty()) {
								ItemStack item1 = fuelItem.getContainerItem(fuel);
								this.inventory.set(6, item1);
							}
						}
					}
				}
				
				if(this.isBurning() && this.canSmelt()) {
					++this.cookTime;
					
					if(this.cookTime == this.totalCookTime) {
						this.cookTime = 0;
						this.smeltItem();
						flag1 = true;
					}
				}else {
					this.cookTime = 0;
				}
			}else if(!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}
			
			if(flag != this.isBurning()) {
				flag1 = true;
				BlockCarbonizer.setState(this.isBurning(), this.world, this.pos, getCarbonizerType());
			}
		}
		
		if(flag1) {
			this.markDirty();
		}
	}
	
	private Block getCarbonizerType() {
		return this.totalCookTime == 200 ? ModBlocks.CARBONIZER : this.totalCookTime == 100 ? ModBlocks.CARBONIZER_TIER_2 : ModBlocks.CARBONIZER_TIER_3;
	}

	private boolean canSmelt() {
		if(((ItemStack)this.inventory.get(0)).isEmpty() || ((ItemStack)this.inventory.get(1)).isEmpty() ||
			((ItemStack)this.inventory.get(4)).isEmpty() || ((ItemStack)this.inventory.get(5)).isEmpty() || !((ItemStack)this.inventory.get(7)).isEmpty()){
			System.out.println("Can't smelt, something is empty or output is not empty");
			return false;
		}else {
			ItemSoda result = CarbonizerRecipes.getInstance().getCarbonizingResult(
					(ItemStack)this.inventory.get(0),
					(ItemStack)this.inventory.get(1),
					(ItemStack)this.inventory.get(2),
					(ItemStack)this.inventory.get(3));
			System.out.println("Can smelt!!!!");
			return result != null;
		}
	}
	
	public void smeltItem() {
		if(this.canSmelt()) {
			ItemStack baseIngredient = (ItemStack)this.inventory.get(0);
			ItemStack fillerIngredient = (ItemStack)this.inventory.get(1);
			ItemStack augmentIngredient = (ItemStack)this.inventory.get(2);
			ItemStack modifierIngredient = (ItemStack)this.inventory.get(3);
			ItemStack bottle = (ItemStack)this.inventory.get(4);
			ItemStack emptyCan = (ItemStack)this.inventory.get(5);
			ItemSoda result = CarbonizerRecipes.getInstance().getCarbonizingResult(baseIngredient, fillerIngredient, augmentIngredient, modifierIngredient);
			ItemStack output = (ItemStack)this.inventory.get(7);
			
			if(output.isEmpty()) {
				this.inventory.set(7, new ItemStack(result));
			}
			
			baseIngredient.shrink(1);
			fillerIngredient.shrink(1);
			
			if(!augmentIngredient.isEmpty()) {
				augmentIngredient.shrink(augmentIngredient.getCount());
			}
			
			if(!modifierIngredient.isEmpty()) {
				int shrinkAmt = modifierIngredient.getCount();
				if(shrinkAmt > 15 && shrinkAmt < 32) {
					shrinkAmt = 16;
				}else if(shrinkAmt > 31 && shrinkAmt < 48) {
					shrinkAmt = 32;
				}else if(shrinkAmt > 47 && shrinkAmt < 64) {
					shrinkAmt = 48;
				}
					
				modifierIngredient.shrink(shrinkAmt);
			}
			
			bottle.shrink(1);
			emptyCan.shrink(1);
			System.out.println("smelted item");
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

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX()+0.5D, (double)this.pos.getY()+0.5D, (double)this.pos.getZ()+0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 7) {
			return false;
		}else if(index != 6) {
			if(index == 4 && stack.getItem() == Items.POTIONITEM) {
				return true;
			}else if(index == 5 && stack.getItem() == ModItems.CAN) {
				return true;
			}else if(index != 4 && index != 5) {
				return true;
			}else {
				return false;
			}
		}else {
			return isItemFuel(stack);
		}
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

	@Override
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
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		this.inventory.clear();
	}
}