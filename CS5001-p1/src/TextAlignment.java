//ATENCIÓN: HACER UN .ZIP
// ==>  stacscheck--archive CS5001-p1.zip /cs/studres/CS5001/Coursework/p1-alignment/Tests
/**
 * TextAlignment is a utility program that reads a text file and prints its content aligned according to a specified format: left, right, centre, or justify.
 * 
 * The program supports word wrapping to fit a defined line width and handles long words appropriately (including hyphenation in justified text).
 * Invalid input arguments or unsupported alignment types produce a usage message.
 */

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class TextAlignment {
    /**
    * Minimum number of arguments required to run the program.
    */
    public static final int MIN_ARGS = 3;

    /**
    * Entry point for the TextAlignment program.
    * @param args args[0]: filename, args[1]: alignment type (left, right, centre, justify), args[2]: line length (positive integer).
    */
    public static void main(String[] args) {

        if (args.length < MIN_ARGS) {
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
     * Aligns a paragraph of text to the left within a specified line width.
     * 
     * Words are separated by spaces and placed sequentially on each line.
     * When a word does not fit in the current line, a new line is started.
     * No extra spaces are added at the end of lines.
     *
     * @param paragraph   the full text to be aligned.
     * @param lineLength  the maximum number of characters allowed per line.
     * @return a string containing the left-aligned text, with line breaks inserted where appropriate
     */
    private static String leftAlign(String paragraph, int lineLength) {
        String[] words = paragraph.split("\\s+");
        StringBuilder alignedText = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + (line.length() > 0 ? 1 : 0) > lineLength) {
                alignedText.append(line).append("\n");
                line.setLength(0);
            }

            if (line.length() > 0) {
                line.append(' ');
            }
            line.append(word);
        }

        if (line.length() > 0) {
            alignedText.append(line);
        }

        return alignedText.toString();
    }

    /**
     * Aligns a paragraph of text to the right within a specified line width.
     * 
     * Words are separated by spaces and placed sequentially on each line.
     * When a word does not fit in the current line, the line is printed with leading spaces added to align the text to the right.
     * The last line is also right-aligned before returning the result.
     *
     * @param paragraph   the full text to be aligned.
     * @param lineLength  the maximum number of characters allowed per line.
     * @return a string containing the right-aligned text, with line breaks and appropriate leading spaces
     */
    private static String rightAlign(String paragraph, int lineLength) {
        String[] words = paragraph.split("\\s+");
        StringBuilder alignedText = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + (line.length() > 0 ? 1 : 0) > lineLength) {
                int spacesToAdd = Math.max(0, lineLength - line.length());
                for (int i = 0; i < spacesToAdd; i++) {
                    alignedText.append(' ');
                }

                alignedText.append(line).append("\n");
                line.setLength(0);
            }

            if (line.length() > 0) {
                line.append(' ');
            }
            line.append(word);
        }

        if (line.length() > 0) {
            int spacesToAdd = Math.max(0, lineLength - line.length());
            for (int i = 0; i < spacesToAdd; i++) {
                alignedText.append(' ');
            }
            alignedText.append(line);
        }

        return alignedText.toString();
    }

    /**
     * Aligns a paragraph of text to the centre within a specified line width.
     * 
     * Words are separated by spaces and arranged sequentially on each line.
     * When a word does not fit in the current line, the line is printed with equal (or nearly equal) spaces on both sides to achieve centred alignment.
     * If the padding is uneven, one extra space is added to the left side.
     *
     * @param paragraph   the full text to be aligned.
     * @param lineLength  the maximum number of characters allowed per line.
     * @return a string containing the centre-aligned text, with line breaks and appropriate left padding for centring.
     */
    private static String centreAlign(String paragraph, int lineLength) {
        StringBuilder result = new StringBuilder();
        String[] words = paragraph.split("\\s+");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + (line.length() > 0 ? 1 : 0) > lineLength) {
                int totalPadding = lineLength - line.length();
                int leftPadding = (totalPadding + 1) / 2;
                for (int i = 0; i < leftPadding; i++) {
                    result.append(' ');
                }
                result.append(line).append('\n');
                line.setLength(0);
            }

            if (line.length() > 0) {
                line.append(' ');
            }
            line.append(word);
        }

        // última línea
        if (line.length() > 0) {
            int totalPadding = lineLength - line.length();
            int leftPadding = (totalPadding + 1) / 2;
            for (int i = 0; i < leftPadding; i++) {
                result.append(' ');
            }
            result.append(line).append('\n');
        }

        return result.toString();
    }

    /**
     * Justifies a paragraph of text within a specified line width.
     * 
     * Words are separated by spaces and distributed across lines so that each line (except the last one) fills the full width as closely as possible.
     * If a word exceeds the remaining space on the current line, it may be split with a hyphen and continued on the next line. The last line is left-aligned by default.
     *
     * @param paragraph   the full text to be justified.
     * @param lineLength  the maximum number of characters allowed per line.
     * @return a string containing the fully justified text, with line breaks and optional hyphenation for long words
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
                    lineWords.add(word);
                    lineLengthSoFar += word.length();
                    word = "";
                } else if (spaceLeft > 1) {
                    String part = word.substring(0, spaceLeft - 1) + "-";
                    lineWords.add(part);

                    result.append(String.join(" ", lineWords)).append("\n");
                    lineWords.clear();
                    lineLengthSoFar = 0;
                    word = word.substring(spaceLeft - 1);
                } else {
                    result.append(String.join(" ", lineWords)).append("\n");
                    lineWords.clear();
                    lineLengthSoFar = 0;
                }
            }
        }

        if (!lineWords.isEmpty()) {
            result.append(String.join(" ", lineWords));
        }

        return result.toString();
    }

}
