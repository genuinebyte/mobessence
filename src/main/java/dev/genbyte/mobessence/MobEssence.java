package dev.genbyte.mobessence;

import org.bukkit.plugin.java.JavaPlugin;

import dev.genbyte.mobessence.stats.StatCommand;
import dev.genbyte.mobessence.stats.StatFile;

public class MobEssence extends JavaPlugin {
	public int dropChance;
	public StatFile stats;

	@Override
	public void onEnable() {
		EssenceHelper.init();
		readConfig();

		stats = new StatFile(this);
		stats.startSaveTask();

		this.getServer().getPluginManager().registerEvents(new CraftHandler(this), this);
		this.getServer().getPluginManager().registerEvents(new MobHandler(this), this);
		this.getCommand("mestats").setExecutor(new StatCommand(this));
	}
	
	@Override
	public void onDisable() {
		stats.run();
		stats.stopSaveTask();
	}

	private void readConfig() {
		this.saveDefaultConfig();
		dropChance = this.getConfig().getInt("drop-chance");
	}
}