package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Class that launches an application to keep track of inventory for a shop.
 <p></p>
 JavaDoc folder is found in root folder of project:  MennearQKM2/JavaDoc
 <p></p>
 <p>
 FUTURE OPTIMIZATION: Instead of locally saving and/or hardcoding the Inventory data, I will create a database and database management system.<br />
 FUTURE OPTIMIZATION: Will create more methods in HelperFunction class of repeat code that is still present in the different Controller classes.<br />
 FUTURE OPTIMIZATION: Will better consolidate the repeat code within the HelperFunction class.
 </p>
 <p></p>
 <p>
 RUNTIME ERROR: The runtime error I corrected was the logic validating for creating/updating a New Dish or Food.
 The runtime error involved with this process is that I could not properly display a dynamic message that represented what needed to be fixed.
 The logic for creating a new Food/Dish is checking: Min, Max, Stock, and textFields. <br />
 The error: I could only show one alert message one by one- despite if there were multiple logical errors present <br />
 The solution: I created a HashMap with keys: the error numerical value and values: the error message. <br />
 I then created an If branch that validated whether there were any logic errors.
 If an error is present, then that specific error number is retrieved in the HashMap.
 After the If branch, once all logical errors are checked, the appropriate errors are added to a String that will be displayed on an Alert Error message. <br />
 The method definition can be found:
 </p>




 @author Stephanie Mennear
 */


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root));
        stage.show();

    }

    /** Launch the app, instance of the Application is constructed.*/
    public static void main(String[] args){
        //Hard Code Data
        Food food1 = new Import(1, "Chicken", 25.99, 10, 2, 99, "Cube");
        Food food2 = new Market(2, "Spinach", 13.75, 15, 1, 89, 8);
        Food food3 = new Import(3, "Strawberry", 800.99, 23, 10, 100, "Helm");
        Food food4 = new Market(4, "Wine", 5.99, 43, 1, 55, 2);
        Food food5 = new Import(5, "Cream", 34.99, 30, 10, 44, "Rex");
        Food food6 = new Market(6, "Butter", 30.99, 5, 1, 66, 45);

        Dish prod1 = new Dish(1,"Country Fried Chicken", 899, 15, 2, 50);
        Dish prod2 = new Dish(2, "Lasagna", 400, 20, 5, 30);
        Dish prod3 = new Dish(3, "Veggie Soup", 600, 23, 5, 24 );
        Dish prod4 = new Dish(4, "Cheesecake", 1500, 16, 5, 20);
        Dish prod5 = new Dish(5, "Mac and Cheese", 1800, 14, 5, 20);
        Dish prod6 = new Dish(6, "Sauteed Veggies", 150, 20, 10, 40);

        prod1.addAssociatedFoods(food5);
        prod1.addAssociatedFoods(food1);
        prod2.addAssociatedFoods(food1);
        prod2.addAssociatedFoods(food2);
        prod3.addAssociatedFoods(food5);
        prod4.addAssociatedFoods(food1);
        prod4.addAssociatedFoods(food3);
        prod5.addAssociatedFoods(food6);

        Inventory.addFood(food1);
        Inventory.addFood(food2);
        Inventory.addFood(food3);
        Inventory.addFood(food4);
        Inventory.addFood(food5);
        Inventory.addFood(food6);

        Inventory.addDish(prod1);
        Inventory.addDish(prod2);
        Inventory.addDish(prod3);
        Inventory.addDish(prod4);
        Inventory.addDish(prod5);
        Inventory.addDish(prod6);


        launch(args);
    }
}

