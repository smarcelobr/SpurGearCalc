package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Point;
import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.math.Real;
import br.nom.figueiredo.sergio.math.Vector;
import br.nom.figueiredo.sergio.spurgearcalc.svg.SVGPath;

import java.util.List;
import java.util.Locale;

import static java.util.Objects.isNull;

public class App {
    public App() {
    }

    public static void main(String[] args) {

        Rational module = Rational.of(8, 5); // 1.6 mm
        GearParameters gearParameters = new GearParameters(12, module);

        System.out.println("Geometry Params");
        System.out.println(gearParameters);

        GearGeometry gear1 = GearGenerator.generate(gearParameters);
        GearGeometry gear2 = GearGenerator.generate(new GearParameters(48, module));

        System.out.println("Geometry results");
        System.out.println(gear1);

        System.out.println("Tooth Path");

        // dpi: 96/pol = 96 pixels per 25.4mm
        Rational scale = Rational.of(960, 254);
        scale = Rational.of(20);
        System.out.println(gearAsHtml(gear1, gear2, scale));

        showFaceHtml(gear1);


    }

    private static void showFaceHtml(GearGeometry gear1) {
        System.out.println("*** FACE ***");

        Point orig = Point.of(0 ,0);

        SVGPath svgPath = getSvgPathFace(orig, gear1.getFace());

        String html = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <body>

                <hr/>
                <svg width="400" height="400" viewBox="-100 -100 300 300">
                <g transform="scale(10 -10)" stroke-width="0.1">
                    <circle r='%2$f' stroke='none' fill='blue' fill-opacity='0.7' />
                    <circle id='baseCircle'  r='%3$f' stroke='red'  stroke-dasharray='1 1' fill='none' stroke-opacity='0.7' />                    
                    <circle id='pitchCircle' r='%4$f' stroke='green' stroke-dasharray='1 1' fill='none' stroke-opacity='0.7' />
                    
                    <line id='Xaxis' stroke='black' stroke-opacity='0.5' x1='-10' y1='0' x2='10' y2='0'/>
                    <line id='Yaxis' stroke='black' stroke-opacity='0.5' x1='0' y1='-10' x2='0' y2='10'/>
                    <path d="%1$s" 
                        stroke="blue" fill="none" transform="matrix(1 0 0 1 %4$f 0)"/>
                         <!-- transform="translate(9.6 0)"-->
                    <path d="%1$s" 
                        stroke="red" fill="none" 
                        transform="scale(1 -1) matrix(%5$f %6$f %7$f %5$f 0 0) translate(%4$f 0)"/>
                        <!-- matrix ref: https://academo.org/demos/rotation-about-point/ -->
                </g>
                      Sorry, your browser does not support inline SVG.
                </svg>
                <hr/>

                </body>
                </html>
                """;

        Real angleOtherFace = gear1.getCircularPitch().divide(gear1.getGearRadius()).divide(-2);
        double sin = Math.sin(angleOtherFace.toDouble());
        System.out.printf(Locale.ENGLISH,
                html,
                svgPath.render(), // %1$s
                gear1.getGearRadius().subtract(gear1.getDedendum()).simplify().toDouble(), // %2$f
                gear1.getBaseCircleRadius().toDouble(), // %3$f
                gear1.getGearRadius().toDouble(), // %4$f
                Math.cos(angleOtherFace.toDouble()), // %5$f
                sin,  // %6$f
                -sin // %7$f
        );
    }

    private static SVGPath getSvgPathFace(Point orig, List<Vector> face) {
        SVGPath svgPath = new SVGPath();
        boolean first = true;
        for (Vector vec : face) {
            if (first) {
                svgPath.move(vec.add(orig));
                first=false;
            } else {
                svgPath.line(vec.add(orig));
            }
        }
        return svgPath;
    }

    public static String gearAsHtml(GearGeometry gear1, GearGeometry gear2, Rational scale) {

        Point[] pts = gear1.getToothProfile().getPoints();
        SVGPath pathToothProfile = new SVGPath();
        pathToothProfile.move(pts[0].multiply(scale));
        for (int i=1;i<pts.length;i++) {
            pathToothProfile.line(pts[i].multiply(scale));
        }

        SVGPath pathGear1 = svgPathGear(gear1, scale);
        SVGPath pathGear2 = svgPathGear(gear2, scale);

        String htmlTemplate = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <body>
                   <svg height="%3$f" width="%4$f" viewBox="%5$f %6$f %3$f %4$f">
<defs>
<path id="gearProfile" d="%7$s"
      stroke="blue" stroke-width="1" fill="none"/>
<path id="gear1" d="%1$s"
      stroke="none" stroke-width="1" fill="red" />
<path id="gear2" d="%2$s"
      stroke="none" stroke-width="1" fill="green"  />
</defs>
<use xlink:href="#gear1" x="0" y="0" fill-opacity="0.8">
<animateTransform
      attributeName="transform"
      attributeType="XML"
      type="rotate"
      from="0 0 0"
      to="%21$f 0 0"
      dur="10s"
      repeatCount="indefinite" />
</use>
<use xlink:href="#gear2" x="%12$f" y="0" fill-opacity="0.8">
<animateTransform
      attributeName="transform"
      attributeType="XML"
      type="rotate"
      from="0 %12$f 0"
      to="-%20$f %12$f 0"
      dur="10s"
      repeatCount="indefinite" />
</use>

<circle id="gear1_d" r="%8$f" stroke="blue" stroke-width="1" stroke-dasharray='10 10' fill="none" stroke-opacity="0.5"/>
<circle id="gear1_db" r="%9$f" stroke="olive" stroke-width="1" stroke-dasharray='10 10' fill="none" stroke-opacity="0.5"/>
<circle id="gear2_d" r="%10$f" stroke="blue" stroke-width="1" stroke-dasharray='10 10' fill="none" stroke-opacity="0.5"
    transform="translate(%12$f 0)" />
<circle id="gear2_db" r="%11$f" stroke="red" stroke-width="1" stroke-dasharray='10 10' fill="none" stroke-opacity="0.5"
    transform="translate(%12$f 0)" />

<g>
    <use xlink:href="#gearProfile" x="0" y="0" transform="translate(%13$f %22$f) rotate(90 0 0)" />
    <use xlink:href="#gearProfile" x="0" y="0" transform="translate(%13$f %16$f) rotate(90 0 0)" />
    <use xlink:href="#gearProfile" x="0" y="0" transform="translate(%13$f %15$f) rotate(90 0 0)" />
    <use xlink:href="#gearProfile" x="0" y="0" transform="translate(%13$f %14$f) rotate(90 0 0)" />
    <use xlink:href="#gearProfile" x="0" y="0" transform="translate(%13$f %17$f) rotate(90 0 0)" />
    <use xlink:href="#gearProfile" x="0" y="0" transform="translate(%13$f %18$f) rotate(90 0 0)" />
    <animateTransform
      attributeName="transform"
      attributeType="XML"
      type="translate"
      from="0 0"
      to="0 %19$f"
      dur="10s"
      repeatCount="indefinite" />
</g>
                      Sorry, your browser does not support inline SVG.
                   </svg>
                </body>
                </html>
                """;

        double anguloAnim = 30.0D; // 30 graus
            Rational margem = scale.multiply(10);
        Rational dois = Rational.of(2);
        Rational metadeMargem = margem.divide(dois);
        Real arcTooth = gear1.getCircularPitch().multiply(scale);
        return String.format(Locale.ENGLISH, htmlTemplate,
                    pathGear1.render(), // 1$s
                    pathGear2.render(), // 2$s
                    margem.add(pathGear1.getHeight().add(pathToothProfile.getHeight())).toDouble(), // 3$f
                    margem.add(pathGear1.getWidth().add(pathToothProfile.getWidth())).toDouble(), // 4$f
                    pathGear1.getTopLeft().getX().subtract(metadeMargem).toDouble(), // 5$f
                    pathGear1.getTopLeft().getY().subtract(metadeMargem).toDouble(), // 6$f
                    pathToothProfile.render(), // 7$f
                    gear1.getGearRadius().multiply(scale).toDouble(),// 8$f
                    gear1.getBaseCircleRadius().multiply(scale).toDouble(),// 9$f
                    gear2.getGearRadius().multiply(scale).toDouble(),// 10$f
                    gear2.getBaseCircleRadius().multiply(scale).toDouble(),// 11$f
                    gear1.getGearRadius().add(gear2.getGearRadius()).multiply(scale).toDouble(),// 12$f distancia entre eixos
                    gear1.getGearRadius().multiply(dois).multiply(scale).toDouble(), // 13$f - translate x profile tooth
                    arcTooth.divide(dois).toDouble(), // 14$f - translate y profile tooth
                    arcTooth.divide(dois).subtract(arcTooth).toDouble(), // 15$f - translate y profile tooth
                    arcTooth.divide(dois).subtract(arcTooth.multiply(dois)).toDouble(), // 16$f - translate y profile tooth
                    arcTooth.divide(dois).add(arcTooth).toDouble(), // 17$f - translate y profile tooth
                    arcTooth.divide(dois).add(arcTooth.multiply(dois)).toDouble(), // 18$f - translate y profile tooth
                    Rational.toRational(anguloAnim).divide(180).multiply(gear1.getGearRadius().multiply(scale)).multiply(Rational.PI).toDouble(),// 19$f - animationTranslateProfileTooth -  ang_degree/180*pi*raio
                    Rational.toRational(gear1.getParameters().getNumTeeth()*anguloAnim).divide(gear2.getParameters().getNumTeeth()).toDouble(), // 20$f - ratio gear1/gear2
                    anguloAnim, // 21$f - angulo animacao
                    arcTooth.divide(dois).subtract(arcTooth.multiply(3)).toDouble() // 22$f - translate y profile tooth

            );

    }

    private static SVGPath svgPathGear(GearGeometry gear, Rational scale) {

        SVGPath svgPath = null;
        for (int t = 0; t < gear.getTeeth().length; t++) {
            svgPath = svgPath(gear.getTeeth()[t], scale, svgPath);
        }

        if (isNull(svgPath)) {
            throw new IllegalStateException("não é esperado.");
        }

        return svgPath;
    }

    private static SVGPath svgPath(Point[] tooth, Rational scale, SVGPath svgPath) {
        Point firstPoint = tooth[0].multiply(scale);
        if (isNull(svgPath)) {
            svgPath = new SVGPath();
            svgPath.move(firstPoint);
        } else {
            svgPath.line(firstPoint);
        }

        for (int i=1; i<tooth.length; i++) {
            svgPath.line(tooth[i].multiply(scale));
        }

        return svgPath;
    }

}
