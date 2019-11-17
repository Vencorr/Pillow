package com.vencorr.pillow;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedTeleport implements CommandExecutor {

    Main main = Main.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String error = main.defautlErr;
        int time = Main.plugin.config.getInt("bedtp.time");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) time *= Main.plugin.config.getInt("bedtp.dimension-multiplier");

            if (player.getBedSpawnLocation() != null && main.playerInOverWorld(player, "bedtp") && !main.enderDragonAlive(player, "bedtp")) {
                return new TeleportProgress(player, player.getBedSpawnLocation(), time).runn >= 0;
            } else {
                if (player.getBedSpawnLocation() == null) error = main.missingBed;
                if (main.enderDragonAlive(player, "bedtp")) error = main.dragonAlive;
                if (!main.playerInOverWorld(player, "bedtp")) error = main.noOverworld;
                String errMes = ChatColor.RED + "Pillow.BedTP was unable to begin teleport:\n" + ChatColor.GOLD + ChatColor.ITALIC + error;
                player.sendMessage(errMes);
            }
        } else {
            sender.sendMessage("Sender is not a player!");
        }
        return true;
    }
}
