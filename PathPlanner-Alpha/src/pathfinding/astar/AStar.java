package pathfinding.astar;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import environment.Grid;
import pathfinding.PathFinder;
import pathfinding.PathFinderData;

public class AStar extends PathFinder<Grid>
{
    PriorityQueue<PathFinderData> openSet;
    HashSet<Point> closedSet;
    HashMap<Point, Point> pointConnectionLibrary = new HashMap<>();

    public AStar(Grid grid)
    { super(grid); }

    public AStar(Grid grid, Point start, Point end)
    { super(grid, start, end); }

    public AStar(Grid grid, Point... point2DS)
    { super(grid, point2DS); }

    public ArrayList<Point2D> _generatePath (Point2D start, Point2D end, boolean containCorners)
    {
        openSet   = new PriorityQueue<>();
        closedSet = new HashSet<>();
        pointConnectionLibrary = new HashMap<>();

        PathFinderData startPoint = new PathFinderData(new Point((int)start.getX(), (int)start.getY()), 0, heuristic(start, end));
        openSet.add(startPoint);

        while(!openSet.isEmpty())
        {
            PathFinderData current = openSet.poll();
            closedSet.add((Point) current.point);

            if(current.point.equals(end))
                return reconstructPath((Point) current.point);

            HashSet<Point> neighbors = ((Grid) super.getEnvironment()).getEmptyNeighboringPositions((Point) current.point, containCorners);

            for(Point neighborPoint : neighbors)
            {
                if(!closedSet.contains(neighborPoint))
                {
                    double newGScore = current.gScore + heuristic(current.point, neighborPoint);
                    PathFinderData currentKeyPointData = getProcessedPointData(neighborPoint);

                    if(currentKeyPointData == null || newGScore < currentKeyPointData.gScore) {
                        openSet.add(new PathFinderData(neighborPoint, newGScore, heuristic(neighborPoint, end)));
                        pointConnectionLibrary.put(neighborPoint, (Point)current.point);
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<Point2D> reconstructPath(Point end)
    {
        Point current = end;
        ArrayList<Point2D> path = new ArrayList<>();
        path.add(end);
        while(pointConnectionLibrary.get(current) != null)
        {
            current = pointConnectionLibrary.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    private PathFinderData getProcessedPointData(Point2D point)
    {
        for(PathFinderData group : openSet)
            if(group.point.equals(point))
                return group;
        return null;
    }

    private double heuristic(Point2D a, Point2D b)
    { return a.distanceSq(b); }
}
