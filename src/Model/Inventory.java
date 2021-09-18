package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Inventory.
 */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add part.
     *
     * @param newPart the new part
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add product.
     *
     * @param newProduct the new product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Lookup part part.
     *
     * @param partId the part id
     * @return the part
     */
    public static Part lookupPart(int partId) {
        for(Part part : allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Lookup product product.
     *
     * @param productId the product id
     * @return the product
     */
    public static Product lookupProduct (int productId) {
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Lookup part observable list.
     *
     * @param partName the part name
     * @return the observable list
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for(Part part : Inventory.getAllParts()){
            if(part.getName().toUpperCase().contains(partName.toUpperCase())){
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    /**
     * Lookup product observable list.
     *
     * @param productName the product name
     * @return the observable list
     */
    public static ObservableList<Product> lookupProduct (String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for(Product product : Inventory.getAllProducts()){
            if(product.getName().toUpperCase().contains(productName.toUpperCase())){
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * Update part.
     *
     * @param index        the index
     * @param selectedPart the selected part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Update product.
     *
     * @param index           the index
     * @param selectedProduct the selected product
     */
    public static void updateProduct (int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Delete part boolean.
     *
     * @param selectedPart the selected part
     * @return the boolean
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.contains(selectedPart) && allParts.remove(selectedPart);
    }

    /**
     * Delete product boolean.
     *
     * @param selectedProduct the selected product
     * @return the boolean
     */
    public static boolean deleteProduct (Product selectedProduct) {
        return allProducts.contains(selectedProduct) && allProducts.remove(selectedProduct);
    }

    /**
     * Gets all parts.
     *
     * @return the all parts
     */
    public static ObservableList<Part> getAllParts () {
        return  allParts;
    }

    /**
     * Gets all products.
     *
     * @return the all products
     */
    public static ObservableList<Product> getAllProducts () {
        return allProducts;
    }



}
