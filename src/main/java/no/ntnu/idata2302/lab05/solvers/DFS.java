/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.solvers;

import no.ntnu.idata2302.lab05.Maze;
import no.ntnu.idata2302.lab05.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * Solve mazes by using a depth-first search
 */
public class DFS implements Solver {

  public List<Vector> solve(Maze maze, Search knownCells) {
    knownCells.markAsPending(maze.entry());
    while (knownCells.hasPendingCells()) {
      var position = knownCells.lastPending();
      for (var move : maze.validMovesAt(position)) {
        var end = position.add(move);
        knownCells.record(position, move, end);
        if (maze.isExit(end))
          return knownCells.movesTo(end);
      }
    }
    return null;
  }

  public static void main(String[] args) {
    Maze maze = new Maze(new String[]{
        "+-+-+-+-+-+-+",
        "|S        |E|",
        "+ +-+ +-+ + +",
        "| |       | |",
        "+ +-+-+-+-+ +",
        "|           |",
        "+-+-+-+-+-+-+"
    });

    DFS solver = new DFS();

    System.out.println(solver.solve(maze));
  }
}
