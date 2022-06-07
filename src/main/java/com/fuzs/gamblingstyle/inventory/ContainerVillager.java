
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
        this.merchantInventory = new InventoryMerchant(this.player, merchant);
        this.addContainerSlots(playerInventory, merchant);
    }

    private void addContainerSlots(InventoryPlayer playerInventory, IMerchant merchant) {

        this.addSlotToContainer(new Slot(this.merchantInventory, 0, 76, 22));
        this.addSlotToContainer(new Slot(this.merchantInventory, 1, 76, 48));
        this.addSlotToContainer(new SlotMerchantResult(this.player, merchant, this.merchantInventory, 2, 134, 35));
        for (int i = 0; i < 3; ++i) {

            for (int j = 0; j < 9; ++j) {

                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {

            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public InventoryMerchant getMerchantInventory() {

        return this.merchantInventory;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {

        this.merchantInventory.resetRecipeAndSlots();
        // moved from super call
        this.detectAndSendChanges();
    }

    @Override
    public void setCurrentRecipeIndex(int currentRecipeIndex) {

        this.merchantInventory.setCurrentRecipeIndex(currentRecipeIndex);
    }

    @Override