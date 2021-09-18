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

/**
 * The type Add product form.
 */
public class AddProductForm implements Initializable {
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
     * The Save product button.
     */
    public Button saveProductButton;
    /**
     * The Cancel button.
     */
    public Button cancelButton;
    /**
     * The Part table.
     */
    public TableView<Part> partTable;
    /**
     * The Part id col.
     */
    public TableColumn partIdCol;
    /**
     * The Part name col.
     */
    public TableColumn partNameCol;
    /**
     * The Part inventory level col.
     */
    public TableColumn partInventoryLevelCol;
    /**
     * The Part price col.
     */
    public TableColumn partPriceCol;
    /**
     * The Add part.
     */
    public Button addPart;
    /**
     * The Modify part.
     */
    public Button modifyPart;
    /**
     * The Part text field.
     */
    public TextField partTextField;
    /**
     * The Associated part table.
     */
    public TableView<Part> associatedPartTable;
    /**
     * The Associated part id col.
     */
    public TableColumn<Part, Integer> associatedPartIdCol;
    /**
     * The Associated part name col.
     */
    public TableColumn<Part, String> associatedPartNameCol;
    /**
     * The Associated part stock col.
     */
    public TableColumn<Part, Integer> associatedPartStockCol;
    /**
     * The Associated part price col.
     */
    public TableColumn<Part, Double> associatedPartPriceCol;
    private final ObservableList<Part> partsToAssociate = FXCollections.observableArrayList();

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

    /**
     * On save product.
     *
     * @param actionEvent the action event
     */
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

                product.getAllAssociatedParts().addAll(partsToAssociate);

                Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 800);
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

    /**
     * On associate part.
     *
     * @param actionEvent the action event
     */
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

    /**
     * On remove associated part.
     *
     * @param actionEvent the action event
     */
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
            associatedPartTable.setItems(partsToAssociate);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Removal");
            alert.setContentText("The selected associated part has been removed.");
            alert.showAndWait();
        }
    }

    /**
     * On action search parts.
     *
     * @param actionEvent the action event
     */
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

        if(parts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No matches found");
            alert.setContentText("There were no parts matching your input.");
            alert.showAndWait();
        }
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
