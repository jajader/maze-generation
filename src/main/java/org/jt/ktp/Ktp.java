package org.jt.ktp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jt.ktp.Listeners.BD;

import java.lang.reflect.Type;
import java.util.*;

public final class Ktp extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("NP Project enabled");
        getServer().getPluginManager().registerEvents(new BD(), this);
        new db_connect().openConnection(this);
    }

    @Override
    public void onDisable() {
    Bukkit.getConsoleSender().sendMessage("NP project disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String Label, String[] args) {
        Player p = (Player) sender;
        if (command.getName().equals("SW")) {
            Map<Enchantment, Integer> enchantments;
            enchantments = new HashMap<>();
            for (Enchantment e : Enchantment.values()) {
                if (Math.random()<0.05) {
                    enchantments.put(e, new Random().nextInt(e.getMaxLevel())+1);
                }
            }

            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);   //다이아몬드 검 생성
            sword.addUnsafeEnchantments(enchantments);  //255레벨은 일반적인 수치가 아니니 addUnsafeEnchantments로 추가해야 한다.
            ItemMeta swordMeta = sword.getItemMeta();   //검의 메타데이터
            sword.setItemMeta(swordMeta);   //메타데이터 저장
            p.getInventory().addItem(sword);
        }

        if (!(command.getName().equals("Maze"))) return false;
        if (!(sender instanceof Player)) return false;
        ArrayList<Location> history = new ArrayList<>();
        HashMap<Location, Integer> history2 = new HashMap<>();
        ArrayList<Location> ym = new ArrayList<>();

        Location lb = p.getLocation().getBlock().getLocation();
        Location llb = lb.clone();
        int rkfh = Integer.parseInt(args[0]);
        int tpfh = Integer.parseInt(args[1]);


        for (int td=0; td<5; td++) {
            for (int i=0; i<rkfh; i++) {
                for (int k=0; k<tpfh; k++) {
                    Location bs = lb.clone();
                    bs.setX(bs.getX()+k);
                    bs.setY(bs.getY()+td);
                    bs.setZ(bs.getZ()+i);
                    bs.getBlock().setType(Material.REINFORCED_DEEPSLATE);
                }
            }
        }

        for (int i=0; i<rkfh+2; i++) {
            for (int k=0; k<tpfh+2; k++) {
                Location bs = lb.clone();
                bs.setX(bs.getX()-1+k);
                bs.setY(bs.getY()+1);
                bs.setZ(bs.getZ()-1+i);
                bs.getBlock().setType(Material.AIR);
            }
        }

        for (int i=0; i<rkfh; i++) {
            for (int k=0; k<tpfh; k++) {
                Location bs = lb.clone();
                bs.setX(bs.getX()+k);
                bs.setY(bs.getY()+1);
                bs.setZ(bs.getZ()+i);
                bs.getBlock().setType(Material.REINFORCED_DEEPSLATE);
            }
        }
        Location fb = lb.clone();
        fb.setX(fb.getX()+1);
        fb.setY(fb.getY()+1);
        fb.setZ(fb.getZ()+1);
        Location current = fb.clone();
        int[] checked = check(fb);
        fb.getBlock().setType(Material.AIR);

        history.add(current.clone());
        history2.put(current, 1);
        boolean ft =true;
        final boolean[] end = {false};

        final Location[] ran = new Location[1];

        BukkitRunnable brun = new BukkitRunnable() {
                boolean ft = true;
            public void run() {
                if (!ft) {

                    for (int tfg=1; tfg<4; tfg++) {
                        for (int i=0; i<rkfh+2; i++) {
                            for (int k=0; k<tpfh+2; k++) {
                                Location bs = llb.clone();
                                bs.setX(bs.getX()+k-1);
                                bs.setY(bs.getY()+1);
                                bs.setZ(bs.getZ()+i-1);
                                Location bbs = bs.clone();
                                bbs.setY(bbs.getY()+tfg);
                                bbs.getBlock().setType(bs.getBlock().getType());

                            }
                        }
                    }

                    int idx = new Random().nextInt(ym.size());
                    ran[0] = ym.get(idx);
                    end[0] = true;

                    //Chest chest = (Chest) ran[0].getBlock().getState();
//
                    //chest.update(true);
                    //chest.getBlockInventory().setItem(13, new ItemStack(Material.STONE));
                    //chest.update();
                    //Inventory inv = chest.getInventory();
                    //inv.addItem(new ItemStack(Material.DARK_OAK_FENCE_GATE));


                    //inv.addItem(sword);




                    cancel();
                }
                ft = next(current, p, history, history2, ym);
            }
        };
        brun.runTaskTimer(this, 0L, 0L);


        if (end[0]) {
            p.sendMessage(String.valueOf(ran[0]));
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

    public boolean next(Location current, Player p, ArrayList<Location> history, HashMap<Location, Integer> history2, ArrayList<Location> history3) {
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
                        history3.add(bac);
                        next(bac, p, history, history2, history3);
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
