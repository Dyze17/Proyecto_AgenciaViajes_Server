package Modelo;

import Exceptions.AtributoVacioException;
import lombok.Getter;
import lombok.extern.java.Log;
import java.util.ArrayList;

@Log
public class ViajesUQ {
    @Getter
    private static ViajesUQ viajesUQ;
    @Getter
    private final ArrayList<Destino> destinos = new ArrayList<>();

    private ViajesUQ() {
        //Aqui se inicializa el logger y se deserializan los archivos de persistencia
    }

    private void inicializarLogger(){
        //Esta sería la forma de inicializar el logger
    }

    //El singleton de la clase ViajesUQ
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
}