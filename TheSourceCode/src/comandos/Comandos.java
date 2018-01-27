package comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.skevydev.thesourcecode.Main;
import net.minecraft.server.v1_7_R1.EntityPig;
import net.minecraft.server.v1_7_R1.WorldServer;

public class Comandos implements CommandExecutor{
	
	Plugin pl = Main.getPlugin(Main.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("warp")){
			if(!(sender instanceof Player)){
				sender.sendMessage(ChatColor.RED + "Somente os jogadores pode executar esse comando!");
				return true;
			}
			Player p = (Player) sender;
			if(args.length == 0){
				p.sendMessage(ChatColor.GREEN + "Use: /warp [nome da warp]");
				return true;
			}
			
			if(args.length == 1){
				String argSS = args[0].toLowerCase();
				if(pl.getConfig().getString("Warps." + argSS + ".World") == null){
					p.sendMessage(ChatColor.RED + "Warp não encontrada!");
					return true;
				}
					Double x = pl.getConfig().getDouble("Warps." + argSS + ".X");
					Double y = pl.getConfig().getDouble("Warps." + argSS + ".Y");
					Double z = pl.getConfig().getDouble("Warps." + argSS + ".Z");
					float pitch = (float) pl.getConfig().getDouble("Warps." + argSS + ".Pitch");
					float yaw = (float) pl.getConfig().getDouble("Warps." + argSS + ".Yaw");
					World w = Bukkit.getWorld(pl.getConfig().getString("Warps." + argSS + ".World"));
					Location tpToWarp = new Location(w, x, y, z);
					tpToWarp.setPitch(pitch);
					tpToWarp.setYaw(yaw);
					p.teleport(tpToWarp);
					p.sendMessage("§aTeleportado para warp §f" + argSS + "§a!");
			}else if(args.length > 1){
				p.sendMessage(ChatColor.GREEN + "Use: /warp [nome da warp]");
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("setwarp")){
			if(!(sender instanceof Player)){
				sender.sendMessage(ChatColor.RED + "Somente os jogadores pode executar esse comando!");
				return true;
			}
			Player p = (Player) sender;
			if(p.hasPermission("thesourcecode.setwarp")){
				if(args.length == 0){
					p.sendMessage(ChatColor.GREEN + "Use: /setwarp [nome da warp]");
					return true;
				}
				
				if(args.length == 1){
					Location loc = p.getLocation();
					
					pl.getConfig().set("Warps." + args[0].toLowerCase() + ".World", loc.getWorld().getName());
					pl.getConfig().set("Warps." + args[0] + ".X", loc.getX());
					pl.getConfig().set("Warps." + args[0] + ".Y", loc.getY());
					pl.getConfig().set("Warps." + args[0] + ".Z", loc.getZ());
					pl.getConfig().set("Warps." + args[0] + ".Pitch", loc.getPitch());
					pl.getConfig().set("Warps." + args[0] + ".Yaw", loc.getYaw());
					pl.saveConfig();
					
					p.sendMessage(ChatColor.GREEN + "Warp §f" + args[0] + ChatColor.GREEN + " setada com sucesso!");
				}else if(args.length > 1){
					p.sendMessage(ChatColor.GREEN + "Use: /setwarp [nome da warp]");
					return true;
				}
			}else{
				p.sendMessage(ChatColor.RED + "Você não tem permissão para executar este comando!");
			}
		}else if(cmd.getName().equalsIgnoreCase("custommob")){
			if(!(sender instanceof Player)){
				sender.sendMessage(ChatColor.RED + "Somente os jogadores pode executar esse comando!");
				return true;
			}
			Player p = (Player) sender;
			Location loc = p.getLocation();
			WorldServer world = ((CraftWorld)p.getWorld()).getHandle();
			
			EntityPig pigE = new EntityPig(world);
			pigE.setCustomName("§dPorco");
			pigE.setCustomNameVisible(true);
			pigE.setSneaking(true);
			pigE.getControllerJump();
			pigE.isBaby();
			pigE.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
			world.addEntity(pigE);
			p.sendMessage(ChatColor.GREEN + "Simples mob customizado spawnado!");
		}
		return false;
	}

}
