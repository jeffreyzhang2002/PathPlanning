package testing;

import barrier.GridBarrier;
import barrier.planeBarriers.LineBarrier;

import java.awt.*;
import java.awt.geom.Line2D;

public class GridBarrierTesting
{
    public static void main(String[] args)
    {
        GridBarrier barrier = new GridBarrier(new Point(10,10)); //Note that you can not change the position of the barrier
        barrier.getPosition().setLocation(new Point(20,20));
        barrier.getPosition().setLocation(new Point(11,11));
        System.out.println(barrier.getPosition());

        LineBarrier line = new LineBarrier(new Line2D.Double(0,0,10,10));
        System.out.println(line.getPosition());
        System.out.println(line.getBoundingPoints());
        System.out.println(line.getPropagatedPoints(10));
        System.out.println(line.toString());
    }
}
