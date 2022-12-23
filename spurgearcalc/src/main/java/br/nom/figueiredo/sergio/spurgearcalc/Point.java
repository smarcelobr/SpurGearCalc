package br.nom.figueiredo.sergio.spurgearcalc;

public class Point {
    private final PIRational x;
    private final PIRational y;

    public Point(PIRational x, PIRational y) {
        this.x = x;
        this.y = y;
    }

    public Point(Rational x, Rational y) {
        this.x = PIRational.toPIRational(x);
        this.y = PIRational.toPIRational(y);
    }

    public PIRational getX() {
        return x;
    }

    public PIRational getY() {
        return y;
    }

    public Point add(Point other) {
        return new Point(this.getX().add(other.getX()), this.getY().add(other.getY()));
    }
}
