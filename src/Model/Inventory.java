package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class to define/describe Inventory. */
public class Inventory {
    private static ObservableList<Dish> allDishes = FXCollections.observableArrayList();
    private static ObservableList<Food> allFoods = FXCollections.observableArrayList();

    /** @param food the Food to add to allFoods ObservableList */
    public static void addFood(Food food){
        allFoods.add(food);
    }

    /** @param dish the Dish to add to allDishes observableList */
    public static void addDish(Dish dish){
        allDishes.add(dish);
    }

    /** Method to search a specific Food in allFoods ObservableList based on the Food ID.
     @param foodID the id of the Food to lookup.
     @return Food the Food that is matched by ID.*/
    public static Food lookupFood(int foodID){
        for(Food food: allFoods){
            if(food.getId() == foodID){
                return food;
            }
        }
        return null;
    }

    /** Method to search a specific Dish in allDishes ObservableList based on Dish ID.
     @param dishID the id of the dish to lookup.
     @return Dish the Dish that is matched by ID.*/
    public static Dish lookupDish(int dishID){
        for(Dish dish: allDishes){
            if(dish.getId() == dishID){
                return dish;
            }
        }
        return null;
    }

    /** Method to search a specific Food in allFoods ObservableList based on the Food name.
     Method converts all strings to lower case.
     Adds the matched strings to a new ObservableList: filteredFoods.
     @param foodName the name of the Food to lookup.
     @return FilteredFoods the ObservableList that is includes all matching Foods by name.*/
    public static ObservableList<Food> lookupFood(String foodName){
        ObservableList<Food> filteredFoods = FXCollections.observableArrayList();
        for (Food food: allFoods){
            if((food.getName().toLowerCase()).contains(foodName.toLowerCase())) {
                filteredFoods.add(food);
            }
        }
        return filteredFoods;
    }

    /** Method to search a specific Dish in allDishes ObservableList based on the Dish name.
     Method converts all strings to lower case.
     Adds the matched strings to a new ObservableList: filteredDishs.
     @param dishName the name of the Dish to lookup.
     @return FilteredDishs the ObservableList that is includes all matching Dishs by name.*/
    public static ObservableList<Dish> lookupDish(String dishName){
        ObservableList<Dish> filteredDishs = FXCollections.observableArrayList();
        for (Dish dish: allDishes){
            if((dish.getName().toLowerCase()).contains(dishName.toLowerCase())) {
                filteredDishs.add(dish);
            }
        }
        return filteredDishs;
    }

    /** Method to update an existing Food.
     @param index the index of the Food in the ObservableList allFoods.
     @param selectedFood the Food to update.*/
    public static void updateFood(int index, Food selectedFood){
        allFoods.set(index, selectedFood);
    }

    /** Method to update an existing Dish.
     @param index the index of the Dish in the ObservableList allDishes.
     @param newDish the Dish to update.*/
    public static void updateDish(int index, Dish newDish){
        allDishes.set(index, newDish);
    }

    /** Method to remove a Food from allFoods ObservableList.
     @param selectedFood the Food to delete.  */
    public static void deleteFood(Food selectedFood){
        allFoods.remove(selectedFood);
    }

    /** Method to remove a Dish from allDishes ObservableList.
     @param selectedDish the Dish to delete. */
    public static void deleteDish(Dish selectedDish){
        allDishes.remove(selectedDish);
    }

    /** Method to return the ObservableList allFoods.
     @return allFoods */
    public static ObservableList<Food> getAllFoods(){
        return allFoods;
    }

    /** Method to return the ObservableList allDishes.
     @return allDishes */
    public static ObservableList<Dish> getAllDishes(){
        return allDishes;
    }
}
