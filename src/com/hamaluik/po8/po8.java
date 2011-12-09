package com.hamaluik.po8;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class po8 extends JavaPlugin {
	// the basics
	Logger log = Logger.getLogger("Minecraft");
	
	// permissions
	PermissionManager permissions = null;
	
	// command executor
	po8CommandExecutor cmdExec = new po8CommandExecutor(this);
	
	// startup routine..
	public void onEnable() {
		// setup permissions
		if(getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
		    permissions = PermissionsEx.getPermissionManager();
		
		// register the command executor
		this.getCommand("shop").setExecutor(cmdExec);
		
		log.info("[PO8] plugin enabled");
	}

	// shutdown routine
	public void onDisable() {
		// TODO: on disabled code
		log.info("[PO8] plugin disabled");
	}
	
	public boolean hasPermission(Player player, String permission) {
		if(permissions != null) {
			return permissions.has(player, permission);
		}
		else {
			return player.isOp();
		}
	}
	
	// allow for colour tags to be used in strings..
	public String processColours(String str) {
		return str.replaceAll("(&([a-f0-9]))", "\u00A7$2");
	}
	
	// strip colour tags from strings..
	public String stripColours(String str) {
		return str.replaceAll("(&([a-f0-9]))", "");
	}
	
	public String getHTTP(String url) {
		return HTTPGet.getURL("http://po8.thehazz.net/api/api.php" + url + "&key=6047c22d1ab5fba96534d770cc0ac94e").replace("\n", "");
	}
}

