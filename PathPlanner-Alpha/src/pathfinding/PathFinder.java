package pathfinding;

import environment.Environment;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * This PathFinder class acts as a scaffolding for other Path Finding algorithms. This class must be extended by
 * child classes and they should override the _generatePath method. This class takes a list of KeyStopPoints where
 * the first point in this list is the starting point and the following points in the list are stopping points
 * where the pathFinding algorithm will stop at. Their must be at least 1 point in the KeyStopPoints list for path
 * generation to occur. If there is one point, generating path will return that single point, if there are multiple
 * points, the path finding algorithm will attempt to find the optimal path from start point through each of the
 * following point all they way to the end point. If no path exist, path generation terminate. To generate a path run
 * the method generatePath().
 * @param <E> is a specific type of Environment that the pathfinder will run on. For astar this will be a grid. For
 * LOSStar this will be a Plane.
 * @version 1
 * @since 5/17/2020
 * @author Jeffrey
 */
public abstract class PathFinder<E extends Environment>
{
    private E environment;
    private ArrayList<Point2D> keyStopPoints;
    private ArrayList<Point2D> path;

    /**
     * Constructor for creating an instance of a PathFinder class. If this constructor is run a start and ending points must
     * be later defined in order to run the generatePath method.
     * @param environment The environment the pathfinder will run on.
     */
    public PathFinder(E environment)
    {
        this.environment = environment;
        keyStopPoints = new ArrayList<>();
    }

    /**
     * Constructor for creating an instance of a PathFinder using the Environment and the start and stop points.
     * If the start or end points are not valid points within the environment this will throw an IllegalArgumentException
     * @param environment The environment the pathfinder will run on.
     * @param start The current starting point of the pathfinder. This may change if the keyStopPoints are altered
     * @param end The current ending point of the pathfinder. This may change if the keyStopPoints are altered
     */
    public PathFinder(E environment, Point2D start, Point2D end)
    {
        this.environment = environment;
        if(!environment.isValidPoint(start) || !environment.isValidPoint(end))
            throw new IllegalArgumentException("Given Points are not on the environment");
        keyStopPoints = new ArrayList<>(2);
        keyStopPoints.add(0,start);
        keyStopPoints.add(1, end);
    }

    /**
     * Constructor for creating an instance of a PathFinder using the Environment and  a list of keyStopPoints.
     * If any points in the list is no a valid point within the Environment, an illegalArgumentException will be thrown
     * @param environment The environment the pathfinder will run on.
     * @param points List of keyStopPoints
     */
    public PathFinder(E environment, Point2D ... points)
    {
        this.environment = environment;
        keyStopPoints = new ArrayList<>();
        for(Point2D point : points)
        {
            if(!environment.isValidPoint(point))
                throw new IllegalArgumentException(points + " is not on the Environment");
            keyStopPoints.add(point);
        }
        path = new ArrayList<>();
    }

    /**
     * This method adds the given point to the end of keyStopPoints making it the new end point. If the given point
     * is not within the environment, an IllegalArgumentException will be thrown
     * @param point the point that will be added to the end of the KeyStopPoint List
     */
    public void addKeyStopPoint(Point2D point)
    {
        isValidPoint(point);
        keyStopPoints.add(point);
    }

    /**
     * This method adds the given at the given index. If the given point
     * is not within the environment, an IllegalArgumentException will be thrown
     * @param point the point that will be added to the end of the KeyStopPoint List
     */
    public void addKeyStopPoint(int index, Point2D point)
    {
        isValidPoint(point);
        keyStopPoints.add(index, point);
    }

    /**
     * This method sets the point at the index to the given point. If the given point is not within the environment
     * an IllegalArgumentException will be thrown. If the index is not within bounds, an Exception will also be thrown
     * @param index The index that will be set to the given point
     * @param point the point that will be set at the index
     */
    public void setStopPoints(int index, Point2D point)
    {
        isValidPoint(point);
        keyStopPoints.set(index, point);
    }

    /**
     * This method removes the given point from the keyStopPoints list. If the point is not within the List nothing
     * will occur.
     * @param point the Point that will be removed
     */
    public void removeStopPoint(Point2D point)
    { keyStopPoints.remove(point); }

    /**
     * This method removes the point located at the index. If the index is not within bounds an ArrayList out of bounds
     * error will be thrown.
     * @param index The index of the Point that will be removed
     */
    public void removeStopPoints(int index)
    { keyStopPoints.remove(index); }

    /**
     * Clears all points from the keyStopPoints list
     */
    public void clearStopPoints()
    { keyStopPoints.clear(); }

    /**
     * This method gets the number of stopPoints within the keyStopPoints list
     * @return size of keyStopPoints
     */
    public int getStopPointsNumber()
    { return keyStopPoints.size(); }

    /**
     * This method returns the point located at the given index
     * @param index the index of the point that will be returned
     * @return the Point located at the index
     */
    public Point2D getStopPoint(int index)
    { return keyStopPoints.get(index); }

    /**
     * Reset the generated path and sets it to null
     */
    public void clearPathPoints()
    { path = null; }

    /**
     * Returns the generated path
     * @return the path that is generated
     */
    public ArrayList<Point2D> getPath()
    { return path; }

    public ArrayList<Point2D> getKeyStopPoints()
    { return keyStopPoints; }

    /**
     * Check if a path exist or has already been generated.
     * @return true if path exists and not null and false if it is null
     */
    public boolean isValidPath()
    { return path != null && !path.isEmpty(); }

    /**
     * Returns the environment that the pathFinding algorithm is currently running on
     * @return the Environment the PathFinder is using.
     */
    public Environment getEnvironment()
    { return  environment; }

    /**
     * Gen
     * @param containCorners
     * @return
     */
    public ArrayList<Point2D> generatePath(boolean containCorners)
    {
        path = new ArrayList<>();
        if(keyStopPoints.size() > 1)
        {
            for(int i = 1; i < keyStopPoints.size(); i++) {
                ArrayList<Point2D> pathPart = _generatePath(keyStopPoints.get(i - 1), keyStopPoints.get(i), containCorners);
                if(pathPart != null) {
                    pathPart.remove(pathPart.size() - 1);
                    path.addAll(pathPart);
                }
                else {
                    path = null;
                    return null;
                }
            }
            path.add(keyStopPoints.get(keyStopPoints.size()-1));
        }
        else if(keyStopPoints.size() == 1)
            path.add(keyStopPoints.get(0));
        else
            return null;
        return path;
    }

    public abstract ArrayList<Point2D> _generatePath(Point2D start, Point2D end, boolean containCorners);

    private void isValidPoint(Point2D point)
    {
        if(!environment.isValidPoint(point))
            throw new IllegalArgumentException(point + "is not in the domain");
    }

    public String toString()
    { return keyStopPoints.toString(); }
}