/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.generators;

import no.ntnu.idata2302.lab05.Dimension;
import no.ntnu.idata2302.lab05.Factory;
import no.ntnu.idata2302.lab05.Generator;
import no.ntnu.idata2302.lab05.Maze;

import java.util.Random;

public class MyGenerator implements Generator {

    private final Random random;

    public MyGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Maze generate(Factory factory, Dimension dimension) {
        // TODO: Implement this method
        throw new RuntimeException("Not yet implemented.");
    }

}
