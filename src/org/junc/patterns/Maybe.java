package org.junc.patterns;

import org.junc.collections.LinkedList;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Implementation of a pattern based on the java.util.Optional and the Scala Option. The Java implementation
 * lacks stream behavior, and that feature is implemented in this class. It is an elegant idiom
 * to use a Maybe instance as a finite stream. When the instance is empty, the stream has a length of zero, and
 * Comprehensions.permutations will not run the supplied callback routine, effectively due to multiplication
 * by zero.
 * @param <T>
 */
public class Maybe<T> {
    final Optional<T> _optional;

    Maybe(T val){
        _optional = Optional.of(val);
    }
    Maybe(){
        _optional = Optional.empty();
    }

    public static<T> Maybe<T> of(T val){
        return new Maybe<>(val);
    }
    public static<T> Maybe<T> empty(){
        return new Maybe<>();
    }

    public static Maybe<Boolean> fromBoolean(boolean val){
        return val ? Maybe.of(true) : Maybe.empty();
    }

    public static<T> Maybe<T> fromOptional(Optional<T> optional){
        if( optional.isPresent() )
            return new Maybe<>(optional.get());
        return new Maybe<>();
    }

    /**
     * Used for list comprehensions. Maybe can replace boolean logic by zeroing out the
     * permutations in a comprehension.
     * @return A stream of length 1 if the Maybe (Optional) is present. Otherwise, returns
     * an empty stream.
     */
    public Stream<T> stream(){
        if( _optional.isPresent() )
            return LinkedList.create(_optional.get()).stream(false);

        return Stream.empty();
    }

    public Optional<T> asOptional(){
        if( _optional.isPresent() )
            return Optional.of(_optional.get());

        return _optional.empty();
    }

    public T get(){
        return _optional.get();
    }

    public boolean isPresent(){
        return _optional.isPresent();
    }

}
