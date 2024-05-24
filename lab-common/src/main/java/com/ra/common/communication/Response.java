package com.ra.common.communication;

import com.ra.common.commands.CommandType;
import com.ra.common.forms.Form;
import com.ra.common.message.MessageType;
import com.ra.common.sample.Ticket;
import lombok.Getter;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
@Getter
public class Response implements Serializable {
    private String additional;
    private MessageType messageType;
    private HashMap<String, CommandType> infoCommand;
    private Ticket[] colllection;
    @Serial
    private static final long serialVersionUID = -8100196035368770163L;

    public Response(String additional) {
        this.additional = additional;
    }

    public Response(String additional, MessageType messageType) {
        this.messageType = messageType;
    }
    public Response(HashMap<String, CommandType> infoCommand) {
        this.infoCommand = infoCommand;
    }
    public Response(Ticket[] colllection, String additional) {
        this.colllection = colllection;
        this.additional = additional;
    }

    @Override
    public String toString() {
        if (colllection != null)
            return additional + Arrays.toString(colllection);
        return additional;
    }
}
