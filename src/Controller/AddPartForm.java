package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Add part form.
 */
public class AddPartForm implements Initializable {
    /**
     * The In house rb.
     */
    public RadioButton inHouseRB;
    /**
     * The Part toggle group.
     */
    public ToggleGroup partToggleGroup;
    /**
     * The Outsourced rb.
     */
    public RadioButton outsourcedRB;
    /**
     * The Part type label.
     */
    public Label partTypeLabel;
    /**
     * The Min text field.
     */
    public TextField MinTextField;
    /**
     * The Name text field.
     */
    public TextField nameTextField;
    /**
     * The Inventory text field.
     */
    public TextField inventoryTextField;
    /**
     * The Price text field.
     */
    public TextField priceTextField;
    /**
     * The Max text field.
     */
    public TextField MaxTextField;
    /**
     * The Part type text field.
     */
    public TextField partTypeTextField;
    /**
     * The Save part button.
     */
    public Button savePartButton;
    /**
     * The Cancel button.
     */
    public Button cancelButton;
    /**
     * The constant currentId.
     */
    public static int currentId = 3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * On in house clicked.
     *
     * @param actionEvent the action event
     */
    public void onInHouseClicked(ActionEvent actionEvent) {
        partTypeLabel.setText("Machine ID");
    }

    /**
     * On outsourced clicked.
     *
     * @param actionEvent the action event
     */
    public void onOutsourcedClicked(ActionEvent actionEvent) {
        partTypeLabel.setText("Company Name");
    }

    /**
     * On save part.
     *
     * @param actionEvent the action event
     */
    public void onSavePart(ActionEvent actionEvent) {
        try {
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            int stock = Integer.parseInt(inventoryTextField.getText());
            int max = Integer.parseInt(MaxTextField.getText());
            int min = Integer.parseInt(MinTextField.getText());
            int machineId = Integer.parseInt(partTypeTextField.getText());
            String companyName = partTypeTextField.getText();

            if(min <= stock && stock <= max){
                if(inHouseRB.isSelected()) {
                    InHouse part = new InHouse(currentId++, name, price, stock, max, min, machineId);
                    Inventory.addPart(part);
                }
                else if (outsourcedRB.isSelected()){
                    Outsourced part = new Outsourced(currentId++, name, price, stock, max, min, companyName);
                    Inventory.addPart(part);
                }

                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 1200);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect input");
                alert.setContentText("Minimum inventory must be less than or equal to maximum inventory. Inventory level must be in between min and max.");
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            warning();
        }

    }

    private void warning(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect input");
        alert.setContentText("Input provided does not meet requirements.");
        alert.showAndWait();
    }

    /**
     * On cancel.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
