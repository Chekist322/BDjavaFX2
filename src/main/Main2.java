package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.model.*;

import java.sql.SQLException;
import java.util.LinkedList;

public class Main2 extends Application {

    enum Tables {
        doctor,
        event,
        illness,
        patient,
        patientcard,
        profession
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final MySQLAccess dao = new MySQLAccess("mydb", "root", "3228");

        final StackPane root = new StackPane();
        primaryStage.setTitle("mydb");
        primaryStage.setScene(new Scene(root, 500, 300));

        final VBox vBox = new VBox(5);
        final TableView<Doctor> tableViewDoctor = new TableView<>();
        final TableView<Event> tableViewEvent = new TableView<>();
        final TableView<Illness> tableViewIllness = new TableView<>();
        final TableView<Patient> tableViewPatient = new TableView<>();
        final TableView<PatientCard> tableViewPatientCard = new TableView<>();
        final TableView<Profession> tableViewProfession = new TableView<>();

        LinkedList<String> tables = dao.getTables();
        final ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll(tables);
        cb.getSelectionModel().selectFirst();

        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                try {
                    LinkedList<String> columns = dao.getColumns(cb.getItems().get((Integer) number2).toString());

                    tableViewDoctor.getColumns().clear();
                    tableViewEvent.getColumns().clear();
                    tableViewIllness.getColumns().clear();
                    tableViewPatient.getColumns().clear();
                    tableViewPatientCard.getColumns().clear();
                    tableViewProfession.getColumns().clear();
                    switch (cb.getItems().get((Integer) number2).toString()) {
                        case "doctor":
                            ObservableList<Doctor> dataDoctor = dao.getDoctorData(cb.getItems().get((Integer) number2).toString());
                            createDoctorTable(columns, tableViewDoctor);
                            tableViewDoctor.setItems(dataDoctor);
                            vBox.getChildren().add(tableViewDoctor);
                            vBox.getChildren().removeAll(tableViewEvent, tableViewIllness, tableViewPatient, tableViewPatientCard, tableViewProfession);
                            break;
                        case "event":
                            ObservableList<Event> dataEvent = dao.getEventData(cb.getItems().get((Integer) number2).toString());
                            createEventTable(columns, tableViewEvent);
                            tableViewEvent.setItems(dataEvent);
                            vBox.getChildren().add(tableViewEvent);
                            vBox.getChildren().removeAll(tableViewDoctor, tableViewIllness, tableViewPatient, tableViewPatientCard, tableViewProfession);
                            break;
                        case "illness":
                            ObservableList<Illness> dataIllness = dao.getIllnessData(cb.getItems().get((Integer) number2).toString());
                            createIllnessTable(columns, tableViewIllness);
                            tableViewIllness.setItems(dataIllness);
                            vBox.getChildren().add(tableViewIllness);
                            vBox.getChildren().removeAll(tableViewDoctor, tableViewEvent, tableViewPatient, tableViewPatientCard, tableViewProfession);
                            break;
                        case "patient":
                            ObservableList<Patient> dataPatient = dao.getPatientData(cb.getItems().get((Integer) number2).toString());
                            createPatientTable(columns, tableViewPatient);
                            tableViewPatient.setItems(dataPatient);
                            vBox.getChildren().add(tableViewPatient);
                            vBox.getChildren().removeAll(tableViewDoctor, tableViewEvent, tableViewIllness, tableViewPatientCard, tableViewProfession);
                            break;
                        case "patientcard":
                            ObservableList<PatientCard> dataPatientCard = dao.getPatientCardData(cb.getItems().get((Integer) number2).toString());
                            createPatientCardTable(columns, tableViewPatientCard);
                            tableViewPatientCard.setItems(dataPatientCard);
                            vBox.getChildren().add(tableViewPatientCard);
                            vBox.getChildren().removeAll(tableViewDoctor, tableViewEvent, tableViewIllness, tableViewPatient, tableViewProfession);
                            break;
                        case "profession":
                            ObservableList<Profession> dataProfession = dao.getProfessionData(cb.getItems().get((Integer) number2).toString());
                            createProfessionTable(columns, tableViewProfession);
                            tableViewProfession.setItems(dataProfession);
                            vBox.getChildren().add(tableViewProfession);
                            vBox.getChildren().removeAll(tableViewDoctor, tableViewEvent, tableViewPatient, tableViewPatientCard, tableViewIllness);
                            break;


                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        vBox.getChildren().add(cb);

//        if (!tables.isEmpty()) {
//            LinkedList<String> columns = dao.getColumns(tables.get(0));
//            if (columns != null)
//                for (int i = 0; i < columns.size(); i++) {
//                    TableColumn tableColumn = new TableColumn();
//                    tableColumn.setText(columns.get(i));
//                    tableView.getColumns().add(tableColumn);
//                }
//        }


        root.getChildren().add(vBox);
        primaryStage.show();
        if (cb.getValue() != null)
            dao.getColumns(cb.getValue().toString());
    }

    private static void createDoctorTable(LinkedList<String> columns, TableView<Doctor> tableView) {
        for (String column : columns) {
            switch (column) {
                case "idDoctor":
                    TableColumn<Doctor, String> tableColumn = new TableColumn<>();
                    tableColumn.setText(column);
                    tableColumn.setCellValueFactory(new PropertyValueFactory<>("idDoctor"));
                    tableView.getColumns().add(tableColumn);
                    break;
                case "name":
                    TableColumn<Doctor, String> tableColumnName = new TableColumn<>();
                    tableColumnName.setText(column);
                    tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    tableView.getColumns().add(tableColumnName);
                    break;
                case "surname":
                    TableColumn<Doctor, String> tableColumnSurname = new TableColumn<>();
                    tableColumnSurname.setText(column);
                    tableColumnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
                    tableView.getColumns().add(tableColumnSurname);
                    break;
                case "profession":
                    TableColumn<Doctor, String> tableColumnProfession = new TableColumn<>();
                    tableColumnProfession.setText(column);
                    tableColumnProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));
                    tableView.getColumns().add(tableColumnProfession);
                    break;
            }
        }
    }

    private static void createEventTable(LinkedList<String> columns, TableView<Event> tableView) {
        for (String column : columns) {
            switch (column) {
                case "idEvent":
                    TableColumn<Event, String> idEvent = new TableColumn<>();
                    idEvent.setText(column);
                    idEvent.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
                    tableView.getColumns().add(idEvent);
                    break;
                case "time":
                    TableColumn<Event, String> time = new TableColumn<>();
                    time.setText(column);
                    time.setCellValueFactory(new PropertyValueFactory<>("time"));
                    tableView.getColumns().add(time);
                    break;
                case "patient":
                    TableColumn<Event, String> patient = new TableColumn<>();
                    patient.setText(column);
                    patient.setCellValueFactory(new PropertyValueFactory<>("patient"));
                    tableView.getColumns().add(patient);
                    break;
                case "doctor":
                    TableColumn<Event, String> doctor = new TableColumn<>();
                    doctor.setText(column);
                    doctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
                    tableView.getColumns().add(doctor);
                    break;
                case "procedure":
                    TableColumn<Event, String> procedure = new TableColumn<>();
                    procedure.setText(column);
                    procedure.setCellValueFactory(new PropertyValueFactory<>("procedure"));
                    tableView.getColumns().add(procedure);
                    break;
            }
        }
    }

    private static void createIllnessTable(LinkedList<String> columns, TableView<Illness> tableView) {
        for (String column : columns) {
            switch (column) {
                case "idIllness":
                    TableColumn<Illness, String> idIllness = new TableColumn<>();
                    idIllness.setText(column);
                    idIllness.setCellValueFactory(new PropertyValueFactory<>("id"));
                    tableView.getColumns().add(idIllness);
                    break;
                case "illness_name":
                    TableColumn<Illness, String> name = new TableColumn<>();
                    name.setText(column);
                    name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    tableView.getColumns().add(name);
                    break;
            }
        }
    }

    private static void createPatientTable(LinkedList<String> columns, TableView<Patient> tableView) {
        for (String column : columns) {
            switch (column) {
                case "idPatient":
                    TableColumn<Patient, String> idIllness = new TableColumn<>();
                    idIllness.setText(column);
                    idIllness.setCellValueFactory(new PropertyValueFactory<>("id"));
                    tableView.getColumns().add(idIllness);
                    break;
                case "patientName":
                    TableColumn<Patient, String> name = new TableColumn<>();
                    name.setText(column);
                    name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    tableView.getColumns().add(name);
                    break;
                case "patientSurname":
                    TableColumn<Patient, String> surname = new TableColumn<>();
                    surname.setText(column);
                    surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
                    tableView.getColumns().add(surname);
                    break;
            }
        }
    }

    private static void createPatientCardTable(LinkedList<String> columns, TableView<PatientCard> tableView) {
        for (String column : columns) {
            switch (column) {
                case "idPatientCard":
                    TableColumn<PatientCard, String> idPatientCard = new TableColumn<>();
                    idPatientCard.setText(column);
                    idPatientCard.setCellValueFactory(new PropertyValueFactory<>("id"));
                    tableView.getColumns().add(idPatientCard);
                    break;
                case "patient":
                    TableColumn<PatientCard, String> patient = new TableColumn<>();
                    patient.setText(column);
                    patient.setCellValueFactory(new PropertyValueFactory<>("patient"));
                    tableView.getColumns().add(patient);
                    break;
                case "illness":
                    TableColumn<PatientCard, String> illness = new TableColumn<>();
                    illness.setText(column);
                    illness.setCellValueFactory(new PropertyValueFactory<>("illness"));
                    tableView.getColumns().add(illness);
                    break;
            }
        }
    }

    private static void createProfessionTable(LinkedList<String> columns, TableView<Profession> tableView) {
        for (String column : columns) {
            switch (column) {
                case "idProfession":
                    TableColumn<Profession, String> idPatientCard = new TableColumn<>();
                    idPatientCard.setText(column);
                    idPatientCard.setCellValueFactory(new PropertyValueFactory<>("id"));
                    tableView.getColumns().add(idPatientCard);
                    break;
                case "profession_name":
                    TableColumn<Profession, String> patient = new TableColumn<>();
                    patient.setText(column);
                    patient.setCellValueFactory(new PropertyValueFactory<>("name"));
                    tableView.getColumns().add(patient);
                    break;
                case "payment":
                    TableColumn<Profession, String> illness = new TableColumn<>();
                    illness.setText(column);
                    illness.setCellValueFactory(new PropertyValueFactory<>("payment"));
                    tableView.getColumns().add(illness);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

