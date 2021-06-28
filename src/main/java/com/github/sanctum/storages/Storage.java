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
import com.github.sanctum.storages.storage.StorageItem;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
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
public interface Storage<T extends StorageItem> extends Iterable<T> {
    /**
     * Get the capacity of the storage.
     *
     * @return capacity of the storage
     */
    int getSize();

    /**
     * Get the name of the storage.
     *
     * @return name of the storage
     */
    @NotNull String getName();

    /**
     * Add a single ItemStack to the storage.
     *
     * @param item an ItemStack
     * @throws ItemException if unable to add the item
     */
    default void addItem(@NotNull ItemStack item) throws ItemException {
        addItem(ImmutableList.of(item));
    }
    /**
     * Add a collection of ItemStacks to the storage.
     *
     * @param items a collection of ItemStacks
     * @throws ItemException if unable to add all items
     */
    void addItem(Collection<@NotNull ItemStack> items) throws ItemException;

    /**
     * Remove a single ItemStack from the storage.
     *
     * @param item an ItemStack
     * @throws ItemException if unable to remove the item
     */
    default void removeItem(@NotNull ItemStack item) throws ItemException {
        removeItem(ImmutableList.of(item));
    }
    /**
     * Remove a collection of ItemStacks from the storage.
     *
     * @param items a collection of ItemStacks
     * @throws ItemException if unable to remove all items
     */
    void removeItem(Collection<@NotNull ItemStack> items) throws ItemException;

    /**
     * Whether this storage contains any items matching
     * the given material.
     *
     * @param material a material
     * @return true if any items match the given material
     */
    boolean contains(Material material);
    /**
     * Whether this storage contains at least an amount
     * of the provided material.
     *
     * @param material a material
     * @param amount an amount
     * @return true if at least amount of material is found
     */
    boolean containsAtLeast(Material material, int amount);
    /**
     * Whether this storage contains a stack similar to the item passed.
     * <p>
     * Useful for stackable items.
     * <p>
     * The comparison performed is {@link ItemStack#isSimilar(ItemStack)}.
     *
     * @param similar an item
     * @return true if this storage contains a similar item
     */
    default boolean containsSimilar(ItemStack similar) {
        return containsSimilar(similar, 1);
    }
    /**
     * Whether this storage contains at least the amount specified
     * of the provided item.
     * <p>
     * Useful for stackable items.
     * <p>
     * The comparison performed is {@link ItemStack#isSimilar(ItemStack)}.
     *
     * @param similar an item
     * @param amount the minimum number of similar items that must be present
     * @return true if this storage contains enough similar items
     */
    boolean containsSimilar(ItemStack similar, int amount);
    /**
     * Whether this storage contains an exact match for the provided stack.
     * <p>
     * Matches meta and amount.
     * <p>
     * The comparison performed is {@link ItemStack#equals(Object)}.
     *
     * @param itemStack an ItemStack
     * @return true if an exact match was found
     */
    default boolean containsExact(ItemStack itemStack) {
        return containsExact(itemStack, 1);
    }
    /**
     * Whether this storage contains at least the amount specified
     * of the exact stack provided.
     * <p>
     * Matches meta and amount.
     * <p>
     * The comparison performed is {@link ItemStack#equals(Object)}.
     *
     * @param itemStack an ItemStack
     * @param amount the number of stacks that must match
     * @return true if enough exact matches are found
     */
    boolean containsExact(ItemStack itemStack, int amount);

    /**
     * Remove all matches of a Material.
     *
     * @param material a material
     * @return true if any items were removed
     */
    boolean remove(Material material);
    /**
     * Remove all exact matches of an ItemStack.
     * <p>
     * Matches meta and amount.
     *
     * @param item an ItemStack
     * @return true if any items were removed
     */
    boolean removeExact(ItemStack item);

    /**
     * Clear the entire storage.
     */
    void clear();

    /**
     * Return a ListIterator which is able to traverse the storage
     * and process item modifications.
     *
     * @return a ListIterator
     */
    @Override
    @NotNull ListIterator<T> iterator();
}
