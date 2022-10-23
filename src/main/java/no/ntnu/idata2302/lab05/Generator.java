/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */

package no.ntnu.idata2302.lab05;


public interface Generator {


     Maze generate(Factory factory, Dimension dimension);


    // private final Random random;

    // public Generator(Random randomizer) {
    //     this.random = randomizer;
    // }

    // public void generate(int height, int width) {
    //     var maze = GridMaze.full(height, width);
    //     var builder = maze.at(1, 1);
    //     var visited = new HashSet();
    //     while (visited.size() < height *  width) {
    //         visited.add(builder.position());
    //         var move = pickOneFrom(builder.moves());
    //         var nextPosition = move.apply(builder.position);
    //         if (!visited.contains(nextPosition)) {
    //             builder.dig(direction);

    //         } else {
    //             builder.repositionAt(nextPosition);

    //         }
    //     }
    // }

    // private Move pickOneFrom(Set<Move> candidates) {
    //     int selectedIndex = new Random().nextInt(candidates.size());
    //     int currentIndex = 0;
    //     for(Move any: candidates) {
    //         if (currentIndex == selectedIndex)
    //             return any;
    //         currentIndex++;
    //     }
    // }


}
