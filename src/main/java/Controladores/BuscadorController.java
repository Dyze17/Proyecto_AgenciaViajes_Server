package Controladores;

import Modelo.AgenciaUQ;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class BuscadorController implements Initializable {
    public Button buscarBoton;
    public ComboBox<String> filtrosBox = new ComboBox<>();
    public TextField buscaField;
    private static final AgenciaUQ instance = AgenciaUQ.getInstance();
    private final String[] filtros = {"Pais", "Ciudad", "Clima"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filtrosBox.getItems().addAll(filtros);
    }
}