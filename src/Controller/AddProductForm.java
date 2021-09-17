package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductForm implements Initializable {
    public TextField MinTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField MaxTextField;
    public Button saveProductButton;
    public Button cancelButton;
    public TableView<Part> partTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partPriceCol;
    public Button addPart;
    public Button modifyPart;
    public TextField partTextField;
    public TableView<Part> associatedPartTable;
    public TableColumn partIdCol1;
    public TableColumn partNameCol1;
    public TableColumn partInventoryLevelCol1;
    public TableColumn partPriceCol1;
    public TableColumn associatedPartIdCol;
    public TableColumn associatedPartNameCol;
    public TableColumn associatedPartStockCol;
    public TableColumn associatedPartPriceCol;
    public ObservableList<Part> partsToAssociate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    public void onSaveProduct(ActionEvent actionEvent) {
        try {
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            int stock = Integer.parseInt(inventoryTextField.getText());
            int max = Integer.parseInt(MaxTextField.getText());
            int min = Integer.parseInt(MinTextField.getText());

            if(min <= stock && stock <= max){

                Product product = new Product(Product.currentId++, name, price, stock, min, max);
                Inventory.addProduct(product);

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

    public void onAssociatePart(ActionEvent actionEvent) {
        Part part = partTable.getSelectionModel().getSelectedItem();

        if(part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part found");
            alert.setContentText("No part was selected.");
            alert.showAndWait();
        }
        else {
            partsToAssociate.add(part);
            associatedPartTable.setItems(partsToAssociate);
        }
    }

    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part part = associatedPartTable.getSelectionModel().getSelectedItem();

        if(part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part found");
            alert.setContentText("No part was selected to be removed.");
            alert.showAndWait();
        }
        else {
            partsToAssociate.remove(part);
        }
    }

    public void onActionSearchParts(ActionEvent actionEvent) {
        String query = partTextField.getText();
        ObservableList<Part> parts = searchParts((query));

        if(parts.size() == 0) {
            try {
                int id = Integer.parseInt(query);
                Part part = searchByPartId(id);
                if(part != null) {
                    parts.add(part);
                }
            }
            catch (NumberFormatException e){
                // ignore exception
            }
        }

        partTable.setItems(parts);
        partTextField.setText("");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No matches found");
        alert.setContentText("There were no parts matching your input.");
        alert.showAndWait();
    }

    private Part searchByPartId (int id) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part part: allParts) {
            if (part.getId() == id){
                return part;
            }
        }
        return null;
    }

    private ObservableList<Part> searchParts (String partialName) {
        ObservableList<Part> matches = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        String partialNameLower = partialName.toLowerCase();
        for(Part part : allParts) {
            if(part.getName().toLowerCase().contains(partialNameLower)) {
                matches.add(part);
            }
        }

        return matches;
    }

    private void warning(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect input");
        alert.setContentText("Input provided does not meet requirements.");
        alert.showAndWait();
    }
}
