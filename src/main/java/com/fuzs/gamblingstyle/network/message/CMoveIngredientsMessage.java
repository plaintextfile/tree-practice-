package com.fuzs.gamblingstyle.network.message;

import com.fuzs.gamblingstyle.inventory.ContainerVillager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class CMoveIngredientsMessage extends Message<CMoveIngredientsMessage> {

    private int currentRecipeIndex;
    private boolean clearSlots;
    private boolean quickMove;
    private boolean skipMove;

    @SuppressWarnings("unused")
    public CMoveIngredientsMessage() {

    }

    public CMoveIngredientsMessage(int currentRecipeIndex, boolean clearSlots, boolean quickMove, boolean skipMove) {

        this.currentRecipeIndex = currentRecipeIndex;
        this.clearSlots = clearSlots;
        this.quickMove