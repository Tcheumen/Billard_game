package Model;

import processing.core.PVector;


/**
 * The BillardThread class represents a thread for handling collisions and updating the state
 * of the billiard game concurrently.
 *
 * Extends: Thread
 *
 * Methods:
 * - run(): The main execution logic of the thread.
 * - startThreads(): Starts the collisionThread.
 * - stopThreads(): Stops the collisionThread by interrupting it.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */
public class BillardThread  extends Thread  {

    /**
     * The billiard table model
     */
    private Table table;

    /**
     * The thread for handling collisions
     */
    private Thread collisionThread;

    /**
     * Flag indicating whether the thread is running
     */
    private boolean running;

    /**
     * Constructor for the BillardThread class.
     * Initializes the table, collisionThread, and sets the running flag to true.
     *
     * @param table : The billiard table model
     */
    public BillardThread(Table table) {
        this.table = table;
        this.collisionThread = new Thread(this::run);
        this.running = true;
    }

    /**
     * The main execution logic of the thread.
     * Handles collisions, updates ball forces, and introduces a sleep interval.
     */
    public void run() {
        while (running) {
            try {
                table.collideCheck();

                for (Ball ball : table.getBalls()) {
                    float decelerationRate = 0.01f;
                    if (ball.getForce().mag() > decelerationRate) {
                        ball.getForce().setMag(ball.getForce().mag() - decelerationRate);
                    } else {
                        ball.setForce(new PVector());
                    }
                }

                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    /**
     * Starts the collisionThread.
     */
    public void startThreads() {
        collisionThread.start();
    }


    /**
     * Stops the collisionThread by interrupting it.
     */
    public void stopThreads() {
        collisionThread.interrupt();
    }
}
