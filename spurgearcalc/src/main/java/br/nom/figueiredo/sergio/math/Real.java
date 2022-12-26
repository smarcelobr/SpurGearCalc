package br.nom.figueiredo.sergio.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Números que pertence ao conjunto dos Reais.
 */
public interface Real extends Comparable<Real> {
    BigInteger getNumerador();
    BigInteger getDenominador();

    Real add(Real other);

    Real subtract(Real other);

    Real multiply(Real other);

    Real divide(Real other);

    Real simplify();

    Real multiply(long multiplier);

    Real multiply(double multiplier);

    Real negate();

    double toDouble();

    BigDecimal toBigDecimal();

    Real adjustSignal();

    int compareTo(Real o);
}
