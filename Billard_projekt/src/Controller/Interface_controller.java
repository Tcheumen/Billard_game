package Controller;

import Model.Table;

/**
 * The Interface_controller interface provides a contract for classes that act as controllers
 * in the billiard game application. Controllers manage the interaction between the model (Table)
 * and the view, as well as control the flow of the game.
 *
 * Methods:
 * - nextFrame(): Advances to the next frame or phase of the game.
 * - getModel(): Gets the model (billiard table) associated with the controller.
 * - getState(): Gets the current state of the game.
 * - setState(GameState state): Sets the current state of the game.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */
public interface Interface_controller {

    /**
     * Advances to the next frame or phase of the game.
     */
    void nextFrame();

    /**
     * Gets the model (billiard table) associated with the controller.
     *
     * @return The billiard table model
     */
    Table getModel();

    /**
     * Gets the current state of the game.
     *
     * @return The current game state
     */
    GameState getState();

    /**
     * Sets the current state of the game.
     *
     * @param state The new game state
     */
    void setState(GameState state);

    /**
     * Handles mouse clicks based on the current game state.
     */
    void handleMouseClicksByGameState();
}
