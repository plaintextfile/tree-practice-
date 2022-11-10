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

        this.data = bufIn;
        if (bufIn.writerIndex() > 1048576) {

            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }

    @Override
    public void read(ByteBuf buf) {

        int bytes = buf.readableBytes();
        if (bytes >= 0 && bytes <= 1048576) {

            this.data = new PacketBuffer(buf.readBytes(bytes));
        } else {

            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }

    @Override
    public void write(ByteBuf buf) {

        synchronized (this.data) {

            this.data.markReaderIndex();
            buf.writeBytes(this.data);
            this.data.resetRead