package ykt.BeYkeRYkt.BkrChatReload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


@SuppressWarnings("deprecation")
public class BkrChatListener
  implements Listener{
 public BkrChatReload plugin;
 
	public BkrChatListener(BkrChatReload instance) {
		plugin = instance;
	}
 
	  
	  @EventHandler(priority=EventPriority.HIGH)
	  public void Save(PlayerChatEvent e)
	    throws Exception
	  {
	    Player player = e.getPlayer();
	    if (!e.isCancelled()) {
	      Calendar cal = new GregorianCalendar();
	      File dir = new File("plugins/BkrChatReload/logs/chat");
	      dir.mkdirs();

	      File f = new File("plugins/BkrChatReload/logs/chat/" + new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()) + ".txt");
	      if (!f.exists()) {
	        f.createNewFile();
	      }
	      BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
	      bw.write("[" + new SimpleDateFormat("kk:mm:ss").format(cal.getTime()) + "]" + player.getDisplayName() + ":" + e.getMessage());
	      bw.newLine();
	      bw.close();
	    }
	  }
	
@EventHandler(priority = EventPriority.LOWEST)
public void onPlayerChat(PlayerChatEvent event)
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

if (chatMessage.startsWith("^")) {
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
public void onPlayerJoin(final PlayerJoinEvent event)
{
	Player p = event.getPlayer();
	java.util.Date date = new java.util.Date(); 
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
	String time = sdf.format(date);
    Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
        public void run() {
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

	  String adminmsg = plugin.getConfig().getString("Chat.adminonline") + ":&f"  + admins;
	  
	  adminmsg = Colors.all(adminmsg);
	  
	  String playermsg = plugin.getConfig().getString("Chat.players") + ":&f " + players;
	  
	  playermsg = Colors.all(playermsg);
	  
	  event.getPlayer().sendMessage(adminmsg);
	  event.getPlayer().sendMessage(playermsg);
    }
    }
    , 40L); 
    
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

@EventHandler(priority = EventPriority.LOWEST)
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
