package com.ra.client.view;

import com.ra.client.view.sours.CustomComboBox;
import com.ra.client.view.sours.CustomForm;
import com.ra.client.view.sours.CustomTable;
import com.ra.client.view.sours.RoundedButton;
import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.awt.BorderLayout.CENTER;

@Getter
public class Home extends JFrame {

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



    public Home(){
        Font font = null;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("lab-client/src/main/resources/ZCOOLKuaiLe-Regular.ttf")).deriveFont(Font.BOLD,50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


        // Создаем основное окно
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1980, 1080);
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
        userButton.setPreferredSize(new Dimension(99, 29));
        settingsButton = new RoundedButton("Settings", 20);
        settingsButton.setPreferredSize(new Dimension(99, 29));
        homeButton = new RoundedButton("Home", 20);
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
        JLabel titleLabel = new JLabel("information", SwingConstants.CENTER);
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
        addButton.setPreferredSize(new Dimension(90, 29));
        addButton.setBackground(new Color(238,238,238));
        removeButton = new RoundedButton("Remove", 20);
        removeButton.setPreferredSize(new Dimension(90, 29));
        removeButton.setBackground(new Color(238,238,238));

        updateButton = new RoundedButton("Update", 20);
        updateButton.setPreferredSize(new Dimension(90, 29));
        updateButton.setBackground(new Color(238,238,238));
        clearButton = new RoundedButton("Clear", 20);
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

        JTabbedPane tabbedPane = new JTabbedPane();
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
        ticketsPanel = new JPanel();
        ticketsPanel.setBorder(BorderFactory.createLineBorder(new Color(224,224,224)));
        ticketsPanel.setLayout(null); // Используем абсолютное позиционирование
        JPanel tabbedPanel = new JPanel(new BorderLayout());

        tabbedPanel.add(ticketsPanel, CENTER);


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
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

        String[] removeItems = {"remove greater", "remove lower"};
        removeComboBox = new CustomComboBox<>(removeItems);
        removeComboBox.setBounds(50, 100, 200, 49);
        removeComboBox.setPreferredSize(new Dimension(150, 29));

        String[] sortItems = {"sort by id", "sort by type"};
        sortComboBox = new CustomComboBox<>(sortItems);
        sortComboBox.setBounds(50, 100, 200, 49);
        sortComboBox.setPreferredSize(new Dimension(150, 29));

        String[] groupingItems = {"grouping by type"};
        groupingComboBox = new CustomComboBox<>(groupingItems);
        groupingComboBox.setBounds(50, 100, 200, 49);
        groupingComboBox.setPreferredSize(new Dimension(150, 29));


        bottomPanel.add(removeComboBox);
        bottomPanel.add(sortComboBox);
        bottomPanel.add(groupingComboBox);
        add(bottomPanel, BorderLayout.SOUTH);


        // Отображаем окно
        setVisible(true);
    }

    public void goInvisible(){
        setVisible(false);
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
