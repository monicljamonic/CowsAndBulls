package com.company;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BullsAndCows {

    public static final Random RANDOM = new Random();
    private int number;
    private int nbGuesses;
    private boolean guessed;

    private JTextPane textPane;
    private JTextField textField;

    private void generateNumber(){
        do {
               number = RANDOM.nextInt(9000) + 1000;
        }while (hasDublicates(number));

        System.out.println("[CHEAT]" + number);
    }

    private boolean hasDublicates(int number){
        boolean [] digits = new boolean[10];

        while (number > 0){
            int last = number % 10;

            if (digits[last])
                return true;

            digits[last] = true;
            number = number / 10;

        }

        return false;
    }

    private int[] bullsAndCows(int entered){
        int bulls = 0, cows = 0;
        String enteredStr = String.valueOf(entered);
        String numberStr = String.valueOf(number);

        for (int i = 0; i < numberStr.length(); i++){
            char c = enteredStr.charAt(i);

            if (c == numberStr.charAt(i)){
                bulls++;
            }else if (numberStr.contains(String.valueOf(c))){
                cows++;

            }
        }
        return new int[]{bulls, cows};

    }

    private void play() {
        JFrame frame = new JFrame("Bulls and Cows on SSaurel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        JPanel buttonsPanel = new JPanel();
        JLabel inputLabel = new JLabel("Input: ");
        buttonsPanel.add(inputLabel, BorderLayout.LINE_START);

        textField = new JTextField(15);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonsPanel.add(textField, BorderLayout.LINE_START);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okClick();

            }
        });

        buttonsPanel.add(okButton, BorderLayout.LINE_START);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int();
            }
        });

        buttonsPanel.add(newGameButton, BorderLayout.LINE_END);
        contentPane.add(buttonsPanel, BorderLayout.PAGE_END);

        textPane = new JTextPane();
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);

        SimpleAttributeSet bSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(bSet, 20);
        StyledDocument doc = textPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), bSet, false);

        contentPane.add(scrollPane, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(600,500));

        Dimension objDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int coordX = (objDimension.width - frame.getWidth()) / 2;
        int coordY = (objDimension.height - frame.getHeight()) / 2;
        frame.setLocation(coordX, coordY);

        frame.pack();
        frame.setVisible(true);

        int();

    }

    private void okClick(){
        String userInput = textField.getText();
        int entered = -1;

        try {
            entered = Integer.parseInt(userInput);
        } catch (Exception e){
            textField.setText("");
            JOptionPane.showMessageDialog(null, "You must enter an integer", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (hasDublicates(entered) || entered < 1000 || entered > 9999) {
            textField.setText("");
            JOptionPane.showMessageDialog(null, "You must enter an integer with no dublicates digits and with 4-digits", "Error", JOptionPane.ERROR_MESSAGE);
            return;

        }
        nbGuesses++;

        int[] bullsAndCows = bullsAndCows(entered);

        if (bullsAndCows[0] == 4){
            guessed = true;
        } else {
            updateText (entered + "\t\t\t\t" + bullsAndCows[0] + "Bulls and" + bullsAndCows[1] + "Cows");

        }
        if (guessed) {
            updateText("\n" + entered +"\n\nYou won after " + nbGuesses + "guesses!");
        }
        textField.setText("");
    }

    private void updateText(String msg){
        textPane.setText(textPane.getText() + "\n" + msg);
    }
    private void init(){
        generateNumber();
        nbGuesses = 0;
        guessed = false;
        textPane.setText("You must guess a 4-digits number with no dublicate digits");
        textField.setText("");
    }

    public static void main(String[] args) {
        BullsAndCows bullsAndCows = new BullsAndCows();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                bullsAndCows.play();
            }
        });
    }
    }

}
