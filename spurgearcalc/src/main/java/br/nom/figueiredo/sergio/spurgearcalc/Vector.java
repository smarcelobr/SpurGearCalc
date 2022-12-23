package br.nom.figueiredo.sergio.spurgearcalc;

public class Vector {
    private final double x;
    private final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Rational x, Rational y) {
        this.x = x.toDouble();
        this.y = y.toDouble();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector add(Vector other) {
        return new Vector(this.getX()+other.getX(), this.getY()+other.getY());
    }

    public Point add(Point point) {
        return new Point(this.getX()+point.getX(), this.getY()+point.getY());
    }

}
