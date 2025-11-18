package view;

import javax.swing.*;
import java.awt.*;
import model.ModelMandelbrot;
import view.UIStyler;

/**
 * Control panel containing iteration settings and reset functionality.
 */
public class ControlPanelMandelbrot extends JPanel {

    private final JSpinner iterationSpinner;
    private final JButton resetButton;
    private final JButton undoButton;
    private final JButton redoButton;
    private final JButton saveButton;
    private final JButton loadButton;
    private final JComboBox<String> colourMapCombo;

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



    public JSpinner getIterationSpinner() {
        return iterationSpinner;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JButton getRedoButton() {
        return redoButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JComboBox<String> getColourMapCombo() {
        return colourMapCombo;
    }
}
