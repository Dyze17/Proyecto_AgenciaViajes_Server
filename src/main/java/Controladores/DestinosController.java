package Controladores;

import Exceptions.AtributoVacioException;
import Modelo.AgenciaUQ;
import Modelo.Destino;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DestinosController implements Initializable {
    public Button selecImgBoton;
    public Button addDestBoton;
    public ComboBox<String> climaBox = new ComboBox<>();
    private final String[] climas = {"Cálido", "Frío", "Templado"};
    public TextField paisField;
    public TextField ciudadField;
    public TextArea descripcionArea;
    private final AgenciaUQ agenciaUQ = AgenciaUQ.getInstance();
    private final PrincipalController principalController = PrincipalController.getInstance();
    public Button añadirBoton;
    public TableView<Destino> tablaDestinos = new TableView<>();
    public TableColumn<Destino, String> paisColumna = new TableColumn<>();
    public TableColumn<Destino, String> ciudadColumna = new TableColumn<>();
    public Button actualizarBoton;
    public Button eliminarBoton;
    public Button gestionarBoton;
    private String imagen;
    private Destino destinoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        climaBox.getItems().addAll(climas);

        paisColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPais()));
        ciudadColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));

        tablaDestinos.setItems(FXCollections.observableArrayList(agenciaUQ.getDestinos()));

        tablaDestinos.getSelectionModel().selectedItemProperty().addListener((observableValue, destinoAntiguo, destinoNuevo) -> {
            if (destinoNuevo != null) {
                destinoSeleccionado = destinoNuevo;
            }
        });
    }

    public void mostrarAñadirDestino() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/AñadirDestino.fxml")));
            principalController.panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void agregarDestino() {
        try{
            agenciaUQ.agregarDestino(
                    paisField.getText(),
                    ciudadField.getText(),
                    climaBox.getValue(),
                    descripcionArea.getText(),
                    imagen);
            paisField.setText("");
            ciudadField.setText("");
            descripcionArea.setText("");
            climaBox.setValue("");
        } catch (AtributoVacioException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarDestino() {
        if(destinoSeleccionado != null) {
            agenciaUQ.eliminarDestino(destinoSeleccionado.getPais(), destinoSeleccionado.getCiudad());
            tablaDestinos.getItems().remove(destinoSeleccionado);
            destinoSeleccionado = null;
        }
    }

    public void actualizarDestino() {
        if(destinoSeleccionado != null) {
            agenciaUQ.actualizarDestino(destinoSeleccionado.getPais(), destinoSeleccionado.getCiudad());
            tablaDestinos.refresh();
            destinoSeleccionado = null;
        }
    }

    public void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg"));
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if(archivoSeleccionado != null) {
            imagen = archivoSeleccionado.getAbsolutePath();
        }
    }

    public void mostrarGestionarDestino() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/ActualizarDestino.fxml")));
            principalController.panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}