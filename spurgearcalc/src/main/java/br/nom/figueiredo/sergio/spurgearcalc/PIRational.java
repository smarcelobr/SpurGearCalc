package br.nom.figueiredo.sergio.spurgearcalc;

/**
 * Classe que representa um número equivalente a seguinte fórmula:
 *
 * value = multiplier * PI + offset
 *
 * Essa classe permite realizar cálculos sem perder a precisão por causa do irracional PI.
 */
public class PIRational implements Value {

    private final Value offset;
    private final Value piRadians;

    protected PIRational(Value piRadians, Value offset) {
        this.offset = offset;
        this.piRadians = piRadians;
    }

    public static PIRational of(Rational piRadians, Rational offset) {
        return new PIRational(piRadians, offset);
    }

    public static PIRational of(Rational piRadians) {
        return of(piRadians, Rational.ZERO);
    }

    public static PIRational toPIRational(Rational other) {
        return new PIRational(Rational.ZERO, other);
    }

    public PIRational multiplyPI(Rational piRadians) {
        return new PIRational(this.piRadians.multiply(piRadians), offset);
    }

    @Override
    public PIRational multiply(double multiplier) {
        return new PIRational(this.piRadians.multiply(multiplier),
                offset.multiply(multiplier));
    }

    @Override
    public PIRational divide(Value other) {
        return new PIRational(this.piRadians.divide(other),
                offset.divide(other));
    }

    @Override
    public Value simplify() {
        return new PIRational(this.piRadians.simplify(),
                offset.simplify());
    }

    @Override
    public Value multiply(long multiplier) {
        return new PIRational(this.piRadians.multiply(multiplier),
                offset.multiply(multiplier));
    }

    public PIRational addPI(Rational piRadians) {
        return new PIRational(this.piRadians.add(piRadians), offset);
    }

    @Override
    public long getNumerador() {
        return getAproxValue().getNumerador();
    }

    @Override
    public long getDenominador() {
        return getAproxValue().getDenominador();
    }

    @Override
    public PIRational add(Value valor) {
        return new PIRational(this.piRadians, valor.add(this.offset));
    }

    @Override
    public PIRational subtract(Value other) {
        return new PIRational(this.piRadians, offset.subtract(other));
    }

    @Override
    public PIRational multiply(Value other) {
        return new PIRational(this.piRadians.multiply(other),
                offset.multiply(other));
    }

    protected Value getAproxValue() {
        return this.piRadians.multiply(Rational.PI)
                .add(offset).simplify();
    }

    @Override
    public double toDouble() {
        Value aprox = this.getAproxValue();
        return aprox.toDouble();
    }

    @Override
    public PIRational adjustSignal() {
        return new PIRational(this.piRadians.adjustSignal(),
                offset.adjustSignal());
    }

    @Override
    public String toString() {
        return this.piRadians + "*PI + " + offset.toString();
    }

    @Override
    public PIRational negate() {
        return new PIRational(this.piRadians.negate(), offset.negate());
    }

}
