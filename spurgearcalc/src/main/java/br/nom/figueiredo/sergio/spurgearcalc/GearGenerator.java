package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.math.Real;

public class GearGenerator {

    private static final Real ADDENDUM_MODULE = Rational.ONE;
    private static final Real DEDENDUM_MODULE = Rational.of(5, 4); // 1.25
    private static final Real WORKING_DEPTH_MODULE = Rational.of(2);
    private static final Real FILLET_RADIUS_FACTOR = Rational.of(38,100).simplify(); // 0.38
    public static final int TOOTH_POINTS = 60;

    private GearGenerator() {
    }

    public static GearGeometry generate(GearParameters gearParameters) {
        GearGeometry geometry = new GearGeometry(gearParameters);

        // tamanho do raio de ação da engrenagem
        Rational module = gearParameters.getModule();

        Rational tanPressureAngle = Rational.toRational(Math.tan(gearParameters.getPressureAngle().toDouble()));
        Rational cosPressureAngle = Rational.toRational(Math.cos(gearParameters.getPressureAngle().toDouble()));
        geometry.setCosPressureAngle(cosPressureAngle);

        // gearDiameter = (module * numTeeth)
        // gearRadius = (module * numTeeth)/2
        Rational gearRadius = module
                .multiply(gearParameters.getNumTeeth())
                .divide(Rational.of(2)).simplify();
        geometry.setGearRadius(gearRadius);

        // tamanho de um dente completo.
        Real circularPitch = module.multiply(Rational.PI);
        geometry.setCircularPitch(circularPitch);

        geometry.setBaseCircleRadius(gearRadius.multiply(cosPressureAngle));

        Real circularBasePitch = geometry.getBaseCircleRadius()
                .multiply(2L) // Diametro do BaseCircle
                .divide(geometry.getParameters().getNumTeeth())
                .multiply(Rational.PI);

        Real addendum = module.multiply(ADDENDUM_MODULE).simplify();
        geometry.setAddendum(addendum);
        Real dedendum = module.multiply(DEDENDUM_MODULE).simplify();
        geometry.setDedendum(dedendum);

        geometry.setWorkingDepth(module.multiply(WORKING_DEPTH_MODULE).simplify());

        ToothGeometry rackToothProfile = new ToothGeometry();
        geometry.setToothProfile(rackToothProfile);

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

        interpolation(geometry.getToothProfile());

//        System.out.printf("fillet radius: %s\n", filletRadius.toDouble());
//        System.out.printf("distancia da raiz do dente: %s\n", rackToothProfile.getDedendumFillet1Center().vectorTo(rootLine).magnitude());
//        System.out.printf("distancia da superfície do dente: %s\n", rackToothProfile.getDedendumFillet1Center().vectorTo(face2Line).magnitude());

        // para cada dente, gera os pontos já na posição final
        //Point[][] teeth = new Point[1][TOOTH_POINTS];
        Point[][] teeth = new Point[gearParameters.getNumTeeth()][TOOTH_POINTS];
        geometry.setTeeth(teeth);

        for (int t=0; t< teeth.length; t++) {
            teeth[t] = involute(geometry, t);
        }

        return geometry;
    }

    /**
     * Aumenta os pontos do profile para dar efeito melhor de encurvado quando
     * houver o involute.
     *
     * @param toothProfile
     */
    public static void interpolation(ToothGeometry toothProfile) {

        Point[] points = new Point[TOOTH_POINTS];
        points[0] = toothProfile.getPitchPoint();
        points[7] = toothProfile.getTopPt1();
        points[12] = toothProfile.getTopPt2();
        points[18] = toothProfile.getPitchPoint2();
        points[27] = toothProfile.getWorkPt1();
        points[36] = toothProfile.getRootClearancePt1();
        points[42] = toothProfile.getRootClearancePt2();
        points[51] = toothProfile.getWorkPt2();
        points[59] = toothProfile.getPitchPoint3();

        linearInterpolation(points, 0, 7);
        linearInterpolation(points, 7, 12);
        linearInterpolation(points, 12, 18);
        linearInterpolation(points, 18, 27);
        arcInterpolation(points, 27, 36, toothProfile.getDedendumFilletRadius());
        linearInterpolation(points, 36, 42);
        arcInterpolation(points, 42, 51, toothProfile.getDedendumFilletRadius());
        linearInterpolation(points, 51, 59);

        toothProfile.setPoints(points);
    }

    private static void arcInterpolation(Point[] points, int start, int end, Rational radius) {
        linearInterpolation(points, start, end);
        // TODO implementar melhor.
    }

    private static void linearInterpolation(Point[] points, int start, int end) {
        Point[] pts = Line.interpolation(points[start], points[end], end - start -1);
        System.arraycopy(pts, 0, points, start+1, pts.length);
    }

    public static Point[] involute(GearGeometry geometry, int toothNumber) {
        Point[] toothProfile = geometry.getToothProfile().getPoints();
        Point[] tooth = new Point[toothProfile.length];

        for (int i=0; i<toothProfile.length; i++) {
            tooth[i] = involute(toothProfile[i], geometry, toothNumber);
        }

        return tooth;
    }

    /**
     * The tooth point in Involute Curve
     *
     * ref.: docs/REF2.md
     *
     */
    private static Point involute(Point pt, GearGeometry geometry, int toothNumber) {
//        return Point.of(pt.getX().add(pitchArcPos).simplify(), pt.getY().simplify());

        Real pitchArcPos = geometry.getCircularPitch().multiply(toothNumber);
        final Real arcOffset = pitchArcPos.add(pt.getX());

//        Real pitchArcPos = geometry.getBaseCircleRadius()
//                .multiply(2L) // Diametro do BaseCircle
//                .divide(geometry.getParameters().getNumTeeth()) // modulo na BaseCircle
//                .multiply(toothNumber) // Posicao do dente
//                .multiply(Rational.PI); // tamanho do arco do dente na base Circle
//        Real xBaseCircle = pt.getX().multiply(geometry.getCosPressureAngle());
//        final Real arcOffset = pitchArcPos.add(xBaseCircle); // será que está certo?

        final double radiansAngle;

        Real baseCircleRadius = geometry.getBaseCircleRadius();

        if (baseCircleRadius.compareTo(pt.getY()) <= 0 && false) { // a base circle limita o ponto de rotação dos dentes

            // alpha = arccos( rb / r )
            double alpha = Math.acos(baseCircleRadius.divide(pt.getY()).toDouble()) + arcOffset.toDouble();

            // inv alpha = tan (alpha) - alpha
            double invAlpha = Math.tan(alpha) - alpha;

            radiansAngle = invAlpha ;

        } else {
//            radiansAngle = arcOffset.divide(baseCircleRadius).toDouble();
            radiansAngle = arcOffset.divide(geometry.getGearRadius()).toDouble();
        }
        Real cosAngle = Rational.toRational(Math.cos(radiansAngle)).simplify();
        Real sinAngle = Rational.toRational(Math.sin(radiansAngle)).simplify();

        return Point.of(pt.getY().multiply(cosAngle).simplify(), pt.getY().multiply(sinAngle).simplify());
    }

}
