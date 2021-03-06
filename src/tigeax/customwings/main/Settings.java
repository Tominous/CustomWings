package tigeax.customwings.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tigeax.customwings.gui.CWGUIType;

/*
 * Class containting all global settings from the config.yml file
 * Settings are parsed when needed for easier use
 */

public class Settings {

	private FileConfiguration config;

	private int wingViewDistance, mainGUISize, removeWingSlot, hideWingsToggleSlot, editorMainSettingsSlot;
	private String mainGUIName, editorGUIName;
	private ItemStack removeWingItem, hideWingsToggleONItem, hideWingsToggleOFFItem, editorMainSettingsItem;

	public Settings(CustomWings plugin) {
		this.config = plugin.getConfig();
		load();
	}

	public void reload() {

		load();

		// Reload players CustomWings GUI's
		for (Player player : Bukkit.getOnlinePlayers()) {

			InventoryView inv = player.getOpenInventory();

			if (inv == null) continue;

			CWGUIType cwGUIType = CustomWings.getCWGUIManager().getCWGUITypeByInvTitle(inv.getTitle());

			if (cwGUIType == null) continue;

			if (cwGUIType == CWGUIType.WINGSELECT)
				CustomWings.getCWPlayer(player).openCWGUI(CWGUIType.WINGSELECT);
			else
				CustomWings.getCWPlayer(player).openCWGUI(CWGUIType.LASTEDITORGUI);
		}
	}

	public void load() {

		wingViewDistance = config.getInt("wingViewDistance");

		mainGUIName = parseColors(config.getString("mainGUI.name"));
		mainGUISize = config.getInt("mainGUI.size");

		removeWingItem = getItem(config.getString("mainGUI.removeWingItem.name"), config.getString("mainGUI.removeWingItem.material"));
		removeWingSlot = config.getInt("mainGUI.removeWingItem.slot");

		hideWingsToggleONItem = getItem(config.getString("mainGUI.hideWingsToggleItem.nameON"), config.getString("mainGUI.hideWingsToggleItem.materialON"));
		hideWingsToggleOFFItem = getItem(config.getString("mainGUI.hideWingsToggleItem.nameOFF"), config.getString("mainGUI.hideWingsToggleItem.materialOFF"));
		hideWingsToggleSlot = config.getInt("mainGUI.hideWingsToggleItem.slot");

		editorGUIName = parseColors(config.getString("editorGUI.name"));

		editorMainSettingsItem = getItem(config.getString("editorGUI.mainSettingsItem.name"), config.getString("editorGUI.mainSettingsItem.material"));
		editorMainSettingsSlot = config.getInt("editorGUI.mainSettingsItem.slot");
	}

	public int getWingViewDistance() { return wingViewDistance; }

	public String getMainGUIName() { return mainGUIName; }

	public int getMainGUISize() { return mainGUISize; }

	public ItemStack getRemoveWingItem() { return removeWingItem; }

	public int getRemoveWingSlot() { return removeWingSlot; }

	public ItemStack getHideWingsToggleONItem() { return hideWingsToggleONItem; }

	public ItemStack getHideWingsToggleOFFItem() { return hideWingsToggleOFFItem; }

	public int getHideWingsToggleSlot() { return hideWingsToggleSlot; }

	public String getEditorGUIName() { return editorGUIName; }

	public ItemStack getEditorMainSettingsItem() { return editorMainSettingsItem; }

	public int getEditorMainSettingsSlot() { return editorMainSettingsSlot; }

	private ItemStack getItem(String name, String material) {
		ItemStack item = new ItemStack(Material.valueOf(material));

		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(parseColors(name));
		item.setItemMeta(itemMeta);

		return item;
	}
	
	private String parseColors(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

}
