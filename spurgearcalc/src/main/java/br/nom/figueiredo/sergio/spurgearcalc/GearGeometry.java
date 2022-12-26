package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.math.Real;
import br.nom.figueiredo.sergio.spurgearcalc.svg.SVGPath;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Locale;

import static java.util.Objects.nonNull;

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
    private final ToothGeometry[] toothArray;

    public GearGeometry(GearParameters parameters) {
        this.parameters = parameters;
        this.toothArray = new ToothGeometry[parameters.getNumTeeth()];
    }

    public GearParameters getParameters() {
        return parameters;
    }

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

    public ToothGeometry getTooth(int toothNumber) {
        return toothArray[toothNumber];
    }

    public void setTooth(int toothNumber, ToothGeometry tooth) {
        this.toothArray[toothNumber] = tooth;
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

    public String teethAsHtml(Rational scale) {

        SVGPath svgPath = null;
        for (int t=0; t< parameters.getNumTeeth(); t++) {
            svgPath = this.toothArray[t].svgPath(scale, svgPath);
        }

        String htmlTemplate = """
<!DOCTYPE html>
<html>
<body>
   <svg height="%2$f" width="%3$f" viewBox="%4$f %5$f %2$f %3$f">
      <path id="lineBC" d="%1$s"
            stroke="red" stroke-width="1" fill="none"/>
      Sorry, your browser does not support inline SVG.
   </svg>
</body>
</html>
""";

        if (nonNull(svgPath)) {
            Rational margem = scale.multiply(10);
            Rational metadeMargem = margem.divide(Rational.of(2));
            return String.format(Locale.ENGLISH, htmlTemplate,
                    svgPath.render(), // 1$s
                    margem.add(svgPath.getHeight()).toDouble(), // 2$f
                    margem.add(svgPath.getWidth()).toDouble(), // 3$f
                    svgPath.getTopLeft().getX().subtract(metadeMargem).toDouble(), // 4$f
                    svgPath.getTopLeft().getY().subtract(metadeMargem).toDouble()); // 5$f
        } else {
            throw new IllegalStateException("não esperado chegar aqui.");
        }
    }
}
