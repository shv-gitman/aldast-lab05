/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.generators;

import no.ntnu.idata2302.lab05.Maze;
import no.ntnu.idata2302.lab05.Dimension;
import no.ntnu.idata2302.lab05.Generator;

import no.ntnu.idata2302.lab05.solvers.BFS;
import no.ntnu.idata2302.lab05.Factory;

import org.junit.Test;
import static org.junit.Assert.*;

public abstract class GeneratorTest {

    protected abstract Generator generator();

    @Test
    public void randomizeGrid() {
        Maze maze = generator().generate(new Factory(), new Dimension(6, 6));
        var solver = new BFS();
        var solution = solver.solve(maze);
        assertNotNull(solution);
    }

}
