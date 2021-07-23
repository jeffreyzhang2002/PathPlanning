package math;

import java.awt.geom.Point2D;
import java.lang.Math;

/**
 * An extension to the relatively impotent java.awt.geom.Point2D.Double,
 * Vector2D allows mathematical manipulation of 2-component vectors.
 */
public class Vector2D extends Point2D.Double {

    public Vector2D()
    { super(); }

    public Vector2D(double x, double y)
    { super(x, y); }


    public Vector2D(Vector2D v)
    {
        x = v.x;
        y = v.y;
    }

    public double getMag()
    { return Math.hypot(x,y); }

    public double getAngle()
    { return Math.atan2(y, x); }

    public void set(double x, double y)
    { super.setLocation(x, y); }

    public void setPolar(double mag, double angle)
    { super.setLocation(mag * Math.cos(angle), mag * Math.sin(angle)); }

    /** Sets the vector's radius, preserving its angle. */
    public void setMag(double mag)
    { setPolar(mag, getAngle()); }

    /** Sets the vector's angle, preserving its radius. */
    public void setTheta(double angle)
    { setPolar(getMag(), angle); }

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    /** The difference of the vector and rhs: this - rhs */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public boolean equals(Object o)
    { return(o instanceof Vector2D) &&  x == ((Vector2D)o).x && y == ((Vector2D)o).y; }

    /** Product of the vector and scalar */
    public Vector2D scale(double scalar)
    { return new Vector2D(scalar * x, scalar * y); }

    /** Dot product of the vector and rhs */
    public double dotProduct(Vector2D other) {
        return x * other.x + y * other.y;
    }

    /**
     * Since Vector2D works only in the x-y plane, (u x v) points directly along
     * the z axis. This function returns the value on the z axis that (u x v)
     * reaches.
     *
     * @return signed magnitude of (this x rhs)
     */
    public double crossProduct(Vector2D other) {
        return x * other.y - y * other.x;
    }

    /** Product of components of the vector: compenentProduct( <x y>) = x*y. */
    public double componentProduct() {
        return x * y;
    }

    /** Componentwise product: <this.x*rhs.x, this.y*rhs.y> */
    public Vector2D componentwiseProduct(Vector2D rhs) {
        return new Vector2D(x * rhs.x, y * rhs.y);
    }

    /**
     * Returns a new vector with the same direction as the vector but with
     * length 1, except in the case of zero vectors, which return a copy of
     * themselves.
     */
    public Vector2D unitVector() {
        if (getMag() != 0) {
            return new Vector2D(x / getMag(), y / getMag());
        }
        return new Vector2D(0,0);
    }

    /** Polar version of the vector, with radius in x and angle in y */
    public Vector2D toPolar() {
        return new Vector2D(Math.sqrt(x * x + y * y), Math.atan2(y, x));
    }

    /** Rectangular version of the vector, assuming radius in x and angle in y*/
    public Vector2D toRect() {
        return new Vector2D(x * Math.cos(y), x * Math.sin(y));
    }

    /** @return Standard string representation of a vector: "<x, y>" */
    public String toString() {
        return "<" + x + ", " + y + ">";
    }
}
