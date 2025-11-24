# Testing Details

This project uses JUnit 5. The standalone runner is already bundled in `JUnit/lib-alone/junit-platform-console-standalone-1.13.4.jar` and is invoked by the `make test` target. Below is a brief description of each test class and what it covers.

## Model Tests — `src/test/model/ModelMandelbrotTest.java`
- **testResetRestoresInitialValues**: Verifies that after changing view bounds, iteration count, and colour map, calling `reset()` restores all initial constants and recalculates data.
- **testUndoRedoStacks**: Exercises `setViewWindow`, `setMaxIterations`, and `pan`, then checks undo/redo stack behavior, including clearing redo after new actions.
- **testSaveAndLoadRoundTrip**: Saves model settings to a temp file and reloads into a new model; asserts all parameters and data are preserved.
- **testLoadFromFileRejectsMalformedData**: Writes malformed properties (invalid integer) and confirms `loadFromFile` throws `IOException` for bad inputs.
- **testSetColorMapNameNullResetsToDefault**: Ensures passing `null` to `setColorMapName` falls back to the default palette.
- **testListenersAreNotifiedOnStateChanges**: Attaches a recording listener and ensures notifications fire for iteration changes, panning, colour map change, and reset.

## Controller Tests — `src/test/controller/ControllerMandelbrotTest.java`
- **modelDelegationFromUiActions**: Uses a `FakeModel` to assert that UI events delegate correctly: spinner changes call `setMaxIterations`, Shift-drag triggers `pan` with correct deltas, and colour map selection calls `setColorMapName` exactly once.

## View Tests — `src/test/view/ViewMandelbrotTest.java`
- **blueColourMapProducesGradient**: Confirms `BlueColourMap` returns varying colours across iterations, names itself "Blue", and maps max iterations to black.
- **blueColourMapHandlesZeroMaxIterations**: Ensures `map` returns black and avoids division issues when `maxIterations` is zero.

## How to Run
- `make test` — compiles sources and tests, then runs them using the bundled JUnit jar.
- If you want to run a single class manually: `java -jar JUnit/lib-alone/junit-platform-console-standalone-1.13.4.jar -cp bin --select-class model.ModelMandelbrotTest` (ensure `bin` contains compiled classes via `make compile_tests`).

## Notes
- Temp files created during tests are cleaned up after each test run.
- The controller test relies on simulated mouse events; no GUI interaction is required.
