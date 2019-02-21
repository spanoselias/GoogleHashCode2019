import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public final class HashCodePractice {

    public static class TotalIngredients {

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

    private static boolean isValidMinSlice(
            final int rowIdx1,
            final int colIdx1,
            final int rowIdx2,
            final int colIdx2,
            final int minMushroom,
            final int minTomatos,
            final char[][] tastyPizza) {

        /*
        |1|2|3|10|
        |4|5|6|11| ->  1, 2, 1, 3
        |7|8|9|12|
        */

        int totalMashroom = 0;
        int totalTomatos = 0;
        for (int i = rowIdx1; i < rowIdx2; i++) {
            for (int j = colIdx1; j <= colIdx2; j++) {

                if (tastyPizza[i][j] == 'M')
                    totalMashroom += 1;
                else
                    totalTomatos += 1;

                System.out.println(i + "," + j);
            }
        }

        System.out.println("Total Mashrooms: " + totalMashroom);
        System.out.println("Total Tomatos: " + totalTomatos);

        return totalMashroom >= minMushroom && totalTomatos >= minTomatos;
    }

    private static boolean isValidMaxSlice(
            final int rowIdx1,
            final int colIdx1,
            final int rowIdx2,
            final int colIdx2,
            final int maxCells,
            final char[][] tastyPizza) {

        /*
        |1|2|3|10|
        |4|5|6|11| ->  1, 2, 1, 3
        |7|8|9|12|
        */

        int cnt = 0;
        for (int i = rowIdx1; i < rowIdx2; i++) {
            for (int j = colIdx1; j <= colIdx2; j++) {

                cnt += 1;
            }
        }

        return cnt <= maxCells;
    }

    public static char[][] saPizza = new char[][]{
            {'T', 'T', 'T', 'T', 'T'},
            {'M', 'M', 'M', 'M', 'M'},
            {'T', 'T', 'T', 'T', 'T'},
            {'M', 'M', 'M', 'M', 'M'},
    };


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

        isValidMinSlice(2, 2, 4, 4, 3, 3, saPizza);


//        final List<String> lines =
//                Files.readAllLines(Paths.get("a_example.in"), StandardCharsets.UTF_8);
//
//        final String[] initialValues = lines.get(0).split(" ");
//        final int totalRows = Integer.parseInt(initialValues[0]);
//        final int totalColumns = Integer.parseInt(initialValues[1]);
//        final int minIngridientCellsInSlice = Integer.parseInt(initialValues[2]);
//        final int maxTotalNoOfCellsOfSlice = Integer.parseInt(initialValues[3]);
//
//        lines.remove(0);
//        final char[][] tastyPizza = constructTastyPizza(lines, totalRows, totalColumns);

        System.out.println("Eating pizza!");
    }


}
