package dev.orf1.plugins.eventscore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class Main extends JavaPlugin implements Listener {

    private File dataFile;
    private YamlConfiguration modifyDataFile;
    List<Player> bypassGamemode = new ArrayList<Player>();
    List<Player> bypassBuild = new ArrayList<Player>();

    public YamlConfiguration getData(){return modifyDataFile;}
    public File getDataFile(){return dataFile;}

    public List<Player> getBypassGamemodeList(){return bypassGamemode;}
    public List<Player> getBypassBuildList(){return bypassBuild;}

    @Override
    public void onEnable() {
        initiateFiles();
        Bukkit.getPluginManager().registerEvents(new EventManager(), this);

        if (!getDescription().getVersion().equalsIgnoreCase(getConfig().getString("config-version"))){
            String msg = "Outdated config version! This may cause various issues. This can be fixed by removing the existing config and letting it re-generate.";
            getLogger().log(Level.WARNING, msg);
        }
    }

    @Override
    public void onDisable() {
    }



    public void initiateFiles() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()){

            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        modifyDataFile = YamlConfiguration.loadConfiguration(dataFile);
    }
}
