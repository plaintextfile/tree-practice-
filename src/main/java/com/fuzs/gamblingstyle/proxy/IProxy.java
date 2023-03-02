package com.fuzs.gamblingstyle.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IProxy {

    /**
     * @return Minecraft client or server instance
   