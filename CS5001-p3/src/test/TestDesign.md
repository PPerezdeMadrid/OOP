# Test Documentation

This document explains the tests that were written for the project and why each of them matters. 

## 1. Model Tests (`src/model/ModelMandelbrot.java`)

### 1.1. Reset Behavior

`reset()` should bring the model back to its initial configuration.
The test first changes several parameters and then calls `reset()`, checking that:

* All real/imaginary limits return to the initial constants.
* `maxIterations` resets correctly.
* The color map returns to `"Black & White"`.

### 1.2. Undo and Redo

Undo/redo support is tested through a simple flow:

1. Call `setViewWindow()`.
2. Run `undo()` and confirm the previous bounds are restored.
3. Run `redo()` and confirm the newer bounds return.
4. Verify that `canUndo()` and `canRedo()` switch appropriately as the stacks change.

This helps validate that the internal history mechanism behaves as expected.

### 1.3. Persistence (`saveToFile()` and `loadFromFile()`)

These tests behave more like integration tests. The process is:

1. Create a temporary file.
2. Save a model after modifying several fields.
3. Create a fresh model.
4. Load the file into the fresh model.
5. Compare all relevant getters between both models.

There are also tests that force malformed data to check that `readDouble()` and `readInt()` correctly throw `IOException`.

### 1.4. Color Map Name

This is a small but useful test. It checks that:

* Setting a valid color map name updates the internal value.
* Passing `null` resets it to the default map.

### 1.5. Listener Notifications

A fake `ModelListener` that increments a counter is registered.
The test ensures that methods such as `setMaxIterations()` or `setColorMapName()` trigger `modelChanged()`.
This guarantees that UI components depending on the model receive updates reliably.


## 2. Test Structure by Layer

The tests are organized by the same architecture used in the project.

### `src/test/model/ModelMandelbrotTest`

Contains all model-related cases: constructor, setters, undo/redo, persistence, and listeners.

### `src/test/controller/ControllerMandelbrotTest`

Here is a clean, concise **Markdown** version in English, listing the controller tests exactly as you'd want them in `testdesign.md`:


### `src/test/controller/ControllerMandelbrotTest`

The tests rely on lightweight **fake model and fake view** classes that simply record method calls rather than performing real computations.

* Model delegation:
  Verify that UI actions (`changeIterations`, `pan`, `setColorMapName`, etc.) call the correct model methods with the expected parameters.


### `src/test/view/*`

Covers parts of the view that contain logic (for example, color maps or panel utilities), not graphical rendering.


## 3. Validation Improvements Found Through Testing

While writing tests, several missing validations became apparent:

* The iteration `JSpinner` should use a `SpinnerNumberModel` with a minimum of 1 and a reasonable maximum. If text input is invalid, the controller should catch the exception and restore the last valid value.
* `ModelMandelbrot.setViewWindow()` should reject invalid intervals where `minReal >= maxReal` or `minImag >= maxImag`.
* `setMaxIterations()` should disallow values below 1 or excessively large numbers.
* `pan()` and `applyZoom()` in the controller should ignore drag rectangles with zero width or height.
* After `loadFromFile()`, the controller could do basic sanity checks before updating the UI.
* `setColorMapName()` should validate that the provided name exists; otherwise revert to default and optionally log a warning.
