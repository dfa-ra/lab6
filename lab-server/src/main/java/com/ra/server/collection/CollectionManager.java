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
    public Ticket[] show(Long id){
        if (notebook.isEmpty()){
            return null;
        }
        else {
            if (id < 0L)
                return notebook.stream().sorted(Comparator.comparing(Ticket::getName)).toList().toArray(new Ticket[0]);
            else{
                return notebook.stream()
                        .filter(p -> Objects.equals(p.getId(), id))
                        .findFirst().stream().toList().toArray(new Ticket[0]);
            }
        }
    }

    /**
     * Метод добавляет новый элемент в коллекцию.
     */
    public Long add(Ticket ticket, String login, String password){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedatetime = ZonedDateTime.of(date, time, zoneId);
        ticket.setCreationDate(zonedatetime);
        try {
            System.out.println("near synhronizde");
            db.addTicket(ticket, login, password);
            System.out.println("here ssunhron");
            ticket.setId(db.getTicketID());
            synchronized (this) {
                notebook.add(ticket);
            }
            return ticket.getId();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return -1L;
        }
    }

    /**
     * Метод, который обновляет элемент коллекции по-заданному id.
     * @param id id элемента который надо обновить.
     */
    public boolean update(long id, Ticket ticket) throws SQLException {

        for (Ticket tmp : notebook) {
            if (tmp.getId() == id) {
                synchronized (this) {
                    notebook.remove(tmp);

                    ZonedDateTime zonedatetime = tmp.getCreationDate();
                    ticket.setId(id);
                    ticket.setCreationDate(zonedatetime);

                    notebook.add(ticket);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Метод удаления элемента коллекции по id.
     * @param id id элемента который надо удалить
     */
    public boolean removeById(long id, String login, String password){
        for (Ticket tmp : notebook) {
            if (tmp.getId() == id){
                db.deleteByID(tmp.getId(), login, password);
                System.out.println("000000000000000000000)))_)))))))))_)_)_)_)_");
                synchronized (this) {
                    notebook.remove(tmp);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Метод очистки коллекции.
     */
    public void clear(String login, String password){
        for (Ticket tmp: notebook){
            db.deleteByID(tmp.getId(), login, password);
        }
        synchronized (this) {
            notebook.clear();
        }

        System.out.println("Collection cleared");
    }

    /**
     * Метод, который удаляет все элементы id больше заданного.
     * @param id заданный id.
     */
    public boolean removeGreater(long id, String login, String password){
        boolean flag = false;

        Iterator<Ticket> iterator = notebook.iterator();
        while (iterator.hasNext()) {
            Ticket tmp = iterator.next();
            try {
                if (tmp.getId() > id && db.checkTicketUser(Math.toIntExact(tmp.getId()), login, password).equals("ok")) {
                    flag = true;
                    if (db.deleteByID(tmp.getId(), login, password)) {
                        synchronized (this) {
                            iterator.remove(); // Удаляем элемент через итератор
                        }
                    }
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return flag;
    }

    /**
     * Метод, который удаляет все элементы id меньше заданного.
     * @param id заданный id.
     */
    public boolean removeLower(long id, String login, String password) throws SQLException {
        boolean flag = false;
        Iterator<Ticket> iterator = notebook.iterator();
        while (iterator.hasNext()) {
            Ticket tmp = iterator.next();
            try {
                System.out.println(tmp.getId() + ":" + id);
                System.out.println(db.checkTicketUser(Math.toIntExact(tmp.getId()), login, password).equals("ok"));
                if (tmp.getId() < id && db.checkTicketUser(Math.toIntExact(tmp.getId()), login, password).equals("ok")) {
                    System.out.println("------OK");
                    flag = true;
                    if (db.deleteByID(tmp.getId(), login, password)) {
                        synchronized (this) {
                            iterator.remove(); // Удаляем элемент через итератор
                        }
                    }
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return flag;
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
    public Ticket[] printFieldDescendingType(){
        return notebook.stream().sorted(Comparator.comparing(Ticket::getType)).toList().toArray(new Ticket[0]);
    }
}
