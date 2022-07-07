package com.filostorm.planetarymachinations.items.planet;

import crafttweaker.api.event.EnchantmentLevelSetEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import java.util.*;


public class PlanetReference {

    static Random rand = new Random();
    static String[] defaultPrimaryMaterialTypes = {"titanium", "tungsten", "iridium", "platinum"};
    public static ArrayList<String> primaryMaterialTypes = new ArrayList<>();
    //Default Veins
    static Map<String, List<String>> primaryMaterialVeins = new HashMap<>();

    public static ItemStack primaryOreTypes(ItemStack stack) {
        for (String key : primaryMaterialVeins.keySet()) {
            assert stack.getTagCompound() != null;
            if (stack.getTagCompound().getString("primaryMaterialType").equals(key)) {
                return (OreDictionary.getOres( //returns an array of item stacks
                        (primaryMaterialVeins.get(key)) //returns the array thats linked to the vein type, that is linked to the nbt
                                .get(rand.nextInt( //grabs a random ore from the linked array of ores
                                        (primaryMaterialVeins.get(key)).size()// based on the size of the array
                                ))).get(0)); //because OreDictionary.getOres returns an array, must convert back to a single item
            }
        }
        return null;
    }

    public static void init() {
        primaryMaterialTypes.addAll(Arrays.asList(defaultPrimaryMaterialTypes));
    }
    public static void postInit() {
        primaryMaterialVeins.put("titanium", Arrays.asList("oreIlmenite")); //yes these are basically random right now
        primaryMaterialVeins.put("iridium", Arrays.asList("oreCooperite"));
        primaryMaterialVeins.put("platinum", Arrays.asList("oreTetrahedrite"));
        primaryMaterialVeins.put("tungsten", Arrays.asList("oreTungstate"));
    }
    public static String[] secondaryMaterialTypes = {"titanium", "tungsten", "iridium", "platinum"};
    public static String[] planetTypes = {"normal", "hot", "ice", "wet", "moon"};
    public static String[] planetPrefixes = {"Bronson", "Ilius", "Thunderdome"};
    public static String[] planetSuffixes = {"Alpha", "Beta", "Epsilon", ""};
    public static int MAX_SCANNING = 2;
    public static int MAX_SIZE = 3;
    static int baseAmountMin = 500000; //500,000
    static int baseAmountMax = 1000000; //1,000,000
    public static int primaryMaterialAmount(ItemStack stack) {
        assert stack.getTagCompound() != null;
        int size = stack.getTagCompound().getInteger("size");
        return (int) ((Math.random() * (baseAmountMax - baseAmountMin)) + baseAmountMin)*(size);
    }
    public static int secondaryMaterialAmount(ItemStack stack) {
        assert stack.getTagCompound() != null;
        int size = stack.getTagCompound().getInteger("size");
        return (int) (((Math.random() * (baseAmountMax - baseAmountMin)) + baseAmountMin)*0.66*(size));
    }
}
