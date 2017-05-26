package Utilities;

import org.bukkit.event.player.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

public class Utils
{
    private String prefix;
    String author;
    
    public Utils() {
        this.prefix = this.color("&a[&9Custom Whitelist&a] ");
        this.author = "9c5dd792-dcb3-443b-ac6c-605903231eb2";
    }
    
    public boolean checkAuthor(final UUID uuid) {
        return uuid.toString().equals(this.author);
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public String color(final String message) {
        return message.replaceAll("&", "§");
    }
    
    private boolean checkWhitelist() {
        return Bukkit.getServer().hasWhitelist();
    }
    
    @Deprecated
    public void runWhitelistCheck(final PlayerLoginEvent event, final Plugin plugin) {
        String config = plugin.getConfig().getString("kick-message");
        config = config.replaceAll("%playername%", event.getPlayer().getName());
        if (this.checkWhitelist() && !event.getPlayer().isWhitelisted()) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', config));
            for (final Player staff : Bukkit.getServer().getOnlinePlayers()) {
                if (!staff.hasPermission("customwhitelist.notify") || event.getPlayer().isWhitelisted()) {
                    return;
                }
                staff.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&cAlert&6] &cThe Player, " + event.getPlayer().getName() + ChatColor.GOLD + " &cisn't &bWhitelisted."));
            }
        }
    }
}
