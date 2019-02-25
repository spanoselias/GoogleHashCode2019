package Algorithms;

import static Utils.ChecksUtils.isValidMaxSlice;
import static Utils.ChecksUtils.isValidMinIngredientsSlice;
import PizzaObjects.Slice;
import PizzaObjects.SliceRowLocation;

import java.util.HashSet;
import java.util.Set;

public class AlgorithmsUtils {

    public static Set<SliceRowLocation> constructRowOrientedOverlapFreeSliderFromPizzaMatrix(int maxRow, int maxColumn, final int minIngredients, final int maxCells, char[][] tastyPizza) {

        final Set<SliceRowLocation> resultSlicesNonOverlapping = new HashSet<>(80000);

        System.out.println("Starting the construction of slices...");

        int slicingStep = maxCells - 1;

        int i = 0;
        int j = 0;
        int g = 0;
        int h = slicingStep;
        boolean wasFoundValidSlice = false;

        while (i + 1 <= maxRow ) {

            final SliceRowLocation newSlice =
                    SliceRowLocation.create(
                            i,
                            Slice.create(i, j, g, h));

            final boolean isValidMaxSlice = isValidMaxSlice(i, g, j, h, maxCells).getIsMaxSliceValid();
            final boolean isValidMinIngrdients = isValidMinIngredientsSlice(i, g, j, h, minIngredients, tastyPizza);

            if (isValidMaxSlice && isValidMinIngrdients) {
                resultSlicesNonOverlapping.add(newSlice);

                wasFoundValidSlice = !wasFoundValidSlice;

                // Ignore cell's slice for avoiding overlapping.
                g += slicingStep + 1;
                h += slicingStep + 1;
                h = Math.min(maxColumn - 1, h);
            }

            if ((h + 1) >= maxColumn - 1 && !wasFoundValidSlice) {
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

        System.out.println("The construction of the slices was ended...");

        return resultSlicesNonOverlapping;

    }

    public static Set<Slice> constructOptimisticSquareSliderOverlapFreeFromPizzaMatrix(int maxRow, int maxColumn, final int minIngredients, final int maxCells, char[][] tastyPizza) {

        System.out.println("Starting the construction of slices...");

        final Set<Slice> allSlices = new HashSet<>(100000);

        int slicingStep = 6;

        int i = 0;
        int j = 1;
        int g = 0;
        int h = slicingStep;
        boolean wasFoundValidSlice = false;

        while (i + 1 <= maxRow) {

            final Slice newSlice = Slice.create(i, j, g, h);

            final boolean isValidMaxSlice = isValidMaxSlice(i, g, j, h, maxCells).getIsMaxSliceValid();
            final boolean isValidMinIngrdients = isValidMinIngredientsSlice(i, g, j, h, minIngredients, tastyPizza);

            if (isValidMaxSlice && isValidMinIngrdients) {
                allSlices.add(newSlice);

                wasFoundValidSlice = !wasFoundValidSlice;

                // Ignore cell's slice for avoiding overlapping.
                g += slicingStep + 1;
                h += slicingStep + 1;
                h = Math.min(maxColumn - 1, h);
            }

            if ((h + 1) >= maxColumn - 1 && !wasFoundValidSlice) {
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

    public static Set<Slice> constructColumnOrientedOverlapFreeSliderFromPizzaMatrix(int maxRow, int maxColumn, final int minIngredients, final int maxCells, char[][] tastyPizza) {

        System.out.println("Starting the construction of slices...");

        final Set<Slice> allSlices = new HashSet<>(100000);

        int slicingStep = maxCells - 1;

        int i = 0;
        int j = slicingStep;
        int g = 0;
        int h = 0;
        boolean wasFoundValidSlice = false;

        while (h + 1 < maxColumn) {

            final Slice newSlice = Slice.create(i, j, g, h);

            final boolean isValidMaxSlice = isValidMaxSlice(i, g, j, h, maxCells).getIsMaxSliceValid();
            final boolean isValidMinIngrdients = isValidMinIngredientsSlice(i, g, j, h, minIngredients, tastyPizza);

            if (isValidMaxSlice && isValidMinIngrdients) {
                allSlices.add(newSlice);

                wasFoundValidSlice = !wasFoundValidSlice;

                // Ignore cell's slice for avoiding overlapping.
                i += slicingStep + 1;
                j += slicingStep + 1;
                j = Math.min(maxRow - 1, j);
            }

            if ((j + 1) >= maxRow - 1 && !wasFoundValidSlice) {
                i = 0;
                j = slicingStep;
                g += 1;
                h = g;
            } else {
                if (wasFoundValidSlice == false) {
                    i += 1;
                    j += 1;
                }
            }

            wasFoundValidSlice = false;
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
