public class CustomerTest {
    public static void main(String[] args) {
        Customer c;
        c = new Customer("nathanmiller@gmail.com", "1212", UserRole.CUSTOMER);

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
        System.out.println(c.getProductInfo(0));
    }
}
