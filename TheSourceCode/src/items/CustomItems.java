package items;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItems {
	
	public ItemStack item = new ItemStack(Material.DIAMOND_AXE, 1);
	private ItemMeta itemMETA = item.getItemMeta();
	
	public ItemStack giveItems(){
		itemMETA.setDisplayName("§fMachado §b§lPODEROSA");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.BLUE + "Esse machado mata qualquer um!");
		itemMETA.setLore(lore);
		//itemMETA.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(itemMETA);
		return item;
	}

	public void craftCustom(){
		itemMETA.setDisplayName("§fMachado §b§lPODEROSO");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.BLUE + "Essa machado mata qualquer um!");
		itemMETA.setLore(lore);
		//itemMETA.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(itemMETA);
		ShapedRecipe rcc = new ShapedRecipe(item);
		rcc.shape("DD%", "DG%", "%G%");
		rcc.setIngredient('D', Material.BEDROCK);
		rcc.setIngredient('G', Material.STICK);
		Bukkit.getServer().addRecipe(rcc);
	}
	
	public void unShape(){
		ItemStack itm = new ItemStack(Material.ENDER_PEARL);
		
		ShapelessRecipe slr = new ShapelessRecipe(itm);
		
		slr.addIngredient(3, Material.LAVA_BUCKET);
		slr.addIngredient(3, Material.FLINT);
		
		Bukkit.getServer().addRecipe(slr);
	}

}
