package com.hamaluik.po8;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class po8CommandExecutor implements CommandExecutor {
	private final po8 plugin;
	
	public po8CommandExecutor(po8 instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("shop")) {
			if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
				showHelp(sender);
				return true;
			}
			else if(args.length >= 1 && (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("b"))) {
				// make sure they have a balance to look up
				if(!(sender instanceof Player)) {
					returnMessage(sender, "&cI'm sorry, but you can't look up a balance because you don't have one!");
					return true;
				}
				
				// make sure they have permission
				if(!plugin.hasPermission((Player)sender, "po8.balance")) {
					returnMessage(sender, "&cYou don't have permission to do that!");
					return true;
				}
				
				// ok, now do the lookup
				String balance = plugin.getHTTP("?balance&username=" + sender.getName());
				
				// and report the results!
				if(balance.equalsIgnoreCase("")) {
					returnMessage(sender, "&cI'm sorry, but you can't look up a balance because you don't have one!");
				}
				else if(balance.equalsIgnoreCase("error")) {
					returnMessage(sender, "&cAn error occurred when attempting to contact the server!");
				}
				else {
					returnMessage(sender, "&6You currently have: &e" + balance + " &6Po8!");
				}
			}
			else if(args.length >= 2 && (args[0].equalsIgnoreCase("stock") || args[0].equalsIgnoreCase("s"))) {				
				// make sure they have permission
				if(!plugin.hasPermission((Player)sender, "po8.stock")) {
					returnMessage(sender, "&cYou don't have permission to do that!");
					return true;
				}
				
				// TODO: parse the item name -> id
				String id = args[1];
				for(int i = 0; i < args.length - 2; i++) {
					id += "%20" + args[i + 2];
				}
				if(id.equalsIgnoreCase("wool")) {
					id = "white%20wool";
				}
				
				// ok, now do the lookup
				String[] result = plugin.getHTTP("?stock&name=" + id).split(":");
				
				// now parse the result!
				String stock = result[0];
				
				// and report the results!
				if(stock.equalsIgnoreCase("")) {
					returnMessage(sender, "&cThat item doesn't exist in the store!");
				}
				else if(stock.equalsIgnoreCase("error")) {
					returnMessage(sender, "&cAn error occurred when attempting to contact the server!");
				}
				else {
					String name = result[1];
					returnMessage(sender, "&6The shop currently has &e" + stock + " &6" + name + "!");
				}
			}
			else if(args.length >= 3 && (args[0].equalsIgnoreCase("price") || args[0].equalsIgnoreCase("p")) && (args[1].equalsIgnoreCase("sell") || args[1].equalsIgnoreCase("s"))) {				
				// make sure they have permission
				if(!plugin.hasPermission((Player)sender, "po8.price")) {
					returnMessage(sender, "&cYou don't have permission to do that!");
					return true;
				}
				
				// TODO: parse the item name -> id				
				boolean isNumber = true;
				Integer amount = 1;
				try {
					amount = Integer.parseInt(args[args.length - 1]);
				}
				catch(NumberFormatException e) {
					isNumber = false;
				}
				String id = args[2];
				if(isNumber) {
					for(int i = 0; i < args.length - 4; i++) {
						id += "%20" + args[i + 3];
					}
				}
				else {
					for(int i = 0; i < args.length - 3; i++) {
						id += "%20" + args[i + 3];
					}
				}
				if(id.equalsIgnoreCase("wool")) {
					id = "white%20wool";
				}
				
				// ok, now do the lookup
				String[] result = plugin.getHTTP("?sellprice&name=" + id + "&amount=" + amount).split(":");
				// now parse the result!
				String price = result[0];
				
				// and report the results!
				if(price.equalsIgnoreCase("")) {
					returnMessage(sender, "&cThat item doesn't exist in the store!");
				}
				else if(price.equalsIgnoreCase("error")) {
					returnMessage(sender, "&cAn error occurred when attempting to contact the server!");
				}
				else {
					String name = result[1];
					returnMessage(sender, "&6The shop will sell you &e" + amount + " &6" + name + " for: &e" + price + " &6Po8!");
				}
			}
			else if(args.length >= 3 && (args[0].equalsIgnoreCase("price") || args[0].equalsIgnoreCase("p")) && (args[1].equalsIgnoreCase("buy") || args[1].equalsIgnoreCase("b"))) {				
				// make sure they have permission
				if(!plugin.hasPermission((Player)sender, "po8.price")) {
					returnMessage(sender, "&cYou don't have permission to do that!");
					return true;
				}
				
				// TODO: parse the item name -> id				
				boolean isNumber = true;
				Integer amount = 1;
				try {
					amount = Integer.parseInt(args[args.length - 1]);
				}
				catch(NumberFormatException e) {
					isNumber = false;
				}
				String id = args[2];
				if(isNumber) {
					for(int i = 0; i < args.length - 4; i++) {
						id += "%20" + args[i + 3];
					}
				}
				else {
					for(int i = 0; i < args.length - 3; i++) {
						id += "%20" + args[i + 3];
					}
				}
				if(id.equalsIgnoreCase("wool")) {
					id = "white%20wool";
				}
				
				// ok, now do the lookup
				String[] result = plugin.getHTTP("?buyprice&name=" + id + "&amount=" + amount).split(":");
				// now parse the result!
				String price = result[0];
				
				// and report the results!
				if(price.equalsIgnoreCase("")) {
					returnMessage(sender, "&cThat item doesn't exist in the store!");
				}
				else if(price.equalsIgnoreCase("error")) {
					returnMessage(sender, "&cAn error occurred when attempting to contact the server!");
				}
				else {
					String name = result[1];
					returnMessage(sender, "&6The shop will buy &e" + amount + " &6" + name + " from you for: &e" + price + " &6Po8!");
				}
			}
			else if(args.length == 3 && (args[0].equalsIgnoreCase("account") && args[1].equalsIgnoreCase("create"))) {	
				// make sure they can create an account
				if(!(sender instanceof Player)) {
					returnMessage(sender, "&cI'm sorry, but you can't create an account from the console!");
					return true;
				}
				
				// make sure they have permission
				if(!plugin.hasPermission((Player)sender, "po8.createaccount")) {
					returnMessage(sender, "&cYou don't have permission to do that!");
					return true;
				}
				
				// ok, now do it!
				String result = plugin.getHTTP("?account&create&username=" + sender.getName() + "&password=" + args[2]);
				
				// and report the results!
				if(result.equalsIgnoreCase("error")) {
					returnMessage(sender, "&cAn error occurred when attempting to contact the server!");
				}
				else {
					returnMessage(sender, "&6" + result);
				}
			}
			else if(args.length == 3 && (args[0].equalsIgnoreCase("account") && args[1].equalsIgnoreCase("password"))) {	
				// make sure they can create an account
				if(!(sender instanceof Player)) {
					returnMessage(sender, "&cI'm sorry, but you can't change your password from the console!");
					return true;
				}
				
				// make sure they have permission
				if(!plugin.hasPermission((Player)sender, "po8.changepassword")) {
					returnMessage(sender, "&cYou don't have permission to do that!");
					return true;
				}
				
				// ok, now do it!
				String result = plugin.getHTTP("?account&changepass&username=" + sender.getName() + "&password=" + args[2]);
				
				// and report the results!
				if(result.equalsIgnoreCase("error")) {
					returnMessage(sender, "&cAn error occurred when attempting to contact the server!");
				}
				else {
					returnMessage(sender, "&6" + result);
				}
			}
			return true;
		}
		
		showHelp(sender);
		return true;
	}
	
	// return a formatted message to whoever send the command
	// (no colours for the console)
	public void returnMessage(CommandSender sender, String message) {
		if(sender instanceof Player) {
			sender.sendMessage(plugin.processColours(message));
		}
		else {
			sender.sendMessage(plugin.stripColours(message));
		}
	}
	
	void showHelp(CommandSender sender) {
		returnMessage(sender, "&e--- &6Po8 Help &e---");
		boolean isConsole = false;
		if(!(sender instanceof Player)) {
			isConsole = true;
		}

		if(isConsole || plugin.hasPermission((Player)sender, "po8.balance")) {
			returnMessage(sender, "  &6/shop balance  &echecks your Po8 balance");
		}
		if(isConsole || plugin.hasPermission((Player)sender, "po8.stock")) {
			returnMessage(sender, "  &6/shop stock <item name>  &echecks the shop's stock");
		}
		if(isConsole || plugin.hasPermission((Player)sender, "po8.price")) {
			returnMessage(sender, "  &6/shop price sell <item name> [amount]  &ehow much the shop will sell for");
		}
		if(isConsole || plugin.hasPermission((Player)sender, "po8.price")) {
			returnMessage(sender, "  &6/shop price buy <item name> [amount]  &ehow much the shop will buy for");
		}
		if(isConsole || plugin.hasPermission((Player)sender, "po8.createaccount")) {
			returnMessage(sender, "  &6/shop account create <password>  &ecreates a Po8 account for yourself");
		}
		if(isConsole || plugin.hasPermission((Player)sender, "po8.changepassword")) {
			returnMessage(sender, "  &6/shop account password <password>  &echanges your Po8 password");
		}
	}
}