package environment;

import barrier.GridBarrier;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * A ListedGrid is subclass of both Grid and Environment. An ListedGrid has the same behavior as an ArrayGrid but both of
 * these classes store the internal GridBarriers in different ways. ListedGrid stores each barrier in an ArrayList of
 * GridBarriers. This class takes longer for accessing internal barriers but it requires much less memory compared to
 * an ArrayGrid. It is recommended to use this class over an ArrayGrid when the Grid is very large or when there are few
 * GridBarriers on the class.
 * @author Jeffrey
 * @since 5/8/2020
 * @version 1
 */
public class ListedGrid extends Grid
{
    private ArrayList<GridBarrier> barriers;

    /**
     * Constructor for creating a ListedGrid with the given number of rows and columns. Rows refers to the maximum
     * x value an actor can have and columns refers to the maximum y value an actor can have. This means that valid x
     * coordinates are in the range [0 ; rows) and valid y coordinates are in the range [0 ; cols). Please remember that
     * after rows and columns are set, their is no way to change these values without creating a new instance of the
     * class. When this method is ran, no memory is reserved for Actors instead more memory usage will change dynamically
     * when more GridBarriers are added.
     * @param rows Number of rows inside the environment and the maximum x position for actors stored inside
     * @param cols Number of columns inside the environment and the maximum y position for actors stored inside
     */
    public ListedGrid(int rows, int cols)
    {
        super(rows,cols);
        barriers = new ArrayList<>();
    }

    /**
     * Method for adding a GridBarriers into the Grid. The position the barrier will be stored at will be the GridBarriers
     * own position. This method is package protected because it acts as a helper method for the addBarrier(E barrier)
     * method and should not run outside of the class. This method does not need to check if the GridBarrier's Position
     * are actually within the Environment because this will be check automatically in the getBarrier(E barrier) method.
     * @param barrier the GridBarrier that will be added into the ArrayGrid at the position of the GridBarrier
     */
    void _addBarrier(GridBarrier barrier)
    { barriers.add(barrier); }

    /**
     * Method for removing the given barrier from the Environment if the given barrier is not inside the Environment
     * nothing should happen otherwise the barrier should be removed from the ArrayGrid.
     * @param barrier the Barrier object that will be removed from the current Environment.
     */
    public void removeBarrier(GridBarrier barrier)
    { barriers.remove(barrier); }

    /**
     * Method for returning a HashSet<E> that contains all of the Barriers that currently inside the ArrayGrid.
     * Altering the HashSet<E> should have no impact on the GridBarriers inside the the ArrayGrid
     * @return set of all barriers in the grid
     */
    public HashSet<GridBarrier> getBarriers()
    { return (HashSet<GridBarrier>) barriers.stream().collect(Collectors.toSet()); }

    /**
     * Deletes all barriers that currently in the ArrayGrid and resets it to the default state
     */
    public void clearBarriers()
    { barriers.clear(); }

    /**
     * Method for getting the GridBarrier located at the given position. If there are more that one barrier class
     * located at that position, this method will return that first GridBarrier that is found at that position. This
     * should not be ran outside of this class an acts as a helper method. Instead the getBarrier(Point  point) method
     * should be used instead. This method does not check if the given point is within the Grid instead the
     * getBarrier(Point point) method will handel these errors. If no barrier exist at that location, null will be returned
     * @param point The position for where the GridBarrier Object you want to get is currently located
     * @return The first GridBarrier that is found that the given point.
     */
    GridBarrier _getBarrier(Point point)
    {
        for(GridBarrier actor : barriers)
            if(actor.getPosition().equals(point))
                return actor;
        return null;
    }

    /**
     * Method for getting all GridBarriers that are located at the given point. This method acts as a helper method
     * for the getBarriers(Point point) method. This function should not be ran outside of
     * this class. In addition, this method will not check if the given point is within the ArrayGrid. Instead the
     * getBarriers(Point point) class will check this automatically.
     * @param point The position for where the GridBarrier Objects you want to get are currently located
     * @return a HashSet<GridBarrier> containing all actors at the given point
     */
    HashSet<GridBarrier> _getBarriers(Point point)
    {
        HashSet<GridBarrier> actorsAtPosition = new HashSet<>();
        for (GridBarrier actor : barriers)
            if (actor.getPosition().equals(point))
                actorsAtPosition.add(actor);
        return actorsAtPosition;
    }

    /**
     * This method removes all GridBarrier located at the given position. This method is package protected
     * and acts as a helper method for the removeBarriers(Point point) method. This method should not be ran
     * outside of the current class. In addition this method does to check if the given point is in the ArrayGrid,
     * instead if only provides functionality for removing GridBarriers from a certain point;
     * @param point The point where you want to remove all GridBarriers
     */
    void _removeBarriers(Point point)
    {
        HashSet<GridBarrier> actorsAtPosition = getBarriers(point);
        for(GridBarrier a: actorsAtPosition)
            removeBarrier(a);
    }

    /**
     * Gets all of the GridBarriers that are neighboring the given point. Contain corners is a boolean parameter
     * to toggle if the 4 corner tiles that neighbor the given points are included. If there are no neighboring barriers,
     * this method should return a empty HashSet.
     * @param point the given point that this method will look for neighboring barriers from
     * @param containCorners true if corner neighbors are counted as a neighboring false if they are not
     * @return a HashSet<GridBarrier> that contain the neighboring barriers.
     */
    public HashSet<GridBarrier> getNeighboringBarriers(Point point, boolean containCorners)
    {
        HashSet<Point> neighbors = getNeighboringPositions(point, containCorners);
        HashSet<GridBarrier> neighborBarriers = new HashSet<>();
        for(Point p : neighbors)
        {
            HashSet<GridBarrier> b = getBarriers(p);
            if(b != null && !b.isEmpty())
                neighborBarriers.addAll(b);
        }
        return neighborBarriers;
    }

    /**
     * This method gets all position that neighbor the given point and do not contain a gridBarrier. The neighbors
     * coordinates are defined as the 8 coordinates that directly surround the given point. The parameter containCorners
     * control if the 4 position that are the corners will be considered a neighboring position. If there are no empty
     * neighboring positions, this method will return an empty set.
     * @param point the point where this method will look for points that are neighboring it
     * @param containCorners true if the 4 corner neighbors are considered a neighbor. False otherwise
     * @return HashSet<Point> containing all Points that are neighboring the given point and do not have a gridBarrier
     */
    public HashSet<Point> getEmptyNeighboringPositions(Point point, boolean containCorners)
    {
        HashSet<Point> neighbors = getNeighboringPositions(point, containCorners);
        HashSet<Point> emptyNeighborPoint = new HashSet<>();
        for(Point p : neighbors)
            if(getBarrier(p) == null)
                emptyNeighborPoint.add(p);
        return emptyNeighborPoint;
    }

    /**
     * This method gets all position that neighbor the given point and contain a gridBarrier. The neighbors
     * coordinates are defined as the 8 coordinates that directly surround the given point. The parameter containCorners
     * control if the 4 position that are the corners will be considered a neighboring position. If there are no
     * occupied positions that neighbor the given point, this method will return an empty set
     * @param point the point where this method will look for points that are neighboring it
     * @param containCorners true if the 4 corner neighbors are considered a neighbor. False otherwise
     * @return HashSet<Point> containing all Points that are neighboring the given point and have a gridBarrier there
     */
    public HashSet<Point> getOccupiedNeighboringPositions(Point point, boolean containCorners)
    {
        HashSet<Point> neighbors = getNeighboringPositions(point, containCorners);
        HashSet<Point> occupiedNeighborPoint = new HashSet<>();
        for(Point p : neighbors)
            if(getBarrier(p) != null)
                occupiedNeighborPoint.add(p);
        return occupiedNeighborPoint;
    }
}
