
package com.fuzs.gamblingstyle.network.message;

import com.fuzs.gamblingstyle.capability.CapabilityController;
import com.fuzs.gamblingstyle.capability.container.ITradingInfo;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CSyncTradingInfoMessage extends Message<CSyncTradingInfoMessage> {

    private int merchantId;
    private int lastTradeIndex;
    private ITradingInfo.FilterMode filterMode;
    private byte[] favoriteTrades;

    @SuppressWarnings("unused")
    public CSyncTradingInfoMessage() {

    }

    public CSyncTradingInfoMessage(int merchantId, int lastTradeIndex, ITradingInfo.FilterMode filterMode, byte[] favoriteTrades) {

        this.merchantId = merchantId;
        this.lastTradeIndex = lastTradeIndex;
        this.filterMode = filterMode;
        this.favoriteTrades = favoriteTrades;
    }

    @Override
    public void write(ByteBuf buf) {

        buf.writeInt(this.merchantId);
        buf.writeByte(this.lastTradeIndex);
        buf.writeByte(this.filterMode.ordinal());