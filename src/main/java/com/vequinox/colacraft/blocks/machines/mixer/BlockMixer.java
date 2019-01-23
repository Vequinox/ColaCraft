package com.vequinox.colacraft.blocks.machines.mixer;

import java.util.Random;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.blocks.BlockBase;
import com.vequinox.colacraft.init.ModBlocks;
import com.vequinox.colacraft.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMixer extends BlockBase implements ITileEntityProvider{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool MIXING = PropertyBool.create("mixing");
	
	private int cookTime;
	private String name;
	private int tier;

	public BlockMixer(String name, int tier) {
		super(name, Material.ROCK);
		this.name = name;
		this.tier = tier;
		setHardness(3.5f);
		setResistance(5f);
		setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MIXING, false));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(this.tier == 2) {
			return Item.getItemFromBlock(ModBlocks.MIXER_TIER_2);
		}else if(this.tier == 3) {
			return Item.getItemFromBlock(ModBlocks.MIXER_TIER_3);
		}else {
			return Item.getItemFromBlock(ModBlocks.MIXER);
		}
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		if(this.tier == 2) {
			return new ItemStack(ModBlocks.MIXER_TIER_2);
		}else if(this.tier == 3) {
			return new ItemStack(ModBlocks.MIXER_TIER_3);
		}else {
			return new ItemStack(ModBlocks.MIXER);
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			int gui = Reference.GUI_MIXER;
			if(this.tier == 2) {
				gui = Reference.GUI_MIXER_TIER_2;
			}else if(this.tier == 3) {
				gui = Reference.GUI_MIXER_TIER_3;
			}
			
			System.out.println("worldIn: " + worldIn);
			System.out.println("BlockPos: " + pos);
			System.out.println("state: " + state);
			System.out.println("playerIn: " + playerIn);
			System.out.println("hand: " + hand);
			System.out.println("facing: " + facing);
			System.out.println("hitX: " + hitX + " | hitY: " + hitY + " | hitZ: " + hitZ);
			if(gui == 1 || gui == 2 || gui == 3) {
				playerIn.openGui(Main.instance, gui, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		
		return true;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			IBlockState stateNorth = worldIn.getBlockState(pos.north());
			IBlockState stateSouth = worldIn.getBlockState(pos.south());
			IBlockState stateWest = worldIn.getBlockState(pos.west());
			IBlockState stateEast = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing)state.getValue(FACING);
			
			if(face == EnumFacing.NORTH && stateNorth.isFullBlock() && !stateSouth.isFullBlock()) {
				face = EnumFacing.SOUTH;
			}else if(face == EnumFacing.SOUTH && !stateNorth.isFullBlock() && stateSouth.isFullBlock()) {
				face = EnumFacing.NORTH;
			}else if(face == EnumFacing.WEST && stateWest.isFullBlock() && !stateEast.isFullBlock()) {
				face = EnumFacing.EAST;
			}else if(face == EnumFacing.EAST && stateEast.isFullBlock() && !stateWest.isFullBlock()) {
				face = EnumFacing.WEST;
			}
			
			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos, Block mixerType) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if(active) {
			worldIn.setBlockState(pos, mixerType.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(MIXING, true), 3);
		}else {
			worldIn.setBlockState(pos, mixerType.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(MIXING, false), 3);
		}
		
		if(tileEntity != null) {
			tileEntity.validate();
			worldIn.setTileEntity(pos, tileEntity);
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMixer().setTier(this.tier);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityMixer tileentity = (TileEntityMixer)worldIn.getTileEntity(pos);
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.getStackInSlot(0)));
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.getStackInSlot(1)));
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.getStackInSlot(2)));
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.getStackInSlot(3)));
		if(this.tier >= 2) {
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.getStackInSlot(4)));
		}
		if(this.tier == 3) {			
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.getStackInSlot(5)));
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {MIXING, FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.byIndex(meta);
		if(facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
}
