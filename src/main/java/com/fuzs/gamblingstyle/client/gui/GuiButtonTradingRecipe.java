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

    private static final R