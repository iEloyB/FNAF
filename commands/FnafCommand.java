package org.de.eloy.fnaf.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.database.dao.ArenaDAO;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.ArenasListGUI;

import java.util.ArrayList;

public class FnafCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        //Fnaf panel
        if (label.equalsIgnoreCase("fnaf") && args.length > 0 && args[0].equalsIgnoreCase("panel")) {
            if (player.hasPermission("fnaf.admin")) {
                player.openInventory(ArenasListGUI.getGUI());
            } else {
                player.sendMessage(FNAF.getMessages().getPREFIX()+" "+ FNAF.getMessages().getNO_PERMISSION());
                return false;
            }
            return true;
        }

        //Fnaf arena create
        if (label.equalsIgnoreCase("fnaf") && args.length == 3 && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("create")) {
            if (player.hasPermission("fnaf.admin")) {

                String arenaName = args[2];
                String arena_server_name = String.valueOf(player.getServer().getPort());

                ArenaDAO arenaDAO = new ArenaDAO();

                ArrayList<Arena> arenas = arenaDAO.getAll();
                if (arenas.size() >= 28) {
                    player.sendMessage(FNAF.getMessages().getPREFIX()+" §cThere can't be more than 21 arenas.");
                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);

                    return false;
                }

                for (Arena arena : arenas) {
                    if (arena.getName().equalsIgnoreCase(arenaName)) {
                        player.sendMessage(FNAF.getMessages().getPREFIX()+" §cThe arena §e"+arenaName+"§c already exists.");
                        player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
                        return false;
                    }
                }

                Arena arena = new Arena(arenaName);
                arena.setServerPort(arena_server_name);

                arenaDAO.insert(arena);
                player.sendMessage(FNAF.getMessages().getPREFIX()+" §aYou have successfully created the arena named §e"+arenaName+"§a.");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
                return true;

            } else {
                player.sendMessage(FNAF.getMessages().getPREFIX()+" "+ FNAF.getMessages().getNO_PERMISSION());
                return false;
            }
        }

        return true;
    }
}
