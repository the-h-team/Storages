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

import com.github.sanctum.storages.exceptions.ItemException;
import com.github.sanctum.storages.exceptions.ProviderException;
import com.github.sanctum.storages.storage.StorageItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a group of storages.
 * <p>
 * Type parameter allows for potential discrete storage element
 * access as well as basic {@link StorageItem} functionality.
 *
 * @since 1.0.0
 * @author ms5984
 * @param <T> element type
 */
public interface StorageGroup<T extends StorageItem> extends Storage<T> {
    /**
     * Add a storage to this group.
     *
     * @param storage storage to add
     */
    void addStorage(Storage<? extends T> storage);

    /**
     * Remove a storage from this group.
     *
     * @param storage storage to remove
     */
    void removeStorage(Storage<? extends T> storage);

    /**
     * Get a list of all storages in this group.
     *
     * @return list of all storages in this group
     */
    List<Storage<? extends T>> getStorages();

    @Override
    default int getSize() throws ProviderException {
        int i = 0;
        for (Storage<? extends T> storage : getStorages()) {
            i += storage.getSize();
        }
        return i;
    }

    @Override
    default void clear() throws ProviderException {
        for (Storage<? extends T> storage : getStorages()) {
            storage.clear();
        }
    }

    @Override
    default boolean contains(Material material) throws ProviderException {
        for (Storage<? extends T> storage : getStorages()) {
            if (storage.contains(material)) {
                return true;
            }
        }
        return false;
    }

    @Override
    default boolean containsAtLeast(Material material, int amount) throws ProviderException {
        for (Storage<? extends T> storage : getStorages()) {
            if (storage.containsAtLeast(material, amount)) {
                return true;
            }
        }
        return false;
    }

    @Override
    default boolean containsSimilar(ItemStack similar, int amount) throws ProviderException {
        for (Storage<? extends T> storage : getStorages()) {
            if (storage.containsSimilar(similar, amount)) {
                return true;
            }
        }
        return false;
    }

    @Override
    default boolean containsExact(ItemStack itemStack, int amount) throws ProviderException {
        for (Storage<? extends T> storage : getStorages()) {
            if (storage.containsExact(itemStack, amount)) {
                return true;
            }
        }
        return false;
    }

    @Override
    default void addItem(Collection<@NotNull ItemStack> items) throws ProviderException, ItemException {
        final Iterator<Storage<? extends T>> iterator = getStorages().iterator();
        List<ItemStack> itemStacks = new ArrayList<>(items);
        while (iterator.hasNext()) {
            final Storage<? extends T> next = iterator.next();
            try {
                next.addItem(itemStacks);
            } catch (ProviderException e) {
                if (!iterator().hasNext()) throw e;
            } catch (ItemException e) {
                if (!iterator.hasNext()) throw e;
                itemStacks = e.getItems();
            }
        }
    }

    @Override
    default void removeItem(Collection<@NotNull ItemStack> items) throws ProviderException, ItemException {
        final Iterator<Storage<? extends T>> iterator = getStorages().iterator();
        List<ItemStack> itemStacks = new ArrayList<>(items);
        while (iterator.hasNext()) {
            final Storage<? extends T> next = iterator.next();
            try {
                next.removeItem(itemStacks);
            } catch (ProviderException e) {
                if (!iterator().hasNext()) throw e;
            } catch (ItemException e) {
                if (!iterator.hasNext()) throw e;
                itemStacks = e.getItems();
            }
        }
    }

    @Override
    default boolean remove(Material material) throws ProviderException {
        boolean removed = false;
        for (Storage<? extends T> storage : getStorages()) {
            if (storage.remove(material)) {
                if (!removed) {
                    removed = true;
                }
            }
        }
        return removed;
    }

    @Override
    default boolean removeExact(ItemStack item) throws ProviderException {
        boolean removed = false;
        for (Storage<? extends T> storage : getStorages()) {
            if (storage.removeExact(item)) {
                if (!removed) {
                    removed = true;
                }
            }
        }
        return removed;
    }
}
