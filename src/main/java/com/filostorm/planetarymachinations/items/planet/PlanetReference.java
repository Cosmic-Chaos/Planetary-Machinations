package com.filostorm.planetarymachinations.items.planet;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class PlanetReference {
    public static void init() {
        primaryMaterialTypes.addAll(Arrays.asList(defaultPrimaryMaterialTypes));
        System.out.println("Modified ArrayList: " + primaryMaterialTypes);
    }
    static String[] defaultPrimaryMaterialTypes = {"titanium", "tungsten", "iridium", "platinum"};
    public static ArrayList<String> primaryMaterialTypes = new ArrayList<>();

    public static String[] secondaryMaterialTypes = {"titanium", "tungsten", "iridium", "platinum"};
    public static String[] planetTypes = {"normal", "hot"};
    public static String[] planetPrefixes = {"Bronson", "Ilius"};
    public static String[] planetSuffixes = {"Alpha", "Beta"};
    public static int MAX_SCANNING = 2;
    public static int MAX_SIZE = 3;
    static int baseAmountMin = 500000;
    static int baseAmountMax = 1000000;
    public static int primaryMaterialAmount(ItemStack stack) {
        int size = stack.getTagCompound().getInteger("size");
        return (int) ((Math.random() * (baseAmountMax - baseAmountMin)) + baseAmountMin)*(size);
    }
    public static int secondaryMaterialAmount(ItemStack stack) {
        int size = stack.getTagCompound().getInteger("size");
        return (int) (((Math.random() * (baseAmountMax - baseAmountMin)) + baseAmountMin)*0.66*(size));
    }
}
