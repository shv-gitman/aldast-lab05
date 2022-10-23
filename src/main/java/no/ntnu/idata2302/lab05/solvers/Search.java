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

public class Search {

    private final LinkedList<Vector> pendingCells;
    private final Map<Vector, Record> knownCells;

    public Search() {
        pendingCells = new LinkedList<Vector>();
        knownCells = new LinkedHashMap<Vector, Record>();
    }

    boolean hasPendingCells() {
        return !pendingCells.isEmpty();
    }

    Vector firstPending() {
        if (pendingCells.isEmpty())
            throw new IllegalStateException("Empty schedule");
        return pendingCells.removeFirst();
    }

    Vector lastPending() {
        if (pendingCells.isEmpty())
            throw new IllegalStateException("Empty schedule");
        return pendingCells.removeLast();
    }

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

    private void showPending() {
        for (var each : pendingCells) {
            System.out.println("    " + each + "(d=" + distanceTo(each) + ")");
        }
    }

    int distanceTo(Vector position) {
        return knownCells.get(position).distance;
    }

    void markAsPending(Vector position) {
        knownCells.put(position, new Record(position, null, 0));
        pendingCells.add(position);
    }

    void record(Vector start, Vector move, Vector end) {
        // TODO: Implement this method
        throw new RuntimeException("Not yet implemented.");
    }

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

    void showSearch() {
        for (var each : knownCells.entrySet()) {
            System.out.println(each.getValue());
        }
    }

    public void showVisitedCells() {
        System.out.println(knownCells.keySet());
    }

}

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
        return String.format("%s (from %s) %d (%s)",
                position,
                move,
                distance);
    }

}
