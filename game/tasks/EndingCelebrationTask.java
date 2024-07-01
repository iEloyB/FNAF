package org.de.eloy.fnaf.game.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.game.objects.Animatronic;

public class EndingCelebrationTask extends BukkitRunnable {
    private final GameManager gameManager;
    private int timeLeft = 10;
    private final boolean guardWon;

    public EndingCelebrationTask(GameManager gameManager) {
        this.gameManager = gameManager;
        guardWon = gameManager.getGuardHP() > 2 && gameManager.getBattery() > 0; //Check who won
    }

    @Override
    public void run() {
        if (timeLeft == 1) {
            String serverName = FNAF.getFiles().getConfigFile().getMessage("default_lobby_server");
            for (Player player : Bukkit.getOnlinePlayers()) {
                gameManager.getPlugin().movePlayerToServer(player,serverName);
            }
        }

        if (timeLeft <= 0) gameManager.setGameState(GameState.RESETING);

        if (guardWon) guardCelebration();
        else animatronicCelebration();

        timeLeft--;
    }

    private void guardCelebration() {
        Player guard = gameManager.getGuard();
        if (guard == null) return;

        spawnInstantFireworkAtPlayerFeet(guard);
    }

    private void animatronicCelebration() {
        if (gameManager.getAnimatronics() == null) return;
        for (Player player : gameManager.getAnimatronics()) {
            if (player == null) continue;

            spawnInstantFireworkAtPlayerFeet(player);
        }
    }

    public void spawnInstantFireworkAtPlayerFeet(Player player) {
        Location playerLocation = player.getLocation();
        Location fireworkLocation = playerLocation.clone();

        fireworkLocation.setY(playerLocation.getBlockY());

        assert playerLocation.getWorld() != null;
        Firework firework = (Firework) playerLocation.getWorld().spawnEntity(fireworkLocation, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
                .flicker(false)
                .trail(false)
                .withColor(Color.WHITE)
                .withFade(Color.BLACK)
                .with(FireworkEffect.Type.BURST)
                .build();

        meta.addEffect(effect);
        meta.setPower(0);
        firework.setFireworkMeta(meta);

        firework.detonate();
    }
}
