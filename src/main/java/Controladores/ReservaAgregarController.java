package Controladores;

import Modelo.AgenciaUQ;
import Modelo.GuiaTuristico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Enum.EstadoReserva;

public class ReservaAgregarController implements Initializable {

    @FXML
    private ChoiceBox<Short> boxNumPersonas;
    @FXML
    private Button btnNueva;
    @FXML
    private TextField txtNombre;
    @FXML
    private ChoiceBox<EstadoReserva> boxEstado;
    @FXML
    private ChoiceBox<String> boxGuia;
    @FXML
    private TextField txtPaquete;
    @FXML
    private DatePicker dateSolicitud;
    @FXML
    private TextField txtDocumento;
    @FXML
    private DatePicker dateViaje;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boxEstado.getItems().addAll(EstadoReserva.values());
                for (short i = 1; i <= 20; i++) {
                    boxNumPersonas.getItems().add(i);
                }
                try {
                    ArrayList<String> nombresGuias = AgenciaUQ.leerGuiasNombres();
                    for(String nombre : nombresGuias) {
                        boxGuia.getItems().add(nombre);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @FXML
    public void showNuevaReserva(ActionEvent event) {

    }

}
