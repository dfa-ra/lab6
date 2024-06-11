package com.ra.client.view.sours;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomComboBox<String> extends JComboBox<String> {
    public CustomComboBox(String[] items) {
        super(items);
        setRenderer(new CenteredComboBoxRenderer());
        setUI(new RoundedComboBoxUI());
    }
    private static class CenteredComboBoxRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            renderer.setHorizontalAlignment(SwingConstants.CENTER);
            return renderer;
        }
    }
    private static class RoundedComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            return new JButton() {
                @Override
                public int getWidth() {
                    return 0; // Устанавливаем ширину стрелки на 0, чтобы скрыть ее
                }

                @Override
                public void paint(Graphics g) {

                }
            };

        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = (BasicComboPopup) super.createPopup();
            popup.setBorder(BorderFactory.createLineBorder(new Color(51,51,51)));
            return popup;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(comboBox.getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, comboBox.getWidth(), comboBox.getHeight(), 15, 15));
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(51, 51, 51));
            g2.draw(new RoundRectangle2D.Float(0, 0, comboBox.getWidth() - 1, comboBox.getHeight() - 1, 15, 15));
        }

    }
}
