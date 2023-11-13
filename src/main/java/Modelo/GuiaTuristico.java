package Modelo;


import Utils.ArchivoUtils;
import lombok.*;
import Enum.Idioma;

import java.io.IOException;
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
