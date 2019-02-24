package Algorithms;

import static Utils.ChecksUtils.isValidMaxSlice;
import static Utils.ChecksUtils.isValidMinIngredientsSlice;
import PizzaObjects.Slice;

import java.util.HashSet;
import java.util.Set;

public class AlgorithmsUtils {

    public static Set<Slice> constructRowOrientedOverlapFreeSlicesFromPizzaMatrix(int maxRow, int maxColumn, final int minIngredients, final int maxCells, char[][] tastyPizza) {

        System.out.println("Starting the construction of slices...");

        final Set<Slice> allSlices = new HashSet<>(100000);

        long cnt = 0;

        int slicingStep = 13;

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

    public static Set<Slice> constructOptimisticSquareSliderOverlapFreeFromPizzaMatrix(int maxRow, int maxColumn, final int minIngredients, final int maxCells, char[][] tastyPizza) {

        System.out.println("Starting the construction of slices...");

        final Set<Slice> allSlices = new HashSet<>(100000);

        long cnt = 0;

        int slicingStep = 6;

        int i = 0;
        int j = 1;
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
                    j = i + 1;
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

        }

        return allSlices;

    }

    public static Set<Slice> constructColumnOrientedOverlapFreeSlicesFromPizzaMatrix(int maxRow, int maxColumn, final int minIngredients, final int maxCells, char[][] tastyPizza) {

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

    public static Set<Slice> selectValidSlices(final char[][] pizzaLosToros, final Set<Slice> allSlices, final int minIngredients, final int maxCells) {

        System.out.println("Starting the selection of valid pizza cells.");

        final Set<Slice> slicesSet = new HashSet<>(100000);

        slicesSet.addAll(allSlices);

        System.out.println("Finished the selection of valid pizza cells.");

        return slicesSet;
    }

}
