package Utils;

import PizzaObjects.Slice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class ReadingWritingUtils {

    public static char[][] constructPizzaMatrix(final List<String> lines, final int totalRows, final int totalColumns) {

        char[][] tastyPizzaArr = new char[totalRows][totalColumns];

        IntStream.range(0, lines.size())
                .forEach(rowIdx -> {
                    final String line = lines.get(rowIdx);
                    IntStream.range(0, lines.get(rowIdx).length())
                            .forEach(columnIdx ->
                                    tastyPizzaArr[rowIdx][columnIdx] = line.charAt(columnIdx));
                });

        return tastyPizzaArr;
    }

    public static void writeAnswerToTheFile(final Set<Slice> results) throws IOException {

        final StringBuilder resultStr = new StringBuilder();
        resultStr.append(results.size()).append(sNewLine);

        results.forEach(x ->
                resultStr.append(x.toString()).append(sNewLine));

        Files.write(Paths.get("Results/output.txt"), resultStr.toString().getBytes());
    }

    private final static String sNewLine = "\n";

}
