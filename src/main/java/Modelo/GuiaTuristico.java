package Modelo;


import lombok.*;
import Enum.Idioma;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuiaTuristico {
    private String nombre;
    private String identificacion;
    private Idioma idioma;
    private String telefono;
    private double calificacion;



}
