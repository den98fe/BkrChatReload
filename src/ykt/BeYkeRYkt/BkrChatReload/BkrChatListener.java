package ykt.BeYkeRYkt.BkrChatReload;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class BkrChatListener
  implements Listener{
 public BkrChatReload plugin;
 
	public BkrChatListener(BkrChatReload instance) {
		plugin = instance;
	}
 
@EventHandler
public void onPlayerChat(AsyncPlayerChatEvent event)
{	 
	java.util.Date date = new java.util.Date(); 
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
	String time = sdf.format(date);
Player player = event.getPlayer();
String message = (plugin.getConfig().getString("Chat.chat-format"));
String chatMessage = event.getMessage();
boolean ranged = true;
double range = plugin.getConfig().getInt("Radius.main");

message = message
.replace("%DISPLAYNAME%", player.getDisplayName())
.replace("%NAME%", player.getName())
.replace("%GROUPNAME%", BkrChatReload.chat.getPrimaryGroup(player))
.replace("%PREFIX%", BkrChatReload.chat.getPlayerPrefix(player))
.replace("%SUFFIX%", BkrChatReload.chat.getPlayerSuffix(player))
.replace("%GAMEMODE%", player.getGameMode().name())
.replace("%HEALTH%", Integer.toString(player.getHealth()))
.replace("%LEVEL%", Integer.toString(player.getLevel()))
.replace("%TOTALXP%", Integer.toString(player.getTotalExperience()))
.replace("%TIME%", time)
.replace("%MSG%", "%2$s");

message = Colors.all(message);

if (chatMessage.startsWith("*")) {
    ranged = false;
    if (player.hasPermission("bkrchat.global")) {
      message = (plugin.getConfig().getString("Chat.chat-global"));
      
      message = message
      .replace("%DISPLAYNAME%", player.getDisplayName())
      .replace("%NAME%", player.getName())
      .replace("%GROUPNAME%", BkrChatReload.chat.getPrimaryGroup(player))
      .replace("%PREFIX%", BkrChatReload.chat.getPlayerPrefix(player))
      .replace("%SUFFIX%", BkrChatReload.chat.getPlayerSuffix(player))
      .replace("%GAMEMODE%", player.getGameMode().name())
      .replace("%HEALTH%", Integer.toString(player.getHealth()))
      .replace("%LEVEL%", Integer.toString(player.getLevel()))
      .replace("%TOTALXP%", Integer.toString(player.getTotalExperience()))
      .replace("%TIME%", time)
      .replace("%MSG%", "%2$s");

      message = Colors.all(message);
      
      chatMessage = ChatColor.GOLD + chatMessage.substring(1);
    }
    else {
      player.sendMessage(ChatColor.RED + plugin.getConfig().getString("Locale.no-permissions"));
      event.setCancelled(true);
    }

  }

if (chatMessage.startsWith("!")) {
    range = plugin.getConfig().getInt("Radius.shout");
    message = plugin.getConfig().getString("Chat.chat-shout");

    message = message
    .replace("%DISPLAYNAME%", player.getDisplayName())
    .replace("%NAME%", player.getName())
    .replace("%GROUPNAME%", BkrChatReload.chat.getPrimaryGroup(player))
    .replace("%PREFIX%", BkrChatReload.chat.getPlayerPrefix(player))
    .replace("%SUFFIX%", BkrChatReload.chat.getPlayerSuffix(player))
    .replace("%GAMEMODE%", player.getGameMode().name())
    .replace("%HEALTH%", Integer.toString(player.getHealth()))
    .replace("%LEVEL%", Integer.toString(player.getLevel()))
    .replace("%TOTALXP%", Integer.toString(player.getTotalExperience()))
    .replace("%TIME%", time)
    .replace("%MSG%", "%2$s");

    message = Colors.all(message);
    
    chatMessage = ChatColor.RED + chatMessage.substring(1);
  }

  if (chatMessage.startsWith("@")) {
    range = plugin.getConfig().getInt("Radius.whispering");
    message = plugin.getConfig().getString("Chat.chat-whispering");
    
    message = message
    .replace("%DISPLAYNAME%", player.getDisplayName())
    .replace("%NAME%", player.getName())
    .replace("%GROUPNAME%", BkrChatReload.chat.getPrimaryGroup(player))
    .replace("%PREFIX%", BkrChatReload.chat.getPlayerPrefix(player))
    .replace("%SUFFIX%", BkrChatReload.chat.getPlayerSuffix(player))
    .replace("%GAMEMODE%", player.getGameMode().name())
    .replace("%HEALTH%", Integer.toString(player.getHealth()))
    .replace("%FOOD%", Integer.toString(player.getFoodLevel()))
    .replace("%LEVEL%", Integer.toString(player.getLevel()))
    .replace("%TOTALXP%", Integer.toString(player.getTotalExperience()))
    .replace("%TIME%", time)
    .replace("%MSG%", "%2$s");

    message = Colors.all(message);
    
    chatMessage = ChatColor.ITALIC + chatMessage.substring(1);
    chatMessage = ChatColor.GRAY + chatMessage;
  }
  if (ranged)
  {
    event.getRecipients().clear();
    event.getRecipients().addAll(getLocalRecipients(player, message, range));
  }
  
event.setFormat(message);
event.setMessage(chatMessage);

}

@EventHandler(priority=EventPriority.HIGH)
public void onPlayerJoin(PlayerJoinEvent event)
{
	java.util.Date date = new java.util.Date(); 
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
	String time = sdf.format(date);
Player p = event.getPlayer();
	  String players= "";
	  String admins = "";

	  for (Player player : Bukkit.getOnlinePlayers()) {

	      if (player.isOp()) {

	          admins+=player.getName() + ", ";
	      } else {
	          players+=player.getName() + ", ";
	      }

	  }

	  admins = admins.substring(0, admins.length());
	  players = players.substring(0, players.length());

	  event.getPlayer().sendMessage(plugin.getConfig().getString("Chat.adminonline") + ":§f"  + admins);
	  event.getPlayer().sendMessage(plugin.getConfig().getString("Chat.players") + ":§f " + players);
    String message = (plugin.getConfig().getString("Chat.player-join"));
    message = message
    .replace("%DISPLAYNAME%", p.getDisplayName())
    .replace("%NAME%", p.getName())
    .replace("%GROUPNAME%", BkrChatReload.chat.getPrimaryGroup(p))
    .replace("%PREFIX%", BkrChatReload.chat.getPlayerPrefix(p))
    .replace("%SUFFIX%", BkrChatReload.chat.getPlayerSuffix(p))
    .replace("%GAMEMODE%", p.getGameMode().name())
    .replace("%HEALTH%", Integer.toString(p.getHealth()))
    .replace("%FOOD%", Integer.toString(p.getFoodLevel()))
    .replace("%LEVEL%", Integer.toString(p.getLevel()))
    .replace("%TOTALXP%", Integer.toString(p.getTotalExperience()))
    .replace("%TIME%", time)
    .replace("%MSG%", "%2$s");

    message = Colors.all(message);
    
    event.setJoinMessage(message);

	}

@EventHandler
public void onPlayerQuit(PlayerQuitEvent event)
{
	java.util.Date date = new java.util.Date(); 
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
	String time = sdf.format(date);
Player p = event.getPlayer();
    String message = (plugin.getConfig().getString("Chat.player-leave"));
    
    message = message
    .replace("%DISPLAYNAME%", p.getDisplayName())
    .replace("%NAME%", p.getName())
    .replace("%GROUPNAME%", BkrChatReload.chat.getPrimaryGroup(p))
    .replace("%PREFIX%", BkrChatReload.chat.getPlayerPrefix(p))
    .replace("%SUFFIX%", BkrChatReload.chat.getPlayerSuffix(p))
    .replace("%GAMEMODE%", p.getGameMode().name())
    .replace("%HEALTH%", Integer.toString(p.getHealth()))
    .replace("%FOOD%", Integer.toString(p.getFoodLevel()))
    .replace("%LEVEL%", Integer.toString(p.getLevel()))
    .replace("%TOTALXP%", Integer.toString(p.getTotalExperience()))
    .replace("%TIME%", time)
    .replace("%MSG%", "%2$s");

    message = Colors.all(message);
    
    
    event.setQuitMessage(message);
    
	    }

@SuppressWarnings({ "rawtypes", "unchecked" })
protected List<Player> getLocalRecipients(Player sender, String message, double range)
{
  Location playerLocation = sender.getLocation();
	List recipients = new LinkedList();
  double squaredDistance = Math.pow(range, 2.0D);
  for (Player recipient : Bukkit.getServer().getOnlinePlayers())
    if (recipient.getWorld().equals(sender.getWorld()))
    {
      if (playerLocation.distanceSquared(recipient.getLocation()) <= squaredDistance)
      {
        recipients.add(recipient);
      }
    }
  return recipients;
}

}