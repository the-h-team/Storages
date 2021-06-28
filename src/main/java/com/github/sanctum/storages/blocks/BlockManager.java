package com.github.sanctum.storages.blocks;

import org.bukkit.block.Container;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockManager {
    private final AtomicReference<Container> container = new AtomicReference<>();

    public <R> R queryContainer(Function<Container, R> queryFunction) {
        return queryFunction.apply(getContainer());
    }

    public void updateContainer(Consumer<Container> queryFunction) {
        queryFunction.accept(getContainer());
    }

    private Container getContainer() {
        return container.get(); // TODO: update this reference
    }
}
