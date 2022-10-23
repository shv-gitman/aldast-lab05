/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.generators;



import no.ntnu.idata2302.lab05.Dimension;
import no.ntnu.idata2302.lab05.Factory;
import no.ntnu.idata2302.lab05.Generator;
import no.ntnu.idata2302.lab05.Maze;
import no.ntnu.idata2302.lab05.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;



public class AldousBroder implements Generator {

    private final Random random;

    public AldousBroder(Random random) {
        this.random = random;
    }

    @Override
    public Maze generate(Factory factory, Dimension dimension) {
        var maze = factory.fullyDisconnected(dimension);
        var visited = new HashSet<Vector>();
        var entry = maze.entry();
        visited.add(entry);
        var currentPosition = entry;
        while (visited.size() < dimension.positionCount()) {
            var move = pickOne(maze.movesAt(currentPosition));
            var nextPosition = currentPosition.add(move);
            if (!visited.contains(nextPosition)) {
                maze.breakWall(currentPosition, move);
                visited.add(nextPosition);
            }
            currentPosition = nextPosition;
        }
        return maze;
    }


    private <E> E pickOne(Collection<E> candidates) {
        int chosenIndex = random.nextInt(candidates.size());
        int index = 0;
        for(var each: candidates) {
            if (index == chosenIndex) return each;
            index++;
        }
        throw new IllegalStateException("Error in pick one!");
    }


}
