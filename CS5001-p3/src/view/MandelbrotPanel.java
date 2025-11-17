import javax.swing.*;
import java.awt.*;

public class MandelbrotPanel extends JPanel implements ModelListener {

    private final ModelMandelbrot model;

    public MandelbrotPanel(ModelMandelbrot model) {
        this.model = model;
        // Nos registramos como listener del modelo
        this.model.addListener(this);

        // Pedimos el tamaño preferido según el modelo
        setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
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

        // Versión simple en blanco y negro:
        // - Negro si está "dentro" (iter >= maxIter)
        // - Blanco si "escapa"
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
    }

    @Override
    public void modelChanged() {
        // Cuando el modelo cambia, repintamos
        repaint();
    }
}
