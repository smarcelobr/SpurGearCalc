package br.nom.figueiredo.sergio.spurgearcalc;

import static org.junit.jupiter.api.Assertions.*;

class RationalTest {

    @org.junit.jupiter.api.Test
    void add() {
        // caso #1
        Rational f1 = new Rational(100L, 3L);
        Rational f2 = new Rational(50L, 2L);
        assertEquals(new Rational((100*6L/3)+(50*6L/2),6L),
                f1.add(f2));

        // caso #2
        f1 = new Rational(100L, 3L);
        f2 = new Rational(50L, 20L);
        assertEquals(new Rational((100*60L/3)+(50*60L/20),60L),
                f1.add(f2));

        // caso #3
        f1 = new Rational(100L, 33L);
        f2 = new Rational(50L, 2L);
        assertEquals(new Rational((100*66L/33)+(50*66L/2),66L),
                f1.add(f2));

        // caso #4
        f1 = new Rational(100L, 23L);
        f2 = new Rational(50L, 23L);
        assertEquals(new Rational(150L,23L),
                f1.add(f2));

    }

    @org.junit.jupiter.api.Test
    void multiply() {
        Rational f1 = new Rational(1L, 3L);
        Rational f2 = new Rational(5L, 2L);
        assertEquals(new Rational(5L,6L),
                f1.multiply(f2));
    }

    @org.junit.jupiter.api.Test
    void simplify() {
        Rational f1 = new Rational(11L, 22L);
        assertEquals(new Rational(1L,2L),
                f1.simplify());

        f1 = new Rational(45L, 5L);
        assertEquals(new Rational(9L,1L),
                f1.simplify());

        f1 = new Rational(47L, 3L);
        assertEquals(new Rational(47L,3L),
                f1.simplify());
    }

    @org.junit.jupiter.api.Test
    void greatestCommonFactor() {
        long gcf = Rational.gcf(8, 24);
        assertEquals(8, gcf);

        gcf = Rational.gcf(33,5);
        assertEquals(1, gcf);

        gcf = Rational.gcf(12, 28);
        assertEquals(4,gcf);
    }

}