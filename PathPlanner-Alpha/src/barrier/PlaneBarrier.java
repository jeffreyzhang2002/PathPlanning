/**
 * Engineering Notebook Notes:
 * 1. This method is used to create specific obstacles that can be placed on the grid.
 *
 * 2. bounding points are stored in an ArrayList because order matters as the first point connects to the second and so on
 *
 * 3. Propagated points are stored are returned as a HashSet because they are simple used to define important points of the
 * obstacle. Generally Propagated points can be calculated using two line segments and finding the point that are the
 * averages of the two nearby line segments
 */
package barrier;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A abstract class designed for use in a Plane Environment. When using this class certain methods must be
 * overridden that are inherited from the Barrier abstract class. a PlaneBarrier is different from a normal
 * barrier because A PlaneBarrier is not defined at a single point but instead defined at multiple points.
 * A PlaneBarrier can be a line segment with 2 bounding points, a square with 4 bounding points or a polygon
 * with n bounding points. When this class is extended, the sub class should define where each bounding point is and
 * they should be stores inside a list for easy access. The first point in the list connect the next point and the last
 * point connects to the first loop forming a continuous loop that is the shape of the Plane Barrier. Please note that
 * even though this class can be used in a grid Environment, it is not recommended as on a grid, the Barrier will be
 * stored on a single point instead of a collection of line segments. In addition, this class allows points to be
 * defined in double, int or float precision as planes store the coordinates continuously.
 * @author Jeffry
 * @version 1
 * @since  5/5/2020
 */
public abstract class PlaneBarrier extends Barrier
{
    /**
     * This method returns the centroid of the PlaneBarrier. Since a plane barrier is not defined at a single point but a
     * collection of connected points that form a shape, when this method is called it returns where the center should be.
     * Please note that if the PlaneBarrier is just set to be a single point, this method will return that point and likewise if
     * this instance only has 2 bounding points and form a line Segment, this method will return the midpoint
     * @return a Point2D containing the centroid of the PlaneBarrier
     */
    public final Point2D getPosition()
    {
        if(getBoundingPoints().size() == 1)
            return getBoundingPoints().get(0);
        else if(getBoundingPoints().size() == 2)
            return new Point.Double((int)(getBoundingPoints().get(0).getX() + getBoundingPoints().get(1).getX()) / 2,
                    (int)(getBoundingPoints().get(0).getX() + getBoundingPoints().get(1).getX()) / 2);

        int xSum = 0, ySum = 0;
        double area = 0;
        for(int i = 0; i < getBoundingPoints().size(); i++)
        {
            int j = (i == getBoundingPoints().size() - 1) ? 0 : i + 1;
            xSum += (getBoundingPoints().get(i).getX() + getBoundingPoints().get(j).getX()) *
                    (getBoundingPoints().get(i).getX() * getBoundingPoints().get(j).getY() -
                            getBoundingPoints().get(j).getX() * getBoundingPoints().get(i).getY());
            ySum += (getBoundingPoints().get(i).getY() + getBoundingPoints().get(j).getY()) *
                    (getBoundingPoints().get(i).getX() * getBoundingPoints().get(j).getY() -
                            getBoundingPoints().get(j).getX() * getBoundingPoints().get(i).getY());
            area += (getBoundingPoints().get(i).getX() * getBoundingPoints().get(j).getY() -
                    getBoundingPoints().get(j).getX() * getBoundingPoints().get(i).getY());
        }
        area /= 2.0;
        return new Point.Double((int) (xSum / (6 * area)), (int) (ySum / (6 * area)));
    }

    /**
     * This method set the position of this plane barrier. Since a plane barrier does not a single point but instead
     * is defined by a list of point, this method will set the centroid of the PlaneBarrier and then shift each of the
     * bounding points accordingly.
     * @param position
     */
    void setPosition(Point2D position)
    {
        Point2D currentPos = getPosition();
        Point2D.Double shift = new Point2D.Double(currentPos.getX() - position.getX(), currentPos.getY() - position.getY());
        for(Point2D point: getBoundingPoints())
            point.setLocation(shift.getX() + point.getX(), shift.getY() + point.getY());
        super.setPosition(getPosition());
    }

    /**
     * This is an abstract method that must be overridden by child classes. Bounding points are the points where the lines
     * connect to. For example, in a trapezoid, there are 4 points and the first point connects to the second point, the second
     * to the third, third to the fourth and the fourth back to the first. For more complex polygons this method can
     * return a longer list of bounding points
     * @return an ArrayList containing the points the bound the shape
     */
    public abstract ArrayList<Point2D> getBoundingPoints();

    /**
     * Propagated points are points that are propagated away from the bounding point. These points are offset from the
     * shape by a certain magnitude. For example the propagated points of line segment are collinear with the lines but the are
     * a certain distance away from the end point. The position where propagated points are placed will be used to generate
     * the shortest path and for finding points where shapes block direct line of sight.
     * @param mag the magnitude of propagation
     * @return a set containing the propagated points
     */
    public abstract HashSet<Point2D> getPropagatedPoints(double mag);

    /**
     * Returns a string containing all the bounding points of the PlaneBarrier
     * @return a String reperesentation of the PlaneBarrier
     */
    public String toString()
    {
        String stringBuilder = "";
        for(Point2D point : getBoundingPoints())
            stringBuilder += "(" + point.getX() + "," + point.getY() + ") --> ";
        return stringBuilder;
    }
}