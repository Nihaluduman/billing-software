import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Item {
    String name;
    double price;
    int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return name + " (Price: " + price + ", Quantity: " + quantity + ", Total: " + getTotalPrice() + ")";
    }
}

public class BillingSoftware extends JFrame implements ActionListener {
    private JTextField nameField, priceField, quantityField;
    private JTextArea billArea;
    private ArrayList<Item> items;

    public BillingSoftware() {
        items = new ArrayList<>();

        setTitle("Billing Software");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Item Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Item Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(this);
        inputPanel.add(addButton);

        JButton billButton = new JButton("Generate Bill");
        billButton.addActionListener(this);
        inputPanel.add(billButton);

        add(inputPanel, BorderLayout.NORTH);

        // Bill Area
        billArea = new JTextArea();
        billArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(billArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add Item")) {
            String name = nameField.getText();
            double price;
            int quantity;

            try {
                price = Double.parseDouble(priceField.getText());
                quantity = Integer.parseInt(quantityField.getText());
                items.add(new Item(name, price, quantity));

                // Clear fields
                nameField.setText("");
                priceField.setText("");
                quantityField.setText("");
                JOptionPane.showMessageDialog(this, "Item added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid price and quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equals("Generate Bill")) {
            generateBill();
        }
    }

    private void generateBill() {
        StringBuilder bill = new StringBuilder();
        double totalAmount = 0;

        bill.append("----- BILL -----\n");
        for (Item item : items) {
            bill.append(item.toString()).append("\n");
            totalAmount += item.getTotalPrice();
        }
        bill.append("-----------------\n");
        bill.append(String.format("Total Amount: %.2f\n", totalAmount));
        bill.append("-----------------");

        billArea.setText(bill.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BillingSoftware::new);
    }
}