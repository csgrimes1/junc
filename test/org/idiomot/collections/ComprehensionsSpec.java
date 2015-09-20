package org.idiomot.collections;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.idiomot.collections.Comprehensions.permutations;

public class ComprehensionsSpec {
    @Test
    public void permuteArity2Test() {
        final Object[] ar = permutations(
                Stream.of("a", "b"),
                Stream.of(1, 2, 3),
                (s, n) ->  s + n
        ).toArray();
        assertEquals(6, ar.length);
        assertEquals("a1", ar[0]);
        assertEquals("b1", ar[3]);
    }

    @Test
    public void permuteArity3Test(){
        final long count3 = permutations(
                Stream.of(100, 200, 300),
                Stream.of(10L, 20L, 30L),
                Stream.of("1", "2"),
                (h, t, o) -> h + t + o
        ).count();
        assertEquals(18, count3);
    }

    @Test
    public void permuteArity4Test(){
        final long count = permutations(
                Stream.of(100, 200, 300),
                Stream.of(10L, 20L, 30L),
                Stream.of("1", "2"),
                Stream.of(3.0, 1.0, 2.0),
                (h, t, o, d) -> h + t + o + d
        ).count();
        assertEquals(54, count);
    }

    @Test
    public void permuteArity5Test(){
        final long count = permutations(
                Stream.of(100, 200, 300),
                Stream.of(10L, 20L, 30L),
                Stream.of("1", "2"),
                Stream.of(3.0, 1.0, 2.0),
                Stream.empty(),
                (h, t, o, d, opt) -> h + t + o + d
        ).count();
        assertEquals(0, count);
    }

    @Test
    public void permuteArityNTest(){
        final long count = permutations(
                (x) -> x,
                Stream.of(100, 200, 300),
                Stream.of(10L, 20L, 30L),
                Stream.of("1", "2"),
                Stream.of(3.0, 1.0, 2.0),
                Stream.of(1),
                Stream.of(-1, -2)
        ).count();
        assertEquals(108, count);
    }

}
