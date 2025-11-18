package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Properties;


public class ModelMandelbrot {

    private static final String DEFAULT_COLOR_MAP = "Black & White";
    public static final int MIN_ITERATIONS = 10;
    public static final int MAX_ITERATIONS = 5000;

    private double minReal;
    private double maxReal;
    private double minImag;
    private double maxImag;
    private int maxIterations;
    private double radiusSquared;

    private final int width;
    private final int height;
    private String colorMapName = DEFAULT_COLOR_MAP;

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

    public String getColorMapName() {
        return colorMapName;
    }

    /**
     * Approximate magnification relative to the first range.
     * Handy if the UI wants to display the zoom level.
     */
    public double getMagnification() {
        double initialWidth = MandelbrotCalculator.INITIAL_MAX_REAL - MandelbrotCalculator.INITIAL_MIN_REAL;
        double currentWidth = maxReal - minReal;
        return initialWidth / currentWidth;
    }

    // --- Basic setters that change the current state ---

    /**
     * Updates the max number of iterations and triggers a recalculation.
     */
    public void setMaxIterations(int maxIterations) {
        validateIterations(maxIterations);
        pushStateToUndo();

        this.maxIterations = maxIterations;
        recalculate();
    }

    /**
     * Sets the complex plane rectangle used for rendering.
     */
    public void setViewWindow(double minReal, double maxReal,
                              double minImag, double maxImag) {
        validateViewWindow(minReal, maxReal, minImag, maxImag);

        pushStateToUndo();

        this.minReal = minReal;
        this.maxReal = maxReal;
        this.minImag = minImag;
        this.maxImag = maxImag;
        recalculate();
    }

    /**
     * Restores the initial coordinates and settings.
     */
    public void reset() {
        this.minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImag = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImag = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.colorMapName = DEFAULT_COLOR_MAP;
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

    /**
     * Saves the current model parameters to a file.
     */
    public void saveToFile(File file) throws IOException {
        Properties props = new Properties();
        props.setProperty("minReal", Double.toString(minReal));
        props.setProperty("maxReal", Double.toString(maxReal));
        props.setProperty("minImag", Double.toString(minImag));
        props.setProperty("maxImag", Double.toString(maxImag));
        props.setProperty("maxIterations", Integer.toString(maxIterations));
        props.setProperty("colorMap", colorMapName);

        try (FileOutputStream out = new FileOutputStream(file)) {
            props.store(out, "Mandelbrot settings");
        }
    }

    /**
     * Loads model parameters from a file and recalculates the set.
     */
    public void loadFromFile(File file) throws IOException {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
        }

        double newMinReal = readDouble(props, "minReal");
        double newMaxReal = readDouble(props, "maxReal");
        double newMinImag = readDouble(props, "minImag");
        double newMaxImag = readDouble(props, "maxImag");
        int newMaxIterations = readInt(props, "maxIterations");
        String newColorMapName = props.getProperty("colorMap", DEFAULT_COLOR_MAP);

        try {
            validateViewWindow(newMinReal, newMaxReal, newMinImag, newMaxImag);
            validateIterations(newMaxIterations);
        } catch (IllegalArgumentException ex) {
            throw new IOException("Invalid settings file: " + ex.getMessage(), ex);
        }

        pushStateToUndo();

        this.minReal = newMinReal;
        this.maxReal = newMaxReal;
        this.minImag = newMinImag;
        this.maxImag = newMaxImag;
        this.maxIterations = newMaxIterations;
        this.colorMapName = normalizeColourMapName(newColorMapName);

        recalculate();
    }

    public void setColorMapName(String colorMapName) {
        this.colorMapName = normalizeColourMapName(colorMapName);
        notifyListeners();
    }

    private String normalizeColourMapName(String colorMapName) {
        return (colorMapName == null || colorMapName.trim().isEmpty())
                ? DEFAULT_COLOR_MAP
                : colorMapName;
    }

    private double readDouble(Properties props, String key) throws IOException {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IOException("Missing property: " + key);
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid double for " + key, e);
        }
    }

    private int readInt(Properties props, String key) throws IOException {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IOException("Missing property: " + key);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid integer for " + key, e);
        }
    }

    private void validateIterations(int iterations) {
        if (iterations < MIN_ITERATIONS || iterations > MAX_ITERATIONS) {
            throw new IllegalArgumentException(
                    "maxIterations must be between " + MIN_ITERATIONS + " and " + MAX_ITERATIONS);
        }
    }

    private void validateViewWindow(double minReal, double maxReal,
                                    double minImag, double maxImag) {
        if (minReal >= maxReal) {
            throw new IllegalArgumentException("minReal must be less than maxReal");
        }
        if (minImag >= maxImag) {
            throw new IllegalArgumentException("minImag must be less than maxImag");
        }
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
