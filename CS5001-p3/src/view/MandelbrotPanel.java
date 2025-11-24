package view;

import javax.swing.*;
import java.awt.*;
import model.ModelListener;
import model.ModelMandelbrot;


/**
 * Panel responsible for rendering the Mandelbrot set and selection overlays.
 */
public class MandelbrotPanel extends JPanel implements ModelListener {

    /** Backing model supplying Mandelbrot pixel data. */
    private final ModelMandelbrot model;
    /** Initial point of a drag gesture used for zoom selection. */
    private Point dragStart;
    /** Current endpoint of the drag gesture for zoom selection. */
    private Point dragEnd;
    /** Colour mapping strategy applied when rendering the set. */
    private ColourMap colourMap = new BlackWhiteColourMap();

    /**
     * Creates a panel bound to the given model and registers as a listener.
     *
     * @param model source of pixel data to render
     */
    public MandelbrotPanel(ModelMandelbrot model) {
        this.model = model;
        this.model.addListener(this);

        setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
    }

    /**
     * Records the start of a drag gesture for zoom selection.
     *
     * @param p starting point in panel coordinates
     */
    public void setDragStart(Point p) {
        this.dragStart = p; 
    }

    /**
     * Records the current drag end position for zoom selection.
     *
     * @param p current drag end in panel coordinates
     */
    public void setDragEnd(Point p) { 
        this.dragEnd = p;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] data = model.getData();
        if (data == null) {
            return;
        }

        int height = data.length;
        int width = data[0].length;
        int maxIter = model.getMaxIterations();


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int iter = data[y][x];
                g.setColor(colourMap.map(iter, maxIter));
                g.fillRect(x, y, 1, 1);
            }
        }

        // Draw zoom rectangle if the user is dragging
        if (dragStart != null && dragEnd != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);
            int x = Math.min(dragStart.x, dragEnd.x);
            int y = Math.min(dragStart.y, dragEnd.y);
            int w = Math.abs(dragStart.x - dragEnd.x);
            int h = Math.abs(dragStart.y - dragEnd.y);
            g2d.drawRect(x, y, w, h);
        }

    }

    @Override
    public void modelChanged() {
        repaint();
    }

    /**
     * Sets the current colour map used for rendering.
     *
     * @param colourMap map strategy to apply
     */
    public void setColourMap(ColourMap colourMap) {
        this.colourMap = colourMap;
        repaint();
    }

    /**
     * @return the current colour map in use
     */
    public ColourMap getColourMap() {
        return colourMap;
    }
}
