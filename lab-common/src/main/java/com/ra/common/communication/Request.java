package com.ra.common.communication;

import com.ra.common.User;
import com.ra.common.sample.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.desktop.UserSessionEvent;
import java.io.Serial;
import java.io.Serializable;

@Getter
@ToString
public class Request implements Serializable {
    private String nameCommand = null;
    private String argumentsCommand = null;
    private Ticket ticket = null;
    @Setter
    private User user = null;
    @Serial
    private static final long serialVersionUID = -8100196035368770163L;

    public Request(String nameCommand, String argumentsCommand, Ticket ticket) {
        this.nameCommand = nameCommand;
        this.argumentsCommand = argumentsCommand;
        this.ticket = ticket;
    }
    public Request(String nameCommand, String argumentsCommand) {
        this.argumentsCommand = argumentsCommand;
        this.nameCommand = nameCommand;
    }
    public Request(String nameCommand) {
        this.nameCommand = nameCommand;
    }

    public Request(String nameCommand, Ticket ticket) {
        this.nameCommand = nameCommand;
        this.ticket = ticket;
    }

    public String getLogin(){
        return user.getLogin();
    }
    public String getPassword(){
        return user.getPassword();
    }
}