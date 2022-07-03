package com.filostorm.planetarymachinations.items.planet;

import com.filostorm.planetarymachinations.init.PMItems;
import mcjty.theoneprobe.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import static com.filostorm.planetarymachinations.items.planet.PlanetReference.*;

public class PlanetEventHandler {
    /*
    Random rand = new Random();

    @SubscribeEvent
    public void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack stack = event.getItemStack();
        EntityPlayer player = event.getEntityPlayer();

        if (!stack.isEmpty() && stack.getItem() == PMItems.planetRandom && stack.hasTagCompound()) {
            player.inventory.addItemStackToInventory(new ItemStack(primaryMaterialOresList.get(rand.nextInt(primaryMaterialOresList.size())).getItem(),1));
            System.out.println("Left Click is True!");
        }
    }
    */
}