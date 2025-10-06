import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class TextAlignment {
    public static void main(String[] args) {

        int minArgs = 3;
        if (args.length < minArgs) {
            System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
            return;
        }
        // Filename = args[0] ; <alignmentType> = args[1] ; <lineLength> = args[2]
        // If it doesnt exist --> "File Not Found: <path>"
        String filename = args[0];
        String alignmentType = args[1].toLowerCase();
        int lineLength;
        try {
            lineLength = Integer.parseInt(args[2]);
            if (lineLength <= 0) {
                System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
            return;
        }

        Set<String> validAlignments = Set.of("left", "right", "centre", "justify");

        if (!validAlignments.contains(alignmentType.toLowerCase())) {
            System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
            return;
        }

        String[] paragraphs = FileUtil.readFile(filename); // Incorporated a File Not Found!
        for (String paragraph : paragraphs) {
            String alignedText = alignParagraph(paragraph, alignmentType, lineLength);
            System.out.println(alignedText);
        }
    }

    public static String alignParagraph(String paragraph, String alignedType, int lineLenght) {
        switch (alignedType) {
            case "left":
                return leftAlign(paragraph, lineLenght);
            case "right":
                return rightAlign(paragraph, lineLenght);

            case "centre":
                return centreAlign(paragraph, lineLenght);
            case "justify":
                return justifyAlign(paragraph, lineLenght);
            default:
                System.out.println("Alignment Type invalid. Use left, right, center or justify.");
                return paragraph;
        }
    }

    /**
     * Se recorren todas las palabras del párrafo y se agregan a una línea hasta alcanzar la longitud máxima (lineLength).
     * Cuando la línea se llena, se imprime y se empieza una nueva.
     * Todas las líneas comienzan desde el margen izquierdo, por lo que no se agregan espacios adicionales al inicio.
     */
    private static String leftAlign(String paragraph, int lineLength) {
        String[] words = paragraph.split("\\s+"); // separar por espacios
        StringBuilder alignedText = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            // si la palabra no cabe en la línea actual, imprimir línea
            if (line.length() + word.length() + (line.length() > 0 ? 1 : 0) > lineLength) {
                alignedText.append(line).append("\n");
                line.setLength(0);
            }

            if (line.length() > 0) line.append(' ');
            line.append(word);
        }

        // agregar la última línea
        if (line.length() > 0) {
            alignedText.append(line);
        }

        return alignedText.toString();
    }


    /**
     * Se construyen las líneas igual que en la izquierda, pero antes de agregarlas al resultado se calculan los espacios necesarios al inicio:
     * int spacesToAdd = lineLength - line.length();
     * Estos espacios iniciales hacen que el texto quede alineado al margen derecho.
     */
    private static String rightAlign(String paragraph, int lineLength) {
        String[] words = paragraph.split("\\s+"); // separar por espacios
        StringBuilder alignedText = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + (line.length() > 0 ? 1 : 0) > lineLength) {
                // calcular espacios al inicio para alinear a la derecha
                int spacesToAdd = Math.max(0, lineLength - line.length());
                for (int i = 0; i < spacesToAdd; i++) alignedText.append(' ');

                alignedText.append(line).append("\n");
                line.setLength(0);
            }

            if (line.length() > 0) line.append(' ');
            line.append(word);
        }

        // agregar la última línea
        if (line.length() > 0) {
            int spacesToAdd = Math.max(0, lineLength - line.length());
            for (int i = 0; i < spacesToAdd; i++) alignedText.append(' ');
            alignedText.append(line);
        }

        return alignedText.toString();
    }


    /** .
     * Similar a la alineación derecha, pero los espacios se distribuyen solo al inicio de la línea para centrar el texto:
     * int leftSpaces = (lineLength - line.length()) / 2;
     */
    private static String centreAlign(String paragraph, int lineLength) {
        StringBuilder result = new StringBuilder();
        String[] words = paragraph.split("\\s+");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + (line.length() > 0 ? 1 : 0) > lineLength) {
                // calcular padding exacto de la línea actual
                int totalPadding = lineLength - line.length();
                int leftPadding = (totalPadding + 1) / 2; // añade 1 si es impar
                for (int i = 0; i < leftPadding; i++) result.append(' ');
                result.append(line).append('\n');
                line.setLength(0); // limpiar línea
            }

            if (line.length() > 0) {
                line.append(' ');
            }
            line.append(word);
        }

        // última línea
        if (line.length() > 0) {
            int totalPadding = lineLength - line.length();
            int leftPadding = (totalPadding + 1) / 2; // añadir espacio extra si es impar
            for (int i = 0; i < leftPadding; i++) result.append(' ');
            result.append(line).append('\n');
        }

        return result.toString();
    }


    /**
     * Se calcula el número de espacios necesarios para que la línea alcance lineLength.
     * Se distribuyen equitativamente entre las palabras de la línea.
     * Si hay espacios restantes (por división entera), se reparten uno a uno desde la izquierda hasta llenar la línea.
     * Esto hace que la línea se vea alineada tanto a la izquierda como a la derecha.
     */
    private static String justifyAlign(String paragraph, int lineLength) {
        String[] words = paragraph.split("\\s+");
        StringBuilder result = new StringBuilder();
        List<String> lineWords = new ArrayList<>();
        int lineLengthSoFar = 0;

        for (String word : words) {
            while (!word.isEmpty()) {
                int spaceLeft = lineLength - lineLengthSoFar - (lineWords.size() > 0 ? lineWords.size() : 0);
                if (word.length() <= spaceLeft) {
                    // la palabra cabe
                    lineWords.add(word);
                    lineLengthSoFar += word.length();
                    word = "";
                } else if (spaceLeft > 1) {
                    // cortar palabra al espacio disponible y añadir guion
                    String part = word.substring(0, spaceLeft - 1) + "-";
                    lineWords.add(part);
                    // imprimir la línea actual
                    result.append(String.join(" ", lineWords)).append("\n");
                    lineWords.clear();
                    lineLengthSoFar = 0;
                    word = word.substring(spaceLeft - 1);
                } else {
                    // espacio restante muy pequeño, imprimir línea y empezar nueva
                    result.append(String.join(" ", lineWords)).append("\n");
                    lineWords.clear();
                    lineLengthSoFar = 0;
                }
            }
        }

        // última línea -> izquierda
        if (!lineWords.isEmpty()) {
            result.append(String.join(" ", lineWords));
        }

        return result.toString();
    }


}
