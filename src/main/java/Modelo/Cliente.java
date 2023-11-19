package Modelo;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente implements Serializable {
    private String cedula;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String direccion;
    private String clave;
    private String rol;
}