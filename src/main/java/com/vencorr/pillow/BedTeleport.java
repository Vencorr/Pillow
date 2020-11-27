package com.vencorr.pillow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedTeleport implements CommandExecutor {

    Main main = Main.plugin;
    boolean equalsDimen;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String error = main.defaultErr;
        int time = Main.plugin.config.getInt("bedtp.time");
        if (sender instanceof Player) {
            Player player = (Player) sender;


            if (player.getBedSpawnLocation() != null && playerInDimension() && !main.enderDragonAlive(player, "bedtp")) {
                equalsDimen = player.getWorld().getEnvironment().equals(player.getBedSpawnLocation().getWorld().getEnvironment());
                if (!equalsDimen) time *= Main.plugin.config.getInt("bedtp.dimension-multiplier");
                return new TeleportProgress(player, player.getBedSpawnLocation(), time).runn >= 0;
            } else {
                if (player.getBedSpawnLocation() == null) error = main.missingBed;
                if (main.enderDragonAlive(player, "bedtp")) error = main.dragonAlive;
                if (!playerInDimension()) error = main.wrongDimen;
                player.sendMessage(error);
            }
        } else {
            sender.sendMessage("Sender is not a player!");
        }
        return true;
    }

    boolean playerInDimension() {
        return !Main.plugin.config.getBoolean("bedtp.strict") || equalsDimen;
    }
}
