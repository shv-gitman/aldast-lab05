/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import no.ntnu.idata2302.lab05.Maze;


public  class PerfectionTest {

    @Test(timeout=100)
    public void shouldBePerfect() {
        var maze = new Maze(new String[]{"+-+-+-+",
                                         "|S|   |",
                                         "+ + + +",
                                         "|   |E|",
                                         "+-+-+-+"});
        assertTrue(new PerfectionChecker().isPerfect(maze));
    }


    @Test(timeout=100)
    public void shouldNotBePerfect() {
        var maze = new Maze(new String[]{"+-+-+-+",
                                         "|S|   |",
                                         "+ + + +",
                                         "|    E|",
                                         "+-+-+-+"});
        assertFalse(new PerfectionChecker().isPerfect(maze));
    }

    @Test(timeout=100)
    public void shouldNotBePerfectEither() {
        var maze = new Maze(new String[]{"+-+-+-+",
                                         "|S    |",
                                         "+ +-+ +",
                                         "|    E|",
                                         "+-+-+-+"});
        assertFalse(new PerfectionChecker().isPerfect(maze));
    }

}
