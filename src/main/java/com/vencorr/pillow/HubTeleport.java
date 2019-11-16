package com.vencorr.pillow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubTeleport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return new TeleportProgress(player, player.getWorld().getSpawnLocation()).runn >= 0;
        } else {
            sender.sendMessage("Sender must be a player!");
        }
        return true;
    }
}
