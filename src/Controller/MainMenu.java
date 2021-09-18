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
 * The type Main menu.
 */
public class MainMenu implements Initializable {

    /**
     * The Part id col.
     */
    public TableColumn<Object, Object> partIdCol;
    /**
     * The Part name col.
     */
    public TableColumn<Object, Object> partNameCol;
    /**
     * The Part inventory level col.
     */
    public TableColumn<Object, Object> partInventoryLevelCol;
    /**
     * The Part price col.
     */
    public TableColumn<Object, Object> partPriceCol;
    /**
     * The Product id col.
     */
    public TableColumn<Object, Object> productIdCol;
    /**
     * The Product name col.
     */
    public TableColumn<Object, Object> productNameCol;
    /**
     * The Product inventory level col.
     */
    public TableColumn<Object, Object> productInventoryLevelCol;
    /**
     * The Product price col.
     */
    public TableColumn<Object, Object> productPriceCol;
    /**
     * The Add part.
     */
    public Button addPart;
    /**
     * The Modify part.
     */
    public Button modifyPart;
    /**
     * The Delete part.
     */
    public Button deletePart;
    /**
     * The Add product.
     */
    public Button addProduct;
    /**
     * The Modify product.
     */
    public Button modifyProduct;
    /**
     * The Delete product.
     */
    public Button deleteProduct;
    /**
     * The Exit.
     */
    public Button exit;
    /**
     * The Part table.
     */
    public TableView<Part> partTable;
    /**
     * The Product table.
     */
    public TableView<Product> productTable;
    /**
     * The Part text field.
     */
    public TextField partTextField;
    /**
     * The Product text field.
     */
    public TextField productTextField;
    /**
     * The constant partToModify.
     */
    public static Part partToModify;
    /**
     * The constant productToModify.
     */
    public static Product productToModify;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * On add part.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPartForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 1000);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On modify part.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        if(partTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part selected");
            alert.setContentText("No part was selected to be modified.");
            alert.showAndWait();
        }
        else {
            partToModify = partTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyPartForm.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 1000);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * On delete part.
     *
     * @param actionEvent the action event
     */
    public void onDeletePart(ActionEvent actionEvent) {
        Part part = partTable.getSelectionModel().getSelectedItem();

        if(part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No part found");
            alert.setContentText("No part was selected to be deleted.");
            alert.showAndWait();
        }
        else {
            Inventory.deletePart(part);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Deletion");
            alert.setContentText("The selected part has been deleted.");
            alert.showAndWait();
        }
    }

    /**
     * On add product.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProductForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On modify product.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        if(productTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No product selected");
            alert.setContentText("No product was selected to be modified.");
            alert.showAndWait();
        }
        else {
            productToModify = productTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyProductForm.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * On delete product.
     *
     * @param actionEvent the action event
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product product = productTable.getSelectionModel().getSelectedItem();

        if(product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No product found");
            alert.setContentText("No product was selected to be deleted.");
            alert.showAndWait();
        }
        else if (product.getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Associated parts found");
            alert.setContentText("Cannot delete product with associated parts.");
            alert.showAndWait();
        }
        else {
            Inventory.deleteProduct(product);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Deletion");
            alert.setContentText("The selected product has been deleted.");
            alert.showAndWait();
        }
    }

    /**
     * On exit.
     *
     * @param actionEvent the action event
     */
    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
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

    /**
     * On action search products.
     *
     * @param actionEvent the action event
     */
    public void onActionSearchProducts(ActionEvent actionEvent) {
        String query = productTextField.getText();
        ObservableList<Product> products = searchProducts((query));

        if(products.size() == 0) {
            try {
                int id = Integer.parseInt(query);
                Product product = searchByProductId(id);
                if(product != null) {
                    products.add(product);
                }
            }
            catch (NumberFormatException e){
                // ignore exception
            }
        }
        productTable.setItems(products);
        productTextField.setText("");

        if(products.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No matches found");
            alert.setContentText("There were no products matching your input.");
            alert.showAndWait();
        }
    }

    private Product searchByProductId (int id) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for(Product product: allProducts) {
            if (product.getId() == id){
                return product;
            }
        }
        return null;
    }

    private ObservableList<Product> searchProducts (String partialName) {
        ObservableList<Product> matches = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        String partialNameLower = partialName.toLowerCase();
        for(Product product : allProducts) {
            if(product.getName().toLowerCase().contains(partialNameLower)) {
                matches.add(product);
            }
        }

        return matches;
    }

}
