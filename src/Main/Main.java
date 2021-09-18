package Main;

import Model.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class extends the application class. It is used to hold the main method and start the initial screen.
 * <p>
 * FUTURE ENHANCEMENT: When a part gets associated with a product, the amount of that part used for the product should be
 * depleted from the total inventory of that product. Then a warning should be passed when that part is depleted to its
 * minimum level.
 */
public class Main extends Application {

    /**
     * The start method creates the first window and calls the add test data method.
     * @param primaryStage
     * */
    @Override
    public void start(Stage primaryStage) throws Exception {

        addTestData();

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 1400, 700));
        primaryStage.show();
    }

    /**
     *
     */
    private static void addTestData(){
        Part[] parts = new Part[] {
                new InHouse(1, "Lens", 5.00, 20, 5, 100, 1),
                new InHouse(2, "Frame", 10.00, 10, 1, 100, 2),
                new InHouse(3, "Wood Pole", 7.50, 20, 2, 100, 5),
                new Outsourced(4, "Rubber Cap", 1.00, 20, 2, 100, "Fred's Supply and Stuff"),
                new Outsourced(5, "Diamond Bling Bling", 100.00, 20, 2, 100, "Fred's Supply and Stuff")
        };

        for(Part part : parts) {
            Inventory.addPart(part);
        }


        Product[] products = new Product[] {
                new Product(1, "Monocle", 55.00, 14, 3, 80),
                new Product(2, "Bling Bling Walking Cane", 505.00, 4, 2, 50)
        };

        for(Product product: products) {
            Inventory.addProduct(product);
        }
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void Main(String[] args) {
        launch(args);
    }
}
