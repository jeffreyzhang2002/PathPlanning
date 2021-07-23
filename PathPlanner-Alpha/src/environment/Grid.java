package environment;

import barrier.GridBarrier;
import java.awt.*;
import java.util.HashSet;

/**
 * A grid is subclass of Environment and can only hold GridBarriers. A grid is different from an Environment as
 * GridBarriers are placed on a discrete integer coordinate. For example, an GridBarrier can be on the position (0,0)
 * but it cannot be at the position (0.5, 0.5). If continuous coordinate are required, the user can either scale values
 * accordingly or they can use a Plane. This abstract Grid class will act as a framework for other grid Based Environments
 * @author Jeffrey
 * @version 1
 * @since 5/8/2020
 */
public abstract class Grid extends Environment<GridBarrier>
{
    /**
     * Constructor for creating a Grid with the given number of rows and columns. Rows refers to the maximum
     * x value an actor can have and columns refers to the maximum y value an actor can have. This means that valid x
     * coordinates are in the range [0 ; rows) and valid y coordinates are in the range [0 ; cols). Please remember that
     * after rows and columns are set, their is no way to change these values without creating a new instance of the
     * class.
     * @param rows Number of rows inside the environment and the maximum x position for actors stored inside
     * @param cols Number of columns inside the environment and the maximum y position for actors stored inside
     */
    public Grid(int rows, int cols)
    { super(rows, cols); }

    /**
     * Method for getting the GridBarrier located at the given position. If there are more that one barrier class
     * located at that position, this method will return that first GridBarrier that is found at that position. This
     * should not be ran outside of this class an acts as a helper method. Instead the getBarrier(Point  point) method
     * should be used instead. This method must be overridden by subclasses. When overriding this method, you do not
     * need to check if the position is within the grid as the getBarrier(Point point) method will handel these errors. If
     * no barriers are found at the point, null should be returned.
     * @param point The position for where the GridBarrier Object you want to get is currently located
     * @return The first GridBarrier that is found that the given point.
     */
    abstract GridBarrier _getBarrier(Point point);

    /**
     * Method for getting all GridBarriers that are located at the given point. This method acts as a helper method
     * for the getBarriers(Point point). This method must be overridden by subclasses and should not be ran outside of
     * this class. Please note that when overriding this method, you do not need check if point is within the bounds on
     * the Grid as the getBarriers(Point point) method will take care of these errors. If no barriers are found at the point
     * an empty set will be returned
     * @param point The position for where the GridBarrier Objects you want to get are currently located
     * @return a HashSet<GridBarrier> containing all actors at the given point
     */
    abstract HashSet<GridBarrier> _getBarriers(Point point);

    /**
     * This method removes all GridBarrier located at the given position. This method as a helper method for the
     * removeBarriers(Point point) method. This method must be overridden by subclasses. In addition, when overriding
     * this method, you do not need to check is the position is within bounds as this will be handled by the
     * removeBarriers method. This method should not be ran outside of the current class.
     * @param point The point where you want to remove all GridBarriers
     */
    abstract void _removeBarriers(Point point);

    /**
     * This method gets all gridBarriers that neighbor the given point. The neighbors gridBarrier are defined as the
     * gridBarriers with position of 8 coordinates that directly surround the given point. If there are no gridBarriers
     * that are neighbors, this method will return an empty set. The parameter containCorners control if the 4 position
     * that are the corners will be considered a neighboring position.
     * @param point the point where this method will look for gridBarriers are neighboring it
     * @param containCorners true if the 4 corner neighbors are considered a neighbor. False otherwise
     * @return HashSet<GridBarrier> containing all gridBarriers that are neighboring the given point
     */
    public abstract HashSet<GridBarrier> getNeighboringBarriers(Point point, boolean containCorners);

    /**
     * This method gets all position that neighbor the given point and do not contain a gridBarrier. The neighbors
     * coordinates are defined as the 8 coordinates that directly surround the given point. The parameter containCorners
     * control if the 4 position that are the corners will be considered a neighboring position.
     * @param point the point where this method will look for points that are neighboring it
     * @param containCorners true if the 4 corner neighbors are considered a neighbor. False otherwise
     * @return HashSet<Point> containing all Points that are neighboring the given point and do not have a gridBarrier
     */
    public abstract HashSet<Point> getEmptyNeighboringPositions(Point point, boolean containCorners);

    /**
     * This method gets all position that neighbor the given point and contain a gridBarrier. The neighbors
     * coordinates are defined as the 8 coordinates that directly surround the given point. The parameter containCorners
     * control if the 4 position that are the corners will be considered a neighboring position.
     * @param point the point where this method will look for points that are neighboring it
     * @param containCorners true if the 4 corner neighbors are considered a neighbor. False otherwise
     * @return HashSet<Point> containing all Points that are neighboring the given point and have a gridBarrier there
     */
    public abstract HashSet<Point> getOccupiedNeighboringPositions(Point point, boolean containCorners);

    /**
     * This method gets the position that neighbor the given point. If the point is on an edge, a maximum  of 5 points
     * will be return and if its on a corner, a maximum of 3 points will be returned. The neighbors surround the given
     * point and the parameter containCorners toggles whether the 5 corner points that are next the the point are
     * considered a Neighbor.
     * @param point the point where this method will look for points that are neighboring it
     * @param containCorners true if the 4 corner neighbors are considered a neighbor. False otherwise
     * @return HashSet<Point> containing all Points that are neighboring the given point
     */
    public HashSet<Point> getNeighboringPositions(Point point, boolean containCorners)
    {
        HashSet<Point> neighboringObjects = new HashSet<>();

        if(isValidPoint(new Point(point.x - 1, point.y)))
            neighboringObjects.add(new Point(point.x - 1, point.y));
        if(isValidPoint(new Point(point.x + 1, point.y)))
            neighboringObjects.add(new Point(point.x + 1, point.y));
        if(isValidPoint(new Point(point.x, point.y - 1)))
            neighboringObjects.add(new Point(point.x, point.y - 1));
        if(isValidPoint(new Point(point.x, point.y + 1)))
            neighboringObjects.add(new Point(point.x, point.y + 1));

        if(!containCorners)
            return neighboringObjects;

        if(isValidPoint(new Point(point.x - 1, point.y - 1)))
            neighboringObjects.add(new Point(point.x - 1, point.y - 1));
        if(isValidPoint(new Point(point.x + 1, point.y - 1)))
            neighboringObjects.add(new Point(point.x + 1, point.y - 1));
        if(isValidPoint(new Point(point.x - 1, point.y + 1)))
            neighboringObjects.add(new Point(point.x - 1, point.y + 1));
        if(isValidPoint(new Point(point.x + 1, point.y + 1)))
            neighboringObjects.add(new Point(point.x + 1, point.y + 1));

        return neighboringObjects;
    }

    /**
     * This method retrieves the first gridBarrier found that is located at the Point p. This method will return null
     * if the point is not located on the Grid or if there is no gridBarriers located at that point. If there are multiple
     * gridBarriers located at the position, this method will return the first one found. In addition, there is no
     * guarantee that this method will return the same gridBarrier every single time.
     * @param point The position for where the GridBarrier Object you want to get is currently located
     * @return The first GridBarrier that is found that the given point.
     */
    public GridBarrier getBarrier(Point point)
    {
        if(!isValidPoint(point))
            return null;
        return _getBarrier(point);
    }

    /**
     * This method will return a HashSet containing all barriers that are located at the given point. If the point is
     * not a valid position, this method should return null, if there are not gridBarriers at that position, this method
     * should instead return an empty HashSet. If there are barrier at this point, a hashset with those barriers will be returned.
     * @param point The position for where the GridBarrier Objects you want to get are currently located
     * @return a HashSet<GridBarrier> containing all actors at the given point
     */
    public HashSet<GridBarrier> getBarriers(Point point)
    {
        if(!isValidPoint(point))
            return null;
        return _getBarriers(point);
    }

    /**
     * This method removes all gridBarriers that are located at the point. If the point is not on the Grid, this
     * method will not do anything. If the point is located on the grid, at gridBarriers located at the position
     * will be removed.
     * @param point The point where you want to remove all GridBarriers
     */
    public void removeBarriers(Point point)
    {
        if(!isValidPoint(point))
            return;
        _removeBarriers(point);
    }
}