/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;

import no.ntnu.idata2302.lab05.export.Printer;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Maze {

    public static Maze fromStream(InputStream input) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(input));
        var lines = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            if (!line.equals(""))
                lines.add(line);
            line = reader.readLine();
        }
        return new Maze(lines.toArray(new String[0]));
    }

    public static final Vector NORTH = new Vector(-1, 0);
    public static final Vector EAST = new Vector(0, 1);
    public static final Vector SOUTH = new Vector(1, 0);
    public static final Vector WEST = new Vector(0, -1);

    public static final Vector[] MOVES = new Vector[] {
            NORTH,
            EAST,
            SOUTH,
            WEST
    };

    private static final char START_MARKER = 'S';
    private static final char EXIT_MARKER = 'E';

    private final String[] mazeMap;
    private final Vector _entry;
    private final Vector _exit;

    public Maze(String[] mazeMap) {
        this.mazeMap = mazeMap;
        this._entry = findMarker(START_MARKER);
        var tmp = new Vector(1, 1);
        try {
            tmp = findMarker(EXIT_MARKER);

        } catch (Exception error) {
            tmp = null;

        }
        this._exit = tmp;
    }

    private Vector findMarker(char marker) {
        for (int rowIndex = 0; rowIndex < mazeMap.length; rowIndex++) {
            var row = mazeMap[rowIndex];
            for (int columnIndex = 0; columnIndex < row.length(); columnIndex++) {
                if (row.charAt(columnIndex) == marker) {
                    return new Vector(fromMapIndex(rowIndex),
                            fromMapIndex(columnIndex));
                }
            }
        }
        throw new RuntimeException("Invalid map: Could not find marker '" + marker + "'");
    }

    public Dimension dimension() {
        return new Dimension(fromMapIndex(mazeMap.length - 1),
                             fromMapIndex(mazeMap[0].length() - 1));
    }

    public boolean hasExit() {
        return this._exit != null;
    }

    public Vector exit() {
        return this._exit;
    }

    public boolean isExit(Vector position) {
        return position.equals(exit());
    }

    public Vector entry() {
        return this._entry;
    }

    public Set<Vector> movesAt(Vector start) {
        var moves = new LinkedHashSet<Vector>();
        for (var anyMove : MOVES) {
            var end = start.add(anyMove);
            if (dimension().includes(end)) {
                moves.add(anyMove);
            }
        }
        return moves;
    }

    public Set<Vector> validMovesAt(Vector position) {
        var validMoves = new LinkedHashSet<Vector>();
        for (var anyMove : movesAt(position)) {
            if (isValidAt(position, anyMove)) {
                validMoves.add(anyMove);
            }
        }
        return validMoves;
    }

    public boolean isValidAt(Vector position, Vector move) {
        var landing = position.add(move);
        var result = dimension().includes(position)
                && dimension().includes(landing)
                && isClear(position, move);
        return result;
    }

    public boolean isWall(Vector position, Vector direction) {
        return !isClear(position, direction);
    }

    public boolean isClear(Vector position, Vector direction) {
        var marker = markerBetween(position, position.add(direction));
        return Character.isWhitespace(marker)
                || marker == START_MARKER
                || marker == EXIT_MARKER;
    }

    public void buildWall(Vector start, Vector direction) {
        var marker = '|';
        if (direction.equals(NORTH) || direction.equals(SOUTH)) {
            marker = '-';
        }
        setMarkerBetween(start, start.add(direction), marker);
    }

    public void breakWall(Vector position, Vector direction) {
        setMarkerBetween((Vector) position,
                (Vector) position.add(direction),
                ' ');
    }

    public void show() {
        for (String eachRow : mazeMap) {
            System.out.println(eachRow);
        }
    }

    public void print(Printer printer) {
        print(printer, new LinkedList<Vector>());
    }

    public void print(Printer printer, List<Vector> solution) {
        var dimension = dimension();
        printer.open(dimension.getColumnCount(), dimension.getRowCount());
        printer.box(0, 0, dimension.getColumnCount(), dimension.getRowCount());
        for (int row = 1; row <= dimension.getRowCount(); row++) {
            printer.text(-0.5, row - 0.5, "" + row);
        }

        for (int column = 1; column <= dimension.getColumnCount(); column++) {
            printer.text(column - 0.5, -0.5, "" + column);
        }

        for (int row = 1; row <= dimension.getRowCount(); row++) {
            for (int column = 1; column <= dimension.getColumnCount(); column++) {
                var position = new Vector(row, column);
                if (column < dimension.getColumnCount()) {
                    if (isWall(position, EAST)) {
                        printer.line(column, row - 1, column, row);
                    } else {
                        printer.usePen("#DCDCDC", 2, "2,2,2");
                        printer.line(column, row - 1, column, row);
                        printer.usePen("black", 2, "");
                    }
                }
                if (row < dimension.getRowCount()) {
                    if (isWall(position, SOUTH)) {
                        printer.line(column - 1, row, column, row);
                    } else {
                        printer.usePen("#DCDCDC", 2, "2,2,2");
                        printer.line(column - 1, row, column, row);
                        printer.usePen("black", 2, "");
                    }
                }
            }
        }
        if (solution != null)
            printSolution(printer, solution);
        printer.close();
    }

    private void printSolution(Printer printer, List<Vector> solution) {
        var entry = (Vector) entry();

        printer.usePen("green", 2, "dashed");
        var currentPosition = entry;
        for (var eachMove : solution) {
            var nextPosition = (Vector) currentPosition.add(eachMove);
            printer.line(currentPosition.column - 1 + 0.5,
                    currentPosition.row - 1 + 0.5,
                    nextPosition.column - 1 + 0.5,
                    nextPosition.row - 1 + 0.5);
            currentPosition = nextPosition;
        }

        printer.text(entry().column - 1 + 0.5,
                     entry().row - 1 + 0.5,
                "S");
        if (hasExit()) {
            printer.text(exit().column - 1 + .5,
                         exit().row - 1 + .5,
                    "E");
        }

    }

    // --- Helpers -------------------------------------------------------------

    private int fromMapIndex(int index) {
        return (index + 1) / 2;
    }

    private char markerAt(int mapRow, int mapColumn) {
        return mazeMap[mapRow].charAt(mapColumn);
    }

    private void setMarkerAt(int row, int column, char marker) {
        StringBuilder builder = new StringBuilder(mazeMap[row]);
        builder.setCharAt(column, marker);
        mazeMap[row] = builder.toString();
    }

    private int toMapIndex(int index) {
        return 2 * index - 1;
    }

    private char markerBetween(Vector start, Vector end) {
        return markerAt(
                interpolateMapIndex(start.row, end.row),
                interpolateMapIndex(start.column, end.column));
    }

    private int interpolateMapIndex(int left, int right) {
        return (toMapIndex(left) + toMapIndex(right)) / 2;
    }

    private void setMarkerBetween(Vector start, Vector end, char marker) {
        setMarkerAt(interpolateMapIndex(start.row, end.row),
                interpolateMapIndex(start.column, end.column),
                marker);
    }

}
