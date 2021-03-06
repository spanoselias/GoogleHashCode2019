package Solution;

import static Utils.ReadingWritingUtils.constructPizzaMatrix;
import static Utils.ReadingWritingUtils.writeAnswerToTheFile;
import PizzaObjects.Slice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Algorithms.*;
import PizzaObjects.SliceRowLocation;

public final class Solution {

    public static void main(String[] args) throws IOException {

        // Read input file.
        final List<String> lines =
                Files.readAllLines(Paths.get("DataSets/c_medium.in"), StandardCharsets.UTF_8);

        // Read values
        final String[] initialValues = lines.get(0).split(" ");
        final int totalRows = Integer.parseInt(initialValues[0]);
        final int totalColumns = Integer.parseInt(initialValues[1]);
        final int minIngridientCellsInSlice = Integer.parseInt(initialValues[2]);
        final int maxTotalNoOfCellsOfSlice = Integer.parseInt(initialValues[3]);

        lines.remove(0);
        final char[][] tastyPizza = constructPizzaMatrix(lines, totalRows, totalColumns);

        final Set<SliceRowLocation> allSlicesRowOrientedLocationFormat =
                AlgorithmsUtils.constructRowOrientedOverlapFreeSliderFromPizzaMatrix(totalRows, totalColumns, minIngridientCellsInSlice, maxTotalNoOfCellsOfSlice, tastyPizza);

        // Slice pieces using row-oriented slider
        final Set<Slice> allSlicesRowOriented =
                allSlicesRowOrientedLocationFormat
                        .stream().map(SliceRowLocation::getSlice)
                        .collect(Collectors.toSet());

        // Slice pieces using square-oriented slider
        final Set<Slice> allSlicesSquareOriented =
                AlgorithmsUtils.constructOptimisticSquareSliderOverlapFreeFromPizzaMatrix(totalRows, totalColumns, minIngridientCellsInSlice, maxTotalNoOfCellsOfSlice, tastyPizza);

        // Slice pieces using square-oriented slider
        final Set<Slice> allSlicesColumnOriented =
                AlgorithmsUtils.constructColumnOrientedOverlapFreeSliderFromPizzaMatrix(totalRows, totalColumns, minIngridientCellsInSlice, maxTotalNoOfCellsOfSlice, tastyPizza);

        // We improve accuracy
        final Set<Slice> allSlicesRowOrientedOptimised1 =
                PostProcessingUtils.postprocessorForIncreasingOverallScore(allSlicesRowOrientedLocationFormat, minIngridientCellsInSlice, maxTotalNoOfCellsOfSlice, tastyPizza);

        // We choose the approach with the highest score.
        final Set<Slice> highestResults =
                Stream.of(allSlicesRowOriented, allSlicesSquareOriented, allSlicesRowOrientedOptimised1, allSlicesColumnOriented)
                        .max(Comparator.comparing(Set::size))
                        .get();

        writeAnswerToTheFile(highestResults);
    }
}
