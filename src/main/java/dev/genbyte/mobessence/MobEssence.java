package dev.genbyte.mobessence;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class MobEssence extends JavaPlugin {
	public int smeltTime;
	public int dropChance;

	@Override
	public void onEnable() {
		EssenceHelper.init();
		readConfig();

		getLogger().log(Level.WARNING, smeltTime + " " + dropChance);

		this.getServer().getPluginManager().registerEvents(new CraftHandler(this), this);
		this.getServer().getPluginManager().registerEvents(new MobHandler(this), this);
	}
	
	private void readConfig() {
		this.saveDefaultConfig();
		smeltTime = this.getConfig().getInt("smelt-time");
		dropChance = this.getConfig().getInt("drop-chance");
	}

	//TODO: Look into getting crafting recipies to work. This'll also help with CompressedBlocks
}