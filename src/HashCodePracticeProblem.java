import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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

    public static class MaxSliceValidation {

        public static MaxSliceValidation create(boolean isMaxSliceValid, int totalCels) {
            return new MaxSliceValidation(isMaxSliceValid, totalCels);
        }

        public boolean getIsMaxSliceValid() {
            return mIsMaxSliceValid;
        }

        public int getTotalCells() {
            return mTotalCells;
        }

        private MaxSliceValidation(boolean isMaxSliceValid, int totalCels) {
            mIsMaxSliceValid = isMaxSliceValid;
            mTotalCells = totalCels;
        }

        private final boolean mIsMaxSliceValid;
        private final int mTotalCells;

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

        for (int i = rowIdx1; i <= rowIdx2; i++) {
            for (int j = colIdx1; j <= colIdx2; j++) {

                if (tastyPizza[i][j] == 'M')
                    cntTotalMashroom += 1;
                else
                    cntTotalTomatos += 1;

            }
        }

        return cntTotalMashroom >= minIngredients && cntTotalTomatos >= minIngredients;
    }

    private static MaxSliceValidation isValidMaxSlice(
            final int rowIdx1,
            final int colIdx1,
            final int rowIdx2,
            final int colIdx2,
            final int maxCells) {

//        int cnt = 0;
//        for (int i = rowIdx1; i <= rowIdx2; i++) {
//            for (int j = colIdx1; j <= colIdx2; j++) {
//
//                cnt += 1;
//            }
//        }

        final int fastCellTotalCnt = ((Math.abs(rowIdx1 - rowIdx2) + 1) * (Math.abs(colIdx1 - colIdx2) + 1));
//        if (fastCellTotalCnt != cnt) {
//            System.out.println("Error in total cell cnt.");
//        }

        return
                MaxSliceValidation.create(
                        fastCellTotalCnt <= maxCells,
                        fastCellTotalCnt);
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

    public static int approximateIndex(int min, int max) {


        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);

        return randomNum;
    }

    private static Set<Slice> constructRowOrientedOverlapFreeSlicesFromPizzaMatrix(int maxRow, int maxColumn, final int minIngredients, final int maxCells, char[][] tastyPizza) {

        System.out.println("Starting the construction of slices...");

        final Set<Slice> allSlices = new HashSet<>(100000);

        long cnt = 0;

        int slicingStep = 11;

        int i = 0;
        int j = 0;
        int g = 0;
        int h = slicingStep;
        boolean wasFoundValidSlice = false;

        while (true) {

            if (i > j || g > h)
                continue;

            ++cnt;

            final Slice newSlice = Slice.create(i, j, g, h);

            final boolean isValidMaxSlice = isValidMaxSlice(i, g, j, h, maxCells).getIsMaxSliceValid();
            final boolean isValidMinIngrdients = isValidMinIngredientsSlice(i, g, j, h, minIngredients, tastyPizza);

            if (isValidMaxSlice && isValidMinIngrdients) {
                allSlices.add(newSlice);

                wasFoundValidSlice = !wasFoundValidSlice;

                if (h >= maxColumn || h + slicingStep + 1 >= maxColumn) {
                    i += 1;
                    j = i;
                    g = 0;
                    h = slicingStep;
                } else {

                    g += slicingStep + 1;
                    h += slicingStep + 1;

                }
            }

            // We reach the end of the file.
            if (i + 1 >= maxRow) {
                break;
            }

            if (((h + 1) >= maxColumn - 1)) {
                i += 1;
                j = i;
                g = 0;
                h = slicingStep;
            } else {
                if (wasFoundValidSlice == false) {
                    g += 1;
                    h += 1;
                }
            }

            wasFoundValidSlice = false;

            if (cnt % 100000 == 0) {
                System.out.println("New Counter: " + cnt);
            }

//            if (cnt >= 6128) {
//                System.out.println("Finished the construction of slices...");
//
//                return allSlices;
//            }

        }

        return allSlices;

    }

    private static Set<Slice> constructColumnOrientedOverlapFreeSlicesFromPizzaMatrix(int maxRow, int maxColumn, final int minIngredients, final int maxCells, char[][] tastyPizza) {

        System.out.println("Starting the construction of slices...");

        final Set<Slice> allSlices = new HashSet<>(100000);

        long cnt = 0;

        int slicingStep = 11;

        int i = 0;
        int j = slicingStep;
        int g = 0;
        int h = 0;
        boolean wasFoundValidSlice = false;

        while (true) {

//            if (i > j || g > h)
//                continue;

            ++cnt;

            final Slice newSlice = Slice.create(i, j, g, h);

            final boolean isValidMaxSlice = isValidMaxSlice(i, g, j, h, maxCells).getIsMaxSliceValid();
            final boolean isValidMinIngrdients = isValidMinIngredientsSlice(i, g, j, h, minIngredients, tastyPizza);

            if (isValidMaxSlice && isValidMinIngrdients) {
                allSlices.add(newSlice);

                wasFoundValidSlice = !wasFoundValidSlice;

                if (j >= maxRow || j + slicingStep + 1 >= maxRow) {
                    i += 1;
                    j = i;
                    g = 0;
                    h = slicingStep;
                } else {

                    g += slicingStep + 1;
                    h += slicingStep + 1;

                }
            }

            // We reach the end of the file.
            if (i + 1 >= maxRow) {
                break;
            }

            if (((h + 1) >= maxColumn - 1)) {
                i += 1;
                j = i;
                g = 0;
                h = slicingStep;
            } else {
                if (wasFoundValidSlice == false) {
                    g += 1;
                    h += 1;
                }
            }

            wasFoundValidSlice = false;

            if (cnt % 100000 == 0) {
                System.out.println("New Counter: " + cnt);
            }

//            if (cnt >= 6128) {
//                System.out.println("Finished the construction of slices...");
//
//                return allSlices;
//            }

        }

        return allSlices;

    }

    private static Set<Slice> selectValidSlices(final char[][] pizzaLosToros, final Set<Slice> allSlices, final int minIngredients, final int maxCells) {

        System.out.println("Starting the selection of valid pizza cells.");

        final Set<Slice> slicesSet = new HashSet<>(100000);

        slicesSet.addAll(allSlices);

        System.out.println("Finished the selection of valid pizza cells.");

        return slicesSet;
    }

    private static void writeAnswerToTheFile(final Set<Slice> results) throws IOException {

        final StringBuilder resultStr = new StringBuilder();
        resultStr.append(results.size()).append(sNewLine);

        results.forEach(x ->
                resultStr.append(x.toString()).append(sNewLine));

        Files.write(Paths.get("output.txt"), resultStr.toString().getBytes());
    }

    public static void main(String[] args) throws IOException {

        // Read input file.
        final List<String> lines =
                Files.readAllLines(Paths.get("c_medium.in"), StandardCharsets.UTF_8);

        // Read values
        final String[] initialValues = lines.get(0).split(" ");
        final int totalRows = Integer.parseInt(initialValues[0]);
        final int totalColumns = Integer.parseInt(initialValues[1]);
        final int minIngridientCellsInSlice = Integer.parseInt(initialValues[2]);
        final int maxTotalNoOfCellsOfSlice = Integer.parseInt(initialValues[3]);

        lines.remove(0);
        final char[][] tastyPizza = constructPizzaMatrix(lines, totalRows, totalColumns);

        final Set<Slice> allSlices = constructRowOrientedOverlapFreeSlicesFromPizzaMatrix(totalRows, totalColumns, minIngridientCellsInSlice, maxTotalNoOfCellsOfSlice, tastyPizza);

        final Set<Slice> res = selectValidSlices(tastyPizza, allSlices, minIngridientCellsInSlice, maxTotalNoOfCellsOfSlice);

        writeAnswerToTheFile(res);

    }

    private final static String sNewLine = "\n";

}
