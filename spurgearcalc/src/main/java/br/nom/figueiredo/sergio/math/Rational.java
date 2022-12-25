package br.nom.figueiredo.sergio.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Rational implements Real {

    public static final Rational ONE = Rational.of(1L,1L);
    public static final Rational ZERO = Rational.of(0L,1L);
    public static final Rational PI = Rational.of(62831853L,20000000L);

    private final BigInteger numerador;
    private final BigInteger denominador;

    public static Rational of(BigInteger numerador, BigInteger denominador) {
        return new Rational(numerador, denominador);
    }

    protected Rational(BigInteger numerador, BigInteger denominador) {
        this.numerador = numerador;
        this.denominador = denominador;
    }

    public static Rational of(long integer) {
        return Rational.of(integer, 1L);
    }

    public static Rational of(long numerador, long denominador) {
        return new Rational(BigInteger.valueOf(numerador), BigInteger.valueOf(denominador));
    }

    @Override
    public BigInteger getNumerador() {
        return numerador;
    }

    @Override
    public BigInteger getDenominador() {
        return denominador;
    }

    @Override
    public Real add(Real other) {
        Rational thisSimplified = this.simplify().adjustSignal();
        Real otherSimplified = other.simplify().adjustSignal();
        BigInteger lcm = Rational.lcm(thisSimplified.denominador,
                otherSimplified.getDenominador());

        return new Rational(thisSimplified.numerador.multiply(lcm).divide(thisSimplified.denominador)
                     .add(
                otherSimplified.getNumerador().multiply(lcm).divide(otherSimplified.getDenominador()))
                , lcm);
    }

    @Override
    public Real subtract(Real other) {
        return this.add(other.negate());
    }

    @Override
    public Rational multiply(Real other) {
        return Rational.of(this.numerador.multiply(other.getNumerador()),
                this.denominador.multiply(other.getDenominador()));
    }

    @Override
    public Rational divide(Real other) {
        return Rational.of(this.numerador.multiply(other.getDenominador()),
                this.denominador.multiply(other.getNumerador()));
    }

    /**
     * Calcula o Least Common Multiple usando o GCF.
     *
     * @param v1 valor 1
     * @param v2 valor 2
     * @return Mínimo Multiplo Comum.
     */
    //
    public static BigInteger lcm(BigInteger v1, BigInteger v2) {
        // (v1*v2)/gcf
        return v1.multiply(v2).divide(gcf(v1,v2));
    }

    /**
     * Greatest Common Factor
     * Usando "Euclid's Algorithm GCF Calculator"
     * @param l1 valor 1
     * @param l2 valor 2
     *
     * @return gcf
     */
    public static BigInteger gcf(final BigInteger l1, final BigInteger l2) {
        assert l1.compareTo(BigInteger.ZERO)!=0;
        assert l2.compareTo(BigInteger.ZERO)!=0;

        BigInteger maior = l1.abs().max(l2.abs());
        BigInteger menor = l1.abs().min(l2.abs());
        while ((maior.mod(menor)).compareTo(BigInteger.ZERO)!=0) {
            BigInteger resto = maior.mod(menor);
            maior = menor;
            menor = resto;
        }
        return menor;

    }

    @Override
    public Rational simplify() {
        if (this.denominador.compareTo(BigInteger.ZERO) != 0) {
            if (this.numerador.compareTo(BigInteger.ZERO)==0) {
                return Rational.ZERO;
            }
            BigInteger gcf = gcf(this.numerador, this.denominador);

            return Rational.of(this.numerador.divide(gcf), this.denominador.divide(gcf));
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
        if (this.denominador.compareTo(BigInteger.ZERO)>=0) {
            // nenhuma modificação de sinal é necessária
            return this;
        }
        // inverte os sinais de modo que tudo fique positivo ou que o negativo fique no numerador:
        BigInteger menos1 = BigInteger.valueOf(-1L);
        return new Rational(this.numerador.multiply(menos1), this.denominador.multiply(menos1));
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
        if (this.getDenominador().compareTo(thisSimplified.getDenominador())!=0) {
            simplfiedStr = " (" + thisSimplified + ")";
        }
        String thisStr = this.getNumerador().toString();
        if (this.getDenominador().compareTo(BigInteger.ONE)!=0) {
            thisStr = thisStr + "/" + this.getDenominador();
        }
        return thisStr + simplfiedStr;
    }

    @Override
    public Rational multiply(long multiplier) {
        return new Rational(this.numerador.multiply(BigInteger.valueOf(multiplier)), this.denominador);
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
        Rational rationalNumber = Rational.of((long)(sign * number * FIRST_MULTIPLIER_MAX), FIRST_MULTIPLIER_MAX);

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
            rationalNumber = Rational.of(sign * truncatedNumber, firstMultiplier - secondMultiplier);
        }
        return rationalNumber;
    }

    public double toDouble() {
        return this.toBigDecimal().doubleValue();
    }

    @Override
    public BigDecimal toBigDecimal() {
        Rational thisSimplified = this.simplify();
        BigDecimal bdNumerador = new BigDecimal(thisSimplified.getNumerador());
        BigDecimal bdDenominador = new BigDecimal(thisSimplified.getDenominador());
        return bdNumerador.setScale(4, RoundingMode.FLOOR).divide(bdDenominador,
                RoundingMode.FLOOR);
    }

    @Override
    public Rational negate() {
        return Rational.of(this.numerador.negate(), this.denominador);
    }

    public static Rational toRational(double numerador, double denominador) {
        Rational rNumerador = toRational(numerador);
        Rational rDenominador = toRational(denominador);

        return rNumerador.divide(rDenominador);
    }

}
