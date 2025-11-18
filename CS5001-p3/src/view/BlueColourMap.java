package view;

import java.awt.Color;

public class BlueColourMap implements ColourMap {

    @Override
    public String getName() {
        return "Blue";
    }

    @Override
    public Color map(int iterations, int maxIterations) {
        if (iterations >= maxIterations) {
            return Color.BLACK;
        }
        float ratio = (maxIterations == 0) ? 0f : (float) iterations / maxIterations;
        float hue = 0.66f - (0.66f * ratio); // retain rainbow gradient but label as blue
        return Color.getHSBColor(hue, 0.9f, 1.0f);
    }
}
