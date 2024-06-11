package com.ra.client.view.mainPagePanels;

import com.ra.client.view.sours.CustomComboBox;
import com.ra.client.view.sours.CustomForm;
import com.ra.client.view.sours.CustomTable;
import com.ra.client.view.sours.RoundedButton;
import com.ra.client.view.sours.CoordinateSystemPanel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.awt.BorderLayout.CENTER;

@Getter
public class HomePanel extends JPanel {

    private final JComboBox<String> removeComboBox;
    private final JComboBox<String> sortComboBox;
    private final JComboBox<String> groupingComboBox;
    private final JPanel ticketsPanel;
    private final CustomTable table;
    private final CustomForm contentPanel;
    private final JButton updateButton;
    private final JButton addButton;
    private final JButton removeButton;
    private final JScrollPane scrollPane;
    private final JButton clearButton;
    private final RoundedButton userButton;
    private final RoundedButton settingsButton;
    private final RoundedButton homeButton;
    private final JLabel log;
    private final JTabbedPane tabbedPane;
    private final JLabel titleLabel;


    public HomePanel(CardLayout cardLayout){
        super(cardLayout);
        Font font = null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("lab-client/src/main/resources/ZCOOLKuaiLe-Regular.ttf")).deriveFont(Font.BOLD,50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


        // Создаем основное окно
        setLayout(new BorderLayout());

        // Верхняя панель с логотипом и кнопками
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        JLabel logoLabel = new JLabel("TickeT", SwingConstants.CENTER);
        if (font != null) {
            System.out.println("here");
            logoLabel.setFont(font);
        }
        topPanel.add(logoLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userButton = new RoundedButton("USER", 20);
        userButton.setName("USER");
        userButton.setPreferredSize(new Dimension(99, 29));
        settingsButton = new RoundedButton("Settings", 20);
        settingsButton.setName("settings");
        settingsButton.setPreferredSize(new Dimension(99, 29));
        homeButton = new RoundedButton("Home", 20);
        homeButton.setName("Home");
        homeButton.setPreferredSize(new Dimension(99, 29));
        buttonPanel.add(userButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(homeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());


        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(400, 600));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Создаем заголовок
        titleLabel = new JLabel("information", SwingConstants.CENTER);
        titleLabel.setName("information");
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        titleLabel.setPreferredSize(new Dimension(200,100));

        // Создаем панель для содержимого
        contentPanel = new CustomForm();

        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(224,224,224)));
        // Создаем панель для кнопок
        JPanel buttonInfoPanel = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Отступы вокруг компонентов

        buttonInfoPanel.setLayout(new GridBagLayout());
        buttonInfoPanel.setPreferredSize(new Dimension(200, 59));
        // Создаем кнопки
        addButton = new RoundedButton("Add", 20);
        addButton.setName("Add");
        addButton.setPreferredSize(new Dimension(90, 29));
        addButton.setBackground(new Color(238,238,238));
        removeButton = new RoundedButton("Remove", 20);
        removeButton.setName("Remove");
        removeButton.setPreferredSize(new Dimension(90, 29));
        removeButton.setBackground(new Color(238,238,238));

        updateButton = new RoundedButton("Update", 20);
        updateButton.setName("Update");
        updateButton.setPreferredSize(new Dimension(90, 29));
        updateButton.setBackground(new Color(238,238,238));
        clearButton = new RoundedButton("Clear", 20);
        clearButton.setName("Clear");
        clearButton.setPreferredSize(new Dimension(90, 29));
        clearButton.setBackground(new Color(238,238,238));
        // Добавляем кнопки на панель
        addComponent(buttonInfoPanel, addButton, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        addComponent(buttonInfoPanel, clearButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        addComponent(buttonInfoPanel, updateButton, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        addComponent(buttonInfoPanel, removeButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        // Добавляем компоненты на основной панель
        infoPanel.add(titleLabel, BorderLayout.NORTH);

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        infoPanel.add(scrollPane, CENTER);
        infoPanel.add(buttonInfoPanel, BorderLayout.SOUTH);

        centerPanel.add(infoPanel, BorderLayout.EAST);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.tabsOverlapBorder", true);
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                // Не рисуем границу панели
            }
        });
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                // Получаем графический контекст
                Graphics2D g2d = (Graphics2D) g.create();

                // Устанавливаем цвет фона вкладки
                if (isSelected) {
                    g2d.setColor(new Color(224,224,224)); // Цвет для выбранной вкладки
                } else {
                    g2d.setColor(new Color(238,238,238)); // Цвет для остальных вкладок
                }

                // Заполняем прямоугольник фоном вкладки
                g2d.fillRect(x-2, y, w+4, h);

                g2d.dispose();
            }
        });
        // Центральная панель с тикетами и информацией
        ticketsPanel = new CoordinateSystemPanel();
        ticketsPanel.setPreferredSize(new Dimension(1550, 850));
        ticketsPanel.setBorder(BorderFactory.createLineBorder(new Color(224,224,224)));
        ticketsPanel.setLayout(null); // Используем абсолютное позиционирование
        JPanel tabbedPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane1 = new JScrollPane(ticketsPanel);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tabbedPanel.add(scrollPane1, CENTER);


        JPanel tabbedPanel2 = new JPanel(new BorderLayout());
        table = new CustomTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane2 = new JScrollPane(table);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbedPanel2.add(scrollPane2, CENTER);
        // Добавление таблицы в панель с прокруткой

        tabbedPane.addTab("Map", tabbedPanel);
        tabbedPane.addTab("Table", tabbedPanel2);

        tabbedPane.setBackground(new Color(238,238,238));
        centerPanel.add(tabbedPane, CENTER);
        add(centerPanel, CENTER);

        // Нижняя панель с кнопками
        JPanel comboboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] removeItems = {"remove_item0", "remove_item1"};
        removeComboBox = new CustomComboBox<>(removeItems);
        removeComboBox.setName("remove_item");
        removeComboBox.setBounds(50, 100, 200, 49);
        removeComboBox.setPreferredSize(new Dimension(150, 29));

        String[] sortItems = {"sort_item0", "sort_item1", "sort_item2"};
        sortComboBox = new CustomComboBox<>(sortItems);
        sortComboBox.setName("sort_item");
        sortComboBox.setBounds(50, 100, 200, 49);
        sortComboBox.setPreferredSize(new Dimension(150, 29));

        String[] groupingItems = {"grouping_item0", "grouping_item1"};

        groupingComboBox = new CustomComboBox<>(groupingItems);
        groupingComboBox.setName("grouping_item");
        groupingComboBox.setBounds(50, 100, 200, 49);
        groupingComboBox.setPreferredSize(new Dimension(150, 29));

        JPanel logPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel logLabel = new JLabel("Log:");
        log = new JLabel(" ");
        logPanel.add(logLabel);
        logPanel.add(log);


        comboboxPanel.add(removeComboBox);
        comboboxPanel.add(sortComboBox);
        comboboxPanel.add(groupingComboBox);

        JPanel bottomPanel = new JPanel(new GridBagLayout());

        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        gbc.gridx = 0; // Столбец 0
        gbc.gridy = 0; // Строка 0
        gbc.weightx = 1.0; // Расширяется по горизонтали
        gbc.anchor = GridBagConstraints.WEST; // Привязка к левому краю
        gbc.insets = new Insets(0, 10, 0, 0); // Отступы
        bottomPanel.add(comboboxPanel, gbc);

        // Настройки для правой кнопки
        gbc.gridx = 1; // Столбец 1
        gbc.gridy = 0; // Строка 0
        gbc.weightx = 1.0; // Расширяется по горизонтали
        gbc.anchor = GridBagConstraints.EAST; // Привязка к правому краю
        gbc.insets = new Insets(0, 0, 0, 10); // Отступы
        bottomPanel.add(logPanel, gbc);

        add(bottomPanel, BorderLayout.SOUTH);
        // Отображаем окно
    }

    private void addComponent(JPanel panel, Component comp, int x, int y, int width, int height, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = new Insets(5, 5, 5, 5); // Отступы вокруг компонента
        panel.add(comp, gbc);
    }
}
