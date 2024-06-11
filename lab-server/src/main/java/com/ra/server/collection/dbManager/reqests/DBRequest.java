package com.ra.server.collection.dbManager.reqests;

import lombok.Getter;

@Getter
public enum DBRequest {
    ADD_USER("INSERT INTO Utilizer(login, password, salt, utilizerName, surname, email) VALUES(?, ?, ?, ?, ?, ?)"),
    ADD_TICKET("INSERT INTO Ticket(name, coordinates_id, creationDate," +
            " price, comment, refundable, type) VALUES(?, ?, ?, ?, ?, ?, ?)"),
    ADD_PERSON_TO_TICKET("UPDATE Ticket SET person_id = ? WHERE id = ?"),
    ADD_COORDINATES("INSERT INTO Coordinates(x,y) VALUES(?,?)"),
    ADD_PERSON("INSERT INTO Person(birthday, hairColor, location_id) VALUES(?, ?, ?)"),
    ADD_LOCATION("INSERT INTO Location(x, y, z, name) VALUES(?, ?, ?, ?)"),

    ADD_USER_TO_TICKET("UPDATE Ticket SET Utilizer_id = ? WHERE id = ?"),

    UPDATE_TICKET("UPDATE Ticket SET name = ?, price = ?, comment = ?, refundable = ?, type = ? WHERE id = ?"),
    UPDATE_SET_NULL_PERSON_ID("UPDATE Ticket SET person_id = NULL WHERE id = ?"),
    UPDATE_COORDINATES("UPDATE Coordinates SET x = ?, y = ? WHERE id = ?"),
    UPDATE_PERSON("UPDATE Person SET birthday = ?, hairColor = ? WHERE id = ?"),
    UPDATE_LOCATION("UPDATE Location SET x = ?, y = ?, z = ?, name = ? WHERE id = ?"),

    DELETE_PERSON("DELETE FROM Person WHERE id = ?"),
    DELETE_TICKET("DELETE FROM Ticket WHERE id = ?"),
    DELETE_LOCATION("DELETE FROM Location WHERE id = ?"),
    DELETE_COORDINATES("DELETE FROM Coordinates WHERE id = ?"),

    SELECT_TICKET("SELECT * FROM Ticket"),
    SELECT_COORDINATES("SELECT * FROM Coordinates"),
    SELECT_PERSON("SELECT * FROM Person"),
    SELECT_LOCATION("SELECT * FROM Location"),
    SELECT_SEQUENCE_TICKET_ID("SELECT last_value FROM id"),
    SELECT_TICKET_INFO("SELECT Utilizer_id, Coordinates_id, Person_id FROM Ticket WHERE id = ?"),
    SELECT_PERSON_INFO("SELECT id ,Location_id From Person WHERE id = ?"),
    SELECT_USER("SELECT id, utilizerName, surname, email FROM Utilizer WHERE (login = ? AND password = ?)"),
    SELECT_USER_LOGIN("SELECT * FROM Utilizer WHERE (login = ?)"),

    SELECT_ALL_IN_ONE_TABLE("SELECT foo.id, foo.name, creationdate, price, comment, refundable, type, foo.x, foo.y, birthday, haircolor, Location.x, Location.y, Location.z, Location.name FROM \n" +
            "\t(SELECT Ticket.id ,name, creationdate, price, comment, refundable, type, Coordinates.x, Coordinates.y, birthday, haircolor, location_id FROM Ticket\n" +
            "\tJOIN Coordinates ON Ticket.coordinates_id = Coordinates.id\n" +
            "\tLEFT JOIN Person ON Ticket.Person_id = Person.id ) AS foo\n" +
            "LEFT JOIN Location ON foo.location_id = Location.id;");



    private final String request;

    DBRequest(String request) {
        this.request = request;
    }
}
