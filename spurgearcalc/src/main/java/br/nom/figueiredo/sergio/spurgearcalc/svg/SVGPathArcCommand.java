package br.nom.figueiredo.sergio.spurgearcalc.svg;

import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.spurgearcalc.Point;

import java.util.Locale;

/**
 * ARC
 *
 * A rx ry x-axis-rotation large-arc-flag sweep-flag x y
 * a rx ry x-axis-rotation large-arc-flag sweep-flag dx dy
 */
public class SVGPathArcCommand implements SVGPathCommand {

    private final Rational rx;
    private final Rational ry;
    private final int xAxisRotation;
    private final int largeArcFlag;
    private final int sweepFlag;

    private final Point end;

    public SVGPathArcCommand(Rational rx, Rational ry, int xAxisRotation, int largeArcFlag, int sweepFlag, Point end) {
        this.rx = rx;
        this.ry = ry;
        this.xAxisRotation = xAxisRotation;
        this.largeArcFlag = largeArcFlag;
        this.sweepFlag = sweepFlag;
        this.end = end;
    }

    @Override
    public String render() {
        return String.format(Locale.ENGLISH,"A%f %f %d %d %d %f %f",
                rx.toDouble(),ry.toDouble(),
                xAxisRotation, largeArcFlag, sweepFlag,
                end.getX().toDouble(), end.getY().toDouble());
    }
}
