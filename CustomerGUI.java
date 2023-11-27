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
 * @version November 26, 2023
 */
public class CustomerGUI extends JComponent {

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // new CustomerGUI("");
            }
        });
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewAllProductsButton) {
                // CustomerClient.getMarketplaceProducts("GET_MARKETPLACE_PRODUCTS")
                    // Display all products in a dropdown menu to customer
                    // Retrieve index of selection based on the index in the list of all products
                    // Ask them how many of the product they want to purchase
                    // CustomerClient.addToCart("ADD_TO_CART", index of selection, quantity);
                    // display confirmation/error message depending on result of action


            } else if (e.getSource() == searchForProductButton) {
                String query = JOptionPane.showInputDialog(null, "What would you like to search for?", "Search for Product", JOptionPane.QUESTION_MESSAGE);
                // CustomerClient.searchProducts("SEARCH_PRODUCTS", query);
                    // Display all products in a dropdown menu to customer
                    // Retrieve index of selection based on the index in the list of all products
                    // Ask them how many of the product they want to purchase
                    // CustomerClient.addToCart("ADD_TO_CART", index of selection, quantity);
                    // display confirmation/error message depending on result of action


            } else if (e.getSource() == exportPurchaseHistoryButton) {
                // CustomerClient.exportPurchaseHistory("EXPORT_PURCHASE_HISTORY")
                // Display error/confirmation message depending on result


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
                // CustomerClient.viewStoresDashboard("VIEW_STORES_DASHBOARD", sortSelection, ascending);
                Object[][] data = null;
                JTable table = new JTable(data, sortChoices);
                JScrollPane scrollPane = new JScrollPane(table);
                // new DisplayDashboardGUI("Stores", scrollPane);

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
                // CustomerClient.viewStoresDashboard("VIEW_PURCHASES_DASHBOARD", sortSelection, ascending, customerClient.getcustomerID);
                Object[][] data = null;
                JTable table = new JTable(data, sortChoices);
                JScrollPane scrollPane = new JScrollPane(table);
                // new DisplayDashboardGUI("Purchases", scrollPane);
                

            } else if (e.getSource() == checkoutItemsButton) {
                // CustomerClient.purchaseItems("PURCHASE_ITEMS")
                // Display confirmation/error message depending on action result

            } else if (e.getSource() == removeItemFromShoppingCartButton) {
                // CustomerClient.getShoppingCart("GET_SHOPPING_CART")
                    // Display all products in a dropdown menu to customer
                    // Retrieve index of selection based on the index in the list of all items
                    // CustomerClient.removeFromCart("REMOVE_FROM_CART", index of selection)
                    // display confirmation/error message depending on result of action
                    
            } else if (e.getSource() == manageAccountButton) {
                // invoke the edit account GUI
                // all actions will call the corresponding method in the client class
                    // edit username
                    // edit password
                    // delete account

            } else if (e.getSource() == signOutButton) {
                // customerClient.signOut();
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