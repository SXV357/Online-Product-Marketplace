#Testing Document

## Initial Login / Sign Up

### Test 1: Customer Account
- User clicks to sign up and create a new customer account
- For example: email "customer@gmail.com" and password "customer"
- Select the "Customer" role from the drop-down
- User logs out and closes the program
- User logs in with the same credentials
- Customer GUI is visible
- Customer logs out
- Expected result: The user can log in and see GUI
- Test Status: Passed

### Test 2: Seller Account
- User clicks to sign up and create a new seller account
- For example: email "seller@gmail.com" and password "seller"
- Select the "Seller" role from the drop-down 
- User signs out and closes the program
- User logs in with the same credentials
- Seller GUI is visible
- Seller logs out
- Expected result: The user can log in and see GUI
- Test Status: Passed

## Seller Functionality
*Log into test account with email: "test_seller@gmail.com" and password: "test"*

### Test 3: Create Store
- Seller clicks "Create Store"
- Seller enters store name (ex: "Fish Market")
- Information prompt states success message
- Program returns to the main menu
- Fish Market exists in the "stores.csv" database
- Expected result: The seller can create a store
- Test Status: Passed

### Test 4: Edit Store
- Seller clicks "Edit Store"
- Seller uses drop-down menu and clicks "Fish Market"
- Seller renames store to "Stop & Shop"
- Information prompt states success message
- Program returns to the main menu
- "Fish Market" changed to "Stop & Shop" in the "stores.csv" database
- Expected result: The seller can edit a store's name
- Test Status: Passed

### Test 5: Add Product
- Seller clicks "Add Product"
- Seller uses drop-down menu and clicks "Stop & Shop"
- Seller names the product "Oreos"
- Seller sets 20 units of the product for sale
- Seller sets the price of the product as "6.99"
- Seller sets the description as "A tasty treat"
- Information prompt states success message
- Program returns to the main menu
- "Oreos" added in the "products.csv" database
- Expected result: The seller can add a product to a store
- Test Status: Passed

### Test 6: Edit Product
- Seller clicks "Edit Product"
- Seller uses drop-down menu and selects "Stop & Shop"
- Seller uses drop-down menu and selects "Oreos"
- Seller uses drop-down menu and selects "Name"
- Seller renames the product "Oreos" to "Biscuits"
- Information prompt states success message
- Program returns to the main menu
- "Oreos" changed to "Biscuits" in the "products.csv" database
- Expected result: The seller can edit a product in a store
- Test Status: Passed

### Test 7: Import Products
- Seller clicks "Import Products"
- Seller types "ProductsToImport.csv"
- Seller selects "Stop & Shop"
- Information prompt states success message
- Program returns to the main menu
- Products added to store in "products.csv"
- Expected result: The seller can import products into a store
- Test Status: Passed

### Test 8: Delete Products
- Seller clicks "Delete Products"
- Seller uses drop-down menu and selects "Stop & Shop"
- Seller uses drop-down menu and selects "Pasta"
- Information prompt states success message
- Program returns to the main menu
- Product "Pasta" removed from the store "Stop & Shop" in "products.csv"
- Expected result: The seller can remove products from a store
- Test Status: Passed

### Test 9: Export Products
- Seller clicks "Export Products"
- Seller uses drop-down menu and selects "Stop & Shop"
- Information prompt states success message
- Program returns to the main menu
- File path "/exportProducts/"Stop & Shop.csv" is created with this given store's products
Expected result: The seller can remove products from a store
Test Status: Passed

### Test 10: View Sales by Store
- Seller clicks "View Sales by Store"
- Seller can view all stores with the products purchased by customers
- Seller clicks "Ok" and program returns to main menu
- Expected result: The seller can view stores by sales
- Test Status: Passed

### Test 11: View Customer Shopping Carts
- Seller clicks "View Customer Shopping Carts"
- Seller can view all the their items that are currently in customer's shopping carts
- Seller clicks "Ok" and program returns to main menu
Expected result: The seller can view their customer's shopping carts
Test Status: Passed

### Test 12: View Customer Dashboard
- Seller clicks "View Customer Dashboard"
- Seller uses drop-down menu and selects "Customer Email"
- Seller uses drop-down menu and selects "Ascending"
- Seller can view all customers with their emails in alphabetical ascending order
- Seller can exit out and return to main menu
- Expected result: The seller can view all customers in a sorted manner
- Test Status: Passed

### Test 13: View Products Dashboard
- Seller clicks "View Customer Dashboard"
- Seller uses drop-down menu and selects "Product Name"
- Seller uses drop-down menu and selects "Ascending"
- Seller can view all their products' names in alphabetical ascending order
- Seller can exit out and return to main menu
- Expected result: The seller can view their products' names in a sorted manner
- Test Status: Passed

### Test 14: Delete Store
- Seller clicks "Delete Store"
- Seller uses drop-down menu and selects "Stop & Shop"
- Information prompt states success message
- Expected result: The seller can delete a store
- Test Status: Passed

## Customer Functionality
*Sign out of the previous seller and sign into the customer account with email: "test_customer@gmail.com" and password: "test"*

### Test 15: Add Item to cart
- Customer clicks "View All Products" button
- Select "Biscuits" from the drop-down menu of all products
- GUI displays product information and a question panel regarding purchase
- Customer selects "Yes"
- Customer writes "5" for purchase quantity
- Information panel displays a success message
- Expected result: The customer can add items to their cart
- Test Status: Passed

### Test 16: Search For Product
- Customer clicks "Search For Product" button
- Customer writes "Biscuits" for product search
- Customer selects "Biscuits" from the drop-down menu of all products
- GUI displays product information
- Customer selects "Ok"
- Program returns to the main menu
- Expected result: The customer search for products
- Test Status: Passed

### Test 17: Sort Product
- Customer clicks "Sort Products" button
- GUI appears with all products and "Sort by Price" and "Sort By Quantity" buttons
- Customer can click "Sort by Price for ascending products by price
- Customer can click "Sort by Quantity for ascending products by quantity available
- Customer can exit out to return to the main menu
- Expected result: The customer sort products
- Test Status: Passed

### Test 18: View Shopping Cart
- Customer clicks "View Shopping Cart" button
- Information panel displays the "Biscuits" item in the cart with relevant information
- Expected result: The customer can purchase items
- Test Status: Passed

### Test 19: Checkout Items
- Customer clicks "Checkout Items" button
- Information panel displays a success message
- The customer's entries in "shoppingCarts.csv" are cleared, the "products.csv" quantity is updated, and "shoppinghistories.csv" is updated
- Expected result: The customer can purchase items
- Test Status: Passed

### Test 20: View Purchase History
- Customer clicks "View Purchase History" button
- Information panel displays the "Biscuits" item in the cart with relevant information (Same format as View Shopping Cart)
- Expected result: The customer can view purchase history
- Test Status: Passed

### Test 21: View Stores Dashboard
- Customer clicks "View Stores Dashboard" button
- Customer can click "Sort Name" from drop-down menu
- Customer can click "Ascending" from drop-down menu
- Information panel displays the all the stores with product sales and revenue
- Customer can exit out to main menu
- Expected result: The customer can view stores page
- Test Status: Passed

### Test 22: View Purchases Dashboard
- Customer clicks "View Purchases Dashboard" button
- Customer can click "Sort Name" from drop-down menu
- Customer can click "Ascending" from drop-down menu
- Information panel displays the all the stores the customer has purchased products and the money spent
- Customer can exit out to main menu
- Expected result: The customer can view their personal purchase dashboard
- Test Status: Passed

### Test 23: Remove Item From Shopping Cart
- Customer clicks "View All Products" button
- Select "Spicy Chicken Wings" from the drop-down menu of all products
- GUI displays product information and a question panel regarding purchase
- Customer selects "Yes"
- Customer writes "5" for purchase quantity
- Information panel displays a success message
- Customer clicks "Remove Item From Shopping Cart" button
- Customer can click "Spicy Chicken Wings" from drop-down menu
- Information panel displays a success message
- Expected result: The customer can remove items
- Test Status: Passed

## User Account Testing
*Stay signed into example customer account*

### Test 24: Edit Email
- Customer clicks "Edit Email" button
- Customer can change their email to "CS180@gmail.com"
- Information panel displays a success message
- Log out of customer account
- Log in with email: "CS180@gmail.com" and password: "test"
- The main menu displays new email at the top of the panel
- Expected result: The customer can change their email
- Test Status: Passed

### Test 25: Edit Password
- Customer clicks "Edit Password" button
- Customer can change their password to "yes"
- Information panel displays a success message
- Log out of customer account
- Log in successfully with email: "CS180@gmail.com" and password: "yes"
- Expected result: The customer can change their password
- Test Status: Passed

### Test 26: Delete Account
- Customer clicks "Delete Account" button
- Customer clicks "yes" to confirm
- Program returns to main log in menu
- Log in with email: "CS180@gmail.com" and password: "yes"
- Error message is displayed
- Expected result: The customer can delete their account
- Test Status: Passed

Expected result: User is able to log in

Expected result:
Result:
### Test 2: Concurrent customer purchases
- Customer 1 logs in
- Customer 2 logs in
- Customer 1 adds all of a product to cart
- Customer 2 adds one of that product to cart and checks out
- Customer 1 attempts to check out
Expected result: Customer 2 sucessfully checks out. Customer 1 gets an error message and the product with too much quantity is removed.
Result: as expected

### Test 22: Concurrent customers purchases while another checks-out
- Customer 1 logs in
- Customer 2 logs in
- Customer 1 adds all of product 1 to cart, does not finish adding to cart
- Customer 2 adds one of product 1to cart and checks out
- Customer 1 attempts to check out
Expected result: Customer 2 sucessfully checks out. Customer 1 attempts to add to cart with a previously valid value and recieves an error.
Result: as expected

### Test 23: Concurrent customer purchases multiple items
- Customer 1 logs in
- Customer 2 logs in
- Customer 1 adds all of product 1 to cart
- Customer 1 adds all of product 2 to car
- Customer 2 adds one of product 1 and product 2 to cart and checks out
- Customer 1 attempts to check out
Expected result: Customer 2 sucessfully checks out. Customer 1 gets an error message and the first product with too much quantity is removed. The second product remains in cart
Result: as expected

### Test 24
- Sign in with a non-existent account with valid format
Expected result: user recieves error message and is sent back to login
Result: as expected

### Test 25
- Sign in with a invalid format email
Expected result: user recieves error message and is sent back to login
Result: as expected

### Test 26
- Sign in without selecting a user type from the drop down menu
Expected result: user recieves error message and is sent back to login
Result: as expected
