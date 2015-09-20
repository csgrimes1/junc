package org.idiomot.collections;

import java.util.Optional;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

class SafeSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
    final AtomicReference<Optional<Iterator<T>>> _state;

    SafeSpliterator(Iterator<T> source) {
        super(source.estimatedSize(), source.characterstics());
        _state = new AtomicReference<Optional<Iterator<T>>>();
        _state.set( Optional.of(source) );
    }

    Optional<T> current() {
        final Optional<Iterator<T>> at = _state.getAndUpdate((iter) -> {
            if (iter.isPresent())
                return iter.get().next();
            return Optional.empty();
        });

        if( at.isPresent())
            return Optional.of( at.get().value() );
        return Optional.empty();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action){
        final Optional<T> val = current();
        if( val.isPresent() ){
            action.accept(val.get());
            return true;
        }
        return false;
    }

}
