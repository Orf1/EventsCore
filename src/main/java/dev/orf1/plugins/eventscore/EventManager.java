package dev.orf1.plugins.eventscore;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EventManager implements Listener {

    Main main = JavaPlugin.getPlugin(Main.class);

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!player.hasPermission("eventscore.bypass.gamemode")) {
            player.setGameMode(GameMode.ADVENTURE);
        }
        if (player.hasPermission("eventscore.silent")) {
            event.setJoinMessage("");
        } else {
            String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.join-message").replace("%player%", player.getDisplayName()));
            event.setJoinMessage(message);
        }
    }
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission("eventscore.silent")) {
            event.setQuitMessage("");
        }else {
            String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.leave-message").replace("%player%", player.getDisplayName()));
            event.setQuitMessage(message);
        }
    }
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (!main.getBypassBuildList().contains(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if (!main.getBypassBuildList().contains(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event){
        Player player = event.getPlayer();
        if (!event.getNewGameMode().equals(GameMode.ADVENTURE)) {
            if (!player.hasPermission("eventscore.bypass.gamemode") && !main.getBypassGamemodeList().contains(player)) {
                event.setCancelled(true);
            }
        }
    }
}
