/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */
package no.ntnu.idata2302.lab05.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import no.ntnu.idata2302.lab05.Maze;
import no.ntnu.idata2302.lab05.Vector;
import no.ntnu.idata2302.lab05.solvers.Factory;
import no.ntnu.idata2302.lab05.solvers.Search;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "solve", description = "Solve the given maze")
class Solve implements Callable<Integer> {

    @Parameters(index = "0", description = "The text file containing the maze")
    private File mazeFile;

    @Option(names = { "-a", "--algorithm" }, description = "Search algorithm to use, DFS, BFS, or Dijkstra")
    private String solverName = "dfs";

    @Option(names={"-v","--visited"}, description="Show visited cells")
    private boolean showVisitedCells = false;

    @Override
    public Integer call() throws Exception {
        InputStream input = null;
        try {
            input = new FileInputStream(mazeFile);
            Maze maze = Maze.fromStream(input);
            var solver = Factory.fromString(solverName);
            System.out.println(maze.dimension() + " grid loaded from " + mazeFile.getName());
            var search = new Search();
            var solution = solver.solve(maze, search);
            System.out.println("Solution: " + formatMoves(solution));

            if (showVisitedCells) {
                search.showVisitedCells();
            }

            return 0;

        } catch (IOException error) {
            System.out.println("Unable to open " + mazeFile.getName());
            return 1;

        } finally {
            if (input != null) {
                try {
                    input.close();

                } catch (IOException error) {
                    System.out.println("Unable to close " + mazeFile.getName());

                }
            }
        }
    }

    private List<String> formatMoves(List<Vector> solution) {
        var moves = new ArrayList<String>();
        for (var eachMove: solution) {
            moves.add(eachMove.asMoveName());
        }
        return moves;
    }

}
