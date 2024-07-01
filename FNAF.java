package org.de.eloy.fnaf;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.de.eloy.fnaf.database.dao.ArenaDAO;
import org.de.eloy.fnaf.game.Game;
import org.de.eloy.fnaf.game.listeners.*;
import org.de.eloy.fnaf.commands.FnafCommand;
import org.de.eloy.fnaf.files.File;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.game.gui.listeners.SelectRolesListener;
import org.de.eloy.fnaf.gui.system.setup.arenas.listeners.*;
import org.de.eloy.fnaf.messages.Message;

import java.util.ArrayList;

public final class FNAF extends JavaPlugin implements PluginMessageListener {
    private static File file;
    private static Message message;
    private static ArrayList<Game> playableGames = new ArrayList<>();

    @Override
    public void onEnable() {
        //Bungee
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        //Files
        file = new File(this);
        message = new Message();

        //Commands
        getCommand("fnaf").setExecutor(new FnafCommand());

        //Events
        Bukkit.getPluginManager().registerEvents(new ArenasListListener(), this);
        Bukkit.getPluginManager().registerEvents(new ConfirmRemoveAllListener(), this);
        Bukkit.getPluginManager().registerEvents(new EditArenaListener(), this);
        Bukkit.getPluginManager().registerEvents(new LightsMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new DoorsMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new CamerasMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuardMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new AnimatronicsMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new AnimatronicListListener(), this);
        Bukkit.getPluginManager().registerEvents(new EndingCelebrationListener(), this);

        //Setup playable games
        loadPlayableGames();
    }
    private void loadPlayableGames() {
        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> allArenas = arenaDAO.getAll();
        ArrayList<Arena> playableArenas = new ArrayList<>();

        for (Arena arena : allArenas) {
            if (arena.isReady()) playableArenas.add(arena);
        }

        for (Arena arena : playableArenas) {
            Game game = new Game(this, arena);
            Bukkit.getPluginManager().registerEvents(new SelectRolesListener(game), this);
            Bukkit.getPluginManager().registerEvents(new PlayerJoinLeaveListeners(game), this);
            Bukkit.getPluginManager().registerEvents(new DoorsFunctionalityListener(game.getGameManager()), this);
            Bukkit.getPluginManager().registerEvents(new WaitingLobbyListener(this, game), this);
            Bukkit.getPluginManager().registerEvents(new HealthAndHungerListener(game.getGameManager()), this);
            Bukkit.getPluginManager().registerEvents(new CamerasListener(game.getGameManager()), this);
            playableGames.add(game);
        }
    }

    public void movePlayerToServer(Player player, String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);

        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public static ArrayList<Game> getPlayableGames() {
        return playableGames;
    }

    @Override
    public void onDisable() {}

    public static File getFiles() {
        return file;
    }
    public static Message getMessages() {
        return message;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {}
}