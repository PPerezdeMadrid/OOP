public class TextAlignment {
    public static void main(String[] args) {
        String[] alignedType = {"left", "right", "center", "justify"};

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
            /** case "center":
                return centerAlign(paragraph, lineLenght);
            case "justify":
                return justify(paragraph, lineLenght); */
            default:
                System.out.println("Tipo de alineación inválido. Use left, right, center o justify.");
                return paragraph;
        }
    }

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
 

}