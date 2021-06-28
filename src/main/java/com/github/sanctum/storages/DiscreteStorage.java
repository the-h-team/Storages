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

import com.github.sanctum.storages.storage.StorageSlot;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public int getSize() {
        return getSlots().size();
    }

    public abstract List<StorageSlot> getSlots();

    public List<StorageSlot> findExact(ItemStack stack) {
        final ImmutableList.Builder<StorageSlot> builder = new ImmutableList.Builder<>();
        getSlots().stream()
                .filter(storageSlot -> storageSlot.getItem().filter(stack::equals).isPresent())
                .forEach(builder::add);
        return builder.build();
    }

    public List<StorageSlot> find(Material material) {
        final ImmutableList.Builder<StorageSlot> builder = new ImmutableList.Builder<>();
        getSlots().stream()
                .filter(storageSlot -> storageSlot.getItem().filter(i -> i.getType() == material).isPresent())
                .forEach(builder::add);
        return builder.build();
    }

    public StorageSlot getSlot(int index) throws IllegalArgumentException {
        final int size = getSlots().size();
        if (index > size || index < -size) throw new IllegalArgumentException("Slot index out of bounds!");
        return getSlots().get((Integer.signum(index) != -1) ? index : size + index);
    }

    public void clearSlot(int index) throws IllegalArgumentException {
        getSlot(index).setItem(null);
    }

    public void setItem(int index, ItemStack item) throws IllegalArgumentException {
        getSlot(index).setItem(item);
    }

    public abstract ItemStack[] getContents();
    public abstract void setContents(ItemStack[] items) throws IllegalArgumentException;

    @Override
    public @NotNull ListIterator<StorageSlot> iterator() {
        return getSlots().listIterator();
    }
}
