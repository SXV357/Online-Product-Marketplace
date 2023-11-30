import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
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
    private JButton editEmailButton;
    private JButton editPasswordButton;
    private JButton deleteAccountButton;
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
    private JButton viewPurchaseHistoryButton;

    public static void main(String[] args) { // For internal testing purposes only
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerGUI(null, "");
            }
        });
    }

    public void displayErrorDialog(String errorMessage) {
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
            new ErrorMessageGUI(errorMessage);
           } 
        });
    }

    public void displayMiscInfo(String informationType, String data) {
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
            JFrame customerMiscInfoFrame = displayInfo.displayMiscInfo(informationType, data);
            customerMiscInfoFrame.setVisible(true);
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

    @SuppressWarnings("unchecked")
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewAllProductsButton) {
                String[] viewAllProductsResult = customerClient.getAllProducts();
                if (viewAllProductsResult[0].equals("ERROR")) {
                    displayErrorDialog(viewAllProductsResult[1]);
                    return;
                } else {
                    String[] products = viewAllProductsResult[1].split("\n");
                    String productChoice = (String) JOptionPane.showInputDialog(null, "Which product\'s details would you like to view?", "Products", JOptionPane.QUESTION_MESSAGE, null, products, products[0]);
                    if (productChoice == null) {
                        return;
                    }
                    
                    int productSelection = Arrays.binarySearch(products, productChoice) + 1;
                    String[] productInfo = customerClient.getProductInfo(productSelection);
                    
                    if (productInfo[0].equals("ERROR")) {
                        displayErrorDialog(productInfo[1]);
                        return;
                    } else {
                        String[] options = {"Yes", "No"};
                        String addToCart = (String) JOptionPane.showInputDialog(null, "Would you like to add this item to your cart?\n" + productInfo, "Add Item", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (addToCart == null || addToCart.equals("No")) {
                            return;
                        } else {
                            Integer desiredQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many would you like?", "Quantity", JOptionPane.QUESTION_MESSAGE));
                            String[] productAddedResult = customerClient.addToCart(productSelection, desiredQuantity);
                    
                            if (productAddedResult[0].equals("SUCCESS")) {
                                JOptionPane.showMessageDialog(null, productAddedResult[1], "Add To Cart", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                displayErrorDialog(productAddedResult[1]);
                            }
                        }
                    }
                }

            } else if (e.getSource() == searchForProductButton) {
                String query = JOptionPane.showInputDialog(null, "What would you like to search for?", "Search for Product", JOptionPane.QUESTION_MESSAGE);
                String[] viewAllProductsResult = customerClient.searchProducts(query);
                if (viewAllProductsResult[0].equals("ERROR")) {
                    displayErrorDialog(viewAllProductsResult[1]);
                    return;
                } else {
                    String[] products = viewAllProductsResult[1].split("\n");
                    String productChoice = (String) JOptionPane.showInputDialog(null, "Which product\'s details would you like to view?", "Products", JOptionPane.QUESTION_MESSAGE, null, products, products[0]);
                    if (productChoice == null) {
                        return;
                    }
                    
                    int productSelection = Arrays.binarySearch(products, productChoice) + 1;
                    String[] productInfo = customerClient.getProductInfo(productSelection);
                    
                    if (productInfo[0].equals("ERROR")) {
                        displayErrorDialog(productInfo[1]);
                        return;
                    } else {
                        String[] options = {"Yes", "No"};
                        String addToCart = (String) JOptionPane.showInputDialog(null, "Would you like to add this item to your cart?\n" + productInfo, "Add Item", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (addToCart == null || addToCart.equals("No")) {
                            return;
                        } else {
                            Integer desiredQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many would you like?", "Quantity", JOptionPane.QUESTION_MESSAGE));
                            String[] productAddedResult = customerClient.addToCart(productSelection, desiredQuantity);
                    
                            if (productAddedResult[0].equals("SUCCESS")) {
                                JOptionPane.showMessageDialog(null, productAddedResult[1], "Add To Cart", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                displayErrorDialog(productAddedResult[1]);
                            }
                        }
                    }
                }

            } else if (e.getSource() == exportPurchaseHistoryButton) {
                String[] exportPurchaseHistoryResult = customerClient.exportPurchaseHistory();
                
                if (exportPurchaseHistoryResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, exportPurchaseHistoryResult[1], "Export Purchase History", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(exportPurchaseHistoryResult[1]);
                } 

            } else if (e.getSource() == viewPurchaseHistoryButton) {
                String[] purchaseHistoryResult = customerClient.getShoppingHistory();
                if (purchaseHistoryResult[0].equals("ERROR")) {
                    displayErrorDialog(purchaseHistoryResult[1]);
                } else {
                    String purchaseHistoryEntries = purchaseHistoryResult[1];
                    displayMiscInfo("Purchase History", purchaseHistoryEntries);
                }

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
                Object[] storeDashboardResult = customerClient.customerGetStoresDashboard(sortSelection, ascending);

                if (storeDashboardResult[0] instanceof String && storeDashboardResult[0].equals("ERROR")) { 
                    String errorMessage = (String) storeDashboardResult[1];
                    displayErrorDialog(errorMessage);
                    return;
                } else if (storeDashboardResult[0] instanceof String && storeDashboardResult[0].equals("SUCCESS")) {
                    ArrayList<String> storeDashboard = (ArrayList<String>) storeDashboardResult[1];
                    Object[][] data = new Object[storeDashboard.size()][3];
                    for (int i = 0; i < storeDashboard.size(); i++) {
                        String[] elems = storeDashboard.get(i).split(",");
                        System.arraycopy(elems, 0, data[i], 0, elems.length);
                    }

                    JTable table = new JTable(data, sortChoices);
                    JScrollPane scrollPane = new JScrollPane(table);
                    displayDashboard("Stores", scrollPane);
                }

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
                Object[] purchasesDashboardResult = customerClient.customerGetPersonalPurchasesDashboard(sortSelection, ascending, customerClient.getCustomer().getUserID());
                
                if (purchasesDashboardResult[0] instanceof String && purchasesDashboardResult[0].equals("ERROR")) {
                    String errorMessage = (String) purchasesDashboardResult[1];
                    displayErrorDialog(errorMessage);
                    return;
                } else if (purchasesDashboardResult[0] instanceof String && purchasesDashboardResult[0].equals("SUCCESS")) {
                    ArrayList<String> purchaseDashboard = (ArrayList<String>) purchasesDashboardResult[1];
                    Object[][] data = new Object[purchaseDashboard.size()][3];
                    for (int i = 0; i < purchaseDashboard.size(); i++) {
                        String[] elems = purchaseDashboard.get(i).split(",");
                        System.arraycopy(elems, 0, data[i], 0, elems.length);
                    }

                    JTable table = new JTable(data, sortChoices);
                    JScrollPane scrollPane = new JScrollPane(table);
                    displayDashboard("Purchases", scrollPane);
                }

            } else if (e.getSource() == checkoutItemsButton) {
                String[] purchaseItemsResult = customerClient.purchaseItems();
                
                if (purchaseItemsResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, purchaseItemsResult[1], "Purchase Items", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(purchaseItemsResult[1]);
                }

            } else if (e.getSource() == removeItemFromShoppingCartButton) {
                String[] getShoppingCartResult = customerClient.getCart();
                if (getShoppingCartResult[0].equals("ERROR")) {
                       displayErrorDialog(getShoppingCartResult[1]);
                       return;
                } else {
                    String[] shoppingCartItems = getShoppingCartResult[1].split("\n");
                    String itemChoice = (String) JOptionPane.showInputDialog(null, "Which item would you like to remove?", "Shopping Cart", JOptionPane.QUESTION_MESSAGE, null, shoppingCartItems, shoppingCartItems[0]);
                    if (itemChoice == null) {
                        return;
                    }
                    int itemSelection = Arrays.binarySearch(shoppingCartItems, itemChoice) + 1;
                    String[] removeFromCartResult = customerClient.removeFromCart(itemSelection);
                    if (removeFromCartResult[0].equals("SUCCESS")) {
                        JOptionPane.showMessageDialog(null, removeFromCartResult[1], "Remove from cart", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        displayErrorDialog(removeFromCartResult[1]);
                    }
                }
                    
            } else if (e.getSource() == editEmailButton) {
                String newEmail = JOptionPane.showInputDialog(null, "What is the new email?", "Edit Email", JOptionPane.QUESTION_MESSAGE);
                if (newEmail == null) {
                    return;
                }
                String[] editEmailResult = customerClient.editEmail(newEmail);
                if (editEmailResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, editEmailResult[1], "Edit Email", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(editEmailResult[1]);
                }

            } else if (e.getSource() == editPasswordButton) {
                String newPassword = JOptionPane.showInputDialog(null, "What is the new password?", "Edit Password", JOptionPane.QUESTION_MESSAGE);
                if (newPassword == null) {
                    return;
                }
                String[] editPasswordResult = customerClient.editPassword(newPassword);
                if (editPasswordResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, editPasswordResult[1], "Edit Password", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(editPasswordResult[1]);
                }

            } else if (e.getSource() == deleteAccountButton) {
                int deleteAccount = JOptionPane.showOptionDialog(null, "Are you sure you want to delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                if (deleteAccount == JOptionPane.YES_OPTION) {
                   String[] deleteAccountResult = customerClient.deleteAccount();
                   if (deleteAccountResult[0].equals("SUCCESS")) {
                        JOptionPane.showMessageDialog(null, deleteAccountResult[1], "Delete Account", JOptionPane.INFORMATION_MESSAGE);
                        // Need to discuss with team how later part will be handled
                    } else {
                        displayErrorDialog(deleteAccountResult[1]);
                    }
                }

            } else if (e.getSource() == signOutButton) {
                // Need to discuss with team how this will be handled
            }
        }
    };

    public CustomerGUI(CustomerClient customerClient, String email) {
        this.customerClient = customerClient;
        this.email = email;
        customerFrame = new JFrame("Customer Page");
        JPanel buttonPanel = new JPanel(new GridLayout(6, 2 , 5, 5));

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

        viewPurchaseHistoryButton = new JButton("View Purchase History");
        viewPurchaseHistoryButton.addActionListener(actionListener);

        viewStoreDashboardButton = new JButton("View Stores Dashboard");
        viewStoreDashboardButton.addActionListener(actionListener);

        viewPurchaseDashboardButton = new JButton("View Purchases Dashboard");
        viewPurchaseDashboardButton.addActionListener(actionListener);

        checkoutItemsButton = new JButton("Checkout Items");
        checkoutItemsButton.addActionListener(actionListener);

        removeItemFromShoppingCartButton = new JButton("Remove Item From Shopping Cart");
        removeItemFromShoppingCartButton.addActionListener(actionListener);

        editEmailButton = new JButton("Edit Email");
        editEmailButton.addActionListener(actionListener);

        editPasswordButton = new JButton("Edit Password");
        editPasswordButton.addActionListener(actionListener);

        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(actionListener);

        signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(actionListener);

        buttonPanel.add(viewAllProductsButton);
        buttonPanel.add(searchForProductButton);
        buttonPanel.add(exportPurchaseHistoryButton);
        buttonPanel.add(viewPurchaseHistoryButton);
        buttonPanel.add(viewStoreDashboardButton);
        buttonPanel.add(viewPurchaseDashboardButton);
        buttonPanel.add(checkoutItemsButton);
        buttonPanel.add(removeItemFromShoppingCartButton);
        buttonPanel.add(editEmailButton);
        buttonPanel.add(editPasswordButton);
        buttonPanel.add(deleteAccountButton);
        buttonPanel.add(signOutButton);

        customerFrame.add(welcomeUserLabel, BorderLayout.NORTH);
        customerFrame.add(buttonPanel, BorderLayout.CENTER);

        customerFrame.setVisible(true);
    }
}