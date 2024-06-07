package com.ra.client.controlers;

import com.ra.client.Handler;
import com.ra.client.command.InvokerClient;
import com.ra.client.view.Home;
import com.ra.client.view.SettingsView;
import com.ra.client.view.sours.CustomForm;
import com.ra.client.view.sours.CustomTable;
import com.ra.client.view.sours.RoundedButton;
import com.ra.common.User;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.sample.Ticket;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class HomeController {
    private static SettingsController settingsController = new SettingsController(new SettingsView());
    private final DefaultTableModel model;
    private final CustomTable table;
    private final Home view;
    private final Handler handler;
    private final User user;
    private final InvokerClient invk = new InvokerClient();
    private final HashMap<Long, JButton> ticketButtons = new HashMap<>();
    public HomeController(Home view, Handler handler, User user) throws IOException, ParseException {
        this.view = view;
        this.handler = handler;
        this.user = user;
        table = view.getTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        model = view.getTable().getMyModel();
        handler.sendRequest(new Request("show", "-1"));
        Response response = handler.dataReception();
        if (response.getColllection() != null) {
            initTableAndCart(response.getColllection());
        }
//        view.getAddButton().addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Добавление новой колонки
//                model.addColumn("columnName");
//                setWidth(200);
//            }
//        });
        actions();

    }
    private void actions(){
        view.getTable().getHeader().addMouseListener(getMouseAdapterToTicketButton());
        view.getClearButton().addActionListener(e -> CustomForm.cleaForm());
        view.getUpdateButton().addActionListener(e -> updateTicket());
        view.getAddButton().addActionListener(e -> addTicket());
        view.getRemoveButton().addActionListener(e->removeTicket());
        view.getSettingsButton().addActionListener(e -> {
            view.goInvisible();
            HomeController.settingsController.setVisible();
        });
    }

    private void removeTicket(){
        if (!invk.checkPermission(user, String.valueOf(view.getContentPanel().getId()))) {
            JOptionPane.showMessageDialog(view, "you cannot change this object", "Warning", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Response res = invk.creationReqest(user, "remove_by_id " + view.getContentPanel().getId(), null);
        System.out.println("----------");
        System.out.println(res.isSuccessfully());
        System.out.println("----------");
        System.out.println(res.getAdditional());
        if (res.isSuccessfully()) {
            view.getTicketsPanel().remove(ticketButtons.get(view.getContentPanel().getId()));
            ticketButtons.remove(view.getContentPanel().getId());

            view.getTicketsPanel().revalidate();
            view.getTicketsPanel().repaint();


            TableColumnModel columnModel = table.getColumnModel();
            int columnIndex = -1;

            // Поиск индекса столбца по его заголовку
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                System.out.println("-----------");
                System.out.println(columnModel.getColumn(i).getHeaderValue());
                System.out.println(view.getContentPanel().getId());
                System.out.println("-----------");
                if (columnModel.getColumn(i).getHeaderValue().equals(String.valueOf(view.getContentPanel().getId()))) {
                    columnIndex = i;
                    break;
                }
            }

            // Если столбец найден, удаляем его
            if (columnIndex != -1) {
                table.removeColumn(columnModel.getColumn(columnIndex));
            } else {
                System.out.println("Столбец с заголовком '" + view.getContentPanel().getId() + "' не найден.");
            }
            table.revalidate();
            table.repaint();
        }
    }

    private void addTicket(){
        List<String> lines = CustomForm.collectFormData();
        System.out.println(lines);
        String joinedLines = String.join("\n", lines);

        StringReader stringReader = new StringReader(joinedLines);

        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Response resp = invk.creationReqest(user, "add", bufferedReader);
        System.out.println(resp.getAdditional());
        if (resp != null) {
            System.out.println("start add");
            handler.sendRequest(new Request("show", resp.getAdditional()));
            Response response = handler.dataReception();
            model.addColumn(response.getColllection()[0].getId());
            for (int i = 0; i < response.getColllection()[0].mytoString().length; i++) {
                model.setValueAt(response.getColllection()[0].mytoString()[i], i, model.getColumnCount() - 1);
            }
            setWidth(200);
            JButton ticketButton;
            ticketButton = new RoundedButton(String.valueOf(response.getColllection()[0].getId()), 90, new Color(8, 124, 8));
            ticketButtons.put(response.getColllection()[0].getId(), ticketButton);
            ticketButton.setFont(new Font("Serif", Font.PLAIN, 10));
            ticketButton.setMargin(new Insets(1, 1, 1, 1));
            ticketButton.setBounds((int) response.getColllection()[0].getCoordinates().getX(), (int) response.getColllection()[0].getCoordinates().getY(), 30, 30);
            ticketButton.addActionListener(getActionListnerToTicketButton());
            view.getTicketsPanel().add(ticketButton);
            view.getTicketsPanel().revalidate();
            view.getTicketsPanel().repaint();
            view.getContentPanel().setId(response.getColllection()[0].getId());
        }
    }

    private void updateTicket(){
        System.out.println(view.getContentPanel().getId());
        if (!invk.checkPermission(user, String.valueOf(view.getContentPanel().getId()))) {
            JOptionPane.showMessageDialog(view, "you cannot change this object", "Warning", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        System.out.println("ok");
        System.out.println("Collected Form Data:");
        List<String> lines = CustomForm.collectFormData();
        System.out.println(lines);
        String joinedLines = String.join("\n", lines);

        StringReader stringReader = new StringReader(joinedLines);

        BufferedReader bufferedReader = new BufferedReader(stringReader);

        if (invk.creationReqest(user, "update " + view.getContentPanel().getId(), bufferedReader) != null){
            int columnIndex = -1;
            for (int i = 0; i < table.getColumnCount(); i++) {
                if (table.getColumnName(i).equals(String.valueOf(view.getContentPanel().getId()))) {
                    columnIndex = i;
                    break;
                }
            }

            // Если столбец найден, меняем значение в каждой строке
            if (columnIndex != -1) {
                int j = 0;
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (i == 3) continue;
                    table.setValueAt(lines.get(j), i, columnIndex);
                    j+=1;
                }
            }

            ticketButtons.get(view.getContentPanel().getId()).setBounds(Integer.parseInt(lines.get(1)), Integer.parseInt(lines.get(2)), 30, 30);;
        }

    }

    private void setWidth(int width){
        for (int j = 0; j < model.getColumnCount(); j++) {
            TableColumn column = table.getColumnModel().getColumn(j);
            System.out.println(column.getWidth());
            column.setMinWidth(width); // Установите необходимую вам ширину
            column.setPreferredWidth(width);
            System.out.println(column.getWidth());
        }
    }

    private void initTableAndCart(Ticket[] tickets){

        for (Ticket ticket : tickets) {
            JButton ticketButton;
            if (invk.checkPermission(user, String.valueOf(ticket.getId())))
                ticketButton = new RoundedButton(String.valueOf(ticket.getId()), 90, new Color(8, 124, 8));
            else
                ticketButton = new RoundedButton(String.valueOf(ticket.getId()), 90, new Color(124,8,8));
            ticketButtons.put(ticket.getId(), ticketButton);
            ticketButton.setFont(new Font("Serif", Font.PLAIN, 10));
            ticketButton.setMargin(new Insets(1, 1, 1, 1));
            ticketButton.setBounds((int) ticket.getCoordinates().getX(), (int) ticket.getCoordinates().getY(), 30, 30);
            ticketButton.addActionListener(getActionListnerToTicketButton());
            view.getTicketsPanel().add(ticketButton);

            model.addColumn(ticket.getId());
            for (int i = 0; i < ticket.mytoString().length; i++) {
                model.setValueAt(ticket.mytoString()[i], i, model.getColumnCount() - 1);
            }
        }
        setWidth(200);
    }

    private ActionListener getActionListnerToTicketButton(){
        return e -> {
            JButton button = (JButton) e.getSource();
            setInformationPlace(button.getText());
        };
    }

    private MouseAdapter getMouseAdapterToTicketButton(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = view.getTable().columnAtPoint(e.getPoint());
                if (col > 0) {
                    JTableHeader header = (JTableHeader) e.getSource();
                    setInformationPlace(table.getColumnName(header.columnAtPoint(e.getPoint())));
                }
            }
        };
    }

    private void setInformationPlace(String text){
        handler.sendRequest(new Request("show", text));
        Response response = handler.dataReception();
        if (response.isSuccessfully())
            view.getContentPanel().setTextFieldAndCheckBoxDefaults(response.getColllection()[0].mytoString(), response.getColllection()[0].getId());
        else System.out.println(response.getAdditional());
    }
}
