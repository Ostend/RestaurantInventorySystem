# RestaurantInventorySystem
Desktop Application to keep track of inventory for ingredients (food) and recipes (dishes). 

See JavaDocs for detailed explanations of all Classes and their members. 
The inventory system follows an MVC architecture. 

User can add, modify and delete instences of Food and/or Dishes. 

## Models: 
Class: 
- Dish
- Inventory
- Food (abstract)
- Market
- Imported
- HelperFunctions

Food can either be classified as bought from the Market or Imported. 
This is implemented by creating subclasses Market and Imported, which extend class Food. 

## Controllers:
- MainMenu
- AddFood
- ModifyFood
- AddDish
- ModifyDish

## Views:
- MainMenu
- AddFood
- ModifyFood
- AddDish
- ModifyDish

