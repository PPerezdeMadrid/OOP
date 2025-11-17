package view;

import javax.swing.*;
import java.awt.*;
import view.UIStyler;

/**
 * Control panel containing iteration settings and reset functionality.
 */
public class ControlPanelMandelbrot extends JPanel {

    private final JSpinner iterationSpinner;
    private final JButton resetButton;
    private final JButton undoButton;
    private final JButton redoButton;

    public ControlPanelMandelbrot(int initialIterations) {
        UIStyler.stylePanel(this);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        iterationSpinner = new JSpinner(
                new SpinnerNumberModel(initialIterations, 10, 5000, 10)
        );

        resetButton = new JButton("Reset");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");

        // Apply styles
        UIStyler.styleResetButton(resetButton);
        UIStyler.styleUndoButton(undoButton);
        UIStyler.styleRedoButton(redoButton);

        add(new JLabel("Iterations:"));
        add(iterationSpinner);
        add(resetButton);
        add(undoButton);
        add(redoButton);

        add(Box.createHorizontalStrut(30)); // large gap
        add(resetButton);

        add(Box.createHorizontalStrut(30)); // large gap
        add(undoButton);

        add(Box.createHorizontalStrut(30)); // large gap
        add(redoButton);

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
}
