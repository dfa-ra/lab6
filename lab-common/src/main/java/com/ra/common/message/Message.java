package com.ra.common.message;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Message implements Serializable {
    private String msg;
    private MessageType type;
    private String nexLine = "\n";

    public Message(MessageType type, String msg) {
        this.msg = msg;
        this.type = type;
    }
    public Message(MessageType type, String msg, String nexLine) {
        this.msg = msg;
        this.type = type;
        this.nexLine = nexLine;
    }

    @Override
    public String toString() {
        if (type == MessageType.DEFAULT) {
            return msg;
        }
        return "[" + type.getColor() + type.name() + "\033[0m" +  "] " + msg + nexLine;
    }
}
