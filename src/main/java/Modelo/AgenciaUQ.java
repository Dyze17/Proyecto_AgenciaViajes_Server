package Modelo;

import Controladores.IniciarSesionController;
import Exceptions.AtributoVacioException;
import Exceptions.CupoInvalidoException;
import Exceptions.ErrorGuardarCambios;
import Exceptions.FechaNoValidaException;
import Utils.ArchivoUtils;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.extern.java.Log;
import Enum.EstadoReserva;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import Enum.Idioma;
@Log
public class AgenciaUQ {
    @Getter
    private static AgenciaUQ agenciaUQ;
    @Getter
    private final ArrayList<Destino> destinos;
    @Getter
    private final ArrayList<PaqueteTuristico> paquetes;
    @Getter
    private final ArrayList<Cliente> clientes;
    private ArrayList<GuiaTuristico> guias;
    @Getter
    private ArrayList<Reserva> reservas;
    private static final String RUTAUSERS = "src/main/resources/Data/users.txt";
    private static final String RUTADESTINOS = "src/main/resources/Data/destinos.txt";
    private static final String RUTAPAQUETES = "src/main/resources/Data/paquetes.ser";
    private static final String RUTAGUIAS = "src/main/resources/Data/guiasTuristicos.txt";

    private AgenciaUQ() {
        inicializarLogger();
        log.info("Se cre una nueva instancia de AgenciaUQ");

        this.destinos = new ArrayList<>();
        leerDestinos();

        this.paquetes = new ArrayList<>();
        try {
            this.paquetes.addAll((ArrayList<PaqueteTuristico>) ArchivoUtils.deserializarObjeto(RUTAPAQUETES));
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }

        this.clientes = new ArrayList<>();
        leerClientes();
    }

    private void inicializarLogger() {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    //El singleton de la clase AgenciaUQApp
    public static AgenciaUQ getInstance() {
        if (agenciaUQ == null) {
            agenciaUQ = new AgenciaUQ();
        }
        return agenciaUQ;
    }

    //Este metodo agrega un destino al arraylist
    public void agregarDestino(Destino destino) throws AtributoVacioException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                destinos.add(destino);
                escribirDestinos(destino);
                log.info("Destino '" + destino.getPais() + " - " + destino.getCiudad() + "' agregado correctamente");
                JOptionPane.showMessageDialog(null, "Destino agregado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }).start();
    }

    //Este metodo borra el destino del arraylist
    public void eliminarDestino(Destino destino) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                destinos.remove(destino);
                borrarDestino(destino);
                log.info("Destino '" + destino.getPais() + " - " + destino.getCiudad() + "' eliminado correctamente");
                JOptionPane.showMessageDialog(null, "Destino eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }).start();
    }

    //Este metodo borra el destino del archivo plano
    private void borrarDestino(Destino destino) {
        String filePath = RUTADESTINOS;
        String lineToRemove = destino.getPais() + "¡" + destino.getCiudad() + "¡" + destino.getDescripcion() + "¡" + destino.getClima() + "¡" + destino.getImagen();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Leer el contenido del archivo y almacenarlo en una lista
                    Path path = Paths.get(filePath);
                    List<String> lines = Files.readAllLines(path);

                    // Buscar la línea que se desea borrar y removerla
                    lines = lines.stream().filter(line -> !line.contains(lineToRemove)).collect(Collectors.toList());

                    // Escribir el nuevo contenido de vuelta al archivo
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        for (String updatedLine : lines) {
                            writer.write(updatedLine);
                            writer.newLine();
                        }
                    }

                    System.out.println("Línea eliminada exitosamente.");
                    log.info("Destino '" + destino.getPais() + " - " + destino.getCiudad() + "' eliminado correctamente");
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
            }
        }).start();
    }

    //Este metodo actualiza el destino en el arraylist
    public void actualizarDestino(Destino destin) {
        new Thread (new Runnable() {
            @Override
            public void run() {
                for (Destino destino : destinos) {
                    if (destino.getPais().equals(destin.getPais()) && destino.getCiudad().equals(destin.getCiudad())) {
                        Destino nuevo = new Destino();
                        nuevo.setPais(destin.getPais());
                        nuevo.setCiudad(destin.getCiudad());
                        nuevo.setDescripcion(JOptionPane.showInputDialog("Ingrese la nueva descripcion"));
                        nuevo.setClima(JOptionPane.showInputDialog("Ingrese el nuevo clima"));

                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Seleccionar imagen");
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg"));
                        File archivoSeleccionado = fileChooser.showOpenDialog(null);

                        String imagen = "";
                        if (archivoSeleccionado != null) {
                            imagen = archivoSeleccionado.getAbsolutePath();
                        }
                        nuevo.setImagen(imagen);
                        editarDestino(destino, nuevo);
                        break;
                    }
                }
            }
        }).start();
    }

    //Este metodo actualiza el destino del archivo plano
    public void editarDestino(Destino destino, Destino nuevo) {
        String filePath = RUTADESTINOS;
        String lineToReplace = destino.getPais() + "¡" + destino.getCiudad() + "¡" + destino.getDescripcion() + "¡" + destino.getClima() + "¡" + destino.getImagen();
        String newLine = nuevo.getPais() + "¡" + nuevo.getCiudad() + "¡" + nuevo.getDescripcion() + "¡" + nuevo.getClima() + "¡" + nuevo.getImagen();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Leer el contenido del archivo y almacenarlo en una lista
                    Path path = Paths.get(filePath);
                    List<String> lines = Files.readAllLines(path);

                    // Reemplazar la línea existente con la nueva línea
                    for (int i = 0; i < lines.size(); i++) {
                        if (lines.get(i).contains(lineToReplace)) {
                            lines.set(i, newLine);
                            break; // Solo necesitas reemplazar una vez
                        }
                    }

                    // Escribir el nuevo contenido de vuelta al archivo
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        for (String updatedLine : lines) {
                            writer.write(updatedLine);
                            writer.newLine();
                        }
                    }

                    System.out.println("Línea reemplazada exitosamente.");
                    log.info("Destino '" + destino.getPais() + " - " + destino.getCiudad() + "' actualizado correctamente");
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
            }
        }).start();
    }

    //Este metodo lee los destinos del archivo plano
    private void leerDestinos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTADESTINOS);

                    for (String linea : lineas) {
                        String[] datos = linea.split("¡");
                        destinos.add(Destino.builder().pais(datos[0]).ciudad(datos[1]).descripcion(datos[2]).clima(datos[3]).imagen(datos[4]).build());
                    }
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
            }
        }).start();
    }

    //Este metodo escribe los destinos en el archivo plano
    private void escribirDestinos(Destino destino) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String linea = destino.getPais() + "¡" + destino.getCiudad() + "¡" + destino.getDescripcion() + "¡" + destino.getClima() + "¡" + destino.getImagen();
                    ArchivoUtils.escribirArchivoBufferedWriter(RUTADESTINOS, List.of(linea), true);
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Se inicializan los guias turisticos que estan en el archivo guiasTuristicos.Txt
     *
     * @return
     */
    public void leerGuias() throws IOException {
        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoBufferedReader(RUTAGUIAS);
            for (String linea : lineas){
                String[] val = linea.split(";");
                this.guias.add(GuiaTuristico.builder()
                        .nombre(val[0])
                        .identificacion(val[1])
                        .idioma(Idioma.valueOf(val[2]))
                        .telefono(val[3])
                        .calificacion(Double.parseDouble(val[4])).build());
            }
        }catch (IOException e){
            log.severe(e.getMessage());
        }
    }

    public static ArrayList<String> leerGuiasNombres() throws IOException {
        ArrayList<String> nombres = null;
        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoBufferedReader(RUTAGUIAS);
            nombres = new ArrayList<>();
            for (String linea : lineas) {
                String[] val = linea.split(";");
                nombres.add(val[0]);
            }
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        return nombres;
    }

    //Este metodo añade un paquete al arraylist correspondiente
    public void añadirPaquete(PaqueteTuristico paquete) throws AtributoVacioException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                paquetes.add(paquete);
                try {
                    ArchivoUtils.serializarObjeto(RUTAPAQUETES, paquetes);
                } catch (IOException e) {
                    log.severe(e.getMessage());
                }
                log.info("Paquete '" +paquete.getNombre() +"' agregado correctamente");
                JOptionPane.showMessageDialog(null, "Paquete agregado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }).start();
    }

    //Este metodo edita un paquete en su arraylist correspondiente
    public void actualizarPaquete(PaqueteTuristico paquet) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (PaqueteTuristico paquete : paquetes) {
                    if (paquete.getNombre().equals(paquet.getNombre())) {
                        paquete.setAdicionales(JOptionPane.showInputDialog("Ingrese la nueva descripcion"));
                        JOptionPane.showMessageDialog(null, "Paquete actualizado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                        log.info("Paquete '" +paquet.getNombre() +"' actualizado correctamente");
                        try {
                            ArchivoUtils.serializarObjeto(RUTAPAQUETES, paquetes);
                        } catch (IOException e) {
                            log.severe(e.getMessage());
                        }
                        break;
                    }
                }
            }
        }).start();
    }

    //Este metodo elimina un paquete en su arraylist correspondiente
    public void eliminarPaquete(PaqueteTuristico paquet) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(PaqueteTuristico paquete : paquetes) {
                    if(paquete.getNombre().equals(paquet.getNombre())) {
                        paquetes.remove(paquete);
                        try {
                            ArchivoUtils.serializarObjeto(RUTAPAQUETES, paquetes);
                            JOptionPane.showMessageDialog(null, "Paquete eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                            log.info("Paquete '" +paquet.getNombre() +"' eliminado correctamente");
                        } catch (IOException e) {
                            log.severe(e.getMessage());
                        }
                        break;
                    }
                }
            }
        }).start();
    }

    public void iniciarSesion(String usuario, String clave) {
        try(BufferedReader br = new BufferedReader(new FileReader(RUTAUSERS))) {
            String line;
            boolean credencialesCorrectos = false;
            while((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length >= 3 && datos[2].trim().equals(usuario) && datos[0].trim().equals(clave)) {
                    credencialesCorrectos = true;
                    IniciarSesionController.administrador = datos[6].equalsIgnoreCase("administrador");
                    break;
                }
            }
            if(credencialesCorrectos) {
                JOptionPane.showMessageDialog(null, "Bienvenido");
                IniciarSesionController.iniciado = true;
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public void registrarUsuario(String clave, String nombre, String documento, String email, String celular, String direccion, String rolSeleccionado) throws IOException {
        if (clave == null || clave.isEmpty() || nombre == null || nombre.isEmpty() || documento == null || documento.isEmpty() || email == null || email.isEmpty() || celular == null || celular.isEmpty() || direccion == null || direccion.isEmpty() || rolSeleccionado == null || rolSeleccionado.isEmpty()) {
            try {
                throw new AtributoVacioException("Todos los atributos deben tener valores");
            } catch (AtributoVacioException e) {
                log.severe(e.getMessage());
            }
        }
        if (encontrarDocumentoExistente(documento)) {
            JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese documento");
        } else {
            Cliente cliente = Cliente.builder().clave(clave).nombreCompleto(nombre).cedula(documento).correo(email).telefono(celular).direccion(direccion).rol(rolSeleccionado).build();
            clientes.add(cliente);
            escribirUsuario(cliente);
            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
            log.info("Usuario '" + cliente.getNombreCompleto() + "' registrado correctamente");
        }
    }

    private void escribirUsuario(Cliente cliente) {
        try {
            String linea = cliente.getClave() + "," + cliente.getNombreCompleto() + "," + cliente.getCedula() + "," + cliente.getCorreo() + "," + cliente.getTelefono() + "," + cliente.getDireccion() + "," + cliente.getRol();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTAUSERS, List.of(linea), true);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    private boolean encontrarDocumentoExistente(String documento) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTAUSERS))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length > 2 && datos[2].trim().equals(documento.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        return false;
    }

    public void crearReserva (LocalDate fechaSolicitud, LocalDate fechaViaje, String idCliente, short numPersonas, PaqueteTuristico paqueteTuristico, Cliente cliente, GuiaTuristico guia, EstadoReserva estado) throws AtributoVacioException, FechaNoValidaException, CupoInvalidoException, IOException {

        if(fechaSolicitud.isAfter(fechaViaje)){
            log.log( Level.WARNING, "La fecha de solicitud no puede ser después de la fecha de viaje" );
            throw new FechaNoValidaException("La fecha de sloicitud no puede ser después de la fecha de viaje");
        }

        if(idCliente == null || idCliente.isBlank()){
            log.log( Level.WARNING, "La referencia es obligatoria para el registro" );
            throw new AtributoVacioException("La referencia es obligatoria");
        }
        if(!idCliente.matches("[0-9]+")){
            log.log( Level.WARNING, "La referencia no puede ser numérica" );
            throw new AtributoVacioException("La referencia no puede ser numérica");
        }

        Reserva reserva = Reserva.builder()
                .fechaSolicitud(fechaSolicitud)
                .fechaViaje(fechaViaje)
                .idCliente(idCliente)
                .numPersonas(numPersonas)
                .paqueteTuristico(paqueteTuristico)
                .cliente(cliente)
                .guia(guia)
                .estado(estado)
                .build();

        reservas.add(reserva);
        ArchivoUtils.escribirArchivoFormatter("src/main/resources/Data/reservas.data", null);

        ArchivoUtils.mostrarMensaje("Informe", "", "Se ha agregado la reserva correctamente", Alert.AlertType.INFORMATION);
        log.log(Level.INFO, "Se ha registrado una nueva reserva del cliente: "+cliente);
    }

    public static ArrayList<String> leerNombresPaquetesTuristicos() throws IOException {
        ArrayList<String> nombres = new ArrayList<>();
        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoBufferedReader(RUTAPAQUETES);
            for (String linea : lineas) {
                String[] val = linea.split(";");
                nombres.add(val[0]);
            }
        } catch (IOException e) {e.getMessage();}
        return nombres;
    }

    public Cliente clienteEnLista(String nombreCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getNombreCompleto().equals(nombreCliente)) return cliente;
        }
        return null;
    }

    public GuiaTuristico obtenerGuiaPorNombre(String nombreGuia) {
        for (GuiaTuristico guia : guias) {
            if (guia.getNombre().equals(nombreGuia)) {
                return guia;
            }
        }
        return null;
    }

    public PaqueteTuristico obtenerPaquetePorNombre(String nombrePaquete) {
        for (PaqueteTuristico paquete : paquetes) {
            if (paquete.getNombre().equals(nombrePaquete)) {
                return paquete;
            }
        }
        return null;
    }

    public boolean obtenerClienteCedula(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(documento)) return true;
        }
        return false;
    }

    private void leerClientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTAUSERS))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length > 2 && datos[6].trim().equalsIgnoreCase("cliente")) {
                    clientes.add(Cliente.builder().clave(datos[0]).nombreCompleto(datos[1]).cedula(datos[2]).correo(datos[3]).telefono(datos[4]).direccion(datos[5]).rol(datos[6]).build());
                }
            }
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }
  
    public void modificarUsuario(String cedula, String nuevoNombre, String nuevoCorreo, String nuevoTelefono, String nuevaDireccion, String nuevaContraseña) throws IOException, ErrorGuardarCambios, ClassNotFoundException {

        // Obtener la lista actualizada de clientes
        ArrayList<Cliente> listaClientes = (ArrayList<Cliente>) ArchivoUtils.deserializarObjeto(RUTAUSERS);
        Optional<Cliente> clienteOptional = listaClientes.stream()
                .filter(cliente -> cliente.getCedula().equals(cedula))
                .findFirst();

        if (clienteOptional.isPresent()) {
            Cliente cliente = Cliente.builder()
                    .cedula(cedula)
                    .nombreCompleto(nuevoNombre)
                    .correo(nuevoCorreo)
                    .telefono(nuevoTelefono)
                    .direccion(nuevaDireccion)
                    .clave(nuevaContraseña)
                    .build();

            // Guardar la lista actualizada de clientes
            ArchivoUtils.escribirArchivoFormatter("src/main/resources/Data/users.data", null);
            ArchivoUtils.mostrarMensaje("Informe", "", "Se ha modificado el usuario correctamente", Alert.AlertType.INFORMATION);
            log.log(Level.INFO, "Se ha modificado el usuario con cédula: " + cedula);

        } else {
            ArchivoUtils.mostrarMensaje("Error", "Cliente no encontrado", "No se encontró ningún cliente con la cédula proporcionada.", Alert.AlertType.ERROR);
            throw new ErrorGuardarCambios("No se encontró ningún cliente con la cédula proporcionada.");
        }
    }
}