package br.nom.figueiredo.sergio.spurgearcalc;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Baseado na referencia.
 *
 * https://khkgears.net/new/gear_knowledge/gear_technical_reference/involute_gear_profile.html
 *
 * Veja docs/REF2.md
 */
public class GearGeometry {

    private Value pitchRadius;
    private Value addendum;
    private Value dedendum;
    private Value circularPitch;
    private Value workingDepth;
    private final TeethGeometry teeth = new TeethGeometry();

    public Value getPitchRadius() {
        return pitchRadius;
    }

    public void setPitchRadius(Value pitchRadius) {
        this.pitchRadius = pitchRadius;
    }

    public Value getAddendum() {
        return addendum;
    }

    public void setAddendum(Value addendum) {
        this.addendum = addendum;
    }

    public Value getDedendum() {
        return dedendum;
    }

    public void setDedendum(Value dedendum) {
        this.dedendum = dedendum;
    }

    public Value getCircularPitch() {
        return circularPitch;
    }

    public void setCircularPitch(Value circularPitch) {
        this.circularPitch = circularPitch;
    }

    public Value getWorkingDepth() {
        return workingDepth;
    }

    public void setWorkingDepth(Value workingDepth) {
        this.workingDepth = workingDepth;
    }

    public TeethGeometry getTeeth() {
        return teeth;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("pitchRadius", pitchRadius)
                .append("addendum", addendum)
                .append("dedendum", dedendum)
                .append("circularPitch", circularPitch)
                .append("workingDepth", workingDepth)
                .toString();
    }
}
