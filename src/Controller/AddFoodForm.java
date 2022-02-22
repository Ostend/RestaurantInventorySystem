package Controller;

import Model.HelperFunctions;
import Model.Import;
import Model.Inventory;
import Model.Market;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**AddFoodForm class to add a Food to the allFoods ObservableList. 
 RUNTIME ERROR: The runtime error I corrected was the logic validating for creating/updating a New Food.
 The runtime error involved with this process is that I could not properly display a dynamic message that represented what needed to be fixed.
 The logic for creating a new Food is checking: Min, Max, Stock, and textFields.
 The error: I could only show one alert message one by one- despite if there were multiple logical errors present
 The solution: I created a HashMap with keys: the error numerical value and values: the error message.
 I then created an If branch that validated whether there were any logic errors.
 If an error is present, then that specific error number is retrieved in the HashMap.
 After the If branch, once all logical errors are checked, the appropriate errors are added to a String that will be displayed on an Alert Error message.
 The method definition can be found:
 @see HelperFunctions#isInputValid(int, int, int, TextField)

 */
public class AddFoodForm {
    Stage stage;
    Parent scene;    

    @FXML
    private Button addFoodClose;

    @FXML
    private TextField foodCostTF;

    @FXML
    private TextField foodIDTF;

    @FXML
    private RadioButton foodImportRB;

    @FXML
    private RadioButton foodMarketRB;

    @FXML
    private TextField foodMaxTF;

    @FXML
    private TextField foodMinTF;

    @FXML
    private TextField foodNameTF;

    @FXML
    private Button foodSave;

    @FXML
    private TextField foodSourceTF;

    @FXML
    private TextField foodStockTF;

    @FXML
    private Label sourceLBL;

    @FXML
    private ToggleGroup foodSourceTG;

    /** This method checks the current Foods in allFoods ObservableList by id.
     If an int id is already taken by a current food, the int id will increment until the int id does not exist.
     The unique int id will then be returned.
     @param id the id to compare to already existing id's.
     @return the unique id to be applied to a new Food object. */
    public int getNewFoodId(int id){
        while (Inventory.lookupFood(id) != null && Inventory.lookupFood(id).getId() == id ) {
            id = Inventory.lookupFood(id).getId() + 1;
        }
        return id;
    };

    /** Method to return to MainScreen view without saving any changes to the Food.
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

    /** Method to clear the text field when Market radio button is selected.
     The text field will clear to change the field from the previous "Company Name" to an empty field for "MarketID".*/
    @FXML
    void onActionImportRB(ActionEvent event) {
        sourceLBL.setText("Company Name");
        foodSourceTF.clear();
    }

    /** Method to clear the text field when Market radio button is selected.
     The text field will clear to change the field from the previous "Company Name" to an empty field for "MarketID".*/
    @FXML
    void onActionMarketRB(ActionEvent event) {
        sourceLBL.setText("Market ID");
        foodSourceTF.clear();


    }

    /** Method to save a Food to the allFoods ObservableList.
     New Food is implemented and added to the allFoods list.
     Logical error checking is sent to a function in Class HelperFunctions called isAmountValid:
     Error checking: Min and Max properties are verified that min !> max.
     Error checking: Inventory is verified that it is not greater than max or less than min.
     Error checking: Checks whether OutSourced radio button is selected and will make sure user inputs a String for company name.
     Error Checking: Checks whether a String is input for food name.
     Syntax error checking is done through a try catch block:
     Will verify if a proper int/double is entered into the appropriate fields.
     Will verify that there are no null fields for the int/double values.
     Saving Food:
     Radio button is verified (whether it is selected) whether it is an Market Food or Import Food.
     New Food is created with validated properties.
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try{
            int id = getNewFoodId(1);
            String name = foodNameTF.getText();
            double price = Double.parseDouble(foodCostTF.getText());
            int stock = Integer.parseInt(foodStockTF.getText());
            int min = Integer.parseInt(foodMinTF.getText());
            int max = Integer.parseInt(foodMaxTF.getText());

            //Logic handling for Min/Max/Inv and verify that Name field is not null:
            if (HelperFunctions.isAmountValid(stock, min, max, foodNameTF, sourceLBL, foodSourceTF)){
                System.out.println("HI");
                return;
            }

            if(foodMarketRB.isSelected()){
                System.out.println("Market RB");
                int marketID = Integer.parseInt(foodSourceTF.getText());
                Inventory.addFood(new Market(id, name, price, stock, min, max, marketID));
            }else{
                System.out.println("Import RB");
                String companyName = foodSourceTF.getText();
                Inventory.addFood(new Import(id, name, price, stock, min, max, companyName));
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Food saved.");
            alert.setTitle("Success");
            alert.showAndWait();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }catch(NumberFormatException | NullPointerException e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage() + " must provide number");
            alert.setTitle("Error");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
