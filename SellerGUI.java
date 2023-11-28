import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
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
 * 
 * @version November 28, 2023
 */
public class SellerGUI extends JComponent {

    private DisplayInformationGUI displayInfo = new DisplayInformationGUI();
    private final String[] SORT_ORDER_CHOICES = {"Ascending", "Descending"};
    private SellerClient sellerClient;
    private JFrame sellerFrame;
    private String email;
    private JLabel welcomeUserLabel;
    private JButton manageAccountButton;
    private JButton signOutButton;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SellerGUI(null, "");
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
            JFrame sellerMiscInfoFrame = displayInfo.displaySellerMiscInfo(informationType, data);
            sellerMiscInfoFrame.setVisible(true);
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
            // Perform certain actions based on which button is clicked
            if (e.getSource() == createStoreButton) {
                String newStoreName = JOptionPane.showInputDialog(null, "What is the new store\'s name?", "New Store Name", JOptionPane.QUESTION_MESSAGE);
                String[] result = sellerClient.createNewStore(newStoreName);
                if (result[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, result[1], "Create Store", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(result[1]);
                }

            } else if (e.getSource() == editStoreButton) {
                Object getStoresResult = sellerClient.getStores();
                // If this seller doesn't have any stores, display error message
                if (getStoresResult instanceof String) { // Error
                    String errorMessage = (String) getStoresResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) getStoresResult;
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
                String[] modifyStoreResult = sellerClient.modifyStoreName(prevStoreName, newStoreName);
                if (modifyStoreResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, modifyStoreResult[1], "Edit Store", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(modifyStoreResult[1]);
                }

            } else if (e.getSource() == deleteStoreButton) {
                Object getStoresResult = sellerClient.getStores();
                // If this seller doesn't have any stores, display error message
                if (getStoresResult instanceof String) { // Error
                    String errorMessage = (String) getStoresResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) getStoresResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store would you like to delete?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }

                String[] deleteStoreResult = sellerClient.deleteStore(storeName);
                if (deleteStoreResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, deleteStoreResult[1], "Delete Store", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(deleteStoreResult[1]);
                }

            } else if (e.getSource() == createProductButton) {
                Object getStoresResult = sellerClient.getStores();
                // If this seller doesn't have any stores, display error message
                if (getStoresResult instanceof String) { // Error
                    String errorMessage = (String) getStoresResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) getStoresResult;
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
                String[] createProductResult = sellerClient.createNewProduct(storeName, productName, availableQuantity, price, description);
                if (createProductResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, createProductResult[1], "Create Product", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(createProductResult[1]);
                }

            } else if (e.getSource() == editProductButton) {
                Object getStoresResult = sellerClient.getStores();
                // If this seller doesn't have any stores, display error message
                if (getStoresResult instanceof String) { // Error
                    String errorMessage = (String) getStoresResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) getStoresResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store contains the product you\'d like to edit?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }
                // ********
                Object getProductsResult = sellerClient.getProducts(storeName);
                if (getProductsResult instanceof String) {
                    String errorMessage = (String) getProductsResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Product> products = (ArrayList<Product>) getProductsResult;
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
                String[] editProductResult = sellerClient.editProduct(storeName, productName, editParam.toLowerCase(), newValue);
                if (editProductResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, editProductResult[1], "Edit Product", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(editProductResult[1]);
                }

            } else if (e.getSource() == deleteProductButton) {
                Object getStoresResult = sellerClient.getStores();
                // If this seller doesn't have any stores, display error message
                if (getStoresResult instanceof String) { // Error
                    String errorMessage = (String) getStoresResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) getStoresResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store contains the product you\'d like to edit?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }
                // ********
                Object getProductsResult = sellerClient.getProducts(storeName);
                if (getProductsResult instanceof String) {
                    String errorMessage = (String) getProductsResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Product> products = (ArrayList<Product>) getProductsResult;
                ArrayList<String> productNames = new ArrayList<>();
                for (Product pr: products) {
                    productNames.add(pr.getName());
                }
                String productName = (String) JOptionPane.showInputDialog(null, "Which product would you like to delete?", "Products", JOptionPane.QUESTION_MESSAGE, null, productNames.toArray(), productNames.get(0));
                if (productName == null) {
                    return;
                }

                String[] deleteProductResult = sellerClient.deleteProduct(storeName, productName);
                if (deleteProductResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, deleteProductResult[1], "Delete Product", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(deleteProductResult[1]);
                }

            } else if (e.getSource() == importProductsButton) {
              String filePath = JOptionPane.showInputDialog(null, "What is the name of the file that contains the products?", "File Name", JOptionPane.QUESTION_MESSAGE);
              if (filePath == null) {
                return;
              }
              Object getStoresResult = sellerClient.getStores();
                // If this seller doesn't have any stores, display error message
                if (getStoresResult instanceof String) { // Error
                    String errorMessage = (String) getStoresResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) getStoresResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store would you like to import the products into?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }

              String[] importProductsResult = sellerClient.importProducts(filePath, storeName);
              if (importProductsResult[0].equals("SUCCESS")) {
                JOptionPane.showMessageDialog(null, importProductsResult[1], "Import Products", JOptionPane.INFORMATION_MESSAGE);
              } else {
                displayErrorDialog(importProductsResult[1]);
              }

            } else if (e.getSource() == exportProductsButton) {
                Object getStoresResult = sellerClient.getStores();
                // If this seller doesn't have any stores, display error message
                if (getStoresResult instanceof String) { // Error
                    String errorMessage = (String) getStoresResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                ArrayList<Store> stores = (ArrayList<Store>) getStoresResult;
                ArrayList<String> storeNames = new ArrayList<>();
                for (Store st: stores) {
                    storeNames.add(st.getStoreName());
                }
                String storeName = (String) JOptionPane.showInputDialog(null, "Which store would you like to export the products from?", "Stores", JOptionPane.QUESTION_MESSAGE, null, storeNames.toArray(), storeNames.get(0));
                if (storeName == null) {
                    return;
                }

                String[] exportProductsResult = sellerClient.exportProducts(storeName);
                if (exportProductsResult[0].equals("SUCCESS")) {
                    JOptionPane.showMessageDialog(null, exportProductsResult[1], "Export Products", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displayErrorDialog(exportProductsResult[1]);
                }

            } else if (e.getSource() == viewCustomerShoppingCartsButton) {
                Object getCustomerShoppingCartsResult = sellerClient.viewCustomerShoppingCarts();
                if (getCustomerShoppingCartsResult instanceof String) {
                    String errorMessage = (String) getCustomerShoppingCartsResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                HashMap<String, ArrayList<String>> shoppingCarts = (HashMap<String, ArrayList<String>>) getCustomerShoppingCartsResult;
                String info = "";
                for (String store : shoppingCarts.keySet()) {
                    info += store;
                    for (String saleInformation : shoppingCarts.get(store)) {
                        info += saleInformation;
                    }
                }
                displayMiscInfo("Customer Shopping Carts", info);

            } else if (e.getSource() == viewSalesByStoreButton) {
                Object getSalesByStoreResult = sellerClient.viewSalesByStore();
                if (getSalesByStoreResult instanceof String) {
                    String errorMessage = (String) getSalesByStoreResult;
                    displayErrorDialog(errorMessage);
                    return;
                }
                HashMap<String, ArrayList<String>> salesByStore = (HashMap<String, ArrayList<String>>) getSalesByStoreResult;
                String info = "";
                for (String store : salesByStore.keySet()) {
                    info += store;
                    for (String saleInformation : salesByStore.get(store)) {
                        info += saleInformation;
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

                Object customerDashboardResult = sellerClient.sellerGetCustomersDashboard(sortSelection, ascending);

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

                Object productDashboardResult = sellerClient.sellerGetProductsDashboard(sortSelection, ascending);
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
                // Behavior TBD

            } else if (e.getSource() == signOutButton) {
                // Behavior TBD
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