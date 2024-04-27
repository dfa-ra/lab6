package com.ra.server.Comands;

import com.ra.common.commands.CommandType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
@Getter
@AllArgsConstructor
public abstract class UpperClass implements Serializable, Command {
    private CommandType commandType;
    private String name;
    private String info;

}
