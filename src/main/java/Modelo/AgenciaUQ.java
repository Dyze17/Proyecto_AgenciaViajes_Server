package Modelo;

import Exceptions.AtributoVacioException;
import Utils.ArchivoUtils;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import Enum.Idioma;
@Log
public class AgenciaUQ {
    @Getter
    private static AgenciaUQ agenciaUQ;
    @Getter
    private final ArrayList<Destino> destinos;
    private String imagen;
    private ArrayList<GuiaTuristico> guias;
    private String rutaGuias = "src/main/resources/Data/guiasTuristicos.txt";

    private AgenciaUQ() {
        inicializarLogger();
        log.info("Se cre una nueva instancia de AgenciaUQ");

        this.destinos = new ArrayList<>();
        leerDestinos();
    }

    private void inicializarLogger(){
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            log.addHandler(fh);
        }catch (IOException e){
            log.severe(e.getMessage() );
        }
    }

    //El singleton de la clase AgenciaUQApp
    public static AgenciaUQ getInstance(){
        if(agenciaUQ == null){
            agenciaUQ = new AgenciaUQ();
        }
        return agenciaUQ;
    }

    public Destino agregarDestino(String pais, String ciudad, String clima, String descripcion, String imagen) throws AtributoVacioException {
        if(pais == null || pais.isEmpty() ) {
            throw new AtributoVacioException("El pais no puede estar vacio");
        }
        if(ciudad == null || ciudad.isEmpty() ) {
            throw new AtributoVacioException("La ciudad no puede estar vacia");
        }
        if(clima == null || clima.isEmpty() ) {
            throw new AtributoVacioException("El clima no puede estar vacio");
        }
        if(descripcion == null || descripcion.isEmpty() ) {
            throw new AtributoVacioException("La descripcion no puede estar vacia");
        }
        if(imagen == null || imagen.isEmpty() ) {
            throw new AtributoVacioException("La imagen no puede estar vacia");
        }
        Destino destino = Destino.builder().pais(pais).ciudad(ciudad).clima(clima).descripcion(descripcion).imagen(imagen).build();
        destinos.add(destino);
        escribirDestinos(destino);
        JOptionPane.showMessageDialog(null, "Destino agregado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        return destino;
    }

    public void eliminarDestino(String pais, String ciudad) {
        for (Destino destino : destinos) {
            if (destino.getPais().equals(pais) && destino.getCiudad().equals(ciudad)) {
                destinos.remove(destino);
                borrarDestino(destino);
                break;
            }
        }
    }

    private void borrarDestino(Destino destino) {
        String filePath = "src/main/resources/Data/destinos.txt";
        String lineToRemove = destino.getPais() + "¡" + destino.getCiudad() + "¡" + destino.getDescripcion() + "¡" + destino.getClima() + "¡" + destino.getImagen();
        System.out.println(lineToRemove);
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
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public void actualizarDestino(String pais, String ciudad) {
        for (Destino destino : destinos) {
            if (destino.getPais().equals(pais) && destino.getCiudad().equals(ciudad)) {
                Destino nuevo = new Destino();
                nuevo.setPais(pais);
                nuevo.setCiudad(ciudad);
                nuevo.setDescripcion(JOptionPane.showInputDialog("Ingrese la nueva descripcion"));
                nuevo.setClima(JOptionPane.showInputDialog("Ingrese el nuevo clima"));
                seleccionarImagen();
                nuevo.setImagen(imagen);
                editarDestino(destino, nuevo);
                break;
            }
        }
    }

    public void editarDestino(Destino destino, Destino nuevo) {
        String filePath = "src/main/resources/Data/destinos.txt";
        String lineToReplace = destino.getPais() + "¡" + destino.getCiudad() + "¡" + destino.getDescripcion() + "¡" + destino.getClima() + "¡" + destino.getImagen();
        String newLine = nuevo.getPais() + "¡" + nuevo.getCiudad() + "¡" + nuevo.getDescripcion() + "¡" + nuevo.getClima() + "¡" + nuevo.getImagen();

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
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    private void leerDestinos() {
        try{
            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner("src/main/resources/Data/destinos.txt");

            for(String linea : lineas) {
                String[] datos = linea.split("¡");
                this.destinos.add(Destino.builder().pais(datos[0]).ciudad(datos[1]).descripcion(datos[2]).clima(datos[3]).imagen(datos[4]).build());
            }
        } catch (IOException e) {
            log.severe(e.getMessage() );
        }
    }

    private void escribirDestinos(Destino destino) {
        try{
            String linea = destino.getPais() + "¡" + destino.getCiudad() + "¡" + destino.getDescripcion() + "¡" + destino.getClima() + "¡" + destino.getImagen();
            ArchivoUtils.escribirArchivoBufferedWriter("src/main/resources/Data/destinos.txt", List.of(linea), true);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg"));
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if(archivoSeleccionado != null) {
            imagen = archivoSeleccionado.getAbsolutePath();
        }
    }

    /**
     * Se inicializan los guias turisticos que estan en el archivo guiasTuristicos.Txt
     *
     * @return
     */
    public void leerGuias() throws IOException {
        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoBufferedReader(rutaGuias);
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
            e.getMessage();
        }
    }

    public ArrayList<String> leerGuiasNombres() throws IOException {
        ArrayList<String> nombres = null;
        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoBufferedReader(rutaGuias);
            nombres = new ArrayList<>();
            for (String linea : lineas) {
                String[] val = linea.split(";");
                nombres.add(val[0]);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return nombres;
    }
}