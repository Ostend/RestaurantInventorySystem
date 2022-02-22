package Controller;

import Model.Dish;
import Model.Food;
import Model.HelperFunctions;
import Model.Inventory;
import javafx.application.Platform;
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

public class MainMenu implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Dish, Integer> dishIDCol;

    @FXML
    private TableColumn<Dish, String> dishNameCol;

    @FXML
    private TableColumn<Dish, Double> dishPriceCol;

    @FXML
    private TextField dishSearch;

    @FXML
    private TableColumn<Dish, Integer> dishStockCol;

    @FXML
    private TableView<Dish> dishTableView;

    @FXML
    private TableColumn<Food, Integer> foodIDCol;

    @FXML
    private TableColumn<Food, String> foodNameCol;

    @FXML
    private TableColumn<Food, Double> foodPriceCol;

    @FXML
    private TextField foodSearch;

    @FXML
    private TableColumn<Food, Integer> foodStockCol;

    @FXML
    private TableView<Food> foodTableView;

    /** Method to open the AddDish view.
     @param event on button press, the scene is loaded and stage is set.
     */
    @FXML
    public void onActionAddDish(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddDish.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Method to open the AddFoodFrom view.
     @param event on button press, the scene is loaded and stage is set.
     */
    @FXML
    public void onActionAddFood(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddFood.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Method to delete a dish from the allDishes ObservableList.
     Before dish is deleted, the dish is checked whether it is associated with a food through the Dish.getAllAssociatedFoods() method.
     If a dish is associated with a food, the user is prompted that it cannot be deleted.
     If dish is not associated with a dish, dish is deleted using the Inventory.deleteDish() method.
     @param event on button press, action is triggered.
     */
    @FXML
    public void onActionDeleteDIsh(ActionEvent event) {
        Dish selectedDish = dishTableView.getSelectionModel().getSelectedItem();
        if(dishTableView.getSelectionModel().getSelectedItem() != null) {
            if(selectedDish.getAllAssociatedFoods().size() > 0){
                Alert failedDelete = new Alert(Alert.AlertType.ERROR);
                failedDelete.setTitle("Error");
                failedDelete.setContentText("Cannot delete Dish that is associated with Food");
                failedDelete.showAndWait();
                return;
            };
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete food?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    Inventory.deleteDish(dishTableView.getSelectionModel().getSelectedItem());
                    Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION);
                    deletedAlert.setTitle("Success");
                    deletedAlert.setContentText("Item deleted");
                    deletedAlert.showAndWait();
                }catch(NullPointerException e){
                    Alert failedDelete = new Alert(Alert.AlertType.ERROR);
                    failedDelete.setTitle("Error");
                    failedDelete.setContentText("Failed to delete: " + e.getMessage());
                };

            }
        }else{
            Alert selectionWarning = new Alert(Alert.AlertType.WARNING);
            selectionWarning.setContentText("Nothing selected");
            selectionWarning.showAndWait();
        }

    }

    /** Method to delete a food from the allFoods ObservableList.
     Before food is deleted, the food is checked whether it is associated with a dish through the Dish.getAllAssociatedFoods() method.
     If a food is associated with a dish, the user is prompted that it cannot be deleted.
     If food is not associated with a dish, food is deleted using the Inventory.deleteFood() method.
     @param event on button press, action is triggered.
     */
    @FXML
    public void onActionDeleteFood(ActionEvent event) {
        if (foodTableView.getSelectionModel().getSelectedItem() != null) {

            if (HelperFunctions.isAssociated(foodTableView.getSelectionModel().getSelectedItem())) {
                Alert failedDelete = new Alert(Alert.AlertType.ERROR);
                failedDelete.setTitle("Error");
                failedDelete.setContentText("Cannot delete Food that is associated with Dish");
                failedDelete.showAndWait();
                return;
            }

            if (!HelperFunctions.isAssociated(foodTableView.getSelectionModel().getSelectedItem())) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete food?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        Inventory.deleteFood(foodTableView.getSelectionModel().getSelectedItem());
                        Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION);
                        deletedAlert.setTitle("Success");
                        deletedAlert.setContentText("Item deleted");
                        deletedAlert.showAndWait();
                    } catch (NullPointerException e) {
                        Alert failedDelete = new Alert(Alert.AlertType.ERROR);
                        failedDelete.setTitle("Error");
                        failedDelete.setContentText("Failed to delete: " + e.getMessage());

                    }
                } //END BLOCK


            } else {
                Alert selectionWarning = new Alert(Alert.AlertType.WARNING);
                selectionWarning.setContentText("Nothing selected");
                selectionWarning.showAndWait();
            }
        }
    }
    /** Method to delete a dish from the allDishes ObservableList.
     Before dish is deleted, the dish is checked whether it is associated with a food through the Dish.getAllAssociatedFoods() method.
     If a dish is associated with a food, the user is prompted that it cannot be deleted.
     If dish is not associated with a dish, dish is deleted using the Inventory.deleteDish() method.
     @param event on button press, action is triggered.
     */
    @FXML
    public void onActionModifyDish(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyDish.fxml"));
        loader.load();
        ModifyDishForm ADMCController = loader.getController();
        if(dishTableView.getSelectionModel().getSelectedItem() != null) {
            ADMCController.sendDish(dishTableView.getSelectionModel().getSelectedItem());
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing selected");
            alert.showAndWait();
        }
    }

    /** Method to open the ModifyFoodForm view.
     Will detect if a food is selected in the table view. A warning will appear if nothing is selected and remains on MainScreen view.
     @param event the button click action
     */
    @FXML
    public void onActionModifyFood(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyFood.fxml"));
        loader.load();
        ModifyFoodForm ADMCController = loader.getController();
        if(foodTableView.getSelectionModel().getSelectedItem() != null) {
            ADMCController.sendFood(foodTableView.getSelectionModel().getSelectedItem());
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing selected");
            alert.showAndWait();
        }
    }
    
    /** Method to search for food in the tableview.
     Searches the allFoods ObservableList.
     Searches both the id and name of a dish. */
    @FXML
    public void foodSearch(KeyEvent event) {
        ObservableList<Food> foodialFoods = FXCollections.observableArrayList();
        String query = foodSearch.getText();
        //Test whether the input text is a string or int with regex
        if(query.matches("\\d+") && Inventory.lookupFood(Integer.parseInt(query)) != null){
            foodialFoods.add(Inventory.lookupFood(Integer.parseInt(query)));
        }else {
            foodialFoods = Inventory.lookupFood(query);
        }
        foodTableView.setItems(foodialFoods);
        foodTableView.getSelectionModel().selectFirst();
        if(foodialFoods.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Item(s) not found.");
            alert.setTitle("Item not found.");
            alert.showAndWait();
        }

    }

    /** Method to search for dishes in the tableview.
     Searches both the id and name of a dish.*/
    @FXML
    public void dishSearch(KeyEvent event) {
        ObservableList<Dish> foodialDishes = FXCollections.observableArrayList();
        String query = dishSearch.getText();
        //Test whether the input text is a string or int with regex
        if(query.matches("\\d+") && Inventory.lookupDish(Integer.parseInt(query)) != null){
            foodialDishes.add(Inventory.lookupDish(Integer.parseInt(query)));
            dishTableView.setItems(foodialDishes);
            dishTableView.getSelectionModel().selectFirst();
        }else {
            foodialDishes = Inventory.lookupDish(query);
            dishTableView.setItems(foodialDishes);
            dishTableView.getSelectionModel().selectFirst();
        }
        if(foodialDishes.size() == 0 || foodialDishes == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Item(s) not found.");
            alert.setTitle("Item not found.");
            alert.showAndWait();
        }
    }
    
    /** Method to exit application.
     Return launch() method. */
    @FXML
    public void onActionExit(ActionEvent event) {
        Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        Platform.exit();
        System.exit(0);
    }

    /** Initialize the controller class.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodTableView.setItems(Inventory.getAllFoods());
        foodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        foodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        foodStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        dishTableView.setItems(Inventory.getAllDishes());
        dishIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dishNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dishPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        dishStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }
}
