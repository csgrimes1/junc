# junc - Functional Java

##Background

In functional programming, developers generally adhere to the practice of immutability. The languages
tend to be very expressive, with even flow control elements of the languages returning values as if
they were functions. As a rule, any time your program stops processing your code in a sequential order,
functional programming and imperitive programming offer different solutions. Java's **if**, **switch**,
**while**, and **for** elements create void (no return value) blocks where mutability is legal. In Erlang
and Scala, you would use pattern matching in place of **switch**, and all pattern matching blocks return
a value. Rather than loops, you would use list comprehensions (map/reduce operations) and recursion.
Erlang optimizes for *tail recursion*, allowing functions that end in a call to themselves to avoid
stack overflow.
 
 The language elements and techniques used in functional programming tend to make the
code concise, but the syntax and semantics take time to learn. The rewards for functional programming
are many. Immutability reduces complexity and eliminates side effects. It makes it safe to share
data between threads in parallel processing. It gives you new types of flow control that reduce your
code's verbosity, and to the functional veteran, it increases your code's readability.
 
Java 1.8 introduced a number of functional features to the language. The **Stream** interface makes it
possible to do map/reduce operations. It is now possible to *generate* sequences rather than allocating
finite collections to hold them. As an illustration, *Fibonacci* is a sequence, and it doesn't really
belong in a collection. It should be generated and iterated over simultaneously. This is possible in
Java 1.8!

Sadly, Java leaves out many of the things that would make it fully functional. It lacks pattern matching,
list comprehensions, and immutability. The implementation of Optional does not implement the class as
a 0..1 length stream, a necessity in order to use it idiomatically inside list comprehensions (see *getDinner
example here: [http://nerd.kelseyinnis.com/blog/2013/11/12/idiomatic-scala-the-for-comprehension/](http://nerd.kelseyinnis.com/blog/2013/11/12/idiomatic-scala-the-for-comprehension/)).

This library attempts to build upon 1.8, adding support for immutability, pattern matching, list comprehensions,
and a **Maybe** class to make **Optional** support streaming. It also implements **TailCall**, basically the 
next best thing to real tail recursion. Down deep, it implements an Erlang-style linked list as an immutable
collection type.

##Control of Flow in Traditional Vs. Functional Languages

Functional languages aim to treat all code as an expression. Expressions always evaluate to a result. In common languages
such as Java, control of flow elements such as **if** and **for** create boolean branches and loops, but the blocks of
code are essentially **void**. They don't evaluate to or return a value; rather, they only control the sequence of
instructions. This has some interesting consequences. Let's illustrate with examples:

    %% Erlang
    %% Map a string to a constant

    Operation = case S of
      '+' -> addition;
      '-' -> subtraction;
      '*' -> multiplication;
      '/' -> division;
      _ -> unknown
    end

And in Java:

    int operation = UNKNOWN;
    switch(opText){
    case '+':
        operation = ADDITION;
        break;
    case '-':
        operation = SUBTRACTION;
        break;
    case '*':
        operation = MULTIPLICATION;
        break;
    case '/':
        operation = DIVISION;
        break;
    }

Erlang fits the familiar mathematical pattern 

    Operation = f(S)

... resembling the ever more familiar

    y = f(x)

Java's approach does not neatly fit into a
mathematical expression. Expressed as pseudocode:

    declare variable operation
    (void)f(opText)
        calculate and set value of operation
    end
    variable operation now has a new value
    
When decomposing these chunks of code, Erlang's approach unveils a function inside of a function. The Java code
block decomposes into a pattern that has a resemblance to the use of global variables! Another issue emerges in
the switch block -- mutability. Many would consider this a code smell. You can make **switch** functional with little
effort and without this library. Here's how:

    int calculateOperation(String opText){
        switch(opText){
        case '+':
            return ADDITION;
        case '-':
            return SUBTRACTION;
        case '*':
            return MULTIPLICATION;
        case '/':
            return DIVISION;
        default:
            return UNKNOWN;
        }
    }
    
Very clean! There are limitations, though. You must declare a function or a block lambda to implement this code. Also,
the switch block does not support powerful pattern matching - just straight equality comparisons.

**Void** control of flow idioms are a bit of a missed opportunity. Note that the terniary **?** operation is often
used in return statements, but this is not possible with **switch** or **if** (except when implemented like the last example!). Thankfully,
the introduction of map/fold operations on **Stream** allows replacement of loops with functional transformations. The
*junq* library implements ways to code logical control of flow in a more functional manner.  The *junc* pattern matching
API **Case.caseMatch(...)** enables expressive Boolean logic resulting in a return value. It's a 
superhero version of **switch** !

##Examples

When I went to start coding examples, I went straight to the unit tests for this library. I had a thought: "How do
I illustrate what values are expected from the code?" That was easy to answer - use the unit tests as the examples! This
complemented a tendency toward laziness when it comes to documentation! It's a win/win situation.

The unit tests' source code can be found in test/org/junq.

##Notes

###Performance

Idiomatic programming does not always lead to optimal performance. Immutability can be expensive, for example, but it
leads to reliable and readable code. I tend to err on the side of idiomatic code. The longterm view is that eventually,
compilers will optimize away performance penalties in your clean code. Compilers can never fix complex code when it
proves itself to be technical debt.
