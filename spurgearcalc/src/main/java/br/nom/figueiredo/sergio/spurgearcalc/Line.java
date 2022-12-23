package br.nom.figueiredo.sergio.spurgearcalc;

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

    private final double a;
    private final double b;
    private final double c;

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    /**
     * Define uma reta que passa pelos dois pontos especificados.
     *
     * @param pointA ponto um
     * @param pointB ponto dois
     */
    public Line(Point pointA, Point pointB) {
        /*
        Matrix = | Xa Ya 1 |
                 | Xb Yb 1 |
                 | x  y  1 |

        Determinante(Matrix) = 0

         */
        this(pointA.getY() - pointB.getY(),
             pointB.getX() - pointA.getX(),
             pointA.getX() * pointB.getY() - pointA.getY() * pointB.getX());
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
    public Point pointAtX(double x) {
        return new Point(x, -(a * x + c) / b);
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

        double iX = (this.b * other.c - other.b * this.c) / (this.a * other.b - other.a * this.b);
        double iY = (other.a * this.c - this.a * other.c) / (this.a * other.b - other.a * this.b);

        return new Point(iX, iY);
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
        return new Line(-this.b, this.a, this.b* intersectionPoint.getX() - this.a*intersectionPoint.getY());
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,"[%f]x + [%f]y + [%f]", a, b, c);
    }

    /**
     * O slope da reta é -a/b
     *
     * @return slope da reta
     */
    public Rational slope() {
        return Rational.toRational(-this.a, this.b);
    }
}
