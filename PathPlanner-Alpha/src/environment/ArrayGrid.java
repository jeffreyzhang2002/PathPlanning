package environment;

import barrier.GridBarrier;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * An ArrayGrid is subclass of both Grid and Environment. An ArrayGrid has the same behavior as ListedGrid but both of
 * these classes store GridBarriers in different ways. ArrayGrid stores each barrier in a 2D array with its position
 * being the coordinate of the GridBarrier. ArrayGrids has faster access time for each barrier but,
 * will often use more memory as memory is allocated to each position even if no GridBarrier is located at that position.
 * This class is preferred in situations when the Grid will have many barriers placed inside or when the Grid has
 * small dimensions. Please note that only
 * @author Jeffrey
 * @since 5/8/2020
 * @version 1
 */
public class ArrayGrid extends Grid
{
    private ArrayList<GridBarrier>[][] barriers;

    /**
     * Constructor for creating a ArrayGrid with the given number of rows and columns. Rows refers to the maximum
     * x value an actor can have and columns refers to the maximum y value an actor can have. This means that valid x
     * coordinates are in the range [0 ; rows) and valid y coordinates are in the range [0 ; cols). Please remember that
     * after rows and columns are set, their is no way to change these values without creating a new instance of the
     * class.
     * @param rows Number of rows inside the environment and the maximum x position for actors stored inside
     * @param cols Number of columns inside the environment and the maximum y position for actors stored inside
     */
    public ArrayGrid(int rows, int cols)
    {
        super(rows, cols);
        barriers = new ArrayList[rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                barriers[i][j] = new ArrayList<>(1);
    }

    /**
     * Method for adding a GridBarriers into the Grid. The position the barrier will be stored at is GridBarriers
     * position. This method is package protected because it acts as a helper method for the addBarrier(E barrier)
     * method and should not run outside of the class. This method does not need to check if the GridBarrier's Position
     * are actually within the Environment because this will be check automatically in the getBarrier(E barrier) method.
     * @param barrier the GridBarrier that will be added into the ArrayGrid at the position of the GridBarrier
     */
    void _addBarrier(GridBarrier barrier)
    {
        Point2D position = barrier.getPosition();
        barriers[(int)position.getX()][(int)position.getY()].add(barrier);
    }

    /**
     * Method for removing the given barrier from the Environment if the given barrier is not inside the Environment
     * nothing should happen otherwise the barrier should be removed from the ArrayGrid.
     * @param barrier the Barrier object that will be removed from the current Environment.
     */
    public void removeBarrier(GridBarrier barrier)
    {
        Point2D position = barrier.getPosition();
        if(isValidPoint(position))
            barriers[(int)position.getX()][(int)position.getY()].remove(barrier);
    }

    /**
     * Method for returning a HashSet<E> that contains all of the Barriers that currently inside the ArrayGrid.
     * Altering the HashSet<E> should have no impact on the GridBarriers inside the the ArrayGrid
     * @return set of all barriers in the grid
     */
    public HashSet<GridBarrier> getBarriers()
    {
        HashSet<GridBarrier> actorList = new HashSet<>();
        for(int i = 0; i < barriers.length; i++)
            for(int j = 0; j < barriers[0].length; j++)
                actorList.addAll(barriers[i][j]);
        return actorList;
    }

    /**
     * Deletes all barriers that currently in the ArrayGrid and resets it to the default state
     */
    public void clearBarriers()
    {
        for(int i = 0; i < barriers.length; i++)
            for(int j = 0; j < barriers[0].length; j++)
                barriers[i][j].clear();
    }

    /**
     * Method for getting the GridBarrier located at the given position. If there are more that one barrier class
     * located at that position, this method will return that first GridBarrier that is found at that position. If there
     * are not barriers at the point, null will also be returned. This should not be ran outside of this
     * class an acts as a helper method. Instead the getBarrier(Point  point) method
     * should be used instead. This method does not check if the given point is within the Grid instead the
     * getBarrier(Point point) method will handel these errors.
     * @param point The position for where the GridBarrier Object you want to get is currently located
     * @return The first GridBarrier that is found that the given point.
     */
    GridBarrier _getBarrier(Point point)
    {
        if(barriers[point.x][point.y].isEmpty())
            return null;
       return barriers[point.x][point.y].get(0);
    }

    /**
     * Method for getting all GridBarriers that are located at the given point. This method acts as a helper method
     * for the getBarriers(Point point) method. This function should not be ran outside of
     * this class. In addition, this method will not check if the given point is within the ArrayGrid. Instead the
     * getBarriers(Point point) class will check this automatically. If the point is located on the grid but nothing is located at
     * the point, an empty HashSet should be returned.
     * @param point The position for where the GridBarrier Objects you want to get are currently located
     * @return a HashSet<GridBarrier> containing all actors at the given point
     */
    HashSet<GridBarrier> _getBarriers(Point point)
    {
        if(isValidPoint(point))
            return (HashSet<GridBarrier>) barriers[point.x][point.y].stream().collect(Collectors.toSet());
        return null;
    }

    /**
     * This method removes all GridBarrier located at the given position. This method is package protected
     * and acts as a helper method for the removeBarriers(Point point) method. This method should not be ran
     * outside of the current class. In addition this method does to check if the given point is in the ArrayGrid,
     * instead if only provides functionality for removing GridBarriers from a certain point;
     * @param point The point where you want to remove all GridBarriers
     */
    void _removeBarriers(Point point)
    { barriers[point.x][point.y].clear(); }

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
        HashSet<GridBarrier> neighborActors = new HashSet<>();
        HashSet<Point> neighborPoints = super.getNeighboringPositions(point, containCorners);
        for(Point p: neighborPoints)
        {
            GridBarrier a = getBarrier(p);
            if(a != null)
                neighborActors.add(a);
        }
        return  neighborActors;
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
        HashSet<Point> emptyNeighborPoints = new HashSet<>();
        HashSet<Point> neighborPoints = super.getNeighboringPositions(point, containCorners);
        for(Point p: neighborPoints)
        {
            if(getBarrier(p) == null)
                emptyNeighborPoints.add(p);
        }
        return  emptyNeighborPoints;
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
        HashSet<Point> emptyNeighborPoints = new HashSet<>();
        HashSet<Point> neighborPoints = super.getNeighboringPositions(point, containCorners);
        for(Point p: neighborPoints)
        {
            if(getBarrier(p) != null)
                emptyNeighborPoints.add(p);
        }
        return  emptyNeighborPoints;
    }
}