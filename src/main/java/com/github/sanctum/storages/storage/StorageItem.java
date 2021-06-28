package com.github.sanctum.storages.storage;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class StorageItem {

    public abstract Optional<@NotNull ItemStack> getItem();

    public abstract void setItem(@Nullable ItemStack item);

    public void set(Supplier<@Nullable ItemStack> supplier) {
        setItem(supplier.get());
    }

    public void update(UnaryOperator<@Nullable ItemStack> updateOperation) {
        setItem(updateOperation.apply(getItem().orElse(null)));
    }

    public Optional<@NotNull ItemStack> getAndUpdate(UnaryOperator<@Nullable ItemStack> updateOperation) {
        final Optional<@NotNull ItemStack> original = getItem();
        update(updateOperation);
        return original;
    }

    public Optional<@NotNull ItemStack> updateAndGet(UnaryOperator<@Nullable ItemStack> updateOperation) {
        update(updateOperation);
        return getItem();
    }
}
