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

public class MainMenu implements Initializable {

    public TableColumn<Object, Object> partIdCol;
    public TableColumn<Object, Object> partNameCol;
    public TableColumn<Object, Object> partInventoryLevelCol;
    public TableColumn<Object, Object> partPriceCol;
    public TableColumn<Object, Object> productIdCol;
    public TableColumn<Object, Object> productNameCol;
    public TableColumn<Object, Object> productInventoryLevelCol;
    public TableColumn<Object, Object> productPriceCol;
    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public Button exit;
    public TableView<Part> partTable;
    public TableView<Product> productTable;
    public TextField partTextField;
    public TextField productTextField;
    public static Part partToModify;
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

    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPartForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 1000);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

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
        }
    }

    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProductForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

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

    public void onDeleteProduct(ActionEvent actionEvent) {
        Product product = productTable.getSelectionModel().getSelectedItem();

        if(product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No product found");
            alert.setContentText("No product was selected to be deleted.");
            alert.showAndWait();
        }
        else {
            Inventory.deleteProduct(product);
        }
    }

    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
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

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No matches found");
        alert.setContentText("There were no products matching your input.");
        alert.showAndWait();
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
