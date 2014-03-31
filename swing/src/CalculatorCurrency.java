import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CalculatorCurrency extends JFrame {
    private ArrayList<Data> data;

    private final int N = 5;
    private JComboBox[] comboBoxes;
    private JTextField[] textFields;
    private JButton[] buttons;

    private String stringNoCurrency = "(нет валюты)";

    public CalculatorCurrency() throws FileNotFoundException {
        super("Калькулятор валют");
        setContentPane(createAndShowGUI());
    }

    private String genStringForList(String code, String fullName) {
        return code + " (" + fullName + ")";
    }

    private JPanel createAndShowGUI() throws FileNotFoundException {
        Reader reader = new Reader("data.txt");
        data = reader.getData();
        data.add(new Data("RUR", 1.0, "Российский рубль"));
        String[] comboBoxData = new String[data.size() + 1];
        comboBoxData[0] = stringNoCurrency;
        for (int i = 0; i < data.size(); i++) {
            comboBoxData[i + 1] = genStringForList(data.get(i).getCode(), data.get(i).getFullName());
        }

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel = new JPanel(new GridLayout(5, 3, 5, 5));
        mainPanel.add(panel);

        comboBoxes = new JComboBox[N];
        textFields = new JTextField[N];
        buttons = new JButton[N];

        for (int i = 0; i < N; i++) {
            comboBoxes[i] = new JComboBox<String>(comboBoxData);
            comboBoxes[i].setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
            textFields[i] = new JTextField("");
            textFields[i].setSize(10, 3);
            buttons[i] = new JButton("Пересчитать");

            panel.add(comboBoxes[i]);
            panel.add(textFields[i]);
            panel.add(buttons[i]);
        }

        for (int i = 0; i < N; i++) {
            buttons[i].addActionListener(new ButtonActionListener(i));
        }
        return mainPanel;
    }

    String[] errors = {
            "",
            "Вы не выбрали валюту",
            "Число не может быть отрицательным",
            "Введённая Вами информация не является числом",
            "Вы ничего не ввели"
    };
    private class ButtonActionListener implements ActionListener {
        private final int id;

        public ButtonActionListener(int id) {
            this.id = id;
        }

        private double getFactor(String item) {
            for (Data d: data) {
                if (item.equals(genStringForList(d.getCode(), d.getFullName()))) {
                    return d.getFactor();
                }
            }
            return 0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int errorLevel = checkErrors();
            if (errorLevel > 0) {
                JOptionPane.showMessageDialog(null, errors[errorLevel], "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedItem = (String) comboBoxes[id].getSelectedItem();
            double cost = Double.parseDouble(textFields[id].getText());
            double cost_rur = getFactor(selectedItem) * cost;

            for (int i = 0; i < N; i++) {
                if (i == id) {
                    continue;
                }
                String item = (String) comboBoxes[i].getSelectedItem();
                if (item.equals(stringNoCurrency)) {
                    textFields[i].setText("");
                    continue;
                }
                double result = cost_rur / getFactor(item);
                result = (long) (result * 1000.0) / 1000.0;
                textFields[i].setText(Double.toString(result));
            }
        }

        private int checkErrors() {
            if (comboBoxes[id].getSelectedItem().equals(stringNoCurrency)) {
                return 1;
            }
            if (textFields[id].getText().length() == 0) {
                return 4;
            }
            double cost;
            try {
                cost = Double.parseDouble(textFields[id].getText());
            } catch (Exception e) {
                return 3;
            }
            if (cost < 0.0) {
                return 2;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CalculatorCurrency calculatorCurrency = null;
                try {
                    calculatorCurrency = new CalculatorCurrency();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                assert calculatorCurrency != null;
                calculatorCurrency.setDefaultCloseOperation(EXIT_ON_CLOSE);
                calculatorCurrency.pack();
                calculatorCurrency.setLocationRelativeTo(null);
                calculatorCurrency.setResizable(false);
                calculatorCurrency.setVisible(true);
            }
        });
    }
}
