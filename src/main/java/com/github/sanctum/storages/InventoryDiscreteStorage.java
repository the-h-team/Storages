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

import com.github.sanctum.storages.exceptions.InventoryHolderException;
import com.github.sanctum.storages.exceptions.ItemException;
import com.github.sanctum.storages.exceptions.ProviderException;
import com.github.sanctum.storages.storage.StorageSlot;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An abstract implementation of common logic shared by all
 * InventoryHolder-based DiscreteStorages.
 *
 * @since 1.0.0
 * @author ms5984
 */
public abstract class InventoryDiscreteStorage extends DiscreteStorage {
    protected final InventoryManager<?> manager;
    protected final ImmutableList<StorageSlot> slotDAOs;

    protected InventoryDiscreteStorage(InventoryManager<?> provider) throws ProviderException {
        this.manager = provider;
        final ImmutableList.Builder<StorageSlot> slots = new ImmutableList.Builder<>();
        final int size = getSize();
        for (int index = 0; index < size; ++index) {
            slots.add(new StorageSlot(index) {
                @Override
                public Optional<@NotNull ItemStack> getItem() throws InventoryHolderException {
                    return Optional.ofNullable(manager.query(holder -> holder.getInventory().getItem(index)));
                }

                @Override
                public void setItem(@Nullable ItemStack item) throws InventoryHolderException {
                    manager.update(p -> p.getInventory().setItem(index, item));
                }
            });
        }
        this.slotDAOs = slots.build();
    }

    @Override
    public List<StorageSlot> getSlots() {
        return slotDAOs;
    }

    @Override
    public ItemStack[] getContents() throws InventoryHolderException {
        return manager.query(holder -> holder.getInventory().getStorageContents());
    }

    @Override
    public void setContents(ItemStack[] items) throws InventoryHolderException, IllegalArgumentException {
        manager.update(holder -> holder.getInventory().getStorageContents());
    }

    @Override
    public int getSize() throws InventoryHolderException {
        return manager.query(holder -> holder.getInventory().getStorageContents().length);
    }

    @Override
    public void clear() throws InventoryHolderException {
        manager.update(holder -> holder.getInventory().clear());
    }

    @Override
    public boolean contains(Material material) throws InventoryHolderException {
        return manager.query(holder -> holder.getInventory().contains(material));
    }

    @Override
    public boolean containsAtLeast(Material material, int amount) throws InventoryHolderException {
        return manager.query(holder -> holder.getInventory().contains(material, amount));
    }

    @Override
    public boolean containsSimilar(ItemStack similar, int amount) throws InventoryHolderException {
        return manager.query(holder -> holder.getInventory().containsAtLeast(similar, amount));
    }

    @Override
    public boolean containsExact(ItemStack itemStack, int amount) throws InventoryHolderException {
        return manager.query(holder -> holder.getInventory().contains(itemStack, amount));
    }

    @Override
    public void addItem(Collection<@NotNull ItemStack> items) throws InventoryHolderException, ItemException {
        final Collection<ItemStack> values = manager.query(holder -> holder.getInventory().addItem(items.toArray(new ItemStack[0]))).values();
        if (!values.isEmpty()) {
            throw new ItemException(ImmutableList.copyOf(values));
        }
    }

    @Override
    public void removeItem(Collection<@NotNull ItemStack> items) throws InventoryHolderException, ItemException {
        final Collection<ItemStack> values = manager.query(holder -> holder.getInventory().removeItem(items.toArray(new ItemStack[0]))).values();
        if (!values.isEmpty()) {
            throw new ItemException(ImmutableList.copyOf(values));
        }
    }

    @Override
    public boolean remove(Material material) throws ProviderException {
        final ListIterator<StorageSlot> iterator = iterator();
        boolean anyRemoved = false;
        while (iterator.hasNext()) {
            final StorageSlot next = iterator.next();
            if (next.getItem().filter(itemStack -> itemStack.getType() == material).isPresent()) {
                next.setItem(null);
                if (!anyRemoved) anyRemoved = true;
            }
        }
        return anyRemoved;
    }

    @Override
    public boolean removeExact(ItemStack item) throws ProviderException {
        final ListIterator<StorageSlot> iterator = iterator();
        boolean anyRemoved = false;
        while (iterator.hasNext()) {
            final StorageSlot next = iterator.next();
            if (next.getItem().filter(item::equals).isPresent()) {
                next.setItem(null);
                if (!anyRemoved) anyRemoved = true;
            }
        }
        return anyRemoved;
    }

    public static abstract class InventoryManager<T extends InventoryHolder & Nameable> {

        public <R> R query(Function<T, R> queryFunction) throws InventoryHolderException {
            return queryFunction.apply(validate());
        }

        public void update(Consumer<T> queryFunction) throws InventoryHolderException {
            queryFunction.accept(validate());
        }

        protected abstract T validate() throws InventoryHolderException;
    }
}
