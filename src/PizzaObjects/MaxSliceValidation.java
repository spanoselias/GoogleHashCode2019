package PizzaObjects;

public  class MaxSliceValidation {

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
