package pathFinders.pathFindingTileStates;

import math.geometry.coordinates.DiscreteCoordinate;

public class DStarState implements Comparable<DStarState>
{
    private DiscreteCoordinate coordinate;
    private double RHS = Double.POSITIVE_INFINITY;
    private double G = Double.POSITIVE_INFINITY;
    private double primaryKey;

    public DStarState(DiscreteCoordinate coordinate)
    {
        this.coordinate = coordinate;
    }

    public double getRHS()
    { return RHS; }

    public double getG()
    { return G; }

    public double getPrimaryKey()
    { return primaryKey; }

    public DiscreteCoordinate getCoordinate()
    {
        return coordinate;
    }

    public void setRHS(double RHS)
    { this.RHS = RHS; }

    public void setG(double G)
    { this.G = G; }

    public void setPrimaryKey(double primaryKey)
    { this.primaryKey = primaryKey; }

    public int compareTo(DStarState other)
    {
        double  t = RHS - other.getRHS();
        return (int) t;
    }

    public boolean equals(Object other)
    {
        if(other instanceof DStarState)
            return coordinate.equals(((DStarState) other).getCoordinate());
        return false;
    }

    public String toString()
    {
        String rhs = "" + RHS;
        String g = "" + G;
        if(RHS != Double.POSITIVE_INFINITY)
        {
            rhs = String.format("%08f",RHS);
        }
        if (G != Double.POSITIVE_INFINITY)
        {
            g = String.format("%08f",G);
        }

        return "| rhs: " + rhs + " g: " + g;
    }
}