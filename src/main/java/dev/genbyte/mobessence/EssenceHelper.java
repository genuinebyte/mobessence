package dev.genbyte.mobessence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EssenceHelper {
	private static boolean initialized = false;
	private static Map<String, EntityType> ENTITY_KEY_MAP;
	private static Map<EntityType, Material> SPAWN_EGG_ENTITY_MAP;

	public static void init() {
		if (!initialized) {
			ENTITY_KEY_MAP = createEntityKeyMap();
			SPAWN_EGG_ENTITY_MAP = createSpawnEggEntityMap();
		}
	}

	private static Map<String, EntityType> createEntityKeyMap() {
		Map<String, EntityType> ret = new HashMap<String, EntityType>();
		for (EntityType type : EntityType.values()) {
			if (type == EntityType.UNKNOWN) {
				continue;
			}

			ret.put(type.getKey().getKey(), type);
		}
		return ret;
	}

	/*
	put statements generated from the SpigotAPI Material list using the below javascript in Firefox.
	You have to assign the main enum <tbody> am id of "ebod" for this code to work
	https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html

	let r = document.getElementById("ebod");
	let ret = "";
 	for (t in r.children) {
		let y = r.children[t];
		if (y.nodeName == "TR") {
			if (y.children[0].innerText.includes("SPAWN_EGG")) {
				let mat = y.children[0].innerText;
				let typ = mat.replace("_SPAWN_EGG", "")
				ret += "ret.put(EntityType." + typ + ", Material." + y.children[0].innerText + ");\n";
			}
		}
	}
	*/
	private static Map<EntityType, Material> createSpawnEggEntityMap() {
		Map<EntityType, Material> ret = new HashMap<EntityType, Material>();

		ret.put(EntityType.BAT, Material.BAT_SPAWN_EGG);
		ret.put(EntityType.BEE, Material.BEE_SPAWN_EGG);
		ret.put(EntityType.BLAZE, Material.BLAZE_SPAWN_EGG);
		ret.put(EntityType.CAT, Material.CAT_SPAWN_EGG);
		ret.put(EntityType.CAVE_SPIDER, Material.CAVE_SPIDER_SPAWN_EGG);
		ret.put(EntityType.CHICKEN, Material.CHICKEN_SPAWN_EGG);
		ret.put(EntityType.COD, Material.COD_SPAWN_EGG);
		ret.put(EntityType.COW, Material.COW_SPAWN_EGG);
		ret.put(EntityType.CREEPER, Material.CREEPER_SPAWN_EGG);
		ret.put(EntityType.DOLPHIN, Material.DOLPHIN_SPAWN_EGG);
		ret.put(EntityType.DONKEY, Material.DONKEY_SPAWN_EGG);
		ret.put(EntityType.DROWNED, Material.DROWNED_SPAWN_EGG);
		ret.put(EntityType.ELDER_GUARDIAN, Material.ELDER_GUARDIAN_SPAWN_EGG);
		ret.put(EntityType.ENDERMAN, Material.ENDERMAN_SPAWN_EGG);
		ret.put(EntityType.ENDERMITE, Material.ENDERMITE_SPAWN_EGG);
		ret.put(EntityType.EVOKER, Material.EVOKER_SPAWN_EGG);
		ret.put(EntityType.FOX, Material.FOX_SPAWN_EGG);
		ret.put(EntityType.GHAST, Material.GHAST_SPAWN_EGG);
		ret.put(EntityType.GUARDIAN, Material.GUARDIAN_SPAWN_EGG);
		ret.put(EntityType.HOGLIN, Material.HOGLIN_SPAWN_EGG);
		ret.put(EntityType.HORSE, Material.HORSE_SPAWN_EGG);
		ret.put(EntityType.HUSK, Material.HUSK_SPAWN_EGG);
		ret.put(EntityType.LLAMA, Material.LLAMA_SPAWN_EGG);
		ret.put(EntityType.MAGMA_CUBE, Material.MAGMA_CUBE_SPAWN_EGG);
		ret.put(EntityType.MUSHROOM_COW, Material.MOOSHROOM_SPAWN_EGG);	// Changed from generator
		ret.put(EntityType.MULE, Material.MULE_SPAWN_EGG);
		ret.put(EntityType.OCELOT, Material.OCELOT_SPAWN_EGG);
		ret.put(EntityType.PANDA, Material.PANDA_SPAWN_EGG);
		ret.put(EntityType.PARROT, Material.PARROT_SPAWN_EGG);
		ret.put(EntityType.PHANTOM, Material.PHANTOM_SPAWN_EGG);
		ret.put(EntityType.PIG, Material.PIG_SPAWN_EGG);
		ret.put(EntityType.PIGLIN_BRUTE, Material.PIGLIN_BRUTE_SPAWN_EGG);
		ret.put(EntityType.PIGLIN, Material.PIGLIN_SPAWN_EGG);
		ret.put(EntityType.PILLAGER, Material.PILLAGER_SPAWN_EGG);
		ret.put(EntityType.POLAR_BEAR, Material.POLAR_BEAR_SPAWN_EGG);
		ret.put(EntityType.PUFFERFISH, Material.PUFFERFISH_SPAWN_EGG);
		ret.put(EntityType.RABBIT, Material.RABBIT_SPAWN_EGG);
		ret.put(EntityType.RAVAGER, Material.RAVAGER_SPAWN_EGG);
		ret.put(EntityType.SALMON, Material.SALMON_SPAWN_EGG);
		ret.put(EntityType.SHEEP, Material.SHEEP_SPAWN_EGG);
		ret.put(EntityType.SHULKER, Material.SHULKER_SPAWN_EGG);
		ret.put(EntityType.SILVERFISH, Material.SILVERFISH_SPAWN_EGG);
		ret.put(EntityType.SKELETON_HORSE, Material.SKELETON_HORSE_SPAWN_EGG);
		ret.put(EntityType.SKELETON, Material.SKELETON_SPAWN_EGG);
		ret.put(EntityType.SLIME, Material.SLIME_SPAWN_EGG);
		ret.put(EntityType.SPIDER, Material.SPIDER_SPAWN_EGG);
		ret.put(EntityType.SQUID, Material.SQUID_SPAWN_EGG);
		ret.put(EntityType.STRAY, Material.STRAY_SPAWN_EGG);
		ret.put(EntityType.STRIDER, Material.STRIDER_SPAWN_EGG);
		ret.put(EntityType.TRADER_LLAMA, Material.TRADER_LLAMA_SPAWN_EGG);
		ret.put(EntityType.TROPICAL_FISH, Material.TROPICAL_FISH_SPAWN_EGG);
		ret.put(EntityType.TURTLE, Material.TURTLE_SPAWN_EGG);
		ret.put(EntityType.VEX, Material.VEX_SPAWN_EGG);
		ret.put(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG);
		ret.put(EntityType.VINDICATOR, Material.VINDICATOR_SPAWN_EGG);
		ret.put(EntityType.WANDERING_TRADER, Material.WANDERING_TRADER_SPAWN_EGG);
		ret.put(EntityType.WITCH, Material.WITCH_SPAWN_EGG);
		ret.put(EntityType.WITHER_SKELETON, Material.WITHER_SKELETON_SPAWN_EGG);
		ret.put(EntityType.WOLF, Material.WOLF_SPAWN_EGG);
		ret.put(EntityType.ZOGLIN, Material.ZOGLIN_SPAWN_EGG);
		ret.put(EntityType.ZOMBIE_HORSE, Material.ZOMBIE_HORSE_SPAWN_EGG);
		ret.put(EntityType.ZOMBIE, Material.ZOMBIE_SPAWN_EGG);
		ret.put(EntityType.ZOMBIE_VILLAGER, Material.ZOMBIE_VILLAGER_SPAWN_EGG);
		ret.put(EntityType.ZOMBIFIED_PIGLIN, Material.ZOMBIFIED_PIGLIN_SPAWN_EGG);

		return ret;
	}

	private static Optional<Material> entityMaterial(EntityType etype) {
		Optional<Material> ret;
		/*
		EntityType enums taken the same way as createSpawnEggEntityMap, but
		replacing that `ret +=` with `ret += "case " + typ + ":\n";`
		*/
		switch (etype) {
			case BAT:
			case BEE:
			case BLAZE:
			case CAT:
			case CAVE_SPIDER:
			case CHICKEN:
			case COD:
			case COW:
			case CREEPER:
			case DOLPHIN:
			case DONKEY:
			case DROWNED:
			case ELDER_GUARDIAN:
			case ENDERMAN:
			case ENDERMITE:
			case EVOKER:
			case FOX:
			case GHAST:
			case GUARDIAN:
			case HOGLIN:
			case HORSE:
			case HUSK:
			case LLAMA:
			case MAGMA_CUBE:
			case MUSHROOM_COW:
			case MULE:
			case OCELOT:
			case PANDA:
			case PARROT:
			case PHANTOM:
			case PIG:
			case PIGLIN_BRUTE:
			case PIGLIN:
			case PILLAGER:
			case POLAR_BEAR:
			case PUFFERFISH:
			case RABBIT:
			case RAVAGER:
			case SALMON:
			case SHEEP:
			case SHULKER:
			case SILVERFISH:
			case SKELETON_HORSE:
			case SKELETON:
			case SLIME:
			case SPIDER:
			case SQUID:
			case STRAY:
			case STRIDER:
			case TRADER_LLAMA:
			case TROPICAL_FISH:
			case TURTLE:
			case VEX:
			case VILLAGER:
			case VINDICATOR:
			case WANDERING_TRADER:
			case WITCH:
			case WITHER_SKELETON:
			case WOLF:
			case ZOGLIN:
			case ZOMBIE_HORSE:
			case ZOMBIE:
			case ZOMBIE_VILLAGER:
			case ZOMBIFIED_PIGLIN:
				ret = Optional.of(Material.POTION);
				break;
			default:
				ret = Optional.empty();
		}

		return ret;
	}

	public static Optional<ItemStack> createEssence(LivingEntity entity) {
		Optional<Material> opmat = entityMaterial(entity.getType());

		if (opmat.isPresent()) {
			ItemStack essence = new ItemStack(opmat.get());
			ItemMeta meta = essence.getItemMeta();

			List<String> lore = Arrays.asList("essence of " + entity.getType().getKey().getKey());
			meta.setLore(lore);
			meta.setDisplayName("Essence");

			essence.setItemMeta(meta);

			return Optional.of(essence);
		}
		return Optional.empty();
	}

	public static Optional<EntityType> entityTypeFromKey(String key) {
		EntityType type = ENTITY_KEY_MAP.get(key);
		return type == null ? Optional.empty() : Optional.of(type);
	}

	public static Optional<Material> spawnEggFromEntityType(EntityType type) {
		Material mat = SPAWN_EGG_ENTITY_MAP.get(type);
		return mat == null ? Optional.empty() : Optional.of(mat);
	}

	public static Optional<Material> spawnEggFromKey(String key) {
		Optional<EntityType> type = entityTypeFromKey(key);
		return type.isPresent() ? spawnEggFromEntityType(type.get()) : Optional.empty();
	}
}