package com.filostorm.planetarymachinations.items.planet;


import com.filostorm.planetarymachinations.PlanetaryMachinations;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemPlanetRandom extends Item {


    public static String[] defaultPrimaryMaterialTypes = {"titanium", "tungsten", "iridium", "platinum"};
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

    private static Random rand = new Random();

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
        } else {
            return 0;
        }
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        final ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            if (!(stack.hasTagCompound())) {
                System.out.println("settingTagForPlanet...");
                stack.setTagCompound(new NBTTagCompound());
                stack.getTagCompound().setString("primaryMaterialType", primaryMaterialTypes.get(rand.nextInt(primaryMaterialTypes.size())));
                stack.getTagCompound().setString("secondaryMaterialType", secondaryMaterialTypes[rand.nextInt(secondaryMaterialTypes.length)]);
                stack.getTagCompound().setString("name", planetPrefixes[rand.nextInt(planetPrefixes.length)] + " " + planetSuffixes[rand.nextInt(planetSuffixes.length)]);
                stack.getTagCompound().setString("planetType", planetTypes[rand.nextInt(planetTypes.length)]);
                stack.getTagCompound().setInteger("size", rand.nextInt(MAX_SIZE) + 1);
                stack.getTagCompound().setInteger("primaryMaterialAmount", ItemPlanetRandom.primaryMaterialAmount(stack));
                stack.getTagCompound().setInteger("secondaryMaterialAmount", ItemPlanetRandom.secondaryMaterialAmount(stack));
                stack.getTagCompound().setInteger("scanningLevel", 1);
            } else if ((stack.hasTagCompound()) && stack.getTagCompound().getInteger("scanningLevel") < MAX_SCANNING) {
                stack.getTagCompound().setInteger("scanningLevel", stack.getTagCompound().getInteger("scanningLevel") + 1);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
/* use this for as soon as you pick it up
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
    }
*/

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (stack.hasTagCompound()) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            String primaryMaterialAmount = (formatter.format(stack.getTagCompound().getInteger("primaryMaterialAmount")));
            String secondaryMaterialAmount = (formatter.format(stack.getTagCompound().getInteger("secondaryMaterialAmount")));
            if (stack.getTagCompound().getInteger("scanningLevel") >= 1) {
                tooltip.add(I18n.format("tooltip.secondary." + stack.getTagCompound().getString("primaryMaterialType") + ".desc") + " [" + primaryMaterialAmount + "/" + primaryMaterialAmount + "]");
            }
            if (stack.getTagCompound().getInteger("scanningLevel") >= 2) {
                tooltip.add(I18n.format("tooltip.secondary." + stack.getTagCompound().getString("secondaryMaterialType") + ".desc") + " [" + secondaryMaterialAmount + "/" + secondaryMaterialAmount + "]");
            }
            if (stack.getTagCompound().getInteger("scanningLevel") < MAX_SCANNING) {
                tooltip.add("Additional Scanning Required for More Information");
            }
        }
        else {
            tooltip.add("Scanning Required");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound()) {
                return stack.getTagCompound().getString("name");
        }
        // This will be used if the item is obtained without nbt data on it.
        return "Undiscovered Planet";
    }

}

