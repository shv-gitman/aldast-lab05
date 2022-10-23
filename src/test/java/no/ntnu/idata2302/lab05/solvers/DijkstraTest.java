/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.solvers;


public class DijkstraTest extends SolverTest {

    @Override
    protected Solver createSolver() {
        return new Dijkstra();
    }

}
