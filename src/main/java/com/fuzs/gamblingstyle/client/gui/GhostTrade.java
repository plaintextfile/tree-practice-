
package com.fuzs.gamblingstyle.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.stream.Stream;

@SideOnly(Side.CLIENT)
public class GhostTrade {
    
    private final int[][] slotCoordinates = {{76, 22}, {76, 48}, {134, 35}};
    private final ItemStack[] recipe = {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
    private Minecraft mc;

    public void initGui(Minecraft mc) {
        
        this.mc = mc;
    }

    public void setRecipe(ItemStack itemToBuy, ItemStack secondItemToBuy, ItemStack itemToSell) {
        
        this.recipe[0] = itemToBuy;
        this.recipe[1] = secondItemToBuy;
        this.recipe[2] = itemToSell;
    }

    public void clear() {

        Arrays.fill(this.recipe, ItemStack.EMPTY);
    }

    private boolean isVisible() {

        return Stream.of(this.recipe).anyMatch(ingredient -> ingredient != ItemStack.EMPTY);
    }

    public void render(int left, int top) {
        
        if (this.isVisible()) {