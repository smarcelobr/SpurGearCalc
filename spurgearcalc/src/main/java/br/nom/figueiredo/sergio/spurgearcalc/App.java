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

        GearGeometry geometry = GearGenerator.generate(gearParameters);

        System.out.println("Geometry results");
        System.out.println(geometry);

        System.out.println("Tooth Path");
        System.out.println(geometry.getTeeth().html(Rational.of(20)));
    }
}
