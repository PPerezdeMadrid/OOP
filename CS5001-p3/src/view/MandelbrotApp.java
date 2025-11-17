package view;

import javax.swing.*;
import model.ModelMandelbrot;
import controller.ControllerMandelbrot;
import java.awt.BorderLayout;


public class MandelbrotApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ModelMandelbrot model = new ModelMandelbrot(800, 800);
            MandelbrotPanel mandelbrotPanel = new MandelbrotPanel(model);

            // Create control panel
            ControlPanelMandelbrot controlPanel = 
                    new ControlPanelMandelbrot(model.getMaxIterations());


            // Create controller with panel + controls
            new ControllerMandelbrot(
                    model,
                    mandelbrotPanel,
                    controlPanel.getIterationSpinner(),
                    controlPanel.getResetButton(),
                    controlPanel.getUndoButton(),
                    controlPanel.getRedoButton()
                );

            JFrame frame = new JFrame("Mandelbrot Explorer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setLayout(new BorderLayout());
            frame.add(mandelbrotPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
