package com.filostorm.planetarymachinations.init;

import com.filostorm.planetarymachinations.Reference;
import com.filostorm.planetarymachinations.items.planet.ItemPlanetBasic;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


@Mod.EventBusSubscriber(modid= Reference.MODID)
public class PMItems {

    public static Item planetBasic;

    public static void init() {
        planetBasic = new ItemPlanetBasic("planet_basic");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(planetBasic);
    }

    @SubscribeEvent
   public static void registerRenders(ModelRegistryEvent event) {
        registerRender(planetBasic);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
}