package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.spurgearcalc.svg.SVGPath;

import java.util.Locale;

public class TeethGeometry {
    private Point pitchPoint;
    private Point topPt1;
    private Point pitchPoint2;
    private Point topPt2;
    private Point pitchPoint3;
    private Point workPt1;
    private Point dedendumFillet1Center;
    private Point rootClearancePt1;
    private Point rootClearancePt2;
    private Point workPt2;
    private Rational dedendumFilletRadius;
    private Point dedendumFillet2Center;

    public Point getPitchPoint() {
        return pitchPoint;
    }

    public void setPitchPoint(Point pitchPoint) {
        this.pitchPoint = pitchPoint;
    }

    public Point getTopPt1() {
        return topPt1;
    }

    public void setTopPt1(Point topPt1) {
        this.topPt1 = topPt1;
    }

    public void setPitchPoint2(Point pitchPoint2) {
        this.pitchPoint2 = pitchPoint2;
    }

    public Point getPitchPoint2() {
        return pitchPoint2;
    }

    public void setTopPt2(Point topPt2) {
        this.topPt2 = topPt2;
    }

    public Point getTopPt2() {
        return topPt2;
    }

    public void setPitchPoint3(Point pitchPoint3) {
        this.pitchPoint3 = pitchPoint3;
    }

    public Point getPitchPoint3() {
        return pitchPoint3;
    }

    /**
     * Exemplo de HTML para ver o SVG em ação:
     *
     * <pre>
     *     
     * &lt;!DOCTYPE html&gt;
     * &lt;html&gt;
     * &lt;body&gt;
     *
     * &lt;svg height="210" width="400"&gt;
     *   &lt;path d="M0.000000 96.000000 L5.823524 112.000000 L19.309217 112.000000 L25.132741 96.000000 L50.265482 96.000000" stroke="red" stroke-width="1" fill="none" /&gt;
     *   Sorry, your browser does not support inline SVG.
     * &lt;/svg&gt;
     *
     * &lt;/body&gt;
     * &lt;/html&gt;
     *     
     * </pre>
     * @param scale aumento ou redução na apresentacao.
     *
     * @return pagina html
     */
    public String html(Rational scale) {

        String htmlTemplate = """
<!DOCTYPE html>
<html>
<body>
   <svg height="400" width="400">
      <path id="lineBC" d="%1$s"
            stroke="red" stroke-width="1" fill="none"/>
      <circle cx="%2$f" cy="%3$f" r="1" stroke="blue" stroke-width="0" fill="blue"/>
      <circle cx="%4$f" cy="%5$f" r="1" stroke="green" stroke-width="0" fill="blue"/>
      Sorry, your browser does not support inline SVG.
   </svg>
</body>
</html>
""";

        Point scaledDedendumFillet1Center = this.dedendumFillet1Center.multiply(scale);
        Point scaledDedendumFillet2Center = this.dedendumFillet2Center.multiply(scale);
        return String.format(Locale.ENGLISH, htmlTemplate,
                svgPath(scale).render(),
                scaledDedendumFillet1Center.getX().toDouble(), scaledDedendumFillet1Center.getY().toDouble(),
                scaledDedendumFillet2Center.getX().toDouble(), scaledDedendumFillet2Center.getY().toDouble());
    }

    public SVGPath svgPath(Rational scale) {

        SVGPath svgPath = new SVGPath();
        svgPath.move(this.getPitchPoint().multiply(scale))
                .line(this.getTopPt1().multiply(scale))
                .line(this.getTopPt2().multiply(scale))
                .line(this.getPitchPoint2().multiply(scale))
                .line(this.getWorkPt1().multiply(scale))
                .arc(this.dedendumFilletRadius.multiply(scale), this.dedendumFilletRadius.multiply(scale),
                        0, 0, 1,
                        this.getRootClearancePt1().multiply(scale))
                .line(this.getRootClearancePt2().multiply(scale))
                .arc(this.dedendumFilletRadius.multiply(scale), this.dedendumFilletRadius.multiply(scale),
                        0, 0, 1,
                        this.getWorkPt2().multiply(scale))
                .line(this.getPitchPoint3().multiply(scale));

        return svgPath;
    }

    public void setWorkPt1(Point workPt1) {
        this.workPt1 = workPt1;
    }

    public Point getWorkPt1() {
        return workPt1;
    }

    public void setDedendumFillet1Center(Point dedendumFillet1Center) {
        this.dedendumFillet1Center = dedendumFillet1Center;
    }

    public Point getDedendumFillet1Center() {
        return dedendumFillet1Center;
    }

    public void setRootClearancePt1(Point rootClearancePt1) {
        this.rootClearancePt1 = rootClearancePt1;
    }

    public Point getRootClearancePt1() {
        return rootClearancePt1;
    }

    public void setRootClearancePt2(Point rootClearancePt2) {
        this.rootClearancePt2 = rootClearancePt2;
    }

    public Point getRootClearancePt2() {
        return rootClearancePt2;
    }

    public void setWorkPt2(Point workPt2) {
        this.workPt2 = workPt2;
    }

    public Point getWorkPt2() {
        return workPt2;
    }

    public void setDedendumFilletRadius(Rational dedendumFilletRadius) {
        this.dedendumFilletRadius = dedendumFilletRadius;
    }

    public Rational getDedendumFilletRadius() {
        return dedendumFilletRadius;
    }

    public void setDedendumFillet2Center(Point dedendumFillet2Center) {
        this.dedendumFillet2Center = dedendumFillet2Center;
    }

    public Point getDedendumFillet2Center() {
        return dedendumFillet2Center;
    }
}
