
package com.fuzs.gamblingstyle.client.gui;

import com.fuzs.gamblingstyle.GamblingStyle;
import com.fuzs.gamblingstyle.capability.container.ITradingInfo;
import com.fuzs.gamblingstyle.network.message.CMoveIngredientsMessage;
import com.fuzs.gamblingstyle.network.message.CSelectedRecipeMessage;
import com.fuzs.gamblingstyle.network.message.CSyncTradingInfoMessage;
import com.fuzs.gamblingstyle.inventory.ContainerVillager;
import com.fuzs.gamblingstyle.network.NetworkHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.IOException;

@SuppressWarnings("NullableProblems")
@SideOnly(Side.CLIENT)
public class GuiVillager extends GuiContainer {

    private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation(GamblingStyle.MODID, "textures/gui/container/merchant.png");

    private final IMerchant merchant;
    private final EntityLivingBase traderEntity;
    private final ITextComponent windowTitle;
    private int currentRecipeIndex;
    private final byte[] favoriteTrades;

    private final GuiTradingBook tradingBookGui;
    private final GhostTrade ghostTrade;

    public GuiVillager(InventoryPlayer playerInventory, IMerchant merchant, EntityLivingBase traderEntity, int currentRecipeIndex, ITradingInfo.FilterMode filterMode, byte[] favoriteTrades) {

        super(new ContainerVillager(playerInventory, merchant, traderEntity.world));
        this.merchant = merchant;
        this.traderEntity = traderEntity;
        this.windowTitle = merchant.getDisplayName();
        this.currentRecipeIndex = currentRecipeIndex;
        this.tradingBookGui = new GuiTradingBook(filterMode);
        this.ghostTrade = new GhostTrade();
        this.favoriteTrades = favoriteTrades;
    }

    @Override
    public void initGui() {

        super.initGui();
        // trading book might be open or not
        this.guiLeft = (this.width - this.xSize) / 2 + 57;
        this.tradingBookGui.initGui(this.mc, this.width, this.height);
        // not that this should every change, but it's updated in the super method
        this.ghostTrade.initGui(this.mc);
    }

    @Override
    public void onGuiClosed() {

        this.tradingBookGui.onGuiClosed();
        if (this.merchant.getCustomer() != null && this.merchant.getRecipes(this.merchant.getCustomer()) != null) {

            NetworkHandler.get().sendToServer(new CSyncTradingInfoMessage(this.traderEntity.getEntityId(), this.currentRecipeIndex, this.tradingBookGui.getCurrentFilterMode(), this.tradingBookGui.getFavoriteTrades()));
        }

        super.onGuiClosed();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        String windowTitle = this.windowTitle.getUnformattedText();
        this.fontRenderer.drawString(windowTitle, this.xSize / 2 - this.fontRenderer.getStringWidth(windowTitle) / 2 + 23, 6, 4210752);
        this.fontRenderer.drawString(new TextComponentTranslation("container.inventory").getUnformattedText(), 62, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public void updateScreen() {
