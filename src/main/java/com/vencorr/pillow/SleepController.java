package com.vencorr.pillow;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class SleepController {

    BossBar sleepBar;

    // Sleep Variables
    int sleeping;
    double sleepRequired = Main.plugin.config.getDouble("multiplayersleep.required");
    float sleepLimit;

    SleepController() {
        sleepBar = Main.plugin.getServer().createBossBar(org.bukkit.ChatColor.BLUE + "Multiplayer Sleep", BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
        sleepBar.setProgress(0);
    }

    int runn = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
        @Override
        public void run() {
            World world = Bukkit.getWorld(Main.plugin.config.getString("world"));
            int overworldPlayers = 0;
            for (Player play : Bukkit.getOnlinePlayers()) {
                if (play.getWorld().getEnvironment() == World.Environment.NORMAL) {
                    overworldPlayers++;
                }
            }
            sleepLimit = Math.round(overworldPlayers * (float) sleepRequired);
            double value = sleeping / sleepLimit;
            if (sleeping <= 0) value = 0;
            if (world.getTime() >= 12542 && !Double.isNaN(value)) {

                sleepBar.setProgress(value);
                if (sleepBar.getProgress() >= 1) {
                    world.setTime(0);
                    world.setWeatherDuration(0);
                    for (Player play : Bukkit.getOnlinePlayers()) {
                        play.sendRawMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("multiplayersleep.broadcast")));
                    }
                    sleepBar.setProgress(0);
                    sleepBar.removeAll();
                    sleeping = 0;
                }
            } else {
                sleeping = 0;
                sleepBar.setProgress(0);
                sleepBar.removeAll();
            }
        }
    }, 0, 1);
}
