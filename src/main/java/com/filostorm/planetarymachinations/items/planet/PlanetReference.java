package com.filostorm.planetarymachinations.items.planet;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.*;


public class PlanetReference {

    static String[] defaultPrimaryMaterialTypes = {"titanium", "tungsten", "iridium", "platinum"};
    public static ArrayList<String> primaryMaterialTypes = new ArrayList<>();

    public static String[] primaryMaterials = {"oreTungstate", "oreCooperite", "oreTetrahedrite","oreIlmenite"};
    public static ArrayList<ItemStack> primaryMaterialOresList = new ArrayList<>();

    public static void init() {
        primaryMaterialTypes.addAll(Arrays.asList(defaultPrimaryMaterialTypes));
    }
    public static void postInit() {
        for (String oreName : primaryMaterials) {
            primaryMaterialOresList.addAll((OreDictionary.getOres(oreName)));
            System.out.println(primaryMaterialOresList.size());
        }
    }
    public static String[] secondaryMaterialTypes = {"titanium", "tungsten", "iridium", "platinum"};
    public static String[] planetTypes = {"normal", "hot"};
    public static String[] planetPrefixes = {"Bronson", "Ilius"};
    public static String[] planetSuffixes = {"Alpha", "Beta"};
    public static int MAX_SCANNING = 2;
    public static int MAX_SIZE = 3;
    static int baseAmountMin = 500000; //500,000
    static int baseAmountMax = 1000000; //1,000,000
    public static int primaryMaterialAmount(ItemStack stack) {
        int size = stack.getTagCompound().getInteger("size");
        return (int) ((Math.random() * (baseAmountMax - baseAmountMin)) + baseAmountMin)*(size);
    }
    public static int secondaryMaterialAmount(ItemStack stack) {
        int size = stack.getTagCompound().getInteger("size");
        return (int) (((Math.random() * (baseAmountMax - baseAmountMin)) + baseAmountMin)*0.66*(size));
    }
}
