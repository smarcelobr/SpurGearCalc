package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Line;
import br.nom.figueiredo.sergio.math.Point;
import br.nom.figueiredo.sergio.math.Rational;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineTest {

    @Test
    void in() {

        Point pA = Point.of(2, 1);
        Point pB = Point.of( 4, 5);
        Line r = Line.of(pA, pB);

        Point pC = r.pointAtX(3);
        Point pD = r.pointAtX(70);

        assertEquals(Rational.of(3), pC.getX());

    }

    @Test
    void intersection() {
        // r => x + 2y + 1 = 0
        Line r = Line.of(1,2,1);

        // s => 2x + 3y + 5 = 0
        Line s = Line.of(2,3,5);

        Point intersect = r.intersection(s);

        assertEquals(Point.of(-7,3),  intersect);

        intersect = s.intersection(r);

        assertEquals(Point.of(-7,3),  intersect);
    }

    @Test
    void perpendicularAt() {

        Line r = Line.of(2,3,5);

        Point intersection = Point.of(-7, 3);

        Line s = r.perpendicularAt(intersection);

        // quando multiplicamos o slope (-a/b) das linhas perpendiculares, dá -1
        assertEquals(Rational.of(-1), r.slope().multiply(s.slope()));

        // o ponto (-7, 3) deve estar nessa linha:
        assertEquals(Point.of(-7,3),  s.pointAtX(-7));
        assertEquals(Line.of(-3,2,-27), s);

    }

    @Test
    void verticalLineOperations() {
        Point A = Point.of(2,8);
        Point B = Point.of(2, 3);
        Line r = Line.of(A, B); // linha vertical

        Line s = r.perpendicularAt(A);
        assertEquals(Line.of(0,5,-40), s);

    }

    @Test
    void interpolation() {
        Line r = Line.of(Point.of(0,1),Point.of(9,7));
        Point[] pts = Line.interpolation(r.pointAtX(2), r.pointAtX(8), 3);
        assertEquals(Point.of(Rational.of(7,2),
                Rational.of(10,3)),pts[0]);
        assertEquals(Point.of(Rational.of(5),
                Rational.of(13,3)),pts[1]);
        assertEquals(Point.of(Rational.of(13,2),
                Rational.of(16,3)),pts[2]);
    }
}