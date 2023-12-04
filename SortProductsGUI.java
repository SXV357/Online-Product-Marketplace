import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
/**
 * Project 5 - SortProductsGUI.java
 * 
 * The interface associated with a customer wanting to sort products in the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 *         Miller, Oliver Long
 * 
 * @version December 4, 2023
 */
public class SortProductsGUI extends JComponent {
    public SortProductsGUI(String[] initialProducts, CustomerClient customerClient) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame sortProductsFrame = new JFrame("Sort Products");
                sortProductsFrame.setSize(350, 350);
                sortProductsFrame.setLocationRelativeTo(null);
                sortProductsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel mainPanel = new JPanel(new BorderLayout());
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JComboBox<String> products = new JComboBox<>();
                for (String product: initialProducts) {
                    products.addItem(product);
                }
                JButton sortByPriceButton = new JButton("Sort By Price");
                sortByPriceButton.addActionListener(new ActionListener() {
                    @Override 
                    public void actionPerformed(ActionEvent e) {
                        Object[] sortProductsResult = customerClient.sortProducts("price");
                        if (sortProductsResult[0].equals("ERROR")) {
                            new ErrorMessageGUI((String) sortProductsResult[1]);
                            return;
                        } else {
                            String[] originalProducts = ((String) sortProductsResult[1]).split("\n");
                            String[] modifiedProducts = Arrays.copyOfRange(originalProducts, 1, originalProducts.length);
                            products.removeAllItems();
                            for (String p: modifiedProducts) {
                                products.addItem(p);
                            }
                        }
                    }
                });
                JButton sortByQuantityButton = new JButton("Sort By Quantity");
                sortByQuantityButton.addActionListener(new ActionListener() {
                    @Override 
                    public void actionPerformed(ActionEvent e) {
                        Object[] sortProductsResult = customerClient.sortProducts("quantity");
                        if (sortProductsResult[0].equals("ERROR")) {
                            new ErrorMessageGUI((String) sortProductsResult[1]);
                            return;
                        } else {
                            String[] originalProducts = ((String) sortProductsResult[1]).split("\n");
                            String[] modifiedProducts = Arrays.copyOfRange(originalProducts, 1, originalProducts.length);
                            products.removeAllItems();
                            for (String p: modifiedProducts) {
                                products.addItem(p);
                            }
                        }
                    }
                });
                buttonPanel.add(sortByPriceButton);
                buttonPanel.add(sortByQuantityButton);
                mainPanel.add(products, BorderLayout.NORTH);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);
                sortProductsFrame.add(mainPanel);
                sortProductsFrame.setVisible(true);
            }
        });
    }
}
