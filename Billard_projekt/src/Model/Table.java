package Model;

import processing.core.PVector;

import java.util.Arrays;

import static java.lang.Math.*;
import static processing.core.PApplet.dist;

/**
 * The Table class represents the billiard table in the game.
 * It contains methods for managing game logic, ball interactions, and collisions.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */

public class Table {

    /**
     * Array to store individual Ball objects on the table
     */
    Ball[] balls;

    /**
     * Array to store individual Hole objects on the table
     */
    Hole [] holes;

    /**
     *  Player's score in the game
     */
    private int score = 0;

    /**
     * Number of moves made by the player
     */
    private int moves = 0;

    /**
     * X-coordinate of the table
     */
    private float myX;

    /**
     * Y-coordinate of the table
     */
    private float myY;

    /**
     * Width of the table
     */
    private float myW;

    /**
     * Height of the table, calculated as half of the width
     */
    private float myH;

    /**
     * Size of the holes on the table
     */
    private float HoleSize;

    /**
     *  Size of the individual balls on the table
     */
    private float BallSize;

    /**
     * Count of white balls pocketed by the player
     */
    private int whiteBallPocketCount = 0;

    /**
     * Number of remaining shots for the player
     */
    private int remainingShots = 5;

    /**
     *  Flag indicating whether the white ball is currently in a hole
     */
    private boolean whiteBallInHole = false;

    /**
     * Flag indicating whether the restart button should be displayed
     */
    boolean showRestartButton = false;

    /**
     * Flag indicating whether the game is over
     */
    private boolean isGameOver;


    /**
     * Constructor for the Table class.
     *
     * @param X : X-coordinate of the table
     * @param Y : Y-coordinate of the table
     * @param W : Width of the table
     * @param holeSize : Size of the holes on the table
     */
   public Table(float X, float Y, float W, float holeSize){
       myX = X;
       myY = Y;
       myW = W;
       myH = W / 2;
       HoleSize = holeSize;
       BallSize = holeSize / 2;
       balls = new Ball[16];
       Arrays.fill(balls, new Ball(50));


       holes = new Hole[]{
               new Hole( myX, myY, HoleSize),
               new Hole( myX, myY + myH, HoleSize),
               new Hole(myX + myW, myY, HoleSize),
               new Hole(myX + myW, myY + myH, HoleSize),
               new Hole(myX + myW / 2, myY, HoleSize),
               new Hole(myX + myW / 2, myY + myH, HoleSize)
       };
   }

    /**
     * Perform actions on the table, including ball movements and collisions.
     *
     * @param widthwall : Width of the wall
     * @param heightWall : Height of the wall
     */
    public void action(float widthwall, float heightWall) {
        collideCheck();
        for (int i = 0; i < balls.length; i++) {
            if (balls[i].getX() <= myX + BallSize/2 || balls[i].getX() >= myX + myW - BallSize/2)
                balls[i].Xbounce(widthwall);
            if (balls[i].getY() <= myY + BallSize/2 || balls[i].getY() >= myY + myH - BallSize/2)
                balls[i].Ybounce(heightWall);

             balls[i].move();
            if(i == 0 && balls[i].isRemoved())
                whiteBallInHole = true;
        }
    }

    /**
     * Get the count of pocketed white balls.
     *
     * @return : Count of white balls pocketed
     */
    public int getWhiteBallPocketCount() {
        return whiteBallPocketCount;
    }


    /**
     * Check for collisions between balls and holes.
     */
    public void collideCheck() {
        for (int i = 0; i < balls.length - 1; i++) {
            for (int j = i + 1; j < balls.length; j++) {
                if (balls[i].getLocation().dist(balls[j].getLocation()) <= BallSize)
                    balls[i].collide(balls[j]);
            }
        }
        checkHoleCollision();
        removeBallFromTable();
    }


    /**
     * Check collisions between balls and holes.
     */
    public void  checkHoleCollision(){

        float hardnessLevel = 2;
        for (int i = 0; i < balls.length; i++) {
            for (int j = 0; j < holes.length; j++) {
                boolean inTheHole = collideCircleCircle(balls[i].getX(), balls[i].getY(), balls[i].getSize(),
                        holes[j].getX(), holes[j].getY(), holes[j].getHoleSize() / hardnessLevel);

                if (inTheHole && i > 0) {
                    whiteBallInHole = true;
                    balls[i].removed = true;
                    score++;
                }
                if (inTheHole && i == 0) {
                    // TODO: Gotta wait until all balls settle.
                    balls[0] = new Ball( new PVector(myX + myW / 4, myY + myH / 2), BallSize, 0);
                    whiteBallPocketCount ++;
                      remainingShots--;
                    if(whiteBallPocketCount >= 5 )
                        isGameOver = true;
                }
            }
        }
    }

    /**
     * Remove flagged balls from the table.
     */
    public void removeBallFromTable(){
        for (int i = balls.length - 1; i >= 0; i--) {
            if (balls[i].removed) {
                for (int j = i; j < balls.length - 1; j++)
                    balls[j] = balls[j + 1];
                balls = Arrays.copyOf(balls, balls.length - 1);
            }
        }
    }

    /**
     * Check collision between two circles.
     *
     * @param x : X-coordinate of the first circle
     * @param y : Y-coordinate of the first circle
     * @param s : Size of the first circle
     * @param x1 : X-coordinate of the second circle
     * @param y1 : Y-coordinate of the second circle
     * @param s1 : Size of the second circle
     * @return : True if the circles collide, false otherwise
     */
    public  boolean collideCircleCircle(float x, float y, float s, float x1, float y1, float s1) {
        return dist(x, y, x1, y1) < (s + s1) / 2;
    }

    /**
     * Increment the moves count.
     */
    public void madeMove() { moves++;
    }

    /**
     * Rack the balls on the table for a new game.
     */
    public void rack() {
        PVector footSpot = new PVector(myX + 3 * myW / 4, myY + myH / 2);
        balls = new Ball[16];

        float offsetX = (float) (BallSize * cos(PI / 3));
        float offsetY = BallSize / 2;

        balls[0] = new Ball(new PVector(myX + myW / 4, myY + myH / 2), BallSize, 0);

        for (int i = 1; i < 16; i++) {
            float angle = (float) (PI / 3 * (i - 1));
            float x = footSpot.x + (float) (i % 2 == 0 ? 2 * offsetX * cos(angle) : offsetX * cos(angle));
            float y = footSpot.y + (float) (i % 2 == 0 ? 2 * offsetY * sin(angle) : offsetY * sin(angle));
            balls[i] = new Ball(new PVector(x, y), BallSize, i);
        }
    }


    /**
     * Reset the game state.
     */
    public void resetGame() {
        score = 0;
        moves = 0;
        remainingShots = 5;
        for (int i = 0; i < balls.length; i++) {
            if (balls[i] != null) {
                balls[i].setRemoved(false);
            }
        }
    }

    /**
     * Restart the game by resetting and racking the balls.
     */
    public void restartGame() {
        resetGame();
        rack();
        whiteBallPocketCount = 0;
        showRestartButton = false;
    }

    /**
     * Check if all balls, except the white ball, are in the holes.
     *
     * @return : True if all balls (except white) are in holes, false otherwise
     */
    public boolean isAllBallsInHolesExceptWhite() {
        int whiteBallIndex = 0;
        boolean allBallsInHolesExceptWhite = true;

        for (int i = 1; i < balls.length; i++) {
            if (i != whiteBallIndex && balls[i] != null && !balls[i].isRemoved()) {
                allBallsInHolesExceptWhite = false;
                break;
            }
        }
        return allBallsInHolesExceptWhite;
    }

    /**
     * Get the game score.
     *
     * @return : Game score
     */
    public int getScore() { return score;
    }

    /**
     * Set the game score.
     *
     * @param score : New game score
     */
    public void setScore(int score) { this.score = score; }

    /**
     * Get the number of moves made by the player.
     *
     * @return : Number of moves
     */
    public int getMoves() { return moves; }

    /**
     * Set the number of moves made by the player.
     *
     * @param moves : New number of moves
     */
    public void setMoves(int moves) { this.moves = moves; }

    /**
     * Get the X-coordinate of the table.
     *
     * @return : X-coordinate of the table
     */
    public float getMyX() { return myX; }

    /**
     * Get the Y-coordinate of the table.
     *
     * @return : Y-coordinate of the table
     */
    public float getMyY() { return myY; }


    /**
     * Get the width of the table.
     *
     * @return : Width of the table
     */
    public float getMyW() { return myW; }


    /**
     * Get the height of the table.
     *
     * @return : Height of the table
     */
    public float getMyH(){ return myH; }


    /**
     * Get the size of the holes on the table.
     *
     * @return : Size of the holes
     */
    public float getHoleSize() { return HoleSize; }


    /**
     * Get the array of holes on the table.
     *
     * @return : Array of holes
     */
    public Hole[] getHoles() { return holes; }


    /**
     * Get the array of balls on the table.
     *
     * @return : Array of balls
     */
    public Ball[] getBalls() { return balls; }

    /**
     * Set the array of balls on the table.
     *
     * @param balls : New array of balls
     */
    public void setBalls(Ball[] balls) { this.balls = balls; }

    /**
     * Check if the game is over.
     *
     * @return : True if the game is over, false otherwise
     */
    public boolean isGameOver() { return isGameOver; }

    /**
     * Get whether the restart button should be displayed.
     *
     * @return True if the restart button should be displayed, false otherwise
     */
    public boolean isShowRestartButton() { return showRestartButton; }


    /**
     * Get the number of remaining shots.
     *
     * @return Number of remaining shots
     */
    public int getRemainingShots() { return remainingShots; }

}
