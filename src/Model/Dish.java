package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** Class to define/describe a Dish. */
public class Dish {
    private ObservableList<Food> associatedFoods = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**Constructor for creating a new instance of Dish. */
    public Dish(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this .name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /** @return id*/
    public int getId() {
        return id;
    }

    /** @return name*/
    public String getName() {
        return name;
    }

    /** @return price*/
    public double getPrice() {
        return price;
    }

    /** @return stock*/
    public int getStock() {
        return stock;
    }

    /** @return min*/
    public int getMin() {
        return min;
    }

    /** @return max*/
    public int getMax() {
        return max;
    }


    /** @param id the id to be set */
    public void setId(int id) {
        this.id = id;
    }

    /** @param name the name to be set */
    public void setName(String name) {
        this.name = name;
    }

    /** @param price the price to be set */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @param stock the stock to be set */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** @param min the min to be set */
    public void setMin(int min) {
        this.min = min;
    }

    /** @param max the max to be set */
    public void setMax(int max) {
        this.max = max;
    }

    /** @return associatedFoods */
    public ObservableList<Food> getAllAssociatedFoods() {
        return associatedFoods;
    }

    /** @param food the food to add to associatedFoods ObservableList*/
    public void addAssociatedFoods(Food food) {
        this.associatedFoods.add(food);
    }

    /** @param selectedAssociatedFood the Food to remove from associatedFoods ObservableList */
    public boolean deleteAssociatedFood(Food selectedAssociatedFood){
        associatedFoods.remove(selectedAssociatedFood);
        return true;
    }
}
