package br.nom.figueiredo.sergio.spurgearcalc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Vector {
    private final Value x;
    private final Value y;

    public Vector(double x, double y) {
        this.x = Rational.toRational(x);
        this.y = Rational.toRational(y);
    }

    public Vector(Value x, Value y) {
        this.x = x;
        this.y = y;
    }

    public Value getX() {
        return x;
    }

    public Value getY() {
        return y;
    }

    public Vector add(Vector other) {
        return new Vector(this.getX().add(other.getX()),
                this.getY().add(other.getY()));
    }

    public Vector subtract(Vector other) {
        return new Vector(this.getX().subtract(other.getX()),
                this.getY().subtract(other.getY()));
    }

    public Point add(Point point) {
        return Point.of(this.getX().add(point.getX()),
                this.getY().add(point.getY()));
    }

    public Rational magnitude() {
        // raiz(x^2 + y^2)
        return Rational.toRational(
                Math.sqrt( this.x.multiply(this.x).add(this.y.multiply(this.y)).toDouble() ));
    }

    /**
     * Return the unit Vector. Magnitude 1 and same direction.
     *
     * @return unit vector
     */
    public Vector normalize() {
        Rational mag = this.magnitude();
        return this.divide(mag);
    }

    /**
     * Direção oposta sem mudar a magnitude.
     * @return vetor inverso
     */
    public Vector inverse() {
        return this.multiply(Rational.of(-1));
    }

    private Vector divide(Rational divisor) {
        return new Vector(this.x.divide(divisor), this.y.divide(divisor));
    }

    public Vector multiply(Rational value) {
        return new Vector(this.x.multiply(value), this.y.multiply(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        return new EqualsBuilder().append(x, vector.x).append(y, vector.y).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(x).append(y).toHashCode();
    }
}
