package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.files.ConfigFile;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.GUIManager;

import java.util.ArrayList;
import java.util.List;

public class AnimatronicListGUI {
    public static Inventory getGUI(Arena arena) {
        ConfigFile configFile = FNAF.getFiles().getConfigFile();

        Inventory inventory = GUIManager.getGuiInitialized("§6§l" + arena.getName() + "'s animatronics list", 3);
        inventory.setItem(11, GUIManager.createHeadItem(configFile.getAnimatronicSkin("freddy"), "§6Freddy", animatronicLore("Freddy", arena)));
        inventory.setItem(12, GUIManager.createHeadItem(configFile.getAnimatronicSkin("chica"), "§eChica", animatronicLore("Chica", arena)));
        inventory.setItem(14, GUIManager.createHeadItem(configFile.getAnimatronicSkin("bonnie"), "§bBonnie", animatronicLore("Bonnie", arena)));
        inventory.setItem(15, GUIManager.createHeadItem(configFile.getAnimatronicSkin("foxy"), "§cFoxy", animatronicLore("Foxy", arena)));
        inventory.setItem(19, GUIManager.createItem(Material.ARROW, "§b§lBack", null));
        inventory.setItem(8, GUIManager.createItem(Material.RED_STAINED_GLASS_PANE, "§c§lX", null));
        return inventory;
    }

    private static List<String> animatronicLore(String animatronicName, Arena arena) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oClick this item to add the:");
        lore.add("§f§oanimatronic §f§l" + animatronicName + "§f§o for the arena §f§l" + arena.getName());
        lore.add("");
        lore.add("§e§lCLICK: §6You set animatronic's spawn on your §ncurrent§6 location");
        return lore;
    }

}