package dev.orf1.plugins.eventscore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BypassCommand implements CommandExecutor {

    Main main = JavaPlugin.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("bypass.gamemode.self") || player.hasPermission("bypass.build.self") || player.hasPermission("bypass.build.others")|| player.hasPermission("bypass.gamemode.others")) {
                if (args.length == 0) {
                    if (main.getBypassBuildList().contains(player) && main.getBypassGamemodeList().contains(player)){
                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.bypass-both"));
                        player.sendMessage(message);
                    } else if (main.getBypassGamemodeList().contains(player)){
                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.bypass-gamemode"));
                        player.sendMessage(message);
                    }else if (main.getBypassBuildList().contains(player)){
                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.bypass-build"));
                        player.sendMessage(message);
                    } else {
                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.bypass-none"));
                        player.sendMessage(message);
                    }
                } else if (args.length == 1){
                    switch(args[0]){
                        case "gamemode":
                            if (player.hasPermission("bypass.gamemode.self")){
                                if (main.getBypassGamemodeList().contains(player)){
                                    main.getBypassGamemodeList().remove(player);
                                    String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.bypass-disable-gamemode"));
                                    player.sendMessage(message);
                                } else {
                                    main.getBypassGamemodeList().add(player);
                                    String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.bypass-enable-gamemode"));
                                    player.sendMessage(message);
                                }
                            } else {
                                String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission"));
                                player.sendMessage(message);
                            }
                            break;
                        case "build":
                            if (player.hasPermission("bypass.build.self")){
                                if (main.getBypassBuildList().contains(player)){
                                    main.getBypassBuildList().remove(player);
                                    String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.bypass-disable-build"));
                                    player.sendMessage(message);
                                } else {
                                    main.getBypassBuildList().add(player);
                                    String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.bypass-enable-build"));
                                    player.sendMessage(message);
                                }
                            } else {
                                String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission"));
                                player.sendMessage(message);
                            }
                            break;
                        default:
                            if (player.hasPermission("bypass.gamemode.self") || player.hasPermission("bypass.build.self") || player.hasPermission("bypass.build.others")|| player.hasPermission("bypass.gamemode.others")){
                                String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.invalid-usage-bypass"));
                                player.sendMessage(message);
                            } else {
                                String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission"));
                                player.sendMessage(message);
                            }
                    }
                } else if (args.length == 2){
                    if (player.hasPermission("bypass.gamemode.others") || player.hasPermission("bypass.build.others")){
                        if (args[0].equalsIgnoreCase("gamemode")){
                            if (player.hasPermission("bypass.gamemode.others")){
                                Player target = Bukkit.getPlayer(args[1]);
                                if (target != null){
                                    if (!main.getBypassGamemodeList().contains(target)){
                                        main.getBypassGamemodeList().add(target);
                                        //TODO enabled target bypass message
                                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bypass-enabled-gamemode-other").replace("%player%", player.getDisplayName()));
                                        player.sendMessage(message);
                                    }else {
                                        main.getBypassGamemodeList().remove(target);
                                        //TODO disabled target bypass message
                                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bypass-disabled-gamemode-other").replace("%player%", player.getDisplayName()));
                                        player.sendMessage(message);
                                    }
                                } else{
                                    String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.invalid-player").replace("%player%", player.getDisplayName()));
                                    player.sendMessage(message);
                                }
                            } else {
                                String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission"));
                                player.sendMessage(message);
                            }
                        } else if (args[0].equalsIgnoreCase("build")){
                            if (player.hasPermission("bypass.build.others")){
                                Player target = Bukkit.getPlayer(args[1]);
                                if (target != null){
                                    if (!main.getBypassBuildList().contains(target)){
                                        main.getBypassBuildList().add(target);
                                        //TODO enabled target bypass message
                                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bypass-enabled-build-other").replace("%player%", player.getDisplayName()));
                                        player.sendMessage(message);
                                    }else {
                                        main.getBypassBuildList().remove(target);
                                        //TODO disabled target bypass message
                                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("bypass-disabled-build-other").replace("%player%", player.getDisplayName()));
                                        player.sendMessage(message);
                                    }
                                } else{
                                    String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.invalid-player").replace("%player%", player.getDisplayName()));
                                    player.sendMessage(message);
                                }
                            } else {
                                String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission"));
                                player.sendMessage(message);
                            }
                        } else {
                            //TODO Invalid args
                        }
                    } else {
                        String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission"));
                        player.sendMessage(message);
                    }
                }
            } else {
                String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission"));
                player.sendMessage(message);
            }
        }else {
            sender.sendMessage("This command can only be used in-game!");
        }
        return false;
    }
}
