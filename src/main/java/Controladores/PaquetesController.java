package Controladores;

import Exceptions.AtributoVacioException;
import Modelo.AgenciaUQ;
import Modelo.Destino;
import Modelo.PaqueteTuristico;
import javafx.beans.property.SimpleStringProperty;
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
    public Button actualizarBoton;
    public Button eliminarBoton;
    public TableView<PaqueteTuristico> tablaPaquetes = new TableView<>();
    public TableColumn<PaqueteTuristico, String> columnaNombre = new TableColumn<>();
    public TableColumn<PaqueteTuristico, String> columnaDestinos = new TableColumn<>();
    private Destino destinoSeleccionado;
    private final ArrayList<Destino> destinos = new ArrayList<>();
    private PaqueteTuristico paqueteSeleccionado;

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

        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaDestinos.setCellValueFactory(cellData -> {
            StringBuilder destinos = new StringBuilder();
            for (Destino destino : cellData.getValue().getDestinoArrayList()) {
                destinos.append(destino.getCiudad()).append(", ");
            }
            return new SimpleStringProperty(destinos.toString());
        });

        tablaPaquetes.setItems(FXCollections.observableArrayList(agenciaUQ.getPaquetes()));

        tablaPaquetes.getSelectionModel().selectedItemProperty().addListener((observableValue, paqueteAntiguo, paqueteNuevo) -> {
            if (paqueteNuevo != null) {
                paqueteSeleccionado = paqueteNuevo;
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

    public void añadirPaquete() {
        try {
            if(destinoSeleccionado != null) {
                agenciaUQ.añadirPaquete(nombreField.getText(), descripcionArea.getText(), fechaInicial.getValue(), fechaFinal.getValue(), destinos);
                nombreField.setText("");
                descripcionArea.setText("");
                fechaInicial.setValue(null);
                fechaFinal.setValue(null);
                destinos.clear();
                tablaPaquetes.refresh();
            }
        } catch (IOException | AtributoVacioException e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarGestionar() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/ActualizarPaquete.fxml")));
            principalController.panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void actualizarPaquete() {
        try {
            if(paqueteSeleccionado != null) {
                agenciaUQ.actualizarPaquete(paqueteSeleccionado.getNombre());
                tablaPaquetes.refresh();
                paqueteSeleccionado = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarPaquete() {
        try {
            if(paqueteSeleccionado != null) {
                agenciaUQ.eliminarPaquete(paqueteSeleccionado.getNombre());
                tablaPaquetes.getItems().remove(paqueteSeleccionado);
                paqueteSeleccionado = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}