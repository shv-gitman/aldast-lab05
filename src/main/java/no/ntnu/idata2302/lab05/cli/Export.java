/*
 * This file is part of NTNU's IDATA2302 Lab05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */
package no.ntnu.idata2302.lab05.cli;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import no.ntnu.idata2302.lab05.Maze;
import no.ntnu.idata2302.lab05.Vector;
import no.ntnu.idata2302.lab05.export.SVG;
import no.ntnu.idata2302.lab05.solvers.BFS;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "export", description = "Export the maze an SVG image")
class Export implements Callable<Integer> {

    @Parameters(index = "0", description = "The text file containing the maze")
    private File mazeFile;

    @Parameters(index = "1", description = "The file to generate")
    private File svgFile;

    @Option(names = { "-s", "--solution" }, description = "Outline the solution")
    private boolean showSolution;

    @Override
    public Integer call() throws Exception {
        try {
            Maze maze = loadMaze();
            List<Vector> solution = null;
            if (showSolution) {
                var solver = new BFS();
                solution = solver.solve(maze);
                System.out.println("Solution: " + solution);
            }
            saveAs(maze, solution);
            return 0;

        } catch (Exception error) {
            System.out.println("Failed: " + error.getMessage());
            return 1;

        }
    }

    private Maze loadMaze() {
        InputStream input = null;
        try {
            input = new FileInputStream(mazeFile);
            Maze maze = Maze.fromStream(input);
            System.out.println(maze.dimension() + " grid loaded from " + mazeFile.getName());
            return maze;

        } catch (IOException error) {
            var message = String.format("Could not read from '%s'.", mazeFile.getName());
            throw new RuntimeException(message, error);

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

    private void saveAs(Maze maze, List<Vector> solution) {
        var document = new SVG(new StringBuilder());
        maze.print(document, solution);

        OutputStream output = null;
        try {
            output = new FileOutputStream(svgFile);
            document.saveAs(output);

        } catch (IOException error) {
            var message = String.format("Could not write in '%s'. ", svgFile.getName());
            throw new RuntimeException(message, error);

        } finally {
            try {
                if (output != null) {
                    output.close();
                }

            } catch (IOException error) {
                var message = String.format("Could not close '%s'. ", svgFile.getName());
                throw new RuntimeException(message, error);
            }

        }
    }

}
