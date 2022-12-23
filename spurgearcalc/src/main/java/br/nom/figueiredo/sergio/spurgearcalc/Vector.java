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

    public Vector subtract(Vector other) {
        return new Vector(this.getX()-other.getX(), this.getY()-other.getY());
    }

    public Point add(Point point) {
        return new Point(this.getX()+point.getX(), this.getY()+point.getY());
    }

    public double magnitude() {
        return Math.sqrt( Math.pow(this.x,2) + Math.pow(this.y,2) );
    }

    /**
     * Return the unit Vector. Magnitude 1 and same direction.
     *
     * @return unit vector
     */
    public Vector normalize() {
        double mag = this.magnitude();
        return this.divide(mag);
    }

    private Vector divide(double divisor) {
        return new Vector(this.x / divisor, this.y / divisor);
    }

    public Vector multiply(double value) {
        return new Vector(this.x * value, this.y * value);
    }
}
