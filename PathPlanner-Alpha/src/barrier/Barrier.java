/**
 * Engineering Notebook Notes:
 * 1. Please note that position is stores as point2D. This was chosen because Point2D.Double and Point both
 * extend this class and PlaneBarrier may return Point2D.Double values.  In this class, the position should be
 * a point as points holder integer x and y values.
 */
package barrier;

import java.awt.geom.Point2D;

/**
 * This class represents a Barrier that is placed into an environment.
 * This class is meant to act as a scaffold for both the PlaneBarriers and GridBarriers class.
 * Please note that after position is set it can not be changed anymore. In order to change it position it the
 * grid, remove the actor at the original position and create a new actor at the desired position.
 * @author Jeffrey
 * @since 5/5/2020  |    5/8/2020
 * @version 1       |        2
 */
public abstract class Barrier
{
    private Point2D position;

    /**
     * Creates and Barrier with a starting position at the given position. After the position is set, the position
     * can no longer change anymore.
     * @param position starting position
     */
    public Barrier(Point2D position)
    { this.position = position; }

    /**
     * Creates a Barrier without any parameters
     */
    public Barrier()
    { this.position = new Point2D.Double(); }

    /**
     * Gets the position of the current Barrier as a new instance of the Point2D class. This is done to
     * prevent altering the position of the Actor within the class.
     * @return the position of the current Barrier
     */
    public Point2D getPosition()
    { return new Point2D.Double(position.getX(), position.getY()); }

    /**
     * Sets the position of the given barrier. This method should not be run outside
     * @param position the position the Barrier will be set to
     */
    void setPosition(Point2D position)
    { this.position = position; }

    /**
     * This method summarizes this class into a simple string containing its position
     * @return a string containing the position of the Barrier
     */
    public String toString()
    { return "(" + position.getX() + "," + position.getY() + ")"; }
}
