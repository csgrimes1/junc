package org.idiomot.collections;

import java.util.Optional;
import java.util.Spliterator;

public interface Iterator<T> {
    Optional<Iterator<T>> next();
    T value();
    long estimatedSize();
    int characterstics();
    default Spliterator<T> spliterator(){
        return new SafeSpliterator<T>(this);
    }
}

