package net.sparkmc.kitpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Utils {

    public static String color(String color) {
        return ChatColor.translateAlternateColorCodes('&' , color);
    }

    public static String getStringLocation(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public  boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public static ItemStack readItemString(String paramString) {
        String[] arrayOfString = paramString.split(",");
        ArrayList<String> localArrayList = new ArrayList();
        ItemStack localItemStack = new ItemStack(Integer.parseInt(arrayOfString[0]), Integer.parseInt(arrayOfString[1]), Short.parseShort(arrayOfString[2]));
        for (int i = 1; i < arrayOfString.length; i++) {
            if (arrayOfString[i].startsWith("lore:")) {
                ItemMeta localItemMeta = localItemStack.getItemMeta();
                String str1 = arrayOfString[i].replace("lore:", "");
                String str3 = ChatColor.translateAlternateColorCodes('&', str1);
                localArrayList.add(str3);
                localItemMeta.setLore(localArrayList);localItemStack.setItemMeta(localItemMeta);
            }
            Enchantment[] arrayOfEnchantment;
            int str1 = (arrayOfEnchantment = Enchantment.values()).length;
            for (int str3 = 0; str3 < str1; str3++) {
                Enchantment s = arrayOfEnchantment[str3];
                if (arrayOfString[i].toUpperCase().startsWith(s.getName().toUpperCase())) {
                    String str4 = arrayOfString[i].replace(s.getName().toUpperCase() + ":", "");
                    localItemStack.addUnsafeEnchantment(s, Integer.parseInt(str4));
                }
            }
            if (arrayOfString[i].startsWith("name:")) {
                ItemMeta localItemMeta = localItemStack.getItemMeta();
                String str2 = arrayOfString[i].replace("name:", "");
                localItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', str2));
                localItemStack.setItemMeta(localItemMeta);
            }
        }
        return localItemStack;
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public Integer toInt(String value){
        return Integer.valueOf(Integer.parseInt(value));
    }

    public Short toShort(String value) {
        return Short.valueOf(Short.parseShort(value));
    }

    public static Location getLocationString(String string) {
        String[] sp = string.split(",");
        World world = Bukkit.getWorld(sp[0]);
        double x = Double.parseDouble(sp[1]);
        double y = Double.parseDouble(sp[2]);
        double z = Double.parseDouble(sp[3]);
        float yaw = Float.parseFloat(sp[4]);
        float pitch = Float.parseFloat(sp[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }
}
