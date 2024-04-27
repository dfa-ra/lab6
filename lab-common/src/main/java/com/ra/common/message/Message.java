package com.ra.common.message;

import lombok.Getter;

@Getter
public class Message {
    private String msg;
    private messageType type;
    private String nexLine = "\n";

    public Message(messageType type, String msg) {
        this.msg = msg;
        this.type = type;
    }
    public Message(messageType type, String msg, String nexLine) {
        this.msg = msg;
        this.type = type;
        this.nexLine = nexLine;
    }

    @Override
    public String toString() {
        if (type == messageType.DEFAULT) {
            return msg;
        }
        return "[" + type.getColor() + type.name() + "\033[0m" +  "] " + msg + nexLine;
    }
}
