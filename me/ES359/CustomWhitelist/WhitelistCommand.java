package me.ES359.CustomWhitelist;

import Utilities.CWPermissions;
import Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Evan on 12/27/2017.
 */
public class WhitelistCommand extends Utils implements CommandExecutor
{
    public CustomWhitelist main;

    public WhitelistCommand(CustomWhitelist instance) {
        main = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("setmsg")) {
            return true;
        }
        if (!sender.hasPermission(CWPermissions.MSG_COMMAND)) {
            sender.sendMessage(color(main.getConfig().getString("permission-messages.command")));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Please enter the message you want.");
            sender.sendMessage(ChatColor.RED + "You can use the function {player}, {uuid}, and \"\n\" inside your message to indicate a playername.");
            return true;
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            str.append(args[i] + " ");
        }
        String message = str.toString();
        main.getConfig().set("Messages.kick-message", message);
        main.saveConfig();
        String newMessage = main.getConfig().getString("Messages.kick-message");
        color(newMessage);
        sender.sendMessage(ChatColor.GREEN + "Kickmessage set to: " + newMessage);
        return true;
    }
}
