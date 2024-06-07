package com.ra.common.communication;

import com.ra.common.commands.CommandType;
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
    private final boolean successfully;
    private String additional;
    private MessageType messageType;
    private HashMap<String, CommandType> infoCommand;
    private Ticket[] colllection;
    @Serial
    private static final long serialVersionUID = -8100196035368770163L;

    public Response(boolean successfully) {
        this.successfully = successfully;
    }
    public Response(boolean successfully, String additional) {
        this.additional = additional;
        this.successfully = successfully;
    }

    public Response(boolean successfully, MessageType messageType) {
        this.messageType = messageType;
        this.successfully = successfully;
    }
    public Response(boolean successfully, HashMap<String, CommandType> infoCommand) {
        this.successfully = successfully;
        this.infoCommand = infoCommand;
    }
    public Response(boolean successfully, Ticket[] colllection, String additional) {
        this.colllection = colllection;
        this.successfully = successfully;
        this.additional = additional;
    }

    @Override
    public String toString() {
        if (colllection != null)
            return additional + Arrays.toString(colllection);
        return additional;
    }
}
