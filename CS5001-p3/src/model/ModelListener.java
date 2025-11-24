package model;

/**
 * Listener for updates to the Mandelbrot model.
 */
public interface ModelListener {
    /**
     * Called when the model changes and views should refresh.
     */
    void modelChanged();
}
