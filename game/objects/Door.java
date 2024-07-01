package org.de.eloy.fnaf.game.objects;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Switch;
import org.bukkit.scheduler.BukkitRunnable;
import org.de.eloy.fnaf.FNAF;

import java.util.Objects;

public class Door {
    private int id;
    private Location location;
    private Location leverLocation;
    private Material doorMaterial;
    private boolean isOpen;
    private int arenaIndex;
    private boolean inCooldown = false;

    public Door(Location location, Location leverLocation, Material doorMaterial, int arenaIndex) {
        this.location = location;
        this.leverLocation = leverLocation;
        this.doorMaterial = doorMaterial;
        this.isOpen = false;
        this.arenaIndex = arenaIndex;
    }

    public void toggle(FNAF plugin) {
        if (isInCooldown()) return;

        if (isOpen) close(plugin);
        else open(plugin);

        startCooldown(plugin);
    }

    private void startCooldown(FNAF plugin) {
        inCooldown = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                inCooldown = false;
            }
        }.runTaskLater(plugin, 30L);
    }

    public void open(FNAF plugin) {
        if (!isOpen) {
            isOpen = true;
            final Location doorLocation = location;

            Block block = leverLocation.getBlock();
            if (block instanceof Powerable) {
                ((Powerable) block).setPowered(true);
            }

            doorLocation.getBlock().setType(Material.AIR);
            Objects.requireNonNull(doorLocation.getWorld()).playSound(doorLocation, Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);

            animateDoorOpen(plugin);
        }
    }

    public void close(FNAF plugin) {
        if (isOpen) {
            isOpen = false;
            final Location doorLocation = location;

            Block block = leverLocation.getBlock();
            if (block instanceof Powerable) {
                ((Powerable) block).setPowered(false);
            }

            if (doorLocation.getWorld() == null) return;

            animateDoorClose(plugin);

            new BukkitRunnable() {
                private int tick = 0; // Inicializar el contador de ticks

                @Override
                public void run() {
                    if (tick == 1) {
                        Location middleBlockLocation = doorLocation.clone().add(0.5, 0.0, 1.0); // Coordenadas del segundo bloque (el medio)
                        doorLocation.getWorld().spawnParticle(Particle.CLOUD, middleBlockLocation, 25, 0.5f, 0.2f, 0.1f, 0.2f); // Generar partículas en el segundo bloque (el medio)
                        doorLocation.getWorld().playSound(doorLocation, Sound.BLOCK_PISTON_CONTRACT, 1.0f, 1.0f);
                    }
                    tick++;
                }
            }.runTaskTimer(plugin, 0L, 2L); // Ejecutar cada 2 ticks y comenzar en el primer tick
        }
    }

    private void animateDoorOpen(FNAF plugin) {
        final int height = 4;
        final int width = 3;

        if (location.getWorld() == null) return;

        for (int i = 0; i < height; i++) {
            final int currentHeight = i;

            final int delay = currentHeight * 2;

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int j = 0; j < width; j++) {
                        Location currentLocation = location.clone().add(0, currentHeight, j); // Cambiar la coordenada Y
                        currentLocation.getBlock().setType(Material.AIR);
                    }

                    location.getWorld().playSound(location, Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);
                }
            }.runTaskLater(plugin, delay);
        }
    }

    private void animateDoorClose(FNAF plugin) {
        final int height = 4;
        final int width = 3;

        if (location.getWorld() == null) return;

        for (int i = height - 1; i >= 0; i--) {
            final int currentHeight = i;

            final int delay = (height - 1 - currentHeight) * 2;

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int j = 0; j < width; j++) {
                        Location currentLocation = location.clone().add(0, currentHeight, j); // Cambiar la coordenada Y
                        currentLocation.getBlock().setType(doorMaterial);
                    }

                    location.getWorld().playSound(location, Sound.BLOCK_PISTON_CONTRACT, 1.0f, 1.0f);
                }
            }.runTaskLater(plugin, delay);
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLeverLocation() {
        return leverLocation;
    }

    public Material getDoorMaterial() {
        return doorMaterial;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isClosed() {
        return !isOpen;
    }

    public int getArenaIndex() {
        return arenaIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInCooldown() {
        return inCooldown;
    }
}
