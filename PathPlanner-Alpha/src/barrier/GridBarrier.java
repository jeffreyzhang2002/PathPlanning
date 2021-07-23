package barrier;

import java.awt.*;

/**
 * A GridBarrier has the same functionality as Barrier except its position must be point with integer x and y values.
 * This class can be used in Grid Environments and can be instantiated as a barrier. Instead or returning point2D positions, this
 * class will use only use subclass of Point2D: Point.
 * @author Jeffrey
 * @since 5/8/2020
 * @version 1
 */
public class GridBarrier extends Barrier
{
    /**
     * Created an instance of GridBarrier at the given point. After points is set it can not change anymore
     * @param position
     */
    public GridBarrier(Point position)
    { super(position); }

    /**
     * returns the position of the GridBarrier as a point. Please note that this class returns a duplicate of the position
     * to prevent altering the x and y variables.
     * @return
     */
    public Point getPosition()
    { return new Point((int) super.getPosition().getX(), (int) super.getPosition().getY()); }

    /**
     * This method summarizes this class into a simple string containing its position in integer precision
     * @return a string containing the position of the GridBarrier
     */
    public String toString()
    { return "(" + (int) super.getPosition().getX() + "," + (int) super.getPosition().getY() + ")"; }
}
