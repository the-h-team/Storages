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

import com.github.sanctum.storages.InventoryDiscreteStorage;
import com.github.sanctum.storages.exceptions.InventoryHolderException;
import com.github.sanctum.storages.exceptions.ProviderException;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A BlockManager-backed, InventoryHolder-based DiscreteStorage implementation.
 *
 * @since 1.0.0
 * @author ms5984
 */
public class BlockInventoryStorage extends InventoryDiscreteStorage<BlockManager> {

    /**
     * Create a BlockInventoryStorage with a BlockManager.
     *
     * @param blockManager a BlockManager
     * @throws ProviderException if the provider encounters an error
     */
    public BlockInventoryStorage(BlockManager blockManager) throws ProviderException {
        super(blockManager);
    }

    @Override
    public ItemStack[] getContents() throws InventoryHolderException {
        return manager.query(c -> c.getInventory().getContents());
    }

    @Override
    public void setContents(ItemStack[] items) throws InventoryHolderException, IllegalArgumentException {
        manager.update(c -> c.getInventory().setContents(items));
    }

    @Override
    public @NotNull String getName() throws InventoryHolderException {
        return manager.query(c -> Optional.ofNullable(c.getCustomName())
                .orElse(c.getType().toString() + '[' + c.getLocation().toString() + ']')
        );
    }
}
