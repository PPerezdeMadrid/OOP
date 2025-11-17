package model;

/**
 * Immutable snapshot of the Mandelbrot model state.
 * Used for undo/redo functionality.
 */
public class MandelbrotState {

    public final double minReal;
    public final double maxReal;
    public final double minImag;
    public final double maxImag;
    public final int maxIterations;

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
