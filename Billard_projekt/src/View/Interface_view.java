package View;

/**
 * The Interface_view interface provides a contract for classes that act as views in the
 * billiard game application. Views are responsible for displaying the game to the user.
 *
 * Methods:
 * - TableDisplay(): Displays the billiard table.
 * - handleMovementAndGuide(): Handles user input for movement and provides guidance.
 * - welcomePage(): Displays a welcome page or initial screen.
 * - winGameDraw(): Displays the draw for winning the game.
 * - LostGamedraw(): Displays the draw for losing the game.
 * - displayInstructions(): Displays instructions or help information.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */
public interface Interface_view {

    /**
     * Displays the billiard table.
     */
    void TableDisplay();

    /**
     * Handles user input for movement and provides guidance.
     */
    void handleMovementAndGuide();

    /**
     * Displays a welcome page or initial screen.
     */
    void welcomePage();

    /**
     * Displays the draw for winning the game.
     */
    void winGameDraw();

    /**
     * Displays the draw for losing the game.
     */
    void LostGamedraw();

    /**
     * Displays instructions or help information.
     */
    void displayInstructions();

    /**
     * Handles clicks in the START game state.
     */
    void  handleStartStateClicks();

    /**
     * Handles clicks in the PLAYING game state.
     */
    void handlePlayingStateClicks();

    /**
     * Handles clicks in the HELP game state.
     */
    void handleHelpStateClicks();

    /**
     * Handles clicks in the GAME WIN game state.
     */
    void handleGameWinStateClicks();


}
