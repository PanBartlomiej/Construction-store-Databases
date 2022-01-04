package com.company;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();

        JLabel napis1 = new JLabel();
        napis1.setText("Menager Sklepu");
        napis1.setHorizontalAlignment(JLabel.CENTER);
        napis1.setVerticalAlignment(JLabel.TOP);
        napis1.setFont(new Font("Arial",Font.PLAIN,20));
        napis1.setForeground(new Color(180,180,10));
        //napis1.setBounds(100,0,100,30);

        myFrame.add(napis1);

   }
}
