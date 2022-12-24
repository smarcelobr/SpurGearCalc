package br.nom.figueiredo.sergio.spurgearcalc;

public interface Value {
    long getNumerador();
    long getDenominador();

    Value add(Value other);

    Value subtract(Value other);

    Value multiply(Value other);

    Value divide(Value other);

    Value simplify();

    Value multiply(long multiplier);

    Value multiply(double multiplier);

    Value negate();

    double toDouble();

    Value adjustSignal();
}
