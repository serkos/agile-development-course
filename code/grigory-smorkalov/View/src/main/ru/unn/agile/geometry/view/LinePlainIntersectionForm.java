package ru.unn.agile.geometry.view;

import javax.swing.*;

/**
 * Created by geser on 10.01.14.
 */
public class LinePlainIntersectionForm {
    private JButton calcButton;
    private JTextField lineP1X;
    private JTextField lineP1Y;
    private JTextField lineP2X;
    private JTextField lineP2Y;
    private JTextField plainPointX;
    private JTextField plainPointY;
    private JTextField plainOrtX;
    private JTextField plainOrtY;
    private JTextField resultX;
    private JTextField resultY;
    private JPanel mainPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("LinePlainIntersectionForm");
        frame.setContentPane(new LinePlainIntersectionForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
