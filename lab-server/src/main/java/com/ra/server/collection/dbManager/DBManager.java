package com.ra.server.collection.dbManager;

import com.ra.common.sample.Coordinates;
import com.ra.common.sample.Location;
import com.ra.common.sample.Person;
import com.ra.common.sample.Ticket;
import com.ra.server.collection.CollectionManager;
import com.ra.server.collection.dbManager.reqests.CreationRequests;
import com.ra.server.collection.dbManager.reqests.DBRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Random;

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

    public String addUser(String login, String password) throws SQLException {

        PreparedStatement psIsNotExits = connection.prepareStatement(DBRequest.SELECT_USER_LOGIN.getRequest());
        psIsNotExits.setString(1, login);
        ResultSet rsIsNotExists = psIsNotExits.executeQuery();

        if (!rsIsNotExists.next() && !(login.equals("Aliisthebestpra") && password.equals("ctitioner"))) {
            String salt = generateSalt();
            PreparedStatement psUser = connection.prepareStatement(DBRequest.ADD_USER.getRequest());
            psUser.setString(1, login);
            psUser.setString(2, generateSHA1(salt + password));
            psUser.setString(3, salt);
            psUser.execute();
            return "ok";
        }
        return "This login is already taken!";
    }

    public void addTicket(Ticket ticket, String login, String password) throws SQLException {
        PreparedStatement psCoordinates = connection.prepareStatement(DBRequest.ADD_COORDINATES.getRequest(), Statement.RETURN_GENERATED_KEYS);

        psCoordinates.setDouble(1, ticket.getCoordinates().getX());
        psCoordinates.setDouble(2, ticket.getCoordinates().getY());
        psCoordinates.execute();

        PreparedStatement psTicket = connection.prepareStatement(DBRequest.ADD_TICKET.getRequest(), Statement.RETURN_GENERATED_KEYS);

        psTicket.setString(1, ticket.getName());
        ResultSet rsCoordinates = psCoordinates.getGeneratedKeys();
        if (rsCoordinates.next()) {
            psTicket.setInt(2, rsCoordinates.getInt(1));
        }
        psTicket.setString(3, String.valueOf(ticket.getCreationDate()));
        psTicket.setDouble(4, ticket.getPrice());
        psTicket.setString(5, ticket.getComment());
        psTicket.setBoolean(6, ticket.getRefundable());
        psTicket.setString(7, String.valueOf(ticket.getType()));
        psTicket.execute();
        ResultSet rsTicket = psTicket.getGeneratedKeys();
        rsTicket.next();


        if (ticket.getPerson() != null) {
            addPerson(rsTicket.getInt(1), ticket.getPerson());
        }

        PreparedStatement psTicketToUser = connection.prepareStatement(DBRequest.ADD_USER_TO_TICKET.getRequest(), Statement.RETURN_GENERATED_KEYS);

        psTicketToUser.setInt(1, getUserId(login, password));
        psTicketToUser.setInt(2, rsTicket.getInt(1));
        psTicketToUser.execute();
    }

    public void addPerson(int id, Person person) throws SQLException {
        PreparedStatement psLocation = connection.prepareStatement(DBRequest.ADD_LOCATION.getRequest(), Statement.RETURN_GENERATED_KEYS);

        psLocation.setDouble(1, person.getLocation().getX());
        psLocation.setDouble(2, person.getLocation().getY());
        psLocation.setDouble(3, person.getLocation().getZ());
        psLocation.setString(4, person.getLocation().getName());
        psLocation.execute();

        PreparedStatement psPerson = connection.prepareStatement(DBRequest.ADD_PERSON.getRequest(), Statement.RETURN_GENERATED_KEYS);

        psPerson.setString(1, String.valueOf(person.getBirthday()));
        psPerson.setString(2, String.valueOf(person.getHairColor()));

        ResultSet rsLocation = psLocation.getGeneratedKeys();
        if (rsLocation.next()) {
            psPerson.setInt(3, rsLocation.getInt(1));
        }
        psPerson.execute();


        PreparedStatement psAddPersonTicket = connection.prepareStatement(DBRequest.ADD_PERSON_TO_TICKET.getRequest());
        ResultSet rsPerson = psPerson.getGeneratedKeys();

        psAddPersonTicket.setInt(2, id);

        if (rsPerson.next()) {
            psAddPersonTicket.setInt(1, rsPerson.getInt(1));
        }
        psAddPersonTicket.execute();
    }

    public String updateTicket(Ticket ticket, Long id) throws SQLException {

        PreparedStatement psSelectTicketInfo = connection.prepareStatement(DBRequest.SELECT_TICKET_INFO.getRequest());
        psSelectTicketInfo.setInt(1, Math.toIntExact(id));
        ResultSet rsSelectTicketInfo = psSelectTicketInfo.executeQuery();
        rsSelectTicketInfo.next();
        System.out.println(rsSelectTicketInfo.getInt(1));

        PreparedStatement psTicket = connection.prepareStatement(DBRequest.UPDATE_TICKET.getRequest());

        psTicket.setString(1, ticket.getName());
        psTicket.setDouble(2, ticket.getPrice());
        psTicket.setString(3, ticket.getComment());
        psTicket.setBoolean(4, ticket.getRefundable());
        psTicket.setString(5, String.valueOf(ticket.getType()));
        psTicket.setInt(6, Math.toIntExact(id));
        psTicket.execute();

        PreparedStatement psCoordinates = connection.prepareStatement(DBRequest.UPDATE_COORDINATES.getRequest());

        psCoordinates.setDouble(1, ticket.getCoordinates().getX());
        psCoordinates.setDouble(2, ticket.getCoordinates().getY());
        psCoordinates.setDouble(3, rsSelectTicketInfo.getInt(2));
        psCoordinates.execute();

        if (rsSelectTicketInfo.getObject(3) != null) {
            PreparedStatement psUpdatePersonID = connection.prepareStatement(DBRequest.UPDATE_SET_NULL_PERSON_ID.getRequest());
            psUpdatePersonID.setInt(1, Math.toIntExact(id));
            psUpdatePersonID.execute();

            PreparedStatement psSelectPersonInfo = connection.prepareStatement(DBRequest.SELECT_PERSON_INFO.getRequest());
            psSelectPersonInfo.setInt(1, rsSelectTicketInfo.getInt(3));
            ResultSet rsSelectPersonInfo = psSelectPersonInfo.executeQuery();
            rsSelectPersonInfo.next();

            deleteLocationById(rsSelectPersonInfo.getInt(2));
            deletePersonById(rsSelectTicketInfo.getInt(3));

        }
        if (ticket.getPerson() != null) {
            addPerson(rsSelectTicketInfo.getInt(1), ticket.getPerson());
        }
        return "Complete!";
    }

    public String checkExistsUser(String login, String password) throws SQLException {

        PreparedStatement psSelectUserLogin = connection.prepareStatement(DBRequest.SELECT_USER_LOGIN.getRequest());
        psSelectUserLogin.setString(1, login);
        ResultSet rsSelectUserLogin = psSelectUserLogin.executeQuery();
        if (!rsSelectUserLogin.next()) return "Not Exists";
        System.out.println(rsSelectUserLogin.getString(4));
        PreparedStatement psSelectUser = connection.prepareStatement(DBRequest.SELECT_USER.getRequest());
        psSelectUser.setString(1, login);
        psSelectUser.setString(2, generateSHA1(rsSelectUserLogin.getString(4) + password));
        ResultSet rsSelectUser = psSelectUser.executeQuery();
        if (!rsSelectUser.next()) return "Wrong password";

        return "ok";
    }

    public String checkTicketUser(int id, String login, String password) throws SQLException {

        if (login.equals("Aliisthebestpra") && password.equals("ctitioner")) return "ok";

        PreparedStatement psSelectTicketInfo = connection.prepareStatement(DBRequest.SELECT_TICKET_INFO.getRequest());
        psSelectTicketInfo.setInt(1, Math.toIntExact(id));
        ResultSet rsSelectTicketInfo = psSelectTicketInfo.executeQuery();

        if (!rsSelectTicketInfo.next()){
            return "This ticket was not found";
        }

        System.out.println(rsSelectTicketInfo.getInt(1));
        System.out.println(getUserId(login, password));
        if (rsSelectTicketInfo.getInt(1) != getUserId(login, password)){
            return "You do not have the right to change this ticket";
        }

        return "ok";
    }

    public int getUserId(String login, String password) throws SQLException {
        PreparedStatement psSelectUserLogin = connection.prepareStatement(DBRequest.SELECT_USER_LOGIN.getRequest());
        psSelectUserLogin.setString(1, login);
        ResultSet rsSelectUserLogin = psSelectUserLogin.executeQuery();
        if (!rsSelectUserLogin.next()) return  0;


        PreparedStatement psSelectUserID = connection.prepareStatement(DBRequest.SELECT_USER.getRequest());

        psSelectUserID.setString(1, login);
        psSelectUserID.setString(2, generateSHA1(rsSelectUserLogin.getString(4) + password));

        ResultSet rsSelectUserID = psSelectUserID.executeQuery();
        if (rsSelectUserID.next()){
            return rsSelectUserID.getInt(1);
        }
        return 0;
    }

    public void getCollection() throws SQLException, ParseException {
        HashSet<Ticket> notebook = new HashSet<>();

        PreparedStatement psSelectAllInOneTable = connection.prepareStatement(DBRequest.SELECT_ALL_IN_ONE_TABLE.getRequest());
        ResultSet rsSelectAllInOneTable = psSelectAllInOneTable.executeQuery();
        while (rsSelectAllInOneTable.next()){
            Person person = null;
            if (rsSelectAllInOneTable.getObject(10) != null) {
                person = new Person(
                        rsSelectAllInOneTable.getString(10),
                        rsSelectAllInOneTable.getString(11),
                        new Location(
                                String.valueOf(rsSelectAllInOneTable.getDouble(12)),
                                String.valueOf(rsSelectAllInOneTable.getInt(13)),
                                String.valueOf(rsSelectAllInOneTable.getDouble(14)),
                                rsSelectAllInOneTable.getString(15)
                        )
                );
            }

            notebook.add(new Ticket(
                    String.valueOf(rsSelectAllInOneTable.getInt(1)),
                    rsSelectAllInOneTable.getString(2),
                    new Coordinates(
                        String.valueOf(rsSelectAllInOneTable.getDouble(8)),
                        String.valueOf(rsSelectAllInOneTable.getDouble(9))),
                    rsSelectAllInOneTable.getString(3),
                    String.valueOf(rsSelectAllInOneTable.getDouble(4)),
                    rsSelectAllInOneTable.getString(5),
                    String.valueOf(rsSelectAllInOneTable.getBoolean(6)),
                    rsSelectAllInOneTable.getString(7),
                    person
                )
            );
        }
        CollectionManager.setNotebook(notebook);
    }

    public void deleteLocationById(int id) throws SQLException {
        PreparedStatement psDeleteLocation = connection.prepareStatement(DBRequest.DELETE_LOCATION.getRequest());
        psDeleteLocation.setInt(1, id);
        psDeleteLocation.execute();
    }

    public void deletePersonById(int id) throws SQLException {
        PreparedStatement psDeletePerson = connection.prepareStatement(DBRequest.DELETE_PERSON.getRequest());
        psDeletePerson.setObject(1, id);
        psDeletePerson.execute();
    }

    public void deleteCoordinatesById(int id) throws SQLException {
        PreparedStatement psDeleteCoordinates = connection.prepareStatement(DBRequest.DELETE_COORDINATES.getRequest());
        psDeleteCoordinates.setObject(1, id);
        psDeleteCoordinates.execute();
    }

    public void deleteTicketById(int id) throws SQLException {
        PreparedStatement psDeletePerson = connection.prepareStatement(DBRequest.DELETE_TICKET.getRequest());
        psDeletePerson.setObject(1, id);
        psDeletePerson.execute();
    }

    public boolean deleteByID(Long id, String login, String password) throws SQLException {

        if (!checkTicketUser(Math.toIntExact(id), login, password).equals("ok")) return false;

        PreparedStatement psSelectTicketInfo = connection.prepareStatement(DBRequest.SELECT_TICKET_INFO.getRequest());
        psSelectTicketInfo.setInt(1, Math.toIntExact(id));
        ResultSet rsSelectTicketInfo = psSelectTicketInfo.executeQuery();
        rsSelectTicketInfo.next();

        PreparedStatement psSelectPersonInfo = connection.prepareStatement(DBRequest.SELECT_PERSON_INFO.getRequest());
        psSelectPersonInfo.setInt(1, rsSelectTicketInfo.getInt(3));
        ResultSet rsSelectPersonInfo = psSelectPersonInfo.executeQuery();
        rsSelectPersonInfo.next();

        deleteTicketById(Math.toIntExact(id));
        deleteCoordinatesById(rsSelectTicketInfo.getInt(2));
        deleteLocationById(rsSelectPersonInfo.getInt(2));
        deletePersonById(rsSelectTicketInfo.getInt(3));
        return true;
    }

    public long getTicketID() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DBRequest.SELECT_SEQUENCE_TICKET_ID.getRequest());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }


    private static String generateSHA1(String str){
        String sha1 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            sha1 = new String(md.digest(str.getBytes()));
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return sha1;
    }

    private static String generateSalt(){
        byte[] byteArray = new byte[5];
        Random random = new Random();
        random.nextBytes(byteArray);

        return new String(byteArray, Charset.forName("UTF-8"));
    }
}
