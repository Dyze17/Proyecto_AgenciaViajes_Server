package Modelo;

import Exceptions.AtributoVacioException;
import Utils.ArchivoUtils;
import lombok.Getter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

@Log
public class ViajesUQ {
    @Getter
    private static ViajesUQ viajesUQ;
    @Getter
    private final ArrayList<Destino> destinos;

    private ViajesUQ() {
        inicializarLogger();
        log.info("Se cre una nueva instancia de ViajesUQ");

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

    //El singleton de la clase AgenciaUQ
    public static ViajesUQ getInstance(){
        if(viajesUQ == null){
            viajesUQ = new ViajesUQ();
        }
        return viajesUQ;
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
                break;
            }
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
}