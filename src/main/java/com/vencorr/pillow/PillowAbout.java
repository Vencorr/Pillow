package com.vencorr.pillow;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PillowAbout implements CommandExecutor {

    String mods() {
        String modules = "main";
        if (Main.plugin.config.getBoolean("bedtp.enabled")) modules += ", BedTP";
        if (Main.plugin.config.getBoolean("hubtp.enabled")) modules += ", HubTP";
        if (Main.plugin.config.getBoolean("multiplayersleep.enabled")) modules += ", MultiplayerSleep";
        return modules;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof Player && Main.plugin.hasPerms((Player) sender, "pillow.about")) || sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lPillow Plugin Information\n&r" +
                    "&6Version: &e0.1.4\n" +
                    "&6Author: &eVencorr\n" +
                    "&6Modules: &e" + mods() + "\n" +
                    "&6GitHub: &ehttps://github.com/Vencorr/Pillow&r"));
        }
        return true;
    }
}
