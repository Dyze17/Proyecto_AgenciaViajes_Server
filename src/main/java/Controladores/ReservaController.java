package Controladores;

import Modelo.AgenciaUQ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class ReservaController {

    @FXML
    private Pane paneDer;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnModificar;

    @FXML
    void showAgregar(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/ReservaAgregar.fxml")));
            paneDer.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void showModificar(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/ReservaModificar.fxml")));
            paneDer.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
