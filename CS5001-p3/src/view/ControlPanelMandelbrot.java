package view;

import javax.swing.*;
import java.awt.*;
import model.ModelMandelbrot;
import view.UIStyler;

/**
 * Control panel containing iteration settings and reset functionality.
 */
public class ControlPanelMandelbrot extends JPanel {

    /** Spinner used to adjust the maximum iteration count. */
    private final JSpinner iterationSpinner;
    /** Resets the view and iteration count to defaults. */
    private final JButton resetButton;
    /** Reverts the last zoom or movement action. */
    private final JButton undoButton;
    /** Reapplies an action that was previously undone. */
    private final JButton redoButton;
    /** Saves the current Mandelbrot image to disk. */
    private final JButton saveButton;
    /** Loads a saved Mandelbrot image. */
    private final JButton loadButton;
    /** Drop-down list for choosing the active colour map. */
    private final JComboBox<String> colourMapCombo;

    /**
     * Builds the control panel with styled controls and initial iteration value.
     *
     * @param initialIterations starting iteration count for the spinner
     */
    public ControlPanelMandelbrot(int initialIterations) {
        UIStyler.stylePanel(this);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        iterationSpinner = new JSpinner(
                new SpinnerNumberModel(
                        initialIterations,
                        ModelMandelbrot.MIN_ITERATIONS,
                        ModelMandelbrot.MAX_ITERATIONS,
                        10
                )
        );

        resetButton = new JButton("Reset");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        colourMapCombo = new JComboBox<>();

        // Apply styles
        UIStyler.styleResetButton(resetButton);
        UIStyler.styleUndoButton(undoButton);
        UIStyler.styleRedoButton(redoButton);
        UIStyler.styleButton(saveButton);
        UIStyler.styleButton(loadButton);
        colourMapCombo.setMaximumSize(new Dimension(150, 25));

        add(new JLabel("Iterations:"));
        add(Box.createHorizontalStrut(10));
        add(iterationSpinner);
        add(Box.createHorizontalStrut(20));
        add(resetButton);
        add(Box.createHorizontalStrut(20));
        add(undoButton);
        add(Box.createHorizontalStrut(20));
        add(redoButton);
        add(Box.createHorizontalStrut(20));
        add(saveButton);
        add(Box.createHorizontalStrut(20));
        add(loadButton);
        add(Box.createHorizontalStrut(20));
        add(new JLabel("Colour map:"));
        add(Box.createHorizontalStrut(10));
        add(colourMapCombo);

    }

    /**
     * @return spinner controlling the iteration count
     */
    public JSpinner getIterationSpinner() {
        return iterationSpinner;
    }

    /**
     * @return reset button component
     */
    public JButton getResetButton() {
        return resetButton;
    }

    /**
     * @return undo button component
     */
    public JButton getUndoButton() {
        return undoButton;
    }

    /**
     * @return redo button component
     */
    public JButton getRedoButton() {
        return redoButton;
    }

    /**
     * @return save button component
     */
    public JButton getSaveButton() {
        return saveButton;
    }

    /**
     * @return load button component
     */
    public JButton getLoadButton() {
        return loadButton;
    }

    /**
     * @return colour map selection combo box
     */
    public JComboBox<String> getColourMapCombo() {
        return colourMapCombo;
    }
}
