package testing;

import barrier.PlaneBarrier;
import barrier.planeBarriers.LineBarrier;
import environment.Plane;
import processing.core.PApplet;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashSet;

public class PlaneTesting extends PApplet
{
    public static void main(String[] args)
    {
        PApplet.main("testing.PlaneTesting");
    }

    Plane p = new Plane(500,500);

    public void settings()
    {
        size(500,500);
    }

    public void setup()
    {
        background(255);
        p.setPropagationMagnitude(10);
        LineBarrier temp = new LineBarrier(new Line2D.Double(100,100,400,400));
        p.addBarrier(temp);
        p.addBarrier(new LineBarrier(new Line2D.Double(100,100,400,400)));
        p.addBarrier(new LineBarrier(new Line2D.Double(100,400,400,100)));
        System.out.println(p.getActorBounds());
        System.out.println(p.getBarriers());
        System.out.println(p.getPropagatedPoints());
        p.removeBarrier(temp);
    }

    public void draw()
    {
        background(255);
        drawLine();
        drawPoint();

    }

    public void drawPoint()
    {
        for(Point2D point : p.getPropagatedPoints())
        {
            ellipse((float)point.getX(), (float)point.getY(),5,5);
        }
    }

    public void drawLine()
    {
        for(PlaneBarrier p : p.getBarriers())
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

    public void line(Point2D pointA, Point2D pointB)
    {
        line((float)pointA.getX(),(float)pointA.getY(),(float)pointB.getX(),(float)pointB.getY());
    }
}
