package br.nom.figueiredo.sergio.spurgearcalc;

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
     * @param scale
     * @return
     */
    public String svgPath(int scale) {

        String htmlTemplate = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<svg height=\"400\" width=\"400\">\n" +
                " <path id=\"lineBC\" d=\"M%1$f %2$f L%3$f %4$f L%5$f %6$f L%7$f %8$f " +
                "L%9$f %10$f A%23$f %23$f 0 0 1 %11$f %12$f " +
                "L%13$f %14$f A%23$f %23$f 0 0 1 %15$f %16$f L%17$f %18$f\" \n" +
                "  stroke=\"red\" stroke-width=\"1\" fill=\"none\" />\n" +
                "  <circle cx=\"%19$f\" cy=\"%20$f\" r=\"1\" stroke=\"blue\" stroke-width=\"1\" fill=\"blue\" />\n" +
                "  <circle cx=\"%21$f\" cy=\"%22$f\" r=\"1\" stroke=\"green\" stroke-width=\"1\" fill=\"blue\" />\n" +
                "  Sorry, your browser does not support inline SVG.  \n" +
                "</svg> \n" +
                " \n" +
                "</body>\n" +
                "</html>";

        return String.format(Locale.ENGLISH, htmlTemplate,
                this.getPitchPoint().getX()*scale,this.getPitchPoint().getY()*scale, // 1,2
                this.getTopPt1().getX()*scale,this.getTopPt1().getY()*scale,               // 3,4
                this.getTopPt2().getX()*scale,this.getTopPt2().getY()*scale,               // 5,6
                this.getPitchPoint2().getX()*scale,this.getPitchPoint2().getY()*scale,     // 7,8
                this.getWorkPt1().getX()*scale, this.getWorkPt1().getY()*scale,            //9,10
                this.getRootClearancePt1().getX()*scale, this.getRootClearancePt1().getY()*scale, // 11,12
                this.getRootClearancePt2().getX()*scale, this.getRootClearancePt2().getY()*scale, // 13,14
                this.getWorkPt2().getX()*scale, this.getWorkPt2().getY()*scale,            // 15,16
                this.getPitchPoint3().getX()*scale, this.getPitchPoint3().getY()*scale,    // 17,18
                this.dedendumFillet1Center.getX()*scale, this.dedendumFillet1Center.getY()*scale, // 19,20
                this.dedendumFillet2Center.getX()*scale, this.dedendumFillet2Center.getY()*scale, // 21,22
                this.dedendumFilletRadius.toDouble()*scale  // 23
                );
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
