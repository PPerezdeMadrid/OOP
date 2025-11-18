package view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for view helpers (control panel mechanics, colour-map utilities, etc.).
 */
public class ViewMandelbrotTest {

    @BeforeAll
    public static void enableHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    /**
     * Ensure the iteration spinner enforces the same bounds as the model constants.
     */
    @Test
    public void controlPanelSpinnerRespectsModelBounds() {
        ControlPanelMandelbrot panel = new ControlPanelMandelbrot(100);
        JSpinner spinner = panel.getIterationSpinner();
        SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();

        // Force value below minimum via the model to mimic user input
        model.setValue(ModelMandelbrot.MIN_ITERATIONS - 5);
        spinner.commitEdit();
        spinner.getModel().setValue(model.getValue()); // trigger change event
        panel.getIterationSpinner().fireStateChanged();
        assertEquals(ModelMandelbrot.MIN_ITERATIONS, spinner.getValue());

        // Force value above maximum
        model.setValue(ModelMandelbrot.MAX_ITERATIONS + 123);
        spinner.commitEdit();
        spinner.getModel().setValue(model.getValue());
        panel.getIterationSpinner().fireStateChanged();
        assertEquals(ModelMandelbrot.MAX_ITERATIONS, spinner.getValue());
    }
}
