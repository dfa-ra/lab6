package com.ra.server.collection.dbManager;

import com.ra.common.sample.Ticket;
import com.ra.server.collection.dbManager.reqests.CreationRequests;
import com.ra.server.collection.dbManager.reqests.RequestDB;

import java.sql.*;

public class DBManager {

    private final Connection connection;

    public DBManager(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public void dbInit() throws SQLException {
        for (CreationRequests req : CreationRequests.values()) {
            PreparedStatement ps = connection.prepareStatement(req.getRequest());
            ps.execute();
        }
    }

    public void addUser(String username, String password) throws SQLException {
        PreparedStatement psUser = connection.prepareStatement(RequestDB.ADD_USER.getRequest());
        psUser.setString(1, username);
        psUser.setString(2, password);
        psUser.execute();
    }

    public void addTicket(Ticket ticket, String login, String password) throws SQLException {
        PreparedStatement psCoordinates = connection.prepareStatement(RequestDB.ADD_COORDINATES.getRequest(), Statement.RETURN_GENERATED_KEYS);
        PreparedStatement psLocation = connection.prepareStatement(RequestDB.ADD_LOCATION.getRequest(), Statement.RETURN_GENERATED_KEYS);
        PreparedStatement psPerson = connection.prepareStatement(RequestDB.ADD_PERSON.getRequest(), Statement.RETURN_GENERATED_KEYS);
        PreparedStatement psTicket = connection.prepareStatement(RequestDB.ADD_TICKET.getRequest(), Statement.RETURN_GENERATED_KEYS);
        PreparedStatement psTicketToUser = connection.prepareStatement(RequestDB.ADD_USER_TICKET_ID.getRequest(), Statement.RETURN_GENERATED_KEYS);

        psCoordinates.setDouble(1, ticket.getCoordinates().getX());
        psCoordinates.setDouble(2, ticket.getCoordinates().getY());
        psCoordinates.execute();

        if (ticket.getPerson() != null) {
            psLocation.setDouble(1, ticket.getPerson().getLocation().getX());
            psLocation.setDouble(2, ticket.getPerson().getLocation().getY());
            psLocation.setDouble(3, ticket.getPerson().getLocation().getZ());
            psLocation.setString(4, ticket.getPerson().getLocation().getName());
            psLocation.execute();

            psPerson.setString(1, String.valueOf(ticket.getPerson().getBirthday()));
            psPerson.setString(2, String.valueOf(ticket.getPerson().getHairColor()));

            ResultSet rsLocation = psLocation.getGeneratedKeys();
            if (rsLocation.next()) {
                psPerson.setInt(3, rsLocation.getInt(1));
            }
            psPerson.execute();

            ResultSet rsPerson = psPerson.getGeneratedKeys();
            if (rsPerson.next()) {
                psTicket.setInt(8, rsPerson.getInt(1));
            }
        }

        psTicket.setString(1, ticket.getName());
        ResultSet rsCoordinates = psCoordinates.getGeneratedKeys();
        if (rsCoordinates.next()) {
            System.out.println("HERERERERE");
            psTicket.setInt(2, rsCoordinates.getInt(1));
        }
        psTicket.setString(3, String.valueOf(ticket.getCreationDate()));
        psTicket.setDouble(4, ticket.getPrice());
        psTicket.setString(5, ticket.getComment());
        psTicket.setBoolean(6, ticket.getRefundable());
        psTicket.setString(7, String.valueOf(ticket.getType()));

        psTicket.execute();

        ResultSet rsTicket = psTicket.getGeneratedKeys();
        if (rsTicket.next()) {
            psTicketToUser.setInt(1, rsTicket.getInt(1));
        }

        PreparedStatement psSelectUser = connection.prepareStatement(RequestDB.SELECT_USER.getRequest());
        psSelectUser.setString(1, login);
        psSelectUser.setString(2, password);
        ResultSet rsSelectUser = psSelectUser.executeQuery();

        if (rsSelectUser.next()) {
            psTicketToUser.setInt(2, rsSelectUser.getInt(1));
        }


    }
}
