package com.ra.client.command;


import com.ra.common.enum_.Color;
import com.ra.common.enum_.TicketType;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;
import com.ra.common.sample.Coordinates;
import com.ra.common.sample.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface InputInfo {
    /**
     * Метод для работы с ведёнными строками.
     * @param about (String) что вводить?
     * @param canBeNull (boolean) может быть null или нет?
     * @return Метод возвращает введённую строку либо null если не ввели ничего
     */
    default String addStr(String about, boolean canBeNull){
        Scanner in = new Scanner(System.in);
        String str = "";
        Sender.send(new Message(messageType.INPUT, about + ": ", ""));
        str=in.nextLine();
        if (!canBeNull){
            while (str == "" || str.matches("^\\s*$")) {
                Sender.send(new Message(messageType.INPUT,about + ": ", ""));
                str=in.nextLine();
            }
        }
        else if (str == "" || str.matches("^\\s*$")) str = null;
        return str;
    }

    /**
     * Метод для введения координат.
     * @return Метод возвращает введённые координаты x и y.
     */
    default Coordinates addCoordinates(){
        Scanner in = new Scanner(System.in);
        int y;
        float x = 0;
        for(;;) {
            try {
                Sender.send(new Message(messageType.INPUT,"coordinates x (>-785): ", ""));
                x = in.nextFloat();
                if (x <= -785) {
                    Sender.send(new Message(messageType.WARNING,"(>-785)!!!" ));
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                Sender.send(new Message(messageType.ERROR,"Invalid data type. Try again."));
                in.next();
            }
        }
        for(;;){
            try{
                Sender.send(new Message(messageType.INPUT,"coordinates y: ", ""));
                y = in.nextInt();
                break;
            } catch (InputMismatchException e) {
                Sender.send(new Message(messageType.ERROR,"Invalid data type. Try again."));
                in.next();
            }
        }
        return new Coordinates(x, y);
    }

    /**
     * Метод для введения цены.
     * @return Метод возвращает введённую цену.
     */
    default Double addPrice(){
        Scanner in = new Scanner(System.in);
        Double price;
        for(;;){
            try{
                Sender.send(new Message(messageType.INPUT,"price x (>0): ", ""));
                price = in.nextDouble();
                if (price < 0){
                    Sender.send(new Message(messageType.WARNING,"price > 0"));
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                Sender.send(new Message(messageType.ERROR,"Error! Invalid data type. Try again."));
                in.next();
            }
        }
        return price;
    }

    /**
     * Метод для введения возвратности билета.
     * @return Возвращает ответ (да или нет) на вопрос. Является ли билет возвратным?
     */
    default Boolean addRefundable(){
        Scanner in = new Scanner(System.in);
        Boolean refundable;
        String str;
        //refundable
        for(;;){
            Sender.send(new Message(messageType.INPUT,"refundable(can be null): ", ""));
            str=in.nextLine();
            if (str.equals("true")) {
                refundable = true;
                break;
            }
            else if (str.equals("false")) {
                refundable = false;
                break;
            }
            else if (str.isEmpty()) {
                refundable = null;
                break;
            }
            else {
                Sender.send(new Message(messageType.ERROR,"Available answers are true or false!"));
            }
        }
        return refundable;
    }

    /**
     * Метод для ввода типа билета.
     * @return Возвращает тип билета.
     */
    default TicketType addTicketType(){
        int i = 0;
        for (TicketType type : TicketType.values()) {
            i+=1;
            Sender.send(new Message(messageType.DEFAULT,i + ")" + type.name() + "\n"));
        }

        Scanner in = new Scanner(System.in);
        TicketType ticketType;
        for(;;){
            try{
                Sender.send(new Message(messageType.INPUT,"Enter one of these ticket types: ", ""));
                String tmp = in.next();
                if (tmp == "") ticketType = null;
                else ticketType = TicketType.valueOf(tmp);
                break;
            } catch (IllegalArgumentException e) {
                Sender.send(new Message(messageType.ERROR,"Select an option from the list above!"));
            }
        }
        return ticketType;
    }

    /**
     * Метод для ввода даты рождения.
     * @return Возвращает введённую дату рождения
     */
    default Date addBirthDay(){
        Scanner in = new Scanner(System.in);
        String answBirthday = null;
        Date birthday = null;
        SimpleDateFormat birthdayFormat = new SimpleDateFormat("dd.MM.yyyy");
        while (true) {
            try {
                Sender.send(new Message(messageType.INPUT,"your birthday (dd.mm.yyyy)", ""));
                answBirthday = in.next();
                birthday = birthdayFormat.parse(answBirthday.trim());
                break;
            } catch (ParseException ignored) {
                Sender.send(new Message(messageType.WARNING,"try again"));
            }
        }
        return birthday;
    }

    /**
     * Метод для ввода цвета волос.
     * @return Возвращает введённый цвет волос.
     */
    default Color addHaitColor(){

        int i = 0;
        for (Color c : Color.values()) {
            i+=1;
            Sender.send(new Message(messageType.DEFAULT, i + ")" + c.name() + "\n"));
        }

        Scanner in = new Scanner(System.in);
        Color hairColor;
        for(;;){
            try{
                Sender.send(new Message(messageType.INPUT,"Enter one of these hair color: ", ""));
                hairColor = Color.valueOf(in.next());
                break;
            } catch (IllegalArgumentException e) {
                Sender.send(new Message(messageType.ERROR,"Select an option from the list above!"));
            }
        }
        return hairColor;
    }

    /**
     * Метод для ввода локации.
     * @return Возвращает введённую локацию с координатами и названием.
     */
    default Location addLocation(){
        double X;
        Long Y;
        float Z;
        Scanner in = new Scanner(System.in);
        for(;;){
            try{
                Sender.send(new Message(messageType.INPUT,"location x (not null): ", ""));
                X = in.nextDouble();
                break;
            } catch (InputMismatchException e) {
                Sender.send(new Message(messageType.ERROR," Invalid data type. Try again."));
                in.next();
            }
        }
        for(;;){
            try{
                Sender.send(new Message(messageType.INPUT,"location y: ", ""));
                Y = in.nextLong();
                break;
            } catch (InputMismatchException e) {
                Sender.send(new Message(messageType.ERROR,"Invalid data type. Try again."));
                in.next();
            }
        }
        for(;;){
            try{
                Sender.send(new Message(messageType.INPUT,"location z: ", ""));
                Z = in.nextFloat();
                break;
            } catch (InputMismatchException e) {
                Sender.send(new Message(messageType.ERROR,"Invalid data type. Try again."));
                in.next();
            }
        }
        return new Location(X, Y, Z, addStr("location name", true));
    }
}
