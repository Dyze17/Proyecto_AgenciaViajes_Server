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
    private static PrincipalController instance;

    public static PrincipalController getInstance() {
        if (instance == null) {
            instance = new PrincipalController();
        }
        return instance;
    }

    public Button botonIniciar;
    public Button botonGestion;
    public Button botonBuscador;

    public Button btnModificarPerfil;

    public AnchorPane panelFormulario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
    }

    public void showReserva() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/Reserva.fxml")));
            panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showModificar (){
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/Modificar.fxml")));
            panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarGestion() {
        //Solamente muestra la pantalla de gestion si el usuario iniciado de sesion es administrador
        if(IniciarSesionController.iniciado && IniciarSesionController.administrador) {
            try {
                Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/Gestion.fxml")));
                panelFormulario.getChildren().setAll(node);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/IniciarSesion.fxml")));
                panelFormulario.getChildren().setAll(node);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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

    public void mostrarIniciar() {
        if(IniciarSesionController.iniciado) {
            try {
                Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/SesionIniciada.fxml")));
                panelFormulario.getChildren().setAll(node);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/IniciarSesion.fxml")));
                panelFormulario.getChildren().setAll(node);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}