
package com.fuzs.gamblingstyle.capability.container;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface ITradingInfo extends INBTSerializable<NBTTagCompound> {

    int getLastTradeIndex();

    void setLastTradeIndex(int lastTradeIndex);
