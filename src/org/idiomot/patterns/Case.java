package org.idiomot.patterns;

import org.idiomot.collections.RequiredVariadric;

import java.util.Optional;

public class Case {

    public static<R> Optional<R> caseMatch(Object subject, When<R> case1, When<R>... cases)
    {
        RequiredVariadric<When<R>> args = RequiredVariadric.create(case1, cases);
        return args.stream()
                .map((testCase) -> testCase.test(subject))
                .filter((testResult) -> testResult.isPresent() )
                .findFirst()
                .flatMap((r) -> r);
    }
}
