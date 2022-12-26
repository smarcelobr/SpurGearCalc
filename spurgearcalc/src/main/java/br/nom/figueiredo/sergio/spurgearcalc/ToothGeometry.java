package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.math.Real;
import br.nom.figueiredo.sergio.spurgearcalc.svg.SVGPath;

import java.util.Locale;

import static java.util.Objects.isNull;

public class ToothGeometry {
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

    // all points
    private Point[] points;

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
                svgPath(scale, null).render(),
                scaledDedendumFillet1Center.getX().toDouble(), scaledDedendumFillet1Center.getY().toDouble(),
                scaledDedendumFillet2Center.getX().toDouble(), scaledDedendumFillet2Center.getY().toDouble());
    }

    public SVGPath svgPath(Rational scale, SVGPath svgPath) {

        for (Point pt : this.points) {
            Point ptScaled = pt.multiply(scale);
            if (isNull(svgPath)) {
                svgPath = (new SVGPath()).move(ptScaled);
            } else {
                svgPath = svgPath.line(ptScaled);
            }
        }

        return svgPath;
    }

    public ToothGeometry scale(Rational factor) {
        ToothGeometry scaled = new ToothGeometry();
        scaled.setPitchPoint(           this.getPitchPoint()           .multiply(factor));
        scaled.setPitchPoint2(          this.getPitchPoint2()          .multiply(factor));
        scaled.setPitchPoint3(          this.getPitchPoint3()          .multiply(factor));
        scaled.setTopPt1(               this.getTopPt1()               .multiply(factor));
        scaled.setTopPt2(               this.getTopPt2()               .multiply(factor));
        scaled.setWorkPt1(              this.getWorkPt1()              .multiply(factor));
        scaled.setWorkPt2(              this.getWorkPt2()              .multiply(factor));
        scaled.setRootClearancePt1(     this.getRootClearancePt1()     .multiply(factor));
        scaled.setRootClearancePt2(     this.getRootClearancePt2()     .multiply(factor));
        scaled.setDedendumFillet1Center(this.getDedendumFillet1Center().multiply(factor));
        scaled.setDedendumFillet2Center(this.getDedendumFillet2Center().multiply(factor));
        scaled.setDedendumFilletRadius( this.getDedendumFilletRadius() .multiply(factor));

        return scaled;
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

    public ToothGeometry involute(GearParameters gearParameters, int toothNumber) {
        Rational module = gearParameters.getModule();
        Real pitchRadius = module.multiply(gearParameters.getNumTeeth()).divide(Rational.of(2L));
        Real pitchArcPos = module.multiply(toothNumber).multiply(Rational.PI);
        ToothGeometry involuted = new ToothGeometry();

        involuted.setPitchPoint(           involute(this.getPitchPoint()           ,pitchRadius, pitchArcPos));
        involuted.setPitchPoint2(          involute(this.getPitchPoint2()          ,pitchRadius, pitchArcPos));
        involuted.setPitchPoint3(          involute(this.getPitchPoint3()          ,pitchRadius, pitchArcPos));
        involuted.setTopPt1(               involute(this.getTopPt1()               ,pitchRadius, pitchArcPos));
        involuted.setTopPt2(               involute(this.getTopPt2()               ,pitchRadius, pitchArcPos));
        involuted.setWorkPt1(              involute(this.getWorkPt1()              ,pitchRadius, pitchArcPos));
        involuted.setWorkPt2(              involute(this.getWorkPt2()              ,pitchRadius, pitchArcPos));
        involuted.setRootClearancePt1(     involute(this.getRootClearancePt1()     ,pitchRadius, pitchArcPos));
        involuted.setRootClearancePt2(     involute(this.getRootClearancePt2()     ,pitchRadius, pitchArcPos));
        involuted.setDedendumFillet1Center(involute(this.getDedendumFillet1Center(),pitchRadius, pitchArcPos));
        involuted.setDedendumFillet2Center(involute(this.getDedendumFillet2Center(),pitchRadius, pitchArcPos));
        involuted.setDedendumFilletRadius( this.getDedendumFilletRadius() );

        involuted.points = new Point[this.points.length];
        for (int i=0; i<this.points.length; i++) {
            involuted.points[i] = involute(this.points[i], pitchRadius, pitchArcPos);
        }

        return involuted;
    }

    private Point involute(Point pt, Real pitchRadius, Real pitchArcPos) {
//        return Point.of(pt.getX().add(pitchArcPos).simplify(), pt.getY().simplify());

        Real radiansAngle = pt.getX().add(pitchArcPos).divide(pitchRadius).simplify();
        Real cosAngle = Rational.toRational( Math.cos( radiansAngle.toDouble() ) ).simplify();
        Real sinAngle = Rational.toRational( Math.sin( radiansAngle.toDouble() ) ).simplify();

        return Point.of(pt.getY().multiply(cosAngle).simplify(), pt.getY().multiply(sinAngle).simplify());
    }

    public void interpolation() {

        this.points = new Point[60];
        this.points[0] = this.getPitchPoint();
        this.points[7] = this.getTopPt1();
        this.points[12] = this.getTopPt2();
        this.points[18] = this.getPitchPoint2();
        this.points[27] = this.getWorkPt1();
        this.points[36] = this.getRootClearancePt1();
        this.points[42] = this.getRootClearancePt2();
        this.points[51] = this.getWorkPt2();
        this.points[59] = this.getPitchPoint3();

        linearInterpolation(0, 7);
        linearInterpolation(7, 12);
        linearInterpolation(12, 18);
        linearInterpolation(18, 27);
        arcInterpolation(27, 36, this.dedendumFilletRadius);
        linearInterpolation(36, 42);
        arcInterpolation(42, 51, this.dedendumFilletRadius);
        linearInterpolation(51, 59);

    }

    private void arcInterpolation(int start, int end, Rational radius) {
        linearInterpolation(start, end);
    }

    private void linearInterpolation(int start, int end) {
        Point[] pts = Line.interpolation(this.points[start], this.points[end], end - start -1);
        System.arraycopy(pts, 0, this.points, start+1, pts.length);
    }
}
