
package com.fuzs.gamblingstyle.network.message;

import com.fuzs.gamblingstyle.GamblingStyle;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.Consumer;

public abstract class Message<T extends Message<T>> implements IMessage, IMessageHandler<T, T> {

    @Override
    public final T onMessage(T message, MessageContext ctx) {

        EntityPlayerMP serverPlayer = null;
        IThreadListener minecraftServer = null;
        if (ctx.side.isServer()) {

            NetHandlerPlayServer netHandlerPlayServer = ctx.getServerHandler();
            serverPlayer = netHandlerPlayServer.player;
            minecraftServer = serverPlayer.mcServer;
        }

        IThreadListener threadListener = GamblingStyle.proxy.getInstance(minecraftServer);
        EntityPlayer player = GamblingStyle.proxy.getPlayer(serverPlayer);
        threadListener.addScheduledTask(() -> message.process(player));

        return null;
    }

    @Override
    public final void toBytes(ByteBuf buf) {

        this.write(buf);
    }