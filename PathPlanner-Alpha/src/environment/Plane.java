/**
 * Engineering Notebook Notes:
 * 1. an ArrayList was chosen to store propagatedPoints because an ArrayList allows elements to be
 * repeated. When propagated Points are calculated for plane actors, there is a chance that the propagate to the same point
 *
 * 2. Point2D was chosen over Point because Point2D stores the x and y position with double precision and this can be
 * useful for some calculations
 */
package environment;

import barrier.PlaneBarrier;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A Plane is an type of an environment where actors are not defined at a single point. Instead
 * an environment contains 2D actors such as polygons, and lines that are formed by a set of bounding points
 * A Plane is designed to be used with Line of Sight astar. A plane will only contain PlaneBarrier objects
 * algorithm.
 * @author Jeffrey
 * @since 5/5/2020
 * @version 1
 */
public class Plane extends Environment<PlaneBarrier>
{
    private ArrayList<PlaneBarrier> actors;
    private ArrayList<Point2D> propagatedPoints;
    private double propagationMagnitude;

    /**
     * Creates a Plane with specific length and specific width. The length and width are equivalent to
     * the rows and columns
     * @param length
     * @param width
     */
    public Plane(int length, int width)
    {
        super(length, width);
        actors = new ArrayList<>();
        propagatedPoints = new ArrayList<>();
        propagationMagnitude = 1;
    }

    /**
     * Creates a Plane with specific length and specific width. For this constructor the propagationMagnitude is predefined
     * @param length the length of the Plane
     * @param width the width of the Plane
     * @param propagationMagnitude the magnitude of propagation for propagation points
     */
    public Plane(int length, int width, double propagationMagnitude)
    {
        super(length, width);
        actors = new ArrayList<>();
        propagatedPoints = new ArrayList();
        this.propagationMagnitude = propagationMagnitude;
    }

    //overridden class from Environment
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Adds a barrier into the Plane. This method should no run instead the addBarrier method should be ran instead
     * Please note that when a PlaneBarrier is added its propagation points for the barrier is retrieved and stored in the
     * propagatePoints list. This is done to save on computing power as calculating propagated points required substantial
     * computing power.
     * @param barrier the barrier that will be added.
     */
    public void _addBarrier(PlaneBarrier barrier)
    {
        actors.add(barrier);
        propagatedPoints.addAll(barrier.getPropagatedPoints(propagationMagnitude));
    }

    public void addBarrier(PlaneBarrier barrier)
    {
        for(Point2D point : barrier.getBoundingPoints())
            if(!isValidPoint(point))
                throw new IllegalArgumentException("Barrier out of domain");
        _addBarrier(barrier);
    }

    /**
     * Removes the given barrier from the Plane. When this method is run, the associated propagated points are also removed
     * from the propagatedPoints list. This is done to save on processing power and recalculating all propagated points required
     * substantial computing power. propagatedPoints are stored in an ArrayList because ArrayList allows for duplicated
     * values to be stores and occasionally two different actors can have a repeated propagation point.
     * @param barrier the Barrier that will be removed
     */
    public void removeBarrier(PlaneBarrier barrier)
    {
        actors.remove(barrier);
        HashSet<Point2D> propPoint = barrier.getPropagatedPoints(propagationMagnitude);
        for(Point2D p : propPoint)
            propagatedPoints.remove(p);
    }

    /**
     * Gets all of the actors that are stored in the current plane
     * @return HashSet<PlaneBarrier>
     */
    public HashSet<PlaneBarrier> getBarriers()
    { return new HashSet<>(actors); }

    /**
     * Removed all actors that are currently in the plane. When this method is called all the propagatedPoints are
     * also removed.
     */
    public void clearBarriers()
    {
        actors.clear();
        propagatedPoints.clear();
    }

    //class methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Checks if there is Line of sight between the startPoint and endPoint. Line of Sight means that there is
     * no planeActors that are blocking the path.
     * @param startPoint The starting point to check for line of sight
     * @param endPoint The ending point to check for line of sight
     * @return true if there is line of sight. False is there is no line of sight
     */
    public boolean LineOfSight(Point2D startPoint, Point2D endPoint)
    {
        Line2D.Double intersectionLine = new Line2D.Double(startPoint,endPoint);

        for(PlaneBarrier actor : actors)
        {
            ArrayList<Point2D> actorBoundingPoints = actor.getBoundingPoints();
            for(int i = 0; i < actorBoundingPoints.size(); i++)
            {
                int j = (i == actorBoundingPoints.size() - 1) ? 0 : i + 1;
                Line2D.Double currentLine = new Line2D.Double(actorBoundingPoints.get(i), actorBoundingPoints.get(j));
                if(currentLine.intersectsLine(intersectionLine))
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns a set of points that contain the bounds of each barrier contained in the Plane
     * @return HashSet<Point> that contain the actors bounds
     */
    public HashSet<Point2D> getActorBounds()
    {
        HashSet<Point2D> actorBound = new HashSet<>();
        for(PlaneBarrier actor : actors)
            actorBound.addAll(actor.getBoundingPoints());
        return actorBound;
    }

    /**
     * Returns an List containing all of the propagated points for the current actors in the plane. Please note that
     * when this method is run it simple returns a list of propagated points and does not recalculate the propagatedPoints
     * @return List of propagated point
     */
    public HashSet<Point2D> getPropagatedPoints()
    { return new HashSet<>(propagatedPoints); }

    /**
     * This method recalculates the propagated points. This method is computaitaly expensive and should only be ran when
     * it is required.
     */
    public HashSet<Point2D> calculatePropagatedPoints()
    {
        propagatedPoints.clear();
        for(PlaneBarrier actor : actors)
            propagatedPoints.addAll(actor.getPropagatedPoints(propagationMagnitude));
        return getPropagatedPoints();
    }

    /**
     * Sets the propagationMagnitude for the plane. This method will automatically recalculate Propagated Points using
     * recalculatePropagatedPoints()
     * @param propagationMagnitude
     */
    public void setPropagationMagnitude(double propagationMagnitude)
    {
        this.propagationMagnitude = propagationMagnitude;
        calculatePropagatedPoints();
    }

    /**
     * Gets the propagatedMagnitude for the current plane
     * @return the propagatedMagnitude
     */
    public double getPropagationMagnitude()
    { return propagationMagnitude; }

    /**
     * This method check if the given point is located on the Plane. A point is located on the plane if the x value is the set [0 ; rows]
     * and the y value is also between [0 ; columns]. This method differs from the super version of this method as points with an x value
     * equal to rows is included. Likewise, points with a y value that is equal to columns is also included
     * @param point The Point that will be checked whether it is inside the Environment.
     * @return true if the point is on the plane. False if it not on the plane
     */
    public boolean isValidPoint(Point2D point)
    { return point.getX() >= 0 && point.getX() <= super.getRows() && point.getY() >= 0 && point.getY() <= super.getCols(); }

    /**
     * This method checks if the given point is located on the edge of the plane. A point is located on the edge if the x value equals 0 or rows
     * and the y values equals 0 or cols. This method differs from the super version of the method as pointwith an x equal to rows
     * or a y equals to columns is said to be on the edge.
     * @param point The Point that will be checked whether it is on the edge of the Environment.
     * @return true if the point is on the edge. False is it is not the edge.
     */
    public boolean isEdgePoint(Point2D point)
    { return point.getX() == 0 || point.getX() == super.getRows() || point.getY() == 0 || point.getY() == super.getCols();}
}