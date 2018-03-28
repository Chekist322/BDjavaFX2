package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.LinkedList;

public class Main2 extends Application {

    enum Tables {
        Doctor,
        Event,
        Illness,
        Patient,
        PatientCard,
        Profession
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        final MySQLAccess dao = new MySQLAccess("mydb", "root", "322");

        final Group root = new Group();
        primaryStage.setTitle("mydb");
        primaryStage.setScene(new Scene(root, 500, 300));

        final VBox vBox = new VBox(5);
        final TableView tableView = new TableView();

        LinkedList<String> tables = dao.getTables();
        final ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll(tables);
        cb.getSelectionModel().selectFirst();
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                try {
                    LinkedList<String> columns = dao.getColumns(cb.getItems().get((Integer) number2).toString());
                    ObservableList<?> data = dao.getData(cb.getItems().get((Integer) number2).toString());
                    tableView.getColumns().clear();

                    for (int i = 0; i < columns.size(); i++) {
                        TableColumn tableColumn = new TableColumn();
                        tableColumn.setText(columns.get(i));
                        tableView.getColumns().add(tableColumn);
                    }
                    tableView.setItems(data);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        vBox.getChildren().add(cb);


        LinkedList<String> columns = dao.getColumns(tables.get(0));
        if (columns != null)
        for (int i = 0; i < columns.size(); i++) {
            TableColumn tableColumn = new TableColumn();
            tableColumn.setText(columns.get(i));
            tableView.getColumns().add(tableColumn);
        }
        vBox.getChildren().add(tableView);

        root.getChildren().add(vBox);
        primaryStage.show();

        dao.getColumns(cb.getValue().toString());
    }


    public static void main(String[] args) {
        launch(args);
    }
}

