
package com.fuzs.gamblingstyle.capability.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

/**
 * dispatcher for this serializable capability
 * @param <T> capability class
 */
public class CapabilityDispatcher<T extends INBTSerializable<NBTTagCompound>> implements ICapabilitySerializable<NBTTagCompound> {

    /**
     * capability wrapper for object
     */
    private final Capability<T> capability;
    /**
     * capability object
     */
    private final T storage;