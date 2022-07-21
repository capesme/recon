package net.voxfun.vox.recon.listners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.voxfun.vox.recon.manager.*;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamagedListener implements Listener {
    private GameManager gameManager;
    public static final Map<Player, Integer> kills = new HashMap<>();
    public static final Map<Player, Integer> deaths = new HashMap<>();
    public static final List<Player> alivePlayers = new ArrayList<>();
    public static final Map<Player, Integer> killstreak = new HashMap<>();

    public DamagedListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = killed.getKiller();
        event.getDrops().clear();
        if (!killed.isDead()) { return; }
        if (killer == null) { return; }

        PlayerManager.clearKit(killed);

        if (killer != killed) {
            if (kills.get(killer) == null) {
                kills.put(killer, 1);
                killstreak.put(killer, 1);
                scoreboardManager.setKills(killer, 1);
            } else {
                kills.replace(killer, kills.get(killer) + 1);
                killstreak.put(killer, killstreak.get(killer) == null ? 1 : killstreak.get(killer) + 1);
                scoreboardManager.setKills(killer, kills.get(killer));
                if (killstreak.get(killer) > 1) {
                    if (killstreak.get(killer) == 5) {
                        Bukkit.broadcastMessage(ChatColor.BOLD + FormatBroadcast.format(String.format("%s is on a 5 kill streak!", killer.getName())));
                    }
                    if (killstreak.get(killer) == 10) {
                        Bukkit.broadcastMessage(ChatColor.BOLD + FormatBroadcast.format(String.format("%s is on a 10 kill streak!", killer.getName())));
                    }
                    killer.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.format("You are on a %s kill streak!", killstreak.get(killer))));
                }
                if (kills.get(killer) > 14) {
                    gameManager.setGameState(GameState.WON);
                }
            }
        }

        killer.getInventory().addItem(new ItemStack(Material.ARROW));
        killer.setHealthScale(20);
        killer.setSaturation(killer.getSaturation() + 28);

        if (killer != killed) {
            Bukkit.broadcastMessage(FormatBroadcast.format(String.format("%s has killed %s.", killer.getName(), killed.getName())));
        } else {
            Bukkit.broadcastMessage(FormatBroadcast.format(String.format("%s aimed poorly...", killer.getName())));
        }

        if (killstreak.get(killed) != null) {
            killstreak.remove(killed);
        }

        if (deaths.get(killed) == null) {
            deaths.put(killed, 1);
        } else {
            deaths.replace(killed, deaths.get(killed) + 1);
            if (deaths.get(killed) > 4) {
                killed.setGameMode(GameMode.SPECTATOR);
                // Other death stuff...
                for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                    killed.showPlayer(allPlayers);
                    if (!allPlayers.getGameMode().equals(GameMode.SPECTATOR) && !allPlayers.hasPermission("recon.specs.seeSpecs")) {
                        allPlayers.hidePlayer(killed);
                    }
                }
                Bukkit.broadcastMessage(FormatBroadcast.format(ChatColor.RED + String.format("%s has been eliminated.", killed.getName())));
                alivePlayers.remove(killed);
            }
        }

        if (alivePlayers.size() < 2) {
            gameManager.setGameState(GameState.WON);
        }

        if (killed.getInventory().getItem(8) == null) {
            killed.getInventory().setItem(8, new ItemStack(Material.RED_DYE));
            killed.getInventory().getItem(8).setAmount(5 - deaths.get(killed));
        } else {
            killed.getInventory().getItem(8).setAmount(5 - deaths.get(killed));
        }
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {
        Entity hurt = event.getEntity();
        if (DontHurtEntity.get().contains(hurt.getType())) { event.setCancelled(true); return; }
        if (hurt instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && gameManager.getGameState() == GameState.ACTIVE) { event.setDamage(100); }
        if (!(hurt instanceof Player) && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            if (gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.WAITING) {
                event.setDamage(100);
            }
        }
    }

    public static Map<Player, Integer> getKills() { return kills; }
    public static Map<Player, Integer> getDeaths() { return deaths; }
}