package com.ra.common.communication;

import com.ra.common.commands.CommandType;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
@Getter
public class Response implements Serializable {
    private String additional;
    private HashMap<String, CommandType> infoCommand;

    public Response(String additional) {
        this.additional = additional;
    }
    public Response(HashMap<String, CommandType> infoCommand) {
        this.infoCommand = infoCommand;
    }
}
