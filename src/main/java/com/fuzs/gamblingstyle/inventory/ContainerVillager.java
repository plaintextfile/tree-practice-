
package com.fuzs.gamblingstyle.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

// extend original merchant container to prevent class cast exception
@SuppressWarnings("NullableProblems")
public class ContainerVillager extends ContainerMerchant {

    private final IMerchant merchant;
    private final InventoryMerchant merchantInventory;
    private final World world;

    private final EntityPlayer player;
    private int lastIndex = 3;

    public ContainerVillager(InventoryPlayer playerInventory, IMerchant merchant, World worldIn) {

        super(playerInventory, merchant, worldIn);
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();

        this.merchant = merchant;
        this.world = worldIn;
        this.player = playerInventory.player;