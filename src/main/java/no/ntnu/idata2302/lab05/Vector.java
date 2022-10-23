/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;

public class Vector {

    public final int row;
    public final int column;

    public Vector(int row, int column) {
        this.column = column;
        this.row = row;
    }

    public Vector(Vector source) {
        this(source.row, source.column);
    }

    public Vector add(Vector shift) {
        var vector = (Vector) shift;
        return new Vector(row + vector.row,
                          column + vector.column);
    }

    public Vector opposite() {
        return new Vector(-1 * row, -1 * column);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Vector))
            return false;
        Vector casted = (Vector) other;
        return this.column == casted.column
                && this.row == casted.row;
    }

    public int hashCode() {
        return 17 * this.column + 53 * this.row;
    }

    public String toString() {
        return String.format("(%d, %d)", row, column);
    }

    public String asMoveName() {
        if (row == 1 && column == 0)
            return "south";
        else if (row == -1 && column == 0)
            return "north";
        else if (row == 0 && column == 1)
            return "east";
        else if (row == 0 && column == -1)
            return "west";
        else
            return toString();
    }

}
