package br.nom.figueiredo.sergio.spurgearcalc.svg;

import br.nom.figueiredo.sergio.math.Rational;
import br.nom.figueiredo.sergio.math.Real;
import br.nom.figueiredo.sergio.spurgearcalc.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SVG Path Builder.
 */
public class SVGPath implements SVGPathCommand {

    private final List<SVGPathCommand> commandList;
    private Point topLeft;
    private Point rightBottom;

    public SVGPath() {
        commandList = new ArrayList<>();
    }

    public SVGPath move(Point point) {
        updateLimits(point);
        commandList.add( new SVGPathMoveCommand(point));
        return this;
    }

    public SVGPath line(Point ...end) {
        for (Point e: end) {
            updateLimits(e);
            commandList.add(new SVGPathLineCommand(e));
        }
        return this;
    }

    public SVGPath arc(Rational rx, Rational ry, int xAxisRotation, int largeArcFlag, int sweepFlag, Point end) {
        updateLimits(end);
        commandList.add( new SVGPathArcCommand(rx, ry, xAxisRotation, largeArcFlag, sweepFlag, end));
        return this;
    }

    @Override
    public String render() {
        return commandList.stream().map(SVGPathCommand::render).collect(Collectors.joining(" "));
    }

    private void updateLimits(Point point) {
        if (commandList.isEmpty()) {
            topLeft = point;
            rightBottom = point;
            return;
        }

        // top
        if (point.getY().compareTo(topLeft.getY())<0) {
            topLeft = Point.of(topLeft.getX(), point.getY());
        }

        // left
        if (point.getX().compareTo(topLeft.getX())<0) {
            topLeft = Point.of(point.getX(), topLeft.getY());
        }
        // bottom
        if (point.getY().compareTo(rightBottom.getY())>0) {
            rightBottom = Point.of(rightBottom.getX(), point.getY());
        }

        // right
        if (point.getX().compareTo(rightBottom.getX())>0) {
            rightBottom = Point.of(point.getX(), rightBottom.getY());
        }
    }

    public Real getHeight() {
        return this.rightBottom.getY().subtract(this.topLeft.getY()).simplify();
    }

    public Real getWidth() {
        return this.rightBottom.getX().subtract(this.topLeft.getX()).simplify();
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(Point rightBottom) {
        this.rightBottom = rightBottom;
    }
}
