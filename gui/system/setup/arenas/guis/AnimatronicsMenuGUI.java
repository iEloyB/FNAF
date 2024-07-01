package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.de.eloy.fnaf.database.dao.AnimatronicDAO;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.GUIManager;

import java.util.ArrayList;
import java.util.List;

public class AnimatronicsMenuGUI {
    public static Inventory getGUI(Arena arena) {
        Inventory inventory = GUIManager.getGuiInitialized("§6§l"+arena.getName()+"'s animatronics", 6);
        inventory.setItem(48, GUIManager.createItem(Material.BARRIER, "§c§lRemove Animatronics", null));
        inventory.setItem(50, GUIManager.createItem(Material.EMERALD_BLOCK, "§2§lAdd animatronic", null));
        inventory.setItem(49, GUIManager.createItem(Material.ARROW, "§b§lBack", null));
        inventory.setItem(8, GUIManager.createItem(Material.RED_STAINED_GLASS_PANE, "§c§lX", null));

        AnimatronicDAO animatronicDAO = new AnimatronicDAO();
        ArrayList<Animatronic> animatronicsList = animatronicDAO.getAllByArena(arena.getId());

        if (animatronicsList == null || animatronicsList.isEmpty()) {
            inventory.setItem(31, GUIManager.createItem(Material.RED_STAINED_GLASS, "§cNo animatronics", null));
            return inventory;
        }

        animatronicsList(inventory,animatronicsList);
        return inventory;
    }

    private static void animatronicsList(Inventory inventory, List<Animatronic> animatronicsList) {
        int slot = 0;
        for (Animatronic animatronic : animatronicsList) {
            while (slot < inventory.getSize()) {
                ItemStack item = inventory.getItem(slot);

                if (item == null || item.getType() == Material.AIR) {
                    ItemStack animatronicItem = GUIManager.createItem(Material.SKELETON_SKULL, "§6§l" + animatronic.getName().toUpperCase(),getAnimatronicLore(animatronic));
                    ItemMeta animatronicItemItemMeta = animatronicItem.getItemMeta();
                    assert animatronicItemItemMeta != null;
                    animatronicItemItemMeta.setCustomModelData(animatronic.getId());
                    animatronicItem.setItemMeta(animatronicItemItemMeta);
                    inventory.setItem(slot, animatronicItem);
                    break;
                }

                slot++;
            }
        }
    }

    private static List<String> getAnimatronicLore(Animatronic animatronic) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oAnimatronic info:");
        if (animatronic == null || animatronic.getSpawn() == null) lore.add("§b§l➤ §cUnconfigured spawn");
        else {
            Location ANIMATRONIC_SPAWN = animatronic.getSpawn();
            lore.add("§b§l➤ §f§lSpawn location: §3" + ANIMATRONIC_SPAWN.getBlockX()+" "+ANIMATRONIC_SPAWN.getBlockY()+" "+ANIMATRONIC_SPAWN.getBlockZ());
        }
        lore.add("");
        lore.add("§e§lCLICK: §6You set animatronic's spawn on your §ncurrent§6 location");
        return lore;
    }

}