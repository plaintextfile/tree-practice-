
package com.fuzs.gamblingstyle.capability.container;

import net.minecraft.nbt.NBTTagCompound;

public class TradingInfo implements ITradingInfo {

    private int lastTradeIndex;
    private FilterMode filterMode = FilterMode.ALL;
    private byte[] favoriteTrades = new byte[0];

    @Override
    public int getLastTradeIndex() {

        return this.lastTradeIndex;
    }

    @Override
    public void setLastTradeIndex(int lastTradeIndex) {

        this.lastTradeIndex = lastTradeIndex;
    }

    @Override
    public FilterMode getFilterMode() {

        return this.filterMode;
    }

    @Override
    public void setFilterMode(FilterMode filterMode) {

        this.filterMode = filterMode;
    }

    @Override
    public byte[] getFavoriteTrades() {

        return this.favoriteTrades;
    }

    @Override
    public void setFavoriteTrades(byte[] favoriteTrades) {

        this.favoriteTrades = favoriteTrades;