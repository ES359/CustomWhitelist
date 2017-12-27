package me.ES359.CustomWhitelist;

import Utilities.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.awt.*;

public class CustomWhitelistCommand extends Utils implements CommandExecutor
{
    private CustomWhitelist main;
    
    public CustomWhitelistCommand( CustomWhitelist instance) {
        main = instance;
    }
    
    public boolean onCommand( CommandSender sender,  Command cmd,  String commandLabel,  String[] args) {
        if (cmd.getName().equalsIgnoreCase("customwhitelist") && !sender.hasPermission(CWPermissions.COMMAND_GENERAL)) {
            sender.sendMessage(color(main.getConfig().getString("permission-messages.command")));
        }
        else if (args.length < 1) {
            sender.sendMessage(main.getConfig().getString("permission-messages.argument-error"));
        }
        else if (args.length > 0) {

            switch (args[0].toLowerCase()) {
                case "reload":
                case "rl": {
                    if (!sender.hasPermission(CWPermissions.RELOAD)) {
                        sender.sendMessage(color(main.getConfig().getString("permission-messages.command")));
                        break;
                    }
                    main.reloadConfig();
                    sender.sendMessage(color(main.getConfig().getString("permissions.config-reload")));
                    break;
                }
                case "help":
                case "?": {
                    sender.sendMessage(color("&a===============&b[Help&b]&a==============="));
                    sender.sendMessage(color("&6Help menu for &b&lCustomWhitelist"));
                    sender.sendMessage(color("&c/setmsg &a&l- &7Sets the whitelist message in-game."));
                    sender.sendMessage(color("&c/customwhitelist help &a&l- &7Displays the menu you are seeing now..."));
                    sender.sendMessage(color("&c&o/customwhitelist reload &a&l- &7Only give this to Administrators please. "));
                    break;
                }

                case "enforce":
                    if(!sender.hasPermission(CWPermissions.ENFORCE_USE))
                    {
                        sender.sendMessage(color(main.getConfig().getString("permission-messages.command")));
                    }else
                    {
                         for(Player p : Bukkit.getServer().getOnlinePlayers())
                         { List kickList = new List();
                             if (!p.isWhitelisted() || !(p.hasPermission(CWPermissions.ENFORCE_BYPASS)))
                             {

                                 kickList.add(p.getName());
                                 p.kickPlayer(color(main.getConfig().getString("Messages.enforce-kick")));
                             }

                             if(p.hasPermission(CWPermissions.NOTIFY))
                             {
                                 String fix = main.getConfig().getString("Messages.enforce").replace("{admin}",sender.getName());

                                 TextComponent wlist = new TextComponent("[players]");
                                 wlist.setColor(ChatColor.GRAY);
                                 wlist.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("-> " +kickList).create()));
                                 //fix = fix.replace("{removed_players}",wlist.toString());
                                 p.sendMessage(color(fix));
                                 p.spigot().sendMessage(wlist);

                             }
                         }
                    }
                        break;

                case "status":
                    boolean wl = Bukkit.getServer().hasWhitelist();
                    int wlAmt = Bukkit.getServer().getWhitelistedPlayers().size();
                    String value = wl ? color(main.getConfig().getString("Messages.whitelist")): color(main.getConfig().getString("Messages.no-whitelist"));
                    String result = color(main.getConfig().getString("Messages.status"));
                    result = result.replace("{whitelist_status}",value);
                    result = result.replace("{whitelist_count}",""+wlAmt);
                    sender.sendMessage(result);
                default: {
                    sender.sendMessage(color(main.getConfig().getString("permission-messages.argument-error")));
                    break;
                }
            }
        }
        return true;
    }
}
