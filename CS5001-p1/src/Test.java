import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        // Creamos archivos de prueba (normalmente ya existirían)
        createFile("sample.txt",
            "Many things went on at the Unseen University and, regrettably teaching had to be one of them. "
          + "The faculty had long ago confronted this fact and had perfected various devices for avoiding it. "
          + "But this was perfectly all right because to be fair, so had the students."
        );

        createFile("mary.txt",
            "It's... supercalifragilisticexpialidocious! Even though the sound of it is something quite atrocious. "
          + "If you say it loud enough you'll always sound precocious. Supercalifragilisticexpialidocious!"
        );

        // 🔹 Casos de error
        System.out.println("=== TEST: sin argumentos ===");
        TextAlignment.main(new String[]{});  // debe mostrar usage

        System.out.println("\n=== TEST: archivo inexistente ===");
        TextAlignment.main(new String[]{"noFile.txt", "left", "20"});

        System.out.println("\n=== TEST: longitud inválida ===");
        TextAlignment.main(new String[]{"sample.txt", "left", "-1"});
        TextAlignment.main(new String[]{"sample.txt", "left", "dog"}); // debería mostrar usage

        System.out.println("\n=== TEST: tipo inválido ===");
        TextAlignment.main(new String[]{"sample.txt", "diagonal", "30"});

        // 🔹 Left align
        System.out.println("\n=== TEST: LEFT ===");
        TextAlignment.main(new String[]{"sample.txt", "left", "40"});

        // 🔹 Right align
        System.out.println("\n=== TEST: RIGHT ===");
        TextAlignment.main(new String[]{"sample.txt", "right", "40"});

        // 🔹 Center align
        System.out.println("\n=== TEST: CENTER (mary.txt, 40) ===");
        TextAlignment.main(new String[]{"mary.txt", "center", "40"});

        System.out.println("\n=== TEST: CENTER (sample.txt, 50) ===");
        TextAlignment.main(new String[]{"sample.txt", "center", "50"});

        // 🔹 Justify align
        System.out.println("\n=== TEST: JUSTIFY (mary.txt, 40) ===");
        TextAlignment.main(new String[]{"mary.txt", "justify", "40"});

        System.out.println("\n=== TEST: JUSTIFY (sample.txt, 50) ===");
        TextAlignment.main(new String[]{"sample.txt", "justify", "50"});
    }

    private static void createFile(String filename, String content) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(content);
        }
    }
}
