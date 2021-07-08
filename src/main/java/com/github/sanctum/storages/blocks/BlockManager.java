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
package com.github.sanctum.storages.blocks;

import com.github.sanctum.storages.InventoryDiscreteStorage.InventoryManager;
import com.github.sanctum.storages.exceptions.InventoryHolderException;
import org.bukkit.block.Block;
import org.bukkit.block.Container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class BlockManager extends InventoryManager<Container> {
    private static final Map<BlockLocation, BlockManager> INSTANCES = new ConcurrentHashMap<>();
    private final BlockLocation blockLocation;

    private BlockManager(BlockLocation blockLocation) {
        this.blockLocation = blockLocation;
    }

    @Override
    public void update(Consumer<Container> queryFunction) throws InventoryHolderException {
        super.update(queryFunction.andThen(Container::update));
    }

    @Override
    protected Container validate() throws InventoryHolderException {
        final Container c;
        try {
            c = (Container) blockLocation.toBlock().getState();
        } catch (ClassCastException e) {
            throw new InventoryHolderException("Unable to cast BlockState to Container. The block probably changed.", e);
        }
        return c;
    }

    public static BlockManager ofBlock(Block block) throws InventoryHolderException {
        final BlockLocation blockLocation = BlockLocation.of(block);
        BlockManager blockManager = INSTANCES.get(blockLocation);
        if (blockManager != null) return blockManager;
        INSTANCES.put(blockLocation, (blockManager = new BlockManager(blockLocation)));
        blockManager.validate(); // Validates container
        return blockManager;
    }
}
