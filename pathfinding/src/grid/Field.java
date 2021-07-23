package grid;

import actor.Actor;
import math.geometry.coordinates.DiscreteCoordinate;
import math.RGB;

/**
 * a Class that represent a 2D field in which actors can interact with its environment
 */
public class Field extends BoundedGrid<Actor>
{
    private BoundedGrid<RGB> tileColorTracker;

    /**
     * creates a instance of the Field
     * @param rows the number of rows the Field will have
     * @param cols the number or cols the Field will have
     */
    public Field(int rows, int cols)
    {
        super(rows,cols);
        tileColorTracker = new BoundedGrid<>(rows, cols);
    }

    /**
     * Runs through all the Actors on the Field and calls has the actor move to its next position
     */
    public void step()
    {
        for(Actor actor: super.getAllObjects())
            actor.step();
    }

    /**
     * Places an actor inside the Grid using the ContinuousCoordinate already specified within the actor class. For example if
     * the actor's position is (1,1) the Actor will be placed at the (1,1) spot on the field.
     * @param actor An Actor class that will be placed into the Grid
     * @return The Actor that was originally at that position
     */
    public Actor put(Actor actor)
    { return super.set(actor.getPosition(),actor); }

    /**
     * Places an actor inside the Grid using the specified coordinate and then sets the actors internal position to
     * be the given coordinate. For example if the actor original position is (1,1) and this method is run giving it a
     * a coordinate of (2,2) the actors internal position will be set the given position and then it will be added to the
     * field.
     * @param coordinate The coordinate the actor will be placed at
     * @param actor The actor that will be placed in
     * @return The Actor that was originally at that position
     */
    public Actor set(DiscreteCoordinate coordinate, Actor actor)
    {
        actor.setPosition(coordinate);
        return super.set(coordinate,actor);
    }

    /**
     * Runs through the Grid and corrects for mismatches between the actors internal position and their actual position
     * on the Field. The parameter gridCorrect specifies whether the Grid position is correct or the actors internal position
     * is correct.
     * @param gridCorrect True the grid is correct, False the actor is correct
     * @return
     */
    public boolean fixActorPositionMismatches(boolean gridCorrect)
    {
        boolean errors = false;
        for(int i = 0; i < super.getRows(); i++) {
            for (int j = 0; j < super.getCols(); j++) {
                DiscreteCoordinate current = new DiscreteCoordinate(i, j);
                Actor actor = super.get(current);
                if (actor.getPosition() != current) {
                    errors = true;
                    if (gridCorrect)
                        actor.setPosition(current);
                    else {
                        super.remove(current);
                        super.set(actor.getPosition(), actor);
                    }
                }
            }
        }
        return errors;
    }

    /**
     * gets the Grid that contains what the colors of each tile should be
     * @return
     */
    public Grid<RGB> getTileColorTracker()
    { return tileColorTracker; }

    /**
     * sets the Tile Color at the given coordinate
     * @param coordinate the ContinuousCoordinate that will have its color changed
     * @param color the Color it will be changed to
     */
    public void setTileColor(DiscreteCoordinate coordinate, RGB color)
    { tileColorTracker.set(coordinate, color);}

    /**
     * gets the color at the given coordinate
     * @param coordinate
     * @return
     */
    public RGB getTileColor(DiscreteCoordinate coordinate)
    { return tileColorTracker.get(coordinate); }
}
