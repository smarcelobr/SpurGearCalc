package br.nom.figueiredo.sergio.spurgearcalc;

public class Rational {

    public static final Rational ONE = new Rational(1,1);
    public static final Rational ZERO = new Rational(0,1);
    public static final Rational PI = new Rational(62831853,20000000);

    private final long numerador;
    private final long denominador;

    public Rational(long numerador, long denominador) {
        this.numerador = numerador;
        this.denominador = denominador;
    }

    public Rational(long integer) {
        this.numerador = integer;
        this.denominador = 1L;
    }

    public long getNumerador() {
        return numerador;
    }

    public long getDenominador() {
        return denominador;
    }

    public Rational add(Rational other) {
        long lcm = this.lcm(other);
        return new Rational(this.numerador * (lcm / this.denominador) +
                other.numerador * (lcm / other.denominador)
                , lcm);
    }

    public Rational subtract(Rational other) {
        long lcm = this.lcm(other);
        return new Rational(this.numerador * (lcm / this.denominador) -
                other.numerador * (lcm / other.denominador)
                , lcm);
    }

    public Rational multiply(Rational other) {
        return new Rational(this.numerador * other.numerador,
                this.denominador * other.denominador);
    }

    public Rational divide(Rational other) {
        return new Rational(this.numerador * other.denominador,
                this.denominador * other.numerador);
    }

    //  least common multiple
    public long lcm(Rational other) {
        long d1 = 1L;
        long d2 = 1L;
        while ((d1 * this.denominador) != (d2 * other.denominador)) {

            if ((d1 * this.denominador) < (d2 * other.denominador)) {
                d1 = Math.max((other.denominador * d2) / this.denominador, d1 + 1);
            } else {
                d2 = Math.max((this.denominador * d1) / other.denominador, d2 + 1);
            }

        }
        return d1 * this.denominador;
    }

    // greatest common factor
    public static long gcf(final long l1, final long l2) {
        assert l1!=0;
        assert l2!=0;

        long d1 = 1;
        long d2 = 1;

        long factor1;
        long factor2;
        while (true) {
            factor1 = Math.abs(l1)/d1;
            factor2 = Math.abs(l2)/d2;
            if (factor1 == factor2) {
                break;
            } else if (factor1<factor2) {
                do
                    d2++;
                while ((l2%d2)!=0);
            } else {
                do
                   d1++;
                while ((l1%d1)!=0);
            }
        }

        return factor1;
    }

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

        Rational thisSimplified = this.simplify();
        Rational oSimplified = ((Rational) o).simplify();

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(thisSimplified.numerador, oSimplified.numerador)
                .append(thisSimplified.denominador, oSimplified.denominador).isEquals();
    }

    @Override
    public int hashCode() {
        Rational thisSimplified = this.simplify();
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

    public Rational multiply(long multiplier) {
        return new Rational(this.numerador*multiplier, this.denominador);
    }

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

    public Rational negate() {
        return new Rational(-this.numerador, this.denominador);
    }
}
