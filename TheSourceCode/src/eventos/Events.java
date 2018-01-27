package eventos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import items.CustomItems;
import me.skevydev.thesourcecode.Main;

public class Events implements Listener {

	private CustomItems cus = new CustomItems();

	@SuppressWarnings("deprecation")
	@EventHandler

	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		Inventory pInventory = p.getInventory();
		ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);

		if (!p.isOnGround()) {
			pInventory.addItem(item);
		}
	}

	@SuppressWarnings("static-access")

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action action = e.getAction();
		Block b = e.getClickedBlock();
		Damageable ss = p;

		if (action.equals(action.LEFT_CLICK_BLOCK)) {
			if (b.getType().equals(Material.EMERALD_BLOCK)) {
				if (ss.getHealth() != 20) {
					ss.setHealth(ss.getHealth() + 2);
					p.sendMessage(ChatColor.GREEN + "Regenerado 1 de vida!");
				} else {
					p.sendMessage(ChatColor.RED + "Sua vida já está regenerada.");
				}
			} else {
				return;
			}
		} else {
			p.sendMessage(ChatColor.GREEN + "Você clicou no bloco: " + ChatColor.WHITE
					+ b.getType().toString().toUpperCase());
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Location b_loc = b.getLocation();

		p.sendMessage(
				ChatColor.RED + "Você quebrou o bloco: " + ChatColor.WHITE + b.getType().toString().toUpperCase());
		p.sendMessage(ChatColor.RED + "Location:");
		p.sendMessage(ChatColor.RED + "World: " + ChatColor.WHITE + b_loc.getWorld().getName());
		p.sendMessage(ChatColor.RED + "X: " + ChatColor.WHITE + b_loc.getBlockX());
		p.sendMessage(ChatColor.RED + "Y: " + ChatColor.WHITE + b_loc.getBlockY());
		p.sendMessage(ChatColor.RED + "Z: " + ChatColor.WHITE + b_loc.getBlockZ());
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Location b_loc = b.getLocation();
		p.sendMessage(
				ChatColor.GREEN + "Você colocou o bloco: " + ChatColor.WHITE + b.getType().toString().toUpperCase());
		p.sendMessage(ChatColor.GREEN + "Location:");
		p.sendMessage(ChatColor.GREEN + "World: " + ChatColor.WHITE + b_loc.getWorld().getName());
		p.sendMessage(ChatColor.GREEN + "X: " + ChatColor.WHITE + b_loc.getBlockX());
		p.sendMessage(ChatColor.GREEN + "Y: " + ChatColor.WHITE + b_loc.getBlockY());
		p.sendMessage(ChatColor.GREEN + "Z: " + ChatColor.WHITE + b_loc.getBlockZ());

		if (b.getType().equals(Material.TNT)) {
			Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.WHITE + "colocou: " + ChatColor.RED
					+ b.getType().toString().toUpperCase());
		} else if (b.getType().equals(Material.AIR) || b.getType() == null || !b.getType().equals(Material.TNT)) {
			return;
		}
	}

	@EventHandler
	public void onPunch(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		p.getInventory().addItem(cus.giveItems());
		p.sendMessage(ChatColor.BLUE + "Adicionado o machado poderoso em seu inventário!");

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		e.setJoinMessage("");

		p.sendMessage(
				ChatColor.GREEN + "Bem-Vindo(a) " + ChatColor.WHITE + p.getName() + ChatColor.GREEN + " ao servidor!");

		// Location locSPAWN = p.getWorld().getSpawnLocation();
		// p.teleport(locSPAWN);

		ItemStack itm = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bM = (BookMeta) itm.getItemMeta();
		bM.setAuthor("SkevyDev");
		bM.setTitle("Regras");
		ArrayList<String> paginas = new ArrayList<>();
		paginas.add("§f1º §cNão use hack" + "\n" + "§f2º §cNão ofenda players/staffs");
		bM.setPages(paginas);
		itm.setItemMeta(bM);
		ItemMeta itmMETA = itm.getItemMeta();
		itmMETA.setDisplayName("§dRegras");
		ArrayList<String> itmLore = new ArrayList<>();
		itmLore.add(ChatColor.WHITE + "Leia as regras para evitar de ser banido!");
		itmMETA.setLore(itmLore);
		itm.setItemMeta(itmMETA);

		if (!p.getInventory().contains(Material.BOOK)) {
			p.getInventory().addItem(itm);
		}

		if (!(p.hasPlayedBefore())) {
			Firework fw = p.getWorld().spawn(p.getLocation(), Firework.class);
			FireworkMeta fwMETA = fw.getFireworkMeta();
			Builder builder = FireworkEffect.builder();
			fwMETA.addEffect(builder.flicker(true).withColor(Color.BLUE).build());
			fwMETA.addEffect(builder.trail(true).build());
			fwMETA.addEffect(builder.withFade(Color.RED).build());
			fwMETA.addEffect(builder.with(Type.CREEPER).build());
			fwMETA.setPower(2);
			fw.setFireworkMeta(fwMETA);
		}
	}

	@EventHandler
	public void onCraftReward(CraftItemEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if (item.getType().equals(Material.DIAMOND_SWORD)) {
			Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.WHITE + " craftou a espada poderosa!");
			for (Player onlines : Bukkit.getServer().getOnlinePlayers()) {
				onlines.playSound(onlines.getLocation(), Sound.EXPLODE, 4F, 4F);
			}
		} else if (item.getType().equals(Material.GOLDEN_APPLE)) {
			Bukkit.broadcastMessage(
					ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " está craftando §emaçã dourada§f!");
			p.giveExp(100);
			p.sendMessage(ChatColor.YELLOW + "Você craftou uma maçã e recebeu 100 de xp!");
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("creativo") || e.getLine(1).equalsIgnoreCase("creativo")
				|| e.getLine(2).equalsIgnoreCase("creativo") || e.getLine(3).equalsIgnoreCase("creativo")) {
			e.setLine(0, ChatColor.GREEN + "[Creativo]");
			e.setLine(1, ChatColor.WHITE + "Clique para");
			e.setLine(2, ChatColor.WHITE + "alterar seu");
			e.setLine(3, ChatColor.WHITE + "Modo");
			p.sendMessage(ChatColor.GREEN + "Placa de creativo criada com sucesso!");
		}
	}

	@EventHandler
	public void onSingInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();

		if (b.getState() instanceof Sign) {
			Sign sign = (Sign) b.getState();
			if (sign.getLine(0).equals(ChatColor.GREEN + "[Creativo]")
					|| sign.getLine(0).equals(ChatColor.RED + "[Creativo]")
							&& sign.getLine(1).equals(ChatColor.WHITE + "Clique para")
							&& sign.getLine(2).equals(ChatColor.WHITE + "alterar seu")
							&& sign.getLine(3).equals(ChatColor.WHITE + "Modo")) {
				if (p.getGameMode() == GameMode.CREATIVE) {
					p.sendMessage(ChatColor.RED + "Você já está no modo creativo!");
					sign.setLine(0, ChatColor.RED + "[Creativo]");
					sign.update();
				} else {
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage(ChatColor.GREEN + "Seu modo de jogo foi alterado para creativo!");
					sign.setLine(0, ChatColor.GREEN + "[Creativo]");
					sign.update();
				}
			}
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void granadeLauncher(PlayerInteractEvent e) {
		ItemStack item = e.getItem();
		Action action = e.getAction();
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		loc.setY(loc.getY() + 1.5f);

		if (item == null) {
			return;
		}

		if (action.equals(action.RIGHT_CLICK_AIR) || action.equals(action.RIGHT_CLICK_BLOCK)) {
			if (item.getType().equals(Material.DIAMOND)) {
				item.setAmount(item.getAmount() - 1);
				ItemStack bomb = new ItemStack(item.getType(), 1);
				Entity drop = loc.getWorld().dropItemNaturally(loc, bomb);
				drop.setVelocity(loc.getDirection().multiply(2));

				new BukkitRunnable() {

					@Override
					public void run() {
						loc.getWorld().createExplosion(drop.getLocation(), 10, true);
					}
				}.runTaskLater(Main.getPlugin(Main.class), 40);
			}
		}
	}

	@EventHandler
	public void onEditMobDrops(EntityDeathEvent e) {
		e.getDrops().clear();
		e.setDroppedExp(0);

		LivingEntity eee = e.getEntity();

		if (eee instanceof Pig) {
			eee.getLocation().getWorld().dropItem(eee.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
			eee.getLocation().getWorld().dropItem(eee.getLocation(), new ItemStack(Material.COOKIE, 3));
		} else if (e instanceof Creeper) {
			eee.getLocation().getWorld().dropItem(eee.getLocation(), new ItemStack(Material.TNT));
			eee.getLocation().getWorld().dropItem(eee.getLocation(), new ItemStack(Material.FIREBALL));
		} else if (e instanceof Player) {
			eee.getLocation().getWorld().dropItem(eee.getLocation(), new ItemStack(Material.APPLE));
		}
	}
}
