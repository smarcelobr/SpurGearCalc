package br.nom.figueiredo.sergio.spurgearcalc.svg;

import br.nom.figueiredo.sergio.spurgearcalc.Point;

public class SVGPathLineCommand implements SVGPathCommand {

    private final Point end;

    public SVGPathLineCommand(Point end) {
        this.end = end;
    }

    @Override
    public String render() {
        return String.format("M%f %f",end.getX().toDouble(), end.getY().toDouble());
    }
}
