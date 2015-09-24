package org.junc.patterns;

import com.sun.istack.internal.NotNull;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.*;

public class When<R> {
    final Predicate<Object> _test;
    final Supplier<R> _callback;

    private When(Predicate<Object> test, Supplier<R> thenCall){
        _test = test;
        _callback = thenCall;
    }

    public static<R> When<R> when(Predicate<Object> test, Supplier<R> thenCall){
        return new When(test, thenCall);
    }

    public static<R, T> When<R> when(@NotNull Class<T> tClass, Supplier<R> thenCall){
        return when( (subject) -> tClass.isAssignableFrom(subject.getClass()), thenCall );
    }
    public static<R, T> When<R> when(@NotNull Class<T> tClass, R result){
        return when( (subject) -> tClass.isAssignableFrom(subject.getClass()), () -> result );
    }

    public static<R, T> When<R> when(T compareTo, Supplier<R> thenCall, Comparator comparator){
        return when( (subject) -> comparator.compare(subject, compareTo)==0, thenCall);
    }
    public static<R, T> When<R> when(T compareTo, R result, Comparator comparator){
        return when( (subject) -> comparator.compare(subject, compareTo)==0, () -> result);
    }

    static final Comparator _defaultComparer = Comparators.defaultComparator();
    public static<R, T> When<R> when(T compareTo, Supplier<R> thenCall){
        return when( compareTo, thenCall, _defaultComparer);
    }
    public static<R, T> When<R> when(T compareTo, R result){
        return when( compareTo, () -> result, _defaultComparer);
    }

    public static<R> When<R> defaultTo(Supplier<R> thenCall){
        return new When((_arg) -> true, thenCall);
    }
    public static<R> When<R> defaultTo(R result){
        return new When((_arg) -> true, () -> result);
    }

    public Optional<R> test(Object potentialMatch){
        if( _test.test(potentialMatch) ){
           return Optional.of( _callback.get() );
        }
        return Optional.empty();
    }
}
