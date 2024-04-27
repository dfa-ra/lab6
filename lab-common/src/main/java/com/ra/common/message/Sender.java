package com.ra.common.message;

public class Sender {
    public static void send(Message message){
        switch (message.getType().name()){
            case "ERROR" -> System.out.print("!! " + message);
            case "WARNING" -> System.out.print("!  " + message);
            default -> System.out.print(message);
        }
    }
}
