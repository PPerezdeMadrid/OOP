public class TextAlignment {
    public static void main(String[] args) {
        if (args.length < 3){
            System.out.println("usage: java TextAlignment <filename> <alignmentType> <lineLength>");
            return;
        }
        // Filename = args[0] ; <alignmentType> = args[1] ; <lineLength> = args[2]
        // If it doesnt exist --> "File Not Found: <path>"
        String filename = args[0];
        String alignmentType = args[1].toLowerCase();
        int lineLength = Integer.parseInt(args[2]);
        
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
            case "center":
                return centerAlign(paragraph, lineLenght);
            case "justify":
                return justify(paragraph, lineLenght);
            default:
                System.out.println("Tipo de alineación inválido. Use left, right, center o justify.");
                return paragraph;
        }
    }

    /**
     * Se recorren todas las palabras del párrafo y se agregan a una línea hasta alcanzar la longitud máxima (lineLength).
     * Cuando la línea se llena, se imprime y se empieza una nueva.
     * Todas las líneas comienzan desde el margen izquierdo, por lo que no se agregan espacios adicionales al inicio
     */
    private static String leftAlign(String paragraph, int lineLength) {
        String[] words = paragraph.split("\\s+"); // separar por espacios
        StringBuilder alignedText = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 > lineLength) {
                // Supera la longitud actual --> tenemos que "romper" esta línea y pasarla a la siguiente
                alignedText.append(line.toString().trim()).append("\n");
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }

        if (line.length() > 0) {
            alignedText.append(line.toString().trim());
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
            if (line.length() + word.length() + 1 > lineLength) {
                // calcular espacios al inicio para alinear a la derecha
                int spacesToAdd = lineLength - line.length();
                String padding = " ".repeat(spacesToAdd);
                alignedText.append(padding).append(line.toString().trim()).append("\n");
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }

        // agregar la última línea
        if (line.length() > 0) {
            int spacesToAdd = lineLength - line.length();
            String padding = " ".repeat(spacesToAdd);
            alignedText.append(padding).append(line.toString().trim());
        }

        return alignedText.toString();
    }

    /**
     * Similar a la alineación derecha, pero los espacios se distribuyen solo al inicio de la línea para centrar el texto:
     * int leftSpaces = (lineLength - line.length()) / 2;
     */
    private static String centerAlign(String paragraph, int lineLength) {
    String[] words = paragraph.split("\\s+");
    StringBuilder alignedText = new StringBuilder();
    StringBuilder line = new StringBuilder();

    for (String word : words) {
        if (line.length() + word.length() + 1 > lineLength) {
            int totalSpaces = lineLength - line.toString().trim().length();
            int leftSpaces = totalSpaces / 2;
            alignedText.append(" ".repeat(leftSpaces)).append(line.toString().trim()).append("\n");
            line = new StringBuilder();
        }
        line.append(word).append(" ");
    }

    if (line.length() > 0) {
        int totalSpaces = lineLength - line.toString().trim().length();
        int leftSpaces = totalSpaces / 2;
        alignedText.append(" ".repeat(leftSpaces)).append(line.toString().trim());
    }

    return alignedText.toString();
}

    /**
     * Se calcula el número de espacios necesarios para que la línea alcance lineLength.
     * Se distribuyen equitativamente entre las palabras de la línea.
     * Si hay espacios restantes (por división entera), se reparten uno a uno desde la izquierda hasta llenar la línea.
     * Esto hace que la línea se vea alineada tanto a la izquierda como a la derecha.
     */
    private static String justify(String paragraph, int lineLength) {
        String[] words = paragraph.split("\\s+");
        StringBuilder alignedText = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 > lineLength) {
                String[] lineWords = line.toString().trim().split("\\s+");
                int totalSpaces = lineLength - line.toString().trim().length();
                int gaps = lineWords.length - 1;

                if (gaps > 0) {
                    int spacesPerGap = totalSpaces / gaps;
                    int extraSpaces = totalSpaces % gaps;

                    for (int i = 0; i < lineWords.length; i++) {
                        alignedText.append(lineWords[i]);
                        if (i < gaps) {
                            alignedText.append(" ".repeat(spacesPerGap + (i < extraSpaces ? 1 : 0)));
                        }
                    }
                } else {
                    alignedText.append(lineWords[0]);
                }

                alignedText.append("\n");
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }

        if (line.length() > 0) {
            alignedText.append(line.toString().trim());
        }

        return alignedText.toString();
    }
}