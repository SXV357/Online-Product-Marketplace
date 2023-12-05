### Test 1
- User creates a new account with a valid email
- User logs out and closes the program
- User logs in with the same credentials
Expected result: User is able to log in
Result: TODO

Expected result:
Result:
### Test 2
- Customer 1 logs in
- Customer 2 logs in
- Customer 1 adds all of a product to cart
- Customer 2 adds one of that product to cart and checks out
- Customer 1 attempts to check out
Expected result: Customer 2 sucessfully checks out. Customer 1 gets an error message and the product with too much quantity is removed.
Result: as expected

### Test 3
- Customer 1 logs in
- Customer 2 logs in
- Customer 1 adds all of product 1 to cart
- Customer 1 adds all of product 2 to car
- Customer 2 adds one of product 1 and product 2 to cart and checks out
- Customer 1 attempts to check out
Expected result: Customer 2 sucessfully checks out. Customer 1 gets an error message and the first product with too much quantity is removed. The second product remains in cart
Result: as expected

### Test 4
- Sign in with a non-existent account with valid format
Expected result: user recieves error message and is sent back to login
Result: as expected

### Test 5
- Sign in with a invalid format email
Expected result: user recieves error message and is sent back to login
Result: as expected

### Test 6
- Sign in without selecting a user type from the drop down menu
Expected result: user recieves error message and is sent back to login
Result: as expected