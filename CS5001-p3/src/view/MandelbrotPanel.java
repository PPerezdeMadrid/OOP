package view;

import javax.swing.*;
import java.awt.*;
import model.ModelListener;
import model.ModelMandelbrot;


public class MandelbrotPanel extends JPanel implements ModelListener {

    private final ModelMandelbrot model;
    private Point dragStart;
    private Point dragEnd;

    public MandelbrotPanel(ModelMandelbrot model) {
        this.model = model;
        this.model.addListener(this);

        setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
    }

    public void setDragStart(Point p) {
        this.dragStart = p; 
    }

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

                if (iter >= maxIter) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }

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
}
