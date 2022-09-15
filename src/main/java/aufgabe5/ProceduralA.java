package aufgabe5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public final class ProceduralA {
    private ProceduralA() {
    }

    private static final int MIN_LENGTH = 20;

    //result = 57503 (175 microsec)
    public static void main(String[] args) throws IOException {
        final int run = 10000;
        int sum = 0;
        for (int i = 0; i < run; i++) {
            var input = Files.newBufferedReader(Paths.get("C:\\Users\\dfell\\Desktop\\INTELLIJ_IDEA_MAIN\\Sprachkonzepte\\src\\main\\resources\\input.txt"));
            var lines = new LinkedList<String>();

            long start = System.nanoTime();

            readLines(input, lines);
            removeEmptyLines(lines);
            removeShortLines(lines); // alle kürzer als MIN_LENGTH
            int n = totalLineLengths(lines);

            long stop = System.nanoTime();
            sum += (stop - start) / 1000;
            //System.out.printf("result = %d (%d microsec)%n", n, (stop - start) / 1000);
        }
        System.out.printf("result = (%d microsec)%n", sum / run);

    }

    private static void removeShortLines(final LinkedList<String> lines) {
        LinkedList<String> newLL = new LinkedList<>();
        for (final String line : lines) {
            if (line.length() >= MIN_LENGTH) {
                newLL.add(line);
            }
        }
        lines.clear();
        lines.addAll(newLL);
    }

    private static int totalLineLengths(final LinkedList<String> lines) {
        int count = 0;
        for (final String line : lines) {
            count += line.length();
        }
        return count;
    }

    private static void removeEmptyLines(final LinkedList<String> lines) {
        LinkedList<String> newLL = new LinkedList<>();
        for (final String line : lines) {
            if (!line.isEmpty()) {
                newLL.add(line);
            }
        }
        lines.clear();
        lines.addAll(newLL);
    }

    private static void readLines(final BufferedReader input, final LinkedList<String> lines) {
        try {
            while (input.ready()) {
                lines.add(input.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}