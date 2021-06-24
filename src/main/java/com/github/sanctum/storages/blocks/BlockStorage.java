package com.github.sanctum.storages.blocks;

import com.github.sanctum.storages.Storage;
import com.github.sanctum.storages.exceptions.ItemException;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.ListIterator;

public class BlockStorage implements Storage {
    private final BlockManager blockManager;

    private BlockStorage(BlockManager blockManager) {
        this.blockManager = blockManager;
    }

    @Override
    public int getSize() {
        return blockManager.queryContainer(c -> c.getInventory().getSize());
    }

    @Override
    public @NotNull String getName() {
        return null;
    }

    @Override
    public void addItem(Collection<@NotNull ItemStack> items) throws ItemException {
        final Collection<ItemStack> values = blockManager.queryContainer(c -> c.getInventory().addItem(items.toArray(new ItemStack[0]))).values();
        if (!values.isEmpty()) {
            throw new ItemException(ImmutableList.copyOf(values));
        }
    }

    @Override
    public void removeItem(Collection<@NotNull ItemStack> items) throws ItemException {
        final Collection<ItemStack> values = blockManager.queryContainer(c -> c.getInventory().removeItem(items.toArray(new ItemStack[0]))).values();
        if (!values.isEmpty()) {
            throw new ItemException(ImmutableList.copyOf(values));
        }
    }

    @Override
    public boolean contains(Material material) {
        return blockManager.queryContainer(c -> c.getInventory().contains(material));
    }

    @Override
    public boolean containsAtLeast(Material material, int amount) {
        return blockManager.queryContainer(c -> c.getInventory().contains(material, amount));
    }

    @Override
    public boolean containsSimilar(ItemStack similar, int amount) {
        return blockManager.queryContainer(c -> c.getInventory().containsAtLeast(similar, amount));
    }

    @Override
    public boolean containsExact(ItemStack itemStack, int amount) {
        return blockManager.queryContainer(c -> c.getInventory().contains(itemStack, amount));
    }

    @Override
    public boolean remove(Material material) {
        return false;
    }

    @Override
    public boolean removeExact(ItemStack item) {
        return false;
    }

    @Override
    public void clear() {
    }

    @NotNull
    @Override
    public ListIterator<ItemStack> iterator() {
        return blockManager.queryContainer(c -> c.getInventory().iterator());
    }
}
