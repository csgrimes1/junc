package org.junc.patterns;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class TailCall<TResult> {
    final Callable<TailCall<TResult>> _nextCall;
    final Optional<TResult> _result;

    private TailCall(Callable<TailCall<TResult>> nextCall, Optional<TResult> result){
        _nextCall = nextCall;
        _result = result;
    }

    public static<TResult> TailCall<TResult> done(TResult returnValue){
        return new TailCall<TResult>( () -> null, Optional.of(returnValue));
    }

    public static<TArg, TResult> TailCall<TResult> scheduleRecursion(Callable<TailCall<TResult>> nextCall){
        return new TailCall<TResult>( nextCall, Optional.empty());
    }

    public boolean isDone() {
        return _result.isPresent();
    }

    TailCall<TResult> recurse() {
        try {
            return _nextCall.call();
        }
        catch(Exception x){
            throw new RuntimeException(x);
        }
    }

    public static<TResult> TResult run(Callable<TailCall<TResult>> callback) throws Exception{
        return Stream.iterate(scheduleRecursion(callback), (r) -> r.recurse())  //Start a stream of tail calls.
                .filter( (r) -> r.isDone() )    //Search for the done node.
                .findFirst()                    //Reduce the infinite sequence to just one element.
                .get()                          //Get the TailCall from the Optional.
                ._result
                .get();
        /*
        //Leaving original mutable code for reference...
        TailCall<TResult> res = null;
        for(res = scheduleRecursion(callback); !res.isDone(); res = res.recurse()){
        }
        return res._result.get();
        */
    }

}
