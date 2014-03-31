/*Задание 2.
Выполнено: Кобыленко Д., группа 2743.

Вариант 1. (А - Л). Данные представляют собой таблицу курсов иностранных валют к рублю. Данные представлены в
текстовом файле, который содержит список валют в виде строк, содержащих код и название валюты, а также курс валюты
к рублю, например:
AUD    33.0155     Австралийский доллар
AZN    46.7753    Азербайджанский манат
BGN    25.9668    Болгарский лев
BRL    15.4975     Бразильский реал
DKK     6.8043     Датская крона
USD    36.6391    Доллар США
EUR    50.7635    ЕВРО
Программа должна позволять пользователю на основании этих данных рассчитывать кросс-курсы разных валют (включая
российский рубль), при этом диалог должен иметь приблизительно такой вид.
Всего в диалоге задействовано 5 строк с элементами управления. Пользователь вводит в некоторых строках интересующие
его валюты, например, как показано на картинке - российский рубль, доллар США и датскую крону (выбор в остальных
строках - строка “(нет валюты)”), далее он вводит в одной из строк сумму в указанной валюте и нажимает на кнопку
“Пересчитать”. Если данные введены правильно, то программа рассчитывает соответствующие суммы в валютах остальных
выбранных стран и выводит результаты в соответствующих окнах ввода. Если данные введены неправильно (ошибка в формате
числа, не выбрана валюта, отрицательная сумма и т.п.), то пользователю сообщают об этом в отдельном информационном
модальном окне.*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CalculatorCurrency extends JFrame {
    private ArrayList<Data> data; //Хранит считанные данные
    private final int N = 5;
    private JComboBox[] comboBoxes;
    private JTextField[] textFields;
    private String stringNoCurrency = "(нет валюты)"; //Добавляем строку, которой нет в файле

    public CalculatorCurrency() throws FileNotFoundException {
        super("Калькулятор валют");
        setContentPane(createAndShowGUI());
    }

/*Метод, "склеивающий" код и полное название валюты*/

    private String genStringForList(String code, String fullName) {
        return code + " (" + fullName + ")";
    }

    private JPanel createAndShowGUI() throws FileNotFoundException {
        FileReader reader = new FileReader("data.txt");
        data = reader.getData();
        data.add(new Data("RUR", 1.0, "Российский рубль")); //Добавляем рубль к данным
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
        JButton[] buttons = new JButton[N];

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

/*Создадим массив возможных ошибок*/
    
    String[] errors = {"", "Вы не выбрали валюту", "Число не может быть отрицательным",
            "Введённая Вами информация не является числом", "Вы ничего не ввели"};

    private class ButtonActionListener implements ActionListener {
        private final int id;

        public ButtonActionListener(int id) {
            this.id = id;
        }

        private double getFactor(String item) {
            for (Data d : data) {
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
