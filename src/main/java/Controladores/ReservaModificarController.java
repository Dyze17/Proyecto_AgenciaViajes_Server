package Controladores;

import Modelo.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReservaModificarController implements Initializable {

    @FXML
    private TableColumn<?, ?> columNombre;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    @FXML
    private TableColumn<?, ?> columPaquete;
    @FXML
    private TableColumn<?, ?> columEstado;
    @FXML
    private TableView<?> tableReserva;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void showModificar(ActionEvent event) {



    }

    @FXML
    void showEliminar(ActionEvent event) {

    }


}
