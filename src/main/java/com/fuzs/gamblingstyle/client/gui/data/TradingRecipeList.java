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