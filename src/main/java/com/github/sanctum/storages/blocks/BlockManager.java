/*
 *  This file is part of Storages.
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

import java.util.function.Consumer;

/**
 * An InventoryManager implementation for {@link Container} handling
 * Container resolution from an original block location.
 *
 * @since 1.0.0
 * @author ms5984
 */
public class BlockManager extends InventoryManager<Container> {
    private final BlockLocation blockLocation;

    /**
     * Create a BlockManager for the provided BlockLocation.
     *
     * @param blockLocation a valid BlockLocation
     */
    public BlockManager(BlockLocation blockLocation) throws InventoryHolderException {
        this.blockLocation = blockLocation;
        // force-validate block location before exiting constructor
        getRawState();
    }

    @Override
    public void update(Consumer<Container> queryFunction) throws InventoryHolderException {
        super.update(queryFunction.andThen(Container::update));
    }

    @Override
    protected Container getRawState() throws InventoryHolderException {
        final Container c;
        final Block block = blockLocation.toBlock();
        if (block == null) throw new InventoryHolderException("Unable to resolve block in world!");
        try {
            c = (Container) block.getState();
        } catch (ClassCastException e) {
            throw new InventoryHolderException("Unable to cast BlockState to Container. The block probably changed.", e);
        }
        return c;
    }

}
