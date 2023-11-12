package Controladores;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    public Button botonGestion;
    public Button botonBuscador;
    public static AnchorPane panelFormulario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void mostrarGestion() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/Gestion.fxml")));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void mostrarBuscador() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/Buscador.fxml")));
            panelFormulario.getChildren().setAll(node);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}