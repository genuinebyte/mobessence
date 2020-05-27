package dev.genbyte.mobessence;

import org.bukkit.plugin.java.JavaPlugin;

public class MobEssence extends JavaPlugin {
	public int dropChance;

	@Override
	public void onEnable() {
		EssenceHelper.init();
		readConfig();

		this.getServer().getPluginManager().registerEvents(new CraftHandler(this), this);
		this.getServer().getPluginManager().registerEvents(new MobHandler(this), this);
	}
	
	private void readConfig() {
		this.saveDefaultConfig();
		dropChance = this.getConfig().getInt("drop-chance");
	}
}