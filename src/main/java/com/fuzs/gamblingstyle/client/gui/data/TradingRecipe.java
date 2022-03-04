
package com.fuzs.gamblingstyle.client.gui.data;

import com.fuzs.gamblingstyle.capability.container.ITradingInfo;
import com.google.common.collect.Lists;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class TradingRecipe {

    private final ItemStack[] recipe = new ItemStack[3];
    private boolean visible = true;
    private boolean selected;
    private boolean favorite;
    int itemIngredients;
    int secondItemIngredients;

    public TradingRecipe(ItemStack itemToBuy, ItemStack secondItemToBuy, ItemStack itemToSell) {

        this.recipe[0] = itemToBuy;
        this.recipe[1] = secondItemToBuy;
        this.recipe[2] = itemToSell;
    }

    public ItemStack getItemToBuy() {

        return this.recipe[0];
    }

    public ItemStack getSecondItemToBuy() {

        return this.recipe[1];
    }

    public boolean hasSecondItemToBuy() {

        return !this.getSecondItemToBuy().isEmpty();
    }

    public ItemStack getItemToSell() {

        return this.recipe[2];
    }

    public boolean isVisible() {

        return this.visible;
    }

    public void setVisible(boolean visible) {

        this.visible = visible;
    }

    public boolean isSelected() {

        return this.selected;
    }
