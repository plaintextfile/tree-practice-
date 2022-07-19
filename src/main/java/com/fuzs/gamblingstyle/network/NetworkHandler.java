package com.fuzs.gamblingstyle.network;

import com.fuzs.gamblingstyle.GamblingStyle;
import com.fuzs.gamblingstyle.network.message.Message;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class NetworkHandler {

    private static final SimpleNetworkWrapper MAIN_CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(GamblingStyle.MODID);
    private static final AtomicInteger DISCRIMINATOR = new AtomicInteger();

    private static NetworkHandler instance;

    public <T extends Message<T>> void registerMessage(Class<T> messageType, Side receivingSide) {

        MAIN_CHANNEL.registerMessage(messageType, messageType, DISCRIMINATOR.getAndIncrement(), receivingSide);
    }

    public void sendToServer(Message<?> message) {

        MAIN_CHANNEL.sendToServer(message);
    }

    public void sendTo(Message<?> message, EntityPlayerMP player) {

        MAIN_CHANNEL.sendTo(message, player);
    }

    public void sendToAll(Message<?> message) {

        MAIN_CHANNEL.sendToAll(message);
    }

    public void sendToAllNear(M