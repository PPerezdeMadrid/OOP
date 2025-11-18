package view;

import java.awt.Color;

/**
 * Strategy interface for converting iteration counts into colors.
 */
public interface ColourMap {

    /**
     * Human-friendly name to show in the UI.
     */
    String getName();

    /**
     * @param iterations iterations needed for the point to escape
     * @param maxIterations current maximum iterations configured
     * @return color representing the point
     */
    Color map(int iterations, int maxIterations);
}
