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

    private final GearParameters parameters;

    private Real gearRadius;
    private Real addendum;
    private Real dedendum;
    private Real circularPitch;
    private Real workingDepth;

    private Real baseCircleRadius;

    private ToothGeometry toothProfile;

    private Point[][] teeth;
    private Real cosPressureAngle;

    public GearGeometry(GearParameters parameters) {
        this.parameters = parameters;
    }

    public GearParameters getParameters() {
        return parameters;
    }

    public Real getGearRadius() {
        return gearRadius;
    }

    /**
     * Raio da engrenagem.
     *
     * @param gearRadius raio da engrenagem
     */
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

    /**
     * Tamanho (arco) de um dente. =module * pi
     * @return Tamanho (arco) de um dente
     */
    public Real getCircularPitch() {
        return circularPitch;
    }

    /**
     * seta o tamanho (arco) de um dente.
     *
     * @param circularPitch tamanho (arco) de um dente.
     */
    public void setCircularPitch(Real circularPitch) {
        this.circularPitch = circularPitch;
    }

    public Real getWorkingDepth() {
        return workingDepth;
    }

    public void setWorkingDepth(Real workingDepth) {
        this.workingDepth = workingDepth;
    }

    public Real getBaseCircleRadius() {
        return baseCircleRadius;
    }

    public void setBaseCircleRadius(Real baseCircleRadius) {
        this.baseCircleRadius = baseCircleRadius;
    }

    public ToothGeometry getToothProfile() {
        return toothProfile;
    }

    public void setToothProfile(ToothGeometry toothProfile) {
        this.toothProfile = toothProfile;
    }

    public Point[][] getTeeth() {
        return teeth;
    }

    public void setTeeth(Point[][] teeth) {
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

    public void setCosPressureAngle(Real cosPressureAngle) {
        this.cosPressureAngle = cosPressureAngle;
    }

    public Real getCosPressureAngle() {
        return cosPressureAngle;
    }
}
