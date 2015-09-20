package org.idiomot.function;

@FunctionalInterface
public interface NoArgFunctionWithThrows<R> {
    R call() throws Exception;
}
