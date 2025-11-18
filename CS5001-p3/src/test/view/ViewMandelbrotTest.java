package view;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for view helpers. 
 */
public class ViewMandelbrotTest {

    /**
     * Simple regression test for the blue colour map gradient.
     */
    @Test
    public void blueColourMapProducesGradient() {
        BlueColourMap map = new BlueColourMap();
        Color nearZero = map.map(0, 100);
        Color mid = map.map(50, 100);
        Color max = map.map(100, 100);

        assertEquals("Blue", map.getName());
        assertNotEquals(nearZero, mid); // Gradient should change across iterations
        assertEquals(Color.BLACK, max); // Points at max iterations should be black
    }
}
