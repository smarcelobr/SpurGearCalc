package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.math.Real;

public class GearGenerator {

    private static final Real ADDENDUM_MODULE = Rational.ONE;
    private static final Real DEDENDUM_MODULE = Rational.of(5, 4); // 1.25
    private static final Real WORKING_DEPTH_MODULE = Rational.of(2);
    private static final Real FILLET_RADIUS_FACTOR = Rational.of(38,100).simplify(); // 0.38

    private GearGenerator() {
    }

    public static GearGeometry generate(GearParameters gearParameters) {
        GearGeometry geometry = new GearGeometry();

        // tamanho do raio de ação da engrenagem
        Rational module = gearParameters.getModule();

        // gearDiameter = (module * numTeeth)
        // gearRadius = (module * numTeeth)/2
        geometry.setGearRadius(module
                .multiply(gearParameters.getNumTeeth())
                .divide(Rational.of(2)).simplify());

        // tamanho de um dente completo.
        geometry.setCircularPitch(module.multiply(Rational.PI));

        Real addendum = module.multiply(ADDENDUM_MODULE).simplify();
        geometry.setAddendum(addendum);
        Real dedendum = module.multiply(DEDENDUM_MODULE).simplify();
        geometry.setDedendum(dedendum);

        geometry.setWorkingDepth(module.multiply(WORKING_DEPTH_MODULE).simplify());

        TeethGeometry teeth = geometry.getTeeth();
        Rational tanPressureAngle = Rational.toRational(Math.tan(gearParameters.getPressureAngle().toDouble()));

        Vector vCircularPitch = new Vector(geometry.getCircularPitch(),Rational.ZERO);

        teeth.setPitchPoint( Point.of(Rational.ZERO, geometry.getGearRadius()) );
        teeth.setPitchPoint2( Point.of(geometry.getCircularPitch().divide(Rational.of(2)), geometry.getGearRadius()) );
        teeth.setPitchPoint3( Point.of(geometry.getCircularPitch(), geometry.getGearRadius()) );

        teeth.setTopPt1( teeth.getPitchPoint().add(Point.of( tanPressureAngle.multiply(addendum), addendum)) );
        teeth.setTopPt2( teeth.getPitchPoint2().add(Point.of( tanPressureAngle.multiply(addendum).negate(), addendum)) );

        teeth.setWorkPt1( teeth.getTopPt2().add(Point.of( tanPressureAngle.multiply(geometry.getWorkingDepth()), geometry.getWorkingDepth().negate())) );
        teeth.setWorkPt2( teeth.getTopPt1().add(vCircularPitch).add(Point.of( tanPressureAngle.multiply(geometry.getWorkingDepth()).negate(), geometry.getWorkingDepth().negate()))) ;

        Point rootClearancePt1 = teeth.getPitchPoint2().add(Point.of(tanPressureAngle.multiply(dedendum), dedendum.negate()));
        Point rootClearancePt2 = teeth.getPitchPoint3().add(Point.of(tanPressureAngle.multiply(dedendum).negate(), dedendum.negate()));

        Line rootLine = Line.of(rootClearancePt1, rootClearancePt2);
        Line face2Line = Line.of(teeth.getTopPt2(), rootClearancePt1);

        Vector rootVector = rootClearancePt1.vectorTo(rootClearancePt2).normalize();
        Vector surface2Vector = rootClearancePt1.vectorTo(teeth.getTopPt2()).normalize();
        Vector surface3Vector = rootClearancePt2.vectorTo(teeth.getPitchPoint3()).normalize();
        Vector fillet1Vector = rootVector.add(surface2Vector).normalize();
        Vector fillet2Vector = rootVector.inverse().add(surface3Vector).normalize();

        Rational filletRadius = module.multiply(FILLET_RADIUS_FACTOR);
        Point dedendumFillet1Center = rootClearancePt1.pointAt(fillet1Vector.multiply(filletRadius));
        Point dedendumFillet2Center = rootClearancePt2.pointAt(fillet2Vector.multiply(filletRadius));
        teeth.setDedendumFillet1Center(dedendumFillet1Center);
        teeth.setDedendumFillet2Center(dedendumFillet2Center);
        teeth.setDedendumFilletRadius(filletRadius);

        teeth.setRootClearancePt1(dedendumFillet1Center.nearestPointAt(rootLine));
        teeth.setRootClearancePt2(dedendumFillet2Center.nearestPointAt(rootLine));

        System.out.printf("fillet radius: %s\n", filletRadius.toDouble());
        System.out.printf("distancia da raiz do dente: %s\n", teeth.getDedendumFillet1Center().vectorTo(rootLine).magnitude());
        System.out.printf("distancia da superfície do dente: %s\n", teeth.getDedendumFillet1Center().vectorTo(face2Line).magnitude());

        return geometry;
    }

}
