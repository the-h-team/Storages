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
package com.github.sanctum.storages.players;

import com.github.sanctum.storages.InventoryDiscreteStorage;
import com.github.sanctum.storages.exceptions.ProviderException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A complete implementation of DiscreteStorage that
 * is backed by the live inventory of a Player.
 *
 * @since 1.0.0
 * @author ms5984
 */
public class PlayerStoragePassthrough extends InventoryDiscreteStorage<PlayerManager> {

    public PlayerStoragePassthrough(@NotNull Player player) throws ProviderException {
        super(new PlayerManager(player));
    }

    @Override
    public @NotNull String getName() throws ProviderException {
        return manager.query(Player::getName);
    }
}
