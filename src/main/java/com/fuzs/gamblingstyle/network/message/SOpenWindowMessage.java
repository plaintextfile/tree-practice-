
package com.fuzs.gamblingstyle.network.message;

import com.fuzs.gamblingstyle.capability.container.ITradingInfo;
import com.fuzs.gamblingstyle.client.gui.GuiVillager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SOpenWindowMessage extends Message<SOpenWindowMessage> {

    private int windowId;
    private ITextComponent windowTitle;
    private int slotCount;
    private int merchantId;
    private int lastTradeIndex;
    private ITradingInfo.FilterMode filterMode;
    private byte[] favoriteTrades;

    @SuppressWarnings("unused")
    public SOpenWindowMessage() {

    }

    public SOpenWindowMessage(int windowId, ITextComponent windowTitle, int slotCount, int merchantId, int lastTradeIndex, ITradingInfo.FilterMode filterMode, byte[] favoriteTrades) {

        this.windowId = windowId;
        this.windowTitle = windowTitle;
        this.slotCount = slotCount;
        this.merchantId = merchantId;