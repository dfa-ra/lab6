package com.ra.client.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsScript {

    public static List<String> parser(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        List<String> commands = new ArrayList<>();
        String str = "";
        while (line != null) {
            commands.add(line);
            line = br.readLine();
        }
        br.close();
        return commands;
    }
}
