package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GearParameters {

    private final Integer numTeeth;
    private final Rational module;
    private final Rational pressureAngle; // em radians

    public GearParameters(Integer numTeeth, Rational module) {
        this.numTeeth = numTeeth;
        this.module = module;
        this.pressureAngle = Rational.of(1,9).multiply(Rational.PI); // 20° ou PI/9
    }

    public Integer getNumTeeth() {
        return numTeeth;
    }

    public Rational getModule() {
        return module;
    }

    public Rational getPressureAngle() {
        return pressureAngle;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("numTeeth", numTeeth)
                .append("module", module)
                .append("pressureAngle", pressureAngle)
                .toString();
    }
}
