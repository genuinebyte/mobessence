package dev.genbyte.mobessence.stats;

import java.text.DecimalFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev.genbyte.mobessence.MobEssence;
import net.md_5.bungee.api.ChatColor;

public class StatCommand implements CommandExecutor {
	private final MobEssence me;
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("%#.##");

	public StatCommand(MobEssence me) {
		this.me = me;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("mobessence.mestats")) {
			return false;
		}

		boolean printPercent = false;
		if (args.length > 0 && args[0].equalsIgnoreCase("percent")) {
			printPercent = true;
		}

		StatFile.Stat[] stats = me.stats.getSectionalNumbers();
		
		sender.sendMessage(ChatColor.GOLD + "Total Mob Deaths:      " + ChatColor.LIGHT_PURPLE + me.stats.getTotal());
		sender.sendMessage(ChatColor.GOLD + "Total Essence Dropped: " + ChatColor.LIGHT_PURPLE + me.stats.getTotalHits());

		for (int i = 0; i < stats.length; ++i) {
			String numStr = Integer.toString(stats[i].number);
			if (printPercent) {
				numStr = DECIMAL_FORMAT.format(stats[i].number/(double) me.stats.getTotal());
			}

			sender.sendMessage(
				ChatColor.GOLD.toString() + stats[i].start + "-" + stats[i].end + ": " +
				ChatColor.LIGHT_PURPLE + numStr
			);
		}

		return true;
	}	
}