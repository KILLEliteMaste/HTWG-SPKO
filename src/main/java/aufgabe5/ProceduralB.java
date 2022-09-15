package aufgabe5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public final class ProceduralB {
    private ProceduralB() {
    }

    private static final int MIN_LENGTH = 20;

    //57503
    //result = (175 microsec) <-ohne ausgabe
    //result = (6 microsec)  <- ohne ausgabe || mit ausgabe 24 microsec
    public static void main(String[] args) throws IOException {
        final int run = 10000;
        int sum = 0;
        for (int i = 0; i < run; i++) {
            var input = Files.readAllLines(Paths.get("C:\\Users\\dfell\\Desktop\\INTELLIJ_IDEA_MAIN\\Sprachkonzepte\\src\\main\\resources\\input.txt"));

            long start = System.nanoTime();

            int n = input.stream()
                    .filter(s -> !s.isEmpty())
                    .filter(s -> s.length() >= MIN_LENGTH)
                    .mapToInt(String::length)
                    .sum();
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