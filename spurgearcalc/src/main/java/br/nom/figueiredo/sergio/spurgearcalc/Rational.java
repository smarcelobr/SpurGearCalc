package br.nom.figueiredo.sergio.spurgearcalc;

public class Rational implements Value {

    public static final Rational ONE = new Rational(1,1);
    public static final Rational ZERO = new Rational(0,1);
    public static final Rational PI = new Rational(62831853,20000000);

    private final long numerador;
    private final long denominador;

    protected Rational(long numerador, long denominador) {
        this.numerador = numerador;
        this.denominador = denominador;
    }

    public static Rational of(long integer) {
        return new Rational(integer, 1);
    }

    public static Rational of(long numerador, long denominador) {
        return new Rational(numerador, denominador);
    }

    @Override
    public long getNumerador() {
        return numerador;
    }

    @Override
    public long getDenominador() {
        return denominador;
    }

    @Override
    public Value add(Value other) {
        long lcm = this.lcm(this.denominador, other.getDenominador());
        return new Rational(this.numerador * (lcm / this.denominador) +
                other.getNumerador() * (lcm / other.getDenominador())
                , lcm);
    }

    @Override
    public Value subtract(Value other) {
        long lcm = Rational.lcm(this.denominador, other.getDenominador());
        return new Rational(this.numerador * (lcm / this.denominador) -
                other.getNumerador() * (lcm / other.getDenominador())
                , lcm);
    }

    @Override
    public Rational multiply(Value other) {
        return new Rational(this.numerador * other.getNumerador(),
                this.denominador * other.getDenominador());
    }

    @Override
    public Rational divide(Value other) {
        return new Rational(this.numerador * other.getDenominador(),
                this.denominador * other.getNumerador());
    }

    /**
     * Calcula o Least Common Multiple usando o GCF.
     *
     * @param v1 valor 1
     * @param v2 valor 2
     * @return Mínimo Multiplo Comum.
     */
    //
    public static long lcm(long v1, long v2) {
        return (v1*v2)/gcf(v1,v2);
    }

    /**
     * Greatest Common Factor
     * Usando "Euclid's Algorithm GCF Calculator"
     * @param l1 valor 1
     * @param l2 valor 2
     *
     * @return gcf
     */
    public static long gcf(final long l1, final long l2) {
        assert l1!=0;
        assert l2!=0;

        long maior = Math.max(Math.abs(l1),Math.abs(l2));
        long menor = Math.min(Math.abs(l1),Math.abs(l2));
        while ((maior%menor)!=0) {
            long resto = (maior%menor);
            maior = menor;
            menor = resto;
        }
        return menor;

    }

    @Override
    public Rational simplify() {
        if (this.numerador!=0 && this.denominador != 0) {
            long gcf = gcf(this.numerador, this.denominador);

            return new Rational(this.numerador / gcf, this.denominador / gcf);
        } else {
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Rational thisSimplified = this.simplify().adjustSignal();
        Rational oSimplified = ((Rational) o).simplify().adjustSignal();

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(thisSimplified.numerador, oSimplified.numerador)
                .append(thisSimplified.denominador, oSimplified.denominador).isEquals();
    }

    // acerta o sinal de modo que o negativo fique sempre no numerador.
    public Rational adjustSignal() {
        if (this.denominador>=0) {
            // nenhuma modificação de sinal é necessária
            return this;
        }
        // inverte os sinais de modo que tudo fique positivo ou que o negativo fique no numerador:
        return new Rational(this.numerador*-1, this.denominador*-1);
    }

    @Override
    public int hashCode() {
        Rational thisSimplified = this.simplify().adjustSignal();
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(thisSimplified.numerador).append(thisSimplified.denominador).toHashCode();
    }

    @Override
    public String toString() {
        Rational thisSimplified = this.simplify();
        String simplfiedStr = "";
        if (this.getDenominador() != thisSimplified.getDenominador()) {
            simplfiedStr = " (" + thisSimplified + ")";
        }
        String thisStr = Long.toString(this.getNumerador());
        if (this.getDenominador()!=1) {
            thisStr = thisStr + "/" + this.getDenominador();
        }
        return thisStr + simplfiedStr;
    }

    @Override
    public Rational multiply(long multiplier) {
        return new Rational(this.numerador*multiplier, this.denominador);
    }

    @Override
    public Rational multiply(double multiplier) {
        return this.multiply(toRational(multiplier));
    }

    public static Rational toRational(double number){
        return toRational(number, 8).simplify();
    }

    /**
     * ref: https://stackoverflow.com/questions/14014158/double-to-fraction-in-java (lido em 22/12/2022)
     *
     * @param number
     * @param largestRightOfDecimal
     * @return
     */
    public static Rational toRational(double number, int largestRightOfDecimal){

        long sign = 1;
        if(number < 0){
            number = -number;
            sign = -1;
        }

        final long SECOND_MULTIPLIER_MAX = (long)Math.pow(10, largestRightOfDecimal - 1);
        final long FIRST_MULTIPLIER_MAX = SECOND_MULTIPLIER_MAX * 10L;
        final double ERROR = Math.pow(10, -largestRightOfDecimal - 1);
        long firstMultiplier = 1;
        long secondMultiplier = 1;
        boolean notIntOrIrrational = false;
        long truncatedNumber = (long)number;
        Rational rationalNumber = new Rational((long)(sign * number * FIRST_MULTIPLIER_MAX), FIRST_MULTIPLIER_MAX);

        double error = number - truncatedNumber;
        while( (error >= ERROR) && (firstMultiplier <= FIRST_MULTIPLIER_MAX)){
            secondMultiplier = 1;
            firstMultiplier *= 10;
            while( (secondMultiplier <= SECOND_MULTIPLIER_MAX) && (secondMultiplier < firstMultiplier) ){
                double difference = (number * firstMultiplier) - (number * secondMultiplier);
                truncatedNumber = (long)difference;
                error = difference - truncatedNumber;
                if(error < ERROR){
                    notIntOrIrrational = true;
                    break;
                }
                secondMultiplier *= 10;
            }
        }

        if(notIntOrIrrational){
            rationalNumber = new Rational(sign * truncatedNumber, firstMultiplier - secondMultiplier);
        }
        return rationalNumber;
    }

    public double toDouble() {
        return this.getNumerador() / (double) this.getDenominador();
    }

    @Override
    public Rational negate() {
        return new Rational(-this.numerador, this.denominador);
    }

    public static Rational toRational(double numerador, double denominador) {
        Rational rNumerador = toRational(numerador);
        Rational rDenominador = toRational(denominador);

        return rNumerador.divide(rDenominador);
    }

}
