package view;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * View unit tests for {@link BlueColourMap}.
 */
public class ViewMandelbrotTest {

    /**
     * Ensures the blue colour map produces a gradient and labels itself correctly.
     */
    @Test
    public void blueColourMapProducesGradient() {
        BlueColourMap map = new BlueColourMap();
        Color nearZero = map.map(0, 100);
        Color mid = map.map(50, 100);
        Color max = map.map(100, 100);

        assertEquals("Blue", map.getName());
        assertNotEquals(nearZero, mid); // Gradient should change across iterations
        assertNotEquals(Color.BLACK, nearZero); // Near-zero iterations should still show colour
        assertEquals(Color.BLACK, max); // Points at max iterations should be black
    }

    /**
     * Verifies the colour map handles zero max iterations safely and returns black.
     */
    @Test
    public void blueColourMapHandlesZeroMaxIterations() {
        BlueColourMap map = new BlueColourMap();
        // Guard against division by zero and confirm in-set colouring
        assertEquals(Color.BLACK, map.map(0, 0));
        assertEquals(Color.BLACK, map.map(10, 0));
    }
}
