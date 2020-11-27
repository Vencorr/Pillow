package com.vencorr.pillow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;
    FileConfiguration config = this.getConfig();
    SleepController control;

    // Error Messages
    String defaultErr;
    String missingBed;
    String wrongDimen;
    String dragonAlive;

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
            if (Main.plugin.config.getString("world") == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Unable to locate a world named '" + Main.plugin.config.getString("multiplayersleep.world") + "'!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
            for (World wrld : Bukkit.getWorlds()) {
                if (control.runn(wrld) < 0)
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "There was an error running Pillow.MPSleep.");
            }
        }

        defaultErr = ChatColor.translateAlternateColorCodes('&', config.getString("messages.defaultErr"));
        missingBed = ChatColor.translateAlternateColorCodes('&', config.getString("messages.missingBed"));
        wrongDimen = ChatColor.translateAlternateColorCodes('&', config.getString("messages.wrongDimen"));
        dragonAlive = ChatColor.translateAlternateColorCodes('&', config.getString("messages.dragonAlive"));

        if (config.getBoolean("bedtp.enabled")) this.getCommand("bedtp").setExecutor(new BedTeleport());
        if (config.getBoolean("hubtp.enabled")) this.getCommand("hub").setExecutor(new HubTeleport());
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    boolean hasPerms(Player player, String permission) {
        return player.hasPermission(permission) || player.isOp();
    }

    boolean enderDragonAlive(Player player, String permission) {
        boolean endConf = config.getBoolean(permission + ".allow-on-boss");
        if (player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
            EnderDragon dragon = null;
            for (LivingEntity ent : player.getWorld().getLivingEntities()) {
                if (ent instanceof EnderDragon) dragon = (EnderDragon) ent;
            }
            if (dragon != null && (endConf || !endConf)) return true;
            else return false;
        } else return false;
    }
}
