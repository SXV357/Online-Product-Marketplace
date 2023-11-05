import java.util.ArrayList;
/**
 * Project 4 - Store.java
 *
 * Class that represents an individual store that belongs to a seller in the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic Miller, Oliver Long
 *
 * @version November 2, 2023
 */
public class Store {

    private String name;
    private ArrayList <Product> products;

    public Store(String name) {
        this.name = name;
        products = null;
    }

    public String getName() {
        return name;
    }

    public void  setName(String name) {
        this.name = name;
    }

    public void setProducts(ArrayList <Product> products) {
        this.products = products;
    }

    public ArrayList <Product> getProducts() {
        return products;
    }

    public boolean deleteProduct(String productName) {
        int e = -1;
        for (int i = 0; i < products.size(); i++){
            if (products.get(i).getName().equals(productName)){
                e = i;
            }
        }

        if (e > 0) {
            products.remove(e);
            return true;
        }
        return false;
    }

    public boolean addProduct(Product newProduct) {
        for (Product e: products) {
            if(e.getName().equals(newProduct.getName())) {
                return false;
            }
        }
        products.add(newProduct);
        return true;
    }
}
