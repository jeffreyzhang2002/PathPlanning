package environment;

import barrier.Barrier;
import java.awt.geom.Point2D;
import java.util.HashSet;

/**
 * Environment is a fully enclosed abstract class that acts as a framework for storing barriers. Environments are used
 * with pathfinders to generate the optimal path from a starting point to an ending point while avoiding all obstacles
 * and barriers on the Environment.  This class must be overridden to be instantiated and used in a pathfinder
 * @author Jeffrey
 * @since 5/5/2020
 * @version 1
 * @param <E> An Environment can only contain Barriers and its subclasses
 */
public abstract class Environment<E extends Barrier>
{
    private final int rows, cols;

    /**
     *Constructor for creating an environment with the given number of rows and columns. Rows refers to the maximum x
     * value an actor can have and columns refers to the maximum y value an actor can have. This means that valid x
     * coordinates are in the range [0 ; rows) and valid y coordinates are in the range [0 ; cols). After the number of
     * rows and columns are set, their is no way of changing them without creating a new Environment.
     * @param rows Number of rows inside the environment and the maximum x position for actors stored inside
     * @param cols Number of cols inside the environment and the maximum y position for actors stored inside
     */
    public Environment(int rows, int cols)
    {
        if(rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("Argument must be at least 1");
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Method for adding a barrier into the Environment. This method is package protected because it acts as a helper
     * method for the addBarrier(E barrier) method. This method must be overridden and should include code that add the
     * given barrier into the Environment. This method does not need to check if the Barrier's Position/Positions are
     * actually within the Environment because this will be check automatically in the getBarrier(E barrier) method.
     * @param barrier The Barrier object that will be added into the Environment.
     */
    abstract void _addBarrier(E barrier);

    /**
     * Method for removing the given barrier from the Environment if the given barrier is not inside the Environment
     * nothing should happen otherwise, the barrier should be removed. This method must be overridden by sub classes.
     * @param barrier the Barrier object that will be removed from the current Environment.
     */
    public abstract void removeBarrier(E barrier);

    /**
     * Method for returning a HashSet<E> that contains all of the Barriers that currently inside the Environment.
     * Altering the HashSet<E> should have no impact on the Barriers inside the Environment. This method must be
     * overridden by sub classes.
     * @return HashSet<E> containing all actors that are currently present in the Environment
     */
    public abstract HashSet<E> getBarriers();

    /**
     * Method for removing all barriers from the Environment. This method must be overridden by sub classes
     */
    public abstract void clearBarriers();

    /**
     * Method for adding a barrier into the Environment. This method will add the given actor into the Environment
     * using the barrier's position. If the actor's position is not within the domain of the Environment, this method
     * will throw an IllegalArgumentException. This method should be ran instead of the _addBarrier method
     * @param barrier The Barrier Object that will be added in the current Environment.
     */
    public void addBarrier(E barrier)
    {
        if(!isValidPoint(barrier.getPosition()))
            throw new IllegalArgumentException(barrier.getPosition() + " outside the domain");
        _addBarrier(barrier);
    }

    /**
     * Method for checking if the given Point2D is within the domain of the Environment. A point is considered inside
     * the Environment if the x value is within the range [0 ; rows) and the y value is within the range [0 ; cols).
     * Please note that rows and columns behave the same way length works in an array and they method will return false
     * if the given Point2D has a x value of rows or a y value of cols. This method will return true if the Point2D is
     * within the domain and false if it is not.
     * @param point The Point that will be checked whether it is inside the Environment.
     * @return true if x is within [0 ; rows) and y is within [0 ; cols). False otherwise
     */
    public boolean isValidPoint(Point2D point)
    { return point.getX() >= 0 && point.getX() < rows && point.getY() >= 0 && point.getY() < cols; }

    /**
     * Method for checking if the given Point2D is on the edge of the Environment. A point is on the edge of the
     * Environment if the x value is 0 or rows - 1 or the y value is 0 or cols - 1. This method will return true if
     * these conditions are satisfied and false otherwise.
     * @param point The Point that will be checked whether it is on the edge of the Environment.
     * @return true if x value is 0 or rows - 1 or the y value is 0 or cols - 1. False otherwise
     */
    public boolean isEdgePoint(Point2D point)
    { return point.getX() == 0 || point.getX() == rows - 1 || point.getY() == 0 || point.getY() == cols - 1;}

    /**
     * This method returns the number of barriers that are inside the given environment. The value
     * return will always be greater than or equal to 0
     * @return the number of barriers inside the Environment
     */
    public final int getBarrierNumber()
    { return getBarriers().size(); }

    /**
     * This method returns the number of rows in the Environment
     * @return rows The number of rows
     */
    public final int getRows()
    { return rows; }

    /**
     * This method returns the number of columns in the Environment.
     * @return cols The number of columns
     */
    public final int getCols()
    { return cols; }

    /**
     * Summarizes class as a String
     * @return
     */
    public String toString()
    { return getBarriers().toString(); }
}