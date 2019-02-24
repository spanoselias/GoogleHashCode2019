package PizzaObjects;

import java.util.Objects;

public final class SliceRowLocation {


    public static SliceRowLocation create(final int rowLocation, final Slice slicesList) {
        return new SliceRowLocation(rowLocation, slicesList);
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public Slice getSlice() {
        return slice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SliceRowLocation that = (SliceRowLocation) o;
        return Objects.equals(slice, that.slice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slice);
    }

    private SliceRowLocation(final int rowLocation, final Slice slice) {
        this.rowLocation = rowLocation;
        this.slice = slice;
    }

    private final int rowLocation;
    private final Slice slice;
}
