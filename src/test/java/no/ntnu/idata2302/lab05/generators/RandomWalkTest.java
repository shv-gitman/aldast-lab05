/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.generators;

import no.ntnu.idata2302.lab05.Generator;

import java.util.Random;

public class RandomWalkTest extends GeneratorTest {

    @Override
    public Generator generator() {
        return new RandomWalk(new Random());
    }

}
