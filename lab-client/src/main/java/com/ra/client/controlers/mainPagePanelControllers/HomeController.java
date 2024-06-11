package com.ra.client.controlers.mainPagePanelControllers;

import com.ra.client.command.InvokerClient;
import com.ra.client.view.mainPagePanels.HomePanel;
import com.ra.client.view.sours.CustomTable;
import com.ra.client.view.sours.RoundedButton;
import com.ra.common.User;
import com.ra.common.communication.Request;
import com.ra.common.communication.Response;
import com.ra.common.enum_.Price;
import com.ra.common.enum_.TicketType;
import com.ra.common.sample.Ticket;
import lombok.Setter;

import javax.accessibility.AccessibleHyperlink;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;

public class HomeController {
    private static final int THREAD_POOL_SIZE = 5;
    private final ExecutorService jumper;

    private final DefaultTableModel model;
    private final CustomTable table;
    private final HomePanel view;
    private final User user;
    private final HashMap<Long, JButton> ticketButtons = new HashMap<>();
    private final InvokerClient invk;
    private final CardLayout cardLayout;
    private final TableRowSorter<TableModel> sorter;
    private final JPanel mainPanel;
    private HashMap<JButton, Timer> buttonTimers = new HashMap<>();
    @Setter
    private static Locale locale;
    private String[] plan = {"", "", ""};

    public HomeController(HomePanel view, InvokerClient invk, User user, CardLayout cardLayout, JPanel mainPanel) throws IOException, ParseException {
        this.view = view;
        this.user = user;
        this.invk = invk;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        table = view.getTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);
        model = view.getTable().getMyModel();
        jumper = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);


        Thread checkThread = new Thread(() -> {
            Ticket[] oldCollection = null;
            while (true){
                Response response = invk.creationReqest(user, "show -1", null);
                if (response.getColllection() != null && !Arrays.equals(oldCollection, response.getColllection())){
                    delTable();
                    delTicketButtons();
                    System.out.println("IIIINNNNNNNIIIIIIIIITTTTTTTTTTTT");
                    initTicketButton(response.getColllection());
                    initTable(response.getColllection());
                    if (plan[2].equals("grouping_item1")) {
                        groupingByType();
                        view.getLog().setText("complete");
                    } else if (plan[2].equals("grouping_item0")) {
                        setDefaultBG();
                        view.getLog().setText("complete");
                    }
                    view.revalidate();
                    view.repaint();
                }
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                oldCollection = response.getColllection();
            }
        });
        checkThread.setDaemon(true);
        checkThread.start();
        Thread action = new Thread(() -> actions());
        action.setDaemon(true);
        action.start();
    }

    private void actions(){
        table.getHeader().addMouseListener(getMouseAdapterToTicketButton());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                if (column == 0) {
                    setInformationPlace((String) table.getValueAt(row, 0));
                }
            }
        });

        view.getClearButton().addActionListener(e -> view.getContentPanel().cleaForm());
        view.getUpdateButton().addActionListener(e -> updateTicket());
        view.getAddButton().addActionListener(e -> addTicket());
        view.getRemoveButton().addActionListener(e -> removeTicket());
        view.getRemoveComboBox().addActionListener(e -> {
            String selectedItem = (String) view.getRemoveComboBox().getSelectedItem();
            System.out.println("----------------------" + locale);
            if (selectedItem.equals(ResourceBundle.getBundle("messages", locale).getString("remove_item0"))) {
                invk.creationReqest(user, "remove_lower " + view.getContentPanel().getId(), null);
                plan[0] = "remove_lower";
            }else if (selectedItem.equals(ResourceBundle.getBundle("messages", locale).getString("remove_item1"))) {
                invk.creationReqest(user, "remove_greater " + view.getContentPanel().getId(), null);
                plan[0] = "remove_greater";
            }
            delTable();
            delTicketButtons();

            Response response = invk.creationReqest(user, "show -1", null);
            if (response.getColllection() != null) {
                initTable(response.getColllection());
                initTicketButton(response.getColllection());
            } else {
                view.getLog().setText("no response received from the server");
            }
        });
        view.getSortComboBox().addActionListener(e -> {
            table.getRowSorter().setSortKeys(null);
            String selectedItem = (String) view.getSortComboBox().getSelectedItem();
            System.out.println("----------------------" + locale);
            Response response;
            assert selectedItem != null;
            if (selectedItem.equals(ResourceBundle.getBundle("messages", locale).getString("sort_item2"))) {
                response = invk.creationReqest(user, "print_ascending_type", null);
                plan[1] = "print_ascending_type";
            }else if (selectedItem.equals(ResourceBundle.getBundle("messages", locale).getString("sort_item1"))) {
                response = invk.creationReqest(user, "print_ascending_id", null);
                plan[1] = "print_ascending_id";
            }else {
                response = invk.creationReqest(user, "show -1", null);
                plan[1] = "";
            }
            delTable();

            if (response.getColllection() != null) {
                initTable(response.getColllection());
            } else {
                view.getLog().setText("no response received from the server");
            }
        });
        view.getGroupingComboBox().addActionListener(e -> {getBGforTickets();
        });
        view.getSettingsButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "settings");
        });
        view.getUserButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "user");
        });
    }

    private synchronized void delTable(){
        TableColumnModel columnModel = table.getColumnModel();
        // Удаляем столбцы начиная с последнего, кроме первого
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
            table.revalidate();
            table.repaint();
        }

//        while (model.getColumnCount() > 1) {
//            model.setColumnCount(1);
//        }
    }

    private synchronized void delTicketButtons(){
        view.getTicketsPanel().removeAll();
        ticketButtons.clear();
        view.getTicketsPanel().revalidate();
        view.getTicketsPanel().repaint();
    }

    private synchronized void initTable(Ticket[] tickets){
        if (!Objects.equals(plan[0], "")) {
            System.out.println("++++++++++++" + plan[0] + " " + view.getContentPanel().getId());
            invk.creationReqest(user, plan[0] + " " + view.getContentPanel().getId(), null);
            tickets = invk.creationReqest(user, "show -1", null).getColllection();
        }
        if (!Objects.equals(plan[1], ""))
            tickets = invk.creationReqest(user, plan[1], null).getColllection();

        for (Ticket ticket : tickets) {
            model.addRow(new Object[model.getColumnCount()]);
            System.out.println("add");
            for (int i = 0; i < ticket.mytoString().length; i++) {
                model.setValueAt(ticket.mytoString()[i], model.getRowCount() - 1, i);
            }
        }

        setWidth(200);
        view.getLog().setText("complete");
        plan[0] = "";
        table.revalidate();
        table.repaint();
    }

    private synchronized void initTicketButton(Ticket[] tickets){

        for (Ticket ticket : tickets) {
            System.out.println("init ticket: " + ticket.getId());
            JButton ticketButton;
            if (invk.checkPermission(user, String.valueOf(ticket.getId())))
                ticketButton = new RoundedButton(String.valueOf(ticket.getId()), 90, new Color(8, 124, 8));
            else
                ticketButton = new RoundedButton(String.valueOf(ticket.getId()), 90, new Color(124,8,8));
            ticketButtons.put(ticket.getId(), ticketButton);
            ticketButton.setFont(new Font("Serif", Font.PLAIN, (int) (5 + 3 * ticket.getPrice() / Price.MAX_PRICE.getPrice())));
            ticketButton.setMargin(new Insets(1, 1, 1, 1));
            ticketButton.setBounds(getXForTicket(ticket),
                    getYForTickket(ticket),
                    20 + (int) ((10 * ticket.getPrice()) / Price.MAX_PRICE.getPrice()),
                    20 + (int) ((10 * ticket.getPrice()) / Price.MAX_PRICE.getPrice()));
            ticketButton.addActionListener(getActionListnerToTicketButton());
            view.getTicketsPanel().add(ticketButton);

            System.out.println("-----------------------------");

        }
        view.getLog().setText("complete");
    }

    private void getBGforTickets(){
        String selectedItem = (String) view.getGroupingComboBox().getSelectedItem();
        assert selectedItem != null;
        if (selectedItem.equals(ResourceBundle.getBundle("messages", locale).getString("grouping_item1"))) {
            groupingByType();
            view.getLog().setText("complete");
            plan[2] = "grouping_item1";
        }
        else if (selectedItem.equals(ResourceBundle.getBundle("messages", locale).getString("grouping_item0"))) {
            setDefaultBG();
            view.getLog().setText("complete");
            plan[2] = "grouping_item0";
        }
    }

    private void setDefaultBG(){
        for (JButton button : ticketButtons.values()) {
            button.setBackground(new Color(238,238,238));
        }
    }

    private void groupingByType(){
        invk.getConnectHendler().sendRequest(new Request("show", "-1"));
        Response response = invk.getConnectHendler().dataReception();
        if (response.getColllection() != null) {
            Ticket[] tickets = response.getColllection();
            for (Ticket ticket : tickets) {
                System.out.println(TicketType.getColor(ticket.getType()));
                ticketButtons.get(ticket.getId()).setBackground(TicketType.getColor(ticket.getType()));
            }
        } else {
            view.getLog().setText("no response received from the server");
        }
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

            String targetValue = String.valueOf(view.getContentPanel().getId());
            for (int row = 0; row < table.getRowCount(); row++) {
                if (table.getValueAt(row, 0).equals(targetValue)) {
                    ((DefaultTableModel) table.getModel()).removeRow(row);
                    break; // если нужно удалить только одну строку с данным значением, иначе удалите эту строку
                }
            }

            table.revalidate();
            table.repaint();
            view.getLog().setText("complete");
        }else{
            view.getLog().setText("failed to delete object");
        }
    }

    private void startBounceAnimation(RoundedButton button) {
        Timer timer = new Timer(25, new ActionListener() {
            private final int startPos = button.getY();
            private double S = 0;
            private double V = 10;

            @Override
            public void actionPerformed(ActionEvent e) {

                int currentY = button.getY();
                double g = -1;
                double Vx = (V + g);
                double y = ((V * V - Vx * Vx) / (2 * abs(g)));
                V = Vx;
                S += y;
                button.setLocation(button.getX(), (int) (currentY - y));
                if (S <= 0) {
                    button.setLocation(button.getX(), (int) (startPos));
                    S = 0;
                    V = (int) (abs(Vx) / 1.5);
                }
                if (abs(V + 1) == abs(Vx) && S <= 0) {
                    button.setLocation(button.getX(), (int) (startPos));
                    ((Timer) e.getSource()).stop();
                    buttonTimers.remove(button);
                }
            }
        });
        buttonTimers.put(button, timer);
        timer.start();
    }

    private void addTicket(){
        List<String> lines = view.getContentPanel().collectFormData();
        System.out.println(lines);
        String joinedLines = String.join("\n", lines);

        StringReader stringReader = new StringReader(joinedLines);

        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Response resp = invk.creationReqest(user, "add", bufferedReader);
        if (resp == null){
            view.getLog().setText("error. Incorrect data. Check the correctness of the entered data types.");
            return;
        }
        System.out.println(resp.getAdditional());
        System.out.println("start add");
        invk.getConnectHendler().sendRequest(new Request("show", resp.getAdditional()));
        Response response = invk.getConnectHendler().dataReception();

        Object[] rowData = new Object[model.getColumnCount()];
        rowData[0] = response.getColllection()[0].getId();
        for (int i = 0; i < response.getColllection()[0].mytoString().length - 1; i++) {
            rowData[i + 1] = response.getColllection()[0].mytoString()[i];
        }
        model.addRow(rowData);

        setWidth(200);
        JButton ticketButton;
        ticketButton = new RoundedButton(String.valueOf(response.getColllection()[0].getId()), 90, new Color(8, 124, 8));
        ticketButtons.put(response.getColllection()[0].getId(), ticketButton);
        ticketButton.setFont(new Font("Serif", Font.PLAIN, (int) (5 + 3 * response.getColllection()[0].getPrice() / Price.MAX_PRICE.getPrice())));
        ticketButton.setMargin(new Insets(1, 1, 1, 1));
        ticketButton.setBounds(getXForTicket(response.getColllection()[0]),
                getYForTickket(response.getColllection()[0]),
                20 + (int) ((10 * response.getColllection()[0].getPrice()) / Price.MAX_PRICE.getPrice()),
                20 + (int) ((10 * response.getColllection()[0].getPrice()) / Price.MAX_PRICE.getPrice()));
        ticketButton.addActionListener(getActionListnerToTicketButton());
        view.getTicketsPanel().add(ticketButton);
        view.getTicketsPanel().revalidate();
        view.getTicketsPanel().repaint();
        view.getContentPanel().setId(response.getColllection()[0].getId());
        getBGforTickets();
        view.getTicketsPanel().revalidate();
        view.getTicketsPanel().repaint();

        view.getTable().revalidate();
        view.getTable().repaint();
        view.getLog().setText("complete");
    }

    private void updateTicket(){
        System.out.println(view.getContentPanel().getId());
        if (!invk.checkPermission(user, String.valueOf(view.getContentPanel().getId()))) {
            JOptionPane.showMessageDialog(view, "you cannot change this object", "Warning", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        System.out.println("ok");
        System.out.println("Collected Form Data:");
        List<String> lines = view.getContentPanel().collectFormData();
        System.out.println(lines);
        String joinedLines = String.join("\n", lines);

        StringReader stringReader = new StringReader(joinedLines);

        BufferedReader bufferedReader = new BufferedReader(stringReader);

        if (invk.creationReqest(user, "update " + view.getContentPanel().getId(), bufferedReader) != null){
            int rowIndex = -1;
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 0).equals(String.valueOf(view.getContentPanel().getId()))) {
                    rowIndex = i;
                    break;
                }
            }

            // Если строка найдена, меняем значение в каждом столбце
            if (rowIndex != -1) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    if (j == 3) continue;
                    table.setValueAt(lines.get(j), rowIndex, j);
                }
            }getBGforTickets();
            ticketButtons.get(view.getContentPanel().getId()).setBounds(
                    (int) Double.parseDouble(lines.get(1)),
                    (int) Double.parseDouble(lines.get(2)),
                    (int) (30 + 20 * Double.parseDouble(view.getContentPanel().collectFormData().get(3)) / Price.MAX_PRICE.getPrice()),
                    (int) (30 + 20 * Double.parseDouble(view.getContentPanel().collectFormData().get(3)) / Price.MAX_PRICE.getPrice()));
            ticketButtons.get(view.getContentPanel().getId()).setFont(new Font("Serif", Font.PLAIN, (int) (10 + 6 * Double.parseDouble(view.getContentPanel().collectFormData().get(3)) / Price.MAX_PRICE.getPrice())));

            view.getTicketsPanel().revalidate();
            view.getTicketsPanel().repaint();
            view.getLog().setText("complete");
        } else {
            view.getLog().setText("error. Incorrect data. Check the correctness of the entered data types.");
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

    private ActionListener getActionListnerToTicketButton(){
        return e -> {
            RoundedButton button = (RoundedButton) e.getSource();
            setInformationPlace(button.getText());

            Timer timer = buttonTimers.get(button);
            if (timer != null && timer.isRunning()) {
                return;
            }
            jumper.submit(() -> startBounceAnimation(button));
        };
    }

    private MouseAdapter getMouseAdapterToTicketButton(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTable().rowAtPoint(e.getPoint());
                if (row >= 0) {
                    setInformationPlace((String) view.getTable().getValueAt(row, 0));
                }
            }
        };
    }

    private void setInformationPlace(String text){
        invk.getConnectHendler().sendRequest(new Request("show", text));
        Response response = invk.getConnectHendler().dataReception();
        if (response.isSuccessfully()) {
            view.getContentPanel().setTextFieldAndCheckBoxDefaults(response.getColllection()[0].mytoString(), response.getColllection()[0].getId());
            view.getLog().setText("complete");
        }else {
            System.out.println(response.getAdditional());
            view.getLog().setText("error");
        }
    }

    private int getXForTicket(Ticket ticket){
        System.out.println("view.getTicketsPanel().getWidth() = " + view.getTicketsPanel().getWidth());
        System.out.println((view.getTicketsPanel().getWidth() / 2 + ticket.getCoordinates().getX()) - (int) ((20 + (10 * ticket.getPrice()) / Price.MAX_PRICE.getPrice())/2));
        return (int) (view.getTicketsPanel().getWidth() / 2 + ticket.getCoordinates().getX()) - (int) ((20 + (10 * ticket.getPrice()) / Price.MAX_PRICE.getPrice())/2);
    }

    private int getYForTickket(Ticket ticket){
        System.out.println("view.getTicketsPanel().getHeight() = " + view.getTicketsPanel().getHeight());
        System.out.println((view.getTicketsPanel().getHeight() / 2 - ticket.getCoordinates().getY()) - (int) ((20 + (10 * ticket.getPrice()) / Price.MAX_PRICE.getPrice())/2));
        return (int) (view.getTicketsPanel().getHeight() / 2 - ticket.getCoordinates().getY()) - (int) ((20 + (10 * ticket.getPrice()) / Price.MAX_PRICE.getPrice())/2);
    }
}
