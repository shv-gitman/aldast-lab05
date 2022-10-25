/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */


package no.ntnu.idata2302.lab05.generators;

import no.ntnu.idata2302.lab05.Generator;

import java.util.Random;


public class AldousBroderTest extends GeneratorTest {

    @Override
    public Generator generator() {
        return new AldousBroder(new Random());
    }

}
