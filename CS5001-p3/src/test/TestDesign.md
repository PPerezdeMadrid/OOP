El constructor y getters (src/model/ModelMandelbrot.java (lines 48-133)) admiten un test sencillo: instanciar ModelMandelbrot, verificar que getWidth()/getHeight() coinciden con los argumentos, que los límites reales/imaginarios y maxIterations se inicializan con los valores INITIAL_*, que getColorMapName() arranca en "Black & White" y que getData() tiene tamaño [height][width].

Métodos que cambian el estado (setMaxIterations, setViewWindow, pan en src/model/ModelMandelbrot.java (lines 139-188)) pueden comprobarse garantizando que actualizan los campos y que, tras llamarles, la pila de undo contiene un elemento (canUndo() verdadero). Se puede validar que getMagnification() cambia en consonancia al modificar el ancho del intervalo real.

reset() (src/model/ModelMandelbrot.java (lines 161-172)) merece un test que primero altere varios parámetros, invoque reset() y verifique que todo vuelve a los valores iniciales y que colorMapName también se restaura.

El bloque de undo/redo (src/model/ModelMandelbrot.java (lines 194-260)) se presta a tests de flujo: ejecutar setViewWindow(), llamar a undo() y comprobar que los límites regresan; repetir con redo() para confirmar que el estado reaparece y que canUndo()/canRedo() cambian adecuadamente cuando se consumen las pilas.

Persistencia (saveToFile y loadFromFile en src/model/ModelMandelbrot.java (lines 265-304)) permite un test de integración usando un File temporal: guardar tras modificar parámetros, crear un segundo modelo, cargar el archivo y comparar todos los getters. También conviene forzar propiedades inválidas para verificar que readDouble/readInt (src/model/ModelMandelbrot.java (lines 312-334)) lanzan IOException.

setColorMapName() (src/model/ModelMandelbrot.java (lines 307-310)) admite un test trivial para asegurarse de que cambia el nombre y de que al pasar null vuelve al mapa por defecto.
Finalmente, la notificación de listeners (src/model/ModelMandelbrot.java (lines 338-351)) puede probarse con un ModelListener falso que incremente un contador cada vez que modelChanged() se invoca; entonces se comprueba que operaciones como setMaxIterations() disparan el callback y que setColorMapName() también lo hace pese a no recalcular datos.

Tiene sentido agrupar los tests por capa siguiendo la estructura actual del código (model, controller, view). Podrías crear tres clases (o paquetes) principales:

src/test/model/ModelMandelbrotTest para todos los casos del modelo (constructor, setters, undo/redo, persistencia, listeners). Si hubiera más clases de modelo en el futuro, añadirías otros archivos dentro de ese paquete.
src/test/controller/ControllerMandelbrotTest para verificar la lógica del controlador: cómo interpreta eventos del UI, interacción con el modelo, validaciones, etc. Aquí suele ser útil usar dobles (mocks/fakes) del modelo y la vista.
src/test/view/... para componentes de la vista que tengan lógica propia (por ejemplo mapas de color). Normalmente los tests de UI se limitan a partes elásticas (formatos, utilidades) y se ubican en clases como ColourMapTest, MandelbrotPanelTest, etc., dentro del mismo paquete view.

Con los test me he dado cuenta que no he hecha mucha validación de excepciones. 
    - Limita el JSpinner de iteraciones (src/controller/ControllerMandelbrot.java (line 87)) con un SpinnerNumberModel definido (mínimo 1, máximo razonable, paso 10) para que no puedan entrar valores negativos o cero. Si alguien manipula el spinner con texto, captura IllegalArgumentException al leerlo y restaura el último valor válido mostrando un aviso.
    - En ModelMandelbrot.setViewWindow() (src/model/ModelMandelbrot.java (lines 149-159)) valida que minReal < maxReal y minImag < maxImag; lanza IllegalArgumentException si no se cumple, lo que evitará zonas degeneradas que luego provoquen divisiones entre 0 en recalculate().
    - setMaxIterations() (src/model/ModelMandelbrot.java (lines 136-144)) podría rechazar valores < 1 o mayores a, por ejemplo, 10000, dependiendo de los límites aceptables. De nuevo, lanzar IllegalArgumentException protege al modelo de estados inútiles.
    - pan() y applyZoom() (src/controller/ControllerMandelbrot.java (lines 103-162)) deberían ignorar o avisar si el rectángulo arrastrado tiene anchura/altura 0 (hoy ya se comprueba en mouseReleased, pero podrías reforzarlo con un early return en applyZoom).
    - En handleLoad() (src/controller/ControllerMandelbrot.java (lines 154-178)) después de model.loadFromFile() podrías hacer validación básica de los valores cargados (por ejemplo, asegurarte de que las iteraciones siguen dentro del rango permitido); si no, muestra error y no actualices la UI.
    - Para setColorMapName() (src/model/ModelMandelbrot.java (lines 307-310)), valida que el parámetro sea uno de los nombres registrados; si no, lanza una excepción o vuelve explícitamente al mapa por defecto y registra un warning (por ejemplo vía Logger) para que sea detectable durante depuración.

Haré test de esto también. 

---

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

---

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
