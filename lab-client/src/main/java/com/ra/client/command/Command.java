package com.ra.client.command;

import java.io.IOException;

public interface Command {
    void execute(String arg) throws IOException;
}
