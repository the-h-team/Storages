package com.github.sanctum.storages.blocks;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents a block location in a particular world.
 */
public final class BlockLocation {
    public final int x;
    public final int y;
    public final int z;
    public final String world;
    public final AtomicReference<World> resolvedWorld = new AtomicReference<>();

    public BlockLocation(int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world.getName();
        this.resolvedWorld.set(world);
    }
    protected BlockLocation(@NotNull Map<String, Object> map) {
        try {
            this.x = (int) map.get("x");
            this.y = (int) map.get("y");
            this.z = (int) map.get("z");
            final String world = (String) map.get("world");
            final World resolved = Bukkit.getServer().getWorld(world);
            if (resolved == null) {
                throw new IllegalArgumentException("Unable to resolve world");
            }
            this.world = world;
            this.resolvedWorld.set(resolved);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid map!", e);
        }
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
     * @return Block object
     */
    public Block toBlock() {
        return getWorld().getBlockAt(x, y, z);
    }

    private World getWorld() {
        return resolvedWorld.updateAndGet(w -> w == null ? Bukkit.getServer().getWorld(world) : w);
    }

    public static BlockLocation of(@NotNull Block block) {
        return new BlockLocation(block.getX(), block.getY(), block.getZ(), block.getWorld());
    }

    public static BlockLocation ofLocation(@NotNull Location location) {
        return new BlockLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld());
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
        return Objects.hash(x, y, z, world);
    }

    public @NotNull Map<String, Object> serialize() {
        ImmutableMap.Builder<String, Object> map = new ImmutableMap.Builder<>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        map.put("world", world);
        return map.build();
    }

    public static BlockLocation deserialize(@NotNull Map<String, Object> map) {
        return new BlockLocation(map);
    }

    public static BlockLocation valueOf(@NotNull Map<String, Object> map) {
        return new BlockLocation(map);
    }
}