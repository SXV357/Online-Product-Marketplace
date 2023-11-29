import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
/**
 * Project 5 - CustomerGUI.java
 * 
 * The interface associated with a customer in the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 29, 2023
 */
public class CustomerGUI extends JComponent {

    private DisplayInformationGUI displayInfo = new DisplayInformationGUI();
    private JFrame customerFrame;
    private CustomerClient customerClient;
    private String email;
    private JLabel welcomeUserLabel;
    private JButton manageAccountButton;
    private JButton signOutButton;
    private final String[] SORT_ORDER_CHOICES = {"Ascending", "Descending"};

    // Associated with customer permissions
    private JButton viewAllProductsButton;
    private JButton searchForProductButton;
    private JButton exportPurchaseHistoryButton;
    private JButton viewStoreDashboardButton;
    private JButton viewPurchaseDashboardButton;
    private JButton checkoutItemsButton;
    private JButton removeItemFromShoppingCartButton;

    public static void main(String[] args) {
        // SwingUtilities.invokeLater(new Runnable() {
        //     @Override
        //     public void run() {
        //         new CustomerGUI(null, "");
        //     }
        // });
    }

    public void displayErrorDialog(String errorMessage) {
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
            new ErrorMessageGUI(errorMessage);
           } 
        });
    }

    public void displayDashboard(String dashboardType, JScrollPane scrollPane) {
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
            JFrame dashboardFrame = displayInfo.displayDashboard(dashboardType, scrollPane);
            dashboardFrame.setVisible(true);
           } 
        });
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewAllProductsButton) {
                // String[] viewAllProductsResult = CustomerClient.getMarketplaceProducts("GET_MARKETPLACE_PRODUCTS");
                /*
                 * if (viewAllProductsResult[0].equals("ERROR")) {
                 *      displayErrorDialog(viewAllProductsResult[1]);
                 *      return;
                 * }
                 * 
                 * String[] products = viewAllProductsResult[1].split("\n");
                 * String productChoice = (String) JOptionPane.displayInputDialog(null, "Which product\'s details would you like to view?", "Products", JOptionPane.QUESTION_MESSAGE, null, productRepresentation, productRepresentation[0])
                 * if (productChoice == null) {
                 *  return;
                 * }
                 * int productSelection = Arrays.binarySearch(products, productChoice) + 1;
                 * String[] productInfo = CustomerClient.getProductInfo(productSelection);
                 * if (productInfo[0].equals("ERROR")) {
                 *  displayErrorDialog(productInfo[1]);
                 *  return;
                 * } else {
                 *  String addToCart = JOptionPane.showInputDialog(null, "Would you like to add this item to your cart?\n" + productInfo, JOptionPane.QUESTION_MESSAGE);
                 * if (addToCart == null || addToCart.isEmpty() || addToCart.toLowerCase().equals("no")) {
                 *  return;
                 * } else if (addToCart.toLowerCase().equals("yes")) {
                 *  Integer desiredQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many would you like?", "Quantity", JOptionPane.QUESTION_MESSAGE));
                 * String[] productAddedResult = CustomerClient.addToCart(productSelection, desiredQuantity);
                 * if (productAddedResult[0].equals("SUCCESS")) {
                 *      JOptionPane.showMessageDialog(null, productAddedResult[1], "Add To Cart", JOptionPane.INFORMATION_MESSAGE);
                 * } else {
                 *      displayErrorDialog(productAddedResult[1]);
                 * }
                 * 
                 * 
                 * }
                 * 
                 * }
                 */

            } else if (e.getSource() == searchForProductButton) {
                String query = JOptionPane.showInputDialog(null, "What would you like to search for?", "Search for Product", JOptionPane.QUESTION_MESSAGE);
                // same behavior as view all products
                    // select a product -> view its details -> choose if they want to add it to cart -> specify quantity -> add to cart

            } else if (e.getSource() == exportPurchaseHistoryButton) {
                // String[] exportPurchaseHistoryResult = CustomerClient.exportPurchaseHistory("EXPORT_PURCHASE_HISTORY")
                /*
                 * if (exportPurchaseHistoryResult[0].equals("SUCCESS")) {
                 *     JOptionPane.displayMessageDialog(null, exportPurchaseHistory[1], "Export Purchase History", JOptionPane.INFORMATION_MESSAGE);
                 * } else {
                 *   displayErrorMessage(exportPurchaseHistoryResult[1]);
                 * }
                 */

            } else if (e.getSource() == viewStoreDashboardButton) {
                String[] sortChoices = {"Store name", "Number of product sales", "Total revenue"};
                String sortChoice = (String) JOptionPane.showInputDialog(null, "How would you like to sort the dashboard?", "Dashboard Sort Choice", JOptionPane.QUESTION_MESSAGE, null, sortChoices, sortChoices[0]);
                if (sortChoice == null) {
                    return;
                }
                String orderChoice = (String) JOptionPane.showInputDialog(null, "In what order would you like to sort the dashboard?", "Sorting Order", JOptionPane.QUESTION_MESSAGE, null, SORT_ORDER_CHOICES, SORT_ORDER_CHOICES[0]);
                if (orderChoice == null) {
                    return;
                }
                boolean ascending = orderChoice.equals("Ascending") ? true: false;
                int sortSelection = Arrays.binarySearch(sortChoices, sortChoice);
                // Object[] storeDashboardResult = CustomerClient.customerGetStoresDashboard(sortSelection, ascending);

                // if (storeDashboardResult[0] instanceof String && storeDashboardResult[0].equals("ERROR")) { // error was thrown
                //     String errorMessage = (String) storeDashboardResult[1];
                //     displayErrorDialog(errorMessage);
                //     return;
                // }
                // ArrayList<String> storeDashboard = (ArrayList<String>) storeDashboardResult[1];
                // Object[][] data = new Object[storeDashboard.size()][3];
                // for (int i = 0; i < storeDashboard.size(); i++) {
                //     String[] elems = storeDashboard.get(i).split(",");
                //     System.arraycopy(elems, 0, data[i], 0, elems.length);
                // }

                // JTable table = new JTable(data, sortChoices);
                // JScrollPane scrollPane = new JScrollPane(table);
                // displayDashboard("Stores", scrollPane);

            } else if (e.getSource() == viewPurchaseDashboardButton) {
                String[] sortChoices = {"Product name", "Number of products purchased", "Total spent"};
                String sortChoice = (String) JOptionPane.showInputDialog(null, "How would you like to sort the dashboard?", "Dashboard Sort Choice", JOptionPane.QUESTION_MESSAGE, null, sortChoices, sortChoices[0]);
                if (sortChoice == null) {
                    return;
                }
                String orderChoice = (String) JOptionPane.showInputDialog(null, "In what order would you like to sort the dashboard?", "Sorting Order", JOptionPane.QUESTION_MESSAGE, null, SORT_ORDER_CHOICES, SORT_ORDER_CHOICES[0]);
                if (orderChoice == null) {
                    return;
                }
                boolean ascending = orderChoice.equals("Ascending") ? true: false;
                int sortSelection = Arrays.binarySearch(sortChoices, sortChoice);
                // Object[] purchasesDashboardResult = CustomerClient.customerGetPurchasesDashboard(sortSelection, ascending, customerClient.getcustomerID);
                
                // if (purchasesDashboardResult[0] instanceof String && purchasesDashboardResult[0].equals("ERROR")) { // error was thrown
                //     String errorMessage = (String) purchasesDashboardResult[1];
                //     displayErrorDialog(errorMessage);
                //     return;
                // }
                // ArrayList<String> purchaseDashboard = (ArrayList<String>) purchasesDashboardResult[1];
                // Object[][] data = new Object[purchaseDashboard.size()][3];
                // for (int i = 0; i < purchaseDashboard.size(); i++) {
                //     String[] elems = purchaseDashboard.get(i).split(",");
                //     System.arraycopy(elems, 0, data[i], 0, elems.length);
                // }

                // JTable table = new JTable(data, sortChoices);
                // JScrollPane scrollPane = new JScrollPane(table);
                // displayDashboard("Purchases", scrollPane);

            } else if (e.getSource() == checkoutItemsButton) {
                // String[] purchaseItemsResult = CustomerClient.purchaseItems("PURCHASE_ITEMS");
                /*
                 * if (purchaseItemsResult[0].equals("SUCCESS")) {
                 *      JOptionPane.displayMessageDialog(null, purchaseItemsResult[1], "Purchase Items", JOptionPane.INFORMATION_MESSAGE);
                 * } else {
                 *  displayErrorMessage(purchaseItemsResult[1]);
                 * }
                 */

            } else if (e.getSource() == removeItemFromShoppingCartButton) {
                // String[] getShoppingCartResult = CustomerClient.getShoppingCart("GET_SHOPPING_CART");
                /*
                 * if (getShoppingCartResult[0].equals("ERROR")) {
                 *      displayErrorDialog(getShoppingCartResult[1]);
                 * } else {
                 *      String[] shoppingCartItems = getShoppingCartResult[1].split("\n");
                 *      String itemChoice = (String) JOptionPane.displayInputDialog(null, "Which item would you like to remove?", "Shopping Cart", JOptionPane.QUESTION_MESSAGE, null, shoppingCartItems, shoppingCartItems[0])
                 * }
                 * if (itemChoice == null) {
                 *      return;
                 * }
                 * int itemSelection = Arrays.binarySearch(shoppingCartItems, itemChoice) + 1;
                 * String[] removeFromCartResult = customerClient.removeFromCart(itemSelection);
                 * if (removeFromCartResult[0].equals("SUCCESS")) {
                 *      JOptionPane.displayMessageDialog(null, removeFromCartResult[1], "Remove from cart", JOptionPane.INFORMATION_MESSAGE);
                 * } else {
                 *      displayErrorDialog(removeFromCartResult[1]);
                 * }
                 */
                    
            } else if (e.getSource() == manageAccountButton) {
                // Behavior TBD

            } else if (e.getSource() == signOutButton) {
                // Behavior TBD
            }
        }
    };

    public CustomerGUI(CustomerClient customerClient, String email) {
        this.customerClient = customerClient;
        this.email = email;
        customerFrame = new JFrame("Customer Page");
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 5, 5));

        // Source: https://stackoverflow.com/questions/5328405/jpanel-padding-in-java
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        customerFrame.setSize(800, 500);
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Welcome message label initialization
        welcomeUserLabel = new JLabel("Welcome " + this.email, SwingConstants.CENTER);
        welcomeUserLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        welcomeUserLabel.setFont(new Font("Serif", Font.BOLD, 20));

        viewAllProductsButton = new JButton("View All Products");
        viewAllProductsButton.addActionListener(actionListener);

        searchForProductButton = new JButton("Search For Product");
        searchForProductButton.addActionListener(actionListener);

        exportPurchaseHistoryButton = new JButton("Export Purchase History");
        exportPurchaseHistoryButton.addActionListener(actionListener);

        viewStoreDashboardButton = new JButton("View Stores Dashboard");
        viewStoreDashboardButton.addActionListener(actionListener);

        viewPurchaseDashboardButton = new JButton("View Purchases Dashboard");
        viewPurchaseDashboardButton.addActionListener(actionListener);

        checkoutItemsButton = new JButton("Checkout Items");
        checkoutItemsButton.addActionListener(actionListener);

        removeItemFromShoppingCartButton = new JButton("Remove Item From Shopping Cart");
        removeItemFromShoppingCartButton.addActionListener(actionListener);

        manageAccountButton = new JButton("Manage Account");
        manageAccountButton.addActionListener(actionListener);

        signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(actionListener);

        buttonPanel.add(viewAllProductsButton);
        buttonPanel.add(searchForProductButton);
        buttonPanel.add(exportPurchaseHistoryButton);
        buttonPanel.add(viewStoreDashboardButton);
        buttonPanel.add(viewPurchaseDashboardButton);
        buttonPanel.add(checkoutItemsButton);
        buttonPanel.add(removeItemFromShoppingCartButton);
        buttonPanel.add(manageAccountButton);
        buttonPanel.add(signOutButton);

        customerFrame.add(welcomeUserLabel, BorderLayout.NORTH);
        customerFrame.add(buttonPanel, BorderLayout.CENTER);

        customerFrame.setVisible(true);
    }
}