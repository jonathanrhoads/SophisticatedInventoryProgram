package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Product.
 */
public class Product {

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**
     * The constant currentId.
     */
    public static int currentId = 3; // Set to 3 to compensate for test data.

    /**
     * Instantiates a new Product.
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max   the max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets id.
     *
     * @param id the product id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets name.
     *
     * @param name the product name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets price.
     *
     * @param price the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets stock.
     *
     * @param stock the inventory level of the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets min.
     *
     * @param min the minimum inventory level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets max.
     *
     * @param max the maximum inventory level
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets id.
     *
     * @return the product's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the product's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets price.
     *
     * @return the product's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets stock.
     *
     * @return the product's inventory level
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets min.
     *
     * @return the product's min inventory level
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets max.
     *
     * @return the product's max inventory level
     */
    public int getMax() {
        return max;
    }

    /**
     * Add associated part.
     *
     * @param part the part to add
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Gets all associated parts.
     *
     * @return the list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Delete associated part boolean.
     *
     * @param selectedAssociatedPart the part to be deleted from the list of associated parts
     * @return the boolean
     */
    public boolean deleteAssociatedPart (Part selectedAssociatedPart) {
        return associatedParts.contains(selectedAssociatedPart) && associatedParts.remove(selectedAssociatedPart);
    }
}
