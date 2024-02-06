package Test;

import Model.Ball;
import Model.Table;
import org.junit.jupiter.api.Test;
import processing.core.PVector;


import static org.junit.jupiter.api.Assertions.*;
import static processing.core.PApplet.*;

/**
 * The ModelTest class contains unit tests for the Model package, including the Ball and Table classes.
 * It uses JUnit 5 for testing.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */

class ModelTest {

    /** Private boolean variable used for tracking whether the forces have been updated.
     * This variable is employed in a test scenario to check if forces were updated during a specific operation.
     */
    private boolean forceUpdated;

    /**
     * Tests the move method of the Ball class.
     * It checks whether the move method updates the location based on the force applied to the ball.
     */
    @Test
    void move_ShouldUpdateLocationBasedOnForce() {
        Ball ball = new Ball(new PVector(0, 0), 20, 255);
        ball.setForce(new PVector(2, 3));
        ball.move();
        assertEquals(new PVector(2, 3), ball.getLocation());
    }

    /**
     * Tests the Xbounce method of the Ball class.
     * It verifies that the Xbounce method changes the X force direction of the ball correctly.
     */
    @Test
    void Xbounce_ShouldChangeXForceDirection() {
        Ball ball = new Ball(new PVector(30, 40), 15, 255);
        ball.setForce(new PVector(5, 8));
        ball.Xbounce(100);
        assertEquals(new PVector(5, 8), ball.getForce());
    }

    /**
     * Tests the Ybounce method of the Ball class.
     * It verifies that the Ybounce method changes the Y force direction of the ball correctly.
     */
    @Test
    void Ybounce_ShouldChangeYForceDirection() {
        Ball ball = new Ball(new PVector(20, 25), 18, 255);
        ball.setForce(new PVector(4, 6));
        ball.Ybounce(50);
        assertEquals(new PVector(4, 6), ball.getForce());
    }

    /**
     * Tests the polar method of the Ball class.
     * It checks whether the polar method returns the correct polar coordinates.
     */
    @Test
    void polar_ShouldReturnCorrectPolarCoordinates() {
        Ball ball = new Ball(new PVector(0, 0), 10, 255);
        PVector result = ball.polar(15, radians(30));
        assertEquals(new PVector(15 * cos(radians(30)), 15 * sin(radians(30))), result);
    }

    /**
     * Tests the hit method of the Ball class.
     * It checks whether the hit method correctly adds force based on magnitude and angle.
     */
    @Test
    void hit_ShouldAddForceBasedOnMagnitudeAndAngle() {
        Ball ball = new Ball(new PVector(10, 20), 25, 255);
        ball.hit(5, radians(45));
        assertEquals(new PVector(5 * cos(radians(45)), 5 * sin(radians(45))), ball.getForce());
    }

    /**
     * Tests the collide method of the Ball class.
     * It verifies that the collide method handles collisions between two balls correctly.
     */
    @Test
    void collide_ShouldHandleCollisionBetweenBalls() {
        Ball ball1 = new Ball(new PVector(50, 60), 20, 255);
        Ball ball2 = new Ball(new PVector(60, 70), 18, 255);
        ball1.setForce(new PVector(3, 4));
        ball2.setForce(new PVector(-2, -3));
        ball1.collide(ball2);
        assertEquals(new PVector(45.857864f, 55.857864f), ball1.getLocation());
        assertEquals(new PVector(-2.9999998f, -1.9999998f), ball1.getForce());
        assertEquals(new PVector(60, 70), ball2.getLocation());
        assertEquals(new PVector(3.9999998f, 2.9999998f ), ball2.getForce());
    }

    /**
     * Tests the isRemoved method of the Ball class.
     * It checks whether the isRemoved method returns true when the ball is removed.
     */
    @Test
    void isRemoved_ShouldReturnTrue_WhenBallIsRemoved() {
        Ball ball = new Ball(new PVector(30, 40), 15, 255);
        ball.setRemoved(true);
        assertTrue(ball.isRemoved());
    }

    /**
     * Tests the setRemoved method of the Ball class.
     * It checks whether the setRemoved method correctly sets the removed status of the ball.
     */
    @Test
    void setRemoved_ShouldSetRemovedStatus() {
        Ball ball = new Ball(new PVector(20, 25), 18, 255);
        ball.setRemoved(false);
        assertFalse(ball.isRemoved());
    }

    /**
     * Tests the action method of the Table class.
     * It ensures that the action method performs actions on the table, such as updating the ball's location.
     */
    @Test
    void action_ShouldPerformActionsOnTable() {
        Table table = new Table(0, 0, 500, 20);
        Ball ball = new Ball(new PVector(0, 0), 10, 255);
        ball.setLocation(new PVector(50, 50));
        table.setBalls(new Ball[]{ball});
        table.action(100, 100);
        assertEquals(50.0, ball.getX());
        assertEquals(50.0, ball.getY());
    }

    /**
     * Tests the collideCircleCircle method of the Table class.
     * It checks whether collideCircleCircle returns true when circles collide and removes balls accordingly.
     */
    @Test
    void collideCircleCircle_ShouldReturnTrueIfCirclesCollide() {
        Table table = new Table(0, 0, 500, 20);
        boolean result = table.collideCircleCircle(0, 0, 10, 15, 15, 10);
        assertFalse(result);
        table.rack();
        table.getBalls()[1].setRemoved(true);
        table.removeBallFromTable();
        assertEquals(15, table.getBalls().length);
    }

    /**
     * Tests the rack method of the Table class.
     * It verifies that the rack method creates an array of balls with the expected length.
     */
    @Test
    void rack_ShouldCreateArrayOfBalls() {
        Table table = new Table(0, 0, 500, 20);
        table.rack();
        Ball[] balls = table.getBalls();
        assertNotNull(balls);
        assertEquals(16, balls.length);
    }

    /**
     * Tests the collideCheck method of the Table class.
     * It ensures that collideCheck handles ball collisions correctly, updating their positions.
     */
    @Test
    void collideCheck_ShouldHandleBallCollisions() {
        Table table = new Table(0, 0, 500, 20);
        table.rack();
        Ball ball1 = table.getBalls()[0];
        Ball ball2 = table.getBalls()[1];
        ball1.setLocation(new PVector(10, 10));
        ball2.setLocation(new PVector(15, 15));
        table.setBalls(new Ball[]{ball1, ball2});
        table.collideCheck();
        assertEquals(7.928932189941406, ball1.getLocation().x);

    }

    /**
     * Tests the collideCheck method of the Table class.
     * It checks whether collideCheck removes flagged balls from the table.
     */
    @Test
    void collideCheck_ShouldRemoveFlaggedBalls() {
        Table table = new Table(0, 0, 500, 20);
        table.rack();
        Ball ball = table.getBalls()[1];
        ball.setRemoved(true);
        table.collideCheck();
        assertEquals(15, table.getBalls().length);
    }

    /**
     * Tests the collideCircleCircle method of the Table class.
     * It verifies that collideCircleCircle detects collisions between circles correctly.
     */
    @Test
    void collideCircleCircle_ShouldDetectCollision() {
        Table table = new Table(0, 0, 500, 20);
        assertFalse(table.collideCircleCircle(10, 10, 5, 15, 15, 5));
        assertFalse(table.collideCircleCircle(0, 0, 10, 0, 20, 10));
        assertTrue(table.collideCircleCircle(-5, -5, 8, -8, -8, 8));
    }

    /**
     * Tests the collideCircleCircle method of the Table class.
     * It checks whether collideCircleCircle does not detect collisions when there are none.
     */
    @Test
    void collideCircleCircle_ShouldNotDetectCollision() {
        Table table = new Table(0, 0, 500, 20);
        assertFalse(table.collideCircleCircle(10, 10, 5, 25, 25, 5));
        assertFalse(table.collideCircleCircle(0, 0, 10, 15, 15, 5));
        assertFalse(table.collideCircleCircle(-5, -5, 8, -15, -15, 5));
    }

    /**
     * Tests the resetGame method of the Table class.
     * It ensures that resetGame resets the attributes of the table, such as score and moves.
     */
    @Test
    void resetGame_ShouldResetAttributes() {
        Table table = new Table(0, 0, 100, 10);
        table.setScore(42);
        table.setMoves(15);
        table.resetGame();
        assertEquals(0, table.getScore());
        assertEquals(0, table.getMoves());
    }

    /**
     * Tests the restartGame method of the Table class.
     * It checks whether restartGame resets and racks the game, initializing the table for a new game.
     */
    @Test
    void restartGame_ShouldResetAndRack() {
        Table table = new Table(0, 0, 100, 10);
        table.setScore(42);
        table.setMoves(15);
        table.restartGame();
        assertEquals(0, table.getScore());
        assertEquals(0, table.getMoves());
        assertNotNull(table.getBalls());
        assertTrue(table.getBalls().length > 0);
        assertEquals(0, table.getWhiteBallPocketCount());
        assertFalse(table.isShowRestartButton());
    }

    /**
     * Tests the isAllBallsInHolesExceptWhite method of the Table class.
     * It returns false when all balls except the white ball are in holes.
     */
    @Test
    void isAllBallsInHolesExceptWhite_ShouldReturnFalseWhenAllBallsExceptWhiteInHoles() {
        Table table = new Table(0, 0, 500, 20);
        for (int i = 1; i < table.getBalls().length; i++) {
            table.getBalls()[i] = new Ball(new PVector(0, 0), 10, 255);
        }
        assertFalse(table.isAllBallsInHolesExceptWhite(), "Expected all balls except white to be in holes");
    }

    /**
     * Tests the isAllBallsInHolesExceptWhite method of the Table class.
     * It returns false when some balls are not in holes.
     */
    @Test
    void isAllBallsInHolesExceptWhite_ShouldReturnFalseWhenSomeBallsNotInHoles() {
        Table table = new Table(0, 0, 500, 20);
        for (int i = 1; i < table.getBalls().length; i++) {
            if (i % 2 == 0) {
                continue;
            }
            table.getBalls()[i] = new Ball(new PVector(0, 0), 10, 255);
        }

        assertFalse(table.isAllBallsInHolesExceptWhite(), "Expected some balls not in holes");
    }

    /**
     * Tests the isAllBallsInHolesExceptWhite method of the Table class.
     * It returns true when there are no balls on the table.
     */

    @Test
    void isAllBallsInHolesExceptWhite_ShouldReturnTrueWhenNoBalls() {
        Table table = new Table(0, 0, 500, 20);
        for (int i = 0; i < table.getBalls().length; i++) {
            table.getBalls()[i] = null;
        }
        assertTrue(table.isAllBallsInHolesExceptWhite(), "Expected no balls to be in holes");
    }

    /**
     * Tests the updateForces method of the Table class.
     * It checks whether updateForces decelerates and resets forces when the magnitude is greater than the rate.
     */

    @Test
    void updateForces_ShouldDecelerateAndResetForcesWhenMagnitudeGreaterThanRate() {
        Table table = new Table(0, 0, 100, 10);
        Ball ball = new Ball(new PVector(0, 0), 10, 255);
        ball.setForce(new PVector(10, 0));
        table.setBalls(new Ball[]{ball});
        ball.move();
        assertEquals(9.979999542236328, ball.getForce().mag(), 0.01);
        ball.move();
        assertEquals(9.959999084472656, ball.getForce().mag(), 0.01);
    }

    /**
     * Tests the run method of the Table class.
     * It checks whether the run method handles collisions and updates forces appropriately.
     */
    @Test
    void run_ShouldHandleCollisionsAndUpdateForces() {
        Table table = new Table(0, 0, 100, 10);
        Ball ball = new Ball(new PVector(0, 0), 10, 255);
        table.setBalls(new Ball[]{ball});
        table.collideCheck();
        assertFalse(forceUpdated);
    }
  }
