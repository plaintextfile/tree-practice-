
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
    public boolean canInteractWith(EntityPlayer playerIn) {

        return this.merchant.getCustomer() == playerIn;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot indexSlot = this.inventorySlots.get(index);
        if (indexSlot != null && indexSlot.getHasStack()) {

            ItemStack stackAtIndex = indexSlot.getStack();
            itemstack = stackAtIndex.copy();
            if (index == 2) {

                if (!this.mergeItemStack(stackAtIndex, 3, 39, true)) {

                    return ItemStack.EMPTY;
                }

                indexSlot.onSlotChange(stackAtIndex, itemstack);
            } else if (index != 0 && index != 1) {

                if (index >= 3 && index < 39) {

                    ItemStack itemToBuy = this.merchantInventory.getStackInSlot(0);
                    ItemStack secondItemToBuy = this.merchantInventory.getStackInSlot(1);
                    boolean flag = false;
                    if (!ItemStack.areItemsEqualIgnoreDurability(secondItemToBuy, stackAtIndex) || !itemToBuy.isEmpty()) {

                        flag = !this.mergeItemStack(stackAtIndex, 0, 1, false);
                    }

                    if ((!ItemStack.areItemsEqualIgnoreDurability(itemToBuy, stackAtIndex) || !secondItemToBuy.isEmpty())) {

                        flag = !this.mergeItemStack(stackAtIndex, 1, 2, false);
                    }

                    if (flag) {

                        return ItemStack.EMPTY;
                    } else {

                        this.onCraftMatrixChanged(this.merchantInventory);
                    }
                }
            } else if (!this.mergeItemStack(stackAtIndex, 3, 39, false)) {

                return ItemStack.EMPTY;
            }

            if (stackAtIndex.isEmpty()) {

                indexSlot.putStack(ItemStack.EMPTY);
            } else {

                indexSlot.onSlotChanged();
            }

            if (stackAtIndex.getCount() == itemstack.getCount()) {

                return ItemStack.EMPTY;
            }

            indexSlot.onTake(playerIn, stackAtIndex);
        }

        return itemstack;
    }

    public boolean areSlotsFilled() {

        return !this.merchantInventory.getStackInSlot(0).isEmpty() || !this.merchantInventory.getStackInSlot(1).isEmpty();
    }

    /**
     * Move trading slot contents to inventory so that a ghost recipe can properly be displayed
     */
    public void clearTradingSlots() {

        ItemStack itemToBuy = this.merchantInventory.getStackInSlot(0);
        ItemStack secondItemToBuy = this.merchantInventory.getStackInSlot(1);
        if (!itemToBuy.isEmpty()) {

            this.mergeItemStack(itemToBuy, 3, 39, true);
        }

        if (!secondItemToBuy.isEmpty()) {

            this.mergeItemStack(secondItemToBuy, 3, 39, true);
        }

        this.onCraftMatrixChanged(this.merchantInventory);
    }

    /**
     * Are the buy items switched in the trading slots
     */
    private boolean getItemsSwitched(ItemStack itemToBuy, ItemStack secondItemToBuy, ItemStack itemToBuyRecipe, ItemStack secondItemToBuyRecipe) {

        boolean itemToBuySwitched = !itemToBuy.isEmpty() && ItemStack.areItemsEqual(itemToBuy, secondItemToBuyRecipe);
        boolean secondItemToBuySwitched = !secondItemToBuy.isEmpty() && ItemStack.areItemsEqual(secondItemToBuy, itemToBuyRecipe);
        return (itemToBuySwitched || secondItemToBuySwitched) && !(ItemStack.areItemsEqual(itemToBuy, itemToBuyRecipe) || ItemStack.areItemsEqual(secondItemToBuy, secondItemToBuyRecipe));
    }

    /**
     * Handle item moving when a recipe button is clicked
     *
     * @param recipeIndex Id of the recipe belonging to the clicked button
     * @param clear       Force clearing trading slots
     * @param quickMove   Move as many items as possible
     * @param skipMove    Move output directly to player inventory
     */
    public void handleClickedButtonItems(int recipeIndex, boolean clear, boolean quickMove, boolean skipMove) {

        MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.player);
        if (merchantrecipelist != null && merchantrecipelist.size() > recipeIndex) {

            MerchantRecipe recipe = merchantrecipelist.get(recipeIndex);
            ItemStack itemToBuy = this.merchantInventory.getStackInSlot(0);
            ItemStack secondItemToBuy = this.merchantInventory.getStackInSlot(1);
            ItemStack itemToBuyRecipe = recipe.getItemToBuy();
            ItemStack secondItemToBuyRecipe = recipe.getSecondItemToBuy();
            boolean slotsSwitched = this.getItemsSwitched(itemToBuy, secondItemToBuy, itemToBuyRecipe, secondItemToBuyRecipe);
            if (!itemToBuy.isEmpty() && (clear && !skipMove || !ItemStack.areItemsEqual(itemToBuy, itemToBuyRecipe) && !slotsSwitched || !ItemStack.areItemsEqual(itemToBuy, secondItemToBuyRecipe) && slotsSwitched)) {

                if (!this.mergeItemStack(itemToBuy, 3, 39, true)) {

                    return;
                }

                this.merchantInventory.setInventorySlotContents(0, itemToBuy);
            }

            if (!secondItemToBuy.isEmpty() && (clear && !skipMove || !ItemStack.areItemsEqual(secondItemToBuy, secondItemToBuyRecipe) && !slotsSwitched || !ItemStack.areItemsEqual(secondItemToBuy, itemToBuyRecipe) && slotsSwitched)) {

                if (!this.mergeItemStack(secondItemToBuy, 3, 39, true)) {

                    return;
                }