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
        return String.format(Locale.ENGLISH, "M%f %f L%f %f L%f %f L%f %f L%f %f L%f %f L%f %f L%f %f L%f %f",
                this.getPitchPoint().getX()*scale,this.getPitchPoint().getY()*scale,
                this.getTopPt1().getX()*scale,this.getTopPt1().getY()*scale,
                this.getTopPt2().getX()*scale,this.getTopPt2().getY()*scale,
                this.getPitchPoint2().getX()*scale,this.getPitchPoint2().getY()*scale,
                this.getWorkPt1().getX()*scale, this.getWorkPt1().getY()*scale,
                this.getRootClearancePt1().getX()*scale, this.getRootClearancePt1().getY()*scale,
                this.getRootClearancePt2().getX()*scale, this.getRootClearancePt2().getY()*scale,
                this.getWorkPt2().getX()*scale, this.getWorkPt2().getY()*scale,
                this.getPitchPoint3().getX()*scale, this.getPitchPoint3().getY()*scale);
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
}
