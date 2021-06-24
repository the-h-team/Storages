package com.github.sanctum.storages.exceptions;

import com.google.common.collect.ImmutableList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
