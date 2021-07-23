package math.hermiteSplines;

import math.Vector2D;

import java.awt.geom.Point2D;

public class CubicHermiteSpline
{
    Point2D p1,p2;
    Vector2D v1,v2;

    public CubicHermiteSpline(Point2D p1, Vector2D v1, Point2D p2, Vector2D v2)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.v1 = v1;
        this.v2 = v2;
    }

    public Point2D generatePoint(double t)
    {
        double H30 = H3_0(t);
        double H31 = H3_1(t);
        double H32 = H3_2(t);
        double H33 = H3_3(t);

        return new Point2D.Double(H30 * p1.getX() + H31 * v1.getX() + H32 * v2.getX() + H33 * p2.getX(),
                H30 * p1.getY() + H31 * v1.getY() + H32 * v2.getY() + H33 * p2.getY());
    }

    private double H3_0(double t)
    { return 1 - 3 * Math.pow(t,2) + 2 * Math.pow(t,3); }

    private double H3_1(double t)
    { return t - 2 * Math.pow(t,2) + Math.pow(t,3); }

    private double H3_2(double t)
    { return -1 * Math.pow(t,2) + Math.pow(t,3); }

    private double H3_3(double t)
    { return 3 * Math.pow(t,2) - 2 * Math.pow(t,3); }

}
