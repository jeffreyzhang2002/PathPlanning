package testing;

import math.Vector2D;
import math.hermiteSplines.CubicHermiteSpline;
import math.hermiteSplines.QuinticHermiteSpline;
import processing.core.PApplet;

import java.awt.*;
import java.awt.geom.Point2D;

public class QuinticHermiteSplineTester extends PApplet
{
    public static void main(String[] args)
    {
        PApplet.main("testing.QuinticHermiteSplineTester");
    }

    Point p1 = new Point(200,200);
    Point p2 = new Point(300,400);
    Point p3 = new Point(400,200);
    Vector2D v1 = new Vector2D(20,0);
    Vector2D v2 = new Vector2D(70,0);
    Vector2D v3 = new Vector2D(100,100);

    Vector2D a1 = new Vector2D(20,0);
    Vector2D a2 = new Vector2D(70,0);
    Vector2D a3 = new Vector2D(100,100);

    QuinticHermiteSpline spline = new QuinticHermiteSpline(p1,v1,a1,p2,v2,a2);
    QuinticHermiteSpline spline1 = new QuinticHermiteSpline(p2,v2,a2,p3,v3,a3);

    public void settings()
    {
        size(500,500);
    }

    public void setup() {
        background(255);
        fill(255, 0, 0);
        drawPoint(p1);
        drawPoint(p2);
        drawPoint(p3);
        fill(0, 255, 0);
        drawPoint(v1);
        drawPoint(v2);
        drawPoint(v3);
        fill(255, 255, 0);
        drawPoint(a1);
        drawPoint(a2);
        drawPoint(a3);


        fill(0, 0, 255);
        for (double i = 0; i < 1; i += 0.001) {
            drawPoint(spline.generatePoint(i));
           drawPoint(spline1.generatePoint(i));
        }
    }

    public void drawPoint(Point2D p)
    {
        ellipse((float)p.getX(), (float)p.getY(), 2,2);
    }

    public void mousePressed()
    {
        a1.setLocation(mouseX,mouseY);
        background(255);
        fill(255, 0, 0);
        drawPoint(p1);
        drawPoint(p2);
        drawPoint(p3);
        fill(0, 255, 0);
        drawPoint(v1);
        drawPoint(v2);
        drawPoint(v3);
        fill(255, 255, 0);
        drawPoint(a1);
        drawPoint(a2);
        drawPoint(a3);


        fill(0, 0, 255);
        for (double i = 0; i < 1; i += 0.001) {
            drawPoint(spline.generatePoint(i));
            drawPoint(spline1.generatePoint(i));
        }
    }

    public void keyPressed()
    {
        a2.setLocation(mouseX,mouseY);
        background(255);
        fill(255, 0, 0);
        drawPoint(p1);
        drawPoint(p2);
        drawPoint(p3);
        fill(0, 255, 0);
        drawPoint(v1);
        drawPoint(v2);
        drawPoint(v3);
        fill(255, 255, 0);
        drawPoint(a1);
        drawPoint(a2);
        drawPoint(a3);


        fill(0, 0, 255);
        for (double i = 0; i < 1; i += 0.001) {
            drawPoint(spline.generatePoint(i));
            drawPoint(spline1.generatePoint(i));
        }
    }

    public void draw()
    {

    }
}
