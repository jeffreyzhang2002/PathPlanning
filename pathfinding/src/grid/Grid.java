package grid;

import math.geometry.coordinates.DiscreteCoordinate;
import java.util.HashSet;

/**
 * Basic structure for a Grid. A Grid acts like a 2D array but also has additional functionality that allows for
 * neighbor detection and edge detection
 * @param <E>
 */
public abstract class Grid<E>
{
    private final int rows, cols;

    /**
     * creates a Grid with the given number of rows and cols. Rows and Cols must be greater than 0
     * @param rows the number of rows this Grid will contain (X direction)
     * @param cols the number of cols this Grid will contain (Y direction)
     */
    public Grid(int rows, int cols)
    {
        if(rows <= 0 || cols <= 0 )
            throw new IllegalArgumentException("arguments must be > 0");
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * gets the number of rows the Grid contains
     * @return number of rows
     */
    public final int getRows()
    { return rows; }

    /**
     * returns the number of cols this grid has
     * @return the number of cols
     */
    public final int getCols()
    { return cols; }

    /**
     * checks if a given point is on the Grid. The Data structure acts the same way a 2D array would work.
     * Valid position are 0 <= x < rows && 0 <= y < cols
     * @param position the given position you want to check
     * @return True if the position is on the Grid. False if not on the Grid
     */
    public final boolean isValid(DiscreteCoordinate position)
    { return isValid(position.getX(), position.getY()); }

    /**
     * overloaded method version of isValid see above for more detail
     * @param x the x value
     * @param y the y value
     * @return true/false
     */
    public final boolean isValid(int x, int y)
    { return x >=0 && x < getRows() && y >=0 && y < getCols(); }

    /**
     * Checks if the position is on the edge of the grid.
     * @param position the Position you wish to check
     * @return true if it is on the edge and false otherwise
     */
    public final boolean isEdge(DiscreteCoordinate position)
    {
        return isValid(position) &&
                position.getX() == 0 || position.getX() == getRows() - 1
                && position.getY() ==0 && position.getY() == getCols() - 1;
    }

    /**
     * returns the current Grid
     * @return the current Grid
     */
    public final Grid getGrid()
    { return this; }

    /**
     * returns true or false depending on if this Grid contains any objects
     * @return true if there is no objects contained inside this Grid
     */
    public final boolean isEmpty()
    { return getAllObjects().isEmpty(); }

    /**
     * returns if the given position is Empty
     * @param position the Position you wish to check
     * @return true if the given position is null on the Grid. False otherwise
     */
    public final boolean isEmptyPosition(DiscreteCoordinate position)
    { return get(position) == null; }

    /**
     * This method gets a set of all the coordinates directly neighboring the given point. The parameter containCorners
     * allows the user to choose if coordinates on the corners are included in this list. If the given position
     * is on an edge or corner, this method will only return valid neighbors
     * @param position The position you want to get the neighbors of
     * @param containCorners True if this method should return the corners false otherwise
     * @return a HashSet with all of the valid NeighboringCoordinates
     */
    public final HashSet<DiscreteCoordinate> getNeighborCoordinates(DiscreteCoordinate position, boolean containCorners)
    {
        HashSet<DiscreteCoordinate> coordinates;
        if(containCorners)
            coordinates = new HashSet<>(8);
        else
            coordinates = new HashSet<>(4);

        if(isValid(position.getX() + 1, position.getY()))
            coordinates.add(new DiscreteCoordinate(position.getX() + 1, position.getY()));
        if(isValid(position.getX() - 1, position.getY()))
            coordinates.add(new DiscreteCoordinate(position.getX() - 1, position.getY()));
        if(isValid(position.getX(), position.getY() + 1))
            coordinates.add(new DiscreteCoordinate(position.getX(), position.getY() + 1));
        if(isValid(position.getX(), position.getY() - 1))
            coordinates.add(new DiscreteCoordinate(position.getX(), position.getY() - 1));

        if(containCorners)
        {
            if(isValid(position.getX() + 1, position.getY() + 1))
                coordinates.add(new DiscreteCoordinate(position.getX() + 1, position.getY() + 1));
            if(isValid(position.getX() + 1, position.getY() - 1))
                coordinates.add(new DiscreteCoordinate(position.getX() + 1, position.getY() - 1));
            if(isValid(position.getX() - 1, position.getY() + 1))
                coordinates.add(new DiscreteCoordinate(position.getX() - 1, position.getY() + 1));
            if(isValid(position.getX() - 1, position.getY() - 1))
                coordinates.add(new DiscreteCoordinate(position.getX() - 1, position.getY() - 1));
        }
        return coordinates;
    }

    /**
     * returns a list of all the neighboring coordinates that DO NOT contain any objects
     * @param position the position you wish to find the empty neighboring coordinates
     * @param containCorners if the corners should be included
     * @return a HashSet containing all empty neighboring coordinates
     */
    public HashSet<DiscreteCoordinate> getEmptyNeighboringCoordinates(DiscreteCoordinate position, boolean containCorners)
    {
        HashSet<DiscreteCoordinate> coordinateList = getNeighborCoordinates(position,containCorners);
        HashSet<DiscreteCoordinate> returnList = new HashSet<DiscreteCoordinate>();
        for(DiscreteCoordinate c : coordinateList)
            if(isEmptyPosition(c))
                returnList.add(c);
        return returnList;
    }

    /**
     * returns a Set of all the Neighbors to the the given position that contain objects
     * @param position the given position
     * @param containCorners true of false if the corners should be considered
     * @return a Set of all occupied neighboringCoordinates
     */
    public HashSet<DiscreteCoordinate> getOccupiedNeighboringCoordinates(DiscreteCoordinate position, boolean containCorners)
    {
        HashSet<DiscreteCoordinate> coordinatelist = getNeighborCoordinates(position,containCorners);
        HashSet<DiscreteCoordinate> returnList = new HashSet<DiscreteCoordinate>();
        for(DiscreteCoordinate c : coordinatelist)
            if(!isEmptyPosition(c))
                returnList.add(c);
        return returnList;
    }

    /**
     * this method gets a Set of all the objects that are neighboring the the given position
     * @param position The position you wish to find neighboring actors of
     * @param containCorners true or false if corners should be included
     * @return a set of all neighboring objects
     */
    public HashSet<E> getNeighboringObjects(DiscreteCoordinate position, boolean containCorners)
    {
        HashSet<DiscreteCoordinate> neighboringCoordinate = getOccupiedNeighboringCoordinates(position,containCorners);
        HashSet<E> actorList = new HashSet<>();

        for(DiscreteCoordinate c: neighboringCoordinate)
            actorList.add(this.get(c));
        return actorList;
    }

    /**
     * gets a Set of all Object on the Grid
     * @return a Set of all objects contained on the Grid
     */
    public HashSet<E> getAllObjects()
    {
        HashSet<E> objectList = new HashSet<>();
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                DiscreteCoordinate temp = new DiscreteCoordinate(i,j);
                if(!isEmptyPosition(temp))
                    objectList.add(get(temp));
            }
        }
        return objectList;
    }

    /**
     * gets a list of all the Coordinates that contain and Object
     * @return list of all coordinates that have Objects
     */
    public HashSet<DiscreteCoordinate> getOccupiedCoordinates()
    {
        HashSet<DiscreteCoordinate> coordinates = new HashSet<>();
        for(int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                DiscreteCoordinate temp = new DiscreteCoordinate(i,j);
                if(!isEmptyPosition(temp))
                    coordinates.add(temp);
            }
        }
        return coordinates;
    }

    /**
     * Sets the given position to contain null
     * @param position the position that you with to remove
     * @return the Object that was originally at the position
     */
    public E remove(DiscreteCoordinate position)
    { return set(position, null); }

    /**
     * gets the Object at the given position
     * @param position the position that is contained inside the Grid. This method can return null if nothing is at the given position
     * @return the Object at the given position. Null is a valid output
     */
    public abstract E get(DiscreteCoordinate position);

    /**
     * Set the given position to contain the given object
     * @param position The position that you wish to set
     * @param object The object that the position should be set to
     * @return the Object that was originally at the position
     */
    public abstract E set(DiscreteCoordinate position, E object);

    /**
     * clears all objects of the grid
     */
    public abstract void clear();

    public String toString()
    {
        String s = "";
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
                s += this.get(new DiscreteCoordinate(i,j)).toString() + " ";
            s += "\n";
        }
        return s;
    }
}
