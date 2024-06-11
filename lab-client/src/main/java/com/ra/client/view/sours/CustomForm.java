package com.ra.client.view.sours;

import com.ra.common.enum_.TicketType;
import com.ra.common.enum_.Color;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

@Getter
public class CustomForm extends JPanel{
    private final LinkedHashMap<JTextField, String> textFieldDefaults = new LinkedHashMap<>();
    private final LinkedHashMap<JCheckBox, Boolean> checkBoxDefaults = new LinkedHashMap<>();
    private final LinkedHashMap<JComboBox<String>, String> comboBoxes = new LinkedHashMap<>();
    private final LinkedHashMap<JLabel, String> labelDefaults = new LinkedHashMap<>();

    @Setter
    private Long id;

    public CustomForm(){
        setLayout(new GridBagLayout());
//        setPreferredSize(new Dimension(400, 100));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 7, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Adding components to the frame
        addLabelAndTextField(gbc, "Name:", 0);
        addLabelAndTextField(gbc, "Coordinates_X:", 1);
        addLabelAndTextField(gbc, "Coordinates_Y:", 2);
        addLabelAndLabel(gbc, "Creation date:", "", 3);
        addLabelAndTextField(gbc, "Price:", 4);
        addLabelAndTextField(gbc, "Comment:", 5);
        addLabelAndCheckBox(gbc, "Refundable:", 6);

        addLabelAndComboBox(gbc, "Type:", 7, new String[] {"", TicketType.USUAL.name(), TicketType.BUDGETARY.name(), TicketType.VIP.name(), TicketType.CHEAP.name()});
        addLabelAndTextField(gbc, "Birthday:", 8);
        addLabelAndComboBox(gbc, "Hair_Color:", 9, new String[] {"","-", Color.BLACK.name(), Color.BLUE.name(), Color.GREEN.name()});
        addLabelAndTextField(gbc, "Location_X:", 10);
        addLabelAndTextField(gbc, "Location_Y:", 11);
        addLabelAndTextField(gbc, "Location_Z:", 12);
        addLabelAndTextField(gbc, "Location_Name:", 13);

    }
    private void addLabelAndLabel(GridBagConstraints gbc, String labelText, String labelText2,  int yPos) {
        JLabel label = new JLabel(labelText);
        label.setName(labelText);
        JLabel label2 = new JLabel(labelText2);
        labelDefaults.put(label2, labelText2);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(label, gbc);
        gbc.gridx = 1;
        this.add(label2, gbc);
    }
    private void addLabelAndTextField(GridBagConstraints gbc, String labelText, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setName(labelText);
        JTextField textField = new JTextField(20);
        textFieldDefaults.put(textField, ""); // Сохранение значения по умолчанию
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(label, gbc);
        gbc.gridx = 1;
        this.add(textField, gbc);
    }

    private void addLabelAndCheckBox( GridBagConstraints gbc, String labelText, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setName(labelText);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setBackground(new java.awt.Color(238,238,238));
        checkBox.setOpaque(true);
        checkBox.setBorder(new LineBorder(new java.awt.Color(51,51,51)));
        checkBoxDefaults.put(checkBox, false); // Сохранение значения по умолчанию
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(label, gbc);
        gbc.gridx = 1;
        this.add(checkBox, gbc);
    }
    private void addLabelAndComboBox(GridBagConstraints gbc, String labelText, int yPos, String[] options) {
        JLabel label = new JLabel(labelText);
        label.setName(labelText);
        JComboBox<String> comboBox = new CustomComboBox<>(options);
        comboBoxes.put(comboBox, labelText);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(label, gbc);
        gbc.gridx = 1;
        this.add(comboBox, gbc);
    }


    public void cleaForm(){
        textFieldDefaults.replaceAll((t, v) -> "");
        checkBoxDefaults.replaceAll((c, v) -> false);
        comboBoxes.replaceAll((c, v) -> "");
        labelDefaults.replaceAll((c, v) -> "");
        fillDefaults();
    }

    public void setTextFieldAndCheckBoxDefaults(String[] ticket, Long id){
        this.id = id;
        System.out.println(Arrays.toString(ticket));
        int i = 0;
        for (JTextField tf: textFieldDefaults.keySet()){
            if (i == 3 ||  i == 9)
                i += 1;
            if (i == 6)
                i += 2;
            textFieldDefaults.put(tf, ticket[i+1]);
            i += 1;
        }
        checkBoxDefaults.replaceAll((c, v) -> Boolean.valueOf(ticket[6+1]));
        labelDefaults.replaceAll((c, v) -> ticket[3+1]);
        int j = 7;
        for (JComboBox<String> cob: comboBoxes.keySet()){
            comboBoxes.put(cob, ticket[j+1]);
            j += 2;
        }
        fillDefaults();
    }
    private void fillDefaults() {
        for (HashMap.Entry<JTextField, String> entry : textFieldDefaults.entrySet()) {
            entry.getKey().setText(entry.getValue());
        }
        for (HashMap.Entry<JCheckBox, Boolean> entry : checkBoxDefaults.entrySet()) {
            entry.getKey().setSelected(entry.getValue());
        }
        for (HashMap.Entry<JComboBox<String>, String> entry : comboBoxes.entrySet()) {
            entry.getKey().setSelectedItem(entry.getValue());
        }
        for (HashMap.Entry<JLabel, String> entry : labelDefaults.entrySet()) {
            entry.getKey().setText(entry.getValue());
        }
    }
    public List<String> collectFormData() {
        List<String> formData = new ArrayList<>();

        // Получение данных из текстовых полей
        for (Map.Entry<JTextField, String> entry : textFieldDefaults.entrySet()) {
            JTextField textField = entry.getKey();
            formData.add(textField.getText());
        }

        // Получение данных из чекбоксов
        for (Map.Entry<JCheckBox, Boolean> entry : checkBoxDefaults.entrySet()) {
            JCheckBox textField = entry.getKey();
            formData.add(String.valueOf(textField.isSelected()));
        }
            // Получение данных из выпадающих списков
        for (Map.Entry<JComboBox<String>, String> entry : comboBoxes.entrySet()) {
            JComboBox<String> textField = entry.getKey();
            formData.add((String) textField.getSelectedItem());
        }
        System.out.println(formData);
        formData.add(5, formData.get(10));
        System.out.println(formData);
        formData.add(6, formData.get(12));
        System.out.println(formData);
        formData.add(8, formData.get(14));
        System.out.println(formData);
        formData.remove(12);
        formData.remove(12);
        formData.remove(12);
            // Вывод собранных данных в консоль
        System.out.println(formData);
        return formData;
    }
}
