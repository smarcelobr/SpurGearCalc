package br.nom.figueiredo.sergio.spurgearcalc;

public class GearGeometry {

    private Rational pitchRadius;
    private Rational addendum;
    private Rational dedendum;
    private PIRational circularPitch;
    private Point pitchPoint;
    private Point topPt1;

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

    public void setPitchPoint(Point pitchPoint) {
        this.pitchPoint = pitchPoint;
    }

    public Point getPitchPoint() {
        return pitchPoint;
    }

    public void setTopPt1(Point topPt1) {
        this.topPt1 = topPt1;
    }

    public Point getTopPt1() {
        return topPt1;
    }
}
