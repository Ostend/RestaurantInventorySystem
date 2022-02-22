package Controller;

import Model.Dish;
import Model.Food;
import Model.HelperFunctions;
import Model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**AddDishForm class to add a Dish to the allDishs ObservableList.
 RUNTIME ERROR: The runtime error I corrected was the logic validating for creating/updating a New Dish.
 The runtime error involved with this process is that I could not properly display a dynamic message that represented what needed to be fixed.
 The logic for creating a new Dish is checking: Min, Max, Stock, and textFields.
 The error: I could only show one alert message one by one- despite if there were multiple logical errors present
 The solution: I created a HashMap with keys: the error numerical value and values: the error message.
 I then created an If branch that validated whether there were any logic errors.
 If an error is present, then that specific error number is retrieved in the HashMap.
 After the If branch, once all logical errors are checked, the appropriate errors are added to a String that will be displayed on an Alert Error message.
 The method definition can be found:
 @see HelperFunctions#isAmountValid(int, int, int, TextField, Label, TextField)
 */
public class AddDishForm implements Initializable {
    Stage stage;
    Parent scene;

    public ObservableList<Food> selectedNewFoods = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Food, Integer> allFoodIDCol;

    @FXML
    private TableColumn<Food, String> allFoodNameCol;

    @FXML
    private TableColumn<Food, Double> allFoodPriceCol;

    @FXML
    private TableColumn<Food, Integer> allFoodStockCol;

    @FXML
    private TableView<Food> allFoodTableView;

    @FXML
    private TableColumn<Food, Integer> associatedFoodIDCol;

    @FXML
    private TableColumn<Food, String> associatedFoodNameCol;

    @FXML
    private TableColumn<Food, Double> associatedFoodPriceCol;

    @FXML
    private TableColumn<Food, Integer> associatedFoodStockCol;

    @FXML
    private TableView<Food> associatedFoodTableView;

    @FXML
    private TextField dishCostTF;

    @FXML
    private TextField dishIDTF;

    @FXML
    private TextField dishMaxTF;

    @FXML
    private TextField dishMinTF;

    @FXML
    private TextField dishNameTF;

    @FXML
    private TextField dishStockTF;

    @FXML
    private TextField searchTextField;


    /** Method to add Food(s) to a Dish's associatedFoodsTableView.
     Will verify if a Food in the tableview is selected. If not, Error Alert is initiated.
     For the selected Food in the allInventoryFoodsTableView, when user clicks 'Add', the food is added to selectedNewFoods ObservableList.
     The associatedFoodsTableView is then populated with the Food(s) in the selectedNewFoods ObservableList.
     */
    @FXML
    void onActionAddFoodB(ActionEvent event) {
        try {
            if(allFoodTableView.getSelectionModel().getSelectedItem() != null) {
                selectedNewFoods.add(allFoodTableView.getSelectionModel().getSelectedItem());
                associatedFoodTableView.setItems(selectedNewFoods);
                associatedFoodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                associatedFoodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                associatedFoodStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                associatedFoodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                return;
            }
            Alert failedAdd = new Alert(Alert.AlertType.ERROR);
            failedAdd.setTitle("Error");
            failedAdd.setContentText("Must select a food to add.");
            failedAdd.showAndWait();


        }catch (NullPointerException e){
            System.out.println(e);
        };
    }
    
    /** This method checks the current Dishes in allDishes ObservableList by id.
     If an int id is already taken by a current Dish, the int id will increment until the int id does not exist.
     The unique int id will then be returned.
     @param id the id to compare to already existing id's.
     @return the unique id to be applied to a new Dish object. */
    public int getNewDishID(int id){
        while (Inventory.lookupDish(id) != null && Inventory.lookupDish(id).getId() == id ) {
            id = Inventory.lookupDish(id).getId() + 1;
        }
        return id;
    }

    /** Method to return to MainMenu view without creating a new instance of Dish.
     @param event button click 'OK' exits current view to the MainFood view. */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Data will be lost, do you want to cancel and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Method to remove Food(s) from a Dish's associatedFoodsTableView.
     Will verify if a Food in the tableview is selected. If not, Error Alert is initiated.
     For the selected Food in the associatedFoodsTableView, when user clicks 'Remove', the food is removed from selectedNewFoods ObservableList.
     The associatedFoodsTableView is then updated with the Food(s) in the selectedNewFoods ObservableList.
     */
    @FXML
    void onActionRemoveFoodB(ActionEvent event) {
        if(associatedFoodTableView.getSelectionModel().getSelectedItem() == null){
            Alert failedRemove = new Alert(Alert.AlertType.ERROR);
            failedRemove.setTitle("Error");
            failedRemove.setContentText("Must select a food to remove.");
            failedRemove.showAndWait();
        }
        selectedNewFoods.remove(associatedFoodTableView.getSelectionModel().getSelectedItem());

    }

    /** Method to save a new instance of Dish to the allDishs ObservableList.
     New Dish is implemented and added to the allDishs list.
     Logical error checking is sent to a function in Class HelperFunctions called isInputValid():
     Error checking: Min and Max properties are verified that min !> max.
     Error checking: Inventory is verified that it is not greater than max or less than min.
     Error Checking: Checks whether a String is input for dish name.
     Syntax error checking is done through a try catch block:
     Will verify if a proper int/double is entered into the appropriate fields.
     Will verify that there are no null fields for the int/double values.
     Saving Dish:
     New Dish is added to the allDishs ObservableList using Inventory.addDish() method.
     The new Dish's associatedFoods ObservableList is then updated based on the selectedNewFoods ObservableList.
     If selectedNewFoods has one or more Foods, an enhanced for loop is called.
     Within the enhanced for loop, each Food existing in selectedNewFoods is added to the new Dish's associatedFoods ObservableList.
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try{
            int id = getNewDishID(1);
            String name = dishNameTF.getText();
            double price = Double.parseDouble(dishCostTF.getText());
            int stock = Integer.parseInt(dishStockTF.getText());
            int min = Integer.parseInt(dishMinTF.getText());
            int max = Integer.parseInt(dishMaxTF.getText());

            if(HelperFunctions.isInputValid(stock, min, max, dishNameTF)) return;
            Inventory.addDish(new Dish(id, name, price, stock, min, max));

            if(selectedNewFoods.size() > 0){
                for(Food food : selectedNewFoods){
                    (Inventory.lookupDish(id)).addAssociatedFoods(food);
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Dish saved.");
            alert.setTitle("Success");
            alert.showAndWait();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }catch(NullPointerException  | NumberFormatException e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage() + " must provide number");
            alert.setTitle("Error");
            alert.showAndWait();
            //System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Method to search for foods in the tableview.
     Searches the allFoods ObservableList.
     Searches both the id and name of a food.*/
    @FXML
    void foodsSearchTextField(KeyEvent event) {
        ObservableList<Food> foodialFoods = FXCollections.observableArrayList();
        String query = searchTextField.getText();
        //Test whether the input text is a string or int with regex
        if(query.matches("\\d+") && Inventory.lookupFood(Integer.parseInt(query)) != null){
            foodialFoods.add(Inventory.lookupFood(Integer.parseInt(query)));
        }else {
            foodialFoods = Inventory.lookupFood(query);
        }
        allFoodTableView.setItems(foodialFoods);
        allFoodTableView.getSelectionModel().selectFirst();
        if(foodialFoods.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Item(s) not found.");
            alert.setTitle("Item not found.");
            alert.showAndWait();
        }
    }

    /** Initialize the allInventoryFoodsTableView with all existing Foods. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allFoodTableView.setItems(Inventory.getAllFoods());
        allFoodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allFoodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allFoodStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allFoodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
