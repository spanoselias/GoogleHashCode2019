import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public final class HashCodePractice {

    public class TotalIngredients {

        public TotalIngredients create(int totalMushrooms, int totalTomatos) {
            return new TotalIngredients(totalMushrooms, totalTomatos);
        }

        public int getmTotalMushrooms() {
            return mTotalMushrooms;
        }

        public int getmTotalTomatos() {
            return mTotalTomatos;
        }

        private TotalIngredients(int totalMushrooms, int totalTomatos) {
            mTotalMushrooms = totalMushrooms;
            mTotalTomatos = totalTomatos;
        }

        final int mTotalMushrooms;
        final int mTotalTomatos;
    }

    public boolean isValidSlice(
            final int rowIdx1,
            final int colIdx1,
            final int rowIdx2,
            final int colIdx2,
            final int minMushroom,
            final int minTomatos,
            final char[][] tastyPizza) {
        
    }

    private static char[][] constructTastyPizza(final List<String> lines, int totalRows, int totalColumns) {

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

    public static void main(String[] args) throws IOException {

        final List<String> lines =
                Files.readAllLines(Paths.get("a_example.in"), StandardCharsets.UTF_8);

        final String[] initialValues = lines.get(0).split(" ");
        final int totalRows = Integer.parseInt(initialValues[0]);
        final int totalColumns = Integer.parseInt(initialValues[1]);
        final int minIngridientCellsInSlice = Integer.parseInt(initialValues[2]);
        final int maxTotalNoOfCellsOfSlice = Integer.parseInt(initialValues[3]);

        lines.remove(0);
        final char[][] tastyPizza = constructTastyPizza(lines, totalRows, totalColumns);

        System.out.println("Eating pizza!");
    }


}
