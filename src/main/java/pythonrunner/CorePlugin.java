package pythonrunner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pythonrunner.commands.CommandGraphPy;
import pythonrunner.commands.CommandRunPy;
import snekboxwrapper.SnekboxClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class CorePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();

        String host = config.getString("snekboxInstanceURI");

        // SnekboxClient snekboxClient = new SnekboxClient("http://snekbox:8060");
        // getLogger().info(snekboxClient.eval("print('hello world')"));

        getLogger().info("Plugin started!");
        Objects.requireNonNull(this.getCommand("runpy")).setExecutor(new CommandRunPy(host));
        Objects.requireNonNull(this.getCommand("graphpy")).setExecutor(new CommandGraphPy(host));

        // PluginManager pluginManager = getServer().getPluginManager();
        // pluginManager.registerEvents(new ListenerBookEdited(this), this);

//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                long now = System.currentTimeMillis();
//            }
//        };
    }
    public void onDisable() {
        getLogger().info("Plugin stopped!");
    }
}
