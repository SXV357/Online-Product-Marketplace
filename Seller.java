import java.util.ArrayList;

/**
 * Project 4 - Seller.java
 * 
 * Class to represent the permissions and details associated with a seller
 * 
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 * 
 * @version November 6, 2023
 */
public class Seller extends User{

    private static Database db = new Database();
    private ArrayList<Store> stores;

    public Seller(String email, String password, UserRole role) {
        super(email, password, role);
        this.stores = new ArrayList<Store>();
    }

    public ArrayList<Store> getStores() {
        return this.stores;
    }

    // 1. Create a new store
    public boolean createNewStore(String storeName) {
        return false;
    }

    // 2. Delete an existing store(in its entirety)
    public boolean deleteStore(String storeID) {
        return false;
    }

    // 3. Modify the name of an existing store
    public boolean modifyStoreName(String storeID, String prevStoreName, String newStoreName) {
        return false;
    }

    // 4. Add a new product to any store of their choosing
    public boolean createNewProduct(String storeID, String productName, String productDescription, String availableQuantity, String price) {
        return false;
    }

    // 5. Edit an existing product in any store of their choosing
    public boolean editProduct(String productID, String editParam) {
        // the edit param determines which attribute of the product they want to edit
        return false;
    }

    // 6. Delete an existing product in any store of their choosing
    public boolean deleteProduct(String productID) {
        return false;
    }

    // 7. View list of sales by store(includes customer information and revenues from sale - would make use of the purchaseHistories.csv file for information retrieval)
    public void viewStoreSales() {

    }
    
    // 8. import products into any store of their choosing(would just create new entries in the products.csv file)
    public void importProducts(String storeName) {

    }

    // 9. export the products associated with any of their existing stores
    public void exportProducts(String storeName) {

    }

    // 10. View number of products currently in customers' shopping carts(includes store information and details associated with the products - would make use of the shoppingCarts.csv file for information retrieval)
    public void viewCustomerShoppingCarts() {
        // only for their stores and not for other sellers' stores
    }

}