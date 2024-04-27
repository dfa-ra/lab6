package com.ra.common.commands;

import lombok.Getter;

import java.io.Serializable;
@Getter
public class CommandType implements Serializable {
    private final int argumentCount_min;
    private final int argumentCount_max;
    private final boolean withForm;

    public CommandType(int argumentCount, boolean withForm) {
        this.argumentCount_min = argumentCount;
        this.argumentCount_max = argumentCount;
        this.withForm = withForm;
    }
    public CommandType(int argumentCount_min, int argumentCount_max, boolean withForm) {
        this.argumentCount_min = argumentCount_min;
        this.argumentCount_max = argumentCount_max;
        this.withForm = withForm;
    }
}
