package Controller;

import Model.BillardThread;
import Model.Table;
import View.Interface_view;

/**
 * The BillardController class is responsible for controlling the flow of the billiard game,
 * managing the interactions between the model (Table) and the view (Interface_view).
 * It uses a BillardThread for concurrent processing of the game.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */
public class BillardController implements Interface_controller {

    /**
     * The billiard table model
     */
    private Table myTable;

    /**
     * The view interface for displaying the game
     */
    private Interface_view view;


    /**
     * The thread responsible for concurrent processing of the game
     */
    private BillardThread billardThread;

    /**
     * The current state of the game
     */
    private GameState state;

    /**
     * Constructor for the BillardController class.
     * Initializes the game state to START.
     */
    public BillardController() {
        this.state = GameState.START;
    }

    /**
     * Set the model (billiard table) for the controller.
     * Initialize the table, start the BillardThread for concurrent processing.
     *
     * @param myTable : The billiard table model
     */
    public void setModel(Table myTable) {
        this.myTable = myTable;
        myTable.rack();
        billardThread = new BillardThread(myTable);
        billardThread.startThreads();
    }

    /**
     * Set the view for the controller.
     *
     * @param view : The view interface
     */
    public void setView(Interface_view view) {
        this.view = view;
    }

    /**
     * Get the current state of the game.
     *
     * @return : The current game state
     */
    public GameState getState() {
        return state;
    }

    /**
     * Set the current state of the game.
     *
     * @param state : The new game state
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * Get the model (billiard table) associated with the controller.
     *
     * @return : The billiard table model
     */
    public Table getModel() {
        return myTable;
    }

    /**
     * Advance to the next frame of the game based on the current state.
     */
    public void nextFrame() {
        switch (state) {
            case START -> {
                view.welcomePage();
                break;
            }
            case PLAYING -> {
                view.TableDisplay();
                view.handleMovementAndGuide();
                myTable.action(1041, 541);
                if (myTable.isAllBallsInHolesExceptWhite()) {
                    state = GameState.GAME_WIN;
                } else if (myTable.isGameOver()) {
                    state = GameState.GAME_OVER;
                }
                break;
            }
            case GAME_WIN -> {
                view.winGameDraw();
                billardThread.stopThreads();
                break;
            }
            case GAME_OVER -> {
                view.LostGamedraw();
                billardThread.stopThreads();
                break;
            }
            case HELP -> {
                view.displayInstructions();
                break;
            }
        }
    }

    /**
     * Handles mouse clicks based on the current game state.
     * Delegates the handling of mouse clicks to specific methods in the view
     * corresponding to each game state.
     */
    public void handleMouseClicksByGameState(){
        switch (state){
            case START -> {
                view.handleStartStateClicks();
                break;
            }
            case PLAYING -> {
                view.handlePlayingStateClicks();
                break;
            }
            case HELP -> {
                view.handleHelpStateClicks();
                break;
            }
            case GAME_WIN -> {
                view.handleGameWinStateClicks();
                break;
            }
        }
    }
}
