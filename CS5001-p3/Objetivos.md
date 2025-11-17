# CS5001 – Practical 3: Mandelbrot Set Explorer

## 1. Objetivo del proyecto

* Implementar **una aplicación GUI** para explorar el **conjunto de Mandelbrot**:

  * Mostrar el fractal.
  * Permitir **zoom** y **pan** interactivo.
  * Permitir ajustar parámetros de cálculo.
  * Añadir funcionalidades avanzadas (enhancements) bien diseñadas.

* Usar una **arquitectura orientada a objetos** cuidada (idealmente **MD / MVC**) con buen diseño y buena separación de responsabilidades. 

---

## 2. Requisitos funcionales básicos (obligatorios)

Estos son los mínimos que el enunciado exige y con los que podrías llegar a ~14–16 puntos si el diseño es muy bueno. 

### 2.1. Visualización básica

* Mostrar el conjunto de Mandelbrot **en blanco y negro**:

  * Fondo blanco.
  * Puntos del conjunto en negro (iteración >= maxIterations).
* Usar los parámetros iniciales razonables (los del `MandelbrotCalculator` o equivalentes).

### 2.2. Zoom

* Permitir al usuario **seleccionar un área rectangular** sobre la imagen:

  * Arrastrar con el ratón para dibujar un rectángulo translucido o bordeado.
  * Mostrar esa caja de zoom superpuesta mientras arrastra.
* Tras soltar el ratón:

  * Transformar el rectángulo de píxeles (x1,y1,x2,y2) a nuevo rango de:

    * `minReal, maxReal, minImaginary, maxImaginary`.
  * Recalcular el Mandelbrot en ese rango.
  * Redibujar la imagen.

### 2.3. Pan (desplazamiento)

* Permitir al usuario “mover” la vista actual:

  * Por ejemplo, arrastrando con otra tecla/modificador (o usando botones de dirección).
  * Traducir la distancia arrastrada en pantalla a desplazamiento en las coordenadas complejas.
* Tras el pan:

  * Recalcular mandelbrot con los nuevos límites y redibujar.

### 2.4. Magnificación (zoom factor)

* Calcular la **magnificación aproximada** respecto a la vista inicial:

  * Por ejemplo, una razón entre ancho/alto de los rangos iniciales y los actuales.
* Mostrar ese factor superpuesto en la imagen (ej. esquina inferior izquierda).

### 2.5. maxIterations configurable

* Exponer en la interfaz una forma de cambiar `maxIterations`:

  * Campo numérico / slider / botones +/–.
* Recalcular el Mandelbrot con el nuevo valor:

  * Permitir imágenes más precisas al hacer deep zoom.

### 2.6. Undo / Redo básicos

* Implementar **Undo** y **Redo** para la última operación de zoom o pan:

  * Mantener una pila de estados (por lo menos para el estado inmediatamente anterior y posterior).
  * Un “estado” debe incluir:

    * `minReal, maxReal, minImaginary, maxImaginary`.
    * `maxIterations` (idealmente).
    * Cualquier otro parámetro relevante.
* Botones o atajos para:

  * **Undo** (volver al estado anterior).
  * **Redo** (rehacer).

---

## 3. Enhancements recomendados para apuntar a 20/20

El enunciado indica que para llegar a **17+** necesitas una **muy buena implementación de uno o más enhancements**. Para apuntar a 20, te conviene implementar **varios enhancements de forma sólida y bien integrada**. 

### 3.1. Mapeos de color avanzados (muy recomendable)

* Implementar **diferentes color maps**:

  * Blanco y negro (básico).
  * Mapa azul (como en la figura del enunciado).
  * Mapa rojo (otro ejemplo).
* La idea típica:

  * Valores de iteración bajos → tonos oscuros.
  * Valores cercanos a `maxIterations` → tonos más brillantes/blancos.
* Añadir **controles GUI** para cambiar el mapa de color:

  * Botones “Blue”, “Red”, “B/W”.
  * O menú desplegable de esquema de color.
* Diseñar una **clase o interfaz `ColourMap`** para hacerlo extensible:

  * Puntos extra por buen diseño.

### 3.2. Full Undo / Redo / Reset

Ir más allá del Undo/Redo básico:

* Historial completo de acciones:

  * Cada zoom, pan o cambio de parámetros se guarda en la pila de undo.
* **Undo múltiple**:

  * El usuario puede ir deshaciendo pasos hacia atrás.
* **Redo múltiple**:

  * Si el usuario hace Undo, puede “rehacer” hasta volver al estado actual.
* **Reset**:

  * Botón que:

    * Vuelve a los valores iniciales de la vista (rangos default del enunciado).
    * Restaura `maxIterations` por defecto.
    * Limpia o reinicia el historial según decisión de diseño (explicarlo en el informe).

### 3.3. Guardar / Cargar settings (Save / Load)

* Permitir guardar el estado actual a un archivo:

  * Ejemplo: fichero de texto o formato simple (JSON, propiedades, etc.) con:

    * `minReal, maxReal, minImaginary, maxImaginary`
    * `maxIterations`
    * Tipo de colour map seleccionado
    * Cualquier otro parámetro relevante.
* Permitir **cargar** un estado desde archivo:

  * Restaurar la vista y parámetros.
  * Recomendada una UI sencilla:

    * Menú **File → Save Settings… / Load Settings…**.

### 3.4. Exportar imagen (Export)

* Permitir al usuario **exportar la imagen actual del fractal** a:

  * Formato estándar: PNG / JPEG / BMP, etc.
* Uso típico:

  * `BufferedImage` + `ImageIO.write`.
* Debe exportarse la imagen **con el color map actual** y la resolución de la ventana (o resolución configurable).

### 3.5. Animaciones de zoom

* Permitir definir y reproducir **animaciones de zoom**:

  * Por ejemplo:

    * Usuario define un “punto objetivo” y número de pasos.
    * O define una secuencia de vistas/rectángulos.
  * Se genera una serie de frames interpolando entre vistas.
* Reproducir la animación en la GUI:

  * Temporizador que va cambiando los parámetros y redibujando.
* Opcional extra:

  * Exportar la animación a imágenes sucesivas o un formato de vídeo/gif (si te da tiempo, no es obligatorio).

### 3.6. Otras mejoras de usabilidad (opcionales pero suman)

* Barra de estado con:

  * Coordenadas complejas del pixel bajo el ratón.
  * Iteration count del punto bajo el ratón.
  * Magnificación actual.
* Menús bien estructurados:

  * **File, View, Colour, Help…**
* Atajos de teclado:

  * `Ctrl+Z` Undo, `Ctrl+Y` Redo, `Ctrl+S` Save, etc.
* Preferencias persistentes (recordar última ruta de archivo, etc.).

---

## 4. Diseño orientado a objetos (clave para nota alta)

El enunciado destaca que para 14–16 necesitas **muy buen diseño OO**, y para ≥17, además de enhancements, ese diseño debe seguir siendo sólido. 

### 4.1. Arquitectura MD/MVC

* Separar claramente:

  * **Modelo (Model)**:

    * Gestiona parámetros del Mandelbrot:

      * Rango complejo actual.
      * `maxIterations`.
      * `radiusSquared` si lo usas.
      * Colour map actual.
    * Contiene o usa la clase que calcula el Mandelbrot (`MandelbrotCalculator` o equivalente).
    * Gestiona el historial de estados (para undo/redo).
    * Métodos públicos:

      * `zoomTo(rectangle)`, `pan(dx, dy)`, `setMaxIterations(int)`, `setColourMap(...)`, `reset()`, etc.
  * **Vista/Delegado (View/Delegate)**:

    * Componentes Swing (o el toolkit elegido).
    * Lienzo de dibujo que:

      * Sólo sabe dibujar la imagen que el Modelo le proporciona.
    * Paneles de control (botones, sliders, menús).
  * **Controlador** (puede estar en el delegate o en clases separadas):

    * Captura eventos de ratón/teclado.
    * Llama a los métodos del Modelo.
* Implementar **Observer**:

  * El Delegate/Vista se registra como observador del Modelo (por ejemplo con `PropertyChangeListener`).
  * Cuando el Modelo cambia:

    * Notifica a sus observers.
    * La vista redibuja el canvas.

### 4.2. Principios de buen diseño

* Encapsulación:

  * Campos privados, getters/setters cuando sean necesarios.
* Baja acoplamiento:

  * La vista no debe calcular Mandelbrot ella misma.
  * El modelo no debe preocuparse de detalles de Swing.
* Alta cohesión:

  * Cada clase tiene una responsabilidad clara:

    * `MandelbrotCalculator` → sólo cálculo numérico.
    * `MandelbrotModel` → parámetros + gestión de estado.
    * `MandelbrotPanel` → dibujo.
    * `ControlPanel` → botones y controles de usuario.
    * `ColourMap` + implementaciones (`BlueColourMap`, `RedColourMap`, etc.).
* Uso razonable de **herencia** e **interfaces**:

  * Por ejemplo:

    * Interfaz `ColourMap`.
    * Clases abstractas para vistas si tiene sentido.
* Buena descomposición de métodos:

  * Métodos cortos y claros.
  * Sin métodos gigantes que hagan de todo.

---

## 5. Implementación de la GUI y renderizado

### 5.1. Render Mandelbrot

* Usar la clase `MandelbrotCalculator` (del starter code) o tu propia versión correctamente. 
* Mapeo iteraciones → colores:

  * Del array `int[][]` a un `BufferedImage` píxel a píxel.
* Optimización razonable:

  * Reusar `BufferedImage` cuando sea posible.
  * Evitar recalcular mientras el usuario arrastra el rectángulo de zoom (sólo redibujar overlays).

### 5.2. Manejo de ratón

* MousePressed, MouseDragged, MouseReleased para:

  * Dibujar rectángulo de zoom.
  * Dibujar vector de pan (si usas esa metáfora).
* Cuidar que la interfaz se sienta fluida:

  * Redibujar sólo lo necesario.

---

## 6. Calidad del código

Para aspirar a matrícula / 20:

* **Estilo consistente**:

  * Nombres de clases en `UpperCamelCase`.
  * Nombres de métodos y variables en `lowerCamelCase`.
* Comentarios y Javadoc:

  * Javadoc para clases públicas y métodos importantes.
  * Comentarios donde la lógica sea menos obvia.
* No duplicar código:

  * Extraer métodos auxiliares comunes.
* Manejo correcto de excepciones:

  * Errores de I/O al guardar/cargar/exportar.
  * Inputs inválidos en campos de texto (por ejemplo, iteraciones negativas).
* Estructura de paquetes limpia:

  * `model`, `view`, `controller`, `util`, etc. (opcional pero profesional).

---

## 7. Informe escrito (indispensable para 20/20)

El enunciado exige un **informe en PDF** dentro de la carpeta de la práctica, explicando tu diseño, implementación, pruebas, etc. 

### 7.1. Contenido mínimo del informe

Incluye, como mínimo, secciones como:

1. **Introducción**

   * Objetivo del práctico.
   * Breve descripción de la aplicación.

2. **Diseño**

   * Justificación del patrón arquitectónico (MD/MVC).
   * Diagrama(s) de clases principales.
   * Explicación de responsabilidades de cada clase.
   * Cómo gestionas undo/redo, color maps, etc.

3. **Implementación**

   * Detalles clave:

     * Cómo mapeas coordenadas de pantalla ↔ plano complejo.
     * Cómo integras el `MandelbrotCalculator`.
     * Cómo están implementados los enhancements.
   * Explicación de cualquier decisión de diseño no trivial.

4. **Interfaz de usuario**

   * Descripción de la GUI:

     * Capturas de pantalla (muy aconsejable).
     * Flujos de uso típicos: hacer zoom, pan, cambiar color, guardar, cargar, etc.

5. **Pruebas**

   * Qué has probado:

     * Casos de zoom extremo.
     * Cambios de `maxIterations`.
     * Undo/Redo repetidos, Reset.
     * Guardar/cargar, exportar, animaciones, etc.
   * Cómo has verificado que los resultados son correctos (al menos de forma razonable).

6. **Limitaciones y trabajo futuro**

   * Qué no funciona o no te ha dado tiempo a hacer.
   * Posibles mejoras futuras.

7. **Conclusiones**

   * Lo aprendido sobre GUI, diseño OO, patrones, etc.

### 7.2. Presentación del informe

* Debe ser **word-processado** (no a mano) y exportado a **PDF**. 
* Lenguaje claro, sin faltas graves.
* Estructura con secciones y numeración.

---

## 8. Entrega y estructura del proyecto

### 8.1. Estructura sugerida del directorio

Dentro de tu carpeta `CS5001-p3` (por ejemplo):

* `src/`

  * paquetes de código Java.
* `report/`

  * `CS5001_P3_Report.pdf` (o similar).
* `resources/` (opcional)

  * iconos, archivos de configuración, etc.
* `README.md` (este documento o uno similar).
* Cualquier script de compilación/ejecución si procede.

### 8.2. Archivo a entregar

* Entregar un **.zip** con:

  * Todo el código fuente.
  * Informe en PDF.
  * Cualquier recurso necesario para compilar y ejecutar. 

---

## 9. Aspectos administrativos

* **Lateness**: saben que hay penalización de 1 punto por cada 24 horas o fracción de retraso (Scheme A). 
* **Good Academic Practice**:

  * Nada de copiar código sin citar.
  * Si usas ideas externas (blogs, StackOverflow, etc.), cítalas en el informe. 

---

## 10. Checklist rápida para 20/20

Antes de entregar, revisa:

* [ ] La app muestra el Mandelbrot en blanco y negro correctamente.
* [ ] Zoom con rectángulo, dibujado al arrastrar, recalcula y redibuja.
* [ ] Pan (desplazamiento) funcionando.
* [ ] Magnificación mostrada sobre la imagen.
* [ ] `maxIterations` ajustable desde la GUI.
* [ ] Undo/Redo básicos funcionando.
* [ ] **Múltiples colour maps** y cambio dinámico desde la GUI.
* [ ] **Full Undo/Redo/Reset** (historial de estados).
* [ ] **Save/Load** de parámetros funcionando.
* [ ] **Export** de imagen en formato estándar funcionando.
* [ ] (Opcional pero recomendable) **Animaciones de zoom** implementadas.
* [ ] Diseño MD/MVC claro, con separación Model / View / Controller.
* [ ] Uso adecuado de encapsulación, interfaces, etc.
* [ ] Código limpio, comentado, sin duplicaciones innecesarias.
* [ ] Informe en PDF completo (diseño, implementación, pruebas, capturas, etc.).
* [ ] Proyecto compila y se ejecuta sin errores en el entorno esperado.
* [ ] Zip entregado correctamente con todo lo necesario.
