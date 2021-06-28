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
package com.github.sanctum.storages.blocks;

import com.github.sanctum.storages.DiscreteStorage;
import com.github.sanctum.storages.exceptions.ItemException;
import com.github.sanctum.storages.storage.StorageSlot;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class BlockStorage extends DiscreteStorage {
    private final BlockManager blockManager;
    private final ImmutableList<StorageSlot> slotDAOs;

    private BlockStorage(BlockManager blockManager) {
        this.blockManager = blockManager;
        final ImmutableList.Builder<StorageSlot> slots = new ImmutableList.Builder<>();
        final int size = getSize();
        for (int index = 0; index < size; ++index) {
            slots.add(new StorageSlot(index) {
                @Override
                public Optional<@NotNull ItemStack> getItem() {
                    return Optional.ofNullable(blockManager.queryContainer(c -> c.getInventory().getItem(index)));
                }

                @Override
                public void setItem(@Nullable ItemStack item) {
                    blockManager.updateContainer(c -> c.getInventory().setItem(index, item));
                }
            });
        }
        this.slotDAOs = slots.build();
    }

    @Override
    public int getSize() {
        return blockManager.queryContainer(c -> c.getInventory().getSize());
    }

    @Override
    public List<StorageSlot> getSlots() {
        return slotDAOs;
    }

    @Override
    public ItemStack[] getContents() {
        return blockManager.queryContainer(c -> c.getInventory().getContents());
    }

    @Override
    public void setContents(ItemStack[] items) throws IllegalArgumentException {
        blockManager.updateContainer(c -> c.getInventory().setContents(items));
    }

    @Override
    public @NotNull String getName() {
        return blockManager.queryContainer(c -> c.getType().toString() + '[' + c.getLocation().toString() + ']');
    }

    @Override
    public void addItem(Collection<@NotNull ItemStack> items) throws ItemException {
        final Collection<ItemStack> values = blockManager.queryContainer(c -> c.getInventory().addItem(items.toArray(new ItemStack[0]))).values();
        if (!values.isEmpty()) {
            throw new ItemException(ImmutableList.copyOf(values));
        }
    }

    @Override
    public void removeItem(Collection<@NotNull ItemStack> items) throws ItemException {
        final Collection<ItemStack> values = blockManager.queryContainer(c -> c.getInventory().removeItem(items.toArray(new ItemStack[0]))).values();
        if (!values.isEmpty()) {
            throw new ItemException(ImmutableList.copyOf(values));
        }
    }

    @Override
    public boolean contains(Material material) {
        return blockManager.queryContainer(c -> c.getInventory().contains(material));
    }

    @Override
    public boolean containsAtLeast(Material material, int amount) {
        return blockManager.queryContainer(c -> c.getInventory().contains(material, amount));
    }

    @Override
    public boolean containsSimilar(ItemStack similar, int amount) {
        return blockManager.queryContainer(c -> c.getInventory().containsAtLeast(similar, amount));
    }

    @Override
    public boolean containsExact(ItemStack itemStack, int amount) {
        return blockManager.queryContainer(c -> c.getInventory().contains(itemStack, amount));
    }

    @Override
    public boolean remove(Material material) {
        boolean removedAny = false;
        final ListIterator<ItemStack> iterator = blockManager.queryContainer(c -> c.getInventory().iterator());
        while (iterator.hasNext()) {
            final ItemStack next = iterator.next();
            if (next != null && next.getType() == material) {
                if (!removedAny) removedAny = true;
                iterator.set(null);
            }
        }
        return removedAny;
    }

    @Override
    public boolean removeExact(ItemStack item) {
        if (item == null) return false;
        boolean removedAny = false;
        final ListIterator<ItemStack> iterator = blockManager.queryContainer(c -> c.getInventory().iterator());
        while (iterator.hasNext()) {
            final ItemStack next = iterator.next();
            if (item.equals(next)) {
                if (!removedAny) removedAny = true;
                iterator.set(null);
            }
        }
        return removedAny;
    }

    @Override
    public void clear() {
        blockManager.updateContainer(c -> c.getInventory().clear());
    }
}
