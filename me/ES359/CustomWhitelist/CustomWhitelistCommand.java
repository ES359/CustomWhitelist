package me.ES359.CustomWhitelist;

import Utilities.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class CustomWhitelistCommand extends Utils implements CommandExecutor
{
    private CustomWhitelist main;
    
    public CustomWhitelistCommand( CustomWhitelist instance) {
        main = instance;
    }
    
    public boolean onCommand( CommandSender sender,  Command cmd,  String commandLabel,  String[] args) {
        if (cmd.getName().equalsIgnoreCase("customwhitelist") && !sender.hasPermission("Customwhitelist.cmd")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this.. " + "" + ChatColor.GOLD + sender.getName() + ChatColor.RED + ".");
        }
        else if (args.length < 1) {
            sender.sendMessage(ChatColor.GREEN + "Please use more arguments: /customwhitelist help");
        }
        else if (args.length > 0) {

            switch (args[0].toLowerCase()) {
                case "reload":
                case "rl": {
                    if (!sender.hasPermission("Customwhitelist.reload")) {
                        sender.sendMessage(ChatColor.RED + "Ur nawt admin. c; Talk to Admin for dis!");
                        break;
                    }
                    main.reloadConfig();
                    sender.sendMessage(color("&aThe configuration file has been &6&lreloaded&a, &b&o" + sender.getName()));
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

                default: {
                    sender.sendMessage("ur a scrub. use /customwhitelist help <3 ");
                    break;
                }
            }
        }
        return true;
    }
}
