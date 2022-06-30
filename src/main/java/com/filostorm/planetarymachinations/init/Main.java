package com.filostorm.planetarymachinations.init;

import com.filostorm.planetarymachinations.items.planet.ItemPlanetRandom;

import java.util.Arrays;

import static com.filostorm.planetarymachinations.items.planet.ItemPlanetRandom.*;

public class Main {
    public static void main(String[] args) {
        primaryMaterialTypes.addAll(Arrays.asList(defaultPrimaryMaterialTypes));
        System.out.println("Modified ArrayList: " + primaryMaterialTypes);
    }
}
