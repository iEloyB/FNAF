package org.de.eloy.fnaf.game.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.gui.system.GUIManager;

public class PreparePlayersTask {
    private final GameManager gameManager;

    public PreparePlayersTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void giveItems() {
        for (Player player : gameManager.getAnimatronics()) {
            Animatronic animatronic = (Animatronic) gameManager.getRoleByPlayer(player);

            switch (animatronic.getName()) {
                case "bonnie":
                    giveBonnieItems(player);
                    break;
                case "chica":
                    giveChicaItems(player);
                    break;
                case "freddy":
                    giveFreddyItems(player);
                    break;
                case "foxy":
                    giveFoxyItems(player);
                    break;
            }
        }

        giveGuardItems();
    }

    public void removeItems() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
        }
    }

    public void setGuardHp() {
        int hp = gameManager.getArena().getGuard().getHp();
        gameManager.getGuard().setHealthScale(hp);
    }
    public void giveChicaItems(Player player) {
        //TODO give chica items
    }

    public void giveBonnieItems(Player player) {
        //TODO give bonnie items
    }

    public void giveFoxyItems(Player player) {
        //TODO give foxy items
    }

    public void giveFreddyItems(Player player) {
        //TODO give freddy items
    }

    public void giveGuardItems() {
        Player player = gameManager.getGuard();

        String enterCamerasName = FNAF.getMessages().getENTER_CAMERAS();
        ItemStack item = GUIManager.createItem(Material.FILLED_MAP,enterCamerasName,null);
        player.getInventory().setItem(4,item);
    }
}