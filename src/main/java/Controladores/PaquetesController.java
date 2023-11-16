package Controladores;

import Exceptions.AtributoVacioException;
import Modelo.AgenciaUQ;
import Modelo.Destino;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaquetesController implements Initializable {
    private final PrincipalController principalController = PrincipalController.getInstance();
    private final AgenciaUQ agenciaUQ = AgenciaUQ.getInstance();
    public Button añadirBoton;
    public Button gestionarBoton;
    public TextField nombreField;
    public TextArea descripcionArea;
    public DatePicker fechaInicial;
    public DatePicker fechaFinal;
    public Button añadirPaqueteBoton;
    public TableView<Destino> tablaDestinos = new TableView<>();
    public TableColumn<Destino, String> columnaPais = new TableColumn<>();
    public TableColumn<Destino, String> columnaCiudad = new TableColumn<>();
    private Destino destinoSeleccionado;
    private final ArrayList<Destino> destinos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnaPais.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPais()));
        columnaCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));

        tablaDestinos.setItems(FXCollections.observableArrayList(agenciaUQ.getDestinos()));

        tablaDestinos.getSelectionModel().selectedItemProperty().addListener((observableValue, destinoAntiguo, destinoNuevo) -> {
            if (destinoNuevo != null) {
                destinoSeleccionado = destinoNuevo;
                destinos.add(destinoNuevo);
            }
        });
    }

    public void mostrarAñadir() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/AñadirPaquete.fxml")));
            principalController.panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void añadirPaquete() throws AtributoVacioException {
        try {
            if(destinoSeleccionado != null) {
                agenciaUQ.añadirPaquete(nombreField.getText(), descripcionArea.getText(), fechaInicial.getValue(), fechaFinal.getValue(), destinos);
                nombreField.setText("");
                descripcionArea.setText("");
                fechaInicial.setValue(null);
                fechaFinal.setValue(null);
                destinos.clear();
            }
        } catch (AtributoVacioException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}