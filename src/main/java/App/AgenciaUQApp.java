package App;

import Exceptions.AtributoVacioException;
import Modelo.AgenciaUQ;
import Modelo.Destino;
import Modelo.PaqueteTuristico;
import Modelo.Peticion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AgenciaUQApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(AgenciaUQApp.class.getResource("/Interfaces/Principal.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Viajes UQ");
        stage.show();
    }

    public static void main(String[] args) {
        launch(AgenciaUQApp.class,args);
        final int PUERTO = 6969;

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                Socket socketCliente = serverSocket.accept(); // Espera a que llegue una conexión desde un cliente
                System.out.println("Cliente conectado desde: " + socketCliente.getInetAddress());

                // Crear un hilo para manejar la conexión con el cliente
                Thread clientHandlerThread = new Thread(() -> {
                    try {
                        // Crear ObjectInputStream y ObjectOutputStream para la conexión del cliente
                        ObjectInputStream inputStream = new ObjectInputStream(socketCliente.getInputStream());
                        ObjectOutputStream outputStream = new ObjectOutputStream(socketCliente.getOutputStream());

                        // Leer un objeto enviado desde el cliente
                        Object objetoRecibido = inputStream.readObject();
                        verificarMensaje((Peticion) objetoRecibido);

                        // Enviar una respuesta al cliente
                        outputStream.writeObject("¡Mensaje recibido en el servidor!");
                        outputStream.flush();

                        // Cerrar streams y socket para este cliente
                        inputStream.close();
                        outputStream.close();
                        socketCliente.close();
                    } catch (IOException | ClassNotFoundException | AtributoVacioException e) {
                        e.printStackTrace();
                    }
                });

                // Iniciar el hilo para manejar al cliente
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void verificarMensaje(Peticion peticion) throws AtributoVacioException, IOException {
        if(peticion.getMensaje().equalsIgnoreCase("agregardestino")) {
            Destino destino = (Destino) peticion.getObjeto();
            AgenciaUQ.getInstance().agregarDestino(destino);
        } else if(peticion.getMensaje().equalsIgnoreCase("eliminardestino")) {
            Destino destino = (Destino) peticion.getObjeto();
            AgenciaUQ.getInstance().eliminarDestino(destino);
        } else if(peticion.getMensaje().equalsIgnoreCase("editardestino")) {
            Destino destino = (Destino) peticion.getObjeto();
            AgenciaUQ.getInstance().actualizarDestino(destino);
        } else if(peticion.getMensaje().equalsIgnoreCase("agregarpaquete")) {
            PaqueteTuristico paquete = (PaqueteTuristico) peticion.getObjeto();
            AgenciaUQ.getInstance().añadirPaquete(paquete);
        } else if(peticion.getMensaje().equalsIgnoreCase("editarpaquete")) {
            PaqueteTuristico paquete = (PaqueteTuristico) peticion.getObjeto();
            AgenciaUQ.getInstance().actualizarPaquete(paquete);
        } else if(peticion.getMensaje().equalsIgnoreCase("eliminarpaquete")) {
            PaqueteTuristico paquete = (PaqueteTuristico) peticion.getObjeto();
            AgenciaUQ.getInstance().eliminarPaquete(paquete);
        }
        else {
            System.out.println("Mensaje no reconocido");
        }
    }
}