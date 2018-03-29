package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.model.*;
import main.model.Event;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Optional;

public class Main2 extends Application {

    static String currentTableName = "none";
    final static MySQLAccess dao = new MySQLAccess("mydb", "root", "3228");

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
        final StackPane root = new StackPane();
        primaryStage.setTitle("mydb");
        primaryStage.setScene(new Scene(root, 500, 300));

        final VBox vBox = new VBox(5);
        final HBox hBox = new HBox(5);
        final Button addButton = new Button("add");
        final Button deleteButton = new Button("delete");
        final TableView<Doctor> tableViewDoctor = new TableView<>();
        tableViewDoctor.setEditable(true);
        final TableView<Event> tableViewEvent = new TableView<>();
        tableViewEvent.setEditable(true);
        final TableView<Illness> tableViewIllness = new TableView<>();
        tableViewIllness.setEditable(true);
        final TableView<Patient> tableViewPatient = new TableView<>();
        tableViewPatient.setEditable(true);
        final TableView<PatientCard> tableViewPatientCard = new TableView<>();
        tableViewPatientCard.setEditable(true);
        final TableView<Profession> tableViewProfession = new TableView<>();
        tableViewProfession.setEditable(true);

        LinkedList<String> tables = dao.getTables();
        final ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll(tables);
        cb.getSelectionModel().selectFirst();

        cb.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
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
                        currentTableName = "doctor";
                        ObservableList<Doctor> dataDoctor = dao.getDoctorData(cb.getItems().get((Integer) number2).toString());
                        createDoctorTable(columns, tableViewDoctor);
                        tableViewDoctor.setItems(dataDoctor);
                        vBox.getChildren().add(tableViewDoctor);
                        vBox.getChildren().removeAll(tableViewEvent, tableViewIllness, tableViewPatient, tableViewPatientCard, tableViewProfession);
                        break;
                    case "event":
                        currentTableName = "event";
                        ObservableList<Event> dataEvent = dao.getEventData(cb.getItems().get((Integer) number2).toString());
                        createEventTable(columns, tableViewEvent);
                        tableViewEvent.setItems(dataEvent);
                        vBox.getChildren().add(tableViewEvent);
                        vBox.getChildren().removeAll(tableViewDoctor, tableViewIllness, tableViewPatient, tableViewPatientCard, tableViewProfession);
                        break;
                    case "illness":
                        currentTableName = "illness";
                        ObservableList<Illness> dataIllness = dao.getIllnessData(cb.getItems().get((Integer) number2).toString());
                        createIllnessTable(columns, tableViewIllness);
                        tableViewIllness.setItems(dataIllness);
                        vBox.getChildren().add(tableViewIllness);
                        vBox.getChildren().removeAll(tableViewDoctor, tableViewEvent, tableViewPatient, tableViewPatientCard, tableViewProfession);
                        break;
                    case "patient":
                        currentTableName = "patient";
                        ObservableList<Patient> dataPatient = dao.getPatientData(cb.getItems().get((Integer) number2).toString());
                        createPatientTable(columns, tableViewPatient);
                        tableViewPatient.setItems(dataPatient);
                        vBox.getChildren().add(tableViewPatient);
                        vBox.getChildren().removeAll(tableViewDoctor, tableViewEvent, tableViewIllness, tableViewPatientCard, tableViewProfession);
                        break;
                    case "patientcard":
                        currentTableName = "patientcard";
                        ObservableList<PatientCard> dataPatientCard = dao.getPatientCardData(cb.getItems().get((Integer) number2).toString());
                        createPatientCardTable(columns, tableViewPatientCard);
                        tableViewPatientCard.setItems(dataPatientCard);
                        vBox.getChildren().add(tableViewPatientCard);
                        vBox.getChildren().removeAll(tableViewDoctor, tableViewEvent, tableViewIllness, tableViewPatient, tableViewProfession);
                        break;
                    case "profession":
                        currentTableName = "profession";
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
        });

        addButton.setOnMouseClicked(event -> {
            if (currentTableName.equals("doctor")) {

                final StackPane rootAdd = new StackPane();

                Stage secondStage = new Stage();
                secondStage.setTitle("add doctor");
                secondStage.setScene(new Scene(rootAdd, 200, 300));

                VBox vBox1 = new VBox(20);

                vBox1.setAlignment(Pos.CENTER);
                TextField nameTextField = new TextField();
                nameTextField.setPadding(new Insets(5));
                nameTextField.setMaxWidth(180);
                nameTextField.setPromptText("name");

                TextField surnameTextField = new TextField();
                surnameTextField.setPadding(new Insets(5));
                surnameTextField.setMaxWidth(180);
                surnameTextField.setPromptText("surname");

                TextField profTextField = new TextField();
                profTextField.setPromptText("profession");
                profTextField.setMaxWidth(180);
                profTextField.setPadding(new Insets(5));

                Button add = new Button("add");
                add.setPrefWidth(180);
                add.setFont(new Font(18));
                add.setOnMouseClicked(event1 -> {
                    try {
                        tableViewDoctor.getColumns().clear();
                        dao.addDoctorOnPosition(new Doctor(0, nameTextField.getText(), surnameTextField.getText(), Integer.parseInt(profTextField.getText())));
                        LinkedList<String> columns = dao.getColumns("doctor");
                        ObservableList<Doctor> dataDoctor = dao.getDoctorData("doctor");
                        createDoctorTable(columns, tableViewDoctor);
                        tableViewDoctor.setItems(dataDoctor);
//                        vBox.getChildren().add(tableViewDoctor);
////                        vBox.getChildren().removeAll(tableViewEvent, tableViewIllness, tableViewPatient, tableViewPatientCard, tableViewProfession);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Insert operation");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                });

                vBox1.getChildren().addAll(nameTextField, surnameTextField, profTextField, add);
                rootAdd.getChildren().add(vBox1);

                secondStage.showAndWait();
            }
        });

        deleteButton.setOnMouseClicked(event -> {
            if (currentTableName.equals("doctor")) {
                tableViewDoctor.getFocusModel();
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete operation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want delete " + tableViewDoctor.getFocusModel().getFocusedItem().getName());
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get() == null) {
                    } else if (option.get() == ButtonType.OK) {
                        tableViewDoctor.getColumns().clear();
                        dao.deleteDoctorOnPosition(tableViewDoctor.getFocusModel().getFocusedItem().getIdDoctor());
                        LinkedList<String> columns = dao.getColumns("doctor");
                        ObservableList<Doctor> dataDoctor = dao.getDoctorData("doctor");
                        createDoctorTable(columns, tableViewDoctor);
                        tableViewDoctor.setItems(dataDoctor);
                    } else if (option.get() == ButtonType.CANCEL) {
                    } else {
                    }

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete operation");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        });

        hBox.getChildren().addAll(cb, addButton, deleteButton);
        vBox.getChildren().add(hBox);
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
                    tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
                    tableView.getColumns().add(tableColumnName);

                    tableColumnName.setOnEditCommit(event -> {
                        TablePosition<Doctor, String> position = event.getTablePosition();
                        String newValue = event.getNewValue();
                        int row = position.getRow();
                        Doctor doctor = event.getTableView().getItems().get(row);
                        doctor.setName(newValue);
                        try {
                            dao.updateDoctorOnPosition(doctor);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText("Updated successfully!");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }
                    });
                    break;
                case "surname":
                    TableColumn<Doctor, String> tableColumnSurname = new TableColumn<>();
                    tableColumnSurname.setText(column);
                    tableColumnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
                    tableColumnSurname.setCellFactory(TextFieldTableCell.forTableColumn());
                    tableView.getColumns().add(tableColumnSurname);

                    tableColumnSurname.setOnEditCommit(event -> {
                        TablePosition<Doctor, String> position = event.getTablePosition();
                        String newValue = event.getNewValue();
                        int row = position.getRow();
                        Doctor doctor = event.getTableView().getItems().get(row);
                        doctor.setSurname(newValue);
                        try {
                            dao.updateDoctorOnPosition(doctor);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText("Updated successfully!");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }
                    });
                    break;
                case "profession":
                    TableColumn<Doctor, Integer> tableColumnProfession = new TableColumn<>();
                    tableColumnProfession.setText(column);
                    tableColumnProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));
                    tableColumnProfession.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
                        @Override
                        public String toString(Integer object) {
                            return object.toString();
                        }

                        @Override
                        public Integer fromString(String string) {
                            try {
                                return Integer.parseInt(string);
                            } catch (NumberFormatException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Update operation");
                                alert.setHeaderText(null);
                                alert.setContentText("NumberFormatException: " + e.getMessage());
                                alert.showAndWait();
                                return Integer.parseInt(string);
                            }

                        }
                    }));
                    tableView.getColumns().add(tableColumnProfession);

                    tableColumnProfession.setOnEditCommit(event -> {
                        TablePosition<Doctor, Integer> position = event.getTablePosition();
                        int newValue = event.getNewValue();
                        int row = position.getRow();
                        Doctor doctor = event.getTableView().getItems().get(row);
                        doctor.setProfession(newValue);
                        try {
                            dao.updateDoctorOnPosition(doctor);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText("Updated successfully!");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Update operation");
                            alert.setHeaderText(null);
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }

                    });
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

