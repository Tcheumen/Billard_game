package Model;

/**
 * The Hole class represents a hole on the billiard table.
 * It contains information about the hole's position and size.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */
public class Hole {

    /**
     * Size of the hole
     */
    private float holeSize;

    /**
     * X-coordinate of the hole
     */
    private float x;

    /**
     * Y-coordinate of the hole
     */
    private float y;

    /**
     * Constructor for the Hole class.
     *
     * @param x : X-coordinate of the hole
     * @param y : Y-coordinate of the hole
     * @param holeSize : Size of the hole
     */
    public Hole(float x, float y, float holeSize) {
        this.x = x;
        this.y = y;
        this.holeSize = holeSize;
    }

    /**
     * Get the size of the hole.
     *
     * @return : Size of the hole
     */
    public float getHoleSize() {
        return holeSize;
    }



    /**
     * Get the X-coordinate of the hole.
     *
     * @return : X-coordinate of the hole
     */
    public float getX() {
        return x;
    }



    /**
     * Get the Y-coordinate of the hole.
     *
     * @return : Y-coordinate of the hole
     */
    public float getY() {
        return y;
    }


}
