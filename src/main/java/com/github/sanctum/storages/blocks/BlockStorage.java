package com.github.sanctum.storages.blocks;

import com.github.sanctum.storages.Storage;
import com.github.sanctum.storages.exceptions.ItemException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

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

    }

    @Override
    public void removeItem(Collection<@NotNull ItemStack> items) throws ItemException {

    }

    @Override
    public boolean contains(Material material) {
        return false;
    }

    @Override
    public boolean containsAtLeast(Material material, int amount) {
        return false;
    }

    @Override
    public boolean containsSimilar(ItemStack similar, int amount) {
        return false;
    }

    @Override
    public boolean containsExact(ItemStack itemStack, int amount) {
        return false;
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
    public Iterator<ItemStack> iterator() {
        return null;
    }
}
