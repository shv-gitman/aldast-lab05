/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */
package no.ntnu.idata2302.lab05.export;



import java.io.OutputStream;
import java.io.IOException;



public interface Printer {

    void open(int width, int height);

    void usePen(String color, int width, String style);

    void box(int x, int y, int width, int height);

    void line(double sx, double sy, double tx, double ty);

    void text(double x, double y, String text);

    void close();

    void saveAs(OutputStream output) throws IOException;

}
