package org.de.eloy.fnaf.gui.system;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class GUIManager {
    public static Inventory getGuiInitialized(String guiTitle, int guiRows) {
        Inventory inventory = Bukkit.createInventory(null, guiRows * 9, guiTitle);

        for (int y = 0; y < guiRows; y++) {
            for (int x = 0; x < 9; x++) {
                if (y == 0 || y == guiRows - 1 || x == 0 || x == 8) {
                    inventory.setItem(y * 9 + x, createItem(Material.BLACK_STAINED_GLASS_PANE, "§f", null));
                }
            }
        }
        return inventory;
    }

    public static ItemStack createHeadItem(String owner, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        assert meta != null;
        meta.setOwner(owner);
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (name != null) meta.setDisplayName(name);
            if (lore != null) meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }
}