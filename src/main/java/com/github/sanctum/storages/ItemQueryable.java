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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * An object that may be tested for the presence of items.
 *
 * @since 1.0.0
 * @see ItemReceiver
 * @see ItemSource
 * @author ms5984
 */
public interface ItemQueryable {
    /**
     * Whether this object contains any items matching
     * the given material.
     *
     * @param material a material
     * @return true if any items match the given material
     * @throws ProviderException if the provider encounters an error
     */
    boolean contains(Material material) throws ProviderException;

    /**
     * Whether this object contains at least an amount
     * of the provided material.
     *
     * @param material a material
     * @param amount   an amount
     * @return true if at least amount of material is found
     * @throws ProviderException if the provider encounters an error
     */
    boolean containsAtLeast(Material material, int amount) throws ProviderException;

    /**
     * Whether this object contains a stack similar to the item passed.
     * <p>
     * Useful for stackable items.
     * <p>
     * The comparison performed is {@link ItemStack#isSimilar(ItemStack)}.
     *
     * @param similar an item
     * @return true if object contains a similar item
     * @throws ProviderException if the provider encounters an error
     */
    default boolean containsSimilar(ItemStack similar) throws ProviderException {
        return containsSimilar(similar, 1);
    }

    /**
     * Whether this object contains at least the amount specified
     * of the provided item.
     * <p>
     * Useful for stackable items.
     * <p>
     * The comparison performed is {@link ItemStack#isSimilar(ItemStack)}.
     *
     * @param similar an item
     * @param amount  the minimum number of similar items that must be present
     * @return true if object contains enough similar items
     * @throws ProviderException if the provider encounters an error
     */
    boolean containsSimilar(ItemStack similar, int amount) throws ProviderException;

    /**
     * Whether this object contains an exact match for the provided stack.
     * <p>
     * Matches meta and amount.
     * <p>
     * The comparison performed is {@link ItemStack#equals(Object)}.
     *
     * @param itemStack an ItemStack
     * @return true if an exact match was found
     * @throws ProviderException if the provider encounters an error
     */
    default boolean containsExact(ItemStack itemStack) throws ProviderException {
        return containsExact(itemStack, 1);
    }

    /**
     * Whether this object contains at least the amount specified
     * of the exact stack provided.
     * <p>
     * Matches meta and amount.
     * <p>
     * The comparison performed is {@link ItemStack#equals(Object)}.
     *
     * @param itemStack an ItemStack
     * @param amount    the number of stacks that must match
     * @return true if enough exact matches are found
     * @throws ProviderException if the provider encounters an error
     */
    boolean containsExact(ItemStack itemStack, int amount) throws ProviderException;
}
