package view;

import java.awt.Color;

/**
 * Maps points to a simple black-and-white palette.
 */
public class BlackWhiteColourMap implements ColourMap {

    @Override
    public String getName() {
        return "Black & White";
    }

    @Override
    public Color map(int iterations, int maxIterations) {
        return (iterations >= maxIterations) ? Color.BLACK : Color.WHITE;
    }
}
