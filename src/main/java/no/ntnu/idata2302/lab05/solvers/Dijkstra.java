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


/**
 * Solve mazes using a BFS (breadth-first search).
 */
public class Dijkstra implements Solver {

    public List<Vector> solve(Maze maze, Search knownCells) {
        knownCells.markAsPending(maze.entry());
        while (knownCells.hasPendingCells()) {
            var position = knownCells.closestPending();
            for (var move : maze.validMovesAt(position)) {
                var end = position.add(move);
                knownCells.record(position, move, end);
                if (maze.isExit(end))
                    return knownCells.movesTo(end);
            }
        }
        return null;
    }

}
