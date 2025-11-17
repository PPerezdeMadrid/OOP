# Design

## Model 
- Where are we looking in the complex plane (real/image min/max)?
- How many iterations are we using?
- What color scheme are we using?
- Undo/redo history?
- Use the MandelbrotCalculator to generate the data (int[][]).

## View (JPanel)
- Graphical components:
    + A main window (JFrame).
    + A panel that draws the fractal (JPanel).
    + A panel with controls (buttons, sliders, etc.).
- It is only responsible for displaying what the Model defines and capturing events, which are then passed to the Controller.

## Controller
- Handles user input (mouse clicks, button presses, slider changes).
- Updates the Model based on user actions.
- Tells the View to refresh when the Model changes.
- Manages the interaction between Model and View.

