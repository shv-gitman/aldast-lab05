/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;

import org.junit.Test;

public class FactoryTest {

    private Factory factory = new Factory();

    @Test
    public void testFullyConnected() {
        Maze maze = factory.fullyConnected(new Dimension(5, 5));
        maze.show();
    }

    @Test
    public void testFullyDisconnected() {
        Maze maze = factory.fullyDisconnected(new Dimension(5, 5));
        maze.show();
    }

}
