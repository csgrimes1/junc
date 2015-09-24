package org.junc.collections;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//First argument in a variadric function is required.
public class RequiredVariadric<T> {
    final List<T> _args;

    RequiredVariadric(T[] reqdArgs, T[] optionalArgs){
        _args =  Stream.concat(Stream.of(reqdArgs), Arrays.stream(optionalArgs))
                .collect(Collectors.toList());
    }

    //This method creates a variadric that concatenates the first argument with the optional
    //ones. Your variadric method should have an identical signature in the final two args.
    @SuppressWarnings("unchecked")
    public static<T> RequiredVariadric<T> create(@NotNull T arg1, T... optionalArgs){
        return new RequiredVariadric<>((T[]) new Object[]{arg1}, optionalArgs);
    }

     @SuppressWarnings("unchecked")
    public static<T> RequiredVariadric<T> create(@NotNull T arg1, @NotNull T arg2, T... optionalArgs){
        return new RequiredVariadric<>((T[]) new Object[]{arg1, arg2}, optionalArgs);
    }

    public int size(){ return _args.size(); }
    public T get(int index) { return _args.get(index); }
    public Stream<T> stream( ){
        return _args.stream();
    }
}
