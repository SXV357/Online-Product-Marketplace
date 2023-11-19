import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
/**
 * Project 5 - SellerGUI.java
 * 
 * The interface associated with a seller in the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version November 18, 2023
 */
public class SellerGUI extends JComponent {

    private String email;
    private JLabel welcomeUserLabel;
    private static final String[] SORT_ORDER_CHOICES = {"Ascending", "Descending"};

    // Related to seller permissions
    private JButton createStoreButton;
    private JButton editStoreButton;
    private JButton deleteStoreButton;
    private JButton createProductButton;
    private JButton editProductButton;
    private JButton deleteProductButton;
    private JButton importProductsButton;
    private JButton exportProductsButton;
    private JButton viewCustomerShoppingCartsButton;
    private JButton viewSalesByStoreButton;
    private JButton viewCustomerDashboardButton;
    private JButton viewProductDashboardButton;
    private JButton manageAccountButton;
    private JButton signOutButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Login/Signup GUI will pass in email to be utilized here
                new SellerGUI("");
            }
        });
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform certain actions based on which button is clicked
            if (e.getSource() == createStoreButton) {
                String newStoreName = JOptionPane.showInputDialog(null, "What is the new store\'s name?", "New Store Name", JOptionPane.QUESTION_MESSAGE);
                // Client.createStore("CREATE_STORE", newStoreName);
                // Display corresponding error/confirmation dialog

            } else if (e.getSource() == editStoreButton) {
                String prevStoreName = JOptionPane.showInputDialog(null, "What is the old name of the store?", "Previous Store Name", JOptionPane.QUESTION_MESSAGE);
                if (prevStoreName == null) {
                    return;
                }
                String newStoreName = JOptionPane.showInputDialog(null, "What is the new name of the store?", "New Store Name", JOptionPane.QUESTION_MESSAGE);
                if (newStoreName == null) {
                    return;
                }
                // Client.modifyStore("MODIFY_STORE_NAME", prevStoreName, newStoreName);
                // Display corresponding error/confirmation dialog

            } else if (e.getSource() == deleteStoreButton) {
                String storeName = JOptionPane.showInputDialog(null, "What is the name of the store to delete?", "Store Name", JOptionPane.QUESTION_MESSAGE);
                // Client.deleteStore("DELETE_STORE", storeName);
                // Display corresponding error/confirmation dialog

            } else if (e.getSource() == createProductButton) {
                String storeName = JOptionPane.showInputDialog(null, "What is the name of the store to add the product to?", "Store Name", JOptionPane.QUESTION_MESSAGE);
                if (storeName == null) {
                    return;
                }
                String productName = JOptionPane.showInputDialog(null, "What is the name of the product?", "Product Name", JOptionPane.QUESTION_MESSAGE);
                if (productName == null) {
                    return;
                }
                String availableQuantity = JOptionPane.showInputDialog(null, "How many of this product are for sale?", "Product Quantity", JOptionPane.QUESTION_MESSAGE);
                if (availableQuantity == null) {
                    return;
                }
                String price = JOptionPane.showInputDialog(null, "What is the price of this product?", "Product Price", JOptionPane.QUESTION_MESSAGE);
                if (price == null) {
                    return;
                }
                String description = JOptionPane.showInputDialog(null, "Product Description?", "Product Description", JOptionPane.QUESTION_MESSAGE);
                if (description == null) {
                    return;
                }
                // Client.createNewProduct("CREATE_NEW_PRODUCT", storeName, productName, availableQuantity, price, description);
                // Display corresponding error/confirmation dialog

            } else if (e.getSource() == editProductButton) {
                String storeName = JOptionPane.showInputDialog(null, "What is the name of the store that contains the product to be edited?", "Store Name", JOptionPane.QUESTION_MESSAGE);
                if (storeName == null) {
                    return;
                }
                String productName = JOptionPane.showInputDialog(null, "What is the name of the product to edit?", "Product Name", JOptionPane.QUESTION_MESSAGE);
                if (productName == null) {
                    return;
                }
                String editParam = JOptionPane.showInputDialog(null, "Which parameter would you like to edit?", "Edit Parameter", JOptionPane.QUESTION_MESSAGE);
                if (editParam == null) {
                    return;
                }
                String newValue = JOptionPane.showInputDialog(null, "What is the new value?", "New Value", JOptionPane.QUESTION_MESSAGE);
                if (newValue == null) {
                    return;
                }
                // Client.editProduct("EDIT_PRODUCT", storeName, productName, editParam, newValue);
                // Display corresponding error/confirmation dialog

            } else if (e.getSource() == deleteProductButton) {
                String storeName = JOptionPane.showInputDialog(null, "What is the name of the store that contains the product to be deleted?", "Store Name", JOptionPane.QUESTION_MESSAGE);
                if (storeName == null) {
                    return;
                }
                String productName = JOptionPane.showInputDialog(null, "What is the name of the product to delete?", "Product Name", JOptionPane.QUESTION_MESSAGE);
                if (productName == null) {
                    return;
                }
                // Client.deleteProduct("DELETE_PRODUCT", storeName, productName);
                // Display corresponding error/confirmation dialog

            } else if (e.getSource() == importProductsButton) {
              String filePath = JOptionPane.showInputDialog(null, "What is the name of the file that contains the products?", "File Name", JOptionPane.QUESTION_MESSAGE);
              if (filePath == null) {
                return;
              }
              String storeName = JOptionPane.showInputDialog(null, "What is the name of the store to import the products into?", "Store Name", JOptionPane.QUESTION_MESSAGE);
              if (storeName == null) {
                return;
              }
              // Client.importProducts("IMPORT_PRODUCTS", filePath, storeName);
              // Display corresponding error/confirmation dialog

            } else if (e.getSource() == exportProductsButton) {
                String storeName = JOptionPane.showInputDialog(null, "What is the name of the store to export the products from?", "Store Name", JOptionPane.QUESTION_MESSAGE);
                // Client.exportProducts("EXPORT_PRODUCTS", storeName);
                // Display corresponding error/confirmation dialog

            } else if (e.getSource() == viewCustomerShoppingCartsButton) {
                // Client.viewCustomerShoppingCarts("VIEW_CUSTOMER_SHOPPING_CARTS");
                // Display corresponding error/information dialog

            } else if (e.getSource() == viewSalesByStoreButton) {
                // Client.viewSalesByStore("VIEW_SALES_BY_STORE");
                // Display corresponding error/information dialog

            } else if (e.getSource() == viewCustomerDashboardButton) {
                String[] sortChoices = {"Customer Email", "Quantity Purchased", "Money Spent"};
                String sortChoice = (String) JOptionPane.showInputDialog(null, "How would you like to sort the dashboard?", "Dashboard Sort Choice", JOptionPane.QUESTION_MESSAGE, null, sortChoices, sortChoices[0]);
                if (sortChoice == null) {
                    return;
                }
                String orderChoice = (String) JOptionPane.showInputDialog(null, "In what order would you like to sort the dashboard?", "Sorting Order", JOptionPane.QUESTION_MESSAGE, null, SORT_ORDER_CHOICES, SORT_ORDER_CHOICES[0]);
                if (orderChoice == null) {
                    return;
                }
                boolean ascending = orderChoice.equals("Ascending") ? true: false;
                // Client.sellerGetCustomersDashboard("CUSTOMERS_DASHBOARD", sortChoice, ascending)
                // Display corresponding error/information dialog

            } else if (e.getSource() == viewProductDashboardButton) {
                String[] sortChoices = {"Product Name", "Quantity Sold", "Total revenues"};
                String sortChoice = (String) JOptionPane.showInputDialog(null, "How would you like to sort the dashboard?", "Dashboard Sort Choice", JOptionPane.QUESTION_MESSAGE, null, sortChoices, sortChoices[0]);
                if (sortChoice == null) {
                    return;
                }
                String orderChoice = (String) JOptionPane.showInputDialog(null, "In what order would you like to sort the dashboard?", "Sorting Order", JOptionPane.QUESTION_MESSAGE, null, SORT_ORDER_CHOICES, SORT_ORDER_CHOICES[0]);
                if (orderChoice == null) {
                    return;
                }
                boolean ascending = orderChoice.equals("Ascending") ? true: false;
                // Client.sellerGetProductsDashboard("PRODUCTS_DASHBOARD", sortChoice, ascending)
                // Display corresponding error/information dialog

            } else if (e.getSource() == manageAccountButton) {
                // re-direct them to the manage account GUI

            } else if (e.getSource() == signOutButton) {
                //new UserGUI();
            }
        }
    };

    public SellerGUI(String email) {
        this.email = email;
        JFrame sellerFrame = new JFrame("Seller Page");
        JPanel buttonPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        // Source: https://stackoverflow.com/questions/5328405/jpanel-padding-in-java
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        sellerFrame.setSize(500, 800);
        sellerFrame.setLocationRelativeTo(null);
        sellerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Welcome message label initialization
        welcomeUserLabel = new JLabel("Welcome " + this.email, SwingConstants.CENTER);
        welcomeUserLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        welcomeUserLabel.setFont(new Font("Serif", Font.BOLD, 20));

        // Seller permission button initialization
        createStoreButton = new JButton("Create Store");
        createStoreButton.addActionListener(actionListener);

        editStoreButton = new JButton("Edit Store");
        editStoreButton.addActionListener(actionListener);

        deleteStoreButton = new JButton("Delete Store");
        deleteStoreButton.addActionListener(actionListener);

        createProductButton = new JButton("Add Product");
        createProductButton.addActionListener(actionListener);

        editProductButton = new JButton("Edit Product");
        editProductButton.addActionListener(actionListener);

        deleteProductButton = new JButton("Delete Product");
        deleteProductButton.addActionListener(actionListener);

        importProductsButton = new JButton("Import Products");
        importProductsButton.addActionListener(actionListener);

        exportProductsButton = new JButton("Export Products");
        exportProductsButton.addActionListener(actionListener);

        viewCustomerShoppingCartsButton = new JButton("View Customer Shopping Carts");
        viewCustomerShoppingCartsButton.addActionListener(actionListener);

        viewSalesByStoreButton = new JButton("View Sales by Store");
        viewSalesByStoreButton.addActionListener(actionListener);

        viewCustomerDashboardButton = new JButton("View Customers Dashboard");
        viewCustomerDashboardButton.addActionListener(actionListener);

        viewProductDashboardButton = new JButton("View Products Dashboard");
        viewProductDashboardButton.addActionListener(actionListener);

        manageAccountButton = new JButton("Manage Account");
        manageAccountButton.addActionListener(actionListener);

        signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(actionListener);

        buttonPanel.add(createStoreButton);
        buttonPanel.add(editStoreButton);
        buttonPanel.add(deleteStoreButton);
        buttonPanel.add(createProductButton);
        buttonPanel.add(editProductButton);
        buttonPanel.add(deleteProductButton);
        buttonPanel.add(importProductsButton);
        buttonPanel.add(exportProductsButton);
        buttonPanel.add(viewCustomerShoppingCartsButton);
        buttonPanel.add(viewSalesByStoreButton);
        buttonPanel.add(viewCustomerDashboardButton);
        buttonPanel.add(viewProductDashboardButton);
        buttonPanel.add(manageAccountButton);
        buttonPanel.add(signOutButton);

        sellerFrame.add(welcomeUserLabel, BorderLayout.NORTH);
        sellerFrame.add(buttonPanel, BorderLayout.CENTER);

        sellerFrame.setVisible(true);

    }
}