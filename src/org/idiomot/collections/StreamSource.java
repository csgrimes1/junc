package org.idiomot.collections;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface StreamSource<T> {
    public Iterator<T> iterate();
    default Stream<T> stream(boolean parallel){
        return StreamSupport.stream(iterate().spliterator(), parallel);
    }
}
