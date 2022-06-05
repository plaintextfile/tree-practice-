
package com.fuzs.gamblingstyle.handler;

import com.fuzs.gamblingstyle.GamblingStyle;
import com.fuzs.gamblingstyle.capability.CapabilityController;
import com.fuzs.gamblingstyle.capability.container.ITradingInfo;
import com.fuzs.gamblingstyle.inventory.ContainerVillager;
import com.fuzs.gamblingstyle.network.NetworkHandler;
import com.fuzs.gamblingstyle.network.message.SOpenWindowMessage;
import com.fuzs.gamblingstyle.network.message.STradingListMessage;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

public class OpenContainerHandler {

    @SuppressWarnings({"unused", "unchecked"})
    @SubscribeEvent(priority = EventPriority.HIGH)
    public <T extends EntityLivingBase & IMerchant> void onContainerOpen(final PlayerContainerEvent.Open evt) {

        if (evt.getContainer() instanceof ContainerMerchant) {

            IMerchant merchant = this.getMerchant((ContainerMerchant) evt.getContainer());
            if (merchant instanceof EntityLivingBase && evt.getEntityPlayer() instanceof EntityPlayerMP) {

                EntityPlayerMP player = (EntityPlayerMP) evt.getEntityPlayer();