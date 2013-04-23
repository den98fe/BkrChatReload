package ykt.BeYkeRYkt.BkrChatReload;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandHandler implements CommandExecutor {
    protected final BkrChatReload plugin;

    public CommandHandler(BkrChatReload plugin) {
        this.plugin = plugin;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    protected static boolean anonymousCheck(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Cannot execute that command, I don't know who you are!");
            return true;
        } else {
            return false;
        }
    }

    protected static Player getPlayer(CommandSender sender, String[] args, int index) {
        if (args.length > index) {
            List<Player> players = sender.getServer().matchPlayer(args[index]);

            if (players.isEmpty()) {
                sender.sendMessage(ChatColor.RED + "I don't know who '" + args[index] + "' is!");
                return null;
            } else {
                return players.get(0);
            }
        } else {
            if (anonymousCheck(sender)) {
                return null;
            } else {
                return (Player) sender;
            }
        }
    }

    protected static String recompileMessage(String[] args, int start, int end) {
        if (start > args.length) {
            throw new IndexOutOfBoundsException();
        }

        String result = args[start];

        for (int i = start + 1; i <= end; i++) {
            result += " " + args[i];
        }

        return result;
    }
}