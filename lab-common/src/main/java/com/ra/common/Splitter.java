package com.ra.common;

import java.util.List;

public class Splitter {
        public static List<String> mySplit(String str)
        {
            List<String> tokens = new java.util.ArrayList<>(List.of(str.split(" ", 2)));
            if (tokens.size() < 2) {
                tokens.add("");
            }
            return tokens;
        }
}
