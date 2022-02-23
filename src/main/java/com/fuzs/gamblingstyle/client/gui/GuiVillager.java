
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

        super.updateScreen();
        MerchantRecipeList merchantRecipes = this.merchant.getRecipes(this.mc.player);
        if (merchantRecipes != null) {

            this.tradingBookGui.updateScreen(merchantRecipes, (ContainerVillager) this.inventorySlots);
        }

        Slot hoveredSlot = this.getSlotUnderMouse();
        this.tradingBookGui.hoveredSlot = hoveredSlot != null ? hoveredSlot.getHasStack() ? 2 : 1 : 0;
        if (((ContainerVillager) this.inventorySlots).areSlotsFilled()) {

            this.ghostTrade.clear();
        }
    }

    @Override
    protected boolean hasClickedOutside(int mouseX, int mouseY, int guiLeft, int guiTop) {

        boolean flag = mouseX < guiLeft || mouseY < guiTop || mouseX >= guiLeft + this.xSize || mouseY >= guiTop + this.ySize;

        return this.tradingBookGui.hasClickedOutside(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize) && flag;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.mc.player);
        if (merchantrecipelist != null) {

            int selectedRecipe = this.currentRecipeIndex;
            MerchantRecipe merchantrecipe = merchantrecipelist.get(selectedRecipe);
            if (merchantrecipe.isRecipeDisabled()) {

                this.mc.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableLighting();
                this.drawTexturedModalRect(this.guiLeft + 97, this.guiTop + 32, 212, 0, 28, 21);
            }
        }

        GuiInventory.drawEntityOnScreen(this.guiLeft + 33, this.guiTop + 75, 30, this.guiLeft + 33 - mouseX,
                this.guiTop + 75 - 50 - mouseY, this.traderEntity);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        if (!this.tradingBookGui.mouseClicked(mouseX, mouseY, mouseButton)) {

            int recipeIndex = this.tradingBookGui.mouseClickedTradeButtons(mouseX, mouseY, mouseButton);
            if (recipeIndex != -1) {

                this.updateSelectedRecipe(recipeIndex, mouseButton == 1);
            } else {

                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    private void updateSelectedRecipe(int recipeIndex, boolean skipMove) {

        MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.mc.player);
        if (merchantrecipelist != null) {

            MerchantRecipe recipe = merchantrecipelist.get(recipeIndex);
            boolean isNotSelected = this.currentRecipeIndex != recipeIndex;
            boolean hasIngredients = this.tradingBookGui.hasRecipeContents(recipeIndex);
            boolean isDisabled = recipe.isRecipeDisabled();
            if (isNotSelected) {

                this.currentRecipeIndex = recipeIndex;
                this.sendSelectedRecipe(!hasIngredients || isDisabled);
            }

            if (hasIngredients) {

                this.ghostTrade.clear();
                if (!isDisabled) {

                    this.moveRecipeIngredients(isNotSelected, GuiScreen.isShiftKeyDown(), skipMove);
                }
            } else {

                this.ghostTrade.setRecipe(recipe.getItemToBuy(), recipe.getSecondItemToBuy(), recipe.getItemToSell());
                if (((ContainerVillager) this.inventorySlots).areSlotsFilled()) {

                    this.sendSelectedRecipe(true);
                }
            }
        }
    }
