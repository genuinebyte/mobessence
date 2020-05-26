package dev.genbyte.mobessence;

import java.util.Optional;
import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class MobHandler implements Listener {
	private MobEssence me;
	private Random rand;

	public MobHandler(MobEssence me) {
		this.me = me;
		rand = new Random();
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		int randInt = rand.nextInt(me.dropChance);
		boolean dropFlag = me.dropChance != 0 && randInt == me.dropChance-1;

		if (dropFlag && !event.getEntity().hasMetadata("no_essence")) {
			Optional<ItemStack> opstack = EssenceHelper.createEssence(event.getEntity());
			if (opstack.isPresent()) {
				event.getDrops().add(opstack.get());
			}
		}
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		SpawnReason reason = event.getSpawnReason();
		if (reason == SpawnReason.SPAWNER || reason == SpawnReason.SPAWNER_EGG) {
			event.getEntity().setMetadata("no_essence", new FixedMetadataValue(me, true));
		}
	}
}