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

import static java.lang.Integer.parseInt;

public class ModifyDishForm {
    Stage stage;
    Parent scene;
    private ObservableList<Food> associatedFoodsCopy = FXCollections.observableArrayList();


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

    /** Method will accept a dish object that is loaded from the MainMenu as a result of choosing: Modify Dish.
     This method will populate the ModifyFood view with the appropriate values.
     The associated foods Table View values are populated from a copy of the observable list from Dish.getAllAssociatedFoods() called associatedFoodsCopy
     By doing this, if the user cancels the modifications, the original list is not modified.
     @param dish  the specific Dish that will be modified. */
    public void sendDish(Dish dish){

        associatedFoodsCopy.addAll(dish.getAllAssociatedFoods());

        dishIDTF.setText(String.valueOf(dish.getId()));
        dishNameTF.setText(String.valueOf(dish.getName()));
        dishCostTF.setText(String.valueOf(dish.getPrice()));
        dishStockTF.setText(String.valueOf(dish.getStock()));
        dishMinTF.setText(String.valueOf(dish.getMin()));
        dishMaxTF.setText(String.valueOf(dish.getMax()));

        associatedFoodTableView.setItems(dish.getAllAssociatedFoods());
        associatedFoodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedFoodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedFoodStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedFoodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        allFoodTableView.setItems(Inventory.getAllFoods());
        allFoodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allFoodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allFoodStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allFoodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Method to add an associated Food to a Dish.
     Method will detect if nothing is selected in the table view and will raise an error alert.
     Selected Food is added to the Dish's associatedFood ObservableList.*/
    @FXML
    void onActionAddFoodB(ActionEvent event) {
        try{
            if(allFoodTableView.getSelectionModel().getSelectedItem() != null) {
                (Inventory.lookupDish((Integer.parseInt(dishIDTF.getText())))).getAllAssociatedFoods().add(allFoodTableView.getSelectionModel().getSelectedItem());
                return;
            }
            Alert failedAdd = new Alert(Alert.AlertType.ERROR);
            failedAdd.setTitle("Error");
            failedAdd.setContentText("Must select a food to add.");
            failedAdd.showAndWait();


        }catch (NullPointerException e){
            System.out.println(e);
        }
    }

    /** Method to cancel the modification of an existing Dish.
     Method alerts user with Confirmation alert to cancel.
     The Dish's associatedFoods ObservableList is cleared.
     The Dish's associatedFoods ObservableList is replaced with the associatedFoodsCopy ObservableList that was assigned in the sendDish() method.
     The associatedFoodsCopy represents the Dish's original associatedFoods ObservableList before any modifications were made.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Dish dish = Inventory.lookupDish((parseInt(dishIDTF.getText())));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Data will be lost, do you want to cancel and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            dish.getAllAssociatedFoods().clear();

            for (Food food : associatedFoodsCopy) {
                dish.addAssociatedFoods(food);
            }
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Method to remove an associated Food from a Dish.
     Method will detect if nothing is selected in the table view and will raise an error alert.
     Upon confirmation to delete, selected Food is deleted from the Dish's associatedFood ObservableList.*/
    @FXML
    void onActionRemoveFoodB(ActionEvent event) {
        if(associatedFoodTableView.getSelectionModel().getSelectedItem() == null){
            Alert failedRemove = new Alert(Alert.AlertType.ERROR);
            failedRemove.setTitle("Error");
            failedRemove.setContentText("Must select a food to remove.");
            failedRemove.showAndWait();
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete food?");
        Optional<ButtonType> result = confirm.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            (Inventory.lookupDish((Integer.parseInt(dishIDTF.getText())))).deleteAssociatedFood(associatedFoodTableView.getSelectionModel().getSelectedItem());
        }
    }

    /** Method to update instance of Dish to the allDishs ObservableList.
     Logical error checking is sent to a function in Class HelperFunctions called isInputValid():
     Error checking: Min and Max properties are verified that min !> max.
     Error checking: Inventory is verified that it is not greater than max or less than min.
     Error Checking: Checks whether a String is input for dish name.
     Syntax error checking is done through a try catch block:
     Will verify if a proper int/double is entered into the appropriate fields.
     Will verify that there are no null fields for the int/double values.
     Saving Dish:
     Inventory.updateDish() is called to update the current Dish.
     If the associatedFoods Table View has one or more items, an enhanced for loop will add each Food using the addAssociatedFood() method.
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
            ObservableList<Food> foodList = (Inventory.lookupDish((parseInt(dishIDTF.getText()))).getAllAssociatedFoods());
            int index = -1;
            int id = parseInt(dishIDTF.getText());
            String name = dishNameTF.getText();
            double price = Double.parseDouble(dishCostTF.getText());
            int stock = parseInt(dishStockTF.getText());
            int min = parseInt(dishMinTF.getText());
            int max = parseInt(dishMaxTF.getText());
            if (HelperFunctions.isInputValid(stock, min, max, dishNameTF))
                return;
            for (Dish dish : Inventory.getAllDishes()) {
                index++;
                if (dish.getId() == id) {
                    Inventory.updateDish(index, new Dish(id, name, price, stock, min, max));
                }
            }

            if (foodList.size() > 0) {
                for (Food food : foodList) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /** Method to search for foods in the tableview.
     Searches both the id and name of a food.*/
    @FXML
    void partsSearchTextField(KeyEvent event) {
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

}
