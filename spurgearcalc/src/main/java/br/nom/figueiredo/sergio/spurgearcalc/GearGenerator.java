package br.nom.figueiredo.sergio.spurgearcalc;

public class GearGenerator {

    private static final Rational ADDENDUM_MODULE = Rational.ONE;
    private static final Rational DEDENDUM_MODULE = new Rational(5, 4); // 1.25

    private GearGenerator() {
    }

    public static GearGeometry generate(GearParameters gearParameters) {
        GearGeometry geometry = new GearGeometry();

        // tamanho do raio de ação da engrenagem
        geometry.setPitchRadius(gearParameters.getModule()
                .multiply(gearParameters.getNumTeeth())
                .divide(new Rational(2)).simplify());

        // tamanho de um dente completo.
        geometry.setCircularPitch(new PIRational(gearParameters.getModule()));

        Rational addendum = gearParameters.getModule().multiply(ADDENDUM_MODULE).simplify();
        geometry.setAddendum(addendum);
        geometry.setDedendum(gearParameters.getModule().multiply(DEDENDUM_MODULE).simplify());

        geometry.setPitchPoint( new Point(Rational.ZERO, geometry.getPitchRadius()) );

        geometry.setTopPt1( geometry.getPitchPoint().add(new Point( gearParameters.getPressureAngle().multiply(Math.tan(addendum.toDouble())), addendum)) );

        return geometry;
    }

}
