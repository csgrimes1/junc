package org.idiomot.patterns;

import org.idiomot.function.NoArgFunctionWithThrows;

public class Try {
    public static<R> R catchAndThrow( NoArgFunctionWithThrows<R> codeBlock ){
        try {
            return codeBlock.call();
        }
        catch(Exception x){
            throw new RuntimeException(x);
        }
    }


    

}
