package com.vequinox.colacraft.blocks.machines.mixer;

import com.vequinox.colacraft.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMixer extends GuiContainer{

	private final static ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/mixer_tier_1_gui.png");
	private final InventoryPlayer player;
	private final TileEntityMixer tileEntity;
	
	public GuiMixer(InventoryPlayer player, TileEntityMixer tileEntity) {
		super(new ContainerMixer(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize/2 - this.fontRenderer.getStringWidth(tileName)/2)+3, 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(TileEntityMixer.isBurning(tileEntity)) {
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 31 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		
		int l = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 113, this.guiTop + 38, 176, 14, l + 1, 16);
	}
	
	private int getBurnLeftScaled(int pixels) {
		int i = this.tileEntity.getField(1);
		if(i == 0) {
			i = 200;
		}
		
		return this.tileEntity.getField(0)*pixels/i;
	}
	
	private int getCookProgressScaled(int pixels) {
		int i = this.tileEntity.getField(2);
		int j = this.tileEntity.getField(3);
		return j != 0 && i != 0 ? i*pixels/j : 0;
	}
	
}
