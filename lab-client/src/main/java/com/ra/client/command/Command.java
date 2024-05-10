package com.ra.client.command;

import java.io.IOException;
import java.text.ParseException;

public interface Command {
    void execute(String arg) throws IOException, ParseException;
}
