package dev.genbyte.mobessence;

import java.util.Optional;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import dev.genbyte.mobessence.stats.StatFile;

public class MobHandler implements Listener {
	private MobEssence me;
	private StatFile stats;
	private Random rand;

	public MobHandler(MobEssence me) {
		this.me = me;
		this.stats = me.stats;
		rand = new Random();
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity().hasMetadata("no_essence")) {
			return;
		}

		if (me.requirePlayerKill) {
			EntityDamageEvent damageEvent = event.getEntity().getLastDamageCause();

			if (!(damageEvent instanceof EntityDamageByEntityEvent)) {
				return;
			}
			EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) damageEvent;

			if (!(damageByEntityEvent.getDamager() instanceof Player)) {
				return;
			}
		}

		int randInt = rand.nextInt(me.dropChance);
		boolean dropFlag = me.dropChance != 0 && randInt == me.dropChance-1;
		stats.logNumber(randInt);

		if (dropFlag) {
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