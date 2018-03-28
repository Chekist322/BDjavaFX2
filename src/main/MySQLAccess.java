package main;


//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.*;

import java.sql.*;
import java.util.LinkedList;

public class MySQLAccess {
    private String login;
    private String password;
    private String dataBaseName;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MySQLAccess(String dataBaseName, String login, String password) {
        this.dataBaseName = dataBaseName;
        this.login = login;
        this.password = password;

        connectToDataBase();
    }

    public void connectToDataBase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/"
                            + dataBaseName+ "?user=" + login + "&password="
                            + password + "&verifyServerCertificate=false"+
                            "&useSSL=false"+
                            "&requireSSL=false"+
                            "&useLegacyDatetimeCode=false"+
                            "&amp"+
                            "&serverTimezone=UTC");
            statement = connect.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.println("У вас не установлен драйвер");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ошибка присоеденения к БД. Возможно, не запушен сервер или нет БД с таким именем");
            e.printStackTrace();
        }
    }

    public LinkedList<String> getTables() throws SQLException {
        LinkedList<String> tables = new LinkedList<>();
        DatabaseMetaData md = connect.getMetaData();
        resultSet = md.getTables(null, null, "%", null);
        while (resultSet != null && resultSet.next()) {
            if (resultSet.getString(3).equals(Main2.Tables.doctor.name()) ||
                    resultSet.getString(3).equals(Main2.Tables.event.name()) ||
                    resultSet.getString(3).equals(Main2.Tables.illness.name()) ||
                    resultSet.getString(3).equals(Main2.Tables.patient.name()) ||
                    resultSet.getString(3).equals(Main2.Tables.patientcard.name()) ||
                    resultSet.getString(3).equals(Main2.Tables.profession.name()))
            tables.add(resultSet.getString(3));
        }
        return tables;
    }

    public LinkedList<String> getColumns(String tableName) throws SQLException {
        LinkedList<String> columns = new LinkedList<String>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Нумерация колонок начинается с 1
            for (int i = 1; i < columnCount + 1; i++) {
                columns.add(rsmd.getColumnName(i));
            }
            return columns;
        } catch (SQLSyntaxErrorException e) {
            return null;
        }
    }

    public ObservableList<Doctor> getDoctorData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            doctors.add(new Doctor(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4)));
        }
        return doctors;
    }

    public ObservableList<Event> getEventData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Event> events = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            events.add(new Event(rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getInt(5)));
        }
        return events;
    }

    public ObservableList<Illness> getIllnessData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Illness> illnesses = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            illnesses.add(new Illness(rs.getInt(1),
                    rs.getString(2)));
        }
        return illnesses;
    }

    public ObservableList<Patient> getPatientData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            patients.add(new Patient(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)));
        }
        return patients;
    }

    public ObservableList<PatientCard> getPatientCardData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<PatientCard> patients = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            patients.add(new PatientCard(rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3)));
        }
        return patients;
    }

    public ObservableList<Profession> getProfessionData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        ObservableList<Profession> professions = FXCollections.observableArrayList();
        while (rs != null && rs.next()) {
            professions.add(new Profession(rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3)));
        }
        return professions;
    }
}
