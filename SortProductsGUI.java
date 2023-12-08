import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Project 5 - SortProductsGUI.java
 * 
 * The interface associated with a customer wanting to sort products in the application.
 *
 * @author Shafer Anthony Hofmann, Qihang Gan, Shreyas Viswanathan, Nathan Pasic
 * Miller, Oliver Long
 * 
 * @version December 6, 2023
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
                JPanel buttonPanel = new JPanel();

                JButton sortByPriceButton = new JButton("Sort By Price");
                JButton sortByQuantityButton = new JButton("Sort By Quantity");
                buttonPanel.add(sortByPriceButton);
                buttonPanel.add(sortByQuantityButton);

                DefaultListModel<String> listModel = new DefaultListModel<>();
                JList<String> list = new JList<>(listModel);

                for (String product : initialProducts) {
                    listModel.addElement(product);
                }

                sortByPriceButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] sortProductsResult = customerClient.sortProducts("price");
                        if (sortProductsResult[0].equals("ERROR")) {
                            new ErrorMessageGUI((String) sortProductsResult[1]);
                            return;
                        } else {
                            String[] originalProducts = ((String) sortProductsResult[1]).split("\n");
                            listModel.clear();
                            for (String product : originalProducts) {
                                listModel.addElement(product);
                            }
                        }
                    }
                });
                sortByQuantityButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] sortProductsResult = customerClient.sortProducts("quantity");
                        if (sortProductsResult[0].equals("ERROR")) {
                            new ErrorMessageGUI((String) sortProductsResult[1]);
                            return;
                        } else {
                            String[] originalProducts = ((String) sortProductsResult[1]).split("\n");
                            listModel.clear();
                            for (String product : originalProducts) {
                                listModel.addElement(product);
                            }
                        }
                    }
                });

                mainPanel.add(new JScrollPane(list), BorderLayout.CENTER);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);
                sortProductsFrame.add(mainPanel);
                sortProductsFrame.setVisible(true);
            }
        });
    }
}