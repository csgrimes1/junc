package org.idiomot.patterns;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.idiomot.patterns.Case.caseMatch;
import static org.idiomot.patterns.When.defaultTo;
import static org.idiomot.patterns.When.when;
import static junit.framework.TestCase.assertEquals;

public class CaseSpec {
    @Test
    public void defaultingCase(){
        final int res = caseMatch(12345,
                when(String.class, () -> 1),
                defaultTo(2)
        ).get();

        assertEquals(2, res);
    }

    @Test
    public void typeMatchingCase(){
        final String res = caseMatch(234,
                when(Integer.class, "int"),
                defaultTo(() -> "")
        ).get();
        assertEquals("int", res);
    }

    @Test
    public void noMatchCase(){
        final Optional<String> res = caseMatch("what?",
                when(Integer.class, () -> "int")
        );
        assertEquals(false, res.isPresent());
    }

    @Test
    public void simpleMatchCase(){
        final String testVal = "test",
            subject = testVal.concat("");
        final int res = caseMatch(subject,
                when((Object)null, 0),
                when(testVal, 1)
        ).get();
        assertEquals(1, res);
    }

    @Test
    public void nullMatchCase(){
        final Object nullRef = null;
        final long res = caseMatch(null,
                when("test", () -> 1L),
                when(nullRef, () -> 2L)
        ).get();
        assertEquals(2L, res);
    }
}
