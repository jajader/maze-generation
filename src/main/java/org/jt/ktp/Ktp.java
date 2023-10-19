package org.jt.ktp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jt.ktp.Listeners.BD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public final class Ktp extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("NP Project enabled");
        getServer().getPluginManager().registerEvents(new BD(), this);
    }

    @Override
    public void onDisable() {
    Bukkit.getConsoleSender().sendMessage("NP project disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String Label, String[] args) {
        if (!(command.getName().contains("Maze"))) return false;
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        ArrayList<Location> history = new ArrayList<>();
        HashMap<Location, Integer> history2 = new HashMap<>();

        Location lb = p.getLocation().getBlock().getLocation();
        for (int i=0; i<49; i++) {
            for (int k=0; k<29; k++) {
                Location bs = lb.clone();
                bs.setX(bs.getX()-1+k);
                bs.setZ(bs.getZ()-1+i);
                bs.getBlock().setType(Material.AIR);
            }
        }

        for (int i=0; i<47; i++) {
            for (int k=0; k<27; k++) {
                Location bs = lb.clone();
                bs.setX(bs.getX()+k);
                bs.setZ(bs.getZ()+i);
                bs.getBlock().setType(Material.REINFORCED_DEEPSLATE);
            }
        }
        Location fb = lb.clone();
        fb.setX(fb.getX()+1);
        fb.setZ(fb.getZ()+1);
        Location current = fb.clone();
        int[] checked = check(fb);
        fb.getBlock().setType(Material.AIR);

        ArrayList cango = new ArrayList();
        if (checked[0] == 1) cango.add(0);
        if (checked[1] == 1) cango.add(1);
        if (checked[2] == 1) cango.add(2);
        if (checked[3] == 1) cango.add(3);

        Random random = new Random();
        int randomIndex = random.nextInt(cango.size());
        int f = (int) cango.get(randomIndex);
        history.add(current.clone());
        history2.put(current, 1);
        if (f==0) {
            current.setZ(current.getZ()-1);
            current.getBlock().setType(Material.AIR);
            current.setZ(current.getZ()-1);
            current.getBlock().setType(Material.AIR);
        }
        if (f==1) {
            current.setX(current.getX()+1);
            current.getBlock().setType(Material.AIR);
            current.setX(current.getX()+1);
            current.getBlock().setType(Material.AIR);

        }
        if (f==2) {
            current.setZ(current.getZ()+1);
            current.getBlock().setType(Material.AIR);
            current.setZ(current.getZ()+1);
            current.getBlock().setType(Material.AIR);
        }

        if (f==3) {
            current.setX(current.getX()-1);
            current.getBlock().setType(Material.AIR);
            current.setX(current.getX()-1);
            current.getBlock().setType(Material.AIR);

        }
        history.add(current.clone());
        history2.put(current, 1);
        boolean ft =true;
        while (ft) {
            ft = next(current, p, history, history2);
        }










        return true;
    }



    public int[] check(Location l) {
        Location one = l.clone();
        one.setZ(one.getZ()-2);
        Location two = l.clone();
        two.setX(two.getX()+2);
        Location three = l.clone();
        three.setZ(three.getZ()+2);
        Location four = l.clone();
        four.setX(four.getX()-2);
        int[] ar = new int[4];
        if (one.getBlock().getType() == Material.AIR) {
            ar[0] = 0;
        } else {
            ar[0] = 1;
        }
        if (two.getBlock().getType() == Material.AIR) {
            ar[1] = 0;
        } else {
            ar[1] = 1;
        }
        if (three.getBlock().getType() == Material.AIR) {
            ar[2] = 0;
        } else {
            ar[2] = 1;
        }
        if (four.getBlock().getType() == Material.AIR) {
            ar[3] = 0;
        } else {
            ar[3] = 1;
        }

        return ar;



    }


    public void check2(Location l, Player p, ArrayList<Location> history, HashMap<Location, Integer> history2){




    }

    public boolean next(Location current, Player p, ArrayList<Location> history, HashMap<Location, Integer> history2) {
        int[] checked = check(current);

        ArrayList cango = new ArrayList();
        if (checked[0] == 1) cango.add(0);
        if (checked[1] == 1) cango.add(1);
        if (checked[2] == 1) cango.add(2);
        if (checked[3] == 1) cango.add(3);

//
        if (cango.size() == 0) {
            history2.put(current, 2);
            int back = 2;
            for (int i=0; i<50; back++) {
                if (history.size()-back < 0 ) return false;
                Location bac = history.get(history.size()-back);
                //p.sendMessage(String.valueOf(history2.get(bac)));
                    int[] checked1 = check(bac);
                    ArrayList cango1 = new ArrayList();
                    if (checked1[0] == 1) cango1.add(0);
                    if (checked1[1] == 1) cango1.add(1);
                    if (checked1[2] == 1) cango1.add(2);
                    if (checked1[3] == 1) cango1.add(3);
                    //p.sendMessage(String.valueOf(cango.size()));
                    //p.sendMessage(String.valueOf(bac));
                    if (cango1.size() > 0) {
                        next(bac, p, history, history2);
                        return true;
                    }
                    history2.put(bac, 2);
            }

            return true;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(cango.size());
        int f = (int) cango.get(randomIndex);
        if (f==0) {
            current.setZ(current.getZ()-1);
            current.getBlock().setType(Material.AIR);
            current.setZ(current.getZ()-1);
            current.getBlock().setType(Material.AIR);
        }
        if (f==1) {
            current.setX(current.getX()+1);
            current.getBlock().setType(Material.AIR);
            current.setX(current.getX()+1);
            current.getBlock().setType(Material.AIR);

        }
        if (f==2) {
            current.setZ(current.getZ()+1);
            current.getBlock().setType(Material.AIR);
            current.setZ(current.getZ()+1);
            current.getBlock().setType(Material.AIR);
        }

        if (f==3) {
            current.setX(current.getX()-1);
            current.getBlock().setType(Material.AIR);
            current.setX(current.getX()-1);
            current.getBlock().setType(Material.AIR);

        }
        history.add(current.clone());
        history2.put(current, 1);


        return true;



    }



}
