package controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import view.ControlPanelMandelbrot;
import view.MandelbrotPanel;
import model.ModelMandelbrot;

/**
 * Controller unit tests for {@link ControllerMandelbrot}.
 */
public class ControllerMandelbrotTest {

    /**
     * Simple fake model recording controller interactions.
     */
    private static class FakeModel extends ModelMandelbrot {
        int storedIterations;
        int iterationsCalls;
        int panCalls;
        double lastPanReal;
        double lastPanImag;
        int colourMapCalls;
        String colourMap = super.getColorMapName();

        FakeModel() {
            super(120, 80);
            storedIterations = super.getMaxIterations();
        }

        @Override
        public void setMaxIterations(int maxIterations) {
            iterationsCalls++;
            storedIterations = maxIterations;
        }

        @Override
        public int getMaxIterations() {
            return storedIterations;
        }

        @Override
        public void pan(double deltaReal, double deltaImag) {
            panCalls++;
            lastPanReal = deltaReal;
            lastPanImag = deltaImag;
        }

        @Override
        public void setColorMapName(String name) {
            colourMapCalls++;
            colourMap = name;
        }

        @Override
        public String getColorMapName() {
            return colourMap;
        }

        void resetCallCounts() {
            iterationsCalls = 0;
            panCalls = 0;
            colourMapCalls = 0;
        }
    }

    /*
    * Model delegation: Verify that UI actions call the correct model methods with the expected parameters.
    * UI Actions tested: spinner changes, combo selection, SHIFT-drag for panning
    */
    @Test
    public void modelDelegationFromUiActions() {
        double EPSILON = 1e-9; 
        FakeModel model = new FakeModel();
        ControlPanelMandelbrot controls = new ControlPanelMandelbrot(model.getMaxIterations());
        MandelbrotPanel panel = new MandelbrotPanel(model);

        ControllerMandelbrot controller = new ControllerMandelbrot(
                model,
                panel,
                controls.getIterationSpinner(),
                controls.getResetButton(),
                controls.getUndoButton(),
                controls.getRedoButton(),
                controls.getSaveButton(),
                controls.getLoadButton(),
                controls.getColourMapCombo()
        );

        model.resetCallCounts();

        controls.getIterationSpinner().setValue(200);
        assertEquals(1, model.iterationsCalls); // Spinner change should hit the model once.
        assertEquals(200, model.getMaxIterations()); // Model reflects the new iteration count.

        // Simulate a SHIFT drag to exercise panning logic.
        Point start = new Point(20, 20);
        Point end = new Point(50, 45);
        controller.mousePressed(new MouseEvent(
                panel, MouseEvent.MOUSE_PRESSED, 0, InputEvent.SHIFT_DOWN_MASK,
                start.x, start.y, 1, false));
        controller.mouseDragged(new MouseEvent(
                panel, MouseEvent.MOUSE_DRAGGED, 0, InputEvent.SHIFT_DOWN_MASK,
                end.x, end.y, 0, false));
        controller.mouseReleased(new MouseEvent(
                panel, MouseEvent.MOUSE_RELEASED, 0, InputEvent.SHIFT_DOWN_MASK,
                end.x, end.y, 0, false));

        // Expect a single pan call and accurate deltas.
        assertEquals(1, model.panCalls);
        double expectedRealPerPixel = (model.getMaxReal() - model.getMinReal()) / model.getWidth();
        double expectedImagPerPixel = (model.getMaxImag() - model.getMinImag()) / model.getHeight();
        assertEquals(-(end.x - start.x) * expectedRealPerPixel, model.lastPanReal, EPSILON);
        assertEquals(-(end.y - start.y) * expectedImagPerPixel, model.lastPanImag, EPSILON);

        // Switching the combo should inform the model once.
        controls.getColourMapCombo().setSelectedItem("Blue");
        assertEquals(1, model.colourMapCalls);
        assertEquals("Blue", model.getColorMapName());
    }
}
