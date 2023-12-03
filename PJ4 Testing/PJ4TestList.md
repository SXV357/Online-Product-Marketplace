## Database
### Note: The tests for the Database class can be found in the root directory in the TestDatabase.java class.
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
### Note: The tests for the Seller class can be found in the root directory in the TestStoreManagement.java class. The test file for a seller to import products from into one of their existing stores is located in the root directory with the name <ins>ProductsToImport.csv</ins>.
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

## Customer
### Note: The junit tests for the Customer subclass are located in "CustomerTest.java". Each test case will report its results with either a customized error message showing the program encountered a problem or "[methodName] ... OK" indicating the test's success. The test cases should be run sequentially because subsequent tests may rely on updated data from previous cases. The following are all cases that are tested:
1. When retrieving product information that is not a valid index in the database, the program returns "Invalid Product."
2. When retrieving the information of multiple distinct products, each returns a correct and properly formatted string of the product's information.
3. When adding a product to the cart with a quantity that exceeds the available supply on the marketplace, the program returns false.
4. When adding a product to the cart with an index that does not exist in the marketplace, the program returns false.
5. When adding a product to the cart, the program properly formats and updates the user's shopping cart
6. When adding a product that already exists in the shopping cart, the program modifies the old entry with a summation of the quantity and price
7. When adding a different product, the cart adds a properly formatted entry
8. When retrieving a customer's cart information, the cart is returned as expected
9. When retrieving a customer's empty cart, the program returns "No Products Available"
10. When removing a product from the cart that is out of its index, the program returns false
11. When the customer removes two different products from the cart, the exact products are removed
12. When the customer retrieves their purchase history and it contains products, the correct purchase history is returned
13. When a customer has an empty purchase history, the program returns "No Products Available"
14. When the customer retrieves the marketplace that has products, it returns a properly formatted list
15. When the customer retrieves an empty marketplace, it returns "No Products Available"
16. When the customer purchases items, the program correctly adds the item to the purchase history, removes the entry from the cart, and updates the quantity of the product on the marketplace. If there is a duplicate product in the purchase history, the quantity and purchase price will be summed.
17. When the customer purchases an item that is out of stock, returns an error message, removes the item from the cart, does not update the product page quantity, and continues purchasing items.
18. When the customer sorts the stores with an invalid tag, returns "Invalid Search!"
19. When the customer sorts the stores by price, the bubble sort returns a correctly formatted list from least to highest prices
20. When the customer sorts the stores by quantity, the bubble sort returns a correctly formatted list from least to highest quantities
21. When the customer searches the product information by an unsearchable query, returns "Query has returned no results!"
22. When the customer searches the product by product name, store name, or description, the program returns stores and products with those distinct attributes
23. When the customer exports their purchase history, a file is created under the "exportedHistory" directory with a "[email].csv" file with their past purchases
24. When the customer exports their purchase history and they have made no purchases, return false and no file is created.

## Dashboard 
### Note: The tests for the Dashboard class can be found in the root directory in the TestDashboard.java class. Tests were performed by checking that the output of each function equaled the expected output for the given dataset. 3 tests were performed for each method: sort descending by name, sort ascending by quantity, and sort ascending by total revenue. Invalid input testing was not performed as dashboard is an internal class only meant to be called by other classes. For the `customerGetPersonalPurchasesDashboard()` method, testing was also done on a different customer for each test case.
1. The method SellerGetCustomersDashboard was tested to ensure it returned a list of customers and the number of products they have bought, as well as their total spending.
2. The method SellerGetProductsDashboard was tested to ensure it returned a list of products and the number of sales, as well as their total revenue.
3. The method customerGetStoresDashboard was tested to ensure it returned a list of stores and the number of products they have sold, as well as their total revenue.
4. The method customerGetPersonalPurchasesDashboard was tested to ensure it returned a list of stores and the number of products that user bought from that store, as well as their total spending at that strore.

# Store
### Note:  The tests for the Store class can be found in the root directory in the TestStore.java class
1. When retrieving the products associated with a store that doesn't contain any products yet, a null value is returned as expected
2. When retrieving the products associated with a store that contains several products, the arraylist containing all the associated product objects are correctly returned

# User
### Note: The tests for the User class can be found in the root directory in the TestUser.java class
1. When a user is modifying their email and provides a new value that isn't already associated with an existing account, then the modification is successful
2. When a user modifies their email to a value associated with an existing account, a descriptive error message is displayed and the email remains unchanged
3. When a customer deletes their account, the users.csv database is updated accordingly, and the customer's associated shopping carts and purchase histories are also deleted successfully
4. When a seller deletes their account, the users.csv database is updated accordingly, all stores and products associated with the seller are deleted and all customers who have added products from that specific seller's store(s) to their shopping carts have the entries removed accordingly
