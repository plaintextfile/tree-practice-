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

    private 