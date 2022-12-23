package br.nom.figueiredo.sergio.spurgearcalc;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineTest {

    @Test
    void in() {

        Point pA = new Point(2, 1);
        Point pB = new Point( 4, 5);
        Line r = new Line(pA, pB);

        Point pC = r.pointAtX(3);
        Point pD = r.pointAtX(70);

        String svgPath = String.format(Locale.ENGLISH, "M%f %f L%f %f L%f %f L%f %f",
                pA.getX(), pA.getY(),
                pB.getX(), pB.getY(),
                pC.getX(), pC.getY(),
                pD.getX(), pD.getY());

        System.out.println(svgPath);
        assertEquals(3.0, pC.getX());

    }

    @Test
    void intersection() {
        // r => x + 2y + 1 = 0
        Line r = new Line(1,2,1);

        // s => 2x + 3y + 5 = 0
        Line s = new Line(2,3,5);

        Point intersect = r.intersection(s);

        assertEquals(-7, intersect.getX());
        assertEquals(3, intersect.getY());

        intersect = s.intersection(r);

        assertEquals(-7, intersect.getX());
        assertEquals(3, intersect.getY());
    }

    @Test
    void perpendicularAt() {

        Line r = new Line(2,3,5);

        Point intersection = new Point(-7, 3);

        Line s = r.perpendicularAt(intersection);

        // quando multiplicamos o slope (-a/b) das linhas perpendiculares, dá -1
        assertEquals(new Rational(-1), r.slope().multiply(s.slope()));

        // o ponto (-7, 3) deve estar nessa linha:
        assertEquals(3, s.pointAtX(-7).getY());
        assertEquals("[-3.000000]x + [2.000000]y + [-27.000000]", s.toString());

    }

    @Test
    void verticalLineOperations() {
        Point A = new Point(2,8);
        Point B = new Point(2, 3);
        Line r = new Line(A, B); // linha vertical

        Line s = r.perpendicularAt(A);
        assertEquals("[-0.000000]x + [5.000000]y + [-40.000000]", s.toString());

    }
}