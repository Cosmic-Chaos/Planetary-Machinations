package com.filostorm.planetarymachinations.items.planet;


import com.filostorm.planetarymachinations.PlanetaryMachinations;
import com.filostorm.planetarymachinations.Reference;
import com.filostorm.planetarymachinations.init.PMItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemPlanetBasic extends Item {

    public ItemPlanetBasic(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(PlanetaryMachinations.tabPlanetaryMachinations);
        this.setHasSubtypes(true);
    }

    String[] primaryMaterialTypes = { "titanium", "tungsten", "iridum", "platinum"};
    String[] planetPremades = { "That one planet from a book", "Oh yeah"};

    private static Random rand = new Random();

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (CreativeTabs tab, NonNullList<ItemStack> items) {

        for (int pos = 0; pos < planetPremades.length; pos++) {

            ItemStack planetStack = new ItemStack(PMItems.planetBasic, 1);
            planetStack.setTagCompound(new NBTTagCompound());
            planetStack.getTagCompound().setString("primaryMaterialType", primaryMaterialTypes[rand.nextInt(primaryMaterialTypes.length)]);
            planetStack.getTagCompound().setString("name", planetPremades[pos]);
            items.add(planetStack);
        }
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if ( stack.hasTagCompound()&& stack.getTagCompound().hasKey("primaryMaterialType"))
        {
            tooltip.add("tooltip." + Reference.MODID + "." + stack.getTagCompound().getString("primaryMaterialType") + ".desc");
        }
        else
        {
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

