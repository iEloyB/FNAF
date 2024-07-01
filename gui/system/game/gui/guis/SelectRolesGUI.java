package org.de.eloy.fnaf.gui.system.game.gui.guis;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.files.ConfigFile;
import org.de.eloy.fnaf.game.Game;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Guard;
import org.de.eloy.fnaf.gui.system.GUIManager;
import org.de.eloy.fnaf.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class SelectRolesGUI {
    private static final Message MSG = FNAF.getMessages();

    public static Inventory getGUI(Game game) {
        Inventory inventory = GUIManager.getGuiInitialized("§7" + game.getGameManager().getArena().getName() + " " + FNAF.getMessages().getSELECT_ROLES_TITLE(), 6);

        addAnimatronics(inventory, game);

        return inventory;
    }

    private static void addAnimatronics(Inventory inventory, Game game) {
        int slot = 0;
        ConfigFile config = FNAF.getFiles().getConfigFile();
        ArrayList<Animatronic> animatronics = game.getGameManager().getArena().getAnimatronics();

        for (Animatronic animatronic : animatronics) {
            slot = findEmptySlot(inventory, slot);
            if (slot < 0) break;

            String animatronicName = getAnimatronicName(game, animatronic);
            ItemStack animatronicItem = createAnimatronicItem(config, animatronic.getName(), animatronic.getId(), animatronicName);
            inventory.setItem(slot, animatronicItem);
            slot++;
        }

        slot = findEmptySlot(inventory, slot);
        if (slot >= 0) {
            String guardName = getGuardName(game);
            ItemStack guardItem = createGuardItem(config, guardName);
            inventory.setItem(slot, guardItem);
        }
    }

    private static int findEmptySlot(Inventory inventory, Integer startSlot) {
        for (int slot = startSlot; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item == null || item.getType().isAir()) {
                return slot;
            }
        }
        return -1;
    }

    private static String getAnimatronicName(Game game, Animatronic animatronic) {
        String animatronicName = Animatronic.getAnimatronicTitle(animatronic.getName()) + " §d(§a0§7/§a1§d)";
        if (game.getGameManager().roleAlreadyPicked(animatronic)) {
            animatronicName = Animatronic.getAnimatronicTitle(animatronic.getName()) + " §d(§c1§7/§c1§d)";
        }
        return animatronicName;
    }

    private static ItemStack createAnimatronicItem(ConfigFile config, String animatronicName, Integer customModelData, String displayName) {
        ItemStack animatronicItem = GUIManager.createHeadItem(config.getAnimatronicSkin(animatronicName), displayName, Animatronic.getAnimatronicLore(animatronicName));
        ItemMeta animatronicItemMeta = animatronicItem.getItemMeta();
        assert animatronicItemMeta != null;
        animatronicItemMeta.setCustomModelData(customModelData);
        animatronicItem.setItemMeta(animatronicItemMeta);
        return animatronicItem;
    }

    private static String getGuardName(Game game) {
        String guard_title = MSG.getGUARD_TITLE();

        String guardName = guard_title + " §d(§a0§7/§a1§d)";
        Guard guard = game.getGameManager().getArena().getGuard();
        if (game.getGameManager().roleAlreadyPicked(guard)) guardName = guard_title + " §d(§c1§7/§c1§d)";

        return guardName;
    }

    private static ItemStack createGuardItem(ConfigFile config, String displayName) {
        List<String> guardLore = MSG.getGUARD_SUBTITLE();
        ItemStack guardItem = GUIManager.createHeadItem(config.getAnimatronicSkin("guard"), displayName, guardLore);
        ItemMeta guardItemMeta = guardItem.getItemMeta();
        assert guardItemMeta != null;
        guardItemMeta.setCustomModelData(99999999);
        guardItem.setItemMeta(guardItemMeta);
        return guardItem;
    }

}
