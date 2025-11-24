package view;

import java.awt.Color;

/**
 * Strategy interface for converting iteration counts into colors.
 */
public interface ColourMap {

    /**
     * Name to show in the UI.
     *
     * @return display name of the map
     */
    String getName();

    /**
     * @param iterations iterations needed for the point to escape
     * @param maxIterations current maximum iterations configured
     * @return color representing the point
     */
    Color map(int iterations, int maxIterations);
}
