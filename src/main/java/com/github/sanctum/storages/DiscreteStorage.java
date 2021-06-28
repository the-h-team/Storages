package com.github.sanctum.storages;

import com.github.sanctum.storages.storage.StorageSlot;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class DiscreteStorage implements Storage {

    @Override
    public int getSize() {
        return getSlots().size();
    }

    public abstract List<StorageSlot> getSlots();

    public List<StorageSlot> findExact(ItemStack stack) {
        final ImmutableList.Builder<StorageSlot> builder = new ImmutableList.Builder<>();
        getSlots().stream()
                .filter(storageSlot -> storageSlot.getItem().filter(stack::equals).isPresent())
                .forEach(builder::add);
        return builder.build();
    }

    public List<StorageSlot> find(Material material) {
        final ImmutableList.Builder<StorageSlot> builder = new ImmutableList.Builder<>();
        getSlots().stream()
                .filter(storageSlot -> storageSlot.getItem().filter(i -> i.getType() == material).isPresent())
                .forEach(builder::add);
        return builder.build();
    }

    public StorageSlot getSlot(int index) throws IllegalArgumentException {
        final int size = getSlots().size();
        if (index > size || index < -size) throw new IllegalArgumentException("Slot index out of bounds!");
        return getSlots().get((Integer.signum(index) != -1) ? index : size + index);
    }

    public void clearSlot(int index) throws IllegalArgumentException {
        getSlot(index).setItem(null);
    }

    public void setItem(int index, ItemStack item) throws IllegalArgumentException {
        getSlot(index).setItem(item);
    }

    public abstract ItemStack[] getContents();
    public abstract void setContents(ItemStack[] items) throws IllegalArgumentException;
}
