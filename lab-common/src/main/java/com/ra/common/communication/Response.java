package com.ra.common.communication;

import com.ra.common.commands.CommandType;
import com.ra.common.sample.Ticket;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
@Getter
public class Response implements Serializable {
    private String additional;
    private HashMap<String, CommandType> infoCommand;
    private Ticket[] colllection;

    public Response(String additional) {
        this.additional = additional;
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
