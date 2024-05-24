package com.ra.server.collection;

import com.ra.common.enum_.TicketType;
import com.ra.common.sample.Ticket;
import com.ra.server.collection.dbManager.ConnectionBaseSQL;
import com.ra.server.collection.dbManager.DBManager;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Класс работы с коллекцией (добавление, обновление, информация и т.д.)
 * @author Захарченко Роман
 */
@Getter
@Setter
public class CollectionManager{
    /**
     * @return HashSet коллекция состоящая из ticket-ов
     */
    private static HashSet<Ticket> notebook = new HashSet<>();
    private DBManager db = null;
    {
        try {
            db = new DBManager(ConnectionBaseSQL.getInstance().getConnection());
        }catch (SQLException e){
            e.getMessage();
        }
    }
    /**
     * Дата инициализации коллекции в программе
     */
    static Date date = new Date();

    public CollectionManager(){
    }

    /**
     * Метод инициализации коллекции notebook
     * @param notebook коллекция которую надо инициализировать
     */
    public static void setNotebook(HashSet<Ticket> notebook) {
        CollectionManager.notebook = notebook;
    }

    static HashSet<Ticket> getNotebook(){
        return notebook;
    }
    /**
     * Метод, который выводит информацию о коллекции.
     */
    public String info(){
        String str = "";
        if (!notebook.isEmpty()){
            str += "Type: " + notebook.iterator().next().getClass() + "\n";
        }
        str += "Date: " + date + "\n";
        str += "Lenght: " + notebook.size();
        return str;
    }

    /**
     * Метод выводящий в стандартный поток вывода все элементы коллекции в строковом представлении
     */
    public Ticket[] show(){
        if (notebook.isEmpty()){
            return null;
        }
        else {
            return notebook.stream().sorted(Comparator.comparing(Ticket::getName)).toList().toArray(new Ticket[0]);
        }
    }

    /**
     * Метод добавляет новый элемент в коллекцию.
     */
    public void add(Ticket ticket, String login, String password){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedatetime = ZonedDateTime.of(date, time, zoneId);
        ticket.setCreationDate(zonedatetime);
        try {
            db.addTicket(ticket, login, password);
            ticket.setId(db.getTicketID());
            notebook.add(ticket);
        }catch (SQLException e){
            e.getMessage();
        }
    }

    /**
     * Метод, который обновляет элемент коллекции по-заданному id.
     * @param id id элемента который надо обновить.
     */
    public String update(long id, Ticket ticket) throws SQLException {

        try {
            String str = db.updateTicket(ticket, id);
            for (Ticket tmp : notebook) {
                if (tmp.getId() == id) {
                    notebook.remove(tmp);
                    ZonedDateTime zonedatetime = tmp.getCreationDate();
                    ticket.setId(id);
                    ticket.setCreationDate(zonedatetime);
                    notebook.add(ticket);
                    return "Complete";
                }
            }
            return str;
        }catch (SQLException e){
            e.getMessage();
            return "Some Errors";
        }
    }

    /**
     * Метод удаления элемента коллекции по id.
     * @param id id элемента который надо удалить
     */
    public String removeById(long id, String login, String password){
        for (Ticket tmp : notebook) {
            if (tmp.getId() == id){
                try {
                    if (db.deleteByID(tmp.getId(), login, password)) {
                        notebook.remove(tmp);
                        return "Complete!";
                    }
                }catch (SQLException e){
                    e.getMessage();
                }
            }
        }
        return "No such ID found. Try again!";
    }

    /**
     * Метод очистки коллекции.
     */
    public void clear(String login, String password){
        for (Ticket tmp: notebook){
            try {
                db.deleteByID(tmp.getId(), login, password);
                }catch (SQLException e){
                e.getMessage();
            }
        }
        notebook.clear();

        System.out.println("Collection cleared");
    }

    /**
     * Метод, который удаляет все элементы id больше заданного.
     * @param id заданный id.
     */
    public String removeGreater(long id, String login, String password){
        boolean flag = false;
        for (Ticket tmp: notebook){
            if (tmp.getId() > id ){
                flag = true;
                try {
                    if (db.deleteByID(tmp.getId(), login, password))
                        notebook.remove(tmp);
                }catch (SQLException e){
                    e.getMessage();
                }
            }
        }
        if (!flag) return "No IDs were found that matched the requirements. Try again!";
        else return "Complete!";
    }

    /**
     * Метод, который удаляет все элементы id меньше заданного.
     * @param id заданный id.
     */
    public String removeLower(long id, String login, String password) throws SQLException {
        boolean flag = false;
        for(Ticket tmp: notebook){
            if (tmp.getId() < id ){
                flag = true;
                if (db.deleteByID(tmp.getId(), login, password))
                    notebook.remove(tmp);
            }
        }
        if (!flag) return "No IDs were found that matched the requirements. Try again!";
        else return "Complete!";
    }

    /**
     * Метод сгруппировывающий элементы коллекции по значению поля type и выводящий количество элементов в каждой группе.
     */
    public String groupCountingByType(){
        String str = "";
        for(TicketType type : TicketType.values()){
            str += type.name() + ": " + notebook.stream().filter(ticket -> ticket.getType().name().equals(type.name())).count() + "\n";
        }
        return str;
    }

    /**
     * Метод выводит элементы коллекции в порядке возрастания.
     */
    public Ticket[] printAscending() {
        return notebook.stream().sorted().toList().toArray(new Ticket[0]);
    }

    /**
     * Метод выводит значения поля type всех элементов в порядке убывания
     */
    public String printFieldDescendingType(){
        List<Ticket> list = notebook.stream().sorted().toList();
        String str = "";
        for (int i = list.size()-1; i >=0 ; i--) {
            str += list.get(i).getType().toString() + "\n";
        }
        return str;
    }
}
