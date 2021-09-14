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

import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents a block location in a particular world.
 *
 * @since 1.0.0
 * @author ms5984
 */
public final class BlockLocation implements ConfigurationSerializable {
    /** The block X coordinate. */
    public final int x;
    /** The block Y coordinate. */
    public final int y;
    /** The block z coordinate. */
    public final int z;
    /** The block world name. */
    public final String world;
    private final AtomicReference<World> resolvedWorld = new AtomicReference<>();

    /**
     * Create a BlockLocation from XYZ-coordinates and a {@link World}.
     * @param x the block's x coordinate
     * @param y the block's y coordinate
     * @param z the block's z coordinate
     * @param world the block's World
     */
    public BlockLocation(int x, int y, int z, @NotNull World world) {
        this(x, y, z, world.getName());
        this.resolvedWorld.set(world);
    }

    /**
     * Create a BlockLocation from XYZ-coordinates and a world name.
     * @param x the block's x coordinate
     * @param y the block's y coordinate
     * @param z the block's z coordinate
     * @param worldName the block's world name
     */
    public BlockLocation(int x, int y, int z, @NotNull String worldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = worldName;
    }

    /**
     * Get the X coordinate of the block.
     *
     * @return the block's x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y coordinate of the block.
     *
     * @return the block's y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Get the Z coordinate of the block.
     *
     * @return the block's z coordinate
     */
    public int getZ() {
        return z;
    }

    /**
     * Get the stored name of the world of the block.
     *
     * @return the world name
     */
    public @NotNull String getWorldName() {
        return world;
    }

    /**
     * Get the runtime {@link World} of the block.
     *
     * @return the runtime world of the block
     */
    public @Nullable World getWorld() {
        return resolvedWorld.updateAndGet(w -> w == null ? Bukkit.getServer().getWorld(world) : w);
    }

    /**
     * Produce a new Bukkit Location consisting of
     * this {@link BlockLocation}'s world, x, y and z.
     *
     * @return a new Location object
     */
    public Location toLocation() {
        return new Location(getWorld(), x, y, z);
    }

    /**
     * Get the Block at this BlockLocation.
     * <p>
     * Delegates to {@link World#getBlockAt(int, int, int)}
     * <p>
     * <b>This can cause chunk loading!
     *
     * @return Block object or null if unable to resolve runtime world
     */
    public @Nullable Block toBlock() {
        final World world = getWorld();
        if (world == null) return null;
        return world.getBlockAt(x, y, z);
    }

    /**
     * Get a BlockLocation representing the provided Block.
     *
     * @param block a Block
     * @return a new BlockLocation initialized to {@code block}'s data
     */
    public static BlockLocation of(@NotNull Block block) {
        return new BlockLocation(block.getX(), block.getY(), block.getZ(), block.getWorld());
    }

    /**
     * Get a BlockLocation representing the block at the provided Location.
     * @param location a Location, which must have a world component
     * @return a new BlockLocation
     * @throws IllegalArgumentException if {@code location} has no world
     */
    public static BlockLocation ofLocation(@NotNull Location location) throws IllegalArgumentException {
        final World world = location.getWorld();
        if (world == null) throw new IllegalArgumentException("Location's world cannot be null");
        return new BlockLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), world);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockLocation that = (BlockLocation) o;
        return x == that.x &&
                y == that.y &&
                z == that.z &&
                world.equals(that.world);
    }

    @Override
    public int hashCode() {
        // crash-course: 1) expand range of values to cover maximum int space 2) XOR
        return (x * 71) ^ (y * 1048573) ^ (z * 71) ^ world.hashCode();
    }

    // for ConfigurationSerializable contract

    @Override
    public @NotNull Map<String, Object> serialize() {
        return ImmutableMap.of(
                "x", x,
                "y", y,
                "z", z,
                "world", world);
    }

    public static @Nullable BlockLocation deserialize(@NotNull Map<String, Object> map) {
        int x, y, z;
        final String worldName;
        try {
            x = (int) map.get("x");
            y = (int) map.get("y");
            z = (int) map.get("z");
            worldName = (String) map.get("world");
        } catch (ClassCastException e) {
            return null;
        }
        if (worldName == null) return null;
        final World resolved = Bukkit.getServer().getWorld(worldName);
        return resolved == null ? new BlockLocation(x, y, z, worldName) : new BlockLocation(x, y, z, resolved);
    }
}
