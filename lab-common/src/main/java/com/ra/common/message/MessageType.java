package com.ra.common.message;

import lombok.Getter;

@Getter
public enum MessageType {
    DEFAULT("\003[0m"),
    INFO("\033[0;32m"),
    ERROR("\033[0;31m"),
    WARNING("\033[0;33m"),
    INPUT("\033[0;34m"),;

    private final String color;

    MessageType(String color) {
        this.color = color;
    }
}
