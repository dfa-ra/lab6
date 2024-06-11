package com.ra.client.localizator;

import com.ra.client.view.MainPageView;
import com.ra.client.view.StartView;
import com.ra.client.view.sours.CustomForm;
import com.sun.glass.ui.View;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainPageLocalization {
    private ResourceBundle bundle;
    private MainPageView view;
    @Getter
    private Locale locale;
    public MainPageLocalization(MainPageView view, Locale locale){
        this.view = view;
        this.locale = locale;
        setLocaleAndBundle(locale);
        updateTexts();
    }

    public void updateLocation(){
        String selectedLanguage = (String) view.getSettingsPanel().getLanguageComboBox().getSelectedItem();
        assert selectedLanguage != null;
        if (selectedLanguage.equals("English (Australia)")) {
            setLocaleAndBundle(new Locale("en"));
        } else if (selectedLanguage.equals("Русский")) {
            setLocaleAndBundle(new Locale("ru"));
        }else if (selectedLanguage.equals("Български")) {
            setLocaleAndBundle(new Locale("bul"));
        }else if (selectedLanguage.equals("Português")) {
            setLocaleAndBundle(new Locale("port"));
        }
        updateTexts();
    }

    private void setLocaleAndBundle(Locale locale) {
        this.locale = locale;
        System.out.println(this.locale);
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("messages", locale);
    }
    public void updateTexts(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            try {
                if (comp instanceof JButton) {
                    JButton button = (JButton) comp;
                    button.setText(bundle.getString(button.getName()));
                } else if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    System.out.println("============" + label.getName());
                    try {
                        label.setText(bundle.getString(label.getName()));
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    if (label.getText().length() > 0)
                        try {
                            label.setText(bundle.getString(label.getName().substring(0, label.getText().length() - 1)) + label.getText().substring(label.getText().length() - 1));
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    try {
                        label.setText(bundle.getString(label.getName()));
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                } else if (comp instanceof JTabbedPane) {
                    JTabbedPane tabbedPane = (JTabbedPane) comp;
                    tabbedPane.setTitleAt(0, bundle.getString("map"));
                    tabbedPane.setTitleAt(1, bundle.getString("table"));

                } else if (comp instanceof JComboBox<?>) {
                    JComboBox<String> comboBox = (JComboBox<String>) comp;
                    System.out.println("combobox items: " + comboBox.getItemCount());
                    for (int i = 0; i < comboBox.getItemCount(); i++) {
                        String key = comboBox.getName() + i;
                        try {
                            String translatedText = bundle.getString(key);
                            comboBox.insertItemAt(translatedText, i);
                            comboBox.removeItemAt(i + 1); // Удаляем старый элемент после добавления нового
                        } catch (Exception e) {
                            System.out.println("Error updating item " + key + ": " + e.getMessage());
                        }
                    }
                }else if (comp instanceof JScrollPane scrollPane){
                    System.out.println("go new ScrollPane");
                    for (Component comp2 : scrollPane.getComponents()){
                        System.out.println(comp2.getClass());
                        if (comp2 instanceof JViewport) {
                            System.out.println("go new ViewPort");
                            JViewport panel2 = (JViewport) comp2;
                            updateTexts((JPanel) panel2.getComponents()[0]);
                        }
                    }
                }else if (comp instanceof JPanel) {
                    System.out.println("go new Panel");
                    JPanel panel1 = (JPanel) comp;
                    updateTexts(panel1);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        view.revalidate();
        view.repaint();
    }
    private void updateTexts() {
        System.out.println("1)");
        updateTexts(view.getUserPanel());
        System.out.println("2)");
        updateTexts(view.getHomePanel());
        System.out.println("3)");
        updateTexts(view.getSettingsPanel());
        view.revalidate();
        view.repaint();
    }
}
