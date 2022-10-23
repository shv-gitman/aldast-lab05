/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Dimension {

    private final int rowCount;
    private final int columnCount;

    public Dimension(int rowCount, int columnCount) {
        if (rowCount < 1) {
            throw new IllegalArgumentException("Row count must be positive.");
        }
        if (columnCount < 1) {
            throw new IllegalArgumentException("Column count must be positive.");
        }
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public List<Vector> allPositions() {
        var results = new ArrayList<Vector>(positionCount());
        for (var row = 1; row <= rowCount; row++) {
            for (var column = 1; column <= columnCount; column++) {
                results.add(new Vector(row, column));
            }
        }
        return results;
    }

    public int positionCount() {
        return rowCount * columnCount;
    }

    public boolean includes(Vector position) {
        var gridPosition = (Vector) position;
        return gridPosition.row > 0
                && gridPosition.row <= rowCount
                && gridPosition.column > 0
                && gridPosition.column <= columnCount;
    }

    public Vector randomPosition(Random random) {
        return new Vector(
                1 + random.nextInt(this.rowCount + 1),
                1 + random.nextInt(this.columnCount + 1));
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    @Override
    public String toString() {
        return String.format("%d x %d", rowCount, columnCount);
    }

}
