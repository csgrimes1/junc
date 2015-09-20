package org.idiomot.patterns;

import org.idiomot.patterns.TailCall;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TailCallSpec {

    //0 1 2 3 5 8 13 21
    //  0 1 2 3 4  5  6

    //Accumulator function.
    static TailCall<Integer> fibR(int index, int finalIndex, int prev, int cur){
        if( index == finalIndex ){
            return TailCall.done(prev + cur);
        }

        return TailCall.scheduleRecursion(() -> fibR(index + 1, finalIndex, cur, prev + cur));
    }

    //Accumulation wrapper.
    static int fib(int n) throws Exception{
        return TailCall.run( () -> {
            return fibR(0, n, 0, 1);
        });
    }

    @Test
    public void tailCallRun() throws Exception{
        assertEquals(13, fib(5));
        assertEquals(21, fib(6));
    }
}
