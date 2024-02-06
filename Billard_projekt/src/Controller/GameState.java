package Controller;

/**
 * The GameState enum represents the possible states of the billiard game.
 * These states define the different phases or conditions the game can be in.
 *
 * Enum Values:
 * - START: Initial state when the game is just starting.
 * - SETTINGS: State for game settings or configuration (not used in the provided code).
 * - PLAYING: State during regular gameplay.
 * - GAME_OVER: State when the game is over due to a losing condition.
 * - GAME_WIN: State when the game is won.
 * - HELP: State when displaying instructions or help information.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */
public enum GameState {

    /**
     *  Initial state when the game is just starting.
     */
    START,

    /**
     * State during regular gameplay.
     */
    PLAYING,
    /**
     * State when the game is over due to a losing condition.
     */
    GAME_OVER,

    /**
     * State when the game is won.
     */
    GAME_WIN,

    /**
     * State when displaying instructions or help information.
     */
    HELP
}
