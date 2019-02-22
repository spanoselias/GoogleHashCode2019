import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

public final class HashCodePracticeProblem {

    public static class Slice {

        public static Slice create(int row1, int row2, int col1, int col2) {
            return new Slice(row1, row2, col1, col2);
        }

        public int getRow1() {
            return mRow1;
        }

        public int getRow2() {
            return mRow2;
        }

        public int getCol1() {
            return mCol1;
        }

        public int getCol2() {
            return mCol2;
        }

        private Slice(int row1, int row2, int col1, int col2) {
            mRow1 = row1;
            mRow2 = row2;
            mCol1 = col1;
            mCol2 = col2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Slice slice = (Slice) o;
            return mRow1 == slice.mRow1 &&
                    mRow2 == slice.mRow2 &&
                    mCol1 == slice.mCol1 &&
                    mCol2 == slice.mCol2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mRow1, mRow2, mCol1, mCol2);
        }

        @Override
        public String toString() {
            return mRow1 + " " + mCol1 + " " + mRow2 + " " + mCol2;
        }

        private int mRow1;
        private int mRow2;
        private int mCol1;
        private int mCol2;
    }

    public static class TotalIngredients {

        public TotalIngredients create(int totalMushrooms, int totalTomatos) {
            return new TotalIngredients(totalMushrooms, totalTomatos);
        }

        public int getTotalMushrooms() {
            return mTotalMushrooms;
        }

        public int getTotalTomatos() {
            return mTotalTomatos;
        }

        private TotalIngredients(int totalMushrooms, int totalTomatos) {
            mTotalMushrooms = totalMushrooms;
            mTotalTomatos = totalTomatos;
        }

        final int mTotalMushrooms;
        final int mTotalTomatos;
    }

    private static boolean isValidMinIngredientsSlice(
            final int rowIdx1,
            final int colIdx1,
            final int rowIdx2,
            final int colIdx2,
            final int minIngredients,
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

//                System.out.println(i + "," + j);
            }
        }

//        System.out.println("Total Mashrooms: " + totalMashroom);
//        System.out.println("Total Tomatos: " + totalTomatos);

        return totalMashroom >= minIngredients && totalTomatos >= minIngredients;
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
        for (int i = rowIdx1; i <= rowIdx2; i++) {
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

    private static char[][] constructTastyPizza(final List<String> lines, final int totalRows, final int totalColumns) {

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

    private static List<Slice> allCombinationSlices(int maxRow, int maxColumn) {

        final List<Slice> allSlices = new ArrayList<>(100000);

        for (int i = 0; i <= maxRow; i++) {
            for (int j = 0; j <= maxRow; j++) {
                for (int g = 0; g <= maxColumn; g++) {
                    for (int h = 0; h <= maxColumn; h++) {

                        if (i > j || g > h || i == j || g == h )
                            continue;

                        allSlices.add(Slice.create(i, j, g, h));
//                      System.out.println(i + "," + j + "," + g + "," + h );
                    }
                }
            }
        }

        return allSlices;
    }

    private static List<Slice> cutSlices(final char[][] pizzaLosToros, final List<Slice> allSlices, final int minIngredients, final int maxCells) {

        final List<Slice> slicesSet = new ArrayList<>(10000);

        allSlices.forEach(slice -> {

            try {


                final boolean isValidMaxSlice = isValidMaxSlice(slice.getRow1(), slice.getRow2(), slice.getCol1(), slice.getCol2(), maxCells, pizzaLosToros);
                final boolean isValidMinIngredientsSlice = isValidMinIngredientsSlice(slice.getRow1(), slice.getRow2(), slice.getCol1(), slice.getCol2(), minIngredients, pizzaLosToros);

                if (isValidMaxSlice && isValidMinIngredientsSlice) {
                    slicesSet.add(slice);
                }

            } catch (Throwable ex) {

            }

        });

        // final List<String> combinationList = combinations(pizzaLosToros);

        return slicesSet;
    }


    public static void main(String[] args) throws IOException {

//       isValidMinSlice(2, 2, 4, 4, 3, saPizza);

        final List<String> lines =
                Files.readAllLines(Paths.get("a_example.in"), StandardCharsets.UTF_8);

        final String[] initialValues = lines.get(0).split(" ");
        final int totalRows = Integer.parseInt(initialValues[0]);
        final int totalColumns = Integer.parseInt(initialValues[1]);
        final int minIngridientCellsInSlice = Integer.parseInt(initialValues[2]);
        final int maxTotalNoOfCellsOfSlice = Integer.parseInt(initialValues[3]);

        lines.remove(0);
        final char[][] tastyPizza = constructTastyPizza(lines, totalRows, totalColumns);

        final List<Slice> allSlices = allCombinationSlices(totalRows, totalColumns);

        final List<Slice> res = cutSlices(tastyPizza, allSlices, minIngridientCellsInSlice, maxTotalNoOfCellsOfSlice);

        System.out.println(res.size());
        res.stream().distinct().limit(4).forEach(System.out::println);

//        System.out.println("Eating pizza!");
//        System.out.println(allSlices.size());
    }


}
