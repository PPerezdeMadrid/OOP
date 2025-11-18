# Mandelbrot Set Viewer - Feature  Checklist
### Mouse Interaction

* **[ DONE ]** Left-click + SHIFT + drag → **Pan** (move the view window)
* **[ DONE ]** Right-click + drag → **Pan** (alternative pan mode)
* **[ DONE ]** Left-click + drag → **Draw rectangle**
* **[ DONE ]** Release after drawing rectangle → **Zoom into selected area**
* **[ DONE ]** Zoom rectangle is visually shown on screen

### Model Functionality

* **[ DONE ]** View window updates correctly after zoom
* **[ DONE ]** View window updates correctly after pan
* **[ DONE ]** Mandelbrot recalculation on every view change
* **[ DONE ]** Panel redraws automatically via ModelListener

### Rendering / Visuals

* **[ DONE ]** Mandelbrot set renders in black & white
* **[ DONE ]** Pixel-by-pixel drawing based on iteration values
* **[ DONE ]** Zoom rectangle drawn on top of fractal

### MVC Architecture

* **[ DONE ]** Clear separation into:

  * **Model**: holds state and handles pan/zoom logic
  * **View**: renders fractal and zoom-box
  * **Controller**: handles mouse input and delegates to model
* **[ DONE ]** Observer pattern (`ModelListener`) used for repainting

* **[ DONE ]**  Iteration control

  * Slider or spinner to change `maxIterations`
  * Updates model in real time
* **[ DONE ]**  Reset button

  * Resets view to original coordinates
  * Resets iterations to default
* **[ DONE ]**  UI layout (panel at bottom or side)


# CATEGORY 2 – ENHANCEMENTS

## Colour Maps

* **[ DONE ]**  Define interface `ColourMap`
* **[ DONE ]**  Implement at least 2 color schemes:
  * Black & White
  * Blue 
* **[ DONE ]**  Add UI to switch between color maps

## 2. Full Undo / Redo

* **[ DONE ]**  Two stacks in the model:
  * undoStack
  * redoStack
* **[ DONE ]**  Save a snapshot before each:
  * zoom
  * pan
  * iteration change
  * color map change
* **[ DONE ]**  Buttons:
  * Undo
  * Redo

## 3. Save / Load Settings

* **[ DONE ]**  Write current view parameters to a file:
  * minReal, maxReal
  * minImag, maxImag
  * maxIterations
  * selected color map
* **[ DONE ]**  Load them back
* **[ DONE ]** File chooser dialog

# CATEGORY 3 – SOFTWARE QUALITY

### Good documentation

* [ TODO ] Javadoc for main classes
* [ TODO ] Enough comments in Model / Controller

### Complete Report PDF

* [ TODO ] Class diagrams
* [ TODO ] Explanation of MVC
* [ TODO ] Description of enhancements
* [ TODO ] Screenshots
* [ TODO ] Discussion of limitations



