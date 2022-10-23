/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.solvers;

import java.util.Arrays;

import no.ntnu.idata2302.lab05.Vector;
import no.ntnu.idata2302.lab05.Maze;
import static no.ntnu.idata2302.lab05.Maze.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public abstract class SolverTest {

    private Solver solver;

    @Before
    public void setUp() {
        solver = createSolver();
    }

    protected abstract Solver createSolver();

    private void expect(String[] mazeText, Vector... expected) {
        var maze = new Maze(mazeText);
        var solution = solver.solve(maze);
        if (expected == null) {
            assertNull(solution);
        } else {
            assertEquals(Arrays.asList(expected), solution);
        }
    }

    @Test
    public void straightCorridor() {
        expect(new String[] {
                "+-+-+",
                "|S E|",
                "+-+-+" },
                EAST);
    }

    @Test
    public void corridorWithTurn() {
        expect(new String[] {
                "+-+-+",
                "|  S|",
                "+ +-+",
                "|  E|",
                "+-+-+"
        },
                WEST, SOUTH, EAST);
    }

    @Test
    public void oneDeadEnd() {
        expect(new String[] {
                "+-+-+-+",
                "|S    |",
                "+ +-+-+",
                "|    E|",
                "+-+-+-+"
        },
                SOUTH, EAST, EAST);
    }

    @Test
    public void multipleDeadEnds() {
        expect(new String[] {
                "+-+-+-+-+-+-+",
                "|S      |  E|",
                "+-+-+-+ + +-+",
                "|       |   |",
                "+-+-+ +-+ + +",
                "|         | |",
                "+-+-+-+-+-+-+"
        },
                EAST, EAST, EAST, SOUTH, WEST, SOUTH, EAST, EAST, NORTH, NORTH, EAST);
    }

    @Test(timeout = 100)
    public void cycle() {
        expect(new String[] {
                "+-+-+-+-+-+-+",
                "|S        |E|",
                "+ +-+ +-+ + +",
                "| |       | |",
                "+ +-+-+-+-+ +",
                "|           |",
                "+-+-+-+-+-+-+"
        },
                SOUTH, SOUTH, EAST, EAST, EAST, EAST, EAST, NORTH, NORTH);
    }

    @Test
    public void noSolution() {
        expect(new String[] {
                "+-+-+-+-+-+-+",
                "|S        |E|",
                "+ +-+ +-+ + +",
                "| |       | |",
                "+ +-+-+-+-+ +",
                "|     |     |",
                "+-+-+-+-+-+-+"
        },
                null);
    }

    @Test
    public void cycleWithouSolution() {
        expect(new String[] {
                "+-+-+-+-+-+-+",
                "|S          |",
                "+ +-+ +-+-+ +",
                "| |       | |",
                "+ +-+-+-+-+ +",
                "|           |",
                "+-+-+-+-+-+-+"
        },
                null);
    }

}
