package org.idiomot.collections;

import java.util.stream.Stream;

public class StreamExtender<T> {
    Stream<T> _stream;

    private StreamExtender(Stream<T> innerStream){
        _stream = innerStream;
    }

    public static<T> StreamExtender<T> create( Stream<T> innerStream ){
        return new StreamExtender<>(innerStream);
    }

    public<R> StreamExtender<R> cast(){
        return StreamExtender.create(_stream.map( (el) -> (R)el ));
    }

    public Stream<T> stream(){
        return _stream;
    }

}
