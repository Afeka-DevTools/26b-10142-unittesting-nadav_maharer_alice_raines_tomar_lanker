# Alice's Claude logs for the learning:

---

# Explain what unit testing is in Java and why it's important, with a simple example.

---

## Unit Testing in Java

Unit testing means testing individual pieces of code — usually a single method or class — in isolation, to verify it behaves correctly. The most popular framework for this in Java is **JUnit**.

### Why it matters

- **Catches bugs early** — you find problems when you write the code, not weeks later in production.
- **Enables safe refactoring** — if you change code and all tests still pass, you can be confident you didn't break anything.
- **Acts as documentation** — tests show how a method is supposed to be used.
- **Saves time long-term** — automated tests run in seconds, versus manually re-checking behavior by hand every time.
- **Supports CI/CD** — tests can run automatically on every commit, blocking broken code from being merged or deployed.

### A simple example

Suppose you have a class that adds two numbers:

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

Here's a JUnit 5 test for it:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void testAdd() {
        Calculator calc = new Calculator();
        int result = calc.add(2, 3);
        assertEquals(5, result, "2 + 3 should equal 5");
    }

    @Test
    void testAddNegativeNumbers() {
        Calculator calc = new Calculator();
        int result = calc.add(-1, -1);
        assertEquals(-2, result, "-1 + -1 should equal -2");
    }
}
```

### What's happening here

- `@Test` marks a method as a test case that JUnit should run.
- `assertEquals(expected, actual, message)` checks that the actual result matches what you expected; if not, the test fails and prints the message.
- Each test is independent — `testAdd` and `testAddNegativeNumbers` don't depend on each other or on shared state, which is good practice (tests should be isolated and repeatable).

When you run this with a build tool like Maven or Gradle (`mvn test` or `gradle test`), JUnit executes every method marked `@Test` and reports which passed or failed — giving you fast, automated feedback every time you change your code.

---

# What's the difference between `assertEquals`, `assertTrue`, `assertFalse`, `assertNotNull`, and `assertThrows`? Give an example of when to use each.

---

## JUnit Assertion Methods

Each assertion checks a different kind of condition. Picking the right one makes test failures clearer, since the failure message tells you exactly what kind of check failed.

### `assertEquals(expected, actual)`
Checks that two values are equal. Use it when you know the exact expected output.

```java
@Test
void testAdd() {
    Calculator calc = new Calculator();
    assertEquals(5, calc.add(2, 3));
}
```

### `assertTrue(condition)`
Checks that a boolean expression is `true`. Use it when you're testing a condition rather than comparing two specific values.

```java
@Test
void testIsEven() {
    NumberUtils utils = new NumberUtils();
    assertTrue(utils.isEven(4));
}
```

### `assertFalse(condition)`
The opposite of `assertTrue` — checks that an expression is `false`.

```java
@Test
void testIsEvenWithOddNumber() {
    NumberUtils utils = new NumberUtils();
    assertFalse(utils.isEven(3));
}
```

### `assertNotNull(object)`
Checks that a value isn't `null`. Use it when you care that something was created or returned, without checking its exact contents.

```java
@Test
void testCreateUser() {
    UserService service = new UserService();
    User user = service.createUser("Alice");
    assertNotNull(user);
}
```

There's also `assertNull(object)` for the opposite case — confirming something *should* be `null`.

### `assertThrows(ExceptionType.class, executable)`
Checks that a specific exception is thrown when running some code. Use it when invalid input or an error condition should cause your method to fail loudly rather than silently.

```java
@Test
void testDivideByZeroThrowsException() {
    Calculator calc = new Calculator();
    assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
}
```

This passes only if `calc.divide(10, 0)` actually throws an `ArithmeticException`. If it throws nothing, or throws a different exception type, the test fails.

### Quick summary

| Method | Use case |
|---|---|
| `assertEquals` | Comparing an actual result to a known expected value |
| `assertTrue` / `assertFalse` | Checking a boolean condition |
| `assertNotNull` / `assertNull` | Checking presence or absence of a value |
| `assertThrows` | Checking that bad input/state correctly triggers an exception |

A good rule of thumb: use the assertion that most precisely describes *what* you're checking — it makes test code self-documenting and gives you a more useful error message when something breaks.

---

# What are edge cases in unit testing, and how do I figure out which ones matter for a given function?

---

## Edge Cases in Unit Testing

An edge case is an input or condition at the extreme boundary of what a function accepts — situations that are valid (or invalid) but unusual enough that buggy code often handles them wrong, even when it handles "normal" inputs fine.

The danger isn't the typical input (`add(2, 3)`) — it's the boundary: zero, negative numbers, empty collections, nulls, maximum/minimum values, and so on. Bugs love to hide there.

### How to find the edge cases that matter

Rather than guessing randomly, work through these angles systematically for a given function:

**1. Look at the input types and their natural boundaries**
- Numbers: `0`, negative numbers, `Integer.MAX_VALUE`/`MIN_VALUE`, very large/small values
- Strings: empty string `""`, `null`, whitespace-only, very long strings, special characters
- Collections: empty list, single-element list, list with duplicates, `null`
- Dates: leap years, end of month, midnight, time zone boundaries

**2. Look at the function's logic for branch points**
Read the method and ask: *where does behavior change?* Every `if`, loop boundary, or division is a candidate.

```java
public int divide(int a, int b) {
    return a / b;
}
```
Here the obvious branch point is `b == 0` — division by zero throws `ArithmeticException`. That's an edge case the "normal" test (`divide(10, 2)`) never exercises.

**3. Think about invalid/unexpected input, not just valid extremes**
- `null` arguments
- Wrong types if using generics or Object
- Input that violates an implicit assumption (e.g., a "positive number only" function called with `-5`)

**4. Consider the domain/business rules**
If a function processes ages, what about `age = 0` or `age = -1`? If it processes a percentage, what about `150%`? Domain knowledge tells you which boundaries are meaningful.

### Example: edge cases for `divide(int a, int b)`

| Case | Why it matters |
|---|---|
| `divide(10, 0)` | Division by zero — should throw, not crash silently or return garbage |
| `divide(0, 5)` | Zero as numerator — valid, should just return 0 |
| `divide(-10, 2)` | Negative numbers — sign handling |
| `divide(Integer.MIN_VALUE, -1)` | Overflow — this specific case actually overflows in Java! |
| `divide(7, 2)` | Integer division truncation — does the caller expect `3`, not `3.5`? |

```java
@Test
void testDivideByZeroThrows() {
    Calculator calc = new Calculator();
    assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
}

@Test
void testDivideTruncatesTowardZero() {
    Calculator calc = new Calculator();
    assertEquals(3, calc.divide(7, 2)); // not 3.5, not 4
}
```

### A practical checklist to run through for any function

1. What's the smallest/largest valid input?
2. What happens at zero?
3. What happens with negative values, if applicable?
4. Can any argument be `null` or empty?
5. Where are the `if`/`else` branches, and what input triggers each one?
6. What does the function's documentation/contract promise — and is there an input that would break that promise?

You don't need to test *every* theoretical edge case — focus on the ones that are plausible given how the function will actually be called, and the ones where getting it wrong would actually cause a real problem.

---

# What's the difference between testing a 'happy path' and testing edge cases?

---

## Happy Path vs. Edge Cases

### Happy path
The happy path is the normal, expected flow through a function — typical, valid input, used the way it's meant to be used, with no errors or unusual conditions. It answers: *"Does this work when everything goes right?"*

```java
@Test
void testAdd_happyPath() {
    Calculator calc = new Calculator();
    assertEquals(5, calc.add(2, 3));
}
```

Nothing tricky here — just normal numbers, normal usage.

### Edge cases
Edge cases test what happens at the boundaries, with unusual input, or when things could go wrong. They answer: *"Does this still work — or fail gracefully — when conditions aren't typical?"*

```java
@Test
void testAdd_edgeCase_overflow() {
    Calculator calc = new Calculator();
    assertEquals(Integer.MIN_VALUE, calc.add(Integer.MAX_VALUE, 1)); // overflow wraps around
}
```

### Why you need both

A function can pass every happy-path test and still be broken. Happy-path tests confirm the core logic is correct; edge-case tests confirm the function is *robust* — it doesn't crash, corrupt data, or silently misbehave when something unusual happens.

Think of it like testing a bridge: the happy path checks that a normal car can drive across. Edge cases check what happens with a fully loaded truck, in high wind, or if someone drives across at 2am when no one's watching. You wouldn't call the bridge "tested" if you only ever drove a sedan across it in good weather.

### Side-by-side example

For a method that withdraws money from a bank account:

```java
public void withdraw(double amount) {
    if (amount > balance) {
        throw new IllegalStateException("Insufficient funds");
    }
    balance -= amount;
}
```

| Type | Test | What it checks |
|---|---|---|
| Happy path | `withdraw(50)` from a balance of `100` | Normal withdrawal works correctly |
| Edge case | `withdraw(100)` from a balance of `100` | Withdrawing the *exact* balance (boundary: `amount == balance`) |
| Edge case | `withdraw(150)` from a balance of `100` | Should throw `IllegalStateException` |
| Edge case | `withdraw(-50)` | Negative withdrawal — does the function guard against this, or does it accidentally *add* money? |
| Edge case | `withdraw(0)` | Zero withdrawal — should it succeed silently or be rejected? |

### Practical takeaway

A well-tested function usually has:
- **One or a few happy-path tests** — proving the core logic works as designed.
- **Several edge-case tests** — proving the function doesn't break, or fails in a controlled, intentional way, when conditions aren't ideal.

If you only write happy-path tests, your test suite gives you false confidence: it'll pass even if the function mishandles zero, negative numbers, nulls, or boundary values — which is often exactly where real bugs show up in production.

---

# What does code coverage mean, and how do I check if my tests cover all the paths/branches in a function?

---

## Code Coverage

Code coverage measures how much of your source code gets executed when your test suite runs. It's reported as a percentage and comes in a few different flavors, each measuring a different thing.

### Types of coverage

**1. Line coverage** — what percentage of lines were executed at least once.

**2. Branch coverage** — what percentage of decision branches (`if`/`else`, `&&`, `||`, ternaries, loop conditions) were exercised in *both* directions. This is stricter than line coverage and usually more meaningful.

**3. Path coverage** — what percentage of all possible execution paths through the code were tested. This grows combinatorially with the number of branches, so it's rarely tracked directly — branch coverage is the practical stand-in.

### Why line coverage alone can be misleading

```java
public String checkAge(int age) {
    if (age >= 18) {
        return "adult";
    } else {
        return "minor";
    }
}
```

```java
@Test
void testCheckAge() {
    assertEquals("adult", checkAge(25));
}
```

This single test gives you high **line coverage** — most lines executed — but the `else` branch never ran. You'd have only 50% **branch coverage**, and a bug in the "minor" path would go completely undetected. This is the core reason branch coverage matters more than line coverage: a function can look "covered" while half its logic is never verified.

### How to check coverage in Java

The standard tool is **JaCoCo** (Java Code Coverage). It integrates with Maven or Gradle.

**Maven setup** (`pom.xml`):
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals><goal>prepare-agent</goal></goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals><goal>report</goal></goals>
        </execution>
    </executions>
</plugin>
```

Run:
```
mvn test
```

JaCoCo generates an HTML report at `target/site/jacoco/index.html`. Open it in a browser — it highlights your source code:
- **Green** lines/branches = covered
- **Red** = never executed
- **Yellow** = partially covered (e.g., an `if` where only the `true` branch was tested)

**Gradle setup** (`build.gradle`):
```groovy
plugins {
    id 'jacoco'
}

test {
    finalizedBy jacocoTestReport
}

jacocoTestReport {
    reports {
        html.required = true
    }
}
```

Run:
```
gradle test jacocoTestReport
```

Report appears at `build/reports/jacoco/test/html/index.html`.

### How to use the report to find untested branches

1. Open the HTML report and click into a class.
2. Look for **yellow or red** highlighted lines — these are partially or fully uncovered.
3. For each yellow line (usually an `if`/`else`, `switch`, or loop), ask: *which direction wasn't tested?*
4. Write a test that forces execution down that missing path.

Going back to the example: if `checkAge` shows yellow on the `if`, you'd add:

```java
@Test
void testCheckAge_minor() {
    assertEquals("minor", checkAge(10));
}
```

Now both branches are green.

### A few cautions about coverage

- **High coverage ≠ correct code.** You can hit 100% coverage with tests that have no real assertions (`calc.add(2,3);` with no `assertEquals`) — the line ran, but nothing was actually verified.
- **100% coverage is rarely the goal.** Many teams target 70–90% and focus effort on critical/complex logic rather than chasing every defensive `null` check or trivial getter.
- **Coverage tells you what's untested, not what's correct.** Use it to *find gaps*, then think critically (using the edge-case approach from before) about what test would actually catch a bug in that gap — don't just write a test to turn the line green.

### Quick mental model

| Metric | Question it answers |
|---|---|
| Line coverage | Did this line run at all? |
| Branch coverage | Did *every* decision outcome get exercised? |
| Path coverage | Did every combination of branches get exercised? (rarely tracked directly) |

In practice: aim for high **branch coverage**, use JaCoCo's report to spot yellow/red highlights, and write a targeted test for each missing branch — pairing this with the edge-case checklist we discussed earlier (zero, negative, null, boundary values) usually gets you very good real-world coverage.

---

# How do I run JUnit tests in a Gradle project from the terminal?

---

## Running JUnit Tests with Gradle

### Basic command

From your project's root directory (where `build.gradle` or `build.gradle.kts` lives):

```bash
./gradlew test
```

Use `./gradlew` (the Gradle wrapper) rather than a globally installed `gradle` — it ensures everyone on the project uses the same Gradle version. On Windows, use `gradlew.bat test` or `gradlew test` depending on your shell.

### Prerequisites

Make sure your `build.gradle` has JUnit set up and is configured to use the JUnit Platform:

```groovy
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
}

test {
    useJUnitPlatform()
}
```

Without `useJUnitPlatform()`, Gradle won't recognize JUnit 5 (`@Test` from `org.junit.jupiter.api`) and your tests will silently not run.

### Useful variations

**Run a specific test class:**
```bash
./gradlew test --tests "com.example.CalculatorTest"
```

**Run a specific test method:**
```bash
./gradlew test --tests "com.example.CalculatorTest.testAdd"
```

**Run tests matching a pattern (wildcards):**
```bash
./gradlew test --tests "*CalculatorTest"
```

**Re-run even if Gradle thinks nothing changed** (Gradle caches results and skips tests it considers "up to date"):
```bash
./gradlew test --rerun
```

**See more output while tests run** (pass/fail per test, not just a summary):
```bash
./gradlew test --info
```
or for full stack traces on failure:
```bash
./gradlew test --stacktrace
```

### Viewing results

After running, Gradle generates:
- An **HTML report**: `build/reports/tests/test/index.html` — open this in a browser for a readable pass/fail breakdown per class and method.
- **XML results** (used by CI tools): `build/test-results/test/`

### Quick example workflow

```bash
./gradlew test                                    # run everything
./gradlew test --tests "*.CalculatorTest"          # run one class
open build/reports/tests/test/index.html           # view results (macOS)
```

On Linux you'd use `xdg-open` instead of `open`; on Windows, just navigate to the file in File Explorer.

---

# Can you give me a checklist I can use to make sure I've tested a function thoroughly?

---

## Thorough Function Testing Checklist

### 1. Understand the contract first
- [ ] What inputs are valid? What's explicitly invalid?
- [ ] What's the documented/expected behavior for each?
- [ ] What should happen on bad input — throw, return null, return a default?

### 2. Happy path
- [ ] At least one test with typical, normal, valid input
- [ ] If the function has multiple "normal" use cases, cover the main ones

### 3. Boundary values
- [ ] Smallest valid input
- [ ] Largest valid input
- [ ] Zero (if numeric)
- [ ] Negative numbers (if numeric and not restricted to positive)
- [ ] Just inside and just outside any threshold (e.g., `amount == balance`, `amount == balance + 1`)

### 4. Empty / absent input
- [ ] `null` arguments (each parameter, if applicable)
- [ ] Empty string `""`
- [ ] Empty collection/array
- [ ] Whitespace-only string, if relevant

### 5. Invalid / unexpected input
- [ ] Input that violates assumptions (e.g., negative value to a "positive only" function)
- [ ] Wrong format (e.g., malformed date string, invalid email)
- [ ] Out-of-range values

### 6. Every branch and condition
- [ ] Every `if`/`else` — both directions tested
- [ ] Every `switch` case, including `default`
- [ ] Every loop — zero iterations, one iteration, many iterations
- [ ] Compound conditions (`&&`, `||`) — test combinations, not just the overall result

### 7. Exceptions and error handling
- [ ] Each documented exception is actually thrown under the right condition (`assertThrows`)
- [ ] The exception type and message (if relevant) are correct
- [ ] No exception is thrown where one shouldn't be

### 8. Side effects and state
- [ ] If the function mutates state (a field, a collection, a file), verify the state *after* the call, not just the return value
- [ ] If the function is supposed to be idempotent, test calling it twice
- [ ] If order of operations matters, test calling things in different sequences

### 9. Special domain cases
- [ ] Numeric overflow/underflow (`Integer.MAX_VALUE`, `Integer.MIN_VALUE`)
- [ ] Floating-point precision issues, if doubles/floats are involved
- [ ] Dates: leap years, month/year boundaries, time zones
- [ ] Duplicates, if working with collections

### 10. Sanity-check the tests themselves
- [ ] Each test actually has an assertion (not just calling the method with no check)
- [ ] Each test is independent — doesn't rely on another test running first
- [ ] Test names clearly describe what's being verified (e.g., `testWithdraw_exactBalance_succeeds`)
- [ ] Run code coverage (JaCoCo) and check for yellow/red branches you missed

### Quick mental pass for any function
Ask yourself:
1. Does it work for normal input? *(happy path)*
2. Does it work at the edges? *(boundaries)*
3. Does it handle nothing/missing input gracefully? *(null/empty)*
4. Does it reject bad input the way it's supposed to? *(invalid input/exceptions)*
5. Did I exercise every decision point in the code? *(branch coverage)*

If you can answer "yes, and I have a test for it" to all five, you're in good shape.

---

# Nadav Maharer - Java Unit Testing Self-Study

- Tool: OpenAI Codex
- Model: GPT-5 family coding model
- Date: 2026-07-10

**User:** explain how to test this App, and expalin what it means to test it in java

**Codex:** Testing in Java means checking that the methods in the program return the correct result for different inputs. Instead of running the whole program manually, we can write unit tests. A unit test usually checks one method and one expected behavior.

This project uses JUnit 5. Every test has the `@Test` annotation and an assertion that compares the expected behavior with the actual result.

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

For example, `fibonacciUpTo(-1)` should throw `IllegalArgumentException`:

```java
assertThrows(IllegalArgumentException.class, () -> App.fibonacciUpTo(-1));
```

Line coverage tells us which lines were executed. Branch coverage tells us whether both results of decisions such as `if` statements and loops were tested. JaCoCo creates the coverage report, but high coverage is only useful when the tests also have meaningful assertions.

Run the tests on Windows with:

```powershell
.\gradlew.bat clean test
```

On Linux or macOS:

```bash
./gradlew clean test
```

## What I learned

I learned that testing does not only mean checking that the program runs without crashing. Each test should check a specific expected result.

I also learned the difference between a normal test and an edge-case test. For example, testing `reverse("hello")` is a normal case, while testing an empty string or `null` is an edge case.

I learned how to use different JUnit assertions instead of using only `assertEquals`. I can use `assertTrue` and `assertFalse` for boolean results, and `assertThrows` to make sure the correct exception happens.

Finally, I learned that line and branch coverage can help find code that was not tested, but the assertions still need to verify the correct behavior.
