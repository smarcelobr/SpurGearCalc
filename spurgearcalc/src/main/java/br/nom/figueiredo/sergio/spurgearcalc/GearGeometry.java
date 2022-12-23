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

    private Rational pitchRadius;
    private Rational addendum;
    private Rational dedendum;
    private PIRational circularPitch;
    private Rational workingDepth;
    private final TeethGeometry teeth = new TeethGeometry();

    public Rational getAddendum() {
        return addendum;
    }

    public void setAddendum(Rational addendum) {
        this.addendum = addendum;
    }

    public Rational getDedendum() {
        return dedendum;
    }

    public void setDedendum(Rational dedendum) {
        this.dedendum = dedendum;
    }

    public PIRational getCircularPitch() {
        return circularPitch;
    }

    public void setCircularPitch(PIRational circularPitch) {
        this.circularPitch = circularPitch;
    }

    public Rational getPitchRadius() {
        return pitchRadius;
    }

    public void setPitchRadius(Rational pitchRadius) {
        this.pitchRadius = pitchRadius;
    }

    public TeethGeometry getTeeth() {
        return teeth;
    }

    public Rational getWorkingDepth() {
        return workingDepth;
    }

    public void setWorkingDepth(Rational workingDepth) {
        this.workingDepth = workingDepth;
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
