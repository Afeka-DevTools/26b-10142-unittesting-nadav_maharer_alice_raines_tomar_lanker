package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class AppTest {
    // ===== isPrime tests (Alice Raines) =====
    @Test
    void isPrime_returnsFalse_forNegativeNumbers() {
        assertFalse(App.isPrime(-5));
    }
    @Test
    void isPrime_returnsFalse_forZero() {
        assertFalse(App.isPrime(0));
    }
    @Test
    void isPrime_returnsFalse_forOne() {
        assertFalse(App.isPrime(1));
    }
    @Test
    void isPrime_returnsTrue_forSmallestEvenPrime() {
        assertTrue(App.isPrime(2));
    }
    @Test
    void isPrime_returnsTrue_forSmallestOddPrime() {
        assertTrue(App.isPrime(3));
    }
    @Test
    void isPrime_returnsFalse_forSmallestComposite() {
        assertFalse(App.isPrime(4));
    }
    @Test
    void isPrime_returnsFalse_forPerfectSquareComposite() {
        assertFalse(App.isPrime(9));
    }
    @Test
    void isPrime_returnsTrue_forTypicalPrime() {
        assertTrue(App.isPrime(17));
    }
    @Test
    void isPrime_returnsFalse_forTypicalComposite() {
        assertFalse(App.isPrime(18));
    }
    @Test
    void isPrime_returnsTrue_forLargePrime() {
        assertTrue(App.isPrime(999983));
    }
    @Test
    void isPrime_returnsFalse_forLargeCompositeNumber() {
        assertFalse(App.isPrime(997 * 991));
    }

    // ===== add tests (Alice Raines) =====
    @Test
    void add_returnsSum_forTwoPositiveNumbers() {
        assertEquals(8, App.add(3, 5));
    }
    @Test
    void add_returnsSum_forTwoNegativeNumbers() {
        assertEquals(-8, App.add(-3, -5));
    }
    @Test
    void add_returnsSum_forFirstPositiveSecondNegative() {
        assertEquals(-2, App.add(3, -5));
    }
    @Test
    void add_returnsSum_forFirstNegativeSecondPositive() {
        assertEquals(2, App.add(-3, 5));
    }
    @Test
    void add_returnsSum_forZeroAndPositiveNumber() {
        assertEquals(5, App.add(0, 5));
    }
    @Test
    void add_returnsSum_forZeroAndNegativeNumber() {
        assertEquals(-5, App.add(0, -5));
    }
    @Test
    void add_returnsZero_forBothZero() {
        assertEquals(0, App.add(0, 0));
    }
    @Test
    void add_overflows_forMaxValuePlusOne() {
        assertEquals(Integer.MIN_VALUE, App.add(Integer.MAX_VALUE, 1));
    }
    @Test
    void add_underflows_forMinValuePlusNegativeOne() {
        assertEquals(Integer.MAX_VALUE, App.add(Integer.MIN_VALUE, -1));
    }
    @Test
    void add_returnsNegativeOne_forMaxValuePlusMinValue() {
        assertEquals(-1, App.add(Integer.MAX_VALUE, Integer.MIN_VALUE));
    }

    // ===== factorial tests (Alice Raines) =====
    @Test
    void factorial_returnsOne_forZero() {
        assertEquals(1, App.factorial(0));
    }
    @Test
    void factorial_returnsOne_forOne() {
        assertEquals(1, App.factorial(1));
    }
    @Test
    void factorial_returnsTwo_forSmallestRegularNumber() {
        assertEquals(2, App.factorial(2));
    }
    @Test
    void factorial_calculatesCorrectly_forPositiveNumber() {
        assertEquals(120, App.factorial(5));
    }
    @Test
    void factorial_throwsException_forNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> App.factorial(-5));
    }
    @Test
    void factorial_wrapsToExpectedValue_whenExceedingIntRange() {
        assertEquals(1932053504, App.factorial(13));
    }
    @Test
    void factorial_doesNotHangOrCrash_forLargeIterationCount() {
        assertDoesNotThrow(() -> App.factorial(10000));
    }

    // ===== average tests (Alice Raines) =====
    @Test
    void average_throwsException_forEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> App.average(new int[]{}));
    }
    @Test
    void average_throwsNullPointerException_forNullArray() {
        assertThrows(NullPointerException.class, () -> App.average(null));
    }
    @Test
    void average_returnsSameValue_forArrayWithOnePositiveNumber() {
        assertEquals(5.0, App.average(new int[]{5}), 0.0001);
    }
    @Test
    void average_returnsSameValue_forArrayWithOneNegativeNumber() {
        assertEquals(-5.0, App.average(new int[]{-5}), 0.0001);
    }
    @Test
    void average_returnsZero_forArrayWithOneZero() {
        assertEquals(0.0, App.average(new int[]{0}), 0.0001);
    }
    @Test
    void average_returnsCorrectAverage_forAllPositiveNumbers() {
        assertEquals(4.0, App.average(new int[]{2, 4, 6}), 0.0001);
    }
    @Test
    void average_returnsCorrectAverage_forAllNegativeNumbers() {
        assertEquals(-4.0, App.average(new int[]{-2, -4, -6}), 0.0001);
    }
    @Test
    void average_returnsCorrectAverage_forMixOfPositiveAndNegative() {
        assertEquals(2.0, App.average(new int[]{-3, 3, 6}), 0.0001);
    }
    @Test
    void average_returnsZero_forMultipleZeros() {
        assertEquals(0.0, App.average(new int[]{0, 0, 0}), 0.0001);
    }
    @Test
    void average_returnsCorrectAverage_forMixOfPositiveNegativeAndZeros() {
        assertEquals(1.6, App.average(new int[]{-2, 0, 4, 0, 6}), 0.0001);
    }
    @Test
    void average_returnsPreciseDecimal_forNonTerminatingResult() {
        assertEquals(2.3333333, App.average(new int[]{1, 2, 4}), 0.0001);
    }
    @Test
    void average_returnsPreciseResult_forLargeArrayWithUnevenDivision() {
        int[] largeArray = new int[10000];
        Arrays.fill(largeArray, 1);
        largeArray[0] = 2;
        assertEquals(1.0001, App.average(largeArray), 0.0001);
    }

    // ===== Additional tests (Tomer Lanker) =====
    @Test
    void average_doesNotModifyInputArray() {
        int[] arr = new int[]{1, 2, 3};
        int[] copy = arr.clone();
        double ignored = App.average(arr);
        assertArrayEquals(copy, arr);
    }

    @Test
    void add_isCommutative() {
        assertAll("commutative",
            () -> assertEquals(App.add(3, 5), App.add(5, 3)),
            () -> assertEquals(App.add(-7, 2), App.add(2, -7))
        );
    }

    @Test
    void factorial_throwsExactlyForNegative_withMeaningfulMessage() {
        IllegalArgumentException ex = assertThrowsExactly(IllegalArgumentException.class, () -> App.factorial(-1));
        assertNotNull(ex.getMessage());
        String msg = ex.getMessage().toLowerCase();
        assertTrue(msg.contains("negative") || msg.contains("non") || msg.contains(">=0") || msg.contains("non-negative"),
            "Exception message should indicate the non-negative requirement");
    }

    @Test
    void average_handlesLargeValues() {
        int[] arr = new int[1000];
        Arrays.fill(arr, Integer.MAX_VALUE);
        double avg = App.average(arr);
        assertEquals((double) Integer.MAX_VALUE, avg, 0.1);
    }

    // ===== Remaining App method tests by Nadav Maharer =====

    @Test
    void reverse_returnsReversedString_forRegularInput() {
        assertEquals("olleh", App.reverse("hello"));
    }

    @Test
    void reverse_returnsEmptyString_forEmptyInput() {
        assertEquals("", App.reverse(""));
    }

    @Test
    void reverse_returnsSameCharacter_forSingleCharacterInput() {
        assertEquals("a", App.reverse("a"));
    }

    @Test
    void reverse_returnsSameString_forPalindromeInput() {
        assertEquals("level", App.reverse("level"));
    }

    @Test
    void reverse_reversesEveryCharacter_forSpacesNumbersAndPunctuation() {
        assertEquals("!21 ba", App.reverse("ab 12!"));
    }

    @Test
    void reverse_throwsNullPointerException_forNullInput() {
        assertThrows(NullPointerException.class, () -> App.reverse(null));
    }

    @Test
    void isPalindrome_returnsTrue_forTypicalPalindrome() {
        assertTrue(App.isPalindrome("level"));
    }

    @Test
    void isPalindrome_returnsFalse_forTypicalNonPalindrome() {
        assertFalse(App.isPalindrome("hello"));
    }

    @Test
    void isPalindrome_returnsTrue_whenInputDiffersOnlyByCase() {
        assertTrue(App.isPalindrome("RaceCar"));
    }

    @Test
    void isPalindrome_returnsTrue_whenInputContainsSpacesAndPunctuation() {
        assertTrue(App.isPalindrome("A man, a plan, a canal: Panama!"));
    }

    @Test
    void isPalindrome_returnsTrue_forEmptyInput() {
        assertTrue(App.isPalindrome(""));
    }

    @Test
    void isPalindrome_returnsTrue_forPunctuationOnlyInput() {
        assertTrue(App.isPalindrome("!?.,"));
    }

    @Test
    void isPalindrome_returnsTrue_forSingleCharacterInput() {
        assertTrue(App.isPalindrome("x"));
    }

    @Test
    void isPalindrome_throwsNullPointerException_forNullInput() {
        assertThrows(NullPointerException.class, () -> App.isPalindrome(null));
    }

    @Test
    void fibonacciUpTo_throwsIllegalArgumentException_forNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> App.fibonacciUpTo(-1));
    }

    @Test
    void fibonacciUpTo_returnsZero_forZeroLimit() {
        assertIterableEquals(List.of(0), App.fibonacciUpTo(0));
    }

    @Test
    void fibonacciUpTo_includesBothOnes_forOneLimit() {
        assertIterableEquals(List.of(0, 1, 1), App.fibonacciUpTo(1));
    }

    @Test
    void fibonacciUpTo_includesTwo_forTwoLimit() {
        assertIterableEquals(List.of(0, 1, 1, 2), App.fibonacciUpTo(2));
    }

    @Test
    void fibonacciUpTo_stopsBeforeNonFibonacciLimit() {
        assertIterableEquals(List.of(0, 1, 1, 2, 3, 5), App.fibonacciUpTo(6));
    }

    @Test
    void fibonacciUpTo_includesExactFibonacciLimit_inFibonacciOrder() {
        assertIterableEquals(List.of(0, 1, 1, 2, 3, 5, 8), App.fibonacciUpTo(8));
    }

    @Test
    void charFrequency_returnsCompleteCounts_forRepeatedLetters() {
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('b', 1);
        expected.put('a', 3);
        expected.put('n', 2);

        assertEquals(expected, App.charFrequency("banana"));
    }

    @Test
    void charFrequency_countsUpperAndLowerCaseSeparately() {
        assertEquals(Map.of('A', 1, 'a', 1), App.charFrequency("Aa"));
    }

    @Test
    void charFrequency_countsSpaces() {
        assertEquals(Map.of('a', 1, ' ', 2), App.charFrequency("a  "));
    }

    @Test
    void charFrequency_countsPunctuation() {
        assertEquals(Map.of('!', 2, '?', 1), App.charFrequency("!!?"));
    }

    @Test
    void charFrequency_countsDigits() {
        assertEquals(Map.of('1', 2, '2', 1), App.charFrequency("121"));
    }

    @Test
    void charFrequency_returnsEmptyMap_forEmptyInput() {
        assertEquals(Map.of(), App.charFrequency(""));
    }

    @Test
    void charFrequency_throwsNullPointerException_forNullInput() {
        assertThrows(NullPointerException.class, () -> App.charFrequency(null));
    }

    @Test
    void isAnagram_returnsTrue_forRegularAnagrams() {
        assertTrue(App.isAnagram("listen", "silent"));
    }

    @Test
    void isAnagram_returnsFalse_forNonAnagrams() {
        assertFalse(App.isAnagram("hello", "world"));
    }

    @Test
    void isAnagram_returnsTrue_whenCapitalizationDiffers() {
        assertTrue(App.isAnagram("Listen", "SILENT"));
    }

    @Test
    void isAnagram_returnsTrue_whenSpacesDiffer() {
        assertTrue(App.isAnagram("Dormitory", "Dirty room"));
    }

    @Test
    void isAnagram_returnsTrue_whenTabsAndLineBreaksDiffer() {
        assertTrue(App.isAnagram("the\teyes", "they\nsee"));
    }

    @Test
    void isAnagram_returnsFalse_whenCharacterCountsDiffer() {
        assertFalse(App.isAnagram("aab", "abb"));
    }

    @Test
    void isAnagram_returnsFalse_whenLengthsDiffer() {
        assertFalse(App.isAnagram("abc", "abcd"));
    }

    @Test
    void isAnagram_returnsTrue_forTwoEmptyStrings() {
        assertTrue(App.isAnagram("", ""));
    }

    @Test
    void isAnagram_returnsFalse_whenPunctuationDiffers() {
        assertFalse(App.isAnagram("conversation!", "voices rant on"));
    }

    @Test
    void isAnagram_throwsNullPointerException_whenFirstInputIsNull() {
        assertThrows(NullPointerException.class, () -> App.isAnagram(null, "abc"));
    }

    @Test
    void isAnagram_throwsNullPointerException_whenSecondInputIsNull() {
        assertThrows(NullPointerException.class, () -> App.isAnagram("abc", null));
    }

    @Test
    void filterEvens_returnsOnlyEvenNumbers_forMixedInput() {
        assertIterableEquals(List.of(2, 4), App.filterEvens(List.of(1, 2, 3, 4)));
    }

    @Test
    void filterEvens_returnsAllValues_forAllEvenInput() {
        assertIterableEquals(List.of(2, 4, 6), App.filterEvens(List.of(2, 4, 6)));
    }

    @Test
    void filterEvens_returnsEmptyList_forAllOddInput() {
        assertIterableEquals(List.of(), App.filterEvens(List.of(1, 3, 5)));
    }

    @Test
    void filterEvens_returnsEmptyList_forEmptyInput() {
        assertIterableEquals(List.of(), App.filterEvens(List.of()));
    }

    @Test
    void filterEvens_includesZero() {
        assertIterableEquals(List.of(0), App.filterEvens(List.of(0, 1)));
    }

    @Test
    void filterEvens_includesNegativeEvenNumbers() {
        assertIterableEquals(List.of(-4, -2), App.filterEvens(List.of(-4, -3, -2, -1)));
    }

    @Test
    void filterEvens_preservesDuplicatesAndOriginalOrder() {
        assertIterableEquals(List.of(-4, 0, 2, 2), App.filterEvens(List.of(-4, -3, 0, 2, 2)));
    }

    @Test
    void filterEvens_doesNotModifyInputList() {
        List<Integer> input = new java.util.ArrayList<>(List.of(1, 2, 3, 4));
        List<Integer> original = List.copyOf(input);

        App.filterEvens(input);

        assertIterableEquals(original, input);
    }

    @Test
    void filterEvens_throwsNullPointerException_forNullList() {
        assertThrows(NullPointerException.class, () -> App.filterEvens(null));
    }

    @Test
    void filterEvens_throwsNullPointerException_forNullElement() {
        List<Integer> input = new java.util.ArrayList<>();
        input.add(2);
        input.add(null);

        assertThrows(NullPointerException.class, () -> App.filterEvens(input));
    }

    @Test
    void mostCommonWord_returnsClearWinner_forNormalSentence() {
        assertEquals("fish", App.mostCommonWord("One fish, two fish; red fish."));
    }

    @Test
    void mostCommonWord_countsWordsCaseInsensitively() {
        assertEquals("apple", App.mostCommonWord("Apple banana APPLE"));
    }

    @Test
    void mostCommonWord_treatsInternalPunctuationAsSeparator() {
        assertEquals("red", App.mostCommonWord("red-blue red"));
    }

    @Test
    void mostCommonWord_returnsEmptyString_forEmptyInput() {
        assertEquals("", App.mostCommonWord(""));
    }

    @Test
    void mostCommonWord_throwsNoSuchElementException_forWhitespaceOnlyInput() {
        assertThrows(NoSuchElementException.class, () -> App.mostCommonWord("   \t\n"));
    }

    @Test
    void mostCommonWord_throwsNoSuchElementException_forPunctuationOnlyInput() {
        assertThrows(NoSuchElementException.class, () -> App.mostCommonWord("!?.,"));
    }

    @Test
    void mostCommonWord_throwsNullPointerException_forNullInput() {
        assertThrows(NullPointerException.class, () -> App.mostCommonWord(null));
    }

    @Test
    void mostCommonWord_returnsEitherWinner_whenWordsAreTied() {
        String result = App.mostCommonWord("cat dog");

        assertTrue(Set.of("cat", "dog").contains(result));
    }
}
