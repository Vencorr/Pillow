package com.vencorr.pillow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Objects;

class TeleportProgress {

    Player player;
    BossBar bossTime;
    Location playerLoc;
    int seconds;
    Location tpLoc;

    TeleportProgress(Player player, Location intend, int sec) {
        this.player = player;
        bossTime = Main.plugin.getServer().createBossBar(ChatColor.GOLD + "Teleporting...", BarColor.YELLOW, BarStyle.SOLID, BarFlag.CREATE_FOG);
        bossTime.setProgress(1);
        bossTime.addPlayer(player);
        playerLoc = player.getLocation();
        tpLoc = intend;
        this.seconds = sec;
    }

    int runn = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
        boolean still;
        double progress;
        @Override
        public void run() {
            progress = bossTime.getProgress() - (1 / ((float)seconds * 15));
            bossTime.setProgress(Double.max(0, progress));
            still = player.getLocation().getBlock().equals(playerLoc.getBlock());

            if (still) {
                if (bossTime.getProgress() < 0.01) {
                    player.teleport(Objects.requireNonNull(tpLoc));
                    bossTime.removeAll();
                    player = null;
                    Bukkit.getScheduler().cancelTask(runn);
                }
            } else {
                bossTime.removeAll();
                player = null;
                Bukkit.getScheduler().cancelTask(runn);
            }
        }
    }, 0, 1);
}
