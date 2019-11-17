package com.vencorr.pillow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubTeleport implements CommandExecutor {

    World world = Bukkit.getWorld(Main.plugin.config.getString("world"));
    Main main = Main.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String error = main.defautlErr;
        int time = Main.plugin.config.getInt("hubtp.time");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) time *= Main.plugin.config.getInt("hubtp.dimension-multiplier");

            if (main.playerInOverWorld(player, "hubtp") && !main.enderDragonAlive(player, "hubtp")) return new TeleportProgress(player, world.getSpawnLocation(), time).runn >= 0;
            else {
                if (main.enderDragonAlive(player, "hubtp")) error = main.dragonAlive;
                if (!main.playerInOverWorld(player, "hubtp")) error = main.noOverworld;
                String errMes = ChatColor.RED + "Pillow.BedTP was unable to begin teleport:\n" + ChatColor.GOLD + ChatColor.ITALIC + error;
                player.sendMessage(errMes);
            }
        } else {
            sender.sendMessage("Sender must be a player!");
        }
        return true;
    }
}
