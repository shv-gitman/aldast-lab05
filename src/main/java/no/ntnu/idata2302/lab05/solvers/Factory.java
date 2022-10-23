/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.solvers;

public class Factory {

    public static Solver fromString(String name) {
        var escapedName = name.toUpperCase();
        if (escapedName.equals("BFS")) {
            return new BFS();
        } else if (escapedName.equals("DFS")) {
            return new DFS();
        } else if (escapedName.equals("DIJKSTRA")) {
            return new Dijkstra();
        } else {
            return new DFS();
        }
    }

}
