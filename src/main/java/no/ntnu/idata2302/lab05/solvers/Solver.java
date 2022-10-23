/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.solvers;

import no.ntnu.idata2302.lab05.Maze;
import no.ntnu.idata2302.lab05.Vector;

import java.util.List;

public interface Solver {

    /**
     * Solve the given maze. Return a sequence of moves that leads to
     * the exit, or null if there is no exit.
     */
    default List<Vector> solve(Maze maze) {
        return solve(maze, new Search());
    }

    List<Vector> solve(Maze maze, Search search);

}
