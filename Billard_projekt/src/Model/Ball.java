package Model;

import processing.core.PVector;

import static processing.core.PApplet.*;

/**
 * The Ball class represents a ball in the game.
 * It contains methods for moving the ball, handling collisions, and accessing ball properties.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */
    public class Ball {

    /**
     * Position vector of the ball
     */
    PVector myLocation;

    /**
     * Force vector acting on the ball
     */
    PVector myForce;

    /**
     * Flag indicating whether the ball is removed
     */
    boolean removed;

    /**
     * Flag indicating whether the ball is in a hole
     */
    private boolean inHole = false;

    /**
     * Size of the ball
     */
    float S;

    /**
     * Color of the ball
     */
    int C;


    /**
     * Constructor for the Ball class.
     *
     * @param location : initial position of the ball
     * @param SIZE : size of the ball
     * @param COLOR : color of the ball
     */

    public Ball(PVector location, float SIZE, int COLOR) {

        myLocation = location.copy();
        myForce = new PVector(0, 0);
        S = SIZE;
        C = COLOR;
        removed = false;
    }

    /**
     * Alternate constructor with only size parameter.
     *
     * @param SIZE : size of the ball
     */
    public Ball(float SIZE){
        this.S = SIZE;
        removed = false;
    }
    /**
     * Move the ball based on its current force.
     */
    public void move() {
        myLocation.add(myForce);
        if (myForce.mag() > 0.02)
            myForce.setMag((float) (myForce.mag() - 0.02));
         else  myForce = new PVector();
    }

    /**
     * Bounce the ball horizontally when it hits a wall.
     *
     * @param widthWall : width of the wall
     */
    public void Xbounce(float widthWall) {
        myForce.x = (getX() > widthWall / 2) ? -abs(myForce.x) : abs(myForce.x);
    }

    /**
     * Bounce the ball vertically when it hits a wall.
     *
     * @param heigthWall : height of the wall
     */
    public void Ybounce(float heigthWall) {
        myForce.y = (getY() > heigthWall / 2) ? -abs(myForce.y) : abs(myForce.y);
    }

    /**
     * Convert polar coordinates to Cartesian coordinates.
     *
     * @param radius : radius of the polar coordinates
     * @param angle : angle of the polar coordinates
     * @return : Cartesian coordinates as a PVector
     */
    public PVector polar(float radius, float angle) {
        return new PVector((radius * cos(angle)), (radius * sin(angle)));
    }

    /**
     * Apply a hit force to the ball based on force magnitude and angle.
     *
     * @param force : magnitude of the force
     * @param theta : angle of the force
     */
    public void hit(float force, float theta) {

        myForce.add(polar(force, theta));
    }

    /**
     * Handle collision between two balls.
     *
     * @param that : the other ball involved in the collision
     */
    public void collide(Ball that) {
        float a = myLocation.copy().sub(that.myLocation).heading();
        myLocation = that.myLocation.copy().add(polar(S, a));
        float A1 = myForce.heading() - a;
        float A2 = that.myForce.heading() - a;
        PVector V1 = polar(myForce.mag() * cos(A1), a);
        PVector V2 = polar(that.myForce.mag() * cos(A2), a);
        myForce.sub(V1).add(V2);
        that.myForce.sub(V2).add(V1);
    }

    /**
     * Get the force vector acting on the ball.
     *
     * @return : force vector
     */
    public PVector getForce() { return myForce;
    }

    /**
     * Set the force vector acting on the ball.
     *
     * @param V : new force vector
     */
    public void setForce(PVector V) { myForce = V; }

    /**
     * Get the position vector of the ball.
     *
     * @return : position vector
     */
    public PVector getLocation() { return myLocation; }

    /**
     * Set the position vector of the ball.
     *
     * @param location : new position vector
     */
    public void setLocation(PVector location) { myLocation = location.copy();
    }

    /**
     * Get the x-coordinate of the ball.
     *
     * @return : x-coordinate
     */
    public float getX() { return myLocation.x;
    }

    /**
     * Get the y-coordinate of the ball.
     *
     * @return : y-coordinate
     */
    public float getY() { return myLocation.y;
    }

    /**
     * Get the size of the ball.
     *
     * @return : size of the ball
     */
    public float getSize() { return S;
    }

    /**
     * Check if the ball is removed.
     *
     * @return : true if the ball is removed, false otherwise
     */
    public boolean isRemoved() { return removed;
    }

    /**
     * Set the removed status of the ball.
     *
     * @param removed : new removed status
     */
    public void setRemoved(boolean removed) { this.removed = removed;
    }


    /**
     * Get the color of the ball.
     *
     * @return : color of the ball
     */
    public int getC() { return C;
    }
}
