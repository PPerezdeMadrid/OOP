import java.util.ArrayList;
import java.util.List;

package model;

public class ModelMandelbrot {

    // Parámetros del plano complejo actual
    private double minReal;
    private double maxReal;
    private double minImag;
    private double maxImag;
    private int maxIterations;
    private double radiusSquared;

    // Tamaño en píxeles de la imagen
    private final int width;
    private final int height;

    // Datos calculados: para cada píxel [y][x], nº de iteraciones
    private int[][] data;

    // Calculadora que te han dado
    private MandelbrotCalculator calculator;

    // Lista de vistas (u otros) que quieren ser notificadas
    private List<ModelListener> listeners = new ArrayList<>();

    /**
     * Constructor básico.
     * @param width  ancho de la imagen en píxeles
     * @param height alto de la imagen en píxeles
     */
    public ModelMandelbrot(int width, int height) {
        this.width = width;
        this.height = height;

        // Usamos los valores iniciales que da MandelbrotCalculator
        this.minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImag = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImag = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;

        this.calculator = new MandelbrotCalculator();

        // Calculamos por primera vez
        recalculate();
    }

    /**
     * Recalcula el conjunto de Mandelbrot con los parámetros actuales.
     * Llama a la calculadora y luego avisa a los listeners.
     */
    public void recalculate() {
        data = calculator.calcMandelbrotSet(
                width,
                height,
                minReal,
                maxReal,
                minImag,
                maxImag,
                maxIterations,
                radiusSquared
        );
        notifyListeners();
    }

    // --- Getters básicos que usará la vista ---

    public int[][] getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getMinReal() {
        return minReal;
    }

    public double getMaxReal() {
        return maxReal;
    }

    public double getMinImag() {
        return minImag;
    }

    public double getMaxImag() {
        return maxImag;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getRadiusSquared() {
        return radiusSquared;
    }

    /**
     * Magnificación aproximada respecto al rango inicial.
     * (Opcional, pero útil para mostrarlo en pantalla.)
     */
    public double getMagnification() {
        double initialWidth = MandelbrotCalculator.INITIAL_MAX_REAL - MandelbrotCalculator.INITIAL_MIN_REAL;
        double currentWidth = maxReal - minReal;
        return initialWidth / currentWidth;
    }

    // --- Métodos de modificación simples (básico) ---

    /**
     * Cambia el número máximo de iteraciones y recalcula.
     */
    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
        recalculate();
    }

    /**
     * Ajusta el rango del plano complejo (lo usaremos más adelante para el zoom).
     */
    public void setViewWindow(double minReal, double maxReal,
                              double minImag, double maxImag) {
        this.minReal = minReal;
        this.maxReal = maxReal;
        this.minImag = minImag;
        this.maxImag = maxImag;
        recalculate();
    }

    /**
     * Resetea la vista a los valores iniciales.
     */
    public void reset() {
        this.minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImag = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImag = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        recalculate();
    }

    // --- Sistema de listeners ---

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ModelListener l : listeners) {
            l.modelChanged();
        }
    }
}
