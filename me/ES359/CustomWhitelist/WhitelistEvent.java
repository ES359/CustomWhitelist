package me.ES359.CustomWhitelist;

import Utilities.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import java.util.*;

public class WhitelistEvent implements Listener
{
    public CustomWhitelist main;
    Utils util;
    
    public WhitelistEvent(CustomWhitelist instance) {
        util = new Utils();
        main = instance;
    }
    
    public boolean checkWhitelist() {
        return Bukkit.getServer().hasWhitelist();
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        util.displayAuthInfo(p,main.pdfFile.getVersion());
    }
    
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        String config = main.getConfig().getString("Messages.kick-message");
        config = config.replace("{name}", p.getName());
        config = config.replace("{uuid}", "" + uuid);
        config = config.replace("\n", "\n");
        String alert = main.getConfig().getString("Messages.whitelist-alert");
        alert = alert.replace("{name}", p.getName());
        alert = alert.replace("{uuid}", "" + uuid);
        alert.replace("\n", "\n");
        if (checkWhitelist() && !p.isWhitelisted()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', config));
            Bukkit.getServer().getConsoleSender().sendMessage(util.color(alert));
            for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                if (staff.hasPermission(CWPermissions.NOTIFY)) {
                    if (p.isWhitelisted()) {
                        return;
                    }
                    staff.sendMessage(util.color(alert));
                }
            }
        }
    }
}
