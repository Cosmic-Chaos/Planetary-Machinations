package com.filostorm.planetarymachinations.items.planet;


import com.filostorm.planetarymachinations.PlanetaryMachinations;
import com.filostorm.planetarymachinations.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemPlanetRandom extends Item {


    public ItemPlanetRandom(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(PlanetaryMachinations.tabPlanetaryMachinations);

        this.addPropertyOverride(new ResourceLocation("type"),
                new IItemPropertyGetter() {
                    @SideOnly(Side.CLIENT)
                    @Override
                    public float apply (ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                        return ItemPlanetRandom.getPlanetType (stack);
                    }
                });
    }
    public static int getPlanetType(ItemStack stack) {
        if (stack.hasTagCompound()) {
            return Arrays.asList(planetTypes).indexOf(stack.getTagCompound().getString("planetType")) +1;
        } return 0;
    }
    String[] primaryMaterialTypes = {"titanium", "tungsten", "iridium", "platinum"};
    static String[] planetTypes = {"normal", "hot"};
    String[] planetPrefixes = {"Bronson", "Ilius"};
    String[] planetSuffixes = {"Alpha", "Beta"};

    private static Random rand = new Random();

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);

        if (!(stack.hasTagCompound())) {
            System.out.println("settingTagForPlanet...");
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setString("primaryMaterialType", primaryMaterialTypes[rand.nextInt(primaryMaterialTypes.length)]);
            stack.getTagCompound().setString("name", planetPrefixes[rand.nextInt(planetPrefixes.length)] + " " + planetSuffixes[rand.nextInt(planetSuffixes.length)]);
            stack.getTagCompound().setString("planetType", planetTypes[rand.nextInt(planetTypes.length)]);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if ( stack.hasTagCompound()&& stack.getTagCompound().hasKey("primaryMaterialType"))
        {
            tooltip.add(I18n.format("tooltip." + stack.getTagCompound().getString("primaryMaterialType") + ".desc"));

        } else {
            tooltip.add("Additional Scanning Required");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound itemData = stack.getTagCompound();
            if (itemData.hasKey("name"))
            {
                return itemData.getString("name");
            }
        }
        // This will be used if the item is obtained without nbt data on it.

        return "Undiscovered Planet";
    }
}

