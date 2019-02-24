package PizzaObjects;

public class TotalIngredients {

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