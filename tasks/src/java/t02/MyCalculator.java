package t02;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MyCalculator extends JFrame {
    private static JPanel panel = new JPanel(); //создаем панель
    final String[] elements = new String[]{"RUR (Российский рубль)", "AUD (Австрийский доллар)", "AZN (Азербайджанский манат)",
            "BGN (Болгарский лев)", "BRL (Бразильский реал", "DKK (Датская крона)", "USD (Доллар США)", "EUR (ЕВРО)", "(Нет валюты)"};
    private JTextField[] fields = new JTextField[5];
    private JButton[] buttons = new JButton[5];
    private JComboBox[] boxes = new JComboBox[5];
    private final double[] constant = {1.0, 33.0155, 46.7753, 25.9668, 15.4975, 6.8043, 36.6391, 50.7635, 0.0};
    Map<String,Double> map = new HashMap<String, Double>();

    private int buttonNumber = 0;

    public MyCalculator() {
        super("Калькулятор валют"); //заголовок
        setSize(420, 240); //размер окна
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //завершение программы при закрытии
        panel.setLayout(null);

        panel.setLayout((new GridLayout(5, 3, 10, 15)));
        Border border = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        panel.setBorder(border);


        for (int i = 0; i < 9; i++){
            map.put(elements[i], constant[i]);
        }

        makeBoxes();





        setContentPane(panel);
    }

    private void makeBoxes() {
        for (int i = 0; i < 5; i++) {
            boxes[i] = new JComboBox(elements);
            boxes[i].setSelectedIndex(8);
            panel.add(boxes[i]);
            fields[i] = new JTextField(10);
            panel.add(fields[i]);
            buttons[i] = new JButton("Пересчитать");
            panel.add(buttons[i]);
        }
    }
    private void makeActions(){
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k = 0;
                ololo(k);
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k = 1;
                ololo(k);
            }
        });
        for (int i=0; i<5; i++) {
            final int k = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int k = 3;
                    convert(k);

                }
            });
        }
    }

    public void convert(int buttonNumber){
        try{
        double data = Double.parseDouble(fields[buttonNumber].getText());
        double result = map.get(boxes[buttonNumber].getSelectedIndex());
            for (int i=0; i< 5; i++){

            }
        }catch(Exception ex){
            // Здесь должно быть всплывающее окно!
        };
    }
}


//        JComboBox combo1 = new JComboBox(elements);
//        combo1.setSelectedIndex(8);
//        panel.add(combo1);
//
//        JTextField field1 = new JTextField(10);
//        panel.add(field1);
//
//        JButton convertButton1 = new JButton("Пересчитать");
//        panel.add(convertButton1);
//
//        JComboBox combo2 = new JComboBox(elements);
//        combo2.setSelectedIndex(8);
//        panel.add(combo2);
//
//        JTextField field2 = new JTextField(10);
//        panel.add(field2);
//
//        JButton convertButton2 = new JButton("Пересчитать");
//        panel.add(convertButton2);
//
//        JComboBox combo3 = new JComboBox(elements);
//        combo3.setSelectedIndex(8);
//        panel.add(combo3);
//
//        JTextField field3 = new JTextField(10);
//        panel.add(field3);
//
//        JButton convertButton3 = new JButton("Пересчитать");
//        panel.add(convertButton3);
//
//        JComboBox combo4 = new JComboBox(elements);
//        combo4.setSelectedIndex(8);
//        panel.add(combo4);
//
//        JTextField field4 = new JTextField(10);
//        panel.add(field4);
//
//        JButton convertButton4 = new JButton("Пересчитать");
//        panel.add(convertButton4);
//
//        JComboBox combo5 = new JComboBox(elements);
//        combo5.setSelectedIndex(8);
//        panel.add(combo5);
//
//        JTextField field5 = new JTextField(10);
//        panel.add(field5);
//
//        JButton convertButton5 = new JButton("Пересчитать");
//        panel.add(convertButton5);
