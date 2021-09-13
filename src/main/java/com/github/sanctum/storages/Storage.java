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

import com.github.sanctum.storages.exceptions.ProviderException;
import com.github.sanctum.storages.storage.StorageItem;
import org.jetbrains.annotations.NotNull;

import java.util.ListIterator;

/**
 * Represents a dynamic item storage which may be iterated upon.
 * <p>
 * Does not directly expose its slots by their position but
 * allows for the addition, removal, testing of items, as well as
 * the clearing of all items.
 * <p>
 * Type-bound allows for {@link StorageItem} and any of its
 * subclasses to be selected by concrete implementations.
 *
 * @since 1.0.0
 * @see DiscreteStorage
 * @author ms5984
 */
public interface Storage<T extends StorageItem> extends Iterable<T>, ItemReceiver, ItemSource, ItemQueryable {
    /**
     * Get the name of the storage.
     *
     * @return name of the storage
     * @throws ProviderException if the provider encounters an error
     */
    @NotNull String getName() throws ProviderException;

    /**
     * Get the capacity of the storage.
     *
     * @return capacity of the storage
     * @throws ProviderException if the provider encounters an error
     */
    int getSize() throws ProviderException;

    /**
     * Clear the entire storage.
     * @throws ProviderException if the provider encounters an error
     */
    void clear() throws ProviderException;

    /**
     * Return a ListIterator which is able to traverse the storage
     * and process item modifications.
     *
     * @return a ListIterator
     */
    @Override
    @NotNull ListIterator<T> iterator();
}
