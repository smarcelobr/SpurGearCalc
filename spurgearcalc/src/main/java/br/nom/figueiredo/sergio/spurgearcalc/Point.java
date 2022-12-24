package br.nom.figueiredo.sergio.spurgearcalc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Point {
    private final Value x;
    private final Value y;

    private Point(Value x, Value y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(double x, double y) {
        return Point.of(Rational.toRational(x), Rational.toRational(y));
    }

    public static Point of(Value x, Value y) {
        return new Point(x, y);
    }

    public Value getX() {
        return x;
    }

    public Value getY() {
        return y;
    }

    public Point add(Point other) {
        return new Point(this.getX().add(other.getX()), this.getY().add(other.getY()));
    }

    public Point add(Vector vector) {
        return vector.add(this);
    }

    /**
     * <p>Calcula o menor vetor entre o ponto até a linha.</p>
     *
     *
     * @param linha linha
     *
     * @return vetor cuja magnitude é a distância do ponto para a linha e a direção também.
     */
    public Vector vectorTo(Line linha) {
        Point nearestPt = nearestPointAt(linha);
        return vectorTo(nearestPt);
    }

    public Vector vectorTo(Point p) {
        return new Vector(p.x.subtract(this.x),p.y.subtract(this.y));
    }

    public Point pointAt(Vector vector) {
        return vector.add(this);
    }

    /**
     * Retorna o ponto mais proximo deste sobre a linha:
     *
     * <p>Ponto (x,y) sobre a linha mais próximo deste ponto (x0, y0):</p>
     * <pre>
     * x = [ b*(b*x0 - a*y0) - a*c ] / a^2 + b^2
     * y = [ a*(-b*x0 + a*y0) - b*c] / a^2 + b^2
     * </pre>
     *
     * <p>ref: <a href="https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line">Distance from a point to a line (Wikipedia)</a></p>
     *
     * @param linha linha
     * @return ponto mais próximo deste sobre a linha
     */
    public Point nearestPointAt(Line linha) {
        Value a = linha.getA();
        Value b = linha.getB();
        Value c = linha.getC();

        // a^2 + b^2
        Value div = a.multiply(a).add(b.multiply(b));

        // x = [ b*(b*x0 - a*y0) - a*c ] / a^2 + b^2
        Value nX = b.multiply( b.multiply(this.x).subtract(a.multiply(this.y))).subtract(a.multiply(c))
                .divide(div);

        // y = [ a*(-b*x0 + a*y0) - b*c] / a^2 + b^2
        Value nY = a.multiply(b.negate().multiply(this.x).add(a.multiply(this.y))).subtract(b.multiply(c))
                .divide(div);

        return new Point(nX.simplify(), nY.simplify());
    }

    @Override
    public String toString() {
        return String.format("( %s , %s )", this.x, this.y);
    }

    public Point multiply(Value scale) {
        return new Point(this.x.multiply(scale), this.y.multiply(scale));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return new EqualsBuilder().append(x, point.x).append(y, point.y).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(x).append(y).toHashCode();
    }
}
