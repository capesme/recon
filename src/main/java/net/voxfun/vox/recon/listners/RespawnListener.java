package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.index;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.MapManager;
import net.voxfun.vox.recon.manager.PlayerManager;
import net.voxfun.vox.recon.tasks.RespawnTask;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;
import java.util.Random;

public class RespawnListener implements Listener {
    private GameManager gameManager;
    private final index plugin;

    public RespawnListener(GameManager gameManager, index plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    @EventHandler
    private void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (gameManager.getGameState() == GameState.ACTIVE) {
            PlayerManager.giveKit(player);
            Document selectedMap = MapManager.getMap();
            List<Document> mapSpawns = selectedMap.getList("spawns", Document.class);
            Random rand = new Random();
            Document xyz = mapSpawns.get(rand.nextInt(mapSpawns.size()));
            Integer x = xyz.getInteger("x");
            Integer y = xyz.getInteger("y");
            Integer z = xyz.getInteger("z");
            event.setRespawnLocation(new Location(player.getWorld(), x, y, z));
            new RespawnTask(new Location(player.getWorld(), x, y, z), player).runTaskTimer(plugin, 0, 20);
        }
    }
};
