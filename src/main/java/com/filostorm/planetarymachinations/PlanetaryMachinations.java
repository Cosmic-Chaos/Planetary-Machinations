package com.filostorm.planetarymachinations;

import com.filostorm.planetarymachinations.init.PMItems;
import com.filostorm.planetarymachinations.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.filostorm.planetarymachinations.init.PMItems.planetBasic;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:gregtech@[2.0.0-beta,);")
public class PlanetaryMachinations
{


    @SidedProxy(modId = Reference.MODID, clientSide = "com.filostorm.planetarymachinations.proxy.ClientProxy", serverSide = "com.filostorm.planetarymachinations.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static PlanetaryMachinations instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println(Reference.MODID + ":preInit");
        PMItems.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println(Reference.MODID + ":init");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println(Reference.MODID + ":postInit");
    }
    public static final CreativeTabs tabPlanetaryMachinations = (new CreativeTabs("tabPlanetaryMachinations") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(planetBasic);
        }
        @Override
        public boolean hasSearchBar() {
            return true;
        }

    });
}
