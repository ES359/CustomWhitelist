package me.ES359.CustomWhitelist;

import org.bukkit.plugin.java.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;

public class CustomWhitelist extends JavaPlugin
{

    public PluginDescriptionFile pdfFile = this.getDescription();

    public void onEnable()
    {
        final PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new WhitelistEvent(this), this);
        this.getCommand("setmsg").setExecutor(new WhitelistCommand(this));
        this.getCommand("customwhitelist").setExecutor(new CustomWhitelistCommand(this));
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    public void loadMsgs()
    {

    }
}
