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
 * The type Modify part form.
 */
public class ModifyPartForm implements Initializable {
    /**
     * The In house rb.
     */
    public RadioButton inHouseRB;
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
     * The Outsourced rb.
     */
    public RadioButton outsourcedRB;
    /**
     * The Mod part toggle group.
     */
    public ToggleGroup modPartToggleGroup;
    /**
     * The Id text field.
     */
    public TextField idTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part part = MainMenu.partToModify;

        idTextField.setText(String.valueOf(part.getId()));
        MinTextField.setText(String.valueOf(part.getMin()));
        nameTextField.setText(part.getName());
        inventoryTextField.setText(String.valueOf(part.getStock()));
        priceTextField.setText(String.valueOf(part.getPrice()));
        MaxTextField.setText(String.valueOf(part.getMax()));

        if(part instanceof InHouse) {
            inHouseRB.setSelected(true);
            partTypeLabel.setText("Machine ID");
            partTypeTextField.setText(String.valueOf(((InHouse) part).getMachineId()));

        }
        else{
            outsourcedRB.setSelected(true);
            partTypeLabel.setText("Company Name");
            partTypeTextField.setText(((Outsourced) part).getCompanyName());
        }
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
            Part part = MainMenu.partToModify;

            int idx = Inventory.getAllParts().indexOf(part);
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            int stock = Integer.parseInt(inventoryTextField.getText());
            int max = Integer.parseInt(MaxTextField.getText());
            int min = Integer.parseInt(MinTextField.getText());

            if(min <= stock && stock <= max){
                if(inHouseRB.isSelected()) {
                    int machineId = Integer.parseInt(partTypeTextField.getText());
                    Part newPart = new InHouse(part.getId(), name, price, stock, min, max, machineId);
                    Inventory.updatePart(idx, newPart);
                }
                else if (outsourcedRB.isSelected()){
                    String companyName = partTypeTextField.getText();
                    Part newPart = new Outsourced(part.getId(), name, price, stock, min, max, companyName);
                    Inventory.updatePart(idx, newPart);
                }

                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1400, 1200);
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

    private void warning(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect input");
        alert.setContentText("Input provided does not meet requirements.");
        alert.showAndWait();
    }
}
