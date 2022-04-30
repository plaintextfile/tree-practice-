package com.fuzs.gamblingstyle.client.gui.data;

import com.fuzs.gamblingstyle.capability.container.ITradingInfo;
import com.fuzs.gamblingstyle.inventory.ContainerVillager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SideOnly(Side.CLIENT)
public class TradingRecipeList extends ArrayList<TradingRecipe> {

    public TradingRecipeList(MerchantRecipeList merchantRecipes) {

        this.convertMerchantRecipes(merchantRecipes);
    }

    private void convertMerchantRecipes(MerchantRecipeList merchantRecipes) {

        for (MerchantRecipe recipe : merchantRecipes) {

            if (this.isValidRecipe(recipe)) {

                this.add(new TradingRecipe(recipe.getItemToBuy(), recipe.getSecondItemToBuy(), recipe.getItemToSell()));
            }
        }
    }

    private boolean isValidRecipe(MerchantRecipe recipe) {

        return !recipe.getItemToBuy().isEmpty() && !recipe.getItemToSell().isEmpty();
    }

    public int getActiveRecipeAmount() {

        return (int) this.stream().filter(TradingRecipe::isVisible).count();
    }

    public void search(Minecraft mc, String query, ITradingInfo.FilterMode filterMode) {

        ITooltipFlag tooltipFlag = mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL;
        String t