# Nadav Maharer - Learning About Java Unit Testing

- Tool: OpenAI Codex
- Model: GPT-5 family coding model
- Date: 2026-07-10

**User:** explain how to test this App, and expalin what it means to test it in java

**Codex:** Testing in Java means checking that the methods in the program return the correct result for different inputs. Instead of running the whole program manually, we can write unit tests. A unit test usually checks one method and one expected behavior.

This project uses JUnit 5. Every test has the `@Test` annotation and an assertion that compares the expected behavior with the actual result.

For example:

```java
@Test
void reverse_returnsReversedString_forRegularInput() {
    assertEquals("olleh", App.reverse("hello"));
}
```

`assertEquals` checks an exact result. Other useful assertions are:

- `assertTrue` and `assertFalse` for boolean methods;
- `assertThrows` when invalid input should cause an exception;
- `assertIterableEquals` for lists where values and order matter;
- `assertArrayEquals` for arrays.

For every method in `App`, we should test the normal case, which is also called the happy path. We should also test edge cases such as an empty string, zero, negative numbers, punctuation, duplicate values, an empty list, and `null`.

For example, `fibonacciUpTo(-1)` should throw `IllegalArgumentException`, so it can be tested like this:

```java
assertThrows(IllegalArgumentException.class, () -> App.fibonacciUpTo(-1));
```

It is also important to check that the tests cover the code. Line coverage tells us which lines were executed. Branch coverage tells us whether both results of decisions such as `if` statements and loops were tested. JaCoCo creates the coverage report, but high coverage is only useful when the tests also have meaningful assertions.

The tests can be run from the project folder on Windows with:

```powershell
.\gradlew.bat clean test
```

On Linux or macOS, use:

```bash
./gradlew clean test
```

## What I learned

I learned that testing does not only mean checking that the program runs without crashing. Each test should check a specific expected result.

I also learned the difference between a normal test and an edge-case test. For example, testing `reverse("hello")` is a normal case, while testing an empty string or `null` is an edge case.

I learned how to use different JUnit assertions instead of using only `assertEquals`. I can use `assertTrue` and `assertFalse` for boolean results, and `assertThrows` to make sure the correct exception happens.

Finally, I learned that line and branch coverage can help find code that was not tested, but the assertions still need to verify the correct behavior.
