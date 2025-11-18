package view;

import java.awt.Color;

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
