package com.fuzs.gamblingstyle.network.message;

import com.fuzs.gamblingstyle.GamblingStyle;
import com.fuzs.gamblingstyle.client.gui.GuiVillager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.village.MerchantRecipeList;

import java.io.IOException;

public class STradingListMessage extends Message<STradingListMessage> {

    private PacketBuffer data;

    @SuppressWarnings("unused")
    public STradingListMessage() {

    }

    public STradingListMessage(PacketBuffer bufIn) {

        this.data 