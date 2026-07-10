# Nadav Maharer - Unit Test Design and Coverage Conversation

## Conversation record

- Tool: OpenAI Codex
- Model: GPT-5 family coding model
- Date: 2026-07-10
- Context: Part 3 of Home Assignment 2.

## Actual user messages

Nadav first supplied the assignment PDF, the remaining-work plan, and a repository link. The first link pointed to an unrelated Android shift tracker. After Codex reported the mismatch, Nadav provided the corrected repository:

> `Afeka-DevTools/26b-10142-unittesting-nadav_maharer_alice_raines_tomar_lanker.git`

## Actual Codex response during preflight

Codex reported that the corrected repository matched the assignment, pulled the latest `main`, verified a clean tree, and inspected `App.java`, `AppTest.java`, `chats`, `logs`, the empty `README`, and the JaCoCo configuration. The existing tests still covered only Alice's and Tomer's assigned methods: `add`, `isPrime`, `factorial`, and `average`.

Codex then created `nadav/hw2-remaining-work` and limited the test additions to these seven methods:

1. `reverse(String)`
2. `isPalindrome(String)`
3. `fibonacciUpTo(int)`
4. `charFrequency(String)`
5. `isAnagram(String, String)`
6. `filterEvens(List<Integer>)`
7. `mostCommonWord(String)`

## Test-design discussion and resulting decisions

### `reverse`

The tests cover ordinary text, empty and one-character strings, a palindrome, preservation and reversal of spaces/numbers/punctuation, and a null argument. Exact string equality is the strongest clear assertion.

### `isPalindrome`

The production method removes characters outside `[a-zA-Z0-9]`, lowercases the remainder, and compares it with its reverse. Tests therefore cover a normal true and false case, case differences, spaces and punctuation, empty and punctuation-only strings, a single character, and null.

Correction: the tests do not claim that every Unicode letter is preserved. The regular expression explicitly supports only English letters and digits.

### `fibonacciUpTo`

The cases cover negative input, limits 0, 1, and 2, a non-Fibonacci limit of 6, and an exact Fibonacci limit of 8. `assertIterableEquals` verifies both values and order, including the two consecutive `1` values.

Correction: dangerous limits such as `Integer.MAX_VALUE` were rejected. Because the method uses `int`, overflow can corrupt the loop progression and risk a test that does not terminate normally.

### `charFrequency`

The tests compare complete expected maps for repeated letters, case-sensitive keys, spaces, punctuation, digits, and an empty string, plus a null exception test. Complete-map equality catches both missing and unexpected keys.

### `isAnagram`

The production method ignores case and Java regex whitespace but retains punctuation. Tests cover regular matches and mismatches, capitalization, spaces, tabs and line breaks, different counts, different lengths, two empty strings, punctuation differences, and each null argument.

Correction: spaces and capitalization must not be expected to make anagrams unequal. Punctuation can make them unequal because it is not removed.

### `filterEvens`

The cases cover mixed, all-even, all-odd, and empty lists; zero; negative evens; duplicates; original order; input preservation; a null list; and a null element. The input-preservation test copies the original list before the call and compares it afterward.

### `mostCommonWord`

The tests cover a normal sentence with a clear winner, case-insensitive counting, internal punctuation, empty input, whitespace-only input, punctuation-only input, null, and a tie.

Two implementation details required corrections to intuitive assumptions:

- `mostCommonWord("")` returns `""` because Java's split result contains one empty token for that exact input.
- Whitespace-only and punctuation-only inputs produce no tokens and `Collections.max` throws `NoSuchElementException`.

For `"cat dog"`, the test accepts either tied word:

```java
String result = App.mostCommonWord("cat dog");
assertTrue(Set.of("cat", "dog").contains(result));
```

The test does not rely on `HashMap` iteration order. The punctuation test uses internal punctuation so a leading empty token does not distort the intended case.

## Assertion and coverage review

The new section uses descriptive `method_expectedResult_whenCondition` names and meaningful JUnit 5 assertions: `assertEquals`, `assertTrue`, `assertFalse`, `assertThrows`, and `assertIterableEquals`. Alice's and Tomer's test bodies were not edited.

The first sandboxed Gradle run produced `cannot find symbol App` for every test, including all unchanged existing tests. Investigation showed that `App.class` was built correctly, but the restricted Java compiler process could not resolve class files and later exposed a filesystem `AccessDeniedException`. Running the same required command outside that restricted compiler sandbox succeeded, confirming that the problem was environmental rather than a source or test defect.

Final command:

```powershell
.\gradlew.bat clean test
```

Final result:

- 100 tests executed
- 0 failures
- 0 errors
- 0 skipped
- all seven Nadav methods at 100% JaCoCo instruction coverage
- all seven Nadav methods at 100% JaCoCo line coverage
- methods with JaCoCo branch points at 100% branch coverage; methods without branch points reported branch coverage as not applicable

This is full reported line and practical branch coverage for Nadav's assigned methods, not a claim of complete theoretical path coverage.

## Ownership follow-up

Nadav asked why Tomer's `average_handlesLargeValues` test had been deleted. Codex checked the current file and diff and confirmed that the test remains unchanged. The diff removes only the class's former final `}` so Nadav's section can be inserted before it, then adds the final brace after the new section. Tomer's method body is still present immediately above the `Remaining App method tests (Nadav Maharer)` marker.
