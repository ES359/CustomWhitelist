package me.ES359.CustomWhitelist;

import org.bukkit.command.*;
import org.bukkit.*;

public class WhitelistCommand implements CommandExecutor
{
    public CustomWhitelist main;
    
    public WhitelistCommand(CustomWhitelist instance) {
        main = instance;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("setmsg")) {
            return true;
        }
        if (!sender.hasPermission("customwhitelist.setkickmsg")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Please enter the message you want.");
            sender.sendMessage(ChatColor.RED + "You can use the function %playername%, %uuid%, and \"\n\" inside your message to indicate a playername.");
            return true;
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            str.append(args[i] + " ");
        }
        String message = str.toString();
        main.getConfig().set("Messages.kick-message", (Object)message);
        main.saveConfig();
        String newMessage = main.getConfig().getString("Messages.kick-message");
        newMessage = newMessage.replaceAll("&", "�");
        sender.sendMessage(ChatColor.GREEN + "Kickmessage set to: " + newMessage);
        return true;
    }
}
