package org.idiomot.collections;

import org.idiomot.patterns.TailCall;
import org.idiomot.patterns.Try;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedList<T> implements StreamSource<T> {
    final LinkedList<T> _next;
    final T _value;
    final int _size;

    private LinkedList(T head, LinkedList<T> tail){
        _value = head;
        _next = tail;
        if( null == tail )
            _size = 1;
        else
            _size = 1 + tail._size;
    }

    private class LinkedListIterator implements Iterator<T>
    {
        @Override
        public Optional<Iterator<T>> next() {
            if( _next == null )
                return Optional.empty();

            return Optional.of( _next.iterate() );
        }

        @Override
        public T value() {
            return _value;
        }

        @Override
        public long estimatedSize() {
            return size();
        }

        @Override
        public int characterstics() {
            return Spliterator.CONCURRENT | Spliterator.IMMUTABLE | Spliterator.SIZED;
        }
    }

    @Override
    public Iterator<T> iterate() {
        return new LinkedListIterator();
    }

    static class StackWindow<T>
    {
        final List<T> _fullStack;
        final int _usedFrames;
        public StackWindow(List<T> col){
            _fullStack = col;
            _usedFrames = col.size();
        }

        private StackWindow(List<T> col, int usedFrames){
            _fullStack = col;
            _usedFrames = usedFrames;
        }

        public T getTop(){ return _fullStack.get(_usedFrames -1); }
        public StackWindow pop(){
            return new StackWindow(_fullStack, _usedFrames-1);
        }
    }

    static<T> TailCall<LinkedList<T>> build(StackWindow<T> window, LinkedList<T> tail){
        if( window._usedFrames <= 0 )
            return TailCall.done(tail);

        final LinkedList<T> newList = (null == tail
                ? new LinkedList<T>(window.getTop(), null)
                : tail.insert(window.getTop())
        );
        return TailCall.scheduleRecursion(() -> build(window.pop(), newList));
    }

    public static<T> LinkedList<T> create(T head, T... members){
        return Try.catchAndThrow( () -> {
            //At least 1 parameter is ensured with this function signature!
            final List<T> args = Stream.concat(Stream.of(head), Arrays.stream(members))
                    .collect(Collectors.toList());
            return TailCall.run(() -> build(new StackWindow<T>(args), null));
        });
    }


    public LinkedList<T> insert(T newHead){
        return new LinkedList<T>(newHead, this);
    }

    public T head() { return _value; }
    public LinkedList<T> tail() { return _next; }
    public int size() { return _size; }

    static <T> LinkedList<T> create(Stream<T> stream) {
        return Try.catchAndThrow( () -> {
            final Object[] ar = stream.toArray();
            if( ar.length <= 0 )
                return null;
            return TailCall.run(() -> build(new StackWindow<T>(Arrays.asList((T[]) ar)), null));
        });
    }

    static<T> TailCall<LinkedList<T>> reverse(LinkedList<T> sourceList, LinkedList<T> reverseList){
        if( null == sourceList ){
            return TailCall.done(reverseList);
        }

        return TailCall.scheduleRecursion( () -> {
            return reverse(sourceList.tail(), reverseList.insert(sourceList.head()));
        });
    }

    public LinkedList<T> reverse() {
        return Try.catchAndThrow( () -> TailCall.run( () -> reverse(tail(), create(head()))));
    }
}
