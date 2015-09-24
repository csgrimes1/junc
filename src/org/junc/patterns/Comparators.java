package org.junc.patterns;

import java.util.Comparator;

public class Comparators {
    public static Comparator defaultComparator(){ return Comparator.nullsFirst(Comparator.naturalOrder()); }
}
