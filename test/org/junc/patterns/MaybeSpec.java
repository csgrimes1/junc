package org.junc.patterns;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MaybeSpec {
    @Test
    public void maybeFromValue(){
        final int expect = 5150;
        final Maybe<Integer> maybe = Maybe.of(expect);
        assertEquals(expect, (int) maybe.get());
        assertTrue(maybe.isPresent());
        assertEquals(1L, maybe.stream().count());
        final Object[] ar = maybe.stream().toArray();
        assertEquals(expect, (int) ar[0]);
        final Optional<Integer> optional = maybe.asOptional();
        assertTrue(optional.isPresent());
        assertEquals(expect, (int)optional.get());

        final Maybe<Integer> m2 = Maybe.fromOptional(optional);
        assertEquals(expect, (int)m2.get());
    }

    @Test
    public void maybeFromEmpty(){
        final Maybe<Integer> maybe = Maybe.empty();
        assertTrue( !maybe.isPresent() );
        assertEquals(0L, maybe.stream().count());
        final Optional<Integer> optional = maybe.asOptional();
        assertTrue(!optional.isPresent());

        final Maybe<Integer> m2 = Maybe.fromOptional(optional);
        assertTrue(!m2.isPresent());
    }

    @Test
    public void maybeFromBoolTrue(){
        final Maybe<Boolean> maybe = Maybe.fromBoolean(true);
        assertTrue(maybe.isPresent());
        assertEquals(01L, maybe.stream().count());
    }

    @Test
    public void maybeFromBoolFalse(){
        final Maybe<Boolean> maybe = Maybe.fromBoolean(false);
        assertTrue(!maybe.isPresent());
        assertEquals(0L, maybe.stream().count());
    }

}
