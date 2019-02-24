package PizzaObjects;

import java.util.Objects;

public class Slice {

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