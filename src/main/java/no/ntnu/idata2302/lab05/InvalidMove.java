/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;


public class InvalidMove extends Exception {

    private final Vector position;
    private final Vector move;

    public InvalidMove(Vector position, Vector move) {
        super("Cannot go '" + move + "' from position " + position);
        this.move = move;
        this.position = position;
    }

    public Vector getPosition() {
        return this.position;
    }

    public Vector getMove() {
        return this.move;
    }

}
