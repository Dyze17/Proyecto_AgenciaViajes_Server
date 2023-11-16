package Modelo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteTuristico implements Serializable {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<Destino> destinoArrayList;
    private String adicionales;
    private String precio;
}