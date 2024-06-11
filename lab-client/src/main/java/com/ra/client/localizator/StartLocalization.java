package com.ra.client.localizator;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import com.ra.client.view.StartView;

import javax.swing.*;


public class StartLocalization {

    private ResourceBundle bundle;
    private StartView view;

    public StartLocalization(StartView view, Locale locale){
        this.view = view;
        setLocaleAndBundle(locale);
        updateTexts();
    }

    private void setLocaleAndBundle(Locale locale) {
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("messages", locale);
    }
    public void updateTexts(JPanel panel) {
        for (java.awt.Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                System.out.println(button.getName());
                button.setText(bundle.getString(button.getText()));
            } else if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                System.out.println(label.getName());
                label.setText(bundle.getString(label.getText()));
            } else if (comp instanceof JPanel) {
                JPanel panel1 = (JPanel) comp;
                updateTexts(panel1);
            }
        }
        view.revalidate();
        view.repaint();
    }
    private void updateTexts() {
        System.out.println("1");
        updateTexts(view.getStartPanel());
        System.out.println("2");
        updateTexts(view.getSignUpPanel());
        System.out.println("3");
        updateTexts(view.getSignInPanel());

        view.revalidate();
        view.repaint();
    }
}
