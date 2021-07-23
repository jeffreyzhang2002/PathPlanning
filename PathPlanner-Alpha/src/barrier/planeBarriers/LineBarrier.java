package barrier.planeBarriers;

import barrier.PlaneBarrier;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A line Barrier is PlaneBarrier defined by two start points. This type of barrier can be thought of as linesegment
 * connect to two points. This class can only by used in a Plane environment.
 */
public class LineBarrier extends PlaneBarrier
{
    private Line2D line;

    /**
     * Creates a LineBarrier with a given line from java.awt package.
     * @param line a line that will become the barrier
     */
    public LineBarrier(Line2D line)
    { this.line = line; }

    /**
     * This method gets the bounding points of the LineBarrier. The bounding points are defined as the two end points
     * of the Line.
     * @return ArrayList<Point2D> containing the bounding points.
     */
    public ArrayList<Point2D> getBoundingPoints()
    {
        ArrayList<Point2D> boundingPoints = new ArrayList<>(2);
        boundingPoints.add(line.getP1());
        boundingPoints.add(line.getP2());
        return boundingPoints;
    }

    /**
     * This method returns the propagated points of the LineBarrier. Propagated points are points that are a certain
     * magnitude away from the end points. For a LineBarrier, the propagated points will be collinear with line segment
     * and a distance of magnitude away from the endpoints. These points are not directly on the line segment
     * @param mag the magnitude of propagation or the distance away the propagated points are from end points
     * @return a HashSet<Point2D> containing all the propagated points of the LineBarrier
     */
    public HashSet<Point2D> getPropagatedPoints(double mag)
    {
        HashSet<Point2D> propagatedPoints = new HashSet<>();

        double angle = Math.atan2(line.getY2() - line.getY1(), line.getX2() - line.getX1());

        Point2D.Double P1 = new Point2D.Double();
        Point2D.Double P2 = new Point2D.Double();

        if (line.getX1() < line.getX2()) {
            P1.x = line.getX1() - Math.abs(Math.cos(angle)) * mag;
            P2.x = line.getX2() + Math.abs(Math.cos(angle)) * mag;
        } else {
            P1.x = line.getX1() + Math.abs(Math.cos(angle)) * mag;
            P2.x = line.getX2() - Math.abs(Math.cos(angle)) * mag;
        }

        if (line.getY1() < line.getY2()) {
            P1.y = line.getY1() - Math.abs(Math.sin(angle)) * mag;
            P2.y = line.getY2() + Math.abs(Math.sin(angle)) * mag;
        } else {
            P1.y = line.getY1() + Math.abs(Math.sin(angle)) * mag;
            P2.y = line.getY2() - Math.abs(Math.sin(angle)) * mag;
        }

        propagatedPoints.add(P1);
        propagatedPoints.add(P2);

        return propagatedPoints;
    }
}
