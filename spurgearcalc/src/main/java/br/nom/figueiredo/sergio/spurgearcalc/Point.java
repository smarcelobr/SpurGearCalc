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
        return new Vector(p.x - this.x,p.y - this.y);
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
        double a = linha.getA();
        double b = linha.getB();
        double c = linha.getC();

        double den = Math.pow(a,2) + Math.pow(b,2);
        double nX = ( b * ( b * this.x - a * this.y) - a*c ) / den;
        double nY = ( a * ( -b * this.x + a * this.y) - b*c ) / den;

        return new Point(nX, nY);
    }

    @Override
    public String toString() {
        return String.format("( %f , %f )", this.x, this.y);
    }
}
