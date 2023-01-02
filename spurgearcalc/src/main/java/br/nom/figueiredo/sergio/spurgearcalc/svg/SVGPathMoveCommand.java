package br.nom.figueiredo.sergio.spurgearcalc.svg;

import br.nom.figueiredo.sergio.math.Point;

import java.util.Locale;

public class SVGPathMoveCommand implements SVGPathCommand {

    private final Point point;

    public SVGPathMoveCommand(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public String render() {
        return String.format(Locale.ENGLISH,"M%f %f",point.getX().toDouble(), point.getY().toDouble());
    }
}
