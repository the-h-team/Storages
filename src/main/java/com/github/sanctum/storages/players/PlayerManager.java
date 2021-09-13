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

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerManager extends InventoryManager<Player> {
    private static final List<PlayerManager> INSTANCES = new LinkedList<>();
    private final Player player;

    private PlayerManager(Player player) {
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

    public static PlayerManager of(@NotNull Player player) {
        PlayerManager playerManager = null;
        for (PlayerManager pm : INSTANCES) {
            if (pm.player == player) {
                playerManager = pm;
                break;
            }
        }
        if (playerManager != null) return playerManager;
        INSTANCES.add((playerManager = new PlayerManager(player)));
        return playerManager;
    }
}
