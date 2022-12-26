package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.spurgearcalc.svg.SVGPath;

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

        GearGeometry gear1 = GearGenerator.generateToothProfile(gearParameters);
        GearGeometry gear2 = GearGenerator.generateToothProfile(new GearParameters(12, module));

        System.out.println("Geometry results");
        System.out.println(gear1);

        System.out.println("Tooth Path");

        // dpi: 96/pol = 96 pixels per 25.4mm
        Rational scale = Rational.of(960, 254);
        System.out.println(gearAsHtml(gear1, gear2, scale));


    }

    public static String gearAsHtml(GearGeometry gear1, GearGeometry gear2, Rational scale) {

        SVGPath pathGear1 = svgPathGear(gear1, scale);
        SVGPath pathGear2 = svgPathGear(gear2, scale);

        String htmlTemplate = """
                <!DOCTYPE html>
                <html>
                <body>
                   <svg height="%3$f" width="%4$f" viewBox="%5$f %6$f %3$f %4$f">
<path id="gear1" d="%1$s"
      stroke="red" stroke-width="1" fill="none" />
<path id="gear2" d="%2$s"
      stroke="blue" stroke-width="1" fill="none" transform="translate(72.5668 0)"/>
                      
                      Sorry, your browser does not support inline SVG.
                   </svg>
                </body>
                </html>
                """;

            Rational margem = scale.multiply(10);
            Rational metadeMargem = margem.divide(Rational.of(2));
            return String.format(Locale.ENGLISH, htmlTemplate,
                    pathGear1.render(), // 1$s
                    pathGear2.render(), // 2$s
                    margem.add(pathGear1.getHeight().add(pathGear2.getHeight())).toDouble(), // 3$f
                    margem.add(pathGear1.getWidth().add(pathGear2.getWidth())).toDouble(), // 4$f
                    pathGear1.getTopLeft().getX().subtract(metadeMargem).toDouble(), // 5$f
                    pathGear1.getTopLeft().getY().subtract(metadeMargem).toDouble()); // 6$f
    }

    private static SVGPath svgPathGear(GearGeometry gear, Rational scale) {

        SVGPath svgPath = null;
        for (int t = 0; t < gear.getParameters().getNumTeeth(); t++) {
            svgPath = gear.getTooth(t).svgPath(scale, svgPath);
        }

        if (isNull(svgPath)) {
            throw new IllegalStateException("não é esperado.");
        }

        return svgPath;
    }

}
