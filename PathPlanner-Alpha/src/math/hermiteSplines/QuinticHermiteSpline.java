package math.hermiteSplines;

import math.Vector2D;

import java.awt.geom.Point2D;

public class QuinticHermiteSpline
{
    Point2D p1,p2;
    Vector2D v1, v2, a1, a2;

    public QuinticHermiteSpline(Point2D p1, Vector2D v1, Vector2D a1, Point2D p2, Vector2D v2, Vector2D a2)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.v1 = v1;
        this.v2 = v2;
        this.a1 = a1;
        this.a2 = a2;
    }

    public Point2D generatePoint(double t)
    {
        double H50 = H5_0(t);
        double H51 = H5_1(t);
        double H52 = H5_2(t);
        double H53 = H5_3(t);
        double H54 = H5_4(t);
        double H55 = H5_5(t);

        return new Point2D.Double(H50 * p1.getX() + H51 * v1.getX() + H52 * a1.getX() + H53 * a2.getX() + H54 * v2.getX() +
                H55 * p2.getX(), H50 * p1.getY() + H51 * v1.getY() + H52 * a1.getY() + H53 * a2.getY() + H54 * v2.getY() +
                H55 * p2.getY());
    }

    public double H5_0(double t)
    { return 1 - 10 * Math.pow(t,3) + 15 * Math.pow(t, 4) - 6 * Math.pow(t, 5); }

    public double H5_1(double t)
    { return t - 6 * Math.pow(t,3) + 8 * Math.pow(t,4) - 3 * Math.pow(t,5); }

    public double H5_2(double t)
    { return 0.5 * Math.pow(t,2) - 1.5 * Math.pow(t,3) + 1.5 * Math.pow(t,4) - 0.5 * Math.pow(t,5); }

    public double H5_3(double t)
    { return 0.5 * Math.pow(t,3) - Math.pow(t,4) + 0.5 * Math.pow(t,5); }

    public double H5_4(double t)
    { return -4 * Math.pow(t,3) + 7 *  Math.pow(t,4) - 3 * Math.pow(t,5); }

    public double H5_5(double t)
    { return 10 * Math.pow(t,3) - 15 * Math.pow(t,4) + 6 * Math.pow(t, 5); }
}

