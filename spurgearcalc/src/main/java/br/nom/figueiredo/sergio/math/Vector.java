package br.nom.figueiredo.sergio.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Vector {
    private final Real x;
    private final Real y;

    public Vector(double x, double y) {
        this.x = Rational.toRational(x);
        this.y = Rational.toRational(y);
    }

    public Vector(Real x, Real y) {
        this.x = x;
        this.y = y;
    }

    public Real getX() {
        return x;
    }

    public Real getY() {
        return y;
    }

    public Vector add(Vector other) {
        return new Vector(this.getX().add(other.getX()).simplify(),
                this.getY().add(other.getY()).simplify());
    }

    public Point add(Point point) {
        return Point.of(this.getX().add(point.getX()).simplify(),
                this.getY().add(point.getY()).simplify());
    }

    public Vector subtract(Vector other) {
        return new Vector(this.getX().subtract(other.getX()).simplify(),
                this.getY().subtract(other.getY()).simplify());
    }

    public Vector multiply(Rational value) {
        return new Vector(this.x.multiply(value).simplify(), this.y.multiply(value).simplify());
    }

    private Vector divide(Rational divisor) {
        return new Vector(this.x.divide(divisor).simplify(), this.y.divide(divisor).simplify());
    }

    // raiz( x^2 + y^2 )
    public Rational magnitude() {

        // q = x^2 + y^2
        Real q = this.x.multiply(this.x).add(this.y.multiply(this.y));

        return Rational.of( q.getNumerador().sqrt(), q.getDenominador().sqrt());
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

    @Override
    public String toString() {
        return String.format("Vector{ x=[%s], y=[%s] }",this.x, this.y);
    }
}
