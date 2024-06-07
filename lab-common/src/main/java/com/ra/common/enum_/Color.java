package com.ra.common.enum_;

import lombok.Getter;

import java.io.Serializable;

/**
 * Константы цветов
 * @author Захарченко Роман
 */
public enum Color implements Serializable {
    GREEN("GREEN"),
    BLACK("BLACK"),
    BLUE("BLUE");
    @Getter
    private final String color;

    Color(String color) {
        this.color = color;
    }
}
