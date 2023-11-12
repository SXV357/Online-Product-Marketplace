# CS-180-Project4 - Product Marketplace

# Instructions
From the root directory of the project, compile Runner.java and then execute it which will launch the main application.

# Project Submission Details

Shafer Anthony Hofmann - Submitted Report on Brightspace

Shreyas Viswanathan - Submitted project on Vocareum's workspace

# Class Information

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
- Initialize a newly created user with a new unique ID
- Initialize an already existing user when they log back into the application with their identification number, email, password, and role

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

## UserRole
An enum that represents all the possible roles users have in the application.

Relationship to other classes: All users need to have a defined role in the application to take any actions.

# Testing

## Database
### Note: The tests for the Database class can be found in the root directory in the TestDatabase.java class. Before running all tests, there are two methods that have been commented out which need to be executed since they are responsible for generating test data that the test cases have been designed for. There is no need for running each test sequentially, as all can be run at once.
1. Given the name of the database(users.csv, stores.csv, products.csv, shoppingCarts.csv, and purchaseHistories.csv), the correct headers associated with the files are returned
2. Given the name of the database, the test to check whether or not a given index is within bounds or not for how many ever columns exist returns the correct value
3. When adding an entry for the first time to each of the databases, the respective CSVs get populated accordingly with the new information
4. When adding a duplicate entry to each of the databases, the contents of the CSVs remain unchanged and no duplicate entries are found
5. When modifying an existing entry in each of the databases, the respective entries are being updated correctly and the database contents are updated with the latest values
6. When modifying a non-existing entry in each of the databases, the contents of the CSVs remain unchanged
7. When removing an existing entry in each of the databases, the corresponding entries are removed and the database contents are updated accordingly
8. When removing a non-existing entry in each of the databases, the contents of the CSVs remain unchanged
9. When checking whether an existing ID corresponds to an entry in the users.csv, stores.csv, and products.csv file, a truthy value is returned as expected
10. When checking whether a non-existing ID corresponds to an entry in the above mentioned files, a falsy value is returned as expected
11. When retrieving a user from the users.csv file with the same email and password as an existing entry, the actual user entry is returned as expected
12. When retrieving a user from the users.csv file with an existing email but non-existing password, a non-existing email but existing password, and non-existing email and non-existing password, a null value is returned as expected
13. When checking whether an existing entry exists in each of the database files, a truthy value is returned and when checking whether a non-existent entry exists in each of the databases, a falsy value is returned as expected
14. When checking whether an existing or non-existing entry exists in database files apart from users.csv, stores.csv, products.csv, shoppingCarts.csv, and purchaseHistories.csv, a falsy value is returned as expected
15. The correct entries are returned when retrieving the contents from each of the existent databases, including if the contents of one is empty
16. No entries are returned when retrieving the contents from non-existent database files.
17. When searching for a value in a given column in one of the existent databases, all entries that contain that value are returned
18. When searching for a value in a given column and the column index is out of bounds, or the index is in bounds but the search parameter doesn't exist in the given column in any of the entries, or the provided file name within which to search the value for doesn't exist, then no entries are returned

## Seller
### Note: The tests for the Seller class can be found in the root directory in the TestStoreManagement.java class. Before running all tests, there is a method commented out which needs to be executed since it is responsible for clearing out any existing data in any of the databases based on which the test cases are designed. The test file for a seller to import products from into one of their existing stores is located in the root directory with the name import.csv. There is no need for running each test sequentially, as all can be run at once.
1. When retrieving a list of stores for a seller that hasn't created any stores yet, a null value is returned as expected
2. When creating a new store associated with the seller, the contents of stores.csv are updated correctly
3. When creating a store with the same name as an existing one, the store is not created and the contents of stores.csv remain unchanged
4. When deleting a store that doesn't exist, the contents of the stores.csv file remain unchanged
5. When deleting a store that exists, the corresponding store is deleted from users.csv and the file's contents are updated accordingly
6. When modifying the name of an existing store, the corresponding entry is updated with the new name in the database file
7. When modifying a store's name with either one or both values being an empty string or a null value, or when modifying the name of a store that doesn't exist, or when modifying the name of a store that exists but the new name provided is already associated with an existing store, the contents of the stores.csv file remain unchanged and a falsy value is returned as expected
8. When creating a product in a store that exists, the contents of the products.csv file are updated with the new entry and the contents of the stores.csv file are updated for the respective store with regards to how many items it contains
9. When creating a product in a store that exists with the product's name being unique and either one or multiple of the product's attributes being invalid such as the price and quantity being negative, the contents of the products.csv file remain unchanged and a falsy value is returned as expected
10. When creating a product in a store that is non-existent, the contents of the products.csv file remain unchanged and a falsy value is returned as expected
11. When creating a product in a store that is existent but the new product's name matches the name of an existing product within the same store, the contents of the products.csv file remain unchanged and a falsy value is returned as expected
12. When deleting an existing product in an existing store, the entry is removed from the stores.csv file as expected
13. When deleting an existing product in a non-existing store, or deleting a non-existent product from an existent store, or deleting a product that exists in a different store from a store that doesn't have any products, the contents of the products.csv file remain unchanged and a falsy value is returned as expected
14. When editing an existing product in an existing store with a valid edit parameter and valid new value, the corresponding entry is updated in the products.csv file
15. When the seller wants to edit a product in a store that doesn't exist, or the seller wants to edit something other than name, description, price, or quantity, or the store exists and contains products but a match for the given product name wasn't found, or the store exists and the product exists in the store and the edit parameter is name but the new value provided for name already corresponds to an existing product within the same store, the contents of the products.csv file remain unchanged and a falsy value is returned as expected
16. When customers have added products to their shopping carts from the seller's existing stores and when the seller is viewing customer shopping carts, all relevant details are returned such as which store the shopping cart(s) belong to, customer information as well as product information
17. When there are no stores associated with the seller, or no products associated with any of the seller's stores, or no customers have added products to shopping cart from any stores, and when the seller tries viewing customer shopping carts, they are not able to do so as expected
18. When customers have purchased items from the seller's existing stores and when the seller is viewing sales by store, all relevant details are returned such as which store the sales correspond to, customer information as well as product information
19.  When there are no stores associated with the seller, or no products associated with any of the seller's stores, or no customers have purchased items from any stores, and when the seller tries viewing sales by store, they are not able to do so as expected
20.  When the seller wants to import products from an existing file into an existing store, the contents of the products.csv file are updated correctly and the count of the number of items corresponding to the given store in stores.csv is also updated correctly
21.  When the seller tries importing products from a file that doesn't exist, or the seller tries importing products from a file that exists into a non-existent store, neither the products.csv file is updated nor the stores.csv file
22.  When the seller exports products from an existing store containing products, all the relevant products' details are written to a new file successfully
23.  When the seller exports products from a store that doesn't exist or from an existing store but one that doesn't contain any products, a new file containing the products is not created
