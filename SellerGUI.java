import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Project 5 - SellerGUI.java
 * 
 * The interface associated with a seller in the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * s
 * @version November 27, 2023
 */
public class SellerGUI extends JComponent {

    private SellerClient sellerClient;
    private JFrame sellerFrame;
    private String email;
    private JLabel welcomeUserLabel;
    private final String[] SORT_ORDER_CHOICES = {"Ascending", "Descending"};

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
        // SwingUtilities.invokeLater(new Runnable() {
        //     @Override
        //     public void run() {
        //         new SellerGUI(null, "");
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

    public void displayMiscInfo(String informationType, String data) {
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
            new DisplayInformationGUI().displaySellerMiscInfo(informationType, data);
           } 
        });
    }

    public void displayDashboard(String dashboardType, JScrollPane scrollPane) {
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
            new DisplayInformationGUI().displayDashboard(dashboardType, scrollPane);
           } 
        });
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform certain actions based on which button is clicked
            if (e.getSource() == createStoreButton) {
                String newStoreName = JOptionPane.showInputDialog(null, "What is the new store\'s name?", "New Store Name", JOptionPane.QUESTION_MESSAGE);
                String result = sellerClient.createNewStore("CREATE_NEW_STORE", newStoreName);
                JOptionPane.showMessageDialog(null, result, "Create Store", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == editStoreButton) {
                Object result = sellerClient.getStores("GET_ALL_STORES");
                // If this seller doesn't have any stores, display error message
                if (result instanceof String) { // Error
                    String errorMessage = (String) result;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) result;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String prevStoreName = (String) JOptionPane.showInputDialog(null, "Which store\'s name would you like to edit?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (prevStoreName == null) {
                    return;
                }
                String newStoreName = JOptionPane.showInputDialog(null, "What is the new name of the store?", "New Store Name", JOptionPane.QUESTION_MESSAGE);
                if (newStoreName == null) {
                    return;
                }
                String result2 = sellerClient.modifyStoreName("MODIFY_STORE_NAME", prevStoreName, newStoreName);
                JOptionPane.showMessageDialog(null, result2, "Edit Store", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == deleteStoreButton) {
                Object result = sellerClient.getStores("GET_ALL_STORES");
                // If this seller doesn't have any stores, display error message
                if (result instanceof String) { // Error
                    String errorMessage = (String) result;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) result;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store would you like to delete?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }

                String result2 = sellerClient.deleteStore("DELETE_STORE", storeName);
                JOptionPane.showMessageDialog(null, result2, "Delete Store", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == createProductButton) {
                Object result = sellerClient.getStores("GET_ALL_STORES");
                // If this seller doesn't have any stores, display error message
                if (result instanceof String) { // Error
                    String errorMessage = (String) result;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) result;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store would you like to add the product to?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
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
                String description = JOptionPane.showInputDialog(null, "What is the description of the product?", "Product Description", JOptionPane.QUESTION_MESSAGE);
                if (description == null) {
                    return;
                }
                String result2 = sellerClient.createNewProduct("CREATE_NEW_PRODUCT", storeName, productName, availableQuantity, price, description);
                JOptionPane.showMessageDialog(null, result2, "Create Product", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == editProductButton) {
                Object storeResult = sellerClient.getStores("GET_ALL_STORES");
                // If this seller doesn't have any stores, display error message
                if (storeResult instanceof String) { // Error
                    String errorMessage = (String) storeResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) storeResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store contains the product you\'d like to edit?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }
                // ********
                Object productResult = sellerClient.getProducts("GET_ALL_PRODUCTS", storeName);
                if (productResult instanceof String) {
                    String errorMessage = (String) productResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Product> products = (ArrayList<Product>) productResult;
                ArrayList<String> productNames = new ArrayList<>();
                for (Product pr: products) {
                    productNames.add(pr.getName());
                }
                String productName = (String) JOptionPane.showInputDialog(null, "Which product would you like to edit?", "Products", JOptionPane.QUESTION_MESSAGE, null, productNames.toArray(), productNames.get(0));
                if (productName == null) {
                    return;
                }
    
                String[] editParameters = {"Name", "Price", "Description", "Quantity"};
                String editParam = (String) JOptionPane.showInputDialog(null, "Which parameter would you like to edit?", "Edit Parameter", JOptionPane.QUESTION_MESSAGE, null, editParameters, editParameters[0]);
                if (editParam == null) {
                    return;
                }
                String newValue = JOptionPane.showInputDialog(null, "What is the new value?", "New Value", JOptionPane.QUESTION_MESSAGE);
                if (newValue == null) {
                    return;
                }
                String result = sellerClient.editProduct("EDIT_PRODUCT", storeName, productName, editParam, newValue);
                JOptionPane.showMessageDialog(null, result, "Edit Product", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == deleteProductButton) {
                Object storeResult = sellerClient.getStores("GET_ALL_STORES");
                // If this seller doesn't have any stores, display error message
                if (storeResult instanceof String) { // Error
                    String errorMessage = (String) storeResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) storeResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store contains the product you\'d like to edit?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }
                // ********
                Object productResult = sellerClient.getProducts("GET_ALL_PRODUCTS", storeName);
                if (productResult instanceof String) {
                    String errorMessage = (String) productResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Product> products = (ArrayList<Product>) productResult;
                ArrayList<String> productNames = new ArrayList<>();
                for (Product pr: products) {
                    productNames.add(pr.getName());
                }
                String productName = (String) JOptionPane.showInputDialog(null, "Which product would you like to edit?", "Products", JOptionPane.QUESTION_MESSAGE, null, productNames.toArray(), productNames.get(0));
                if (productName == null) {
                    return;
                }

                String result = sellerClient.deleteProduct("DELETE_PRODUCT", storeName, productName);
                JOptionPane.showMessageDialog(null, result, "Delete Product", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == importProductsButton) {
              String filePath = JOptionPane.showInputDialog(null, "What is the name of the file that contains the products?", "File Name", JOptionPane.QUESTION_MESSAGE);
              if (filePath == null) {
                return;
              }
              Object storeResult = sellerClient.getStores("GET_ALL_STORES");
                // If this seller doesn't have any stores, display error message
                if (storeResult instanceof String) { // Error
                    String errorMessage = (String) storeResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) storeResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store contains the product you\'d like to edit?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }

              String result = sellerClient.importProducts("IMPORT_PRODUCTS", filePath, storeName);
              JOptionPane.showMessageDialog(null, result, "Import Products", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == exportProductsButton) {
                Object storeResult = sellerClient.getStores("GET_ALL_STORES");
                // If this seller doesn't have any stores, display error message
                if (storeResult instanceof String) { // Error
                    String errorMessage = (String) storeResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) storeResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store contains the product you\'d like to edit?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }

                String result = sellerClient.exportProducts("EXPORT_PRODUCTS", storeName);
                JOptionPane.showMessageDialog(null, result, "Export Products", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == viewCustomerShoppingCartsButton) {
                Object result = sellerClient.viewCustomerShoppingCarts("VIEW_CUSTOMER_SHOPPING_CARTS");
                if (result instanceof String) {
                    String errorMessage = (String) result;
                    displayErrorDialog(errorMessage);
                    return;
                }
                HashMap<String, ArrayList<String>> shoppingCarts = (HashMap<String, ArrayList<String>>) result;
                String info = "";
                for (String store : shoppingCarts.keySet()) {
                    result += store;
                    for (String saleInformation : shoppingCarts.get(store)) {
                        result += saleInformation;
                    }
                }
                displayMiscInfo("Customer Shopping Carts", info);

            } else if (e.getSource() == viewSalesByStoreButton) {
                Object result = sellerClient.viewSalesByStore("VIEW_SALES_BY_STORE");
                if (result instanceof String) {
                    String errorMessage = (String) result;
                    displayErrorDialog(errorMessage);
                    return;
                }
                HashMap<String, ArrayList<String>> salesByStore = (HashMap<String, ArrayList<String>>) result;
                String info = "";
                for (String store : salesByStore.keySet()) {
                    result += store;
                    for (String saleInformation : salesByStore.get(store)) {
                        result += saleInformation;
                    }
                }
                displayMiscInfo("Sales by Store", info);

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
                int sortSelection = Arrays.binarySearch(sortChoices, sortChoice);


                Object customerDashboardResult = sellerClient.sellerGetCustomersDashboard("CUSTOMERS_DASHBOARD", sortSelection, ascending);

                if (customerDashboardResult instanceof String) { // error was thrown
                    String errorMessage = (String) customerDashboardResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                
                ArrayList<String> customerDashboard = (ArrayList<String>) customerDashboardResult;
                Object[][] data = new Object[customerDashboard.size()][3];
                for (int i = 0; i < customerDashboard.size(); i++) {
                    String[] elems = customerDashboard.get(i).split(",");
                    System.arraycopy(elems, 0, data[i], 0, elems.length);
                }

                JTable table = new JTable(data, sortChoices);
                JScrollPane scrollPane = new JScrollPane(table);
                displayDashboard("Customers", scrollPane);

            } else if (e.getSource() == viewProductDashboardButton) {
                String[] sortChoices = {"Product Name", "Quantity Sold", "Total revenue"};
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


                Object productDashboardResult = sellerClient.sellerGetProductsDashboard("PRODUCTS_DASHBOARD", sortSelection, ascending);
                if (productDashboardResult instanceof String) { // error was thrown
                    String errorMessage = (String) productDashboardResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<String> productDashboard = (ArrayList<String>) productDashboardResult;
                Object[][] data = new Object[productDashboard.size()][3];
                for (int i = 0; i < productDashboard.size(); i++) {
                    String[] elems = productDashboard.get(i).split(",");
                    System.arraycopy(elems, 0, data[i], 0, elems.length);
                }

                JTable table = new JTable(data, sortChoices);
                JScrollPane scrollPane = new JScrollPane(table);
                displayDashboard("Products", scrollPane);

            } else if (e.getSource() == manageAccountButton) {
                // invoke the edit account GUI
                // all actions will call the corresponding method in the client class
                    // edit username
                    // edit password
                    // delete account

            } else if (e.getSource() == signOutButton) {
                try {
                    sellerClient.signOut();
                } catch (IOException ex) {
                }
            }
        }
    };

    public SellerGUI(SellerClient sellerClient, String email) {
        this.sellerClient = sellerClient;
        this.email = email;
        sellerFrame = new JFrame("Seller Page");
        JPanel buttonPanel = new JPanel(new GridLayout(8, 2, 5, 5));

        // Source: https://stackoverflow.com/questions/5328405/jpanel-padding-in-java
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        sellerFrame.setSize(650, 750);
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