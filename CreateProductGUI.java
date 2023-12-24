import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CreateProductGUI extends JComponent {
    // Labels
    private JLabel nameLabel;
    private JLabel availableQuantityLabel;
    private JLabel originalPriceLabel;
    private JLabel descriptionLabel;
    private JLabel orderLimitLabel;
    private JLabel saleQuantityLabel;
    private JLabel salePriceLabel;

    // Fields
    private JTextField nameField;
    private JTextField availableQuantityField;
    private JTextField originalPriceField;
    private JTextField descriptionField;
    private JTextField orderLimitField;
    private JTextField saleQuantityField;
    private JTextField salePriceField;

    private JButton createProductButton;

    /**
     * Invokes the error message dialog to display a custom error message when an action taken by this seller fails.
     * 
     * @param errorMessage The custom error message retrieved through the client
     */
    public void displayErrorDialog(String errorMessage) {
        new ErrorMessageGUI(errorMessage);
    }

    public CreateProductGUI(SellerClient sellerClient, String storeName) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame createProductFrame = new JFrame("Create Product");
                createProductFrame.setSize(400, 400);
                createProductFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                createProductFrame.setLocationRelativeTo(null);

                // initialize all labels
                nameLabel = new JLabel("Product Name");
                nameLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

                availableQuantityLabel = new JLabel("Available Quantity");
                availableQuantityLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

                originalPriceLabel = new JLabel("Original Price");
                originalPriceLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

                descriptionLabel = new JLabel("Product Description");
                descriptionLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

                orderLimitLabel = new JLabel("Order Limit");
                orderLimitLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

                saleQuantityLabel = new JLabel("Sale Quantity");
                saleQuantityLabel.setBorder(new EmptyBorder(0, 10, 0, 0));

                salePriceLabel = new JLabel("Sale Price");
                salePriceLabel.setBorder(new EmptyBorder(0, 10, 0, 0));


                // initialize all fields
                nameField = new JTextField(10);
                nameField.setBorder(new EmptyBorder(0, 0, 0, 10));

                availableQuantityField  = new JTextField(10);
                availableQuantityField.setBorder(new EmptyBorder(0, 0, 0, 10));

                originalPriceField  = new JTextField(10);
                originalPriceField.setBorder(new EmptyBorder(0, 0, 0, 10));

                descriptionField  = new JTextField(10);
                descriptionField.setBorder(new EmptyBorder(0, 0, 0, 10));

                orderLimitField  = new JTextField(10);
                orderLimitField.setBorder(new EmptyBorder(0, 0, 0, 10));

                saleQuantityField  = new JTextField(10);
                saleQuantityField.setBorder(new EmptyBorder(0, 0, 0, 10));

                salePriceField  = new JTextField(10);
                salePriceField.setBorder(new EmptyBorder(0, 0, 0, 10));


                // initialize button
                createProductButton = new JButton("Create Product");
                createProductButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] createProductResult = sellerClient.createNewProduct(storeName, nameField.getText(),
                            availableQuantityField.getText(), originalPriceField.getText(), descriptionField.getText(), orderLimitField.getText(), saleQuantityField.getText(), salePriceField.getText());
                        if (createProductResult[0].equals("SUCCESS")) {
                            JOptionPane.showMessageDialog(null, createProductResult[1],
                                    "Create Product", JOptionPane.INFORMATION_MESSAGE);
                            createProductFrame.dispose();
                        } else {
                            displayErrorDialog((String) createProductResult[1]);
                            createProductFrame.dispose();
                        }
                    }
                });

                JPanel textPanel = new JPanel(new GridLayout(0, 2, 5, 5));
                textPanel.add(nameLabel);
                textPanel.add(nameField);
                textPanel.add(availableQuantityLabel);
                textPanel.add(availableQuantityField);
                textPanel.add(originalPriceLabel);
                textPanel.add(originalPriceField);
                textPanel.add(descriptionLabel);
                textPanel.add(descriptionField);
                textPanel.add(orderLimitLabel);
                textPanel.add(orderLimitField);
                textPanel.add(saleQuantityLabel);
                textPanel.add(saleQuantityField);
                textPanel.add(salePriceLabel);
                textPanel.add(salePriceField);

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(createProductButton);

                createProductFrame.add(textPanel, BorderLayout.CENTER);
                createProductFrame.add(buttonPanel, BorderLayout.SOUTH);

                createProductFrame.setVisible(true);

            }
        });
    }
}