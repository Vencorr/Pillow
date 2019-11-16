package com.vencorr.pillow;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubTeleport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getWorld().getEnvironment() == World.Environment.NORMAL) return new TeleportProgress(player, player.getWorld().getSpawnLocation()).runn >= 0;
            else {
                String errMes = ChatColor.RED + "Pillow.HubTP was unable to begin teleport. This could be due to: \n" +
                        ChatColor.GOLD + ChatColor.ITALIC + " - You are not in the overworld.\n";
                player.sendMessage(errMes);
            }
        } else {
            sender.sendMessage("Sender must be a player!");
        }
        return true;
    }
}
