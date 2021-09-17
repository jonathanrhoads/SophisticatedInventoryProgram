package Controller;

import Model.Inventory;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductForm implements Initializable {
    public TextField MinTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField MaxTextField;
    public Button saveProductButton;
    public Button cancelButton;
    public TableView partTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partPriceCol;
    public Button addPart;
    public Button modifyPart;
    public TextField partTextField;
    public TableView associatedPartTable;
    public TableColumn associatedPartIdCol;
    public TableColumn associatedPartNameCol;
    public TableColumn associatedPartStockCol;
    public TableColumn associatedPartPriceCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(MainMenu.productToModify.getAllAssociatedParts());
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void onSaveProduct(ActionEvent actionEvent) {
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
    }

    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
    }

    public void onActionSearchParts(ActionEvent actionEvent) {
    }
}
