package br.nom.figueiredo.sergio.spurgearcalc.svg;

import br.nom.figueiredo.sergio.spurgearcalc.Point;

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
        return String.format("M%f %f",point.getX().toDouble(), point.getY().toDouble());
    }
}
