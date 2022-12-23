package br.nom.figueiredo.sergio.spurgearcalc;

public class GearGenerator {

    private static final Rational ADDENDUM_MODULE = Rational.ONE;
    private static final Rational DEDENDUM_MODULE = new Rational(5, 4); // 1.25
    private static final Rational WORKING_DEPTH_MODULE = new Rational(2);
    private static final Rational FILLET_RADIUS_FACTOR = new Rational(38,100).simplify(); // 0.38

    private GearGenerator() {
    }

    public static GearGeometry generate(GearParameters gearParameters) {
        GearGeometry geometry = new GearGeometry();

        // tamanho do raio de ação da engrenagem
        Rational module = gearParameters.getModule();
        geometry.setPitchRadius(module
                .multiply(gearParameters.getNumTeeth())
                .divide(new Rational(2)).simplify());

        // tamanho de um dente completo.
        geometry.setCircularPitch(new PIRational(module));

        Rational addendum = module.multiply(ADDENDUM_MODULE).simplify();
        geometry.setAddendum(addendum);
        Rational dedendum = module.multiply(DEDENDUM_MODULE).simplify();
        geometry.setDedendum(dedendum);

        geometry.setWorkingDepth(module.multiply(WORKING_DEPTH_MODULE).simplify());

        TeethGeometry teeth = geometry.getTeeth();
        Rational tanPressureAngle = Rational.toRational(Math.tan(gearParameters.getPressureAngle().toDouble()));

        Vector vCircularPitch = new Vector(geometry.getCircularPitch(),Rational.ZERO);

        teeth.setPitchPoint( new Point(Rational.ZERO, geometry.getPitchRadius()) );
        teeth.setPitchPoint2( new Point(geometry.getCircularPitch().divide(new Rational(2)), geometry.getPitchRadius()) );
        teeth.setPitchPoint3( new Point(geometry.getCircularPitch(), geometry.getPitchRadius()) );

        teeth.setTopPt1( teeth.getPitchPoint().add(new Point( tanPressureAngle.multiply(addendum), addendum)) );
        teeth.setTopPt2( teeth.getPitchPoint2().add(new Point( tanPressureAngle.multiply(addendum).negate(), addendum)) );

        teeth.setWorkPt1( teeth.getTopPt2().add(new Point( tanPressureAngle.multiply(geometry.getWorkingDepth()), geometry.getWorkingDepth().negate())) );
        teeth.setWorkPt2( teeth.getTopPt1().add(vCircularPitch).add(new Point( tanPressureAngle.multiply(geometry.getWorkingDepth()).negate(), geometry.getWorkingDepth().negate()))) ;

        Point rootClearancePt1 = teeth.getPitchPoint2().add(new Point(tanPressureAngle.multiply(dedendum), dedendum.negate()));
        Point rootClearancePt2 = teeth.getPitchPoint3().add(new Point(tanPressureAngle.multiply(dedendum).negate(), dedendum.negate()));

        Line rootLine = new Line(rootClearancePt1, rootClearancePt2);
        Line face2Line = new Line(teeth.getTopPt2(), rootClearancePt1);

        Vector rootVector = rootClearancePt1.vectorTo(rootClearancePt2).normalize();
        Vector surface2Vector = rootClearancePt1.vectorTo(teeth.getTopPt2()).normalize();
        Vector fillet1Vector = rootVector.add(surface2Vector).normalize();

        Rational filletRadius = module.multiply(FILLET_RADIUS_FACTOR);
        Point dedendumFillet1Center = rootClearancePt1.pointAt(fillet1Vector.multiply(filletRadius.toDouble()));
        teeth.setDedendumFillet1Center(dedendumFillet1Center);
        teeth.setDedendumFilletRadius(filletRadius);

        teeth.setRootClearancePt1(dedendumFillet1Center.nearestPointAt(rootLine));
        teeth.setRootClearancePt2(rootClearancePt2);

        System.out.printf("fillet radius: %s\n", filletRadius.toDouble());
        System.out.printf("distancia da raiz do dente: %f\n", teeth.getDedendumFillet1Center().vectorTo(rootLine).magnitude());
        System.out.printf("distancia da superfície do dente: %f\n", teeth.getDedendumFillet1Center().vectorTo(face2Line).magnitude());

        // teeth.setRootLine( teeth.getTopPt2().add(new Point( Math.tan(gearParameters.getPressureAngle().toDouble())*addendum.toDouble(), addendum.toDouble())) );
        // teeth.setWorkPt2( teeth.getPitchPoint2().add(new Point( -Math.tan(gearParameters.getPressureAngle().toDouble())*addendum.toDouble(), addendum.toDouble())) );
        // teeth.setRootPt2( teeth.getPitchPoint2().add(new Point( -Math.tan(gearParameters.getPressureAngle().toDouble())*addendum.toDouble(), addendum.toDouble())) );

        return geometry;
    }

}
