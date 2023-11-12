package Modelo;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteTuristico {
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private ArrayList<Destino> destinoArrayList;
    private String adicionales;
    private String precio;
}