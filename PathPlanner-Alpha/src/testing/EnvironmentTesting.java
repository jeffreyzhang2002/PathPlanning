package testing;

import barrier.GridBarrier;
import environment.ArrayGrid;
import environment.Environment;
import environment.Grid;
import environment.ListedGrid;

import java.awt.*;

public class EnvironmentTesting
{
    public static Grid arrayGrid = new ArrayGrid(10, 10);
    public static Grid listedGrid = new ListedGrid(10, 10);

    public static void main(String[] args)
    {
//        for(int i=0; i<arrayGrid.getRows(); i++)
//            for(int j=0; j<arrayGrid.getCols(); j++) {
//                //if (i % 2 == 0) {
//                    arrayGrid.addBarrier(new GridBarrier(new Point(i, j)));
//                    listedGrid.addBarrier(new GridBarrier(new Point(i, j)));
//               // }
//            }

        Point p = new Point(10,5);
        System.out.println(arrayGrid.getNeighboringBarriers(p,true));
        System.out.println(listedGrid.getNeighboringBarriers(p,true));



//        randomizeGridBarriers(100);

//        for(int i = -1; i<arrayGrid.getRows(); i++)
////        {
////            for(int j=-1; j<arrayGrid.getCols(); j++)
////            {
////                printData(new Point(i,j));
////            }
////        }
//
//        Point p = new Point(15,15);
//        Point q = new Point(4,4);
//        GridBarrier s = new GridBarrier(q);
//        GridBarrier t = new GridBarrier(q);
//
//        arrayGrid.removeBarriers(q);
//        listedGrid.removeBarriers(q);
//
//        arrayGrid.addBarrier(s);
//        listedGrid.addBarrier(s);
//        arrayGrid.addBarrier(t);
//        listedGrid.addBarrier(t);
//        printData(q);
//
//        arrayGrid.removeBarrier(s);
//        listedGrid.removeBarrier(s);
//        printData(q);
//        System.out.println("//////////");
//
//        printData(p);
//        arrayGrid.removeBarriers(p);
//        listedGrid.removeBarriers(p);
//        printData(p);





    }

    public static void printData(Point p)
    {
        System.out.println(p);
        System.out.println("List: " + arrayGrid.getBarriers(p) + " Array: " +  listedGrid.getBarriers(p));
        System.out.println("List: " + listedGrid.getBarrier(p) + " Array: " + arrayGrid.getBarrier(p) + "\n");
    }

    public static void randomizeGridBarriers(int randomBarriers)
    {
        for(int i = 0; i<randomBarriers; i++)
        {
            GridBarrier b = new GridBarrier(new Point(random(0,9), random(0,9)));
            arrayGrid.addBarrier(b);
            listedGrid.addBarrier(b);
        }
    }

    public static int random(int min, int max)
    {
        return (int) (Math.random() * (max - min) + min);
    }
}
