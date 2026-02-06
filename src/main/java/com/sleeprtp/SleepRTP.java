package com.sleeprtp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class SleepRTP extends JavaPlugin {
    
    private Random random;
    private int maxRadius = 5000;
    private int minRadius = 500;
    private int maxAttempts = 10;
    
    @Override
    public void onEnable() {
        random = new Random();
        saveDefaultConfig();
        loadConfig();
        getLogger().info("Sleep RTP has been enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("Sleep RTP has been disabled!");
    }
    
    private void loadConfig() {
        maxRadius = getConfig().getInt("max-radius", 5000);
        minRadius = getConfig().getInt("min-radius", 500);
        maxAttempts = getConfig().getInt("max-attempts", 10);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rtp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command!");
                return true;
            }
            
            Player player = (Player) sender;
            
            if (!player.hasPermission("sleeprtp.use")) {
                player.sendMessage("§cYou don't have permission to use this command!");
                return true;
            }
            
            player.sendMessage("§eFinding a random location...");
            randomTeleport(player);
            return true;
        }
        return false;
    }
    
    private void randomTeleport(Player player) {
        World world = player.getWorld();
        Location safeLoc = findSafeLocation(world);
        
        if (safeLoc != null) {
            player.teleport(safeLoc);
            player.sendMessage("§aYou have been teleported to X: " + safeLoc.getBlockX() + 
                             " Y: " + safeLoc.getBlockY() + " Z: " + safeLoc.getBlockZ());
        } else {
            player.sendMessage("§cCouldn't find a safe location. Please try again!");
        }
    }
    
    private Location findSafeLocation(World world) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            // Generate random coordinates
            int x = random.nextInt(maxRadius - minRadius) + minRadius;
            int z = random.nextInt(maxRadius - minRadius) + minRadius;
            
            // Randomly make coordinates negative
            if (random.nextBoolean()) x = -x;
            if (random.nextBoolean()) z = -z;
            
            // Get highest block at location
            int y = world.getHighestBlockYAt(x, z);
            Location loc = new Location(world, x + 0.5, y, z + 0.5);
            
            // Check if location is safe
            if (isSafeLocation(loc)) {
                return loc;
            }
        }
        return null;
    }
    
    private boolean isSafeLocation(Location loc) {
        World world = loc.getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        
        // Check block below is solid
        Material below = world.getBlockAt(x, y - 1, z).getType();
        if (!below.isSolid()) {
            return false;
        }
        
        // Check for dangerous blocks
        if (below == Material.LAVA || below == Material.MAGMA_BLOCK) {
            return false;
        }
        
        // Check two blocks above are air
        Material feet = world.getBlockAt(x, y, z).getType();
        Material head = world.getBlockAt(x, y + 1, z).getType();
        
        if (feet != Material.AIR || head != Material.AIR) {
            return false;
        }
        
        return true;
    }
}
