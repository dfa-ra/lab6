package com.ra.server.comands;

import com.ra.common.commands.CommandType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
@Getter
@AllArgsConstructor
public abstract class UpperCommand implements Serializable, Command {
    private CommandType commandType;
    private String name;
    private String info;
}
