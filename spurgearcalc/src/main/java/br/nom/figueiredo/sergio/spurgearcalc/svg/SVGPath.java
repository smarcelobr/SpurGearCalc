package br.nom.figueiredo.sergio.spurgearcalc.svg;

import br.nom.figueiredo.sergio.spurgearcalc.Point;
import br.nom.figueiredo.sergio.spurgearcalc.Rational;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SVG Path Builder.
 */
public class SVGPath implements SVGPathCommand {

    private final List<SVGPathCommand> commandList;

    public SVGPath() {
        commandList = new ArrayList<>();
    }

    public SVGPath move(Point point) {
        commandList.add( new SVGPathMoveCommand(point));
        return this;
    }

    public SVGPath line(Point end) {
        commandList.add( new SVGPathLineCommand(end));
        return this;
    }

    public SVGPath arc(Rational rx, Rational ry, int xAxisRotation, int largeArcFlag, int sweepFlag, Point end) {
        commandList.add( new SVGPathArcCommand(rx, ry, xAxisRotation, largeArcFlag, sweepFlag, end));
        return this;
    }

    @Override
    public String render() {
        return commandList.stream().map(SVGPathCommand::render).collect(Collectors.joining(" "));
    }

}
