/*
 * This file is part of NTNU's IDATA2302 Lab 05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05.solvers;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;

import no.ntnu.idata2302.lab05.Vector;

/**
 * Represent a data needed when traversing a maze, namely a list of
 * pending cells (found but not processed) and a set of known cells
 * (processed).
 */
public class Search {

    private final LinkedList<Vector> pendingCells;
    private final Map<Vector, Record> knownCells;

    public Search() {
        pendingCells = new LinkedList<Vector>();
        knownCells = new LinkedHashMap<Vector, Record>();
    }

    /**
     * @return true if there more "pending" cells that needs to be
     *         processed, null otherwise,
     */
    boolean hasPendingCells() {
        return !pendingCells.isEmpty();
    }

    /**
     * @return the first cell in the list of pending cells
     * @throws IllegalStateException when the list of pending cells is empty
     */
    Vector firstPending() {
        if (pendingCells.isEmpty())
            throw new IllegalStateException("Empty schedule");
        return pendingCells.removeFirst();
    }

    /**
     * @return the last cell in the list of pending cells
     * @throws IllegalStateException when the list of pending cells is empty
     */
    Vector lastPending() {
        if (pendingCells.isEmpty())
            throw new IllegalStateException("Empty schedule");
        return pendingCells.removeLast();
    }

    /**
     * @return the closest pending cells found
     * @throws IllegalStateException is the list of pending cells is
     *         empty
     */
    Vector closestPending() {
        if (pendingCells.isEmpty())
            throw new IllegalStateException("Empty schedule");
        if (pendingCells.size() == 1)
            return pendingCells.removeFirst();
        var closest = pendingCells.get(0);
        int minimumDistance = distanceTo(closest);
        var iterator = pendingCells.listIterator(1);
        while (iterator.hasNext()) {
            var position = iterator.next();
            if (minimumDistance < distanceTo(position)) {
                closest = position;
                minimumDistance = distanceTo(position);
            }
        }
        pendingCells.remove(closest);
        return closest;
    }

    /**
     * @returns the distance to the given cell, according to the
     *          information collected so far during this search.
     */
    private int distanceTo(Vector position) {
        return knownCells.get(position).distance;
    }

    /**
     * Mark the given cell/position as pending by adding it to the
     * list of pending cells.
     *
     * @param position the cell to mark as pending
     */
    void markAsPending(Vector position) {
        knownCells.put(position, new Record(position, null, 0));
        pendingCells.add(position);
    }

    /**
     * Record a new triplet source cell/move/target cell into the
     * search.
     *
     * @param start the cell where we were before to move (as a 2D vector)
     * @param move  the move we take (as a 2D vector)
     * @param end   the cell we ended at, also as a 2D Vector
     *
     * TODO: This method can be used for all the DFS, BFS
     * and Dijkstra's algorithm. The point is to look up the given
     * move, see if we can find the newly found cell 'end' among the
     * known cells. If the end cell is new, we should add it,
     * otherwise, we may need to update the distance if we have found
     * a shorter path.
     */
    void record(Vector start, Vector move, Vector end) {
        if (knownCells.containsKey(end)) {
            knownCells.put(start, new Record(start, move.opposite(), distanceTo(end)));
        } else {
            markAsPending(end);
            knownCells.put(end, new Record(end, move, 0));
        }
    }

    /**
     * Extract the list of moves that led to the given
     * position. Useful once we found the exit of the maze return the
     * actual "solution".
     *
     * @param position the move to which we need the list of moves
     *
     * @return the list of moves (2D vector) that leads to the given
     * position
     *
     * @throws IllegalArgumentException when the given position is null.
     *
     * @throws IllegalStateException if the given is the among the
     * know cells
     */
    List<Vector> movesTo(Vector position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        if (!knownCells.containsKey(position)) {
            throw new IllegalStateException("The position " + position + " has not yet been met.");
        }
        var solution = new LinkedList<Vector>();
        var current = position;
        var move = knownCells.get(current).move;
        while (move != null) {
            solution.addFirst(move);
            current = current.add(move.opposite());
            move = knownCells.get(current).move;
        }
        return solution;
    }

    // --- Private helpers for debug -------------------------------------------

    void showSearch() {
        for (var each : knownCells.entrySet()) {
            System.out.println(each.getValue());
        }
    }

    public void showVisitedCells() {
        System.out.println(knownCells.keySet());
    }

}

/**
 * Track the "known" cells. Records contain the starting
 * position, the move applied, and the best known distance so far. The
 * end cell can re-computed by applying the "move" to the starting
 * cell.
 */
class Record {

    public Vector position;
    public Vector move;
    public int distance;

    Record(Vector position, Vector move, int distance) {
        this.position = position;
        this.move = move;
        this.distance = distance;
    }

    public String toString() {
        if (move != null) {
            return String.format("%s (from %s) (%s)",
                position,
                move.asMoveName(),
                distance);
        } else {
            return String.format("%s (from %s) (%s)",
                position,
                move,
                distance);
        }
    }

}
