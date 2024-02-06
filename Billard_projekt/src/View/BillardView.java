package View;

import Controller.GameState;
import Controller.Interface_controller;
import Model.Ball;
import Model.Hole;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * The BillardView class represents the graphical view for the billiard game.
 * It extends the PApplet class and implements the Interface_view interface.
 * It handles the rendering of different game states, button clicks, and the display of the billiard table.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */
public class BillardView extends PApplet implements Interface_view {

    /**
     * Controller for handling interactions between the model and view
     */
    private Interface_controller controller;

    /**
     * Variables for ball color and background color
     */
    private int color;

    /**
     * Flag to track if the restart button is clicked
     */
    private boolean restartButtonClicked;

    /**
     * Flag to show or hide game rules
     */
    private boolean showRules = false;

    /**
     * Flag to control ball movement
     */
    protected boolean canMove = true;


    /**
     * Constructor for the BillardView class.
     *
     * @param width  Width of the PApplet canvas
     * @param height Height of the PApplet canvas
     */
    public BillardView(int width, int height) {
        setSize(width, height);
    }

    /**
     * Setter method to set the controller for the view.
     *
     * @param controller The interface controller to set
     */
    public void setController(Interface_controller controller) {
        this.controller = controller;

    }

    /**
     * Setup method called once at the beginning of the PApplet.
     */
    public void setup() {

    }

    /**
     * Draw method called continuously to render frames.
     */
    public void draw() {
        controller.nextFrame();
    }

    /**
     * MouseReleased method called when the mouse button is released.
     */
    public void mouseReleased() {
        handleMouseReleased();
        controller.handleMouseClicksByGameState();
    }

    /**
     * Method to handle clicks in the START game state.
     */
    public void handleStartStateClicks() {
        if (checkButtonClick("Play Game", width/2, height/2, 150, 50)) {
            System.out.println("Play Game button clicked!");
            System.out.println("Changing state to PLAYING");
            controller.setState(GameState.PLAYING);
            controller.getModel().restartGame();
        } else if (checkButtonClick("Help", width / 2, height / 2 + 100, 100, 50)) {
            controller.setState(GameState.HELP);
        }
    }

    /**
     * Method to handle clicks in the PLAYING game state.
     */
    public void handlePlayingStateClicks() {
        if (checkButtonClick("Restart", width/2 - 50, height/2 + 50, 100, 50)) {
            controller.getModel().restartGame();
        }
    }

    /**
     * Method to handle clicks in the HELP game state.
     */
    public void handleHelpStateClicks() {
        if (checkButtonClick("Zuruck", width/2, height - 50, 100, 50)) {
            controller.setState(GameState.START);
            controller.getModel().restartGame();
        }
    }

    /**
     * Method to handle clicks in the GAME WIN game state.
     */
    public void handleGameWinStateClicks() {
        if (checkButtonClick("Restart", width/2 - 50, height/2 + 50, 100, 50)) {
            System.out.println("Restart button clicked!");
            controller.setState(GameState.PLAYING);
            controller.getModel().restartGame();
        }
    }


    /**
     * Method to display the billiard table and game-related information.
     */
    public void TableDisplay() {

        fill(200, 150, 100);
        rect(controller.getModel().getMyX() - controller.getModel().getHoleSize() / 2, controller.getModel().getMyY() - controller.getModel().getHoleSize() / 2, controller.getModel().getMyW() + controller.getModel().getHoleSize(), controller.getModel().getMyH() + controller.getModel().getHoleSize());

        fill(40, 150, 100);
        rect(controller.getModel().getMyX(), controller.getModel().getMyY(), controller.getModel().getMyW(), controller.getModel().getMyH());

        push();
        fill(255);
        textSize(60);
        noStroke();
        textAlign(CENTER, CENTER);

        text(controller.getModel().getScore(), width / 2 - 400, height / 2 - 180);
        text(controller.getModel().getMoves(), width / 2 - 300, height / 2 - 180);

        textSize(20);
        fill(200);

        text("pockets", width / 2 - 400, height / 2 - 220);
        text("shots", width / 2 - 300, height / 2 - 220);

        fill(255); // Couleur rouge
        textSize(32);
         text("Heart  " + controller.getModel().getRemainingShots(), width - 120, height/2 -220 );
        pop();

        for (Hole hole : controller.getModel().getHoles()) {
            HoleDisplay(hole.getX(), hole.getY(), hole.getHoleSize(), hole.getHoleSize());
        }
        line(controller.getModel().getMyX() + controller.getModel().getMyW() / 4, controller.getModel().getMyY(),
                controller.getModel().getMyX() + controller.getModel().getMyW() / 4, controller.getModel().getMyY() + controller.getModel().getMyH());
        for (Ball ball : controller.getModel().getBalls()) {
            color = ball.getC();
            BallDisplay(ball.getX(), ball.getY(), ball.getSize(), ball.getSize());
        }

    }

    /**
     * Method to display a billiard ball.
     *
     * @param x X-coordinate of the ball
     * @param y Y-coordinate of the ball
     * @param w Width of the ball
     * @param h Height of the ball
     */
    public void BallDisplay(float x, float y, float w, float h) {
        filler(color);
        ellipse(x, y, w, h);
    }

    /**
     * Method to display a hole on the table.
     *
     * @param x X-coordinate of the hole
     * @param y Y-coordinate of the hole
     * @param w Width of the hole
     * @param h Height of the hole
     */
    public void HoleDisplay(float x, float y, float w, float h) {
        fill(color);
        ellipse(x, y, w - 10, h - 10);
    }

    /**
     * Method to fill a shape based on ball color.
     *
     * @param Color Color code for filling the shape
     */
    public void filler(int Color) {
        switch (Color) {
            case 0:
                fill(255);
                break;
            case 1:
                fill(150, 150, 0);
                break;
            case 2:
                fill(0, 0, 150);
                break;
            case 3:
                fill(150, 0, 0);
                break;
            case 4:
                fill(150, 0, 150);
                break;
            case 5:
                fill(250, 150, 0);
                break;
            case 6:
                fill(0, 150, 0);
                break;
            case 7:
                fill(150, 100, 50);
                break;
            case 8:
                fill(0);
                break;
            case 9:
                fill(250, 250, 50);
                break;
            case 10:
                fill(100, 100, 250);
                break;
            case 11:
                fill(250, 50, 50);
                break;
            case 12:
                fill(250, 50, 250);
                break;
            case 13:
                fill(255, 200, 50);
                break;
            case 14:
                fill(100, 250, 100);
                break;
            case 15:
                fill(200, 150, 100);
                break;
            default:
                fill(255, 0, 0);
        }
    }

    /**
     * Method to display an arrow indicating the shooting direction.
     *
     * @param x1 X-coordinate of the starting point
     * @param y1 Y-coordinate of the starting point
     * @param x2 X-coordinate of the ending point
     * @param y2 Y-coordinate of the ending point
     */
    public void arrow(float x1, float y1, float x2, float y2) {
        line(x1, y1, x2, y2);
        pushMatrix();
        translate(x2, y2);
        float angleRotation = atan2(x1 - x2, y2 - y1);
        rotate(angleRotation);
        line(0, 0, -10, -10);
        line(0, 0, 10, -10);
        popMatrix();
    }

    /**
     * Method to handle movement and display the shooting guide.
     */
    public void handleMovementAndGuide() {
        boolean canMove = true;
        for (int i = 0; i < controller.getModel().getBalls().length; i++) {
            if (controller.getModel().getBalls()[i].getForce().mag() != 0) {
                canMove = false;
            }
        }
        if (canMove) {
            push();
            colorMode(HSB);
            float dist = sqrt(pow((controller.getModel().getBalls()[0].getX() - mouseX), 2) +
                    pow((controller.getModel().getBalls()[0].getY() - mouseY), 2));
            float guideHue = map(dist, 0, 1040, 0, 360);
            int guideColor = color(guideHue, 100, 90);
            float guideWidth = map(dist, 0, 1040, 1, 7);
            colorMode(RGB);

            stroke(guideColor);
            strokeWeight(guideWidth);
            arrow(controller.getModel().getBalls()[0].getX(), controller.getModel().getBalls()[0].getY(), mouseX, mouseY);
            pop();

            resetCanMove();

        }
    }

    /**
     * Method called when the mouse is released to handle ball shooting.
     */
    public void handleMouseReleased() {
        System.out.println("Move release");
        System.out.println("CanMove: " + canMove);
        if (canMove) {
            PVector mover = new PVector(mouseX - controller.getModel().getBalls()[0].getX(), mouseY - controller.getModel().getBalls()[0].getY());
            controller.getModel().getBalls()[0].hit(mover.mag() / 25, mover.heading());
            canMove = false;
            controller.getModel().madeMove();
        }
    }

    /**
     * Method to reset the canMove flag.
     */
    public void resetCanMove() {
        canMove = true;
    }

    /**
     * Method to display the welcome page.
     */
    public void welcomePage() {
        background(198, 187, 123);
        fill(color);
        textSize(64);
        text("Willkommen zum Billardspiel !", width/2, height/2 - 170);
        drawButton("Play Game", width / 2, height / 2, 150, 50, color(96, 187, 230));
        drawButton("Help", width / 2, height / 2 + 100, 100, 50, color(127, 108, 122));
    }

    /**
     * Method to display the win game message.
     */
    public void winGameDraw() {
        background(198, 187, 123);
        fill(color);
        textSize(64);
        text("Congratulations YOU WON!", width/2, height/2 - 170);
        restartButtonGame();
    }

    /**
     * Method to display the game over message.
     */
    public void LostGamedraw(){
        background(198, 187, 123);
        fill(color);
        textSize(64);
        text("Game Over", width/2, height/2 - 170);

    }

    /**
     * Method to display the restart button after winning.
     */
    public void restartButtonGame(){
        float buttonWidth = width;
        float buttonHeight = height;
        drawButton("Restart", buttonWidth/2 -50, buttonHeight/2 +50, 100, 50, color(105, 173, 134));

        if (mouseButton == LEFT) {
            if (mouseX > buttonWidth/2 - 50 && mouseX < buttonWidth/2 + 50 &&
                    mouseY > buttonHeight/2 + 50 && mouseY < buttonHeight/2 + 100) {
                println("Restart button clicked!");
                restartButtonClicked = true;
                controller.setState(GameState.PLAYING);
                controller.getModel().restartGame();
            }
        }
    }

    /**
     * Method to draw a clickable button with a label.
     *
     * @param label        Label for the button
     * @param x            X-coordinate of the button
     * @param y            Y-coordinate of the button
     * @param buttonWidth  Width of the button
     * @param buttonHeight Height of the button
     * @param buttoColor   Color of the button
     */
    public void drawButton(String label, float x, float y, float buttonWidth, float buttonHeight, int buttoColor) {
        float buttonX = x - buttonWidth / 2;
        float buttonY = y - buttonHeight / 2;

            if (mouseX > buttonX && mouseX < buttonX + buttonWidth &&
                    mouseY > buttonY && mouseY < buttonY + buttonHeight) {
                fill(96, 187, 230);
            } else {
                fill(buttoColor);
            }

        rect(buttonX, buttonY, buttonWidth, buttonHeight);

        fill(255);
        textSize(16);
        textAlign(CENTER, CENTER);
        text(label, x, y);
    }

    /**
     * Method to check if a button is clicked based on its coordinates and dimensions.
     *
     * @param label        Label for the button
     * @param x            X-coordinate of the button
     * @param y            Y-coordinate of the button
     * @param buttonWidth  Width of the button
     * @param buttonHeight Height of the button
     * @return True if the button is clicked, false otherwise
     */
    public boolean checkButtonClick(String label, float x, float y, float buttonWidth, float buttonHeight) {
        float buttonX = x - buttonWidth / 2;
        float buttonY = y - buttonHeight / 2;

        if (mouseX > buttonX && mouseX < buttonX + buttonWidth &&
                mouseY > buttonY && mouseY < buttonY + buttonHeight) {
            if (label.equals("Play Game")) {
                controller.setState(GameState.PLAYING);
            } else if (label.equals("Help")) {
                controller.setState(GameState.HELP);
            } else if(label.equals("Restart")){

                controller.setState(GameState.PLAYING);
            } else if(label.equalsIgnoreCase("Zuruck")){
                controller.setState(GameState.START   );
            }
            return true;
        }
        return false;
    }

    /**
     * Method to display game instructions.
     */
    public void displayInstructions() {
        background(108, 144, 160);

        fill(255);
        textSize(25);
        textAlign(CENTER, CENTER);
        String instructions = "Willkommen zum Billardspiel !\n\n" +
                "Spielregeln:\n" +
                "- Benutzen Sie die Maus, um zu zielen und klicken Sie, um zu schießen!\n" +
                "- der weiße Ball darf nicht mehr als fünfmal in die Löcher eindringen!\n" +
                "- Sie nicht mehr als 20 Ziehungen durchführen mussten ! \n" +
                "- alle anderen Bälle in die Löcher stecken!\n" +
                "- Klicken Sie auf die Schaltfläche 'Restart', um das Spiel neu zu starten.";
        text(instructions, width/2, height/2);
        drawButton("Zuruck", width/2, height - 50, 100, 50, color(224, 207, 134));
    }
}
