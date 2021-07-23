package math.spline;

import math.Vector2D;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class CatmullRomSpline extends Spline
{
    private boolean edgesCrimped = false;
    public final static int minimumNumberOfPoints = 4;

    /**
     * Creates a CatmullRomSpline object using the 4 points called p1, p2, p3, p4
     * @param p1 first assist point
     * @param p2 first connection point
     * @param p3 second connection point
     * @param p4 second assist point
     */
    public CatmullRomSpline(Point2D p1, Point2D p2, Point2D p3, Point2D p4)
    { super(p1,p2,p3,p4); }

    /**
     * Creates a CatmullRomSpline object using an Arraylist of points
     * @param controlPoints
     */
    public CatmullRomSpline(ArrayList<Point2D> controlPoints)
    { super(controlPoints); }

    /**
     * Creates a CatmullRomSpline without any points. Points must be added later.
     */
    public CatmullRomSpline()
    {}

    /**
     * Adds a point into the spline at the given index
     * @param index the point where it will be added
     * @param point the point that is added
     */
    public void addPoint(int index, Point2D point)
    {
        uncrimpEnds();
        super.addControlPoint(index, point);
    }

    /**
     * Adds a point into the spline at the end
     * @param point the point that will be ended
     */
    public void addPoint(Point2D point)
    {
        uncrimpEnds();
        super.addControlPoint(point);
    }

    /**
     * changed catmull spline control points to overlap ending points
     */
    public void crimpEnds()
    {
        super.controlPoints.add(0, super.controlPoints.get(0));
        super.controlPoints.add(super.controlPoints.get(super.controlPoints.size()-1));
        edgesCrimped = true;
    }

    /**
     * Removes the overlap at the end of control points if ends have already been crimped
     */
    public void uncrimpEnds()
    {
        if(edgesCrimped)
        {
            super.controlPoints.remove(0);
            super.controlPoints.remove(super.controlPoints.size()-1);
            edgesCrimped = false;
        }
    }

    public int getMinRequiredControlPoints()
    { return minimumNumberOfPoints; }

    public ArrayList<Point2D> getPoints(double resolution)
    {
        if(!canGenerateSpline())
            return null;

        double increment = 1.0 / resolution;

        int loopUpperBound = controlPoints.size() - minimumNumberOfPoints - 1;
        ArrayList<Point2D> splinePoints = new ArrayList<Point2D>((int)(loopUpperBound / increment) + 1);

        for(double t=0; t < loopUpperBound; t += increment)
            splinePoints.add(getPoint(t));

        return splinePoints;
    }

    public Point2D getPoint(double t)
    {
        int p0, p1, p2, p3;
        p1 = (int)t + 1;
        p2 = p1 + 1;
        p3 = p2 + 1;
        p0 = p1 - 1;
        t = t - (int)t;

        double tSquared = Math.pow(t, 2);
        double tCubed = Math.pow(t, 3);

        Point2D.Double tp = new Point2D.Double(
                (q1(p0,p1,p2,p3,true ) + q2(p0,p1,p2,p3,true ) * t + q3(p0,p1,p2,p3,true ) * tSquared + q4(p0,p1,p2,p3,true ) * tCubed)/2,
                (q1(p0,p1,p2,p3,false) + q2(p0,p1,p2,p3,false) * t + q3(p0,p1,p2,p3,false) * tSquared + q4(p0,p1,p2,p3,false) * tCubed)/2);

        return tp;
    }

    public ArrayList<Vector2D> getDerivatives(double resolution)
    {
        if(!canGenerateSpline())
            return null;

        double increment = 1.0 / resolution;

        int loopUpperBound = controlPoints.size() - minimumNumberOfPoints - 1;
        ArrayList<Vector2D> splineDerivative = new ArrayList<Vector2D>((int)(loopUpperBound / increment) + 1);

        for(double t=0; t < loopUpperBound; t += increment)
            splineDerivative.add(getDerivative(t));

        return splineDerivative;
    }

    public Vector2D getDerivative(double t)
    {
        int p0, p1, p2, p3;
        p1 = (int)t + 1;
        p2 = p1 + 1;
        p3 = p2 + 1;
        p0 = p1 - 1;
        t = t - (int)t;

        double tSquared = Math.pow(t, 2);

        Vector2D tp = new Vector2D(
                (q2(p0,p1,p2,p3,true ) + 2*q3(p0,p1,p2,p3,true ) * t + 3*q4(p0,p1,p2,p3,true ) * tSquared)/2 ,
                (q2(p0,p1,p2,p3,false) + 2*q3(p0,p1,p2,p3,false) * t + 3*q4(p0,p1,p2,p3,false) * tSquared)/2 );

        return tp;
    }

    public ArrayList<Vector2D> getSecondDerivatives(double resolution)
    {
        if(!canGenerateSpline())
            return null;

        double increment = 1.0 / resolution;

        int loopUpperBound = controlPoints.size() - minimumNumberOfPoints - 1;
        ArrayList<Vector2D> splineDerivative = new ArrayList((int)(loopUpperBound / increment) + 1);

        for(double t=0; t < loopUpperBound; t += increment)
            splineDerivative.add(getSecondDerivative(t));

        return splineDerivative;
    }

    public Vector2D getSecondDerivative(double t)
    {
        int p0, p1, p2, p3;
        p1 = (int)t + 1;
        p2 = p1 + 1;
        p3 = p2 + 1;
        p0 = p1 - 1;
        t = t - (int)t;

        Vector2D tp = new Vector2D(
                (2*q3(p0,p1,p2,p3,true ) + 6*q4(p0,p1,p2,p3,true ) * t)/2 ,
                (2*q3(p0,p1,p2,p3,false) + 6*q4(p0,p1,p2,p3,false) * t)/2 );

        return tp;
    }

    private double q1(int p0, int p1, int p2, int p3, boolean isX)
    {
        if(isX)
            return 2 * super.getControlPoints().get(p1).getX();
        return 2 * super.getControlPoints().get(p1).getY();
    }

    private double q2(int p0, int p1, int p2, int p3, boolean isX)
    {
        if(isX)
            return -super.getControlPoints().get(p0).getX() + super.getControlPoints().get(p2).getX();
        return -super.getControlPoints().get(p0).getY() + super.getControlPoints().get(p2).getY();
    }

    private double q3(int p0, int p1, int p2, int p3, boolean isX)
    {
        if(isX)
            return 2 * super.getControlPoints().get(p0).getX() - 5 * super.getControlPoints().get(p1).getX()
                    + 4 * super.getControlPoints().get(p2).getX() - super.getControlPoints().get(p3).getX();
        else
            return 2 * super.getControlPoints().get(p0).getY() - 5 * super.getControlPoints().get(p1).getY()
                    + 4 * super.getControlPoints().get(p2).getY() - super.getControlPoints().get(p3).getY();
    }

    private double q4(int p0, int p1, int p2, int p3, boolean isX)
    {
        if(isX)
            return -super.getControlPoints().get(p0).getX() + 3 * super.getControlPoints().get(p1).getX()
                    - 3 * super.getControlPoints().get(p2).getX() + super.getControlPoints().get(p3).getX();
        else
            return -super.getControlPoints().get(p0).getY() + 3 * super.getControlPoints().get(p1).getY()
                    - 3 * super.getControlPoints().get(p2).getY() + super.getControlPoints().get(p3).getY();

    }
}
