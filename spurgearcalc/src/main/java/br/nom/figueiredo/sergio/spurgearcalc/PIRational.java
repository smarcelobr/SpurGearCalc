package br.nom.figueiredo.sergio.spurgearcalc;

/**
 * Classe que representa um número equivalente a seguinte fórmula:
 *
 * value = multiplier * PI + offset
 *
 * Essa classe permite realizar cálculos sem perder a precisão por causa do irracional PI.
 */
public class PIRational extends Rational {

    private final Rational piRadians;

    public PIRational(Rational piRadians, Rational offset) {
        super(offset.getNumerador(), offset.getDenominador());
        this.piRadians = piRadians;
    }

    public PIRational(Rational piRadians) {
        this(piRadians, Rational.ZERO);
    }

    public static PIRational toPIRational(Rational other) {
        return new PIRational(Rational.ZERO, other);
    }

    public PIRational multiplyPI(Rational piRadians) {
        return new PIRational(this.piRadians.multiply(piRadians), this);
    }

    @Override
    public PIRational multiply(double multiplier) {
        Rational rationalMultiplier = toRational(multiplier);
        return new PIRational(this.piRadians.multiply(rationalMultiplier).simplify(),  this.multiply(rationalMultiplier).simplify());
    }

    public PIRational addPI(Rational piRadians) {
        return new PIRational(this.piRadians.add(piRadians), this);
    }

    @Override
    public PIRational add(Rational offset) {
        return new PIRational(this.piRadians, super.add(offset));
    }

    public Rational getAproxValue() {
        return this.piRadians.multiply(Rational.PI).add(this).simplify();
    }

    @Override
    public double toDouble() {
        Rational aprox = this.getAproxValue();
        return aprox.toDouble();
    }

    @Override
    public String toString() {
        return this.piRadians + "*PI + " + super.toString();
    }

}
