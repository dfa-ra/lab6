package com.ra.client.view.sours;

import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

@Getter
public class CustomTable extends JTable {
    private final DefaultTableModel myModel;
    public final JTableHeader header;
    public CustomTable(){

        Object[][] data = {{}};

        // Данные таблицы
        String [] columnNames = {
                "Tickets",
                "name",
                "coordinates x",
                "coordinates y",
                "creation date",
                "price",
                "comment",
                "refundable",
                "type",
                "birthday",
                "hair color",
                "Location x",
                "Location y",
                "Location z",
                "Location name"
        };

        // Создание модели таблицы
        myModel = new DefaultTableModel(data, columnNames);
        this.setModel(myModel);
        setIgnoreRepaint(false);

        // Настройка таблицы
        setPreferredScrollableViewportSize(getPreferredSize());
        setFillsViewportHeight(true);
        // Установка шрифта для ячеек
        Font font = new Font("Serif", Font.PLAIN, 18);
        setFont(font);
        setRowHeight(30);

        // Установка шрифта для заголовков таблицы
        header = getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 20));



    }
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Запрет редактирования ячеек
    }

}
