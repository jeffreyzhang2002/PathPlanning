package testing;

import barrier.GridBarrier;
import environment.Grid;
import environment.ListedGrid;
import pathfinding.astar.AStar;
import processing.core.PApplet;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class AStarTesting extends PApplet
{
    public static void main (String[] args)
    {
        PApplet.main("testing.AStarTesting");
    }

    Grid grid = new ListedGrid(20,20);
    AStar pathfinder = new AStar(grid, new Point(0,0), new Point(grid.getRows()-1, grid.getCols()-1));
    int tileWidth;
    int tileHeight;

    public void settings()
    {
        size(600,600);
        tileWidth = width / grid.getRows();
        tileHeight = height / grid.getCols();

        GridBarrier g1 = new GridBarrier(new Point(5,5));
        GridBarrier g2 = new GridBarrier(new Point(5,4));
        GridBarrier g3 = new GridBarrier(new Point(5,6));
        GridBarrier g4 = new GridBarrier(new Point(6,5));
        GridBarrier g5 = new GridBarrier(new Point(8,11));
        GridBarrier g6 = new GridBarrier(new Point(8,10));
        grid.addBarrier(g1);
        grid.addBarrier(g2);
        grid.addBarrier(g3);
        grid.addBarrier(g4);
        grid.addBarrier(g5);
        grid.addBarrier(g6);

        pathfinder.addKeyStopPoint(new Point(0, grid.getCols() - 1));
        pathfinder.addKeyStopPoint(new Point(grid.getRows() - 1, 0));
        pathfinder.addKeyStopPoint(new Point(5,5));

    }

    public void setup()
    {
        pathfinder.generatePath(true);
        background(0);
        drawGrid();
        drawBarriers();
        System.out.println(pathfinder.getPath());
        drawPath();
    }

    public void draw()
    {

    }

    public void mousePressed()
    {

    }

    public void keyPressed()
    {
        background(0);
        drawGrid();
        drawPath();
    }

    public void drawBarriers()
    {
        fill(0);
        for(GridBarrier b : grid.getBarriers())
            drawPathTile(b.getPosition());
    }


    public void drawPath()
    {
        fill(255,0,0);
        for(int i = 0; i < pathfinder.getPath().size(); i++)
            drawPathTile(pathfinder.getPath().get(i));
    }

    public void drawPathTile(Point2D p)
    { rect((int)p.getX() * tileWidth, (int)p.getY() * tileHeight, width / grid.getRows(), height / grid.getCols()); }

    public void drawTile( int x, int y)
    { rect(x, y, width / grid.getRows(), height / grid.getCols()); }

    public void drawGrid()
    {
        fill(255);
        for(int i = 0; i < grid.getRows(); i++)
        {
            for(int j = 0; j < grid.getCols(); j++)
            {
                drawTile(i * tileWidth, j * tileHeight);
            }
        }
    }
}
