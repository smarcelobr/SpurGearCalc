package br.nom.figueiredo.sergio.spurgearcalc;

import br.nom.figueiredo.sergio.math.Rational;

public class App {
    public App() {
    }

    public static void main(String[] args) {

        Rational module = Rational.of(8, 5); // 1.6 mm
        GearParameters gearParameters = new GearParameters(12, module);

        System.out.println("Geometry Params");
        System.out.println(gearParameters);

        GearGeometry geometry = GearGenerator.generateToothProfile(gearParameters);

        System.out.println("Geometry results");
        System.out.println(geometry);

        System.out.println("Tooth Path");

        // dpi: 96/pol = 96 pixels per 25.4mm
        Rational scale = Rational.of(960, 254);
        System.out.println(geometry.getTeeth().html(scale));


    }
}
