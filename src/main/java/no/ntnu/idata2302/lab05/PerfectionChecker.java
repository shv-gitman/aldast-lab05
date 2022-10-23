/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;

import java.util.LinkedList;

public class PerfectionChecker {

    public boolean isPerfect(Maze maze) {
        var visited = new LinkedList<Vector>();
        var pending = new LinkedList<Vector>();
        pending.addFirst(maze.entry());
        while (!pending.isEmpty()) {
            var lastVisited = visited.isEmpty()
                ? null
                : visited.getLast();
            var currentPosition = pending.removeLast();
            visited.add(currentPosition);
            for (var move : maze.validMovesAt(currentPosition)) {
                var destination = currentPosition.add(move);
                if (lastVisited != null && destination.equals(lastVisited)) continue;
                if (!visited.contains(destination)) {
                    pending.addLast(destination);
                } else {
                    return false;
                }
            }
        }
        return visited.size() == maze.dimension().allPositions().size();
    }
}
