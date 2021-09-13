/*
 *  This file is part of Storages.
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
import com.google.common.collect.ImmutableList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * An object to which items may be given.
 *
 * @since 1.0.0
 * @see ItemSource
 * @see ItemQueryable
 * @author ms5984
 */
public interface ItemReceiver {
    /**
     * Add a single ItemStack to this object.
     *
     * @param item an ItemStack
     * @throws ProviderException if the provider encounters an error
     * @throws ItemException if unable to add the item
     */
    default void addItem(@NotNull ItemStack item) throws ProviderException, ItemException {
        addItem(ImmutableList.of(item));
    }

    /**
     * Add a collection of ItemStacks to this object.
     *
     * @param items a collection of ItemStacks
     * @throws ProviderException if the provider encounters an error
     * @throws ItemException if unable to add all items
     */
    void addItem(Collection<@NotNull ItemStack> items) throws ProviderException, ItemException;
}
