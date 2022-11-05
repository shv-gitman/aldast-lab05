/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.generators;

import no.ntnu.idata2302.lab05.*;
import no.ntnu.idata2302.lab05.solvers.BFS;

import java.util.Random;

public class MyGenerator implements Generator {

    private final Random random;

    public MyGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Maze generate(Factory factory, Dimension dimension) {
        AldousBroder aldousBroder = new AldousBroder(new Random());
        Maze maze = aldousBroder.generate(factory, dimension);

        return maze;
    }

    public static void main(String[] args) {
        MyGenerator myGenerator = new MyGenerator(new Random());
        Factory factory = new Factory();

        Maze maze = myGenerator.generate(factory, new Dimension(10, 20));
        var solver = new BFS();
        maze.show();

        StringBuilder stringBuilder = new StringBuilder();
        for (Vector move: solver.solve(maze)) {
            stringBuilder.append(move.asMoveName() + ", ");
        }

        System.out.println(stringBuilder);
    }

}
