package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Real;
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

    private Real gearRadius;
    private Real addendum;
    private Real dedendum;
    private Real circularPitch;
    private Real workingDepth;
    private TeethGeometry teeth = null;

    public Real getGearRadius() {
        return gearRadius;
    }

    public void setGearRadius(Real gearRadius) {
        this.gearRadius = gearRadius;
    }

    public Real getAddendum() {
        return addendum;
    }

    public void setAddendum(Real addendum) {
        this.addendum = addendum;
    }

    public Real getDedendum() {
        return dedendum;
    }

    public void setDedendum(Real dedendum) {
        this.dedendum = dedendum;
    }

    public Real getCircularPitch() {
        return circularPitch;
    }

    public void setCircularPitch(Real circularPitch) {
        this.circularPitch = circularPitch;
    }

    public Real getWorkingDepth() {
        return workingDepth;
    }

    public void setWorkingDepth(Real workingDepth) {
        this.workingDepth = workingDepth;
    }

    public TeethGeometry getTeeth() {
        return teeth;
    }

    public void setTeeth(TeethGeometry teeth) {
        this.teeth = teeth;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("pitchRadius", gearRadius)
                .append("addendum", addendum)
                .append("dedendum", dedendum)
                .append("circularPitch", circularPitch)
                .append("workingDepth", workingDepth)
                .toString();
    }
}
