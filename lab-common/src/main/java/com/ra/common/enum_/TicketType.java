package com.ra.common.enum_;

import lombok.Getter;

import java.awt.Color;
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


    public static Color getColor(TicketType ticketType){
        if (ticketType == TicketType.VIP)
            return new Color(75, 0, 130, 80);
        else if (ticketType == TicketType.USUAL)
            return new Color(0, 128, 0, 80);
        else if (ticketType == TicketType.CHEAP)
            return new Color(255, 165, 0, 80);
        else if (ticketType == TicketType.BUDGETARY)
            return new Color(128, 128, 128, 80);
        else
            return new Color(231,231,231);
    }
}
