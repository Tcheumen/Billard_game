import Controller.BillardController;
import Model.Table;
import View.BillardView;
import processing.core.PApplet;

/**
 * The Main class serves as the entry point for the billiard game application.
 * It initializes the model (Table), controller (BillardController), and view (BillardView).
 * It then connects the model, view, and controller and starts the Processing sketch.
 *
 * @author Tcheumen Nanseu Lionel
 * @version 1.0
 */

public final class Main {

    /**
     * The main method, the entry point for the billiard game application.
     *
     * @param args : Command-line arguments (not used in this example)
     */
    public static void main(String[] args){

        /**
         * Create a new instance of the billiard table model
         */
        var model = new Table(25.0f, 25.0f, 1000.0f, 70.0f);

        /**
         * Create a new instance of the billiard controller
         */
        var controller = new BillardController();

        /**
         * Create a new instance of the billiard view
         */
        var view = new BillardView(1041, 541);



       /**
         * Connect the model, view, and controller
        */
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);

        /**
         * Start the Processing sketch with the specified name and view
         */
        PApplet.runSketch(new String[]{"BillardView"}, view);

    }
}
