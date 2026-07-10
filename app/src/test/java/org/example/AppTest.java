package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

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
}