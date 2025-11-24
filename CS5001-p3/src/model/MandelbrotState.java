package model;

/**
 * Immutable snapshot of the Mandelbrot model state.
 * Used for undo/redo functionality.
 */
public class MandelbrotState {

    /** Minimum real coordinate of view window. */
    public final double minReal;
    /** Maximum real coordinate of view window. */
    public final double maxReal;
    /** Minimum imaginary coordinate of view window. */
    public final double minImag;
    /** Maximum imaginary coordinate of view window. */
    public final double maxImag;
    /** Maximum iterations for Mandelbrot calculation. */
    public final int maxIterations;

    /**
     * Creates a state snapshot of the current view window and iteration cap.
     *
     * @param minReal minimum real coordinate
     * @param maxReal maximum real coordinate
     * @param minImag minimum imaginary coordinate
     * @param maxImag maximum imaginary coordinate
     * @param maxIterations maximum iterations allowed
     */
    public MandelbrotState(double minReal, double maxReal,
                           double minImag, double maxImag,
                           int maxIterations) {

        this.minReal = minReal;
        this.maxReal = maxReal;
        this.minImag = minImag;
        this.maxImag = maxImag;
        this.maxIterations = maxIterations;
    }
}
