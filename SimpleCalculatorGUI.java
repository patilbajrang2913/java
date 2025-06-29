import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculatorGUI extends JFrame implements ActionListener {
    private JTextField displayField;
    private double firstNumber = 0;
    private String operation = "";
    private boolean startNewNumber = true;

    public SimpleCalculatorGUI() {
        // Set up the main window
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the display field
        displayField = new JTextField();
        displayField.setFont(new Font("Arial", Font.PLAIN, 24));
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        add(displayField, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        // Button labels
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "CE", "", ""
        };

        // Create and add buttons
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            // Number or decimal point pressed
            if (startNewNumber) {
                displayField.setText(command.equals(".") ? "0." : command);
                startNewNumber = false;
            } else {
                if (!(command.equals(".") && displayField.getText().contains("."))) {
                    // Only allow one decimal point
                    displayField.setText(displayField.getText() + command);
                } else if (!command.equals(".")) {
                    displayField.setText(displayField.getText() + command);
                }
            }
        } else if (command.equals("C")) {
            // Clear everything
            displayField.setText("0");
            firstNumber = 0;
            operation = "";
            startNewNumber = true;
        } else if (command.equals("CE")) {
            // Clear entry
            displayField.setText("0");
            startNewNumber = true;
        } else if (command.equals("=")) {
            // Perform calculation
            if (!operation.isEmpty() && !startNewNumber) {
                double secondNumber = Double.parseDouble(displayField.getText());
                double result = calculate(firstNumber, secondNumber, operation);
                displayField.setText(String.valueOf(result));
                operation = "";
                startNewNumber = true;
            }
        } else {
            // Operation button pressed (+, -, *, /)
            if (!startNewNumber) {
                if (!operation.isEmpty()) {
                    // If there's already an operation pending, calculate it first
                    double secondNumber = Double.parseDouble(displayField.getText());
                    double result = calculate(firstNumber, secondNumber, operation);
                    displayField.setText(String.valueOf(result));
                    firstNumber = result;
                } else {
                    firstNumber = Double.parseDouble(displayField.getText());
                }
                operation = command;
                startNewNumber = true;
            }
        }
    }

    private double calculate(double num1, double num2, String op) {
        switch (op) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    return num1;
                }
                return num1 / num2;
            default:
                return num2;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalculatorGUI calculator = new SimpleCalculatorGUI();
            calculator.setVisible(true);
        });
    }
}