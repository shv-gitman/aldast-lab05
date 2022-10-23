/*
 * This file is part of NTNU's IDATA2302 Lab05.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */
package no.ntnu.idata2302.lab05.cli;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "mazes", description = "manipulate simple 2D mazes on the CLI.", subcommands = {
        Generate.class,
        Solve.class,
        Export.class
})
public class Runner implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("Nice!");
        return 0;
    }

    public static void main(String[] arguments) throws Exception {
        Runner runner = new Runner();
        new CommandLine(runner).execute(arguments);
    }
}
