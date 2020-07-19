package dev.orf1.plugins.eventscore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class EventsCoreCommand implements CommandExecutor {

    Main main = JavaPlugin.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            if (sender.hasPermission("eventscore.command.version")) {
                sender.sendMessage("Plugin version: " + main.getDescription().getVersion());
            } else {
                String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission-message"));
                sender.sendMessage(message);
            }
        }else {
            if (args[0].equalsIgnoreCase("version")){
                if (sender.hasPermission("eventscore.command.version")) {
                    sender.sendMessage("Plugin version: " + main.getDescription().getVersion());
                } else {
                    String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.no-permission-message"));
                    sender.sendMessage(message);
                }
            }
        }

        return false;
    }
}
