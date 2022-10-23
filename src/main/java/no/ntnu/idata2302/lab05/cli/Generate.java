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
import java.util.Random;
import java.util.concurrent.Callable;

import no.ntnu.idata2302.lab05.Generator;
import no.ntnu.idata2302.lab05.Vector;
import no.ntnu.idata2302.lab05.export.SVG;
import no.ntnu.idata2302.lab05.generators.AldousBroder;
import no.ntnu.idata2302.lab05.generators.MyGenerator;
import no.ntnu.idata2302.lab05.Dimension;
import no.ntnu.idata2302.lab05.Factory;
import no.ntnu.idata2302.lab05.solvers.BFS;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "generate", description = "Generate a random maze")
class Generate implements Callable<Integer> {

    @Parameters(index = "0", description = "The file where to store the maze")
    private File mazeFile;

    @Option(names = { "-r", "--rows" }, description = "numbers of rows in the grid")
    private int rowCount;

    @Option(names = { "-c", "--columns" }, description = "number of columns in the grid")
    private int columnCount;

    @Option(names = { "-a", "--algorithm" }, description = "The generation algorithm ('ad' for Aldous-Broder, 'mine' for your solution)")
    private String algorithm;

    @Override
    public Integer call() throws Exception {
        var generator = selectGenerator();
        var maze = generator.generate(new Factory(),
                new Dimension(rowCount, columnCount));
        var document = new SVG(new StringBuilder());
        maze.show();

        var solver = new BFS();
        List<Vector> solution = solver.solve(maze);

        maze.print(document, solution);

        return 0;
    }

    private Generator selectGenerator() {
        switch (algorithm) {
            case "ab":
                return new AldousBroder(new Random());
            case "mine":
                return new MyGenerator(new Random());
            default:
                return new AldousBroder(new Random());
        }
    }
}
