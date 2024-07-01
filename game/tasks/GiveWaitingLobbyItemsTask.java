package org.de.eloy.fnaf.game.tasks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.gui.system.GUIManager;
import org.de.eloy.fnaf.messages.Message;

public class GiveWaitingLobbyItemsTask {
    public void giveItems(Player player) {
        Message MSG = FNAF.getMessages();

        ItemStack selectRoleItem = GUIManager.createItem(Material.CLOCK,FNAF.getMessages().getSELECT_ROLES_TITLE(),null);
        player.getInventory().setItem(0,selectRoleItem);

        ItemStack lobbyItem = GUIManager.createItem(Material.RED_BED,MSG.getGO_BACK_TITLE(),null);
        player.getInventory().setItem(8,lobbyItem);
    }
}