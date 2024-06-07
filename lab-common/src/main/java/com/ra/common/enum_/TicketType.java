package com.ra.common.enum_;

import lombok.Getter;

import java.io.Serializable;

/**
 * Константы типов билетов
 * @author Захарченко Роман
 */
public enum TicketType implements Serializable {
    VIP("VIP"),
    USUAL("USUAL"),
    BUDGETARY("BUDGETARY"),
    CHEAP("CHEAP");

    @Getter
    private final String type;

    TicketType(String type) {
        this.type = type;
    }
}
