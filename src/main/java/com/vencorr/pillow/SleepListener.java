package com.vencorr.pillow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class SleepListener implements Listener {

    SleepController control = Main.plugin.control;

    @EventHandler
    public void onPlayerBedEnter (PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            control.sleeping++;
            control.sleepBar.addPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerBedLeave (PlayerBedLeaveEvent event) {
        control.sleeping--;
        control.sleepBar.removePlayer(event.getPlayer());
    }
}
