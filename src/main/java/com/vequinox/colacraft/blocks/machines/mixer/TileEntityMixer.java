package com.vequinox.colacraft.blocks.machines.mixer;

import javax.annotation.Nonnull;

import com.vequinox.colacraft.init.ModBlocks;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.items.ItemFlavorPacket;
import com.vequinox.colacraft.items.ItemSolution;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
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
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.vequinox.colacraft.util.StackHelper;

public class TileEntityMixer extends TileEntity implements IInventory, ITickable{

	private NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);
	private String customName;
	private ItemStack smelting = ItemStack.EMPTY;
	
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime;
	
	private int tier;
	
	public TileEntityMixer() {
		
	}
	
	public TileEntity setTier(int tier) {
		this.tier = tier;
		return this;
	}
	
	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.mixer";
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
		return this.inventory.get(index);
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

		if(index == 0 && index + 1 == 1 && index + 2 == 2 && index + 3 == 3 && !flag) {
			this.cookTime = 0;
			this.markDirty();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.inventory);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = getItemBurnTime((ItemStack)this.inventory.get(4));

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
	public static boolean isBurning(TileEntityMixer tileEntity) {
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
			ItemStack fuel = this.inventory.get(4);//fuel slot
			
			if(this.isBurning() || !fuel.isEmpty() && !this.inventory.get(0).isEmpty()){
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
								this.inventory.set(4, item1);
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
				BlockMixer.setState(this.isBurning(), this.world, this.pos, getMixerType());
			}
		}
		
		if(flag1) {
			this.markDirty();
		}
	}
	
	private Block getMixerType() {
		return this.tier == 3 ? ModBlocks.MIXER_TIER_3 : this.tier == 2 ? ModBlocks.MIXER_TIER_2 : ModBlocks.MIXER;
	}

	private boolean canSmelt() {
		if((this.inventory.get(0)).isEmpty() || 
				((this.inventory.get(1)).isEmpty() && this.inventory.get(2).isEmpty() && this.inventory.get(3).isEmpty())){
			return false;
		}else {
			List<ItemStack> ingredients = Arrays.asList(this.inventory.get(1), this.inventory.get(2), this.inventory.get(3));
			ItemStack result = getNewSolution(this.inventory.get(0), ingredients);
			return result != null;
		}
	}
	
	private ItemStack getNewSolution(ItemStack solution, List<ItemStack> ingredients) {
		ItemStack sol = solution.copy();
		//get ingredients from slots
		int sugarAmount = 0;
		int redstoneAmount = 0;
		List<ItemFlavorPacket> packets = new ArrayList<ItemFlavorPacket>();
		boolean hasValidIngredients = true;
		for(ItemStack ingredient : ingredients) {
			if(ingredient.getItem() instanceof ItemFlavorPacket) {
				packets.add((ItemFlavorPacket)ingredient.getItem());
			}else if(ingredient.getItem() == Items.SUGAR) {
				sugarAmount += ingredient.getCount();
			}else if(ingredient.getItem() == Items.REDSTONE) {
				redstoneAmount += ingredient.getCount();
			}else {
				hasValidIngredients = false;
			}
		}
		
		if(!hasValidIngredients) return null;
		
		for(ItemFlavorPacket packet : packets) {
			NBTTagCompound potionTag = StackHelper.getTag(sol).getCompoundTag(packet.getEffect().toString());
			potionTag.setString("Potion", packet.getEffect().toString());
			potionTag.setInteger("durationamount", potionTag.getInteger("durationamount") + sugarAmount);
			potionTag.setInteger("amplifieramount", potionTag.getInteger("amplifieramount") + redstoneAmount);
		}
		
		return sol;
	}
	
	public void smeltItem() {
		if(this.canSmelt()) {
			ItemStack solution = this.inventory.get(0);
			ItemStack ingredient1 = this.inventory.get(1);
			ItemStack ingredient2 = this.inventory.get(2);
			ItemStack ingredient3 = this.inventory.get(3);
			ItemStack output = this.inventory.get(5);
			List<ItemStack> ingredients = Arrays.asList(ingredient1, ingredient2, ingredient3);
			ItemStack result = getNewSolution(solution, ingredients);
			
			if(output.isEmpty()) {
				this.inventory.set(5, result);
			}
			
			
			solution.shrink(1);
			for(ItemStack ingredient : ingredients) {
				if(!ingredient.isEmpty()) {
					if(ingredient.getItem() == Items.SUGAR || ingredient.getItem() == Items.REDSTONE) {
						ingredient.shrink(ingredient.getCount());
					}else {
						ingredient.shrink(1);
					}
				}
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
		if(index == 5) {
			return false;
		}else if(index != 4) {
			if(index == 0 && stack.getItem() == ModItems.BASE_SOLUTION) {
				return true;
			}else if((index == 1 || index == 2 || index == 3) && (stack.getItem() == Items.SUGAR || stack.getItem() == Items.REDSTONE || stack.getItem() instanceof ItemFlavorPacket)) {
				return true;
			}else {
				return false;
			}
		}else {
			return isItemFuel(stack);
		}
	}
	
	@Nonnull
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {
		return new ContainerMixer(playerInventory, this);
	}

	public String getGuiID() {
		return "colacraft:mixer";
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
