package br.nom.figueiredo.sergio.math;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RationalTest {

    @org.junit.jupiter.api.Test
    void add() {
        // caso #1
        Rational f1 = Rational.of(100L, 3L);
        Rational f2 = Rational.of(50L, 2L);
        assertEquals(Rational.of((100*6L/3)+(50*6L/2),6L),
                f1.add(f2));

        // caso #2
        f1 = Rational.of(100L, 3L);
        f2 = Rational.of(50L, 20L);
        assertEquals(Rational.of((100*60L/3)+(50*60L/20),60L),
                f1.add(f2));

        // caso #3
        f1 = Rational.of(100L, 33L);
        f2 = Rational.of(50L, 2L);
        assertEquals(Rational.of((100*66L/33)+(50*66L/2),66L),
                f1.add(f2));

        // caso #4
        f1 = Rational.of(100L, 23L);
        f2 = Rational.of(50L, 23L);
        assertEquals(Rational.of(150L,23L),
                f1.add(f2));

    }

    @org.junit.jupiter.api.Test
    void multiply() {
        Rational f1 = Rational.of(1L, 3L);
        Rational f2 = Rational.of(5L, 2L);
        assertEquals(Rational.of(5L,6L),
                f1.multiply(f2));
    }

    @org.junit.jupiter.api.Test
    void simplify() {
        Rational f1 = Rational.of(11L, 22L);
        assertEquals(Rational.of(1L,2L),
                f1.simplify());

        f1 = Rational.of(45L, 5L);
        assertEquals(Rational.of(9L,1L),
                f1.simplify());

        f1 = Rational.of(47L, 3L);
        assertEquals(Rational.of(47L,3L),
                f1.simplify());
    }

    @org.junit.jupiter.api.Test
    void greatestCommonFactor() {
        BigInteger gcf = Rational.gcf(BigInteger.valueOf(8L), BigInteger.valueOf(24));
        assertEquals(BigInteger.valueOf(8), gcf);

        gcf = Rational.gcf(BigInteger.valueOf(33),BigInteger.valueOf(5));
        assertEquals(BigInteger.valueOf(1), gcf);

        gcf = Rational.gcf(BigInteger.valueOf(12), BigInteger.valueOf(28));
        assertEquals(BigInteger.valueOf(4),gcf);
    }

    @Test
    void subtract() {

        Rational f1 = Rational.of(1L, 2L);
        Rational f2 = Rational.of(5L, 1L);
        assertEquals(Rational.of(-9,2L),
                f1.subtract(f2));


        // teste com numeros acima de 64 bits.
        f1 = Rational.of(1L, 2L);
        f2 = Rational.of(230, Long.MAX_VALUE);
        Rational expected = Rational.of(new BigInteger("9223372036854775347"),
                new BigInteger("18446744073709551614"));
        assertEquals(expected, f1.subtract(f2));

    }
}