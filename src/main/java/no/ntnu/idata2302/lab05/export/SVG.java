/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */
package no.ntnu.idata2302.lab05.export;



import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;



public class SVG implements Printer {

    private static final int MARGIN = 1;

    private StringBuilder builder;
    private Stroke stroke;


    public SVG(StringBuilder builder) {
        this.builder = builder;
        this.stroke = new Stroke("black", 2, "");
    }


    @Override
    public void open(int width, int height) {
        String svgCode =
            String.format("<?xml version=\"1.0\" standalone=\"no\"?>" +
                          "<svg width=\"%dcm\" height=\"%dcm\" " +
                          " version=\"1.1\" fill=\"transparent\" " +
                          " xmlns=\"http://www.w3.org/2000/svg\">" +
                          " <desc>Randomized maze</desc>",
                          width + 2 * MARGIN,
                          height + 2 * MARGIN);
        builder.append(svgCode);
    }

    @Override
    public void usePen(String color, int width, String style) {
        var pattern = style;
        if (style.equals("dashed")) {
            pattern = "5,5";
        }
        stroke = new Stroke(color, width, pattern);
    }


    @Override
    public void box(int x, int y, int width, int height) {
        String svgCode =
            String.format("<rect" +
                          " x=\"%dcm\" y=\"%dcm\" " +
                          " height=\"%dcm\" width=\"%dcm\" " +
                          " fill=\"transparent\" " +
                          " stroke=\"black\" " +
                          " stroke-width=\"6\" " +
                          "/>", MARGIN+x, MARGIN+y, height, width);
        builder.append(svgCode);
    }

    @Override
    public void line(double sx, double sy, double tx, double ty) {
        String svgCode =
            String.format("<line" +
                          " x1=\"%fcm\" y1=\"%fcm\" " +
                          " x2=\"%fcm\" y2=\"%fcm\" " +
                          " stroke=\"%s\" " +
                          " stroke-width=\"%d\" " +
                          " stroke-dasharray=\"%s \"" +
                          "/>", MARGIN+sx, MARGIN+sy, MARGIN+tx, MARGIN+ty, stroke.color,
                          stroke.width, stroke.dashArray);
        builder.append(svgCode);
    }

    @Override
    public void text(double x, double y, String text) {
        String svgCode =
            String.format("<text" +
                          " x=\"%fcm\" y=\"%fcm\" " +
                          " fill=\"black\" " +
                          " text-anchor=\"middle\" " +
                          " alignment-baseline=\"middle\"" +
                          ">%s</text>", MARGIN+x, MARGIN+y, text);
        builder.append(svgCode);
    }

    @Override
    public void close() {
        String svgCode =
            String.format("</svg>");
        builder.append(svgCode);
    }


    @Override
    public void saveAs(OutputStream output) throws IOException {
        new PrintStream(output).println(builder.toString());
    }

}


class Stroke {
    public final String color;
    public final int width;
    public final String dashArray;

    public Stroke(String color, int width, String dash) {
        this.color = color;
        this.width = width;
        this.dashArray = dash;
    }

}
