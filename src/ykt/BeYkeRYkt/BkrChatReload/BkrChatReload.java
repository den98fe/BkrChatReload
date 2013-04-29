package ykt.BeYkeRYkt.BkrChatReload;

import java.io.File;
import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BkrChatReload extends JavaPlugin
{
  public static final Logger _log = Logger.getLogger("Minecraft");
public static Chat chat = null;
public static Permission perms = null;

  public void onEnable()
  {
	    
		//Setup Vault
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			this.getLogger().info("Vault dependency not found!");
			getServer().getPluginManager().disablePlugin(this);
		}
	
		getServer().getPluginManager().registerEvents(new BkrChatListener(this), this);
		
		setupPermissions();
		setupChat();
	  
	      getCommand("pm").setExecutor(new MessageCommand(this));
	    
		PluginDescriptionFile pdFile = getDescription();
		try {
			FileConfiguration fc = getConfig();
			if (!new File(getDataFolder(), "config.yml").exists()) {
				fc.options().header("BkrChatReload v" + pdFile.getVersion() + " Configuration" + 
					"\nOriginal Author: BeYkeRYkt" + 
					"");
				fc.addDefault("Chat.chat-format", "[%TIME%]%PREFIX%%NAME%: %MSG%" );
				fc.addDefault("Chat.chat-global", "&6[Global]%PREFIX%%NAME%: %MSG%" );
				fc.addDefault("Chat.chat-shout", "&4[Shout]%PREFIX%%NAME%: %MSG%" );
				fc.addDefault("Chat.chat-whispering", "&9[Whispering]%PREFIX%%NAME%: %MSG%" );
				fc.addDefault("Chat.player-join", "§4[BkrChat]§7%NAME% §6Joined the game" );
				fc.addDefault("Chat.player-leave", "§4[BkrChat]§7%NAME% §6Left the game" );
				fc.addDefault("Chat.adminonline", "§6Admins online" );
				fc.addDefault("Chat.players", "§5Players" );
				fc.addDefault("Radius.main", 10);
				fc.addDefault("Radius.shout", 20);
				fc.addDefault("Radius.whispering", 3);
				fc.addDefault("Locale.no-permissions", "§4You do not have permission." );
				fc.addDefault("Locale.clearme", "§aYou have clearned your own chat." );
				fc.addDefault("Locale.adminchat", "Using: /a <your message> (not <>)");
				fc.options().copyDefaults(true);
				saveConfig();
				fc.options().copyDefaults(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
  }  

  
  private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			perms = permissionProvider.getProvider();
		}
		return (perms != null);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}
		return (chat != null);
	}
	
	  public boolean canReadAdminChat(Player p)
	  {
	      if(p.hasPermission("bkrchat.adminchat") || p.isOp())
	          return true;
	      else
	          return p.isOp();
	  }

	  public static String FinalMessage(String args[], int start)
	  {
	      StringBuilder msg = new StringBuilder();
	      for(int i = start; i < args.length; i++)
	      {
	          if(i != start)
	              msg.append(" ");
	          msg.append(args[i]);
	  }
	      return msg.toString();
	  }

	  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	  {
	      Player player = (Player) sender;
	      String msg = FinalMessage(args, 0);
	  
	      //Check if the command sender is a player
	      if(sender instanceof Player){
	          if(command.getName().equalsIgnoreCase("clearme")){
	          //check if player has permission or is op
	          if(player.hasPermission("bkrchat.clearme") || player.isOp()){
	                  //Clear The Chat
	                  for(int x = 0; x < 120; x++){
	                          //Send a blank message
	                          player.sendMessage(" ");
	                          //When its done clearing
	                          if(x == 119){
	                                  //Tell the player that their chat is cleared
	                                  player.sendMessage(ChatColor.AQUA + "[BkrChatReload] " + this.getConfig().getString("Locale.clearme"));
	                                  return true;
	                          }
	                  }
	          }else{
	              //Tell the player that they have no permisison.
	              player.sendMessage(ChatColor.DARK_RED + "[BkrChatReload]" + this.getConfig().getString("Locale.no-permissions"));
	              return true;
	          }
	      }
	          
	          
	          if(command.getName().equalsIgnoreCase("clearg")){
	              //check if player has permission or is op
	              if(player.hasPermission("bkrchat.clearg") || player.isOp()){
	                      //Loop through online players
	                      for (Player playerclear : Bukkit.getOnlinePlayers()) {
	                              //Clear The Chat
	                              for(int x = 0; x < 120; x++){
	                                      playerclear.sendMessage(" ");
	                                      //When its done clearing
	                                      if (x == 119) {
	                                              //Tell the server who cleared the chat
	                                    playerclear.sendMessage(ChatColor.AQUA + "[BkrChatReload] " + ChatColor.GREEN + "Chat Clearned" + " " + ChatColor.RED + sender.getName() + ChatColor.GREEN + ".");
	                                    return true;
	                                  }
	                      }
	                      }
	                      //if the player has no permission
	              }else{
		              player.sendMessage(ChatColor.DARK_RED + "[BkrChatReload]" + this.getConfig().getString("Locale.no-permissions"));
	                      return true;
	              }
	      }
	       	  
	      if (command.getName().equalsIgnoreCase("a")) {
	    		 if(player.hasPermission("bkrchat.adminchat") || (player.isOp())) {
	          if (args.length > 0) {
	            for (Player p : getServer().getOnlinePlayers())
	              if (canReadAdminChat(p)) {
	                p.sendMessage(ChatColor.DARK_PURPLE + "[BkrChatReload:OpChanel] " + ChatColor.WHITE + player.getDisplayName() + ": " + msg);
	                _log.info("[BkrChatReload:OpChanel] " + player.getDisplayName() + ": " + msg);
		            return true;	
	              }
	          }else {
	            player.sendMessage(ChatColor.RED + "[BkrChatReload:OpChanel]" + this.getConfig().getString("Locale.adminchat"));
	            return true;
	          }
	      }else{
	        player.sendMessage(ChatColor.RED + "[BkrChatReload:OpChanel]" + this.getConfig().getString("Locale.no-permissions"));
	        return true;
	      }
	    		 }
	      }
	      
		return false;
	  }
}
