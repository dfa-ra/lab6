package com.ra.client.view.sours;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private int radius; // Радиус закругления углов
    public Color color;
    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        this.color = new Color(51,51,51);
        setContentAreaFilled(false); // Устанавливаем прозрачный фон для кнопки
        setBackground(new Color(238,238,238));
    }

    public RoundedButton(String text, int radius, Color color) {
        super(text);
        this.radius = radius;
        this.color = color;
        setContentAreaFilled(false); // Устанавливаем прозрачный фон для кнопки
        setBackground(new Color(238,238,238));
    }

    // Переопределяем метод paintComponent для рисования кнопки с закругленными углами
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray); // Цвет фона кнопки при нажатии
        } else {
            g.setColor(getBackground()); // Цвет фона кнопки
        }
        // Рисуем прямоугольник с закругленными углами
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        super.paintComponent(g);
    }
   // Переопределяем метод paintBorder для рисования рамки с закругленными углами
   @Override
   protected void paintBorder(Graphics g) {
        g.setColor(getForeground()); // Цвет рамки
        // Рисуем окружность вокруг кнопки
        g.setColor(color);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
   }

    // Создаем макет для обработки событий клика мыши внутри кнопки
   Shape shape;
   @Override
   public boolean contains(int x, int y) {
       // Проверяем, находится ли точка (x, y) в пределах формы кнопки
       if (shape == null || !shape.getBounds().equals(getBounds())) {
           shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
       }
       return shape.contains(x, y);
   }
}
