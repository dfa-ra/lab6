package com.ra.server.collection.dbManager.reqests;

import lombok.Getter;

@Getter
public enum CreationRequests {
    CREATE_LOCATION("CREATE TABLE IF NOT EXISTS Location(" +
            "id BIGSERIAL PRIMARY KEY," +
            "x DOUBLE PRECISION," +
            "y BIGINT," +
            "z DOUBLE PRECISION," +
            "name TEXT" +
            ")"),

    CREATE_COORDINATES("CREATE TABLE IF NOT EXISTS Coordinates(" +
            "id BIGSERIAL PRIMARY KEY," +
            "x DOUBLE PRECISION CHECK (x > -785)," +
            "y DOUBLE PRECISION" +
            ")"),

    CREATE_PERSON("CREATE TABLE IF NOT EXISTS Person(" +
            "id BIGSERIAL PRIMARY KEY," +
            "birthday TEXT NOT NULL," +
            "hairColor TEXT," +
            "location_id INTEGER REFERENCES Location(id)" +
            ")"),

    CREATE_TICKET("CREATE TABLE IF NOT EXISTS Ticket(" +
            "id BIGSERIAL PRIMARY KEY," +
            "name TEXT NOT NULL," +
            "coordinates_id INTEGER REFERENCES Coordinates(id)," +
            "creationDate TEXT NOT NULL," +
            "price DOUBLE PRECISION CHECK (price > 0)," +
            "comment TEXT," +
            "refundable BOOLEAN," +
            "type TEXT NOT NULL," +
            "person_id INTEGER NULL REFERENCES Person(id)" +
            ")"),

    CREATE_USER("CREATE TABLE IF NOT EXISTS Utilizer(" +
            "id BIGSERIAL PRIMARY KEY," +
            "login TEXT NOT NULL," +
            "password TEXT NOT NULL" +
            ")"),

    CREATE_USER_TICKET("CREATE TABLE IF NOT EXISTS Utilizer_Ticket(" +
            "utilizer_id INTEGER REFERENCES Utilizer(id) NOT NULL," +
            "ticket_id  INTEGER REFERENCES Ticket(id) NOT NULL," +
            "PRIMARY KEY (utilizer_id, ticket_id)" +
            ")");



    private final String request;

    CreationRequests(String request) {
        this.request = request;
    }
}
