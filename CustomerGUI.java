import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
 * @version December 4, 2023
 */
public class CustomerGUI extends JComponent {

    private DisplayDashboardGUI displayDashboard = new DisplayDashboardGUI();
    private JFrame customerFrame;
    private CustomerClient customerClient;
    private JLabel welcomeUserLabel;
    private JButton editEmailButton;
    private JButton editPasswordButton;
    private JButton deleteAccountButton;
    private JButton signOutButton;
    private final String[] SORT_ORDER_CHOICES = {"Ascending", "Descending"};

    // Associated with customer permissions
    private JButton viewAllProductsButton;
    private JButton searchForProductButton;
    private JButton sortProductsButton;
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
                new CustomerGUI(null);
            }
        });
    }

    public void displayErrorDialog(String errorMessage) {
        new ErrorMessageGUI(errorMessage);
    }

    public void displayDashboard(String dashboardType, JScrollPane scrollPane) {
        displayDashboard.showDashboard(dashboardType, scrollPane);
    }

    @SuppressWarnings("unchecked")
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewAllProductsButton) {
                Object[] viewAllProductsResult = customerClient.getAllProducts();
                if (viewAllProductsResult[0].equals("ERROR")) {
                    displayErrorDialog((String) viewAllProductsResult[1]);
                    return;
                } else {
                    String[] originalProducts = ((String) viewAllProductsResult[1]).split("\n");
                    String[] modifiedProducts = Arrays.copyOfRange(originalProducts, 1, originalProducts.length);
                    String productChoice = (String) JOptionPane.showInputDialog(null, "Which product\'s details would you like to view?", "Products", JOptionPane.QUESTION_MESSAGE, null, modifiedProducts, modifiedProducts[0]);
                    if (productChoice == null) {
                        return;
                    }
                    
                    int productSelection = Arrays.binarySearch(modifiedProducts, productChoice);
                    Object[] incoming = customerClient.getProductInfo(productSelection);  

                    if (incoming[0].equals("ERROR")) {
                        displayErrorDialog((String) incoming[1]);
                        return;
                    } else {
                        String[] productInfo = ((String) incoming[1]).split(",");
                        String storeName = productInfo[3];
                        String productName = productInfo[4];
                        String availableQuantity = productInfo[5]; 
                        String price = productInfo[6];
                        String description = productInfo[7];
                        String info = String.format("Store Name: %s%nProduct Name: %s%nAvailable Quantity: %s%nPrice: %s%nDescription: %s%s", storeName, productName, availableQuantity, price, description);
                        String[] options = {"Yes", "No"};
                        String addToCart = (String) JOptionPane.showInputDialog(null, "Would you like to add this item to your cart?\n" + info, "Add Item", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (addToCart == null || addToCart.equals("No")) {
                            return;
                        } else {
                            Integer desiredQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many would you like?", "Quantity", JOptionPane.QUESTION_MESSAGE));
                            Object[] productAddedResult = customerClient.addToCart(productSelection, desiredQuantity);
                    
                            if (productAddedResult[0].equals("SUCCESS")) {
                                JOptionPane.showMessageDialog(null, productAddedResult[1], "Add To Cart", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                displayErrorDialog((String) productAddedResult[1]);
                            }
                        }
                    }
                }

            } else if (e.getSource() == searchForProductButton) {
                String query = JOptionPane.showInputDialog(null, "What would you like to search for?", "Search for Product", JOptionPane.QUESTION_MESSAGE);
                Object[] searchProductsResult = customerClient.searchProducts(query);
                if (searchProductsResult[0].equals("ERROR")) {
                    displayErrorDialog((String) searchProductsResult[1]);
                    return;
                } else {
                    String[] originalProducts = ((String) searchProductsResult[1]).split("\n");
                    String[] modifiedProducts = Arrays.copyOfRange(originalProducts, 1, originalProducts.length);
                    String productChoice = (String) JOptionPane.showInputDialog(null, "Which product\'s details would you like to view?", "Products", JOptionPane.QUESTION_MESSAGE, null, modifiedProducts, modifiedProducts[0]);
                    if (productChoice == null) {
                        return;
                    }
                    
                    int productSelection = Arrays.binarySearch(modifiedProducts, productChoice);
                    Object[] incoming = customerClient.getProductInfo(productSelection);
                    
                    if (incoming[0].equals("ERROR")) {
                        displayErrorDialog((String) incoming[1]);
                        return;
                    } else {
                        String[] productInfo = ((String) incoming[1]).split(",");
                        String storeName = productInfo[3];
                        String productName = productInfo[4];
                        String availableQuantity = productInfo[5]; 
                        String price = productInfo[6];
                        String description = productInfo[7];
                        String info = String.format("Store Name: %s%nProduct Name: %s%nAvailable Quantity: %s%nPrice: %s%nDescription: %s%s", storeName, productName, availableQuantity, price, description);
                        JOptionPane.showMessageDialog(null, info, "Product Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } else if (e.getSource() == sortProductsButton) {
                Object[] viewAllProductsResult = customerClient.getAllProducts();
                if (viewAllProductsResult[0].equals("ERROR")) {
                    displayErrorDialog((String) viewAllProductsResult[1]);
                    return;
                } else {
                    String[] originalProducts = ((String) viewAllProductsResult[1]).split("\n");
                    String[] modifiedProducts = Arrays.copyOfRange(originalProducts, 1, originalProducts.length);
                    new SortProductsGUI(modifiedProducts, customerClient);
                } 

            } else if (e.getSource() == exportPurchaseHistoryButton) {
                Object[] exportPurchaseHistoryResult = customerClient.exportPurchaseHistory();
                
                if (exportPurchaseHistoryResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, exportPurchaseHistoryResult[1], "Export Purchase History", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog((String) exportPurchaseHistoryResult[1]);
                } 

            } else if (e.getSource() == viewPurchaseHistoryButton) {
                Object[] purchaseHistoryResult = customerClient.getShoppingHistory();
                if (purchaseHistoryResult[0].equals("ERROR")) {
                    displayErrorDialog((String) purchaseHistoryResult[1]);
                } else {
                    String purchaseHistoryEntries = (String) purchaseHistoryResult[1];
                    JOptionPane.showMessageDialog(null, purchaseHistoryEntries, "Purchase History", JOptionPane.INFORMATION_MESSAGE);
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

                if (storeDashboardResult[0].equals("ERROR")) { 
                    String errorMessage = (String) storeDashboardResult[1];
                    displayErrorDialog(errorMessage);
                    return;
                } else if (storeDashboardResult[0].equals("SUCCESS")) {
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
                Object[] purchasesDashboardResult = customerClient.customerGetPersonalPurchasesDashboard(sortSelection, ascending);
                
                if (purchasesDashboardResult[0].equals("ERROR")) {
                    String errorMessage = (String) purchasesDashboardResult[1];
                    displayErrorDialog(errorMessage);
                    return;
                } else if (purchasesDashboardResult[0].equals("SUCCESS")) {
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
                Object[] purchaseItemsResult = customerClient.purchaseItems();
                
                if (purchaseItemsResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, purchaseItemsResult[1], "Purchase Items", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog((String) purchaseItemsResult[1]);
                }

            } else if (e.getSource() == removeItemFromShoppingCartButton) {
                Object[] getShoppingCartResult = customerClient.getCart();
                if (getShoppingCartResult[0].equals("ERROR")) {
                       displayErrorDialog((String) getShoppingCartResult[1]);
                       return;
                } else {
                    String[] shoppingCartItems = ((String) getShoppingCartResult[1]).split("\n");
                    String itemChoice = (String) JOptionPane.showInputDialog(null, "Which item would you like to remove?", "Shopping Cart", JOptionPane.QUESTION_MESSAGE, null, shoppingCartItems, shoppingCartItems[0]);
                    if (itemChoice == null) {
                        return;
                    }
                    int itemSelection = Arrays.binarySearch(shoppingCartItems, itemChoice);
                    Object[] removeFromCartResult = customerClient.removeFromCart(itemSelection);
                    if (removeFromCartResult[0].equals("SUCCESS")) {
                        JOptionPane.showMessageDialog(null, removeFromCartResult[1], "Remove from cart", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        displayErrorDialog((String) removeFromCartResult[1]);
                    }
                }
                    
            } else if (e.getSource() == editEmailButton) {
                String newEmail = JOptionPane.showInputDialog(null, "What is the new email?", "Edit Email", JOptionPane.QUESTION_MESSAGE);
                if (newEmail == null) {
                    return;
                }
                Object[] editEmailResult = customerClient.editEmail(newEmail);
                if (editEmailResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, editEmailResult[1], "Edit Email", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog((String) editEmailResult[1]);
                }

            } else if (e.getSource() == editPasswordButton) {
                String newPassword = JOptionPane.showInputDialog(null, "What is the new password?", "Edit Password", JOptionPane.QUESTION_MESSAGE);
                if (newPassword == null) {
                    return;
                }
                Object[] editPasswordResult = customerClient.editPassword(newPassword);
                if (editPasswordResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, editPasswordResult[1], "Edit Password", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog((String) editPasswordResult[1]);
                }

            } else if (e.getSource() == deleteAccountButton) {
                int deleteAccount = JOptionPane.showOptionDialog(null, "Are you sure you want to delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                if (deleteAccount == JOptionPane.YES_OPTION) {
                    Object[] deleteAccountResult = customerClient.deleteAccount();
                    if (deleteAccountResult[0].equals("SUCCESS")) {
                        try {
                            customerFrame.dispose();
                            customerClient.handleAccountState();
                        } catch (IOException ex) {
                            return;
                        }
                    } else {
                        displayErrorDialog((String) deleteAccountResult[1]);
                    }
                } else {
                    return;
                }

            } else if (e.getSource() == signOutButton) {
                int signOut = JOptionPane.showOptionDialog(null, "Are you sure you want to sign out?", "Sign out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                if (signOut == JOptionPane.YES_OPTION) {
                    Object[] signOutResult = customerClient.signOut();
                     if (signOutResult[0].equals("SUCCESS")) {
                         try {
                             customerFrame.dispose();
                             customerClient.handleAccountState();
                         } catch (IOException ex) {
                             return;
                         }
                        } else {
                            displayErrorDialog((String) signOutResult[1]);
                        }
                 } else {
                     return;
                 }
            }
        }
    };

    public CustomerGUI(CustomerClient customerClient) {
        this.customerClient = customerClient;
        customerFrame = new JFrame("Customer Page");
        JPanel buttonPanel = new JPanel(new GridLayout(6, 2 , 5, 5));

        // Source: https://stackoverflow.com/questions/5328405/jpanel-padding-in-java
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        customerFrame.setSize(800, 500);
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Welcome message label initialization
        welcomeUserLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeUserLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        welcomeUserLabel.setFont(new Font("Serif", Font.BOLD, 20));

        viewAllProductsButton = new JButton("View All Products");
        viewAllProductsButton.addActionListener(actionListener);

        searchForProductButton = new JButton("Search For Product");
        searchForProductButton.addActionListener(actionListener);

        sortProductsButton = new JButton("Sort Products");
        sortProductsButton.addActionListener(actionListener);

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
        buttonPanel.add(sortProductsButton);
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