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

    public static GearGeometry generateToothProfile(GearParameters gearParameters) {
        GearGeometry geometry = new GearGeometry(gearParameters);

        // tamanho do raio de ação da engrenagem
        Rational module = gearParameters.getModule();

        // gearDiameter = (module * numTeeth)
        // gearRadius = (module * numTeeth)/2
        geometry.setGearRadius(module
                .multiply(gearParameters.getNumTeeth())
                .divide(Rational.of(2)).simplify());

        // tamanho de um dente completo.
        Real circularPitch = module.multiply(Rational.PI);
        geometry.setCircularPitch(geometry.getCircularPitch());

        Real addendum = module.multiply(ADDENDUM_MODULE).simplify();
        geometry.setAddendum(addendum);
        Real dedendum = module.multiply(DEDENDUM_MODULE).simplify();
        geometry.setDedendum(dedendum);

        geometry.setWorkingDepth(module.multiply(WORKING_DEPTH_MODULE).simplify());

        ToothGeometry rackToothProfile = new ToothGeometry();
        Rational tanPressureAngle = Rational.toRational(Math.tan(gearParameters.getPressureAngle().toDouble()));

        Vector vCircularPitch = new Vector(circularPitch,Rational.ZERO);

        rackToothProfile.setPitchPoint( Point.of(Rational.ZERO, geometry.getGearRadius()) );
        rackToothProfile.setPitchPoint2( Point.of(circularPitch.divide(Rational.of(2)), geometry.getGearRadius()) );
        rackToothProfile.setPitchPoint3( Point.of(circularPitch, geometry.getGearRadius()) );

        rackToothProfile.setTopPt1( rackToothProfile.getPitchPoint().add(Point.of( tanPressureAngle.multiply(addendum), addendum)) );
        rackToothProfile.setTopPt2( rackToothProfile.getPitchPoint2().add(Point.of( tanPressureAngle.multiply(addendum).negate(), addendum)) );

        rackToothProfile.setWorkPt1( rackToothProfile.getTopPt2().add(Point.of( tanPressureAngle.multiply(geometry.getWorkingDepth()), geometry.getWorkingDepth().negate())) );
        rackToothProfile.setWorkPt2( rackToothProfile.getTopPt1().add(vCircularPitch).add(Point.of( tanPressureAngle.multiply(geometry.getWorkingDepth()).negate(), geometry.getWorkingDepth().negate()))) ;

        Point rootClearancePt1 = rackToothProfile.getPitchPoint2().add(Point.of(tanPressureAngle.multiply(dedendum), dedendum.negate()));
        Point rootClearancePt2 = rackToothProfile.getPitchPoint3().add(Point.of(tanPressureAngle.multiply(dedendum).negate(), dedendum.negate()));

        Line rootLine = Line.of(rootClearancePt1, rootClearancePt2);
        Line face2Line = Line.of(rackToothProfile.getTopPt2(), rootClearancePt1);

        Vector rootVector = rootClearancePt1.vectorTo(rootClearancePt2).normalize();
        Vector surface2Vector = rootClearancePt1.vectorTo(rackToothProfile.getTopPt2()).normalize();
        Vector surface3Vector = rootClearancePt2.vectorTo(rackToothProfile.getPitchPoint3()).normalize();
        Vector fillet1Vector = rootVector.add(surface2Vector).normalize();
        Vector fillet2Vector = rootVector.inverse().add(surface3Vector).normalize();

        Rational filletRadius = module.multiply(FILLET_RADIUS_FACTOR);
        Point dedendumFillet1Center = rootClearancePt1.pointAt(fillet1Vector.multiply(filletRadius));
        Point dedendumFillet2Center = rootClearancePt2.pointAt(fillet2Vector.multiply(filletRadius));
        rackToothProfile.setDedendumFillet1Center(dedendumFillet1Center);
        rackToothProfile.setDedendumFillet2Center(dedendumFillet2Center);
        rackToothProfile.setDedendumFilletRadius(filletRadius);

        rackToothProfile.setRootClearancePt1(dedendumFillet1Center.nearestPointAt(rootLine));
        rackToothProfile.setRootClearancePt2(dedendumFillet2Center.nearestPointAt(rootLine));

        rackToothProfile.interpolation();

        System.out.printf("fillet radius: %s\n", filletRadius.toDouble());
        System.out.printf("distancia da raiz do dente: %s\n", rackToothProfile.getDedendumFillet1Center().vectorTo(rootLine).magnitude());
        System.out.printf("distancia da superfície do dente: %s\n", rackToothProfile.getDedendumFillet1Center().vectorTo(face2Line).magnitude());

        for (int t=0; t< gearParameters.getNumTeeth(); t++) {
            ToothGeometry gearToothProfile = rackToothProfile.involute(gearParameters, t);
            geometry.setTooth(t, gearToothProfile);
        }

        return geometry;
    }



}
