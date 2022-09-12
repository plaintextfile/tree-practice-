
package com.fuzs.gamblingstyle.network.message;

import com.fuzs.gamblingstyle.inventory.ContainerVillager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class CSelectedRecipeMessage extends Message<CSelectedRecipeMessage> {

    private int currentRecipeIndex;
    private boolean clearSlots;

    @SuppressWarnings("unused")
    public CSelectedRecipeMessage() {

    }

    public CSelectedRecipeMessage(int currentRecipeIndex, boolean clearSlots) {

        this.currentRecipeIndex = currentRecipeIndex;
        this.clearSlots = clearSlots;
    }

    @Override
    public void write(ByteBuf buf) {

        buf.writeInt(this.currentRecipeIndex);