package com.vencorr.pillow;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedTeleport implements CommandExecutor {

    private boolean overworldReq = Main.plugin.config.getBoolean("bedtp.require-overworld");
    private int time = Main.plugin.config.getInt("bedtp.time");

    private boolean plyrOverwold(Player player) {
        World.Environment env = player.getWorld().getEnvironment();
        if (overworldReq) {
            if (env.equals(World.Environment.NORMAL)) return true;
            else return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Sender is not a player!");
        } else {
            Player player = (Player) sender;
            int time = Main.plugin.config.getInt("bedtp.time");
            if (player.getWorld().getEnvironment() != World.Environment.NORMAL) time *= Main.plugin.config.getInt("bedtp.dimension-multiplier");
            if (player.getBedSpawnLocation() != null && plyrOverwold(player)) return new TeleportProgress(player, player.getBedSpawnLocation(), time).runn >= 0;
            else {
                String errMes = ChatColor.RED + "Pillow.BedTP was unable to begin teleport. This could be due to: \n" +
                                ChatColor.GOLD + ChatColor.ITALIC + " - Your bed missing.\n" +
                                " - Your bed is in a dangerous spot with no safe teleport.\n";
                if (overworldReq) errMes += " - You are not in the overworld.";
                player.sendMessage(errMes);
            }
        }
        return true;
    }
}
