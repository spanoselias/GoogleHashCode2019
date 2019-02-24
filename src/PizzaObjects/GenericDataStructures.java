package PizzaObjects;

import java.util.Objects;

public class GenericDataStructures {

    public static final class Pair<K, V> {

        private final K first;
        private final V second;

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }

        private Pair(final K first, final V second) {
            this.first = first;
            this.second = second;
        }

        public static <K, V> Pair<K, V> create(final K first, final V second) {

            return new Pair<K, V>(first, second);
        }
    }

    public final static class Triplet<A, B, C> {

        private final A first;
        private final B second;
        private final C third;

        public A getFirst() {
            return first;
        }

        public B getSecond() {
            return second;
        }

        public C getThird() {
            return third;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
            return Objects.equals(first, triplet.first) &&
                    Objects.equals(second, triplet.second) &&
                    Objects.equals(third, triplet.third);
        }

        @Override
        public int hashCode() {

            return Objects.hash(first, second, third);
        }

        private Triplet(final A first, final B second, final C third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public static <A, B, C> Triplet<A, B, C> create(final A first, final B second, final C third) {

            return new Triplet<A, B, C>(first, second, third);
        }
    }
}
