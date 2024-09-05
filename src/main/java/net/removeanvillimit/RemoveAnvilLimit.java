package net.removeanvillimit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class RemoveAnvilLimit extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("RemoveAnvilLimit has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RemoveAnvilLimit has been disabled!");
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();

        try {
            Field repairCostField = anvilInventory.getClass().getDeclaredField("repairCost");
            repairCostField.setAccessible(true);

            int repairCost = (int) repairCostField.get(anvilInventory);

            // Check and adjust the repair cost if needed
            if (repairCost > 39) {
                repairCostField.set(anvilInventory, 39); //Set the cost below the "Too Expensive!" threshold
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}