import javax.swing.*;

public class MandelbrotApp {

    public static void main(String[] args) {
        // Aseguramos que todo el código de Swing se ejecute en el hilo de eventos
        SwingUtilities.invokeLater(() -> {
            // Creamos el modelo (800x800 píxeles)
            ModelMandelbrot model = new ModelMandelbrot(800, 800);

            // Creamos el panel que dibuja el fractal
            MandelbrotPanel panel = new MandelbrotPanel(model);

            // Creamos la ventana
            JFrame frame = new JFrame("Mandelbrot - Versión básica");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel);
            frame.pack();                    // Ajusta el tamaño a lo que pide el panel
            frame.setLocationRelativeTo(null); // Centra la ventana
            frame.setVisible(true);
        });
    }
}
