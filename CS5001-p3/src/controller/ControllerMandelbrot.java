package controller;

import model.ModelMandelbrot;
import view.MandelbrotPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.*;



/**
 * Controller responsible for handling user interactions such as mouse dragging for zooming.
 */
public class ControllerMandelbrot implements MouseListener, MouseMotionListener, ActionListener, ChangeListener{

    private final ModelMandelbrot model;
    private final MandelbrotPanel panel;

    private final JSpinner iterationSpinner;
    private final JButton resetButton; 
    private final JButton undoButton;
    private final JButton redoButton;


    // Mouse drag positions
    private Point dragStart;
    private Point dragEnd;

    private boolean isPanning = false; 


    public ControllerMandelbrot(ModelMandelbrot model, MandelbrotPanel panel, JSpinner iterationSpinner, JButton resetButton,JButton undoButton, JButton redoButton) {
        this.model = model;
        this.panel = panel;
        this.iterationSpinner = iterationSpinner;
        this.resetButton = resetButton;
        this.undoButton = undoButton;
        this.redoButton = redoButton;


        // Register to receive mouse events from the panel
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        // Listeners for control panel
        iterationSpinner.addChangeListener(this);
        resetButton.addActionListener(this);

        undoButton.addActionListener(this);
        redoButton.addActionListener(this);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint();
        dragEnd = dragStart;

        // Detect pan mode:
        // 1. SHIFT + left mouse button
        // 2. OR right mouse button
        if (e.isShiftDown() || SwingUtilities.isRightMouseButton(e)) {
            isPanning = true;
        } else {
            isPanning = false;
            panel.setDragStart(dragStart);
            panel.setDragEnd(dragEnd);
        }

        panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragEnd = e.getPoint();

        if (isPanning) {
            // Live redraw optional — normally no rectangle for pan
            panel.repaint();
        } else {
            // Zoom rectangle feedback
            panel.setDragEnd(dragEnd);
            panel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragEnd = e.getPoint();

        if (dragStart == null) return;

        if (isPanning) {
            applyPan(dragStart, dragEnd);
        } else {
            if (!dragStart.equals(dragEnd)) {
                Rectangle r =  createRectangle(dragStart, dragEnd);
                applyZoom(r);
            }
        }

        // Clear UI
        panel.setDragStart(null);
        panel.setDragEnd(null);
        isPanning = false;
        panel.repaint();
    }

    /** Build a rectangle regardless of drag direction (drag can be left to right or right to left). */
    private Rectangle createRectangle(Point p1, Point p2) {
        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int width = Math.abs(p1.x - p2.x);
        int height = Math.abs(p1.y - p2.y);
        return new Rectangle(x, y, width, height);
    }

    

    /**
     * Converts a rectangle in pixel coordinates into complex-plane coordinates
     * and updates the model's viewport.
     */
    private void applyZoom(Rectangle r) {
        int width = model.getWidth();
        int height = model.getHeight();

        double minReal = model.getMinReal();
        double maxReal = model.getMaxReal();
        double minImag = model.getMinImag();
        double maxImag = model.getMaxImag();

        double realPerPixel = (maxReal - minReal) / width;
        double imagPerPixel = (maxImag - minImag) / height;

        // New complex bounds
        double newMinReal = minReal + r.x * realPerPixel;
        double newMaxReal = minReal + (r.x + r.width) * realPerPixel;

        double newMinImag = minImag + r.y * imagPerPixel;
        double newMaxImag = minImag + (r.y + r.height) * imagPerPixel;

        // Apply new viewing window
        model.setViewWindow(newMinReal, newMaxReal, newMinImag, newMaxImag);
    }

    /**
     * Applies panning: converts pixel drag distance into complex-plane delta values.
     */
    private void applyPan(Point start, Point end) {
        int dx = end.x - start.x;
        int dy = end.y - start.y;

        int width = model.getWidth();
        int height = model.getHeight();

        double realPerPixel = (model.getMaxReal() - model.getMinReal()) / width;
        double imagPerPixel = (model.getMaxImag() - model.getMinImag()) / height;

        // Movement in opposite direction: dragging right means view moves left in the complex plane
        double deltaReal = -dx * realPerPixel;
        double deltaImag = -dy * imagPerPixel;

        model.pan(deltaReal, deltaImag);
    }

    @Override
    public void stateChanged(javax.swing.event.ChangeEvent e) {
        if (e.getSource() == iterationSpinner) {
            int value = (Integer) iterationSpinner.getValue();
            model.setMaxIterations(value);  // real-time updates
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == resetButton) {
            model.reset();  // resets coords & iterations
            iterationSpinner.setValue(model.getMaxIterations());
        }
        if (e.getSource() == undoButton) {
            model.undo();
            iterationSpinner.setValue(model.getMaxIterations());
        }

        if (e.getSource() == redoButton) {
            model.redo();
            iterationSpinner.setValue(model.getMaxIterations());
        }

            }

    // Unused interface methods (required to compile)
    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
