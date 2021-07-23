package math.spline;

import math.Vector2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Spline
{
    ArrayList<Point2D> controlPoints;

    public Spline()
    { controlPoints = new ArrayList<>(); }

    public Spline(Point2D ... points)
    {
        controlPoints = new ArrayList<>();
        for(Point2D p : points)
            controlPoints.add(p);
    }

    public Spline(ArrayList<Point2D> controlPoints)
    { this.controlPoints = controlPoints; }

    public void addControlPoint(Point2D point)
    { controlPoints.add(point); }

    public void addControlPoint(int index, Point2D point)
    { controlPoints.add(index,point); }

    public void removeControlPoint(Point2D point)
    { controlPoints.remove(point); }

    public void removeControlPoint(int index)
    { controlPoints.remove(index); }

    public void clearControlPoint()
    { controlPoints.clear();}

    public int getControlPointSize()
    { return controlPoints.size(); }

    public ArrayList<Point2D> getControlPoints()
    { return controlPoints; }

    public boolean canGenerateSpline()
    { return(controlPoints.size() >= getMinRequiredControlPoints()); }

    public abstract int getMinRequiredControlPoints();

    public abstract Point2D getPoint(double t);

    public abstract ArrayList<Point2D> getPoints(double resolution);

    public abstract Vector2D getDerivative(double t);

    public abstract ArrayList<Vector2D> getDerivatives(double resolution);

    public abstract Vector2D getSecondDerivative(double t);

    public abstract ArrayList<Vector2D> getSecondDerivatives(double resolution);
}
