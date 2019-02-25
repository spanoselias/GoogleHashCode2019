package Algorithms;

import PizzaObjects.GenericDataStructures;
import PizzaObjects.Slice;
import PizzaObjects.SliceRowLocation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static PizzaObjects.GenericDataStructures.*;
import static Utils.ChecksUtils.isValidMaxSlice;
import static Utils.ChecksUtils.isValidMinIngredientsSlice;

public class PostProcessingUtils {

    // It returned the slices which need to be removed from the results
    public static Optional<Pair<Slice, Slice>> reconstructNewSliceFromExistingSlices(
            final Slice leftSlice,
            final Slice rightSlice,
            final int minIngredients,
            final int maxCells,
            char[][] tastyPizza,
            final Set<Slice> newGeneratedSlices) {

        final int shrinkDistance = 2;

        final Slice newLeftSlice = Slice.create(leftSlice.getRow1(), leftSlice.getRow2(), leftSlice.getCol1(), leftSlice.getCol2() - shrinkDistance);
        final Slice newRightSlice = Slice.create(rightSlice.getRow1(), rightSlice.getRow2(), rightSlice.getCol1() + shrinkDistance, rightSlice.getCol2());
        final Slice newSlice = Slice.create(leftSlice.getRow1(), leftSlice.getRow2(), leftSlice.getCol2() - shrinkDistance + 1, rightSlice.getCol1() + shrinkDistance - 1);

        final boolean leftIsValidMaxSlice = isValidMaxSlice(newLeftSlice.getRow1(), newLeftSlice.getCol1(), newLeftSlice.getRow2(), newLeftSlice.getCol2(), maxCells).getIsMaxSliceValid();
        final boolean leftIsValidMinIngrdients = isValidMinIngredientsSlice(newLeftSlice.getRow1(), newLeftSlice.getCol1(), newLeftSlice.getRow2(), newLeftSlice.getCol2(), minIngredients, tastyPizza);

        final boolean rightIsValidMaxSlice = isValidMaxSlice(newRightSlice.getRow1(), newRightSlice.getCol1(), newRightSlice.getRow2(), newRightSlice.getCol2(), maxCells).getIsMaxSliceValid();
        final boolean rightIsValidMinIngrdients = isValidMinIngredientsSlice(newRightSlice.getRow1(), newRightSlice.getCol1(), newRightSlice.getRow2(), newRightSlice.getCol2(), minIngredients, tastyPizza);

        final boolean newIsValidMaxSlice = isValidMaxSlice(newSlice.getRow1(), newSlice.getCol1(), newSlice.getRow2(), newSlice.getCol2(), maxCells).getIsMaxSliceValid();
        final boolean newIsValidMinIngrdients = isValidMinIngredientsSlice(newSlice.getRow1(), newSlice.getCol1(), newSlice.getRow2(), newSlice.getCol2(), minIngredients, tastyPizza);


        if (    leftIsValidMaxSlice &&
                leftIsValidMinIngrdients &&
                rightIsValidMaxSlice &&
                rightIsValidMinIngrdients &&
                newIsValidMaxSlice &&
                newIsValidMinIngrdients) {

            newGeneratedSlices.addAll(Arrays.asList(newLeftSlice, newRightSlice, newSlice));

            return Optional.of(Pair.create(leftSlice, rightSlice));
        }

        return Optional.empty();

    }

    public static Set<Slice> processorForIncreasingRowScore(
            Set<SliceRowLocation> sliceRowLocationSet,
            final int minIngredients,
            final int maxCells,
            char[][] tastyPizza) {

        final Map<Integer, List<Slice>> mapPerRow =
                sliceRowLocationSet
                        .stream()
                        .collect(Collectors.groupingBy(SliceRowLocation::getRowLocation,
                                Collectors.mapping(SliceRowLocation::getSlice, Collectors.toList())));

        final List<Triplet<Integer, Slice, Slice>> candidateCells = new ArrayList<>(500);

        mapPerRow.forEach((key, value) -> {

            final List<Slice> sortedRowSlices =
                    value.stream().sorted(Comparator.comparing(Slice::getCol2)).collect(Collectors.toList());

            for (int i = 1; i < sortedRowSlices.size(); i++) {

                // Detect if there are unused cells.
                final int diff = (sortedRowSlices.get(i).getCol1() - sortedRowSlices.get(i - 1).getCol2()) - 1;

                if (diff > 0) {
                    candidateCells.add(Triplet.create(diff, sortedRowSlices.get(i - 1), sortedRowSlices.get(i)));
                }
            }
        });

        final Set<Slice> finalResults = sliceRowLocationSet.stream().map(SliceRowLocation::getSlice).collect(Collectors.toSet());
        final Set<Slice> newGeneratedSlicesList = new HashSet<>(1000);

        candidateCells.forEach(candCells -> {

            final Optional<Pair<Slice, Slice>> needToBeRemoved =
                    reconstructNewSliceFromExistingSlices(candCells.getSecond(), candCells.getThird(), minIngredients, maxCells, tastyPizza, newGeneratedSlicesList);

            if (needToBeRemoved.isPresent()) {
                final boolean firstIsRemovedCorrectly = finalResults.remove(needToBeRemoved.get().getFirst());
                final boolean secondIsRemovedCorrectly = finalResults.remove(needToBeRemoved.get().getSecond());

                if(!firstIsRemovedCorrectly ) {
                    System.out.println(String.format("Unable to remove first slice: %s", needToBeRemoved.get().getFirst().toString()));
                }
                if(!secondIsRemovedCorrectly ) {
                    System.out.println(String.format("Unable to remove second slice: %s", needToBeRemoved.get().getSecond().toString()));
                }
            }
        });

        finalResults.addAll(newGeneratedSlicesList);

        return finalResults;
    }


}
