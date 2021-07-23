package testing;

import math.Vector2D;
import math.hermiteSplines.CubicHermiteSpline;
import processing.core.PApplet;

import java.awt.*;
import java.awt.geom.Point2D;

public class HermiteSplineTester extends PApplet
{
    public static void main(String[] args)
    {
        PApplet.main("testing.HermiteSplineTester");
    }

    Point p1 = new Point(200,200);
    Point p2 = new Point(300,400);
    Point p3 = new Point(400,200);
    Vector2D v1 = new Vector2D(20,0);
    Vector2D v2 = new Vector2D(70,0);
    Vector2D v3 = new Vector2D(100,100);

    CubicHermiteSpline spline = new CubicHermiteSpline(p1,v1,p2,v2);
    CubicHermiteSpline spline1 = new CubicHermiteSpline(p2,v2,p3,v3);

    public void settings()
    {
        size(500,500);
    }

    public void drawAll()
    {
        background(255);
        fill(255, 0, 0);
        drawPoint(p1);
        drawPoint(p2);
        drawPoint(p3);
        fill(0, 255, 0);
        drawPoint(v1);
        drawPoint(v2);
        drawPoint(v3);

        line((float)p1.getX(), (float)p1.getY(), (float)(p1.getX() + v1.getX()), (float)(p1.getY() + v1.getY()));
        line((float)p2.getX(), (float)p2.getY(), (float)(p2.getX() + v2.getX()), (float)(p2.getY() + v2.getY()));
        line((float)p3.getX(), (float)p3.getY(), (float)(p3.getX() + v3.getX()), (float)(p3.getY() + v3.getY()));


        fill(0, 0, 255);
        for (double i = 0; i < 1; i += 0.001) {
            drawPoint(spline.generatePoint(i));
            drawPoint(spline1.generatePoint(i));
        }
    }

    public void setup() {
       drawAll();
    }

    public void drawPoint(Point2D p)
    {
        ellipse((float)p.getX(), (float)p.getY(), 2,2);
    }

    public void mousePressed()
    {
        v1.setLocation(mouseX,mouseY);
        drawAll();
    }

    public void keyPressed()
    {
        v2.setLocation(mouseX,mouseY);
        drawAll();
    }

    public void draw()
    {

    }
}
