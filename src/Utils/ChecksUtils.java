package Utils;

import PizzaObjects.MaxSliceValidation;
import PizzaObjects.Slice;

import java.util.concurrent.ThreadLocalRandom;

public final class ChecksUtils {

    public static boolean isValidMinIngredientsSlice(
            final int rowIdx1,
            final int colIdx1,
            final int rowIdx2,
            final int colIdx2,
            final int minIngredients,
            final char[][] tastyPizza) {

        int cntMashroom = 0;
        int cntTomatos = 0;

        for (int i = rowIdx1; i <= rowIdx2; i++) {
            for (int j = colIdx1; j <= colIdx2; j++) {

                if (tastyPizza[i][j] == 'M')
                    cntMashroom += 1;
                else
                    cntTomatos += 1;
            }
        }

        return cntMashroom >= minIngredients && cntTomatos >= minIngredients;
    }

    public static MaxSliceValidation isValidMaxSlice(
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

    public static boolean doSlicesOverlap(final Slice l1, final Slice l2) {
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


}
