package Controladores;

import Modelo.AgenciaUQ;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.Objects;

public class IniciarSesionController {
    private static IniciarSesionController instance;
    public Button RegistrarBoton;
    public TextField documentoTxt;
    public TextField claveTxt;
    public Button iniciarBoton;
    public static boolean iniciado;
    public static boolean administrador;

    public static IniciarSesionController getInstance() {
        if (instance == null) {
            instance = new IniciarSesionController();
        }
        return instance;
    }

    public void mostrarRegistrar() {
        try {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Interfaces/Registrar.fxml")));
            PrincipalController.getInstance().panelFormulario.getChildren().setAll(node);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void iniciarSesion() {
        String documento = documentoTxt.getText();
        String clave = claveTxt.getText();
        AgenciaUQ.getInstance().iniciarSesion(documento, clave);
        documentoTxt.setText("");
        claveTxt.setText("");
    }
}
