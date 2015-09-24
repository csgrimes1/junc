package org.junc.function;

@FunctionalInterface
public interface NoArgFunctionWithThrows<R> {
    R call() throws Exception;
}
