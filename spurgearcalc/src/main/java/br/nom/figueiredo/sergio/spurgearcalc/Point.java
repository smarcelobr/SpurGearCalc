package br.nom.figueiredo.sergio.spurgearcalc;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Rational x, Rational y) {
        this.x = x.toDouble();
        this.y = y.toDouble();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point add(Point other) {
        return new Point(this.getX()+other.getX(), this.getY()+other.getY());
    }

    public Point add(Vector vector) {
        return vector.add(this);
    }
}
