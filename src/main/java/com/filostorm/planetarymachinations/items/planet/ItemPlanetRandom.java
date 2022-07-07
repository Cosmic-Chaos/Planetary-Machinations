package com.filostorm.planetarymachinations.items.planet;


import com.filostorm.planetarymachinations.PlanetaryMachinations;
import com.filostorm.planetarymachinations.init.PMItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import static com.filostorm.planetarymachinations.items.planet.PlanetReference.*;

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
        assert stack.getTagCompound() != null;
        if (stack.hasTagCompound()) {
            return Arrays.asList(planetTypes).indexOf(stack.getTagCompound().getString("planetType")) +1;
        } else {
            return 0;
        }
    }
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        final ItemStack stack = playerIn.getHeldItem(handIn);
        assert stack.getTagCompound() != null;
        //NBTTagCompound nbt = stack.getTagCompound(); //Gives NPEs :(
        if (!worldIn.isRemote) {
            if (stack.hasTagCompound()) {
                if (stack.getTagCompound().getInteger("scanningLevel") < MAX_SCANNING && playerIn.isSneaking()) {
                    stack.getTagCompound().setInteger("scanningLevel", stack.getTagCompound().getInteger("scanningLevel") + 1);
                }
                if (stack.getTagCompound().getInteger("primaryMaterialAmount") > 0 && !(playerIn.isSneaking())) {
                    playerIn.inventory.addItemStackToInventory(new ItemStack(primaryOreTypes(stack).getItem(),1));
                    stack.getTagCompound().setInteger("primaryMaterialAmount", (stack.getTagCompound().getInteger("primaryMaterialAmount")-1));
                }
            } else {
                System.out.println("settingTagForPlanet...");
                stack.setTagCompound(new NBTTagCompound());
                stack.getTagCompound().setString("primaryMaterialType", primaryMaterialTypes.get(itemRand.nextInt(primaryMaterialTypes.size())));
                stack.getTagCompound().setString("secondaryMaterialType", secondaryMaterialTypes[itemRand.nextInt(secondaryMaterialTypes.length)]);
                stack.getTagCompound().setString("name", planetPrefixes[itemRand.nextInt(planetPrefixes.length)] + " " + planetSuffixes[itemRand.nextInt(planetSuffixes.length)]);
                stack.getTagCompound().setString("planetType", planetTypes[itemRand.nextInt(planetTypes.length)]);
                stack.getTagCompound().setInteger("size", itemRand.nextInt(MAX_SIZE) + 1);
                stack.getTagCompound().setInteger("primaryMaterialAmount", primaryMaterialAmount(stack));
                stack.getTagCompound().setInteger("primaryMaterialAmountMax", stack.getTagCompound().getInteger("primaryMaterialAmount"));
                stack.getTagCompound().setInteger("secondaryMaterialAmount", secondaryMaterialAmount(stack));
                stack.getTagCompound().setInteger("scanningLevel", 1);
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
        NBTTagCompound nbt = stack.getTagCompound();
        assert nbt != null;
        if (stack.hasTagCompound()) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            String primaryMaterialAmount = (formatter.format(nbt.getInteger("primaryMaterialAmount")));
            String primaryMaterialAmountMax = (formatter.format(nbt.getInteger("primaryMaterialAmountMax")));
            String secondaryMaterialAmount = (formatter.format(nbt.getInteger("secondaryMaterialAmount")));
            if (nbt.getInteger("scanningLevel") >= 1) {
                tooltip.add(I18n.format("tooltip.primary." + nbt.getString("primaryMaterialType") + ".desc") + " [" + primaryMaterialAmount + "/" + primaryMaterialAmountMax + "]");
            } //+ <ore:oreMetal>.displayName +
            if (nbt.getInteger("scanningLevel") >= 2) {
                tooltip.add(I18n.format("tooltip.secondary." + nbt.getString("secondaryMaterialType") + ".desc") + " [" + secondaryMaterialAmount + "/" + secondaryMaterialAmount + "]");
            }
            if (nbt.getInteger("scanningLevel") < MAX_SCANNING) {
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
        assert stack.getTagCompound() != null;
        if (stack.hasTagCompound()) {
                return stack.getTagCompound().getString("name");
        }
        // This will be used if the item is obtained without nbt data on it.
        return "Undiscovered Planet";
    }

}

