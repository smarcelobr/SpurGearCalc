package br.nom.figueiredo.sergio.spurgearcalc;

/**
 * Define uma reta da matemática
 * (A reta é infinita, não tem inicio ou final)
 *
 * E se baseia na fórmula geral dada por:
 *
 *   aX + bY + c = 0
 *
 */
public class Line {

    private final double a;
    private final double b;
    private final double c;

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

        a = pointA.getY()-pointB.getY();
        b = pointB.getX()-pointA.getX();
        c = pointA.getX()*pointB.getY() - pointA.getY()*pointB.getX();


    }

    /**
     * Retorna o ponto na reta que passa por X.
     *
     * aX + bY + c = 0
     * bY = -(aX + c)
     *
     * Y = -(aX + c) / b
     *
     * @param x posicao no eixo X
     *
     * @return ponto na reta que passa em X
     */
    public Point in(double x) {
        return new Point(x, -(a*x + c)/b);
    }
}
