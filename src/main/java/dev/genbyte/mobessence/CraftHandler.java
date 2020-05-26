package dev.genbyte.mobessence;

import java.util.Optional;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftHandler implements Listener {
	private MobEssence me;

	public CraftHandler(MobEssence me) {
		this.me = me;

		registerRecipe();
	}

	private void registerRecipe() {
		NamespacedKey recipeKey = new NamespacedKey(me, "essence_distillation");
		FurnaceRecipe essence_distillation = new FurnaceRecipe(
			recipeKey,
			new ItemStack(Material.DIRT),
			Material.POTION,
			30f,
			20 * me.smeltTime
		);
		me.getServer().addRecipe(essence_distillation);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory() instanceof FurnaceInventory) {
			if (event.isShiftClick() && event.getCurrentItem().getType() == Material.POTION) {
				FurnaceInventory inv = (FurnaceInventory) event.getInventory();

				if (!isEssence(event.getCurrentItem()) || inv.getSmelting() != null) {
					event.setCancelled(true);
				} else {
					inv.setSmelting(event.getCurrentItem());
					event.setCurrentItem(null);
					event.setCancelled(true);
				}
			} else if (event.getSlotType() == SlotType.CRAFTING && event.getCursor().getType() == Material.POTION && !isEssence(event.getCursor())) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent event) {
		ItemStack smelted = event.getSource();
		ItemMeta smeltedMeta = smelted.getItemMeta();
		if (!smeltedMeta.hasLore()) {
			event.setCancelled(true);
			return;
		}

		String firstLore = smeltedMeta.getLore().get(0);

		if (smelted.getType() == Material.POTION && firstLore.startsWith("essence of ")) {
			String key = firstLore.substring(11);
			Optional<Material> optmat = EssenceHelper.spawnEggFromKey(key);
			if (optmat.isPresent()) {
				event.setResult(new ItemStack(optmat.get()));
			} else {
				me.getLogger().log(Level.SEVERE, "Essence of '" + key + "' is invalid! How did this happen?");
			}
		}
	}

	private boolean isEssence(ItemStack stack) {
		if (stack == null || stack.getType() != Material.POTION) {return false;}

		ItemMeta meta = stack.getItemMeta();
		if (meta == null || !meta.hasLore()) {return false;}

		String firstLore = meta.getLore().get(0);
		return firstLore.startsWith("essence of ");
	}
}