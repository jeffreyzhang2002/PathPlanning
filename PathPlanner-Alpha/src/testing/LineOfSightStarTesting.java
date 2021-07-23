package testing;

import barrier.PlaneBarrier;
import barrier.planeBarriers.LineBarrier;
import environment.Plane;
import pathfinding.lineofsightstar.LineOfSightStar;
import processing.core.PApplet;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class LineOfSightStarTesting extends PApplet
{
    Plane environment = new Plane(500,500 , 10);
    LineBarrier barrier1 = new LineBarrier(new Line2D.Double(100,100,400,400));
    LineBarrier barrier2 = new LineBarrier(new Line2D.Double(100,400,400,100));
    LineOfSightStar los = new LineOfSightStar(environment, new Point(10,10), new Point(490,490));

    public static void main(String[] args)
    { PApplet.main("testing.LineOfSightStarTesting"); }

    public void settings()
    {
        environment.addBarrier(barrier1);
        environment.addBarrier(barrier2);

        size(environment.getRows(), environment.getCols());

        los.addKeyStopPoint(new Point(100,100));
    }

    public void setup()
    {
        background(0);
        drawBarriers();
        drawPropPoint();
        drawStopPoint();

    }

    public void draw()
    {

    }

    public void mousePressed()
    {
        los.addKeyStopPoint(1,new Point(mouseX,mouseY));
    }

    public void keyPressed()
    {
        background(0);
        los.generatePath(false);
        stroke(255);

        drawBarriers();
        drawPropPoint();
        drawStopPoint();

        for(int i = 0; i < los.getPath().size()-1; i++)
        {
            line(los.getPath().get(i), los.getPath().get(i + 1));
        }

    }

    public void drawStopPoint()
    {
        fill(255,0,0);
        for(Point2D p : los.getKeyStopPoints())
            point(p, 5);
    }

    public void drawPropPoint()
    {
        fill(255);
        for(Point2D p : environment.getPropagatedPoints())
            point(p,5);
    }

    public void drawBarriers()
    {
        stroke(255);
        for(PlaneBarrier p : environment.getBarriers())
        {
            for(int i = 0; i < p.getBoundingPoints().size(); i++)
            {
                int j = i-1;
                if(j < 0)
                    j = p.getBoundingPoints().size() - 1;
                line(p.getBoundingPoints().get(i),p.getBoundingPoints().get(j));
            }
        }
    }

    public void point(Point2D point, int size)
    { ellipse((float)point.getX(), (float)point.getY(),size,size); }

    public void line(Point2D pointA, Point2D pointB)
    { line((float)pointA.getX(),(float)pointA.getY(),(float)pointB.getX(),(float)pointB.getY()); }
}
