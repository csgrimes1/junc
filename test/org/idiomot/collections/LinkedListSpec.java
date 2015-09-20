package org.idiomot.collections;

import org.junit.Test;

import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;


public class LinkedListSpec {
    @Test
    public void linkedListCreate() throws Exception{
        final LinkedList<String> lst = LinkedList.create("a", "b", "c", "d");
        assertEquals(4, lst.size());
        assertEquals(3, lst.tail().size());
        assertEquals("a", lst.head());
    }

    @Test
    public void linkedListStream() throws Exception {
        final StreamSource<Integer> lst = LinkedList.create(1, 2, 3, 4, 5);
        assertEquals(5, lst.stream(false).count());

        //Reverse sort the first 3 elements and see what is at the head of the stream.
        final int val = lst.stream(false)
                .limit(3)
                .sorted( (a,b) -> a < b ? 1 : -1 )
                .findFirst().get();
        assertEquals(3, val);
    }

    @Test
    public void linkedListCreateFromStream() throws Exception {
        final LinkedList<Integer> lst = LinkedList.create(Stream.of(10, 9, 8, 7, 6, 5));
        assertEquals(6, lst.size());
        final int headVal = lst.head();
        assertEquals(10, headVal);
    }

    @Test
    public void linkedListReverse() throws Exception {
        final LinkedList<Integer> lst = LinkedList.create(0, 1, 2, 3, 4, 5, 6);
        final Object[] lstRev = lst.reverse().stream(false).toArray();
        assertEquals(lst.size(), lstRev.length);
        assertEquals(6, (int)lstRev[0]);
        assertEquals(3, (int)lstRev[3]);
        assertEquals(0, (int)lstRev[6]);
    }

}
