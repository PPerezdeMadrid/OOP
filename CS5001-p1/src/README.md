# P1 - Text Alignment
By Paloma Pérez de Madrid

## Overview

TextAlignment is a Java program that reads a text file and prints its content aligned according to a specified format: **left**, **right**, **centre**, or **justify**.

## Requirements

* Java **JDK 11** or higher.

## Compilation

In the `src` directory, run:

```bash
javac TextAlignment.java
```

## Running the Program

Run the program using:

```bash
java TextAlignment <filename> <alignmentType> <lineLength>
```

* `<filename>`: path to the text file to be aligned.
* `<alignmentType>`: `left`, `right`, `centre`, or `justify`.
* `<lineLength>`: maximum number of characters per line (positive integer).

Example:

```bash
java TextAlignment example.txt left 50
```

## Notes

* If the file does not exist, the program prints:

  ```
  File Not Found: <path>
  ```
* If the alignment type is invalid or line length is incorrect, a usage message is displayed.
