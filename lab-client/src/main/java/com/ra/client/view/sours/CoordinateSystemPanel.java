package com.ra.client.view.sours;
import javax.swing.*;
import java.awt.*;

public class CoordinateSystemPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Определение размеров JPanel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Определение размеров и масштаба системы координат
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;
        int scale = 25; // Масштаб

        // Рисование осей координат
        g.drawLine(0, centerY, panelWidth, centerY); // X-ось
        g.drawLine(centerX, 0, centerX, panelHeight); // Y-ось

        // Рисование делений на осях
        for (int x = 0; x < panelWidth; x += scale) {
            g.drawLine(x, centerY - 5, x, centerY + 5); // Разметка на X-оси
        }
        for (int y = 0; y < panelHeight; y += scale) {
            g.drawLine(centerX - 5, y, centerX + 5, y); // Разметка на Y-оси
        }

        // Рисование названий осей
        g.drawString("X", panelWidth - 10, centerY - 10);
        g.drawString("Y", centerX + 10, 10);

    }
}
