package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class ModifyFoodForm {
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
    private ToggleGroup foodSourceTG;

    @FXML
    private Label sourceLBL;

    /** Method that will accept a Food object that is to be retrieved from the allFoods ObservableList.
     The method will determine if the Food is an instance of Market or Import class.
     Once this is determined, the correct radio button is selected and the SourceTextLabel is changed appropriately.
     @param food the Food which is displayed and to be modified.
     */
    public void sendFood(Food food){

        foodIDTF.setText(String.valueOf(food.getId()));
        foodNameTF.setText(String.valueOf(food.getName()));
        foodCostTF.setText(String.valueOf(food.getPrice()));
        foodStockTF.setText(String.valueOf(food.getStock()));
        foodMaxTF.setText(String.valueOf(food.getMax()));
        foodMinTF.setText(String.valueOf(food.getMin()));
        if(food instanceof Market){
            //by default Market radio button is selected
            foodSourceTF.setText(String.valueOf(((Market) food).getMarketId()));
            sourceLBL.setText("Market ID");
        }
        if(food instanceof Import) {
            sourceLBL.setText("Company Name");
            foodSourceTF.setText(String.valueOf(((Import) food).getCompanyName()));
            foodImportRB.setSelected(true);
        }
    }

    /** Method to return to MainScreen view without saving any changes to the Food.
     @param event button click 'OK' exits current view to the MainFood view. */

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Data will be lost, do you want to cancel and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Method to respond when Import radio button is selected.
     The previous source text label is toggled to display "Company Name" instead of "Market ID".
     The method uses the id of a Food to determine if it is an instance of Market or Import by using the Inventory.lookupFood() method.
     The source text field is populated if the Food is already an instance of Import.
     If food is instance of Market, the text field will clear. */
    @FXML
    void onActionImportRB(ActionEvent event) {
        sourceLBL.setText("Company Name");
        if(Inventory.lookupFood(Integer.parseInt(foodIDTF.getText())) instanceof Market){
            foodSourceTF.clear();
        }else{
            foodSourceTF.setText(String.valueOf(((Import) Inventory.lookupFood(Integer.parseInt(foodIDTF.getText()))).getCompanyName()));

        };
    }

    /** Method to respond when Market radio button is selected.
     The previous source text label is toggled to display "Market ID" instead of "Company Name".
     The method uses the id of a Food to determine if it is an instance of Market or Import by using the Inventory.lookupFood() method.
     The source text field is populated if the Food is already an instance of Market.
     If food is instance of Import, the text field will clear. */
    @FXML
    void onActionMarketRB(ActionEvent event) {
        sourceLBL.setText("Market ID");

        if(Inventory.lookupFood(Integer.parseInt(foodIDTF.getText())) instanceof Import){
            foodSourceTF.clear();
        }else{
            foodSourceTF.setText(String.valueOf(((Market) Inventory.lookupFood(Integer.parseInt(foodIDTF.getText()))).getMarketId()));

        };
    }
    
    /** Method to save(update) the modification of an existing Food in the allFoods ObservableLIst.
     Inventory.updateFood() is called to update the allFoods list.
     Before updating an existing Food, the Food subclass is verified by determining which radio button is selected (Market vs Import).
     Depending on which instance Food is, the CompanyName or MarketID is set.
     Following, the Inventory.getAllFoods() method is called in an enhanced for loop.
     Within the for loop, the index is updated with each iteration until the Food is located.
     Once the Food is located, the Food is updated.
     *Logical error checking is sent to a function in Class HelperFunctions called: isAmountValid.
     Error checking: Min and Max properties are verified that min !> max.
     Error checking: Inventory is verified that it is not greater than max or less than min.
     Error checking: Checks whether OutSourced radio button is selected and will make sure user inputs a String for company name.
     Error Checking: Checks whether a String is input for dish name.
     Syntax error checking is done through a try catch block:
     Will verify if a proper int/double is entered into the appropriate fields.
     Will verify that there are no null fields for the int/double values.
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
            int id = parseInt(foodIDTF.getText());
            String name = foodNameTF.getText();
            double price = Double.parseDouble(foodCostTF.getText());
            int stock = parseInt(foodStockTF.getText());
            int min = parseInt(foodMinTF.getText());
            int max = parseInt(foodMaxTF.getText());

            if (HelperFunctions.isAmountValid(stock, min, max, foodNameTF, sourceLBL, foodSourceTF))
                return;

            int index = -1;
            if (foodImportRB.isSelected()) {
                String companyName = foodStockTF.getText();
                for (Food food : Inventory.getAllFoods()) {
                    index++;
                    if (food.getId() == parseInt(foodIDTF.getText())) {
                        Inventory.updateFood(index, new Import(id, name, price, stock, min, max, companyName));
                    }
                    ;
                }
            }
            if (foodMarketRB.isSelected()) {
                int marketId = parseInt(foodSourceTF.getText());
                for (Food food : Inventory.getAllFoods()) {
                    index++;
                    if (food.getId() == parseInt(foodIDTF.getText())) {
                        Inventory.updateFood(index, new Market(id, name, price, stock, min, max, marketId));
                    }

                }
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
