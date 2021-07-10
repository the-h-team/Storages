/*
 *  This file is part of storages.
 *
 *  Copyright 2021 ms5984 (Matt) <https://github.com/ms5984>
 *  Copyright 2021 the-h-team (Sanctum) <https://github.com/the-h-team>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.sanctum.storages;

import com.github.sanctum.storages.exceptions.ProviderException;
import com.github.sanctum.storages.storage.StorageSlot;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.ListIterator;

/**
 * Represents an iterable item storage with defined slot indexes.
 * <p>
 * Defines ListIterator override with subclass {@link StorageSlot}
 * to provide access to slot index while iterating.
 *
 * @since 1.0.0
 * @author ms5984
 */
public abstract class DiscreteStorage implements Storage<StorageSlot> {

    /**
     * Get a list of all slots in this storage.
     * <p>
     * {@link StorageSlot StorageSlots} are designed as data-access objects.
     *
     * @return immutable list of all slots in this storage
     */
    public abstract List<StorageSlot> getSlots();

    /**
     * Get a list of slots whose contents match the
     * provided {@link ItemStack} exactly.
     * <p>
     * Matches meta and amount.
     *
     * @param stack an ItemStack to match exactly
     * @return sublist of slots whose contents match stack
     * @throws ProviderException if the provider encounters an error
     */
    public List<StorageSlot> findExact(ItemStack stack) throws ProviderException {
        final ImmutableList.Builder<StorageSlot> builder = new ImmutableList.Builder<>();
        for (StorageSlot storageSlot : getSlots()) {
            if (storageSlot.getItem().filter(stack::equals).isPresent()) {
                builder.add(storageSlot);
            }
        }
        return builder.build();
    }

    /**
     * Get a list of slots whose contents match
     * the provided {@link Material}.
     *
     * @param material a material to match
     * @return sublist of slots whose contents match type
     * @throws ProviderException if the provider encounters an error
     */
    public List<StorageSlot> find(Material material) throws ProviderException {
        final ImmutableList.Builder<StorageSlot> builder = new ImmutableList.Builder<>();
        for (StorageSlot storageSlot : getSlots()) {
            if (storageSlot.getItem().filter(i -> i.getType() == material).isPresent()) {
                builder.add(storageSlot);
            }
        }
        return builder.build();
    }

    /**
     * Get the {@link StorageSlot} at the provided index.
     * <p>
     * Negative values start at the end of the storage and
     * count backward; -1 returns the last slot, -2 the
     * second-to-last and so on.
     *
     * @param index the index of the slot
     * @return StorageSlot data-access object at the provided index
     * @throws IllegalArgumentException if index >= getSlots().size()
     * or index < -(getSlots().size())
     */
    public StorageSlot getSlot(int index) throws IllegalArgumentException {
        final int size = getSlots().size();
        if (index > size || index < -size) throw new IllegalArgumentException("Slot index out of bounds!");
        return getSlots().get((Integer.signum(index) != -1) ? index : size + index);
    }

    /**
     * Clear the item at the provided slot index.
     * <p>
     * Negative values start at the end of the storage and
     * count backward; -1 returns the last slot, -2 the
     * second-to-last and so on.
     *
     * @param index the index of the slot
     * @throws ProviderException if the provider encounters an error
     * @throws IllegalArgumentException if index >= getSlots().size()
     * or index < -(getSlots().size())
     */
    public void clearSlot(int index) throws ProviderException, IllegalArgumentException {
        getSlot(index).setItem(null);
    }

    /**
     * Set the item at the provided slot index.
     * <p>
     * Negative values start at the end of the storage and
     * count backward; -1 returns the last slot, -2 the
     * second-to-last and so on.
     *
     * @param index the index of the slot
     * @param item the new contents
     * @throws ProviderException if the provider encounters an error
     * @throws IllegalArgumentException if index >= getSlots().size()
     * or index < -(getSlots().size())
     */
    public void setItem(int index, @Nullable ItemStack item) throws ProviderException, IllegalArgumentException {
        getSlot(index).setItem(item);
    }

    /**
     * Get the contents of the storage as an array of ItemStacks.
     * <p>
     * Individual elements may be null.
     *
     * @return an array of {@link ItemStack ItemStacks}
     * @throws ProviderException if the provider encounters an error
     */
    public abstract ItemStack[] getContents() throws ProviderException;

    /**
     * Set the contents of the storage as an array of ItemStacks.
     * <p>
     * Individual elements may be null.
     * <p>
     * Array must be less than or equal to size of storage.
     *
     * @param items an array of {@link ItemStack ItemStacks}
     * @throws ProviderException if the provider encounters an error
     * @throws IllegalArgumentException if index >= getSlots().size()
     * or index < -(getSlots().size())
     */
    public abstract void setContents(ItemStack[] items) throws ProviderException, IllegalArgumentException;

    @Override
    public @NotNull ListIterator<StorageSlot> iterator() {
        return getSlots().listIterator();
    }
}
