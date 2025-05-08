# CS-180-Project5 - Online Product Marketplace

# Note
For further in-depth technical documentation including system architecture details, please visit this link: [![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/SXV357/Online-Product-Marketplace)

# Instructions
From the root directory of the project, compile Server.java and execute it, then compile InitialClient.java and execute it. To run multiple instances of the program, keep one Server.java instance running and run multiple instances of InitialClient.java. To access the main application, you will first need to enter the host to connect to, whether it is another device or local to the computer. Then you will need to create a new account or log into an existing account, which will then redirect you to the corresponding page from where you can take a variety of actions.

# Project Submission Details

Vocareum - Submitted by Shreyas Viswanathan

Report - Submitted by Shafer Hofmann

Presentation - Submitted by Nathan Miller

# Testing

Detailed testing information can be found in Tests.md in the project root directory. The test cases written for the major classes in Project 4 can be located in the PJ4 Testing directory, but commenting out the classes and running them will throw errors since several major refactors took place in the classes for which the tests were written, such as modifying return types, parameters, and even making it such that they're all throwing custom exceptions and the tests were previously not designed to handle those new changes since they hadn't been made previously. However, proper testing has been done application-wide to verify that all customer and seller-related behaviors are working as expected.

NOTE: A limitation of this application is that it allows the same user to be logged in on two separate or multiple clients. As such, when choosing to delete the account from one of the clients, it doesn't affect the other one, similarly with signing out. The other client will remain unaffected by the changes from a visual perspective, but may crash if certain actions are taken, which holds true only if the account was deleted.

# Class Information

## SellerGUI
The interface associated with a seller in the application.
- Sellers can edit account details, delete their account, and sign out
- Sellers can create, modify, and delete stores
- Sellers can create, modify, and delete products in any of their stores
- Sellers can view information about customers and stores
- Sellers can import products into any of their stores as well as export products from any of their stores

Relationship to other classes: When a user selects that they would like to be a seller from the SignUpGUI or when they log back in with a seller account through the LoginGUI, this GUI is invoked through the SellerClient class.

## SellerClient
Class that handles a seller's connection and requests to the database server.
- Contains functionality to make requests to the server for the various actions that sellers choose to take
- Sends over data containing the action to be taken along with any related data passed on from the SellerGUI
- Retrives a status from the server to indicate whether the action was a success, along with either a success message or any output data, or whether the action was a failure, along with a descriptive error message

Relationship to other classes: It directly communicated with ServerThread, sending data received for various actions from the SellerGUI and then subsequently retrieving a response to be sent back to the SellerGUI for further processing.

## CustomerGUI
The interface associated with a customer in the application.
- Customers can edit account details, delete their account, and sign out
- Customers can view marketplace products, select a product to view more information, then add it to their cart
- Customers can sort the marketplace by price or quantity and can search for products based on their name, price, or description
- Customers can view their purchase history and export their purchase history
- Customers can view information about products and their purchases

Relationship to other classes: When a user selects that they would like to be a customer from the SignUpGUI or when they log back in with a customer account through the LoginGUI, this GUI is invoked through the CustomerClient class.

## CustomerClient
Class that handles a customer's connection and requests to the database server.
- Contains functionality to make requests to the server for the various actions that customers choose to take
- Sends over data containing the action to be taken along with any related data passed on from the CustomerGUI
- Retrives a status from the server to indicate whether the action was a success, along with either a success message or any output data, or whether the action was a failure, along with a descriptive error message

Relationship to other classes: It directly communicated with ServerThread, sending data received for various actions from the CustomerGUI and then subsequently retrieving a response to be sent back to the CustomerGUI for further processing.

## DisplayDashboardGUI
The interface associated with displaying dashboards for both customers and sellers.
- Constructs a new GUI window with data received from either the SellerGUI or the CustomerGUI, the data which was originally received from the server through either the SellerClient or the CustomerClient

Relationship to other classes: It receives data regarding what type of dashboard to display and what content needs to be displayed from both the SellerGUI and CustomerGUI classes.

## InitialClient
The driver class that users can execute to launch the main application.
- Process a user logging back into the application
- Process a user creating either a customer account or a seller account
- Initialize a socket, create the corresponding input and output streams, then launch the window that allows users to sign up or log in

Relationship to other classes: It contains functionality that is used by the LoginGUI and SignUp GUI classes and also contains the streams that all other clients in the application, such as the SellerClient and CustomerClient utilize to write and receive data from the server

## LoginGUI
Class that constructs the GUI for a user that is logging in
- Constructs the GUI containing fields to enter the email as well as the password, including the button to log in
- Includes a button that the user can select to return back to the main menu

Relationship to other classes: Interacts with the InitialClient class to handle users logging back into the application.

## SignupGUI
The interface associated with user signup.
- Constructs the GUI containing fields to enter the email, password, including a menu to select which role they'd like to take, including the button to sign up
- Includes a button that the user can select to return back to the main menu

Relationship to other classes: Interacts with the InitialClient class to handle users creating a new customer account or seller account.

## Server
Class that handles server host and port connection and thread allocation.
- Initializes the port number that communication will take place on
- Constructs a server socket that waits for a client to connect to it before moving on to creating a new thread to handle that client

Relationship to other classes: It is the backbone of the entire application, constructing threads to handle a new seller client or a customer client or as many clients as needed as a new connection is established.

## ServerThread
Class that handles database interactions with client.
- Handles processing of data received from the initial client for creating a new account and logging an existing user back in
- Handles processing of data received from the customer client as well as sending back a confirmation or error message
- Handles processing of data received from the seller client as well as sending back a confirmation or error message

Relationship to other classes: Receives data sent by the CustomerGUI and SellerGUI classes from the CustomerClient and SellerClient classes respectively and processes it.

## CustomerException
A custom exception class to deal with exceptions thrown as a result of actions that customers take.
- Contains a constructor which when invoked with the error message passed in, throws a custom exception

Relationship to other classes: This exception is thrown by most methods in the Customer class as appropriate to indicate that an error occurred when taking a certain action.

## SellerException
A custom exception class to deal with exceptions thrown as a result of actions that sellers take.
- Contains a constructor which when invoked with the error message passed in, throws a custom exception

Relationship to other classes: This exception is thrown by most methods in the Seller class as appropriate to indicate that an error occurred when taking a certain action.

## ErrorMessageGUI
The interface associated with displaying error messages caused due to actions taken by both customers and sellers.
- Contains a constructor which when invoked with the error message passed in, displays an error dialog with the corresponding message

Relationship to other classes: This GUI is invoked in several places in both the CustomerGUI and SellerGUI clases with error messages returned back from the server as a result of Seller exceptions or Customer exceptions occurring.

## SortProductsGUI
The interface associated with a customer wanting to sort products in the application.
- Constructs the GUI that allows the user to initially browse all the products in the marketplace
- Has options for the user to elect to click one of two buttons, to either sort the marketplace by price or quantity
- Clicking one of the buttons will update the data in the dropdown menu which the user can then view

Relationship to other classes: This GUI is invoked from the CustomerGUI and receives information sent from the Server and received through the CustomerClient.

## UserRole
An enum that represents all the possible roles users have in the application.
- Defines 2 roles that users can have in the application, Customers and Sellers

Relationship to other classes: All users need to have a defined role in the application to take any actions.

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
