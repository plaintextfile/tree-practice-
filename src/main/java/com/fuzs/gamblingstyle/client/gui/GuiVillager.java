
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