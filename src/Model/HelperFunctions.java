package Model;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Objects;

/** Class that holds repeating logic and functions to be reused in different methods. */
public class HelperFunctions {

    /** Method to determine if a Food is valid.
     Will return true if Min greater than Max.
     Will return true if Max is greater than or equal to Stock and Min is less than or equal to Stock.
     Will return true if the Name text field is not empty.
     Will return true if the Company Name text field is not empty.
     Will trigger an Alert Error with the appropriate messages for the fields that are not valid.
     If none of the above logic errors are found, the first if block returns false.
     The final return of the method is True-> all fields are valid.
     The method uses a HashMap to access the error message to the error.
     The values of the HashMap contain the error messages.
     Once the keys are match with an error, the values are added to a String "errorBlock".
     The String "errorBlock" is set to the content on the error alert.
     @param stock the stock of the Food.
     @param min the min value of the Food.
     @param max the max value of the Food.
     @param NameTextField the text field holding the name of the Food.
     @param SourceField the text holding the CompanyName
     @param SourceLabel the label determining if outsourced.
     @return boolean
     */
    public static boolean isAmountValid(int stock, int min, int max, TextField NameTextField, Label SourceLabel, TextField SourceField) {

        ObservableList<Integer> errors = FXCollections.observableArrayList();
        String errorBlock = new String();
        HashMap<Integer, String> errorMessages = new HashMap<Integer, String>();
        errorMessages.put(0, "Min cannot be greater than Max.");
        errorMessages.put(1, "Inventory cannot be greater than Max or less than Min.");
        errorMessages.put(2, "Name field cannot be empty.");
        errorMessages.put(3, "Company field cannot be empty.");
        boolean company = (Objects.equals(SourceLabel.getText(), "Company Name") && !(SourceField.getText()).trim().isEmpty());
        boolean inHouse = (Objects.equals(SourceLabel.getText(), "Market ID"));
        if (max >= min && !(NameTextField.getText()).trim().isEmpty() && (stock >= min && stock <= max) && (company || inHouse)) {
            System.out.println("All TRUE");
            return false;
        }else {
            if (min > max) {
                System.out.println("min>max");
                errors.add(0);
            }
            if (stock < min || stock > max) {
                System.out.println("stock");
                errors.add(1);
            }
            if ((NameTextField.getText()).trim().isEmpty()) {
                System.out.println("Name");
                errors.add(2);
            }
            if((Objects.equals(SourceLabel.getText(), "Company Name") && (SourceField.getText()).trim().isEmpty())){
                System.out.println("Company");
                errors.add(3);
            }
            System.out.println("Errors?");
            for (Integer error : errors) {
                if (errorMessages.containsKey(error)) {
                    errorBlock = errorBlock + " " + errorMessages.get(error) + "\n";
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorBlock);
            alert.setTitle("Error");
            alert.showAndWait();
        }

        return true;

    }


    /** Method to determine if a Dish is valid.
     Will return true if Min greater than Max.
     Will return true if Max is greater than or equal to Stock and Min is less than or equal to Stock.
     Will return true if the Name text field is not empty.
     Will trigger an Alert Error with the appropriate messages for the fields that are not valid.
     If none of the above logic errors are found, the first if block returns false.
     The final return of the method is True-> all fields are valid.
     The method uses a HashMap to access the error message to the error.
     The values of the HashMap contain the error messages.
     Once the keys are match with an error, the values are added to a String "errorBlock".
     The String "errorBlock" is set to the content on the error alert.
     @param stock the stock of the Dish.
     @param min the min value of the Dish.
     @param max the max value of the Dish.
     @param NameTextField the text field holding the name of the Dish.
     @return boolean
     */
    public static boolean isInputValid(int stock, int min, int max, TextField NameTextField) {
        ObservableList<Integer> errors = FXCollections.observableArrayList();
        String errorBlock = new String();
        HashMap<Integer, String> errorMessages = new HashMap<Integer, String>();
        errorMessages.put(0, "Min cannot be greater than Max.");
        errorMessages.put(1, "Inventory cannot be greater than Max or less than Min.");
        errorMessages.put(2, "Name field cannot be empty.");
        if (max >= min && !(NameTextField.getText()).trim().isEmpty() && (stock >= min && stock <= max)) {
            return false;
        }else {
            if (min > max) {
                errors.add(0);
            }
            if (stock < min || stock > max) {
                errors.add(1);
            }
            if ((NameTextField.getText()).trim().isEmpty()) {
                errors.add(2);
            }
            for (Integer error : errors) {
                if (errorMessages.containsKey(error)) {
                    errorBlock = errorBlock + " " + errorMessages.get(error) + "\n";
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorBlock);
            alert.setTitle("Error");
            alert.showAndWait();
        }

        return true;
    }


    /** Determine if part is associated with a product, return boolean false if not.
     @param part the Food that is being referenced.
     @return boolean.
     Implemented in MainScreen see linked:
     @see Controller.MainMenu#onActionDeleteFood(ActionEvent)  */
    public static boolean isAssociated(Food part){

        for(Dish product : Inventory.getAllDishes()){
            if(product.getAllAssociatedFoods().contains(part)){
                return true;
            }
        }
        return false;
    }


}