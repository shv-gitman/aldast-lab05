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

public class MazeTest {

    private Maze maze;

    @Before
    public void setUp() {
        maze = new Maze(new String[] {
                "+-+-+-+-+-+-+",
                "|S  |     |E|",
                "+-+ + + + + +",
                "| | | | | | |",
                "+ + + + +-+ +",
                "|     |     |",
                "+-+-+-+-+-+-+"
        });
    }

    @Test
    public void testManualSolution() throws InvalidMove {
        var strategy = new Vector[] {
                Maze.EAST, Maze.SOUTH,
                Maze.SOUTH, Maze.EAST,
                Maze.NORTH, Maze.NORTH,
                Maze.EAST, Maze.SOUTH,
                Maze.SOUTH, Maze.EAST,
                Maze.EAST, Maze.NORTH,
                Maze.NORTH };
        var position = maze.entry();
        for (var eachMove : strategy) {
            position = position.add(eachMove);
        }
        assertTrue(maze.isExit(position));
    }

}
