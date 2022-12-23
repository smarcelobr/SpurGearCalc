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

        Point pC = r.in(3);
        Point pD = r.in(70);

        String svgPath = String.format(Locale.ENGLISH, "M%f %f L%f %f L%f %f L%f %f",
                pA.getX(), pA.getY(),
                pB.getX(), pB.getY(),
                pC.getX(), pC.getY(),
                pD.getX(), pD.getY());

        System.out.println(svgPath);
        assertEquals(3.0, pC.getX());

    }
}