package org.junc.collections;

import org.junc.function.Function2;
import org.junc.function.Function3;
import org.junc.function.Function4;
import org.junc.function.Function5;
import org.junc.patterns.Try;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Semantically similar to the Scala 'for' comprehension. Instead of creating a method named 'for', it uses the
 * term 'permutation' to better indicate that all combinations of all stream members will be passed to the
 * generator function. A stream formed from Maybe.stream() can be used as a 'boolean step' in the sequence,
 * making it possible to zero out the permutations when the Maybe is unset.
 */
public class Comprehensions {

    public static<T1, T2, R> Stream<R> permutations(Stream<T1> s1, Stream<T2> s2, Function2<T1, T2, R> generator)
    {
        Function<Object[], R> gen2 = (ar) -> {
            return generator.apply((T1)ar[0], (T2)ar[1]);
        };
        return permutationsCore(gen2, s1, s2);
    }

    public static<T1, T2, T3, R> Stream<R> permutations(Stream<T1> s1, Stream<T2> s2, Stream<T3> s3, Function3<T1, T2, T3, R> generator)
    {
        Function<Object[], R> gen2 = (ar) -> {
            return generator.apply((T1)ar[0], (T2)ar[1], (T3)ar[2]);
        };
        return permutationsCore(gen2, s1, s2, s3);
    }

    public static<T1, T2, T3, T4, R> Stream<R> permutations(Stream<T1> s1, Stream<T2> s2,
                                                            Stream<T3> s3, Stream<T4> s4, Function4<T1, T2, T3, T4, R> generator)
    {
        Function<Object[], R> gen2 = (ar) -> {
            return generator.apply((T1)ar[0], (T2)ar[1], (T3)ar[2], (T4)ar[3]);
        };
        return permutationsCore(gen2, s1, s2, s3, s4);
    }

    public static<T1, T2, T3, T4, T5, R> Stream<R> permutations(Stream<T1> s1, Stream<T2> s2,
                                                            Stream<T3> s3, Stream<T4> s4, Stream<T5> s5, Function5<T1, T2, T3, T4, T5, R> generator)
    {
        Function<Object[], R> gen2 = (ar) -> {
            return generator.apply((T1)ar[0], (T2)ar[1], (T3)ar[2], (T4)ar[3], (T5)ar[4]);
        };
        return permutationsCore(gen2, s1, s2, s3, s4, s5);
    }


    public static<R> Stream<R> permutations(Function<Object[],R> generator, Stream s1, Stream s2, Stream... tail)
    {
        Stream<Stream> ss = StreamExtender.create(RequiredVariadric.create(s1, s2, tail).stream())
            .<Stream>cast()
                .stream();
        LinkedList<LinkedList> allStreams = LinkedList.create(
                ss.map((stream) -> LinkedList.create(stream))
        );
        return permutations(allStreams, generator, LinkedList.create((Object)""));
    }

    static<R> Stream permutationsCore(Function<Object[], R> generator, Stream ... streams){
        return Try.catchAndThrow( () -> {
            LinkedList<LinkedList> allStreams = LinkedList.create(
                    Arrays.stream(streams)
                        .map( (stream) -> LinkedList.create(stream))
            );
            return permutations(allStreams, generator, LinkedList.create((Object)""));
        });
    }

    static<R> Stream permutations(LinkedList<LinkedList> rows, Function<Object[], R> generator, LinkedList items)
    {
        if( rows.size() <= 0  || rows.head() == null )
            return Stream.empty();
        else if( rows.size() == 1 )
            return rows.head().stream(false).map((element) -> {
                LinkedList finalItems = items.insert(element);
                Object[] perm = finalItems.reverse().tail().stream(false).toArray();
                return generator.apply(perm);
            });

        return rows.head()
                .stream(false)
                .flatMap((el) -> {
                    return permutations(rows.tail(), generator, items.insert(el));
                });
    }
}
