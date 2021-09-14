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
package com.github.sanctum.storages.storage;

import com.github.sanctum.storages.exceptions.ProviderException;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a mutable ItemStack during storage iteration.
 * <p>
 * <b>Acts as a data-access object; edits are delegated to the provider.</b>
 *
 * @since 1.0.0
 * @author ms5984
 */
public abstract class StorageItem {

    /**
     * Get the storage item contents.
     *
     * @return an Optional describing the storage item contents
     * @throws ProviderException if the provider encounters an error
     */
    public abstract Optional<@NotNull ItemStack> getItem() throws ProviderException;

    /**
     * Set the storage item contents using an existing ItemStack.
     *
     * @param item the new contents
     * @throws ProviderException if the provider encounters an error
     */
    public abstract void setItem(@Nullable ItemStack item) throws ProviderException;

    /**
     * Set the storage item contents using the output of a Supplier.
     *
     * @param supplier an ItemStack-producing operation
     * @throws ProviderException if the provider encounters an error
     */
    public void set(Supplier<@Nullable ItemStack> supplier) throws ProviderException {
        setItem(supplier.get());
    }

    /**
     * Update the storage item contents by applying the provided operation.
     *
     * @param updateOperation update function to apply
     * @throws ProviderException if the provider encounters an error
     */
    public void update(Function<@Nullable ItemStack, @Nullable ItemStack> updateOperation) throws ProviderException {
        setItem(updateOperation.apply(getItem().orElse(null)));
    }

    /**
     * Get the original contents and subsequently update the storage item.
     *
     * @param updateOperation update function to apply
     * @return an Optional describing the original storage item contents
     * @throws ProviderException if the provider encounters an error
     */
    public Optional<@NotNull ItemStack> getAndUpdate(Function<@Nullable ItemStack, @Nullable ItemStack> updateOperation) throws ProviderException {
        final Optional<@NotNull ItemStack> original = getItem();
        update(updateOperation);
        return original;
    }

    /**
     * Update the storage item and get the subsequent result.
     *
     * @param updateOperation update function to apply
     * @return an Optional describing the new storage item contents
     * @throws ProviderException if the provider encounters an error
     */
    public Optional<@NotNull ItemStack> updateAndGet(Function<@Nullable ItemStack, @Nullable ItemStack> updateOperation) throws ProviderException {
        update(updateOperation);
        return getItem();
    }
}
