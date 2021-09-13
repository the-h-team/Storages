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
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class StorageItem {

    public abstract Optional<@NotNull ItemStack> getItem() throws ProviderException;

    public abstract void setItem(@Nullable ItemStack item) throws ProviderException;

    public void set(Supplier<@Nullable ItemStack> supplier) throws ProviderException {
        setItem(supplier.get());
    }

    public void update(UnaryOperator<@Nullable ItemStack> updateOperation) throws ProviderException {
        setItem(updateOperation.apply(getItem().orElse(null)));
    }

    public Optional<@NotNull ItemStack> getAndUpdate(UnaryOperator<@Nullable ItemStack> updateOperation) throws ProviderException {
        final Optional<@NotNull ItemStack> original = getItem();
        update(updateOperation);
        return original;
    }

    public Optional<@NotNull ItemStack> updateAndGet(UnaryOperator<@Nullable ItemStack> updateOperation) throws ProviderException {
        update(updateOperation);
        return getItem();
    }
}
