
package com.fuzs.gamblingstyle.client.gui.core;

import com.fuzs.gamblingstyle.inventory.ContainerVillager;
import net.minecraft.client.Minecraft;
import net.minecraft.village.MerchantRecipeList;

public interface IGuiExtension {

    void initGui(Minecraft mc, int width, int height);

    void onGuiClosed();
