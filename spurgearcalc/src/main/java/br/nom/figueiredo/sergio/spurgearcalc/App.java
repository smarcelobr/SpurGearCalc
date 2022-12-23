package br.nom.figueiredo.sergio.spurgearcalc;

public class App {
    public App() {
    }

    public static void main(String[] args) {

        Rational module = new Rational(8, 5); // 1.6 mm
        GearParameters gearParameters = new GearParameters(12, module);

        GearGeometry geometry = GearGenerator.generate(gearParameters);

        System.out.println(geometry.getTeeth().svgPath(20));
    }
}
