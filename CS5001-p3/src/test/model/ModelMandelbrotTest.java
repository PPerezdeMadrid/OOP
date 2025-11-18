package model;

import model.ModelMandelbrot;
import model.MandelbrotCalculator;
import model.ModelListener;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Unit tests that exercise {@link ModelMandelbrot}.
 */
public class ModelMandelbrotTest {

    private ModelMandelbrot model;
    private RecordingListener listener;

    private static final double EPSILON = 2e-14; // Tolerance for floating-point comparisons, 0.00000000000002

    // Listener para pruebas
    private static class RecordingListener implements ModelListener {
        int count = 0;
        @Override
        public void modelChanged() {
            count++;
        }
    }

    /*
    * Tests for reset().
    */
   @Test
    public void testResetRestoresInitialValues() {
        ModelMandelbrot model = new ModelMandelbrot(800, 600);

        // Change from initial values
        model.setViewWindow(-1.0, 0.5, -0.5, 0.5);
        model.setMaxIterations(ModelMandelbrot.MIN_ITERATIONS + 50);
        model.setColorMapName("Blue");

        model.reset();

        // Check that everything returns to initial values
        assertEquals(MandelbrotCalculator.INITIAL_MIN_REAL,
                model.getMinReal(), 1e-15);

        assertEquals(MandelbrotCalculator.INITIAL_MAX_REAL,
                model.getMaxReal(), 1e-15);

        assertEquals(MandelbrotCalculator.INITIAL_MIN_IMAGINARY,
                model.getMinImag(), 1e-15);

        assertEquals(MandelbrotCalculator.INITIAL_MAX_IMAGINARY,
                model.getMaxImag(), 1e-15);

        assertEquals(MandelbrotCalculator.INITIAL_MAX_ITERATIONS,
                model.getMaxIterations());

        // Color map should return to default
        assertEquals("Black & White", model.getColorMapName());

        // It should have recalculated the fractal
        assertNotNull(model.getData());
    }

    /*
    * Tests for undo/redo functionality.
    */
    @Test
    public void testUndoRedoStacks() {
        ModelMandelbrot model = new ModelMandelbrot(200, 200);

        double initialMinReal = model.getMinReal();
        double initialMaxReal = model.getMaxReal();
        double initialMinImag = model.getMinImag();
        double initialMaxImag = model.getMaxImag();
        int initialIterations = model.getMaxIterations();

        // Change view window
        double customMinReal = -1.5;
        double customMaxReal = -0.5;
        double customMinImag = -0.5;
        double customMaxImag = 0.3;
        model.setViewWindow(customMinReal, customMaxReal, customMinImag, customMaxImag);

        // Change max iterations
        int boostedIterations = initialIterations + 25;
        model.setMaxIterations(boostedIterations);

        // Pan the view
        double deltaReal = 0.2;
        double deltaImag = -0.1;
        
        model.pan(deltaReal, deltaImag);

        assertTrue(model.canUndo()); // canUndo() return a boolean
        assertFalse(model.canRedo()); // canRedo() return a boolean

        model.undo(); // undo pan
        assertEquals(customMinReal, model.getMinReal(), EPSILON); 
        assertEquals(customMaxReal, model.getMaxReal(), EPSILON);
        assertEquals(customMinImag, model.getMinImag(), EPSILON);
        assertEquals(customMaxImag, model.getMaxImag(), EPSILON);
        assertEquals(boostedIterations, model.getMaxIterations());
        assertTrue(model.canRedo());

        model.undo(); // undo iteration change
        assertEquals(customMinReal, model.getMinReal(), EPSILON);
        assertEquals(initialIterations, model.getMaxIterations());

        model.undo(); // undo view change
        assertEquals(initialMinReal, model.getMinReal(), EPSILON);
        assertEquals(initialMaxReal, model.getMaxReal(), EPSILON);
        assertEquals(initialMinImag, model.getMinImag(), EPSILON);
        assertEquals(initialMaxImag, model.getMaxImag(), EPSILON);
        assertFalse(model.canUndo()); 
        assertTrue(model.canRedo());

        model.redo(); // redo view change
        assertEquals(customMinReal, model.getMinReal(), EPSILON);
        assertEquals(customMaxReal, model.getMaxReal(), EPSILON);
        assertEquals(customMinImag, model.getMinImag(), EPSILON);
        assertEquals(customMaxImag, model.getMaxImag(), EPSILON);
        assertEquals(initialIterations, model.getMaxIterations());

        model.redo(); // redo iteration change
        assertEquals(boostedIterations, model.getMaxIterations());

        model.redo(); // redo pan
        assertEquals(customMinReal + deltaReal, model.getMinReal(), EPSILON);
        assertEquals(customMaxReal + deltaReal, model.getMaxReal(), EPSILON);
        assertEquals(customMinImag + deltaImag, model.getMinImag(), EPSILON);
        assertEquals(customMaxImag + deltaImag, model.getMaxImag(), EPSILON);
        assertEquals(boostedIterations, model.getMaxIterations());
        assertFalse(model.canRedo());

        model.undo(); // back to state before pan
        assertTrue(model.canRedo());

        int secondIterations = boostedIterations + 25;
        model.setMaxIterations(secondIterations); // new action should clear redo history
        assertEquals(secondIterations, model.getMaxIterations());
        assertFalse(model.canRedo());
    }

    /*
    * Tests for saveToFile() and loadFromFile().
    */
    @Test
    public void testSaveAndLoadRoundTrip() throws Exception {
        ModelMandelbrot original = new ModelMandelbrot(64, 48);
        original.setViewWindow(-1.4, -0.6, -0.2, 0.4);
        original.setMaxIterations(ModelMandelbrot.MIN_ITERATIONS + 40);
        original.setColorMapName("Rainbow");

        Path tempFile = Files.createTempFile("mandelbrot-round-trip", ".props");
        try {
            File file = tempFile.toFile();
            original.saveToFile(file);

            ModelMandelbrot reloaded = new ModelMandelbrot(64, 48);
            reloaded.loadFromFile(file);

            assertEquals(original.getMinReal(), reloaded.getMinReal(), EPSILON);
            assertEquals(original.getMaxReal(), reloaded.getMaxReal(), EPSILON);
            assertEquals(original.getMinImag(), reloaded.getMinImag(), EPSILON);
            assertEquals(original.getMaxImag(), reloaded.getMaxImag(), EPSILON);
            assertEquals(original.getMaxIterations(), reloaded.getMaxIterations());
            assertEquals(original.getColorMapName(), reloaded.getColorMapName());
            assertNotNull(reloaded.getData());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /*
    * Tests for loadFromFile() with malformed data.
    */
    @Test
    public void testLoadFromFileRejectsMalformedData() throws Exception {
        ModelMandelbrot model = new ModelMandelbrot(32, 32);
        Path tempFile = Files.createTempFile("mandelbrot-bad", ".props");
        try {
            String malformed =
                    "minReal=-1.0\n" +
                    "maxReal=-0.5\n" +
                    "minImag=-0.3\n" +
                    "maxImag=0.1\n" +
                    "maxIterations=notANumber\n";
            Files.write(tempFile, malformed.getBytes(StandardCharsets.UTF_8));

            // Expect IOException due to malformed maxIterations
            assertThrows(IOException.class, () -> model.loadFromFile(tempFile.toFile())); 
        } finally {
            Files.deleteIfExists(tempFile); // Clean up temporary file
        }
    }

    /*
    * Tests for passing null resets it to the default map.
    */
    @Test
    public void testSetColorMapNameNullResetsToDefault() {
        ModelMandelbrot model = new ModelMandelbrot(100, 100);

        model.setColorMapName("Blue");
        assertEquals("Blue", model.getColorMapName());

        // Passing null should revert to default map
        model.setColorMapName(null);
        assertEquals("Black & White", model.getColorMapName());
    }


    /*
    * Tests for listener notifications.
    */
    @Test
    public void testListenersAreNotifiedOnStateChanges() {
        ModelMandelbrot model = new ModelMandelbrot(64, 64);
        RecordingListener listener = new RecordingListener();
        model.addListener(listener);

        assertEquals(0, listener.count); // count = 0

        model.setMaxIterations(ModelMandelbrot.MIN_ITERATIONS + 20);
        assertEquals(1, listener.count); // count + 1 = 1

        model.pan(0.1, -0.05);
        assertEquals(2, listener.count); // count + 1 = 2

        model.setColorMapName("Rainbow");
        assertEquals(3, listener.count); // count + 1 = 3

        model.reset();
        assertEquals(4, listener.count); // count + 1 = 4
    }
}
