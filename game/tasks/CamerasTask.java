package org.de.eloy.fnaf.game.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.objects.Camera;

import java.util.ArrayList;

public class CamerasTask {
    private final GameManager gameManager;

    public CamerasTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void enterCameras() {
        if (!canEnterCameras()) return;

        Player guard = gameManager.getGuard();
        gameManager.setWatchingCamera(true);
        guard.teleport(gameManager.getLastCamera().getLocation());
        guard.setAllowFlight(true);
        guard.setFlying(true);

        guard.getInventory().clear();

        toggleVanish(guard);
        generateArmorStand(guard);
        invokeCamChanger(guard);
    }

    public void invokeCamChanger(Player player) {
        Bukkit.getScheduler().runTaskLater(gameManager.getPlugin(), () -> {
            Location loc = player.getLocation();
            loc.add(0, 1, 0);

            if (loc.getWorld() == null) return;

            Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
            villager.setCustomName("cams");
            villager.setCustomNameVisible(false);
            villager.setSilent(true);
            villager.setGravity(false);
            villager.setInvisible(true);
        }, 30L);
    }

    private void removeCamChanger() {
        if (gameManager.getArena().getGuard().getSpawn().getWorld() == null) return;

        World world = gameManager.getArena().getGuard().getSpawn().getWorld();

        for (Entity entity : world.getEntities()) {
            if (entity.getType() == EntityType.VILLAGER && entity.getCustomName() != null && entity.getCustomName().equals("cams")) {
                entity.remove();
            }
        }
    }

    public void leaveCameras() {
        if (!canLeaveCameras()) return;

        Player guard = gameManager.getGuard();
        gameManager.setWatchingCamera(false);
        guard.teleport(gameManager.getArena().getGuard().getSpawn());
        guard.setAllowFlight(false);
        guard.setFlying(false);

        guard.getInventory().clear();
        PreparePlayersTask preparePlayersTask = new PreparePlayersTask(gameManager);
        if (gameManager.getBattery() > 0) preparePlayersTask.giveGuardItems();

        toggleVanish(guard);
        removeArmorStand(gameManager.getLastCamera().getLocation());
        removeArmorStand(gameManager.getArena().getGuard().getSpawn());
        guard.removePotionEffect(PotionEffectType.INVISIBILITY);
        removeCamChanger();
    }

    public void nextCamera() {
        if (!canSwitchCamera()) return;
        ArrayList<Camera> cameras = gameManager.getArena().getCameras();

        int currentIndex = cameras.indexOf(gameManager.getLastCamera());

        int nextIndex;
        if (currentIndex == cameras.size()-1) nextIndex = 0;
        else nextIndex = currentIndex + 1;

        switchToCamera(nextIndex);
    }

    public void previousCamera() {
        if (!canSwitchCamera()) return;
        ArrayList<Camera> cameras = gameManager.getArena().getCameras();

        int currentIndex = cameras.indexOf(gameManager.getLastCamera());

        int prevIndex;
        if (currentIndex == 0) prevIndex = cameras.size()-1;
        else prevIndex = currentIndex - 1;

        switchToCamera(prevIndex);
    }

    private void switchToCamera(int index) {
        ArrayList<Camera> cameras = gameManager.getArena().getCameras();

        Camera camera = cameras.get(index);
        Player guard = gameManager.getGuard();

        guard.teleport(camera.getLocation());
        removeArmorStand(gameManager.getLastCamera().getLocation());
        generateArmorStand(guard);
        gameManager.setLastCamera(camera);
        removeCamChanger();
        invokeCamChanger(guard);
    }

    private boolean canEnterCameras() {
        return !gameManager.isGuardOnCameras() && gameManager.getBattery() > 0;
    }

    private boolean canLeaveCameras() {
        return gameManager.isGuardOnCameras() && gameManager.getBattery() > 0;
    }

    private boolean canSwitchCamera() {
        ArrayList<Camera> cameras = gameManager.getArena().getCameras();
        return gameManager.isGuardOnCameras() || !cameras.isEmpty();
    }

    private void generateArmorStand(Player player) {
        FNAF plugin = gameManager.getPlugin();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Location playerEyeLocation = player.getEyeLocation();
            Location standLocation = playerEyeLocation.clone().subtract(0, 1.7, 0);

            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(standLocation, EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setCanPickupItems(false);
            armorStand.setBasePlate(false);
            armorStand.setInvulnerable(true);
            armorStand.setCollidable(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);

            String headName = FNAF.getFiles().getConfigFile().getAnimatronicSkin("camera");
            ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headMeta = (SkullMeta) headItem.getItemMeta();
            if (headMeta != null && armorStand.getEquipment() != null) {
                headMeta.setOwner(headName);
                headItem.setItemMeta(headMeta);
                armorStand.getEquipment().setHelmet(headItem);
            }
        }, 10);
    }

    public static void removeArmorStand(Location location) {
        if (location.getWorld() == null) return;

        for (Entity entity : location.getWorld().getNearbyEntities(location, 2, 2, 2)) {
            if (entity instanceof ArmorStand) {
                entity.remove();
            }
        }
    }
    private void toggleVanish(Player player) {
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
        }
    }

}
