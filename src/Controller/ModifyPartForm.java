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

public class ModifyPartForm implements Initializable {
    public RadioButton inHouseRB;
    public Label partTypeLabel;
    public TextField MinTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField MaxTextField;
    public TextField partTypeTextField;
    public Button savePartButton;
    public Button cancelButton;
    public RadioButton outsourcedRB;
    public ToggleGroup modPartToggleGroup;
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

    public void onInHouseClicked(ActionEvent actionEvent) {
        partTypeLabel.setText("Machine ID");
    }

    public void onOutsourcedClicked(ActionEvent actionEvent) {
        partTypeLabel.setText("Company Name");
    }

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
                    InHouse part = new InHouse(Part.currentId++, name, price, stock, max, min, machineId);
                    Inventory.addPart(part);
                }
                else if (outsourcedRB.isSelected()){
                    Outsourced part = new Outsourced(Part.currentId++, name, price, stock, max, min, companyName);
                    Inventory.addPart(part);
                }

                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 800);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e) {
            warning();
        }
    }

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
