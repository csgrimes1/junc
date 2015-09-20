package org.idiomot.patterns;

import java.util.Comparator;
import java.util.Optional;

public class Comparators {
    public static Comparator defaultComparator(){ return Comparator.nullsFirst(Comparator.naturalOrder()); }
}
