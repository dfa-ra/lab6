package com.ra.server.collection.dbManager.reqests;

import lombok.Getter;

@Getter
public enum RequestDB {
    ADD_USER("INSERT INTO Utilizer(login, password) VALUES(?, ?)"),
    ADD_TICKET("INSERT INTO Ticket(name, coordinates_id, creationDate," +
            " price, comment, refundable, type, person_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)"),
    ADD_COORDINATES("INSERT INTO Coordinates(x,y) VALUES(?,?)"),
    ADD_PERSON("INSERT INTO Person(birthday, hairColor, location_id) VALUES(?, ?, ?)"),
    ADD_LOCATION("INSERT INTO Location(x, y, z, name) VALUES(?, ?, ?, ?)"),



    ADD_USER_TICKET_ID("INSERT INTO Utilizer_Ticket(utilizer_id, ticket_id) VALUES(?, ?)"),

    SELECT_TICKET("SELECT * FROM Ticket"),
    SELECT_COORDINATES("SELECT * FROM Coordinates"),
    SELECT_PERSON("SELECT * FROM Person"),
    SELECT_LOCATION("SELECT * FROM Location"),
    SELECT_USER("SELECT * FROM Utilizer WHERE (login = ? AND password = ?)");
    private final String request;

    RequestDB(String request) {
        this.request = request;
    }
}
