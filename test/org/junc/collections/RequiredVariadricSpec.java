package org.junc.collections;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RequiredVariadricSpec {
    @Test
    public void makeVariadric(){
        final int first = 1;
        final Integer[] tail = {2, 3};
        final RequiredVariadric<Integer> varArgs = RequiredVariadric.create(first, tail);
        assertEquals(3, varArgs.size());
        assertEquals(first, (int)varArgs.get(0));
        assertEquals(2, (int)varArgs.get(1));
        assertEquals(3, (int)varArgs.get(2));
        assertEquals(3, varArgs.stream().count());
    }

    @Test
    public void makeVariadricReq2(){
        final String first = "a", second = "b";
        final String[] tail = {"c", "d"};
        final RequiredVariadric<String> varArgs = RequiredVariadric.create(first, second, tail);
        assertEquals(4, varArgs.size());
        assertEquals(first, varArgs.get(0));
        assertEquals(second, varArgs.get(1));
        assertEquals(tail[0], varArgs.get(2));
        assertEquals(tail[1], varArgs.get(3));
    }
}
