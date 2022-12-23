package br.nom.figueiredo.sergio.spurgearcalc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTest {

    @Test
    void nearestPointAt() {

        Point A = new Point(2, 3);
        Line r = new Line(1, 1, 5);

        Point nearest = A.nearestPointAt(r);

        assertEquals(-3, nearest.getX());
        assertEquals(-2, nearest.getY());

        r = new Line(2, 1, 5);
        nearest = A.nearestPointAt(r);

        assertEquals(-2.8, nearest.getX());
        assertEquals(0.6, nearest.getY());

        r = new Line(8, -5, 0);
        nearest = A.nearestPointAt(r);

        assertEquals(1.9101123595505618, nearest.getX());
        assertEquals(3.056179775280899, nearest.getY());

    }
}