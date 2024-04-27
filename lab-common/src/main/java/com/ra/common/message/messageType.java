package com.ra.common.message;

import lombok.Getter;

@Getter
public enum messageType {
    DEFAULT("\003[0m"),
    INFO("\033[0;32m"),
    ERROR("\033[0;31m"),
    WARNING("\033[0;33m"),
    INPUT("\033[0;34m"),;

    private final String color;

    messageType(String color) {
        this.color = color;
    }
}
