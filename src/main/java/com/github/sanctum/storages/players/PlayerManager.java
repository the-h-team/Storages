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

import com.github.sanctum.storages.InventoryDiscreteStorage.InventoryManager;
import com.github.sanctum.storages.exceptions.InventoryHolderException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * An InventoryManager implementation for {@link Player}.
 *
 * @since 1.0.0
 * @author ms5984
 */
public class PlayerManager extends InventoryManager<Player> {
    private final Player player;

    /**
     * Create a PlayerManager for the provided Player.
     *
     * @param player an online player
     */
    public PlayerManager(Player player) {
        this.player = player;
    }

    @Override
    public void update(Consumer<Player> queryFunction) throws InventoryHolderException {
        super.update(queryFunction.andThen(Player::updateInventory));
    }

    @Override
    protected @NotNull Player validate(Player rawState) throws InventoryHolderException {
        final Player p = super.validate(rawState);
        if (p.isValid()) return p;
        throw new InventoryHolderException("Player is not valid.");
    }

    @Override
    protected Player getRawState() {
        return player;
    }
}
