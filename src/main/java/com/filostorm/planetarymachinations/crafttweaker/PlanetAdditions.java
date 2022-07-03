package com.filostorm.planetarymachinations.crafttweaker;


import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;
import com.filostorm.planetarymachinations.items.planet.ItemPlanetRandom;

import java.util.ArrayList;

import static com.filostorm.planetarymachinations.items.planet.PlanetReference.*;


@ZenRegister
@ZenClass(PlanetAdditions.CLASS)
public class PlanetAdditions {
    public static final String NAME = "PlanetAdditions";
    public static final String CLASS = "mods.planetarymachinations.PlanetAdditions";
    @ZenMethod
    public static void addPrimaryMaterial (String name) {
        CraftTweakerAPI.apply(new addPrimaryMaterial(name));
    }

    private static class addPrimaryMaterial implements IAction {
        private final String name;
        public addPrimaryMaterial(String name)
        {
            this.name = name;
        }
        @Override
        public void apply()
        {
            primaryMaterialTypes.add(name);
        }
        @Override
        public String describe()
        {
            return "Adding Material Type: " + name;
        }
    }
}