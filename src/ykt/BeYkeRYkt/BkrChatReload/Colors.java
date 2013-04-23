package ykt.BeYkeRYkt.BkrChatReload;

import org.bukkit.ChatColor;

public class Colors {
	public static String all(String text) {
		return color(format(magic(text)));
	}

	public static String color(String text) {
		return text	.replace("&0",ChatColor.BLACK.toString())
					.replace("&1",ChatColor.DARK_BLUE.toString())
					.replace("&2",ChatColor.DARK_GREEN.toString())
					.replace("&3",ChatColor.DARK_AQUA.toString())
					.replace("&4",ChatColor.DARK_RED.toString())
					.replace("&5",ChatColor.DARK_PURPLE.toString())
					.replace("&6",ChatColor.GOLD.toString())
					.replace("&7",ChatColor.GRAY.toString())
					.replace("&8",ChatColor.DARK_GRAY.toString())
					.replace("&9",ChatColor.BLUE.toString())
					.replace("&a",ChatColor.GREEN.toString())
					.replace("&b",ChatColor.AQUA.toString())
					.replace("&c",ChatColor.RED.toString())
					.replace("&d",ChatColor.LIGHT_PURPLE.toString())
					.replace("&e",ChatColor.YELLOW.toString())
					.replace("&f",ChatColor.WHITE.toString())
					.replace("&r",ChatColor.RESET.toString());
	}

	public static String format(String text) {
		return text	.replace("&l",ChatColor.BOLD.toString())
					.replace("&m",ChatColor.STRIKETHROUGH.toString())
					.replace("&n",ChatColor.UNDERLINE.toString())
					.replace("&o",ChatColor.ITALIC.toString());
	}

	public static String magic(String text) {
		return text	.replace("&k",ChatColor.MAGIC.toString());
	}

	public static String undo(String text) {
		return text	.replace(ChatColor.BLACK.toString(),"")
					.replace(ChatColor.DARK_BLUE.toString(),"")
					.replace(ChatColor.DARK_GREEN.toString(),"")
					.replace(ChatColor.DARK_AQUA.toString(),"")
					.replace(ChatColor.DARK_RED.toString(),"")
					.replace(ChatColor.DARK_PURPLE.toString(),"")
					.replace(ChatColor.GOLD.toString(),"")
					.replace(ChatColor.GRAY.toString(),"")
					.replace(ChatColor.DARK_GRAY.toString(),"")
					.replace(ChatColor.BLUE.toString(),"")
					.replace(ChatColor.GREEN.toString(),"")
					.replace(ChatColor.AQUA.toString(),"")
					.replace(ChatColor.RED.toString(),"")
					.replace(ChatColor.LIGHT_PURPLE.toString(),"")
					.replace(ChatColor.YELLOW.toString(),"")
					.replace(ChatColor.WHITE.toString(),"")
					.replace(ChatColor.RESET.toString(),"");
	}
}