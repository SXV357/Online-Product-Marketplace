# CS-180-Project4 - Product Marketplace

# Instructions
From the root directory of the project, compile Runner.java and then execute it which will launch the main application.
When the user is prompted with a list of numbered options, they should input a number corresponding with their choice.
When the user is asked a question, they should input their choice in lowercase.
Example:
>Would you like to change the product's name, quantity, price, or description?
User can input "name", "quantity", "price", or "description"
Note: commas are used internally as a separating character in our file structure.
The current version of this program may crash if certain user inputted fields are filled out with commas.
This error will be fixed in the next iteration of the project.

# Project Submission Details

Shafer Anthony Hofmann - Submitted Report on Brightspace

Nathan Pasic Miller - Submitted project on Vocareum's workspace

# Testing

Testing information can be found in Tests.md in the project root directory.

# Class Information

## UserRole
An enum that represents all the possible roles users have in the application.

Relationship to other classes: All users need to have a defined role in the application to take any actions.

## Runner
The driver class that lets a user interact with the application from start to finish.
- Allow a user to create an account, log into an existing account, or exit the application
- Prevents the user from creating an account if they don't provide an email, password, or a role
- Allows the user to take specific actions based on whether they are a customer or a seller
- Users can edit their account information at any time when interacting with the application and also sign out
- Users are prevented from logging with a non-existent account or creating an account with credentials that already exist in the main users.csv database

Relationship to other classes: The functionality of all the other classes is integrated into this class.

## Dashboard
Class that encompasses functionality for customers to view store and seller information, and for sellers to view statistics for stores and products.
- Sellers can view a list of customers with number of items purchased and total spending and a list of products with number of sales and total revenue.
- Sellers can sort the dashboard in alphabetical ascending and reverse order as well as numerically ascending and reverse order for all data
- Customers can view store and seller information which include list of stores by number of products sold and list of stores with products purchased by that customer.
- Customers can sort the dashboard in alphabetical ascending and reverse order as well as numerically ascending and reverse order
- Dashboard has implemented the extra functionality of displaying the total revenue for each type of dashboard.
(Ex: Sellers can view how much a customer has spent in total when viewing customers.)
Relationship to other classes: Dashboard contains four static methods that generate a list that can be printed to display a database. It also contains a method to display the resulting data to the terminal. Other classes call Dashboard's methods to generate the data required to display a dashboard.

## Database
Class that handles all database access and modification functionality related to the application.
- Retrieve users from the users.csv file
- Add a new entry to any of the databases
- Remove an existing entry from any of the databases
- Modify an existing entry in any of the databases
- Check if an entry exists in any of the databases
- Retrive entries that contain a specific search parameter from any of the databases
- Retrieve all the entries from any of the databases
- Update the contents of any of the databases in response to an addition, deletion, or modification

Relationship to other classes: Interacts with all other classes to retrieve, add, delete, and modify entries in each of the databases.

## User
Superclass that represents the characteristics associated with all users in the application. When a new user is created, they are assigned a unique 7-digit ID which also has a marker associated with it to signify if the current user is a seller or a customer.
- Getters for retrieving all the users' attributes(Identification Number, Email, Password, Role)
- Setters for the customer's email and password for account modification
    - Functionality to check if the new email that the user wants to modify their current email to is already associated with   an existing account in the users.csv database
- Initialize a newly created user with a new unique ID
- Initialize an already existing user when they log back into the application with their identification number, email, password, and role
- Delete account and all corresponding database entries

Relationship to other classes: Users need to exist in order to be able to interact with the application.

## Customer
A subclass of user that encompasses all the details and permissions associated with customers in the application.
- Search for specific products in the marketplace by name, store, or description
- Sort the products in the marketplace by price or quantity available
- Purchase items from product page and review history of previously purchased items
- Select a specific product to be taken to the product's page
- Export file with purchase history
- Add products from different stores to their shopping cart
- Remove products from shopping cart

Relationship to other classes: Related to the product and store class since it involves shopping cart and purchase history management.

## Seller
A subclass of user that encompasses all the details and permissions associated with sellers in the application.
- Retrieve all the stores associated with the seller
- Create a new store
- Delete an existing store
- Modify the name of an existing store
- Create a new product in any of the existing stores
- Edit a product's name, quantity, description, or price in any of the existing stores
- Delete a product from any of the existing stores
- View list of sales by store that includes customer information, product details, items purchased, and revenues
- Import a CSV file containing product information into any of the existing stores
- Export products from any of the existing stores to a CSV file
- View customer shopping carts that includes customer information, product details, and estimated costs

Relationship to other classes: Related to the store and product classes since it involves store and product management.

## Store
Class that represents an individual store that belongs to a seller in the application. When a new store is created, it is assigned a unique 7-digit ID which also has a marker associated with it to signify a store.
- Retrieve all the products associated with the given store
- Getter's for retrieving all the store's attributes(Identification number, Name)
- Initialize a newly created store with a new unique ID
- Initialize an already existing store with its associated identification number and name

Relationship to other classes: Related to the seller and product class since it represents a store containing various products that belongs to a seller 

## Product
Class that represents an individual product available in a seller's store. When a new product is created, it is assigned a unique 7-digit ID which also has a marker associated with it to signify a product.
- Getter's for retrieving all the product's attributes(Identification number, Name, Available Quantity, Price, Description)
- Initialize a newly created product with a new unique ID
- Initialize an already existing product with its associated identification number, name, quantity, price, and description

Relationship to other classes: Related to the store class since it represents an entity that exists in a given store.

## CreateDatabaseData
Class that handles data pre-population in all the CSV files for purposes of testing.
- Clear all data in existing CSV files
- Re-populate data in relevant CSV files

Relationship to other classes: The test cases written in the TestDatabase and the DashboardTest classes are constructed around the data generated by this class.

## DashboardTest
Class that handles all testing related to the functionalities implemented in the Dashboard class.
- Includes tests to verify that all the required functionalities are working as expected
- Includes tests to verify that no methods crash or throw exceptions when receiving invalid input.

Relationship to other classes: Tests the functionalities in the Dashboard class.

## CustomerTest
Class that handles all testing related to the functionalities implemented in the Customer class.
- Includes tests to verify that all the required functionalities are working as expected
- Includes tests to verify that no methods crash or throw exceptions when receiving invalid input.

Relationship to other classes: Tests the functionalities in the Customer class.

## StoreTest
Class that handles all testing related to the functionalities implemented in the Store class.
- Includes tests to verify that all the required functionalities are working as expected
- Includes tests to verify that no methods crash or throw exceptions when receiving invalid input.

Relationship to other classes: Tests the functionalities in the Store class.

## UserTest
Class that handles all testing related to the functionalities implemented in the User class.
- Includes tests to verify that all the required functionalities are working as expected
- Includes tests to verify that no methods crash or throw exceptions when receiving invalid input.

Relationship to other classes: Tests the functionalities in the User class.

## DatabaseTest
Class that handles all testing related to the functionalities implemented in the Database class.
- Includes tests to verify that all the required functionalities are working as expected
- Includes tests to verify that no methods crash or throw exceptions when receiving invalid input.

Relationship to other classes: Tests the functionalities in the Database class.

## SellerTest
Class that handles all testing related to the functionalities implemented in the Seller class.
- Includes tests to verify that all the required functionalities are working as expected
- Includes tests to verify that no methods crash or throw exceptions when receiving invalid input.

Relationship to other classes: Tests the functionalities in the Seller class.
