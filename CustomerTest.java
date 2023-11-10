public class CustomerTest {
    public static void main(String[] args) {
        Customer c = new Customer("nathanmiller@gmail.com", "1212", UserRole.CUSTOMER);

        System.out.println(c.getAllProducts());
        System.out.println(c.getProductInfo(0));
        // c.addToCart(0, 10);
        // System.out.println(c.getCart());
        // System.out.println(c.getShoppingHistory());
        // c.purchaseItems();
        // System.out.println(c.getShoppingHistory());
        System.out.println(c.sortStores("price"));
        System.out.println(c.sortStores("quantity"));
        //System.out.println(c.sortStores("purchased"));
        System.out.println(c.searchProducts("breakfast"));

    }

    public static void testGetProductInfo(Customer c) {
        //Out of Bounds -> Returns "Invalid Product"
        System.out.println(c.getProductInfo(0));
        //Prints out the first product in shopping cart
        System.out.println(c.getProductInfo(1));
    }

    public static void testAddToCart(Customer c) {
        boolean quantityBelowBounds = c.addToCart(0, -1);
        boolean quantityAboveBounds = c.addToCart(0, 100);

        boolean indexBelowBounds = c.addToCart(-1, 1);
        boolean indexAboveBounds = c.addToCart(100, 1);

        assert !quantityAboveBounds & !quantityBelowBounds & !indexAboveBounds & !indexBelowBounds;
    }

    public static void testRemoveFromCart(Customer c) {
        boolean indexBelowBounds = c.removeFromCart(-1);
        boolean indexAboveBounds = c.removeFromCart(100);

        assert !indexAboveBounds & !indexBelowBounds;
    }
}
