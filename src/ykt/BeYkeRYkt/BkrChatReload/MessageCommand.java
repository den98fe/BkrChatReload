package ykt.BeYkeRYkt.BkrChatReload;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand extends CommandHandler {
    private Map<String, CommandSender> lastMessages = new HashMap<String, CommandSender>();

    public MessageCommand(BkrChatReload plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }

        if (!sender.hasPermission("bkrchat.msg")) {
            sender.sendMessage(plugin.getConfig().getString("Locale.no-permissions"));
            return true;
        }

        CommandSender target;

        if (args[0].equalsIgnoreCase("CONSOLE")) {
            target = plugin.getServer().getConsoleSender();
        } else {
            target = getPlayer(sender, args, 0);
        }

        if (target != null) {
            String message = recompileMessage(args, 1, args.length - 1);
            String senderName = sender.getName();
            String targetName = target.getName();

            // TODO: This should use an event, but we need some internal changes to support that fully.

            if (sender instanceof Player) {
                senderName = ((Player) sender).getDisplayName();
            }

            if (target instanceof Player) {
                targetName = ((Player) target).getDisplayName();
            }

            target.sendMessage(String.format("§6[%s]§f->§6[you]§f: %s", senderName, message));
            sender.sendMessage(String.format("§6[you]§f->§6[%s]§f: %s", targetName, message));

            lastMessages.put(targetName, sender);
        }

        return true;
    }

    public CommandSender getLastSender(CommandSender target) {
        CommandSender lastSender = null;

        if (target != null) {
            String senderName = target.getName();
            lastSender = lastMessages.get(senderName);

            if (lastSender instanceof Player) {
                if (!((Player) lastSender).isValid()) {
                    lastSender = plugin.getServer().getPlayerExact(lastSender.getName());
                    lastMessages.put(target.getName(), lastSender);
                }
            }
        }

        return lastSender;
    }
}