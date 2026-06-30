package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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



}