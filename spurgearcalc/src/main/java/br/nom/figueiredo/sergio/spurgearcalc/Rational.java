package br.nom.figueiredo.sergio.spurgearcalc;

import java.util.Objects;

public class Rational {

    private final long numerador;
    private final long denominador;

    public Rational(long numerador, long denominador) {
        this.numerador = numerador;
        this.denominador = denominador;
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
        long d1 = 1;
        long d2 = 1;

        long factor1;
        long factor2;
        while (true) {
            factor1 = l1/d1;
            factor2 = l2/d2;
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
        long gcf = gcf(this.numerador, this.denominador);
        return new Rational(this.numerador/gcf, this.denominador/gcf);
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
        if (this.denominador != thisSimplified.denominador) {
            simplfiedStr = " (" + thisSimplified.numerador + "/" + thisSimplified.denominador + ")";
        }
        return this.numerador + "/" + this.denominador + simplfiedStr;
    }
}
