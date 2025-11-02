### Resumen de la Práctica

Esta tarea práctica forma parte del curso CS5001 Modelado, Diseño y Programación Orientada a Objetos en la Universidad de St Andrews. El objetivo es escribir un programa que lea múltiples párrafos desde un archivo y los alinee o justifique para ajustarse dentro de un margen especificado basado en longitudes de línea dadas.

### Requisitos Clave

- **Argumentos de Entrada**: El programa debe aceptar tres argumentos de la línea de comandos:
  - Nombre del archivo (incluyendo la ruta) que contiene el texto.
  - Tipo de alineación del texto (`left`, `right`, `center` o `justify`).
  - Longitud de la línea, que debe ser mayor que cero.

- **Manejo de Errores**:
  - Si algún argumento está ausente o es inválido, debe mostrar un mensaje de uso.
  - Debe notificar si el archivo especificado no existe.

- **Lectura del Archivo**: El programa leerá desde el archivo de texto utilizando el método `FileUtil.readFile` proporcionado en `FileUtil.java`, o alternativamente con un `Scanner`.

- **Alineación del Texto**:
  - Alinear los párrafos según el tipo especificado (`left`, `right`, `center` o `justify`).
  - Para texto justificado, asegurar que las palabras sean divididas y hifenadas adecuadamente.

- **Salida**: El texto formateado debe imprimirse directamente en la terminal sin caracteres adicionales. 

Estos son los elementos fundamentales de esta práctica que deben cumplir los estudiantes para demostrar su comprensión del manejo de archivos, alineación de texto y estructuración básica de programas en Java.


---

Método de alineacion:
1. Se separa el párrafo en palabras usando split("\\s+").

2. Se van agregando palabras a una línea hasta que se alcance lineLength.

3. Cuando la línea se llena, se agrega al StringBuilder principal y se comienza una nueva línea.

4. Al final, se agrega la última línea si quedó algo pendiente.

Con esto tu método alignParagraph funcionará para "left".