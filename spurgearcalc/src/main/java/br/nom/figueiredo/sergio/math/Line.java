package br.nom.figueiredo.sergio.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Locale;

/**
 * Define uma reta da matemática
 * (A reta é infinita, não tem inicio ou final)
 * <p>
 * E se baseia na fórmula geral dada por:
 * <p>
 * aX + bY + c = 0
 */
public class Line {

    private final Real a;
    private final Real b;
    private final Real c;

    private Line(Real a, Real b, Real c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static Line of(int a, int b, int c) {
        return Line.of(Rational.of(a), Rational.of(b), Rational.of(c));
    }

    public Real getA() {
        return a;
    }

    public Real getB() {
        return b;
    }

    public Real getC() {
        return c;
    }

    /**
     * Define uma reta que passa pelos dois pontos especificados.
     *
     * @param pointA ponto um
     * @param pointB ponto dois
     */
    public static Line of(Point pointA, Point pointB) {
        /*
        Matrix = | Xa Ya 1 |
                 | Xb Yb 1 |
                 | x  y  1 |

        Determinante(Matrix) = 0

        Ya*x + Xb*y + Xa*Yb - Yb*x - Xa*y - Ya*Xb = 0
        (Ya-Yb)x + (Xb-Xa)y + (Xa*Yb)-(Ya-Xb) = 0
              ax +       by +               c = 0

        a = (Ya-Yb)
        b = (Xb-Xa)
        c = (Xa*Yb)-(Ya-Xb)
         */

        Real a = pointA.getY().subtract(pointB.getY());
        Real b = pointB.getX().subtract(pointA.getX());
        Real c = pointA.getX().multiply(pointB.getY()).subtract(pointA.getY().multiply(pointB.getX()));
        return Line.of(a, b, c);
    }

    public static Line of(Real a, Real b, Real c) {
        return new Line(a.simplify(), b.simplify(), c.simplify());
    }

    /**
     * Retorna o ponto na reta que passa por X.
     * <p>
     * aX + bY + c = 0
     * bY = -(aX + c)
     * <p>
     * Y = -(aX + c) / b
     *
     * @param x posicao no eixo X
     * @return ponto na reta que passa em X
     */
    public Point pointAtX(Real x) {
        return Point.of(x, a.multiply(x).add(c).negate().divide(b).simplify());
    }

    /**
     * <p>Calcula o ponto de interseção de duas linhas.</p>
     *
     * <p>ref: <a href="https://byjus.com/point-of-intersection-formula/#:~:text=Point%20of%20intersection%20means%20the%20point%20at%20which%20two%20lines%20intersect">Point of intersection formula</a>.
     * (em 23/12/2022)</p>
     *
     * <p>Onde:</p>
     * <pre>(x,y) = [ (b1*c2 - b2*c1) / (a1*b2-a2*b1) , (a2*c1 - a1*c2) / (a1*b2 - a2*b1) ]</pre>
     *
     * @param other outra linha
     * @return ponto de intersecao
     */
    public Point intersection(Line other) {

        // x = (b1*c2 - b2*c1) / (a1*b2-a2*b1)
        Real iX = this.b.multiply(other.c).subtract(other.b.multiply(this.c)).divide(this.a.multiply(other.b).subtract(other.a.multiply(this.b)));
        // y = (a2*c1 - a1*c2) / (a1*b2 - a2*b1)
        Real iY = other.a.multiply(this.c).subtract(this.a.multiply(other.c)).divide(this.a.multiply(other.b).subtract(other.a.multiply(this.b)));

        return Point.of(iX, iY);
    }

    /**
     *
     * <p><a href="https://byjus.com/perpendicular-line-formula/">Perpendicular Line Formula</a> em 23/12/2022.</p>
     *
     * <p>Onde:</p>
     * <pre>
     *     slope:
     *     m = -a / b
     *
     *     new perpendicular line slope (m2) needs (m x m2) = -1:
     *     m * m2 = -1
     *     m2 = -1 / m
     *     m2 = -b/-a
     *
     *
     *    at PT=(PTx, PTy) point:
     *    (y - PTy) = m( x - PTx)
     *   ....
     *     (y - PTy) = (-b/-a)*( x - PTx)
     *     -a*(y - PTy) = -b*(x - PTx)
     *     -a*y - (-a*PTy) = -b*x - (-b*PTx)
     *     -a*y + a*PTy = -b*x + b*PTx
     *     -a*y = -b*x + b*PTx - a*PTy
     *     -b*x + a*y + (b*PTx - a*PTy)
     *
     *     então: equação da linha perpendicular:
     *     a = -bx
     *     b = ay
     *     c = b*PTx - a*PTy
     * </pre>
     *
     * @param intersectionPoint ponto de interseção da perpendicular
     *
     * @return formula da reta perpendicular no ponto especificado.
     */
    public Line perpendicularAt(Point intersectionPoint) {
        // a = -bx
        Real newA = this.b.negate();
        // b = ay
        Real newB = this.a;
        // c = b*PTx - a*PTy
        Real newC = this.b.multiply(intersectionPoint.getX()).subtract(this.a.multiply(intersectionPoint.getY()));

        return Line.of(newA, newB, newC);
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"[%s]x + [%s]y + [%s]", a, b, c);
    }

    /**
     * O slope da reta é -a/b
     *
     * @return slope da reta
     */
    public Real slope() {
        // -a/b
        return this.a.negate().divide(this.b);
    }

    public Point pointAtX(int vX) {
        return this.pointAtX(Rational.of(vX));
    }

    public Point[] interpolation(Real startX, Real endX, int numPoints) {
        Point[] points = new Point[numPoints];
        // stepX = (end.X - start.X) / (numPoints+1)
        Real step = endX.subtract(startX)
                .divide(Rational.of(numPoints+1L)).simplify();
        for (int idx=0; idx<numPoints; idx++) {
            Real nX = startX.add(step.multiply(idx + 1L)).simplify();
            points[idx] = this.pointAtX(nX);
        }
        return points;
    }

    /**
     * Returns a point array with the interpolation between two points.
     *
     * @param start start point
     * @param end end point
     * @param numPoints number of between points
     * @return array of between points.
     */
    public static Point[] interpolation(Point start, Point end, int numPoints) {
        Line r = Line.of(start, end);
        return r.interpolation(start.getX(), end.getX(), numPoints);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        return new EqualsBuilder().append(a, line.a).append(b, line.b).append(c, line.c).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(a).append(b).append(c).toHashCode();
    }
}
