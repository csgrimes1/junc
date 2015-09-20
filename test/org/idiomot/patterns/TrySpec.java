package org.idiomot.patterns;


import org.idiomot.function.NoArgFunctionWithThrows;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class TrySpec {

    int countRuntimeExceptions(NoArgFunctionWithThrows<Integer> codeBlock) {
        try {
            Try.catchAndThrow( codeBlock );
            return 0;
        }
        catch(RuntimeException rx) {
            return 1;
        }
    }

    @Test
    public void catchBlockSpec(){
        assertEquals(1, countRuntimeExceptions(  ()  -> {
                    if( 1 < 10 )
                        throw new Exception("What?");
                    return 2;
                }
        ));
    }
}
