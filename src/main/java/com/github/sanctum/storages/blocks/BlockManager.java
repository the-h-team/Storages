package com.github.sanctum.storages.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.Container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockManager {
    private static final Map<BlockLocation, BlockManager> INSTANCES = new ConcurrentHashMap<>();
    private final BlockLocation blockLocation;

    private BlockManager(BlockLocation blockLocation) {
        this.blockLocation = blockLocation;
    }

    public <R> R queryContainer(Function<Container, R> queryFunction) {
        return queryFunction.apply(getContainer());
    }

    public void updateContainer(Consumer<Container> queryFunction) {
        queryFunction.andThen(Container::update).accept(getContainer());
    }

    private Container getContainer() throws IllegalStateException {
        final Container c;
        try {
            c = (Container) blockLocation.toBlock().getState();
        } catch (ClassCastException e) {
            throw new IllegalStateException(e);
        }
        return c;
    }

    public static BlockManager ofBlock(Block block) throws IllegalArgumentException {
        final BlockLocation blockLocation = BlockLocation.of(block);
        BlockManager blockManager = INSTANCES.get(blockLocation);
        if (blockManager != null) return blockManager;
        INSTANCES.put(blockLocation, (blockManager = new BlockManager(blockLocation)));
        return blockManager;
    }
}
