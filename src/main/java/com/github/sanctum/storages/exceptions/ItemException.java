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
package com.github.sanctum.storages.exceptions;

import com.google.common.collect.ImmutableList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Thrown if a storage provider is interrupted or otherwise
 * unable to process an ItemStack-based operation.
 *
 * @since 1.0.0
 * @author ms5984
 */
public class ItemException extends Exception {
    private static final long serialVersionUID = -4135278454111555764L;
    private final List<ItemStack> items;

    public ItemException(List<ItemStack> items) {
        this.items = items;
    }

    public ImmutableList<ItemStack> getItems() {
        return (items instanceof ImmutableList) ? (ImmutableList<ItemStack>) items : ImmutableList.copyOf(items);
    }
}
