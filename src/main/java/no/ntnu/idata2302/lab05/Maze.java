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


/**
 * Represent a 2D maze, internally with an array of character.
 */
public class Maze {

    /**
     * Reads a maze from the given input stream
     */
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

    // Set of order of which moves are "explored"
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

    /**
     * Create a new maze, internally, the maze is a 2D array of
     * characters.
     *
     * @param mazeMap the array of string representing the maze as character
     */
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

    /**
     * @return the dimension of the maze, that, its number of rows and
     * columns.
     */
    public Dimension dimension() {
        return new Dimension(fromMapIndex(mazeMap.length - 1),
                             fromMapIndex(mazeMap[0].length() - 1));
    }

    /**
     * @throws  true is this maze has an exit position
     */
    public boolean hasExit() {
        return this._exit != null;
    }

    /**
     * @return the position of the exit associated with this maze, or
     * null if none exists.
     */
    public Vector exit() {
        return this._exit;
    }

    /**
     * @return true if the given position is the exit of this maze
     * @param position the position to consider
     */
    public boolean isExit(Vector position) {
        return position.equals(exit());
    }

    /**
     * @return the entry position of this maze or null if none has
     * been defined.
     */
    public Vector entry() {
        return this._entry;
    }

    /**
     * @return all possible moves at the given position. Not that all
     * possible moves are the moves that land within the dimension of
     * this maze, regardless of walls.
     *
     * @param start the position where the list of possible moves
     * should be computed.
     *
     * @see validMovesAt
     */
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

    /**
     * @return all the *validMoves* at the given position. Valid moves
     * are all moves that lands within the dimension of this maze, and
     * that do not traverse a wall.
     *
     * @param position the position where the list of valid moves
     * should be computed.
     *
     * @see movesAt
     */
    public Set<Vector> validMovesAt(Vector position) {
        var validMoves = new LinkedHashSet<Vector>();
        for (var anyMove : movesAt(position)) {
            if (isValidAt(position, anyMove)) {
                validMoves.add(anyMove);
            }
        }
        return validMoves;
    }

    /**
     * @return true if the given is valid from the given position.
     *
     * @param position the 2D vector from where the move should be consider
     * @param move the 2D vector indicating the moving direction.
     */
    public boolean isValidAt(Vector position, Vector move) {
        var landing = position.add(move);
        var result = dimension().includes(position)
                && dimension().includes(landing)
                && isClear(position, move);
        return result;
    }

    /**
     * @return true is there is a wall at the given direction (east,
     * north, sourth, west) for the given cell, false otherwise.
     *
     * @param position the position to consider as a 2D vector
     * @param direction the direction to check
     */
    public boolean isWall(Vector position, Vector direction) {
        return !isClear(position, direction);
    }

    /**
     * @return true if there is *no wall* in the given direction at the
     * selected position, false otherwise
     *
     * @param position the position to consider as a 2D vector
     * @param direction the direction to consider
     */
    public boolean isClear(Vector position, Vector direction) {
        var marker = markerBetween(position, position.add(direction));
        return Character.isWhitespace(marker)
                || marker == START_MARKER
                || marker == EXIT_MARKER;
    }

    /**
     * Add a wall around the selected position.
     *
     * @param start the position to consider
     * @param direction the side of the cell where the wall should be
     * built
     */
    public void buildWall(Vector start, Vector direction) {
        var marker = '|';
        if (direction.equals(NORTH) || direction.equals(SOUTH)) {
            marker = '-';
        }
        setMarkerBetween(start, start.add(direction), marker);
    }

    /**
     * Take down the wall at the given position, in the given
     * direction. Idempotent.
     *
     * @param position the position to consider
     * @param direction the side of the cell whose wall should be
     * taken down.
     */
    public void breakWall(Vector position, Vector direction) {
        setMarkerBetween((Vector) position,
                (Vector) position.add(direction),
                ' ');
    }

    /**
     * Print the given maze on the console.
     */
    public void show() {
        for (String eachRow : mazeMap) {
            System.out.println(eachRow);
        }
    }

    /**
     * Print this maze using a specific printer object
     *
     * @param printer the selected printer object
     */
    public void print(Printer printer) {
        print(printer, new LinkedList<Vector>());
    }

    /**
     * Print this maze using selected printer object, together with the given
     * solution.
     *
     * @param printer the printer to user
     * @param solution the solution to print
     */
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
