
package com.fuzs.gamblingstyle.client.gui;

import com.fuzs.gamblingstyle.GamblingStyle;
import com.fuzs.gamblingstyle.capability.container.ITradingInfo;
import com.fuzs.gamblingstyle.client.gui.core.IGuiExtension;
import com.fuzs.gamblingstyle.client.gui.core.ITooltipButton;
import com.fuzs.gamblingstyle.client.gui.data.TradingRecipe;
import com.fuzs.gamblingstyle.client.gui.data.TradingRecipeList;
import com.fuzs.gamblingstyle.inventory.ContainerVillager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.List;
import java.util.stream.IntStream;

@SideOnly(Side.CLIENT)
public class GuiTradingBook extends Gui implements IGuiExtension {

    private static final ResourceLocation RECIPE_BOOK = new ResourceLocation(GamblingStyle.MODID, "textures/gui/container/merchant_book.png");
    public static final int MAX_BUTTONS = 6;

    private Minecraft mc;
    private final int xSize = 112;
    private final int ySize = 166;
    public int hoveredSlot;
    private ITooltipButton hoveredButton;
    private final GuiButtonTradingRecipe[] tradeButtons = new GuiButtonTradingRecipe[MAX_BUTTONS];
    private GuiTextField searchField;
    private final GuiButtonFilter filterButton;
    private String lastSearch = "";
    private int guiLeft;
    private int guiTop;
    private boolean requiresRefresh;
    private TradingRecipeList tradingRecipeList;
    // Amount scrolled in Creative mode inventory (0 = top, 1 = bottom)
    private float currentScroll;
    private int scrollPosition;
    private boolean isScrolling;
    private boolean wasClicking;
    private GuiButton clickedButton;
    private int selectedTradingRecipe;
    private boolean clearSearch;
    private int timesInventoryChanged;

    public GuiTradingBook(ITradingInfo.FilterMode filterMode) {

        this.filterButton = new GuiButtonFilter(this.tradeButtons.length, this.guiLeft + 94, this.guiTop + 8, filterMode);
        for (int i = 0; i < this.tradeButtons.length; ++i) {

            this.tradeButtons[i] = new GuiButtonTradingRecipe(i, this.guiLeft + 10, this.guiTop + 24 + 22 * i);
        }
    }

    @Override
    public void initGui(Minecraft mc, int width, int height) {

        this.mc = mc;
        this.requiresRefresh = true;
        Keyboard.enableRepeatEvents(true);
        this.guiLeft = (width - this.xSize) / 2 - 88;
        this.guiTop = (height - this.ySize) / 2;
        this.timesInventoryChanged = this.mc.player.inventory.getTimesChanged();
        this.initSearchField();

        this.filterButton.setPosition(this.guiLeft + 94, this.guiTop + 8);
        for (int i = 0; i < this.tradeButtons.length; i++) {

            this.tradeButtons[i].setPosition(this.guiLeft + 10, this.guiTop + 24 + 22 * i);