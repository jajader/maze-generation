package org.jt.ktp.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class BD implements Listener {

    @EventHandler
    public void swap(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (e.getPlayer().getInventory().getItem(e.getPreviousSlot()) != null) {
            if (e.getPlayer().getInventory().getItem(e.getPreviousSlot()).getType() == Material.WARPED_FUNGUS_ON_A_STICK) {

            }
        }

        if (e.getPlayer().getInventory().getItem(e.getNewSlot()) != null) {
            if (e.getPlayer().getInventory().getItem(e.getNewSlot()).getType() == Material.WARPED_FUNGUS_ON_A_STICK) {
                BlockDisplay bd = (BlockDisplay) p.getWorld().spawn(p.getLocation(), BlockDisplay.class, (display) -> {
                    display.setTransformation(new Transformation(new Vector3f(), new AxisAngle4f(), new Vector3f(), new AxisAngle4f()));
                });
                bd.setBlock(Material.BLUE_STAINED_GLASS.createBlockData());



            }

        }


    }

}
