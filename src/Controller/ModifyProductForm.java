package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
 * The type Modify product form.
 */
public class ModifyProductForm implements Initializable {
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
    public TableColumn associatedPartIdCol;
    /**
     * The Associated part name col.
     */
    public TableColumn associatedPartNameCol;
    /**
     * The Associated part stock col.
     */
    public TableColumn associatedPartStockCol;
    /**
     * The Associated part price col.
     */
    public TableColumn associatedPartPriceCol;
    /**
     * The Id text field.
     */
    public TextField idTextField;
    private ObservableList<Part> partsToAssociate = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Product product = MainMenu.productToModify;
        partsToAssociate = product.getAllAssociatedParts();

        idTextField.setText(String.valueOf(product.getId()));
        MinTextField.setText(String.valueOf(product.getMin()));
        nameTextField.setText(product.getName());
        inventoryTextField.setText(String.valueOf(product.getStock()));
        priceTextField.setText(String.valueOf(product.getPrice()));
        MaxTextField.setText(String.valueOf(product.getMax()));

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(product.getAllAssociatedParts());
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

                Product product = MainMenu.productToModify;
                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);
                product.setMax(max);
                product.setMin(min);

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
     *
     * RUNTIME ERROR: I was experiencing an error when trying to associate parts. This came because I didn't instantiate
     * Observable list of Parts as a FXCollections.ObservableList() and this made me debug through the code to realize that
     * there actually wasn't anything being added to the list because the list wasn't actually there even though i declared it.
     *
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
            MainMenu.productToModify.addAssociatedPart(part);
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
            MainMenu.productToModify.deleteAssociatedPart(part);
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
