package Controladores;

import Modelo.AgenciaUQ;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class RegistrarController implements Initializable {

    public TextField claveTxt;
    public TextField nombreTxt;
    public TextField DocTxt;
    public TextField emailTxt;
    public TextField celTxt;
    public TextField direccionTxt;
    public Button registrarBoton;
    public ComboBox<String> admCombo;
    private final String[] rol = {"Administrador", "Cliente","Guía"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Agregar elementos al ComboBox durante la inicialización
        admCombo.getItems().addAll(rol);

        // Configurar el campo de texto nombreTxt para permitir solo letras
        nombreTxt.setTextFormatter(new TextFormatter<>((TextFormatter.Change change) -> {
            if (change.getText().matches("[a-zA-Z\\s]*")) {
                return change;
            }
            return null;
        }));

        // Configurar los campos de texto DocTxt y celTxt para permitir solo números
        DocTxt.setTextFormatter(new TextFormatter<>((TextFormatter.Change change) -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        }));

        celTxt.setTextFormatter(new TextFormatter<>((TextFormatter.Change change) -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    public void registrar() throws IOException {
        String nombre = nombreTxt.getText();
        String documento = DocTxt.getText();
        String clave = claveTxt.getText();
        String email = emailTxt.getText();
        String celular = celTxt.getText();
        String direccion = direccionTxt.getText();
        String rol = admCombo.getValue();
        AgenciaUQ.getInstance().registrarUsuario(nombre, documento, clave, email, celular, direccion, rol);
        nombreTxt.setText("");
        DocTxt.setText("");
        claveTxt.setText("");
        emailTxt.setText("");
        celTxt.setText("");
        direccionTxt.setText("");
        admCombo.setValue("");
    }
}

