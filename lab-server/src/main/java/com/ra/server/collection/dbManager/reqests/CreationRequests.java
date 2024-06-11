package com.ra.server.collection.dbManager.reqests;

import lombok.Getter;

@Getter
public enum CreationRequests {
    CREATE_TICKET_SEQUENCE("CREATE SEQUENCE IF NOT EXISTS id"),
    CREATE_USER("CREATE TABLE IF NOT EXISTS Utilizer(" +
            "id BIGSERIAL PRIMARY KEY," +
            "login TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "salt TEXT NOT NULL," +
            "utilizerName TEXT NOT NULL," +
            "surname TEXT NOT NULL," +
            "email TEXT NOT NULL" +
            ")"),

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
            "id BIGINT PRIMARY KEY DEFAULT (nextval('id'))," +
            "name TEXT NOT NULL," +
            "coordinates_id INTEGER REFERENCES Coordinates(id)," +
            "creationDate TEXT NOT NULL," +
            "price DOUBLE PRECISION CHECK (price > 0)," +
            "comment TEXT," +
            "refundable BOOLEAN," +
            "type TEXT NOT NULL," +
            "person_id INTEGER NULL REFERENCES Person(id)," +
            "Utilizer_id INTEGER NULL REFERENCES Utilizer(id)" +
            ")");




    private final String request;

    CreationRequests(String request) {
        this.request = request;
    }
}
