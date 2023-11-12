package Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    private static PrincipalController instance;
    public static PrincipalController getInstance() {
        if (instance == null) {
            instance = new PrincipalController();
        }
        return instance;
    }
    public Button botonGestion;
    public Button botonBuscador;
    @FXML
    public AnchorPane panelFormulario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
    }

    public void mostrarGestion() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/Gestion.fxml")));
            panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarBuscador() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/Buscador.fxml")));
            panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}