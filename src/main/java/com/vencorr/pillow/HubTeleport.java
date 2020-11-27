package com.vencorr.pillow;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubTeleport implements CommandExecutor {

    Main main = Main.plugin;
    boolean equalsDimen;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String error = main.defaultErr;
        int time = Main.plugin.config.getInt("hubtp.time");
        if (sender instanceof Player) {
            Player player = (Player) sender;


            if (playerInDimension() && !main.enderDragonAlive(player, "hubtp")) {
                equalsDimen = player.getWorld().getEnvironment().equals(World.Environment.NORMAL);
                if (!player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) time *= Main.plugin.config.getInt("hubtp.dimension-multiplier");
                return new TeleportProgress(player, Bukkit.getWorlds().get(0).getSpawnLocation(), time).runn >= 0;
            } else {
                if (main.enderDragonAlive(player, "hubtp")) error = main.dragonAlive;
                if (!playerInDimension()) error = main.wrongDimen;
                player.sendMessage(error);
            }
        } else {
            sender.sendMessage("Sender must be a player!");
        }
        return true;
    }

    boolean playerInDimension() {
        return !Main.plugin.config.getBoolean("bedtp.strict") || equalsDimen;
    }
}
