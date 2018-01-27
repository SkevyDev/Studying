package me.skevydev.thesourcecode;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import comandos.Comandos;
import eventos.Events;
import items.CustomItems;

public class Main extends JavaPlugin{
	
	private CustomItems cus = new CustomItems();
	
	public void onEnable(){
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin TheSourceCode Habilitado!");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Feito para Estudar!");
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		getCommand("warp").setExecutor(new Comandos());
		getCommand("setwarp").setExecutor(new Comandos());
		getCommand("custommob").setExecutor(new Comandos());
		cus.craftCustom();
		cus.unShape();
		if(!new File(getDataFolder(), "config.yml").exists()){
			saveDefaultConfig();
		}
		runnableLivingEntity();
	}
	
	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Plugin TheSourceCode Desabilitado!");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Feito para Estudar!");
	}
	
	public void runnableLivingEntity(){
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(LivingEntity x : Bukkit.getServer().getWorld("world").getLivingEntities()){
					Damageable ss = x;
					x.setCustomName(x.getType().toString() + " ยงc" + ss.getHealth() + " ยงcโ?ค");
					x.setCustomNameVisible(true);
				}
			}
		}.runTaskTimerAsynchronously(this, 0, 5);
	}
	
}
