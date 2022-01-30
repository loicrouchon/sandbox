# Goal

The goal of this project is to experiment with [JEP 406: Pattern Matching for switch (Preview)](https://openjdk.java.net/jeps/406) by implementing a naive boolean expression `NOT` simplifier.

# The domain problem

A boolean `Expression` is composed of `Variable` combined together using the `NOT`, `AND` and `OR` operators

The `Simplifier` we will implement will aim at pushing the `NOT` all the way to the `Variable`s.
This will be done using the basic boolean algebra laws:

* `NOT(NOT(A))` => `A`
* `NOT(A AND B)` => `(NOT A) OR (NOT B)`
* `NOT(A OR B)` => `(NOT A) AND (NOT B)`

For example, `NOT(A AND (B OR NOT(C))` should be simplified into `NOT(A) AND (NOT(B) AND C)`.

## Source files organization

### Using the visitor design pattern

The file [Expression.java](src/main/java/fr/loicrouchon/visit/Expression.java) file contains the `Expression` interface as well as four implementation records.
It also contains the `accept` methods from the visitor design pattern.

The [ExpressionVisitor.java](src/main/java/fr/loicrouchon/visit/ExpressionVisitor.java) contains the `visit` methods from the visitor design pattern.

The [Simplifier.java](src/main/java/fr/loicrouchon/visit/Simplifier.java) contains the implementation of the naive boolean expression `NOT` simplifier.
It uses two internal visitors, `SimplifierVisitor` and `SimplifierNotVisitor`.

### Using the JEP 406: Pattern Matching for switch

The file [Expression.java](src/main/java/fr/loicrouchon/novisits/Expression.java) file contains the `Expression` interface as well as four implementation records.

The [Simplifier.java](src/main/java/fr/loicrouchon/novisits/Simplifier.java) contains the implementation of the naive boolean expression `NOT` simplifier.

Tests can be found in [SimplifierTest.java](src/test/java/fr/loicrouchon/novisits/SimplifierTest.java).

## Build & run tests

`./mvnw clean test`
