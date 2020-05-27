package dev.genbyte.mobessence;

import java.util.Optional;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftHandler implements Listener {
	private MobEssence me;
	private NamespacedKey essenceInfussionKey;

	public CraftHandler(MobEssence me) {
		this.me = me;

		registerRecipe();
	}

	private void registerRecipe() {
		essenceInfussionKey = new NamespacedKey(me, "essence_infusion");
		ShapedRecipe essence_infusion =
			new ShapedRecipe(essenceInfussionKey, new ItemStack(Material.EGG))
				.shape("MSM", "SeS", "MSM")
				.setIngredient('S', Material.POTION)
				.setIngredient('M', Material.BONE_MEAL)
				.setIngredient('e', Material.EGG);
		me.getServer().addRecipe(essence_infusion);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory() instanceof FurnaceInventory) {
			if (event.isShiftClick() && event.getCurrentItem().getType() == Material.POTION) {
				FurnaceInventory inv = (FurnaceInventory) event.getInventory();

				if (!essenceOf(event.getCurrentItem()).isPresent() || inv.getSmelting() != null) {
					event.setCancelled(true);
				} else {
					inv.setSmelting(event.getCurrentItem());
					event.setCurrentItem(null);
					event.setCancelled(true);
				}
			} else if (event.getSlotType() == SlotType.CRAFTING &&
				event.getCursor().getType() == Material.POTION &&
				!essenceOf(event.getCursor()).isPresent()
			) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPrepareCraftItem(PrepareItemCraftEvent event) {
		if (recipeIsOurs(event.getRecipe())) {
			Optional<ItemStack> optstack = recipeResult(event.getInventory());
			if (optstack.isPresent()) {
				event.getInventory().setResult(optstack.get());
			} else {
				event.getInventory().setResult(null);
			}
		}
	}

	private boolean recipeIsOurs(Recipe recipe) {
		if (recipe instanceof ShapedRecipe) {
			ShapedRecipe shaped = (ShapedRecipe) recipe;
			
			return shaped.getKey().equals(essenceInfussionKey);
		}
		return false;
	}
	
	private Optional<ItemStack> recipeResult(CraftingInventory inv) {
		if (inv.getRecipe() instanceof ShapedRecipe) {
			ShapedRecipe recipe = (ShapedRecipe) inv.getRecipe();
			
			if (recipe.getKey().equals(essenceInfussionKey)) {
				ItemStack[] matrix = inv.getMatrix();
				
				Optional<EntityType> e1 = essenceOf(matrix[1]);
				Optional<EntityType> e2 = essenceOf(matrix[3]);
				Optional<EntityType> e3 = essenceOf(matrix[5]);
				Optional<EntityType> e4 = essenceOf(matrix[7]);

				if (e1.isPresent() && e2.isPresent() && e3.isPresent() && e4.isPresent() &&
					e1.get() == e2.get() && e2.get() == e3.get() && e3.get() == e4.get()
				) {
					Optional<Material> optmat = EssenceHelper.spawnEggFromEntityType(e1.get());
					if (optmat.isPresent()) {
						return Optional.of(new ItemStack(optmat.get()));
					}
				}
			}
		}
		return Optional.empty();
	}

	private Optional<EntityType> essenceOf(ItemStack stack) {
		if (stack == null || stack.getType() != Material.POTION) {return Optional.empty();}

		ItemMeta meta = stack.getItemMeta();
		if (meta == null || !meta.hasLore()) {return Optional.empty();}

		String firstLore = meta.getLore().get(0);
		if (!firstLore.startsWith("essence of ")) {return Optional.empty();}

		String etype = firstLore.substring(11);
		return EssenceHelper.entityTypeFromKey(etype);
	}
}