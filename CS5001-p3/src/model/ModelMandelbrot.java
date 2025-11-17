package model;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;


public class ModelMandelbrot {

    private double minReal;
    private double maxReal;
    private double minImag;
    private double maxImag;
    private int maxIterations;
    private double radiusSquared;

    private final int width;
    private final int height;

    // Data calculated: for each pixel [y][x], number of iterations
    private int[][] data;

    private MandelbrotCalculator calculator;

    // List of listeners (views or others) that want to be notified
    private List<ModelListener> listeners = new ArrayList<>();

    // Undo/redo stacks
    private final Deque<MandelbrotState> undoStack = new ArrayDeque<>();
    private final Deque<MandelbrotState> redoStack = new ArrayDeque<>();


    /**
     * Constructor.
     * @param width  of the image in pixels
     * @param height of the image in pixels
     */
    
    public ModelMandelbrot(int width, int height) {
        this.width = width;
        this.height = height;

        this.minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImag = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImag = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;

        this.calculator = new MandelbrotCalculator();

        recalculate();
    }

    /**
     * Recalculates the Mandelbrot set with the current parameters.
     * Calls the calculator and then notifies the listeners.
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

    // Getters

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
        pushStateToUndo();

        this.maxIterations = maxIterations;
        recalculate();
    }

    /**
     * Ajusta el rango del plano complejo (lo usaremos más adelante para el zoom).
     */
    public void setViewWindow(double minReal, double maxReal,
                              double minImag, double maxImag) {
        
        pushStateToUndo();

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

    /**
     * Moves the current view window by the specified delta values.
     * Positive deltaReal moves the view to the right.
     * Positive deltaImag moves the view downward (complex plane).
     */
    public void pan(double deltaReal, double deltaImag) {
        pushStateToUndo();

        this.minReal += deltaReal;
        this.maxReal += deltaReal;
        this.minImag += deltaImag;
        this.maxImag += deltaImag;

        recalculate();
    }

    /**
     * Saves the current state into the undo stack.
     * Called before any state-changing operation.
     */
    private void pushStateToUndo() {
        undoStack.push(new MandelbrotState(
                minReal, maxReal,
                minImag, maxImag,
                maxIterations
        ));
        // Any new action clears redo history
        redoStack.clear();
    }

     public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void undo() {
        if (!canUndo()) return;

        // Save current state into redo stack
        redoStack.push(new MandelbrotState(
                minReal, maxReal,
                minImag, maxImag,
                maxIterations
        ));

        // Restore previous
        MandelbrotState prev = undoStack.pop();
        minReal = prev.minReal;
        maxReal = prev.maxReal;
        minImag = prev.minImag;
        maxImag = prev.maxImag;
        maxIterations = prev.maxIterations;

        recalculate();
    }

    public void redo() {
        if (!canRedo()) return;

        // Save current state into undo stack
        pushStateToUndoWithoutClearingRedo();

        // Restore next
        MandelbrotState next = redoStack.pop();
        minReal = next.minReal;
        maxReal = next.maxReal;
        minImag = next.minImag;
        maxImag = next.maxImag;
        maxIterations = next.maxIterations;

        recalculate();
    }

    /**
     * Internal helper used only for redo.
     * Does NOT clear redo stack.
     */
    private void pushStateToUndoWithoutClearingRedo() {
        undoStack.push(new MandelbrotState(
                minReal, maxReal,
                minImag, maxImag,
                maxIterations
        ));
    }



    // --- Observer pattern methods ---

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
