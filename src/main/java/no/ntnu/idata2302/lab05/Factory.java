/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;

import java.util.ArrayList;
import java.util.List;

public class Factory {

    public Maze fullyDisconnected(Dimension dimension) {
        var mazeMap = new ArrayList<String>(2 * dimension.getRowCount() + 1);
        mazeMap.add(rowOf(dimension, "+", "+", "-"));
        for (int r = 1; r <= dimension.getRowCount(); r++) {
            mazeMap.add(rowOf(dimension, "|", "|", " "));
            mazeMap.add(rowOf(dimension, "+", "+", "-"));
        }
        setMarker(mazeMap, 'S', 1, 1);
        setMarker(mazeMap, 'E', dimension.getRowCount(), dimension.getColumnCount());
        return new Maze(mazeMap.toArray(new String[mazeMap.size()]));
    }

    private void setMarker(List<String> mazeMap, char marker, int row, int column) {
        StringBuilder builder = new StringBuilder(mazeMap.get(2 * row - 1));
        builder.setCharAt(2 * column - 1, marker);
        mazeMap.set(2 * row - 1, builder.toString());
    }

    private String rowOf(Dimension dimension, String edge, String joint, String cell) {
        var builder = new StringBuilder();
        builder.append(edge);
        for (int i = 1; i <= dimension.getColumnCount(); i++) {
            builder.append(cell);
            if (i < dimension.getColumnCount()) {
                builder.append(joint);
            } else {
                builder.append(edge);
            }
        }
        return builder.toString();
    }

    public Maze fullyConnected(Dimension dimension) {
        var mazeMap = new ArrayList<String>(2 * dimension.getRowCount() + 1);
        mazeMap.add(rowOf(dimension, "+", "+", "-"));
        for (int r = 1; r <= dimension.getRowCount(); r++) {
            mazeMap.add(rowOf(dimension, "|", " ", " "));
            if (r < dimension.getRowCount()) {
                mazeMap.add(rowOf(dimension, "+", "+", " "));

            } else {
                mazeMap.add(rowOf(dimension, "+", "+", "-"));

            }
        }
        setMarker(mazeMap, 'E', 1, 1);
        setMarker(mazeMap, 'S', dimension.getRowCount(), dimension.getColumnCount());
        return new Maze(mazeMap.toArray(new String[mazeMap.size()]));
    }

}
