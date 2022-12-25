package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTest {

    @Test
    void nearestPointAt() {

        Point A = Point.of(2, 3);
        Line r = Line.of(1, 1, 5);

        Point nearest = A.nearestPointAt(r);

        assertEquals(Point.of(-3,-2),  nearest);

        r = Line.of(2, 1, 5);
        nearest = A.nearestPointAt(r);

        assertEquals(Point.of(-2.8, 0.6),  nearest);

        r = Line.of(8, -5, 0);
        nearest = A.nearestPointAt(r);

        assertEquals(Point.of(Rational.of(170,89),
                Rational.of(272,89)), nearest);

    }
}