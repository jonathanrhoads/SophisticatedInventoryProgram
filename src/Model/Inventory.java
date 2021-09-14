package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {


    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        for(Part part : allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct (int productId) {
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for(Part part : Inventory.getAllParts()){
            if(part.getName().toUpperCase().contains(partName.toUpperCase())){
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    public static ObservableList<Product> lookupProduct (String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for(Product product : Inventory.getAllProducts()){
            if(product.getName().toUpperCase().contains(productName.toUpperCase())){
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct (int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.contains(selectedPart) && allParts.remove(selectedPart);
    }

    public static boolean deleteProduct (Product selectedProduct) {
        return allProducts.contains(selectedProduct) && allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts () {
        return  allParts;
    }

    public static ObservableList<Product> getAllProducts () {
        return allProducts;
    }



}
