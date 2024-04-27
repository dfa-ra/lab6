package com.ra.server.Collection;

import com.ra.common.enum_.TicketType;
import com.ra.common.sample.Ticket;
import com.ra.server.Collection.Parser.XmlManager;
import lombok.Getter;
import lombok.Setter;

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
    static HashSet<Ticket> notebook = new HashSet<>();
    /**
     * Дата инициализации коллекции в программе
     */
    static Date date = new Date();

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
        str += "Lenght: " + notebook.size() + "\n";
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
    public void add(Long id, Ticket ticket) {
        Scanner in = new Scanner(System.in);
        Long oldid = Long.valueOf(0);;

        while (id != oldid) {
            oldid = id;
            Iterator<Ticket> i = notebook.iterator();
            while (i.hasNext()) {
                Ticket tmp = i.next();
                if (tmp.getId().equals(id)) {
                    id++;
                }
            }
        }
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedatetime = ZonedDateTime.of(date, time, zoneId);
        ticket.setCreationDate(zonedatetime);
        ticket.setId(id);
        notebook.add(ticket);
    }

    /**
     * Метод, который обновляет элемент коллекции по-заданному id.
     * @param id id элемента который надо обновить.
     */
    public String update(long id, Ticket ticket){
        Iterator<Ticket> i = notebook.iterator();
        while (i.hasNext()) {
            Ticket tmp = i.next();
            if (tmp.getId() == id){
                notebook.remove(tmp);
                ZonedDateTime zonedatetime = tmp.getCreationDate();
                ticket.setId(id);
                ticket.setCreationDate(zonedatetime);
                notebook.add(ticket);
                return "Complete";
            }
        }
        return  "No such ID found. Try again!";
    }

    /**
     * Метод удаления элемента коллекции по id.
     * @param id id элемента который надо удалить
     */
    public String removeById(long id){
        Iterator<Ticket> i = notebook.iterator();
        while (i.hasNext()) {
            Ticket tmp = i.next();
            if (tmp.getId() == id){
                notebook.remove(tmp);
                return "Complete!";
            }
        }
        return "No such ID found. Try again!";
    }

    /**
     * Метод очистки коллекции.
     */
    public void clear(){
        notebook.clear();
        System.out.println("Collection cleared");
    }

    /**
     * Метод, который удаляет все элементы id больше заданного.
     * @param id заданный id.
     */
    public String removeGreater(long id){
        Iterator<Ticket> i = notebook.iterator();
        boolean flag = false;
        while (i.hasNext()) {
            Ticket tmp = i.next();
            if (tmp.getId() > id ){
                flag = true;
                i.remove();
            }
        }
        if (!flag) return "No IDs were found that matched the requirements. Try again!";
        else return "Complete!";
    }

    /**
     * Метод, который удаляет все элементы id меньше заданного.
     * @param id заданный id.
     */
    public String removeLower(long id){
        Iterator<Ticket> i = notebook.iterator();
        boolean flag = false;
        while (i.hasNext()) {
            Ticket tmp = i.next();
            if (tmp.getId() < id ){
                flag = true;
                i.remove();
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
//
//    /**
//     * Метод выводит отсортированные по id элементы коллекции имеющие заданный тип.
//     * @param type тип для которого надо вывести элементы коллекции.
//     */
//    public String printAllType(TicketType type){
//        Iterator<Ticket> i = notebook.iterator();
//        ArrayList<Long> list = new ArrayList<>();
//        String str = "";
//        str += type.toString() + ": ";
//        while (i.hasNext()) {
//            Ticket tmp = i.next();
//            if (tmp.getType() == type ){
//                list.add(tmp.getId());
//            }
//        }
//        Comparator<? super Long> comparator =  new Comparator<Long>() {
//            @Override
//            public int compare(Long o1, Long o2) {
//                return (int) (o1 - o2);
//            }
//        };
//        list.sort(comparator);
//        str += list;
//        return str;
//    }
//
//    /**
//     * Метод выводит id всех типов билетов по группам
//     */
//    public String printFieldType(){
//        String str = "";
//        str += printAllType(TicketType.VIP) + "\n";
//        str += printAllType(TicketType.USUAL) + "\n";
//        str += (TicketType.BUDGETARY) + "\n";
//        str += (TicketType.CHEAP) + "\n";
//        return str;
//    }

    /**
     * Метод выводит значения поля type всех элементов в порядке убывания
     */
    public String printFieldDescendingType(){
        List<Ticket> list =  notebook.stream().sorted().toList();
        String str = "";
        for (int i = list.size()-1; i >=0 ; i--) {
            str += list.get(i).getType().toString() + "\n";
        }
        return str;
    }

    /**
     * Метод, который сохраняет коллекцию.
     * @throws Exception
     */
    public void saveCollection() throws Exception {
        KeepCollection keepCollection = new KeepCollection();
        keepCollection.setTicket(notebook);
        XmlManager.mySecondSaveCollection(keepCollection);
    }
}
