package com.fuzs.gamblingstyle.client.gui;

import com.fuzs.gamblingstyle.GamblingStyle;
import com.fuzs.gamblingstyle.client.gui.core.ITooltipButton;
import com.fuzs.gamblingstyle.client.gui.data.TradingRecipe;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiButtonTradingRecipe extends GuiButton implements ITooltipButton {

    private static final ResourceLocation RECIPE_BOOK = new ResourceLocation(GamblingStyle.MODID, "textures/gui/container/merchant_book.png");

    private final ItemStack[] recipe = {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
    private int recipeId;
    private boolean isSelectedRecipe;
    private boolean hasContents;
    private boolean soldOut;
    private boolean favorite;

    public GuiButtonTradingRecipe(int id, int posX, int posY) {

        super(id, posX, posY, 84, 22, "");
        this.visible = false;
    }

    public void setContents(int id, TradingRecipe recipe, boolean soldOut) {

        this.recipeId = id;
        this.recipe[0] = recipe.getItemToBuy();
        this.recipe[1] = recipe.getSecondItemToBuy();
        this.recipe[2] = recipe.getItemToSell();
        this.isSelectedRecipe = recipe.isSelected();
        this.hasContents = recipe.hasRecipeContents();
        this.soldOut = soldOut;
        this.favorite = rec