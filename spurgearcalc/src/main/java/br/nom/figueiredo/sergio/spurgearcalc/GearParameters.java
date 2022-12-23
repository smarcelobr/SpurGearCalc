package br.nom.figueiredo.sergio.spurgearcalc;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GearParameters {

    private final Integer numTeeth;
    private final Rational module;
    private final PIRational pressureAngle; // em radians

    public GearParameters(Integer numTeeth, Rational module) {
        this.numTeeth = numTeeth;
        this.module = module;
        this.pressureAngle = new PIRational(new Rational(1,9)); // 20° ou PI/9
    }

    public Integer getNumTeeth() {
        return numTeeth;
    }

    public Rational getModule() {
        return module;
    }

    public PIRational getPressureAngle() {
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
