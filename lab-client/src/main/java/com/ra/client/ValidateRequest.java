package com.ra.client;

import com.ra.client.command.InputInfo;
import com.ra.common.commands.CommandType;
import com.ra.common.communication.Request;
import com.ra.common.message.Message;
import com.ra.common.message.Sender;
import com.ra.common.message.messageType;
import com.ra.common.sample.Person;
import com.ra.common.sample.Ticket;

import java.util.HashMap;
import java.util.Scanner;

public class ValidateRequest implements InputInfo {
    private HashMap<String, CommandType> commandType;
    private Scanner in = new Scanner(System.in);

    public ValidateRequest(HashMap<String, CommandType> commandType) {
        this.commandType = commandType;
    }
    public Request Validate(String commandName, String commandArg){
        if (!commandType.containsKey(commandName)) return null;

        int len;
        if (commandArg.isEmpty())
            len = 0;
        else{
            String[] commandArgs = commandArg.split(" ");
            len = commandArgs.length;
        }

        if (!(commandType.get(commandName).getArgumentCount_min() <= len &&
                len <= commandType.get(commandName).getArgumentCount_max())) {
            return null;
        }
        if (commandType.get(commandName).isWithForm())
        {
            Ticket ticket = new Ticket(Long.valueOf(0), addStr("name", false), addCoordinates(), null, addPrice(), addStr("comment", true), addRefundable(), addTicketType(), null);
            Sender.send(new Message(messageType.INPUT,"Would you like to enter a description of the person?(y/anything): ", ""));
            String answ = in.next();
            if (answ.equals("y")) ticket.setPerson(new Person(addBirthDay(), addHaitColor(), addLocation()));
            return new Request(commandName, commandArg, ticket);
        }

        return new Request(commandName, commandArg);
    }
}
