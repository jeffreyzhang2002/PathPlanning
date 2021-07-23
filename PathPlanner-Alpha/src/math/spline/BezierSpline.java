package math.spline;

import math.Vector2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BezierSpline extends Spline
{
    public static final int minimumNumberOfPoints = 4;

    /**
     * Creates a Bezier Spline using 4 control points
     * @param p1 first control point
     * @param p2 second control point
     * @param p3 third control point
     * @param p4 fourth control point
     */
    public BezierSpline(Point2D p1, Point2D p2, Point2D p3, Point2D p4)
    { super(p1,p2,p3,p4); }

    /**
     * Creates a Bezier spline using a List of control points
     * @param addedpoints the list of control points
     */
    public BezierSpline (ArrayList<Point2D> addedpoints)
    { super(addedpoints); }

    /**
     * Creates a Bezier spline with no control points. Point must be added for generation to work
     */
    public BezierSpline()
    {}

    /**
     * Returns the minimum necessary control points needed to control the Bezier spline
     */
    public int getMinRequiredControlPoints()
    { return minimumNumberOfPoints; }

    /**
     * This method overrides the super class method to allow for optimizations. Other wise it does the samething
     */
    public ArrayList<Point2D> getPoints(double resolution)
    {
        if(!canGenerateSpline())
            return null;

        double increment = 1.0 / resolution;

        ArrayList<Point2D> splinePoints = new ArrayList((int)resolution + 1);
        long[] pascals = pascals(controlPoints.size() - 1);

        for(double t=0; t < 1; t += increment)
            splinePoints.add(generateSplinePoint(t, pascals));
        return  splinePoints;
    }

    public Point2D getPoint(double t)
    { return canGenerateSpline()? generateSplinePoint(t, pascals(controlPoints.size() - 1)) : null; }

    public ArrayList<Vector2D> getDerivatives(double resolution)
    {
        if(!canGenerateSpline())
            return null;

        double increment = 1.0 / resolution;

        ArrayList<Vector2D> splineDerivatives = new ArrayList((int)resolution + 1);
        long[] pascals = pascals(controlPoints.size() - 1);

        for(double t=0; t < 1; t += increment)
            splineDerivatives.add(generateSplineDerivative(t, pascals));
        return  splineDerivatives;
    }

    public Vector2D getDerivative(double t)
    { return canGenerateSpline()? generateSplineDerivative(t, pascals(controlPoints.size() - 1)) : null; }

    public ArrayList<Vector2D> getSecondDerivatives(double resolution)
    {
        if(!canGenerateSpline())
            return null;

        double increment = 1.0 / resolution;

        ArrayList<Vector2D> splineDerivatives = new ArrayList((int)resolution + 1);
        long[] pascals = pascals(controlPoints.size() - 1);

        for(double t=0; t < 1; t += increment)
            splineDerivatives.add(generateSplineSecondDerivative(t, pascals));
        return  splineDerivatives;
    }

    public Vector2D getSecondDerivative(double t)
    { return canGenerateSpline()? generateSplineSecondDerivative(t, pascals(controlPoints.size() - 1)) : null;}

    /**
     * Generates a bezier spline at the current t with the known pascals triangle layer setup
     * @param t the currne point of generation
     * @param pascals a long array with pascals triangle number
     * @return
     */
    public Point2D generateSplinePoint(double t, long[] pascals)
    {
        double tPrime = 1-t;
        int degree = super.controlPoints.size();

        double x = 0, y = 0;

        for(int i = 0; i < degree; i++)
        {
            double value = pascals[i] * Math.pow(t,i) * Math.pow(tPrime, degree-i-1);
            x = value * super.controlPoints.get(i).getX();
            y = value * super.controlPoints.get(i).getY();
        }
        return new Point2D.Double(x,y);
    }

    public Vector2D generateSplineDerivative(double t, long[] pascals)
    {
        double tPrime = 1 - t;
        int degree = super.controlPoints.size();

        double x = 0, y = 0;

        for(int i = 0; i < degree; i++)
        {
            double value = pascals[i] * (Math.pow(tPrime, degree - i - 2) * Math.pow(t, i-1) * (i * tPrime - (degree-i-1) * t));
            x = value * super.controlPoints.get(i).getX();
            y = value * super.controlPoints.get(i).getY();
        }
        return new Vector2D(x,y);
    }

    public Vector2D generateSplineSecondDerivative(double t, long[] pascals)
    {
        double tPrime = 1 - t;
        int degree = super.controlPoints.size();

        double x = 0, y = 0;

        for(int i = 0; i < degree; i++)
        {
            double value = pascals[i] *
                    Math.pow(t,i-2) * Math.pow(tPrime,degree - i - 3) *
                    ((degree - i - 1) * ((degree - i - 2) * (t * t) - (2 * i * tPrime * t)) + (i * tPrime * (i-1) * tPrime));
            x = value * super.controlPoints.get(i).getX();
            y = value * super.controlPoints.get(i).getY();
        }
        return new Vector2D(x,y);
    }

    private long[] pascals(int line)
    {
        long[] pascalsValue = new long[line + 1];

        for(int i=0; i <= line; i++)
            pascalsValue[i] = combination(line,i);
        return pascalsValue;
    }

    private long combination(int n, int k)
    {
        int res = 1;

        if (k > n - k)
            k = n - k;

        for (int i = 0; i < k; ++i)
        {
            res *= (n - i);
            res /= (i + 1);
        }
        return res;
    }

}
