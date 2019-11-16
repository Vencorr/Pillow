package com.vencorr.pillow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;
    FileConfiguration config = this.getConfig();
    SleepController control;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config.options().copyDefaults(true);
        plugin = this;
        saveConfig();
        this.getCommand("pillow").setExecutor(new PillowAbout());
        if (config.getBoolean("multiplayersleep.enabled")) {
            control = new SleepController();
            getServer().getPluginManager().registerEvents(new SleepListener(), this);
            if (Main.plugin.config.getString("multiplayersleep.world") == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Pillow.MultiplayerSleep was unable to locate a world named '" + Main.plugin.config.getString("multiplayersleep.world") + "'!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
            if (control.runn < 0) Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "There was an error running Pillow.MPSleep.");
        }
        if (config.getBoolean("bedtp.enabled")) this.getCommand("bedtp").setExecutor(new BedTeleport());
        if (config.getBoolean("hubtp.enabled")) this.getCommand("hub").setExecutor(new HubTeleport());
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public boolean hasPerms(Player player, String permission) {
        return player.hasPermission(permission) || player.isOp();
    }
}
