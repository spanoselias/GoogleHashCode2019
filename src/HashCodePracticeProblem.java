import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
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

        int cntTotalMashroom = 0;
        int cntTotalTomatos = 0;

        for (int i = rowIdx1; i < rowIdx2; i++) {
            for (int j = colIdx1; j <= colIdx2; j++) {

                if (tastyPizza[i][j] == 'M')
                    cntTotalMashroom += 1;
                else
                    cntTotalTomatos += 1;

            }
        }

        return cntTotalMashroom >= minIngredients && cntTotalTomatos >= minIngredients;
    }

    private static boolean isValidMaxSlice(
            final int rowIdx1,
            final int colIdx1,
            final int rowIdx2,
            final int colIdx2,
            final int maxCells) {

        int cnt = 0;
        for (int i = rowIdx1; i <= rowIdx2; i++) {
            for (int j = colIdx1; j <= colIdx2; j++) {

                cnt += 1;
            }
        }

        return cnt <= maxCells;
    }

    private static char[][] constructPizzaMatrix(final List<String> lines, final int totalRows, final int totalColumns) {

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

    private static boolean doSlicesOverlap(final Slice l1, final Slice l2) {
        // If one rectangle is on left side of other
        if (l1.getCol2() < l2.getCol1() && l2.getCol2() > l2.getCol1()) {
            return false;
        }

        return true;
    }


    private static List<Slice> constructAllSlicesFromPizzaMatrix(int maxRow, int maxColumn) {

        final List<Slice> allSlices = new ArrayList<>(100000);

        long cnt = 0;

        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxRow; j++) {
                for (int g = 0; g < maxColumn; g++) {
                    for (int h = 0; h < maxColumn; h++) {

                        if (i > j || g > h)
                            continue;

                        ++cnt;

                        allSlices.add(Slice.create(i, j, g, h));

//                        if (cnt >= 8000000)
//                            return allSlices;

                    }
                }
            }
        }

        return allSlices;
    }

    private static List<Slice> selectValidSlices(final char[][] pizzaLosToros, final List<Slice> allSlices, final int minIngredients, final int maxCells) {

        final List<Slice> slicesSet = new ArrayList<>(10000);

        allSlices.forEach(slice -> {

            final boolean isValidMaxSlice = isValidMaxSlice(slice.getRow1(), slice.getCol1(), slice.getRow2(), slice.getCol2(), maxCells);
            final boolean isValidMinIngredientsSlice = isValidMinIngredientsSlice(slice.getRow1(), slice.getCol1(), slice.getRow2(), slice.getCol2(), minIngredients, pizzaLosToros);

            if (isValidMaxSlice && isValidMinIngredientsSlice) {

                final Optional<Slice> hasOverlap =
                        slicesSet.stream().filter(x -> doSlicesOverlap(x, slice)).findAny();

//                if (hasOverlap.isPresent()) {
//                    int shouldReplace = sRandom.nextInt(10) % 2;
//
//                    if (shouldReplace == 1) {
//                        slicesSet.remove(slice);
//                        slicesSet.add(hasOverlap.get());
//                    }
//                }

                if (hasOverlap.isEmpty()) {
                    slicesSet.add(slice);
                }
            }
        });

        // final List<String> combinationList = combinations(pizzaLosToros);
        return slicesSet;
    }

    /*
    public static char[][] saPizza = new char[][]{
            {'T', 'T', 'T', 'T', 'T'},
            {'M', 'M', 'M', 'M', 'M'},
            {'T', 'T', 'T', 'T', 'T'},
            {'M', 'M', 'M', 'M', 'M'},
    };*/
    public static void main(String[] args) throws IOException {

        // Read input file.
        final List<String> lines =
                Files.readAllLines(Paths.get("b_small.in"), StandardCharsets.UTF_8);

        // Read values
        final String[] initialValues = lines.get(0).split(" ");
        final int totalRows = Integer.parseInt(initialValues[0]);
        final int totalColumns = Integer.parseInt(initialValues[1]);
        final int minIngridientCellsInSlice = Integer.parseInt(initialValues[2]);
        final int maxTotalNoOfCellsOfSlice = Integer.parseInt(initialValues[3]);

        lines.remove(0);
        final char[][] tastyPizza = constructPizzaMatrix(lines, totalRows, totalColumns);

        final List<Slice> allSlices = constructAllSlicesFromPizzaMatrix(totalRows, totalColumns);

//      Collections.shuffle(allSlices);

        final List<Slice> res = selectValidSlices(tastyPizza, allSlices, minIngridientCellsInSlice, maxTotalNoOfCellsOfSlice);

        System.out.println(res.stream().distinct().collect(Collectors.toList()).size());
        res.stream().distinct().forEach(System.out::println);

//      System.out.println("Eating pizza!");

    }

    private final static Random sRandom = new Random();

}
