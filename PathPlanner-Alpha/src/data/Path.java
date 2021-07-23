package data;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Path
{
    private ArrayList<Point2D> pathConstraints;
    private ArrayList<Point2D> path;

    public Path(ArrayList<Point2D> pathConstraints)
    {
        this.pathConstraints = pathConstraints;
        this.path = (ArrayList<Point2D>) pathConstraints.clone();
    }

    public void addConstraint(Point2D constraintPoint)
    { pathConstraints.add(constraintPoint); }

}
